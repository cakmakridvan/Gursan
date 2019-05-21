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

import com.google.gson.Gson;
import com.rotamobile.gursan.R;
import com.rotamobile.gursan.data.Server;
import com.rotamobile.gursan.model.disServisTalepList.DataDisServisTalep;
import com.rotamobile.gursan.model.disServisTalepList.ModelDisServiTalep;
import com.rotamobile.gursan.ui.adapters.DisServisAdapter;

import java.util.ArrayList;
import java.util.List;

public class DisServisTalepList extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView title,talep_list;
    private ImageButton back_btn;
    private RecyclerView recyclerView;
    private DataDisServisTalep response_dataDisServis;
    private ArrayList<ModelDisServiTalep> data_list;
    private String get_mesaj_dis_servis_list = "";
    private List<ModelDisServiTalep> list_data;
    private DisServisAdapter disServisAdapter;
    private DisServisTalep disServisTalep = null;
    private ProgressDialog progressDialog;
    private Bundle extras;
    private Integer get_workerID = 0;
    private TextView emptyList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dis_talep_list);

        emptyList = findViewById(R.id.empty_talep_dis_servis_talep_list);
        title = findViewById(R.id.toolbar_title_dis_servis_talep_list);
        title.setText("Dış Servis Talep Listesi");

        back_btn = findViewById(R.id.back_button_dis_servis_talep_list);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        toolbar = findViewById(R.id.toolbar_top_talep_list);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recycler_talep_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(DisServisTalepList.this));

        list_data = new ArrayList<>();
        //get Values from DisServisForm
        extras = getIntent().getExtras();
        if(extras != null) {
            get_workerID = extras.getInt("workerID"); //get WorkID
        }

        //Progress Diaolog initialize
        progressDialog = new ProgressDialog(DisServisTalepList.this);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressDialog.setIndeterminate(true);


        disServisTalep = new DisServisTalep(get_workerID);
        disServisTalep.execute((Void) null);
    }


    public class DisServisTalep extends AsyncTask<Void, Void, Boolean> {

        private Integer workOrder_id;

        DisServisTalep(Integer workOrder_id){

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
                String codeResult = Server.GetDisServisByWorkOrder(workOrder_id);
                if(!codeResult.trim().equalsIgnoreCase("false")){

                    response_dataDisServis = new Gson().fromJson(codeResult, DataDisServisTalep.class);
                    data_list = response_dataDisServis.getData_list();
                    Log.i("Tag:DeviceHistoryList",""+ data_list);
                    get_mesaj_dis_servis_list = "true";
                }else{
                    get_mesaj_dis_servis_list = "false";
                }

            }catch(Exception e){

            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            if(!get_mesaj_dis_servis_list.equals("false")){

                progressDialog.dismiss();
                if(data_list.size() > 0){

                    for(int i=0; i<data_list.size(); i++){

                        ModelDisServiTalep modelDisServiTalep = new ModelDisServiTalep(data_list.get(i).getProductAndService(),data_list.get(i).getDescription(),
                                data_list.get(i).getAmount(),data_list.get(i).getUnitName());
                        list_data.add(modelDisServiTalep);
                    }
                    disServisAdapter = new DisServisAdapter(list_data,getApplicationContext());
                    recyclerView.setAdapter(disServisAdapter);
                }else{
                    progressDialog.dismiss();
                    emptyList.setVisibility(View.VISIBLE);
                }
            }else{

                progressDialog.dismiss();
                emptyList.setVisibility(View.VISIBLE);
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();

            disServisTalep = null;
            progressDialog.dismiss();
        }
    }
}
