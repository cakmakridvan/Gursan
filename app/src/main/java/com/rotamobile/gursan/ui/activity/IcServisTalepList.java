package com.rotamobile.gursan.ui.activity;

import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
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

import com.crashlytics.android.Crashlytics;
import com.google.gson.Gson;
import com.rotamobile.gursan.Main;
import com.rotamobile.gursan.R;
import com.rotamobile.gursan.data.Server;
import com.rotamobile.gursan.model.icServisTalepList.DataIcServisTalep;
import com.rotamobile.gursan.model.icServisTalepList.ModelIcServiTalep;
import com.rotamobile.gursan.ui.adapters.IcServisAdapter;

import java.util.ArrayList;
import java.util.List;

import io.fabric.sdk.android.Fabric;


public class IcServisTalepList extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView title,talep_list;
    private ImageButton back_btn;
    private RecyclerView recyclerView;
    private DataIcServisTalep response_dataIcServis;
    private ArrayList<ModelIcServiTalep> data_list;
    private String get_mesaj_ic_servis_list = "";
    private List<ModelIcServiTalep> list_data;
    private IcServisAdapter icServisAdapter;
    private IcServisTalep icServisTalep = null;
    private ProgressDialog progressDialog;
    private Bundle extras;
    private Integer get_workerID = 0;
    private TextView emptyList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(IcServisTalepList.this, new Crashlytics());
        setContentView(R.layout.ic_talep_list);

        emptyList = findViewById(R.id.empty_talep_ic_servis_talep_list);
        title = findViewById(R.id.toolbar_title_ic_servis_talep_list);
        title.setText("İç Servis Talep Listesi");

        back_btn = findViewById(R.id.back_button_ic_servis_talep_list);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        toolbar = findViewById(R.id.toolbar_top_ic_talep_list);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recycler_ic_talep_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(IcServisTalepList.this));

        list_data = new ArrayList<>();
        //get Values from İçServisForm
        extras = getIntent().getExtras();
        if(extras != null) {
            get_workerID = extras.getInt("workerID"); //get WorkID
        }

        //Progress Diaolog initialize
        progressDialog = new ProgressDialog(IcServisTalepList.this);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressDialog.setIndeterminate(true);



    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();

        icServisTalep = new IcServisTalep(get_workerID);
        icServisTalep.execute((Void) null);
    }

    public class IcServisTalep extends AsyncTask<Void, Void, Boolean> {

        private Integer workOrder_id;

        IcServisTalep(Integer workOrder_id){

            this.workOrder_id = workOrder_id;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog.setMessage("\tLoading...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            progressDialog.setContentView(R.layout.custom_progress);
        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            try {
                String codeResult = Server.GetIcServisByWorkOrder(workOrder_id);
                if(!codeResult.trim().equalsIgnoreCase("false")){

                    response_dataIcServis = new Gson().fromJson(codeResult, DataIcServisTalep.class);
                    data_list = response_dataIcServis.getData_list_ic();
                    Log.i("Tag:DeviceHistoryList",""+ data_list);
                    get_mesaj_ic_servis_list = "true";
                }else{
                    get_mesaj_ic_servis_list = "false";
                }

            }catch(Exception e){
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            if(!get_mesaj_ic_servis_list.equals("false")){
                list_data.clear();
                progressDialog.dismiss();
                if(data_list.size() > 0){

                    for(int i=0; i<data_list.size(); i++){

                        ModelIcServiTalep modelIcServiTalep = new ModelIcServiTalep(data_list.get(i).getID_ic(),data_list.get(i).getProductID_ic(),data_list.get(i).getAmount_ic(),
                                data_list.get(i).getUnitPrice_ic(),data_list.get(i).getProductName_ic(),data_list.get(i).getUnitName_ic());
                        list_data.add(modelIcServiTalep);
                    }
                    icServisAdapter = new IcServisAdapter(list_data,IcServisTalepList.this);
                    recyclerView.setAdapter(icServisAdapter);
                }else{
                    progressDialog.dismiss();
                    emptyList.setVisibility(View.VISIBLE);

                    icServisAdapter = new IcServisAdapter(list_data,IcServisTalepList.this);
                    recyclerView.setAdapter(icServisAdapter);
                }
            }else{

                progressDialog.dismiss();
                emptyList.setVisibility(View.VISIBLE);
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();

            icServisTalep = null;
            progressDialog.dismiss();
        }
    }
}


