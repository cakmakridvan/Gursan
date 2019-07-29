package com.rotamobile.gursan.service;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rotamobile.gursan.Main;
import com.rotamobile.gursan.R;
import com.rotamobile.gursan.model.bildirim.BildirimModel;
import com.rotamobile.gursan.model.eventBus.MessageEvent;
import com.rotamobile.gursan.ui.activity.Bildirimler;

import org.greenrobot.eventbus.EventBus;

import io.fabric.sdk.android.Fabric;
import io.paperdb.Paper;
import io.realm.Realm;
import io.realm.RealmResults;
import me.leolin.shortcutbadger.ShortcutBadger;

public class FireBaseService extends Service {

    private DatabaseReference mDatabase;
    private Realm realm;
    private String get_userID;
    private String getInserTime = "",getSubject = "",getText = "",getWorkId = "";
    private int getType = 0,getUserId = 0;
    private BildirimModel bildirim;
    private RealmResults<BildirimModel> is_emri;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(FireBaseService.this, new Crashlytics());
        // Get a Realm instance for this thread
        realm = Realm.getDefaultInstance();
        Paper.init(FireBaseService.this);
        get_userID =  Paper.book().read("user_id");

        //FireBase RealTime Database initialize
        mDatabase = FirebaseDatabase.getInstance().getReference(get_userID);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("Tag:", "App title updated");

                for (DataSnapshot node : dataSnapshot.getChildren()) {

                    String get_node_tip = node.getKey();
                    if(get_node_tip.equals("InsertTime")){
                        getInserTime = node.getValue().toString();
                        Log.i("InserTime",getInserTime);
                    }else if(get_node_tip.equals("SubjectText")){
                        getSubject = node.getValue().toString();
                        Log.i("Subject",getSubject);
                    }else if(get_node_tip.equals("Text")){
                        getText = node.getValue().toString();
                        Log.i("Text",getText);
                    }else if(get_node_tip.equals("Type")){
                        getType = node.getValue(Integer.class);
                        Log.i("Type",""+getType);
                    }else if(get_node_tip.equals("UserID")){
                        getUserId = (int) node.getValue(Integer.class);
                        Log.i("UserId",""+getUserId);
                    }else if(get_node_tip.equals("WorkID")){
                        getWorkId = (String) node.getValue();
                        Log.i("WorkId",getWorkId);
                    }

                }

                if(dataSnapshot.getValue() != null) {

                    //send notification
                    sendNotification(getSubject, getText);

                    //Insert value in Realm Database
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {

                            //creating auto increment of primary key
                            Number maxId = realm.where(BildirimModel.class).max("id");
                            int nextId = (maxId == null) ? 1 : maxId.intValue() + 1;
                            //Saving process to Realm Databse
                            bildirim = realm.createObject(BildirimModel.class, nextId);
                            bildirim.setInsertTime(getInserTime);
                            bildirim.setSubjectText(getSubject);
                            bildirim.setText(getText);
                            bildirim.setType(getType);
                            bildirim.setUserId(getUserId);
                            bildirim.setWorkId(getWorkId);

                            //commit transaction
                            realm.copyToRealm(bildirim);

                        }
                    });

                 //getting all data from realm DB
                    is_emri = realm.where(BildirimModel.class).findAll();
                    Log.i("İş Emirleri:", "ds" + is_emri);

                 //Post Value with EventBus
                    EventBus.getDefault().post(new MessageEvent(is_emri.size()));
                 //Change count of badger of app icon
                    ShortcutBadger.applyCount(FireBaseService.this, is_emri.size());

                    //Remove value at FireBase RealDatabase
                    mDatabase.removeValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e("TAG", "Failed to read app title value.", error.toException());
            }
        });

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

/*    @Override
    public void onTaskRemoved(Intent rootIntent) {
        // TODO Auto-generated method stub
        Intent restartService = new Intent(getApplicationContext(),
                this.getClass());
        restartService.setPackage(getPackageName());
        PendingIntent restartServicePI = PendingIntent.getService(
                getApplicationContext(), 1, restartService,
                PendingIntent.FLAG_ONE_SHOT);

        //Restart the service once it has been killed android


        AlarmManager alarmService = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarmService.set(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() +100, restartServicePI);

    }*/

    private void sendNotification(String messageTitle, String messageBody) {

        //When Click Notification
        Intent intent = new Intent(this, Bildirimler.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        long[] pattern = {500,500,500,500};//Titreşim ayarı

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.notification_logo)
                .setContentTitle(messageTitle)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setVibrate(pattern)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        try {
            Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                    + "://" + this.getPackageName() + "/raw/notification");
            Ringtone r = RingtoneManager.getRingtone(this, alarmSound);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

}
