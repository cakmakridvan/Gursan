package com.rotamobile.gursan.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.rotamobile.gursan.R;
import com.rotamobile.gursan.model.bildirim.BildirimModel;
import com.rotamobile.gursan.ui.adapters.BildiriAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class Bildirimler extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private TextView title,talep_list;
    private ImageButton back_btn,clearMsj;
    private Realm realm;
    private RecyclerView recyclerView;
    private List<BildirimModel> list_bildirim;
    private BildiriAdapter bildiriAdapter;
    private RealmResults<BildirimModel> is_emri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bildirimler);

        initialize();
    }

    private void initialize() {

        realm = Realm.getDefaultInstance();

        title = findViewById(R.id.toolbar_title_bildirimler);
        title.setText("Bildirimler");

        back_btn = findViewById(R.id.back_button_bildirimler);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        toolbar = findViewById(R.id.toolbar_bildirimler);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recycler_bildirimler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(Bildirimler.this));

        list_bildirim = new ArrayList<>();

        clearMsj = findViewById(R.id.btn_clearmsj);
        clearMsj.setOnClickListener(this);


        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

            //getting all data from realm DB
                is_emri = realm.where(BildirimModel.class).sort("id", Sort.DESCENDING).findAll();
                Log.i("İş Emirleri:", "ds" + is_emri);

                for (int i = 0; i < is_emri.size(); i++) {

                    String getTime = is_emri.get(i).getInsertTime();
                    String getSubject = is_emri.get(i).getSubjectText();
                    String getText = is_emri.get(i).getText();
                    int getType =is_emri.get(i).getType();
                    int getUserID = is_emri.get(i).getUserId();
                    String getWorkID = is_emri.get(i).getWorkId();

                    BildirimModel bildirimModel = new BildirimModel(getTime,getSubject,getText,getType,getUserID,getWorkID);
                    list_bildirim.add(bildirimModel);

                }

                bildiriAdapter = new BildiriAdapter(list_bildirim,Bildirimler.this);
                recyclerView.setAdapter(bildiriAdapter);
            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btn_clearmsj:

                is_emri = realm.where(BildirimModel.class).findAll();
                if(is_emri.size() > 0) {

                    new SweetAlertDialog(Bildirimler.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText(getString(R.string.bildirim_silme))
                            .setContentText(getString(R.string.bildirim_icerik))
                            .setCancelText(getString(R.string.bildirim_silme_cancel))
                            .setConfirmText(getString(R.string.bildirim_silme_onay))
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

                                    realm.executeTransaction(new Realm.Transaction() {
                                        @Override
                                        public void execute(Realm realm) {
                                            realm.deleteAll();
                                            finish();
                                            startActivity(getIntent());
                                        }
                                    });

                                }
                            })
                            .show();
                }

            break;
        }
    }
}
