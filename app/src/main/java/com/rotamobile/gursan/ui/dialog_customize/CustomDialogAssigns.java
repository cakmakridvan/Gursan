package com.rotamobile.gursan.ui.dialog_customize;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;
import com.rotamobile.gursan.R;
import com.rotamobile.gursan.data.Server;
import com.rotamobile.gursan.model.workStatus.DataWorkStatus;
import com.rotamobile.gursan.model.workStatus.ModelWorkStatus;
import com.rotamobile.gursan.ui.adapters.WorkStatusAdapter;

import java.util.ArrayList;
import java.util.List;

public class CustomDialogAssigns extends Dialog {

    public  Activity c;
    private Integer insert_userID;
    private Integer get_proje_id;
    private Integer workOrder_id;
    private RecyclerView recycler;
    private ProgressDialog progressDialog_workStatus;
    private ArrayList<ModelWorkStatus> dataWorkStatus;
    private DataWorkStatus response_workStatus;
    private List<ModelWorkStatus> list_workStatus;
    private RecyclerView.Adapter adapter;
    private WorkStatusList workStatusListTask = null;

    public CustomDialogAssigns (Activity a, Integer project_id, Integer workOrder_id, Integer insert_userID) {
        super(a);
        this.c = a;
        get_proje_id = project_id;
        this.workOrder_id = workOrder_id;
        this.insert_userID = insert_userID;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_dialog_visible_assigns);

        list_workStatus = new ArrayList<>();
        recycler = findViewById(R.id.recycler_assigns_workStatus);
        recycler.setLayoutManager(new LinearLayoutManager(c));

        //Progress Diaolog initialize
        progressDialog_workStatus = new ProgressDialog(c);
        progressDialog_workStatus.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressDialog_workStatus.setIndeterminate(true);

        workStatusListTask = new WorkStatusList(workOrder_id);
        workStatusListTask.execute((Void) null);
    }

    public class WorkStatusList extends AsyncTask<Void, Void, Boolean> {

        private final Integer workOrderID;
        private String get_mesaj_dataWorkStatus = "";

        WorkStatusList(Integer workOrderID){

            this.workOrderID = workOrderID;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog_workStatus.setMessage("\tLoading...");
            progressDialog_workStatus.setCancelable(false);
            progressDialog_workStatus.show();
            progressDialog_workStatus.setContentView(R.layout.custom_progress);
        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            try {
                String getWorkStatus_service = Server.WorkStatusList(workOrderID);
                if(!getWorkStatus_service.trim().equalsIgnoreCase("false")){

                    response_workStatus = new Gson().fromJson(getWorkStatus_service,DataWorkStatus.class);
                    dataWorkStatus = response_workStatus.getData_list();
                    Log.i("Tag:dataUserType",""+dataWorkStatus);
                    get_mesaj_dataWorkStatus = "true";

                }else{
                    get_mesaj_dataWorkStatus = "false";
                }

            } catch (Exception e) {

            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            if(!get_mesaj_dataWorkStatus.equals("false")) {
                progressDialog_workStatus.dismiss();
                if (dataWorkStatus.size() > 0) {

                    for(int i=0;i<dataWorkStatus.size();i++) {

                        ModelWorkStatus modelWorkStatus = new ModelWorkStatus(dataWorkStatus.get(i).getAssignedName(),dataWorkStatus.get(i).getAssignsName());
                        list_workStatus.add(modelWorkStatus);

                    }
                    adapter = new WorkStatusAdapter(list_workStatus,c);
                    recycler.setAdapter(adapter);
                }
            }else{

                progressDialog_workStatus.dismiss();
            }

        }
    }
}
