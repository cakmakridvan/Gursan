package com.rotamobile.gursan;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.crashlytics.android.Crashlytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rotamobile.gursan.data.prefs.LocaleHelper;
import com.rotamobile.gursan.model.bildirim.BildirimModel;
import com.rotamobile.gursan.model.eventBus.MessageEvent;
import com.rotamobile.gursan.model.eventBus.MessageUserID;
import com.rotamobile.gursan.service.FireBaseService;
import com.rotamobile.gursan.ui.activity.Bildirimler;
import com.rotamobile.gursan.ui.activity.CodeReader;
import com.rotamobile.gursan.ui.bottom_navigation.MainBottomNavigation;
import com.rotamobile.gursan.ui.fragment.Exit;
import com.rotamobile.gursan.ui.fragment.Home;
import com.rotamobile.gursan.ui.fragment.JobOrder;
import com.rotamobile.gursan.ui.fragment.Notifications;
import com.rotamobile.gursan.ui.fragment.Settings;
import com.rotamobile.gursan.ui.login.Login;
import com.rotamobile.gursan.utils.CircleTransform;
import com.rotamobile.gursan.utils.CountDrawable;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.fabric.sdk.android.Fabric;
import io.paperdb.Paper;
import io.realm.Realm;
import io.realm.RealmResults;
import me.leolin.shortcutbadger.ShortcutBadger;

public class Main extends AppCompatActivity {
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase,"tr"));
    }

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private ImageView imgNavHeaderBg, imgProfile;
    private TextView txtName, txtWebsite;
    private Toolbar toolbar;
    private FloatingActionButton fab;


    // urls to load navigation header background image
    // and profile image
    private static final String urlNavHeaderBg = "https://api.androidhive.info/images/nav-menu-header-bg.jpg";
    private static final String urlProfileImg = "https://pngimage.net/wp-content/uploads/2018/06/photo-de-profil-png-8.png";

    // index to identify current nav menu item
    public static int navItemIndex = 0;

    // tags used to attach the fragments
    private static final String TAG_HOME = "ana sayfa";
    private static final String TAG_PHOTOS = "iş emri oluştur";
    private static final String TAG_MOVIES = "gelen iş emirleri";
    private static final String TAG_NOTIFICATIONS = "bildirimler";
    private static final String TAG_SETTINGS = "ayarlar";
    private static final String TAG_CIKIS = "çıkış";
    public static String CURRENT_TAG = TAG_HOME;

    // toolbar titles respected to selected nav menu item
    private String[] activityTitles;

    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;
    private Menu defaultMenu;
    private int x = 0;

    private String get_name;
    private String get_surname;
    private String get_userID;


    private FirebaseDatabase mFirebaseInstance;
    private Realm realm;

    private RealmResults<BildirimModel> is_emri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(Main.this, new Crashlytics());
        setContentView(R.layout.activity_main);

        //Init Paper first (Paper is a fast NoSQL-like storage for Java)
        Paper.init(Main.this);

        realm = Realm.getDefaultInstance();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        get_userID =  Paper.book().read("user_id");


     //start FireBase Service
        if(!isMyServiceRunning(FireBaseService.class)) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                startService(new Intent(this, FireBaseService.class));
            }else{
                startForegroundService(new Intent(this, FireBaseService.class));

            }
        }
        //getting User information from Login
/*        Bundle get_datas = new Bundle();
        get_datas = getIntent().getExtras();
        if(get_datas != null){

            get_name = get_datas.getString("name");
            get_surname = get_datas.getString("last_name");
        }*/
//getting User information from Login
        get_name = Paper.book().read("name");
        get_surname = Paper.book().read("last_name");


        mHandler = new Handler();

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/

                navItemIndex = 1;
                CURRENT_TAG = TAG_PHOTOS;
                loadHomeFragment();

/*                JobOrder fragment = new JobOrder();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame, fragment);
                transaction.commit();*/

            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
     //Disable icon colorStateList in NavigationView
        navigationView.setItemIconTintList(null);

        // Navigation view header
        navHeader = navigationView.getHeaderView(0);
        txtName = (TextView) navHeader.findViewById(R.id.name);
        txtWebsite = (TextView) navHeader.findViewById(R.id.website);
        imgNavHeaderBg = (ImageView) navHeader.findViewById(R.id.img_header_bg);
        imgProfile = (ImageView) navHeader.findViewById(R.id.img_profile);

        // load toolbar titles from string resources
        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);

        // load nav menu header data
        loadNavHeader();

        // initializing navigation menu
        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }

       //Get Saved selected Language
        existingLanguage();
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }



    @Override
    protected void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                is_emri = realm.where(BildirimModel.class).findAll();
                Log.i("İş Emirleri:", "ds" + is_emri);
                EventBus.getDefault().post(new MessageEvent(is_emri.size()));

            }
        });
    }

    @Subscribe
    public void onEvent(MessageEvent event){
        setCount(Main.this,event.getCounter());
    }

    private void existingLanguage() {
        //Get Selected Language,Default Language is Turkish
        String get_language = Paper.book().read("language");
        if(get_language == null)
            Paper.book().write("language","tr");

        updateLanguage((String)Paper.book().read("language"));
    }

    private void updateLanguage(String lang) {

        Context context = LocaleHelper.setLocale(this,lang);
        Resources resources = context.getResources();
        //txtName.setText(resources.getString(R.string.nav_home));
    }


    private void loadNavHeader() {
        // name, website
        txtName.setText(get_name + " " + get_surname);
        txtWebsite.setText(get_name + "@gursan.com");

        // loading header background image
/*        Glide.with(this).load(urlNavHeaderBg)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgNavHeaderBg);*/

        // Loading profile image
        Glide.with(this).load(urlProfileImg)
                .crossFade()
                .thumbnail(0.5f)
                .bitmapTransform(new CircleTransform(this))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgProfile);

        // showing dot next to notifications label
        navigationView.getMenu().getItem(3).setActionView(R.layout.menu_dot);
    }


    private void loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();

        // set toolbar title
        setToolbarTitle();

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();

            // show or hide the fab button
            toggleFab();
            return;
        }

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        // show or hide the fab button
        toggleFab();

        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                // ana sayfa

              Home home = new Home();
              return home;

            case 1:
                // iş emri oluştur
                JobOrder jobOrder = new JobOrder();
                return jobOrder;

            case 2:
                // gelen iş emirleri
/*                IncomingJobOrder incomingJobOrder = new IncomingJobOrder();
                return incomingJobOrder;*/

/*                Intent go_home = new Intent(getApplicationContext(),MainBottomNavigation.class);
                startActivity(go_home);*/

            case 3:
                // bildirimler
/*                Notifications notifications = new Notifications();
                return notifications;*/

/*                Intent go_bildirim = new Intent(Main.this,Bildirimler.class);
                startActivity(go_bildirim);*/

            case 4:
                // iletisim
                Settings settings = new Settings();
                return settings;

            case 5:
                Exit exit = new Exit();
                return exit;

            default:
                return new Home();
        }
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_ana_sayfa:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_HOME;
                        break;
                    case R.id.nav_is_emri_oluştur:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_PHOTOS;
                        break;
                    case R.id.nav_gelen_is_emirleri:
/*                        navItemIndex = 2;
                        CURRENT_TAG = TAG_MOVIES;*/

                        Intent go_home = new Intent(Main.this,MainBottomNavigation.class);
                        startActivity(go_home);

                        break;
                    case R.id.nav_bildirimler:
/*                        navItemIndex = 3;
                        CURRENT_TAG = TAG_NOTIFICATIONS;*/

                        Intent go_bildirim = new Intent(Main.this,Bildirimler.class);
                        startActivity(go_bildirim);

                        break;
                    case R.id.nav_ayarlar:
                        Intent go_scanner = new Intent(Main.this,CodeReader.class);
                        startActivity(go_scanner);

                        break;
                    case R.id.nav_iletisim:
                        navItemIndex = 4;
                        CURRENT_TAG = TAG_SETTINGS;
                        break;
                    case R.id.nav_cikis:

                        new SweetAlertDialog(Main.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText(getString(R.string.uygulama_cikis))
                                .setContentText(getString(R.string.hesap_degistir))
                                .setCancelText(getString(R.string.iptal))
                                .setConfirmText(getString(R.string.hesap_cikis))
                                .showCancelButton(true)
                                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.cancel();
                                    }
                                })
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {

                                        Intent go_login = new Intent(Main.this,Login.class);
                                        go_login.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        go_login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(go_login);
                                        finish();

                                     //Delete existing token
                                        Paper.book().delete("token");
                                        Paper.book().delete("user_id");

                                        stopService(new Intent(Main.this,FireBaseService.class));

                                     //Stop Service
                                        //stopService(new Intent(Main.this, FireBaseService.class));

                                    }
                                })
                                .show();

                        break;
/*                    case R.id.nav_about_us:
                        // launch new intent instead of loading fragment
                        startActivity(new Intent(MainActivity.this, AboutUsActivity.class));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_privacy_policy:
                        // launch new intent instead of loading fragment
                        startActivity(new Intent(Main.this, PrivacyPolicyActivity.class));
                        drawer.closeDrawers();
                        return true;
*/
                    default:
                        navItemIndex = 0;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadHomeFragment();

                return true;
            }
        });

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }

        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                loadHomeFragment();
                return;
            }
        }

        super.onBackPressed();

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        defaultMenu = menu;

        setCount(Main.this,is_emri.size());

        return true;
    }

    public void setCount(Context context, int count) {
        
        MenuItem menuItem = defaultMenu.findItem(R.id.action_notification);
        LayerDrawable icon = (LayerDrawable) menuItem.getIcon();

        CountDrawable badge;

        // Reuse drawable if possible
        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_group_count);
        if (reuse != null && reuse instanceof CountDrawable) {
            badge = (CountDrawable) reuse;
        } else {
            badge = new CountDrawable(context);
        }

        badge.setCount(""+count);
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_group_count, badge);
    }

   @SuppressLint("RestrictedApi")
   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.main,menu);

       if(menu instanceof MenuBuilder){

           MenuBuilder menuBuilder = (MenuBuilder) menu;
           menuBuilder.setOptionalIconsVisible(true);
       }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        String get_saved_language = Paper.book().read("language");
       //Notification icon in ToolBar
        if (id == R.id.action_notification) {

            Intent go_bildirim = new Intent(Main.this,Bildirimler.class);
            startActivity(go_bildirim);

/*            Toast.makeText(Main.this, "Bildirim comes!", Toast.LENGTH_LONG).show();
            setCount(this, x++);
            return true;*/
        }

       //Turkish item selected in ToolBar
        if(id == R.id.action_tr){

            if(!get_saved_language.equals("tr")) {

                new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText(getString(R.string.dil_secenegi))
                        .setContentText(getString(R.string.dil_msj_title) + " " + getString(R.string.turkce) + " " + getString(R.string.dil_msj_title1))
                        .setCancelText(getString(R.string.sweet_cevap2))
                        .setConfirmText(getString(R.string.sweet_cevap1))
                        .showCancelButton(true)
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.cancel();
                            }
                        })
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                Paper.book().write("language", "tr");
                                updateLanguage2((String) Paper.book().read("language"));
                            }
                        })
                        .show();
            }
            else{

                new SweetAlertDialog(this)
                        .setTitleText(getString(R.string.secilmis_dil))
                        .show();
            }
        }

        //English item selected in ToolBar
        if(id == R.id.action_en){

            if(!get_saved_language.equals("en")) {

                new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText(getString(R.string.dil_secenegi))
                        .setContentText(getString(R.string.dil_msj_title) + " " + getString(R.string.ingilizce) +  " " + getString(R.string.dil_msj_title1))
                        .setCancelText(getString(R.string.sweet_cevap2))
                        .setConfirmText(getString(R.string.sweet_cevap1))
                        .showCancelButton(true)
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.cancel();
                            }
                        })
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                Paper.book().write("language", "en");
                                updateLanguage2((String) Paper.book().read("language"));
                            }
                        })
                        .show();
            }
            else{

                new SweetAlertDialog(this)
                        .setTitleText(getString(R.string.secilmis_dil))
                        .show();
            }

        }

        return false;
    }

    private void updateLanguage2(String language) {
        Context context = LocaleHelper.setLocale(this,language);
        Resources resources = context.getResources();
        //txtName.setText(resources.getString(R.string.nav_home));

        //Refreshing Activity
        finish();
        startActivity(getIntent());
        overridePendingTransition(0, 0);

    }

    // show or hide the fab
    private void toggleFab() {
        if (navItemIndex == 0)
            fab.show();
        else
            fab.hide();
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) Main.this.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }




}
