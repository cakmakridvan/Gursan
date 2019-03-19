package com.rotamobile.gursan.ui.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.rotamobile.gursan.Main;
import com.rotamobile.gursan.R;
import com.rotamobile.gursan.data.Server;
import com.rotamobile.gursan.model.areaSpinner.DataArea;
import com.rotamobile.gursan.model.areaSpinner.ModelArea;
import com.rotamobile.gursan.model.buildingSpinner.DataBuilding;
import com.rotamobile.gursan.model.deviceSpinner.DataDevice;
import com.rotamobile.gursan.model.deviceSpinner.ModelDevice;
import com.rotamobile.gursan.model.projectSpinner.DataProject;
import com.rotamobile.gursan.model.territorySpinner.DataTerritory;
import com.rotamobile.gursan.model.buildingSpinner.ModelBuilding;
import com.rotamobile.gursan.model.projectSpinner.ModelProject;
import com.rotamobile.gursan.model.territorySpinner.ModelTerritory;
import com.rotamobile.gursan.model.userSpinner.DataUser;
import com.rotamobile.gursan.model.userSpinner.ModelUser;
import com.rotamobile.gursan.ui.bottom_navigation.MainBottomNavigation;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;
import io.paperdb.Paper;

import static com.rotamobile.gursan.data.Server.GetArea;
import static com.rotamobile.gursan.data.Server.GetBuilding;
import static com.rotamobile.gursan.data.Server.GetDevice;
import static com.rotamobile.gursan.data.Server.GetProjects;
import static com.rotamobile.gursan.data.Server.GetTerritory;
import static com.rotamobile.gursan.data.Server.GetUserList;
import static com.rotamobile.gursan.data.Server.GetUsers;
import static java.lang.Integer.parseInt;


public class JobOrder extends Fragment implements View.OnClickListener {


    private Spinner spin_proje, spin_bolge, spin_alan, spin_bina, spin_cihaz, spin_isemriTipi, spin_isemriTalebi, spin_kisiler;
    private String get_userID;
    private EditText aciklama;
    View view;

    private RadioGroup radioGroup;
    private RadioButton radioButton;

    private ProjectsTask projectsTask = null;
    private TerritoryTask territoryTask = null;
    private BuildingTask buildingTask = null;
    private AreaTask areaTask = null;
    private DeviceTask deviceTask = null;
    private UserListTask userListTask = null;
    private TodoAdd todoAdd = null;

    private ProgressDialog progressDialog,progressDialog_todo;
    private List<String> list_proje;
    private List<String> list_bolge;
    private List<String> list_bina;
    private List<String> list_alan;
    private List<String> list_cihaz;
    private List<String> list_kisiler;
    private Integer projectID = 0;

    //Territory
    private DataTerritory response;
    private ArrayList<ModelTerritory> territoryList;
    private String get_mesaj = "";
    private Integer territoryID = 0;

    //Building
    private DataBuilding response_building;
    private ArrayList<ModelBuilding> buildingList;
    private String get_mesaj_building = "";
    private Integer buildingID = 0;

    //Area
    private DataArea response_area;
    private ArrayList<ModelArea> areaList;
    private String get_mesaj_area = "";
    private Integer areaID = 0;

    //Device
    private DataDevice response_device;
    private ArrayList<ModelDevice> deviceList;
    private String get_mesaj_device = "";
    private Integer deviceID = 0;

    //WorkOrderType
    private Integer workOrderType = 0;

    //WorkCategory model
    private Integer workCategoryModel = 0;

    //WorkImportance
    private Integer workImportance = 0;

    //UserList
    private DataUser response_userList;
    private ArrayList<ModelUser> userList;
    private String get_mesaj_userList = "";
    private Integer userID = 0;

    //Açıklama
    private String get_aciklama = "";

    //Button of Sending Job Order
    private Button send_jobOrder;
    private String get_mesaj_todoAdd = "";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.job_order, container, false);

        initializer();

      //Project Service Running
        projectsTask = new ProjectsTask(get_userID);
        projectsTask.execute((Void) null);

        return view;
    }

    private void initializer() {

        //Progress Diaolog initialize
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressDialog.setIndeterminate(true);

        progressDialog_todo = new ProgressDialog(getActivity());
        progressDialog_todo.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressDialog_todo.setIndeterminate(true);

        //Paper init
        Paper.init(getActivity());
        //get UserID from Login
        get_userID = Paper.book().read("user_id");

        //RadioGroup init
        radioGroup = (RadioGroup) view.findViewById(R.id.radiogroup);

        //Proje Spinner
        spin_proje = view.findViewById(R.id.spinner_proje);
        list_proje = new ArrayList<String>();


        //Bölge
        spin_bolge = view.findViewById(R.id.spinner_bolge);
        list_bolge = new ArrayList<String>();
        list_bolge.add("Bölge Seçiniz");
        spin_bolge.setEnabled(false);
        territorySpinnerAction();

        //Bina
        spin_bina = view.findViewById(R.id.spinner_bina);
        list_bina = new ArrayList<String>();
        list_bina.add("Bina Seçiniz");
        spin_bina.setEnabled(false);
        buildingSpinnerAction();



        //Alan
        spin_alan = view.findViewById(R.id.spinner_alan);
        list_alan = new ArrayList<String>();
        list_alan.add("Alan Seçiniz");
        spin_alan.setEnabled(false);
        areaSpinnerAction();



        //Cihaz
        spin_cihaz = view.findViewById(R.id.spinner_cihaz);
        list_cihaz = new ArrayList<String>();
        list_cihaz.add("Cihaz Seçiniz");
        spin_cihaz.setEnabled(false);
        deviceSpinnerAction();


        //İş Emri Tipi
        spin_isemriTipi = view.findViewById(R.id.spinner_isemritipi);
        List<String> list_isEmriTipi = new ArrayList<String>();
        list_isEmriTipi.add("İş Emri Tipini Seçiniz");
        list_isEmriTipi.add("Talep");
        list_isEmriTipi.add("Arıza");
        ArrayAdapter<String> dataAdapter_isEmriTipi = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, list_isEmriTipi) {

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
        dataAdapter_isEmriTipi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_isemriTipi.setAdapter(dataAdapter_isEmriTipi);
        spin_isemriTipi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint
                if (position > 0) {
                    // Notify the selected item text

                    if(position == 1){
                        //Talep
                        workOrderType = 10;
                    }
                    else if(position == 2){
                        //Arıza
                        workOrderType = 11;
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //İş Emri Modeli
        spin_isemriTalebi = view.findViewById(R.id.spinner_isemritalebi);
        List<String> list_isEmriTalebi = new ArrayList<String>();
        list_isEmriTalebi.add("İş Modelini Seçiniz");
        list_isEmriTalebi.add("Elektirik");
        list_isEmriTalebi.add("Mekanik");
        list_isEmriTalebi.add("İnşaat");
        ArrayAdapter<String> dataAdapter_isEmriTalebi = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, list_isEmriTalebi) {

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
        dataAdapter_isEmriTalebi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_isemriTalebi.setAdapter(dataAdapter_isEmriTalebi);
        spin_isemriTalebi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint
                if (position > 0) {
                    // Notify the selected item text

                    if(position == 1){
                        //Elektirik
                        workCategoryModel = 6;
                    }
                    else if(position == 2){
                        //Mekanik
                        workCategoryModel = 5;
                    }
                    if(position == 3){
                        //İnşaat
                        workCategoryModel = 7;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //Kişiler
        spin_kisiler = view.findViewById(R.id.spinner_kisiler);
        list_kisiler = new ArrayList<String>();
        list_kisiler.add("Kişi Seçiniz");
        userListSpinnerAction();

        //Button
        send_jobOrder = view.findViewById(R.id.btn_jobOrder);
        send_jobOrder.setOnClickListener(this);

        //Açıklama
        aciklama = view.findViewById(R.id.edt_aciklama);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btn_jobOrder:

                int selectedId = radioGroup.getCheckedRadioButtonId();
                radioButton = (RadioButton) view.findViewById(selectedId);

                String get_Selected_Rdaio = radioButton.getText().toString();

                if(get_Selected_Rdaio.equals("Esnek")){

                    workImportance = 27;

                }else if(get_Selected_Rdaio.equals("Normal")){

                    workImportance = 28;

                }else if(get_Selected_Rdaio.equals("Acil")){

                    workImportance = 29;

                }

               String get_Project = spin_proje.getSelectedItem().toString();
               String get_Bolge = spin_bolge.getSelectedItem().toString();
               String get_Bina = spin_bina.getSelectedItem().toString();
               String get_Alan = spin_alan.getSelectedItem().toString();
               String get_Cihaz = spin_cihaz.getSelectedItem().toString();
               String get_isEmriTipi = spin_isemriTipi.getSelectedItem().toString();
               String get_isModeli = spin_isemriTalebi.getSelectedItem().toString();
               String get_kisiler = spin_kisiler.getSelectedItem().toString();
               get_aciklama = aciklama.getText().toString();
               Integer getUserID = Integer.parseInt(get_userID);


               if(get_Project.equals("Proje Seçiniz")){
                   showToasty("Proje Seçiniz");
               }else if(get_Bolge.equals("Bölge Seçiniz")){
                   showToasty("Bölge Seçiniz");
               }else if(get_Bina.equals("Bina Seçiniz")){
                   showToasty("Bina Seçiniz");
               }else if(get_Alan.equals("Alan Seçiniz")){
                   showToasty("Alan Seçiniz");
               }else if(get_Cihaz.equals("Cihaz Seçiniz")){
                   showToasty("Cihaz Seçiniz");
               }else if(get_isEmriTipi.equals("İş Emri Tipini Seçiniz")){
                   showToasty("İş Emri Tipini Seçiniz");
               }else if(get_isModeli.equals("İş Modelini Seçiniz")){
                   showToasty("İş Modelini Seçiniz");
               }else if(get_kisiler.equals("Kişileri Seçiniz")){
                   showToasty("Kişileri Seçiniz");
               }else {

                   //TodoAdd Service Running
                   todoAdd = new TodoAdd(getUserID,projectID,territoryID,buildingID,areaID,deviceID,workOrderType,workImportance,workCategoryModel,userID,get_aciklama);
                   todoAdd.execute((Void) null);

               }

               break;

        }
    }



    private void showToasty(String mesaj) {

        Toasty.info(getActivity(), "Lütfen " + mesaj, Toast.LENGTH_SHORT, true).show();
    }


    public class ProjectsTask extends AsyncTask<Void, Void, Boolean> {

        private final String id;
        private DataProject response;
        private ArrayList<ModelProject> projectList;
        private String get_mesaj = "";

        ProjectsTask(String id) {

          this.id = id;
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
                String projects_resullt = GetProjects(id);
                if (!projects_resullt.trim().equalsIgnoreCase("false")) {

                    response = new Gson().fromJson(projects_resullt, DataProject.class);
                    projectList = response.getData_list();
                    Log.i("", "" + projectList.size());


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

            if(!get_mesaj.equals("false")) {

                progressDialog.dismiss();
                list_proje.add("Proje Seçiniz");

              //UserList Service Running
                userListTask = new UserListTask();
                userListTask.execute((Void) null);

                if (projectList.size() > 0) {
                    for (int i = 0; i < projectList.size(); i++) {

                        //Getting Project Name
                        list_proje.add(projectList.get(i).getName());
                    }
                }
              projeSpinnerAction();
            }else {

                progressDialog.dismiss();
                list_proje.add("Proje Seçiniz");
                projeSpinnerAction();
            }

        }

        private void projeSpinnerAction() {

            ArrayAdapter<String> dataAdapter_proje = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_spinner_item, list_proje) {

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
            dataAdapter_proje.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spin_proje.setAdapter(dataAdapter_proje);

            spin_proje.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String selectedItemText = (String) parent.getItemAtPosition(position);
                    if (position > 0) {

                        //Getting Project ID
                        projectID = projectList.get(position-1).getID();
                        Log.i("Tag:ProjectID:",""+projectID);

                      //Territory Service Running
                        territoryTask= new TerritoryTask(projectID);
                        territoryTask.execute((Void) null);



                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }

    public class TerritoryTask extends AsyncTask<Void, Void, Boolean>{

        private Integer project_id;


        TerritoryTask(Integer project_id){

            this.project_id = project_id;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                String territory_result = GetTerritory(projectID);
                if(!territory_result.trim().equalsIgnoreCase("false")){

                    response = new Gson().fromJson(territory_result, DataTerritory.class);
                    territoryList = response.getData_list();
                    Log.i("Tag:territoryList",""+territoryList);
                    get_mesaj = "true";


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

            if(!get_mesaj.equals("false")){
              //Clear list_Bölge
                list_bolge.clear();
                list_bolge.add("Bölge Seçiniz");
                spin_bolge.setEnabled(false);
              //Clear list Bina
                list_bina.clear();
                list_bina.add("Bina Seçiniz");
                spin_bina.setEnabled(false);
                buildingSpinnerAction();
              //Clear List Alan
                list_alan.clear();
                list_alan.add("Alan Seçiniz");
                spin_alan.setEnabled(false);
                areaSpinnerAction();
              //Clear List Cihaz
                list_cihaz.clear();
                list_cihaz.add("Cihaz Seçiniz");
                spin_cihaz.setEnabled(false);
                deviceSpinnerAction();

                if (territoryList.size() > 0) {
                    for (int i = 0; i < territoryList.size(); i++) {

                        //Getting Project Name

                        spin_bolge.setEnabled(true);
                        list_bolge.add(territoryList.get(i).getName());


                    }
                }
                territorySpinnerAction();
            }else{
                //Clear list_Bölge
                list_bolge.clear();
                list_bolge.add("Bölge Seçiniz");
                spin_bolge.setEnabled(false);
                territorySpinnerAction();
                //Clear list Bina
                list_bina.clear();
                list_bina.add("Bina Seçiniz");
                spin_bina.setEnabled(false);
                buildingSpinnerAction();
                //Clear List Alan
                list_alan.clear();
                list_alan.add("Alan Seçiniz");
                spin_alan.setEnabled(false);
                areaSpinnerAction();
                //Clear List Cihaz
                list_cihaz.clear();
                list_cihaz.add("Cihaz Seçiniz");
                spin_cihaz.setEnabled(false);
                deviceSpinnerAction();
            }
        }


    }

    private void territorySpinnerAction() {

        ArrayAdapter<String> dataAdapter_bolge = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, list_bolge) {

            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {

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
        dataAdapter_bolge.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_bolge.setAdapter(dataAdapter_bolge);

        spin_bolge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);

                if (position > 0) {

                    //Getting TerritoryID
                    territoryID = territoryList.get(position-1).getID();
                    Log.i("Tag:ProjectID:",""+projectID);

                    //Building Service Running
                    buildingTask = new BuildingTask(territoryID);
                    buildingTask.execute((Void) null);


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public class BuildingTask extends AsyncTask<Void, Void, Boolean>{

        private Integer territory_id;


        BuildingTask(Integer territory_id){

            this.territory_id = territory_id;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                String building_result = GetBuilding(territoryID);
                if(!building_result.trim().equalsIgnoreCase("false")){

                    response_building = new Gson().fromJson(building_result, DataBuilding.class);
                    buildingList = response_building.getData_list();
                    Log.i("Tag:buildingList",""+buildingList);
                    get_mesaj_building = "true";


                }else{
                    get_mesaj_building = "false";
                }

            } catch (Exception e) {

            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            if(!get_mesaj_building.equals("false")){
              //Clear Bina
                list_bina.clear();
                list_bina.add("Bina Seçiniz");
                spin_bina.setEnabled(false);
              //Clear Alan
                list_alan.clear();
                list_alan.add("Alan Seçiniz");
                spin_alan.setEnabled(false);
                areaSpinnerAction();
                //Clear List Cihaz
                list_cihaz.clear();
                list_cihaz.add("Cihaz Seçiniz");
                spin_cihaz.setEnabled(false);
                deviceSpinnerAction();

                if (buildingList.size() > 0) {
                    for (int i = 0; i < buildingList.size(); i++) {

                        //Getting Project Name
                        spin_bina.setEnabled(true);
                        list_bina.add(buildingList.get(i).getName());


                    }
                }
                buildingSpinnerAction();
            }else{
              //Clear Bina
                list_bina.clear();
                list_bina.add("Bina Seçiniz");
                spin_bina.setEnabled(false);
                buildingSpinnerAction();
              //Clear Alan
                list_alan.clear();
                list_alan.add("Alan Seçiniz");
                spin_alan.setEnabled(false);
                areaSpinnerAction();
                //Clear List Cihaz
                list_cihaz.clear();
                list_cihaz.add("Cihaz Seçiniz");
                spin_cihaz.setEnabled(false);
                deviceSpinnerAction();
            }
        }


    }

    private void buildingSpinnerAction() {


        ArrayAdapter<String> dataAdapter_bina = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, list_bina) {

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
        dataAdapter_bina.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_bina.setAdapter(dataAdapter_bina);

        spin_bina.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint
                if (position > 0) {

                    //Getting BuildingID to get data from Area
                    buildingID = buildingList.get(position-1).getID();
                    Log.i("Tag:ProjectID:",""+buildingID);

                    //Area Service Running
                    areaTask = new AreaTask(buildingID);
                    areaTask.execute((Void) null);


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public class AreaTask extends AsyncTask<Void, Void, Boolean>{

        private Integer building_id;

        AreaTask(Integer building_id){

            this.building_id = building_id;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                String area_result = GetArea(building_id);
                if(!area_result.trim().equalsIgnoreCase("false")){

                    response_area = new Gson().fromJson(area_result, DataArea.class);
                    areaList = response_area.getData_list();
                    Log.i("Tag:buildingList",""+areaList);
                    get_mesaj_area = "true";


                }else{
                    get_mesaj_area = "false";
                }

            } catch (Exception e) {

            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            if(!get_mesaj_area.equals("false")){
              //Clear List Alan
                list_alan.clear();
                list_alan.add("Alan Seçiniz");
                spin_alan.setEnabled(false);
                //Clear List Cihaz
                list_cihaz.clear();
                list_cihaz.add("Cihaz Seçiniz");
                spin_cihaz.setEnabled(false);
                deviceSpinnerAction();
                if (areaList.size() > 0) {
                    for (int i = 0; i < areaList.size(); i++) {

                        //Getting Project Name
                        spin_alan.setEnabled(true);
                        list_alan.add(areaList.get(i).getName());


                    }
                }
                areaSpinnerAction();
            }else{
              //Clear List Alan
                list_alan.clear();
                list_alan.add("Alan Seçiniz");
                spin_alan.setEnabled(false);
                areaSpinnerAction();
              //Clear List Cihaz
                list_cihaz.clear();
                list_cihaz.add("Cihaz Seçiniz");
                spin_cihaz.setEnabled(false);
                deviceSpinnerAction();
            }
        }


    }

    private void areaSpinnerAction() {

        ArrayAdapter<String> dataAdapter_alan = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, list_alan) {

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
        dataAdapter_alan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_alan.setAdapter(dataAdapter_alan);
        spin_alan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint
                if (position > 0) {

                    //Getting AreaID to get data from Device
                    areaID = areaList.get(position-1).getID();
                    Log.i("Tag:AreaID:",""+areaID);

                    //Area Service Running
                    deviceTask = new DeviceTask(areaID);
                    deviceTask.execute((Void) null);


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public class DeviceTask extends AsyncTask<Void, Void, Boolean>{

        private Integer area_id;

        DeviceTask(Integer area_id){

            this.area_id = area_id;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                String device_result = GetDevice(area_id);
                if(!device_result.trim().equalsIgnoreCase("false")){

                    response_device = new Gson().fromJson(device_result, DataDevice.class);
                    deviceList = response_device.getData_list();
                    Log.i("Tag:deviceList",""+deviceList);
                    get_mesaj_device = "true";


                }else{
                    get_mesaj_device = "false";
                }

            } catch (Exception e) {

            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            if(!get_mesaj_device.equals("false")){
                list_cihaz.clear();
                list_cihaz.add("Cihaz Seçiniz");
                spin_cihaz.setEnabled(false);
                deviceSpinnerAction();
                if (deviceList.size() > 0) {
                    for (int i = 0; i < deviceList.size(); i++) {

                        //Getting Device Name
                        spin_cihaz.setEnabled(true);
                        list_cihaz.add(deviceList.get(i).getName());

                    }
                }
                deviceSpinnerAction();
            }else{
                list_cihaz.clear();
                list_cihaz.add("Cihaz Seçiniz");
                spin_cihaz.setEnabled(false);
                deviceSpinnerAction();
            }
        }


    }

    private void deviceSpinnerAction() {

        ArrayAdapter<String> dataAdapter_cihaz = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, list_cihaz) {

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
        dataAdapter_cihaz.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_cihaz.setAdapter(dataAdapter_cihaz);
        spin_cihaz.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint
                if (position > 0) {
                    // Notify the selected item text

                    //Getting DeviceID to get data from Device
                    deviceID = deviceList.get(position-1).getID();
                    Log.i("Tag:DeviceID:",""+deviceID);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public class UserListTask extends AsyncTask<Void, Void, Boolean>{


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                String userList_result = GetUserList();
                if(!userList_result.trim().equalsIgnoreCase("false")){

                    response_userList = new Gson().fromJson(userList_result, DataUser.class);
                    userList = response_userList.getData_list();
                    Log.i("Tag:userList",""+userList);
                    get_mesaj_userList = "true";


                }else{
                    get_mesaj_userList = "false";
                }

            } catch (Exception e) {

            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            if(!get_mesaj_userList.equals("false")){
                list_kisiler.clear();
                list_kisiler.add("Kişileri Seçiniz");
                if (userList.size() > 0) {
                    for (int i = 0; i < userList.size(); i++) {

                        //Getting Users' Name

                        list_kisiler.add(userList.get(i).getName());

                    }
                }
                userListSpinnerAction();
            }else{
                list_kisiler.clear();
                list_kisiler.add("Kişileri Seçiniz");
                userListSpinnerAction();
            }
        }


    }

    private void userListSpinnerAction() {

        ArrayAdapter<String> dataAdapter_kisiler = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, list_kisiler) {

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
        dataAdapter_kisiler.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_kisiler.setAdapter(dataAdapter_kisiler);
        spin_kisiler.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint
                if (position > 0) {

                    //Getting userID to get data from UserGet
                    userID = userList.get(position-1).getID();
                    Log.i("Tag:UserID:",""+userID);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public class TodoAdd extends AsyncTask<Void, Void, Boolean>{

        private final Integer id;
        private final Integer proje_id;
        private final Integer territory_id;
        private final Integer building_id;
        private final Integer area_id;
        private final Integer device_id;
        private final Integer workOrderType_id;
        private final Integer workImportance_id;
        private final Integer workOrderCategory_id;
        private final Integer user_id;
        private final String descrption;

        TodoAdd(Integer id,Integer proje_id,Integer territory_id,Integer building_id,Integer area_id,
                Integer device_id,Integer workOrderType_id,Integer workImportance_id,Integer workOrderCategory_id,Integer user_id,
                String descrption){

            this.id = id;
            this.proje_id = proje_id;
            this.territory_id = territory_id;
            this.building_id = building_id;
            this.area_id = area_id;
            this.device_id = device_id;
            this.workOrderType_id = workOrderType_id;
            this.workImportance_id = workImportance_id;
            this.workOrderCategory_id = workOrderCategory_id;
            this.user_id = user_id;
            this.descrption = descrption;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog_todo.setMessage("\tLoading...");
            progressDialog_todo.setCancelable(false);
            progressDialog_todo.show();
            progressDialog_todo.setContentView(R.layout.custom_progress);
        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            String todoAdd_service = Server.TodoAdd(id,proje_id,territory_id,building_id,area_id,device_id,
                    workOrderType_id,workImportance_id,workOrderCategory_id,user_id,descrption);
            if(!todoAdd_service.trim().equalsIgnoreCase("false")){

                try {

                    JSONObject jObject = new JSONObject(todoAdd_service);
                    get_mesaj_todoAdd = jObject.getString("Successful");
                    String get_mesaj_data = jObject.getString("Data");

                    Log.i("msjTodoAdd",get_mesaj_todoAdd);
                    Log.i("msjData",get_mesaj_data);

                } catch (JSONException e) {
                    Log.i("Exception: ",e.getMessage());

                }
            }

            else{
                get_mesaj_todoAdd = "false";
            }

            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            if(get_mesaj_todoAdd.equals("true")) {

                progressDialog_todo.dismiss();

                new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("İşlem Mesajı")
                        .setContentText("İşlem Başarılı,İş Emri Oluşturulmuştur")
                        .setConfirmText("Tamam")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismissWithAnimation();

                                Intent go_home = new Intent(getActivity(),MainBottomNavigation.class);
                                startActivity(go_home);
                            }
                        })
                        .show();

            }
            else{

                progressDialog_todo.dismiss();

                Toasty.error(getActivity(), "İşlem Başarısız,tekrar deneyiniz", Toast.LENGTH_SHORT, true).show();

            }

        }
    }


}