package com.rotamobile.gursan.ui.dialog_customize;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.rotamobile.gursan.R;
import com.rotamobile.gursan.data.Server;
import com.rotamobile.gursan.model.userTypeWithProject.DataUserType;
import com.rotamobile.gursan.model.userTypeWithProject.ModelUserType;
import com.rotamobile.gursan.model.workStatus.DataWorkStatus;
import com.rotamobile.gursan.model.workStatus.ModelWorkStatus;
import com.rotamobile.gursan.ui.adapters.WorkStatusAdapter;
import com.rotamobile.gursan.ui.details.Details;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;


public class CustomDialogClass extends Dialog {

    private ProgressDialog progressDialog_todoListUpdate,progressDialog_workStatus,progressDialog_workStatusAdd;
    public Activity c;
    private DataUserType response_dataUserType;
    private ArrayList<ModelUserType> dataUSerType;
    private DataWorkStatus response_workStatus;
    private ArrayList<ModelWorkStatus> dataWorkStatus;
    private List<String> list_assignedUSer;
    private Spinner assigned_user;
    private Integer get_assigned_user_id = 0;

    private GetByUserTypeWithProject getByUserTypeWithProject = null;
    private WorkStatusList workStatusListTask = null;
    private WorkStatusAdd workStatusAdd = null;

    private String get_userTypeID = "";
    private Integer get_proje_id;
    private Integer workOrder_id;
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private List<ModelWorkStatus> list_workStatus;
    private Button btn_kisi_ata;

    public CustomDialogClass(Activity a,Integer project_id,Integer workOrder_id) {
        super(a);
        this.c = a;
        get_proje_id = project_id;
        this.workOrder_id = workOrder_id;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_dialog);

        list_workStatus = new ArrayList<>();
        btn_kisi_ata = findViewById(R.id.kisi_ata);
        btn_kisi_ata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(get_assigned_user_id != 0){

                    workStatusAdd= new WorkStatusAdd(get_assigned_user_id);
                    workStatusAdd.execute((Void) null);
                }else{

                    Toast.makeText(c,"Kişiyi Seçiniz",Toast.LENGTH_LONG).show();
                }

            }
        });

        recycler = findViewById(R.id.recycler_workStatus);
        recycler.setLayoutManager(new LinearLayoutManager(c));

        //Progress Diaolog initialize
        progressDialog_todoListUpdate = new ProgressDialog(c);
        progressDialog_todoListUpdate.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressDialog_todoListUpdate.setIndeterminate(true);

        //Progress Diaolog initialize
        progressDialog_workStatus = new ProgressDialog(c);
        progressDialog_workStatus.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressDialog_workStatus.setIndeterminate(true);

        //Progress Diaolog initialize
        progressDialog_workStatusAdd = new ProgressDialog(c);
        progressDialog_workStatusAdd.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressDialog_workStatusAdd.setIndeterminate(true);

        assigned_user = findViewById(R.id.txt_assing_user);

        //get UserTypeID
        get_userTypeID = Paper.book().read("user_type_id");

        //GetByUserType
        list_assignedUSer = new ArrayList<String>();
        list_assignedUSer.add("Kişiyi seçiniz");

        getByUserTypeWithProject = new GetByUserTypeWithProject(Integer.parseInt(get_userTypeID),get_proje_id);
        getByUserTypeWithProject.execute((Void) null);

        workStatusListTask = new WorkStatusList(workOrder_id);
        workStatusListTask.execute((Void) null);

    }

    public class GetByUserTypeWithProject extends AsyncTask<Void, Void, Boolean> {

        private final Integer userType_id;
        private final Integer project_id;
        private String get_mesaj_dataUserType = "";

        GetByUserTypeWithProject(Integer userType_id,Integer project_id){

            this.userType_id = userType_id;
            this.project_id = project_id;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog_todoListUpdate.setMessage("\tLoading...");
            progressDialog_todoListUpdate.setCancelable(false);
            progressDialog_todoListUpdate.show();
            progressDialog_todoListUpdate.setContentView(R.layout.custom_progress);
        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            try {
                String getByUserType_service = Server.GetByUserTypeWithProjectAuth(userType_id,project_id);
                if(!getByUserType_service.trim().equalsIgnoreCase("false")){

                    response_dataUserType = new Gson().fromJson(getByUserType_service,DataUserType.class);
                    dataUSerType = response_dataUserType.getData_list();
                    Log.i("Tag:dataUserType",""+dataUSerType);
                    get_mesaj_dataUserType = "true";

                }else{
                    get_mesaj_dataUserType = "false";
                }

            } catch (Exception e) {

            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            if(!get_mesaj_dataUserType.equals("false")) {
                progressDialog_todoListUpdate.dismiss();
                if (dataUSerType.size() > 0) {

                    for(int i=0;i<dataUSerType.size();i++){

                        list_assignedUSer.add(dataUSerType.get(i).getName());
                    }

                    userTypeWithAction();
                }
            }else{

                progressDialog_todoListUpdate.dismiss();
            }

        }
    }

    private void userTypeWithAction(){

        ArrayAdapter<String> dataAdapter_userType = new ArrayAdapter<String>(c,
                android.R.layout.simple_spinner_item, list_assignedUSer) {

            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {

                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }

        };

        dataAdapter_userType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        assigned_user.setAdapter(dataAdapter_userType);
        assigned_user.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint
                if (position > 0) {

                    get_assigned_user_id = dataUSerType.get(position-1).getID();


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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

                        ModelWorkStatus modelWorkStatus = new ModelWorkStatus(dataWorkStatus.get(i).getAssignedName(),dataWorkStatus.get(i).getAssignedName());
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

    public class WorkStatusAdd extends AsyncTask<Void, Void, Boolean> {

        private final Integer assigned_id;
        private String get_mesaj_workStatusAdd = "";
        String get_mesaj = "";

        WorkStatusAdd(Integer assigned_id){

            this.assigned_id = assigned_id;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog_workStatusAdd.setMessage("\tLoading...");
            progressDialog_workStatusAdd.setCancelable(false);
            progressDialog_workStatusAdd.show();
            progressDialog_workStatusAdd.setContentView(R.layout.custom_progress);
        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            try {
                String getWorkStatusAdd_service = Server.WorkStatusAdd(assigned_id);
                if(!getWorkStatusAdd_service.trim().equalsIgnoreCase("false")){

                    JSONObject jsonObject = new JSONObject(getWorkStatusAdd_service);
                    get_mesaj = jsonObject.getString("Successful");

                }else{
                    get_mesaj = "false";
                }

            } catch (Exception e) {

            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            if(!get_mesaj_workStatusAdd.equals("false")) {
                progressDialog_workStatusAdd.dismiss();

                Toast.makeText(c,"İşlem Başarılı",Toast.LENGTH_LONG).show();

            }else{

                progressDialog_workStatusAdd.dismiss();
                Toast.makeText(c,"Gönderme Başarısız",Toast.LENGTH_LONG).show();
            }

        }
    }
}
