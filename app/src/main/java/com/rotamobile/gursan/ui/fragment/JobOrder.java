package com.rotamobile.gursan.ui.fragment;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.rotamobile.gursan.R;
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

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

import static com.rotamobile.gursan.data.Server.GetArea;
import static com.rotamobile.gursan.data.Server.GetBuilding;
import static com.rotamobile.gursan.data.Server.GetDevice;
import static com.rotamobile.gursan.data.Server.GetProjects;
import static com.rotamobile.gursan.data.Server.GetTerritory;


public class JobOrder extends Fragment {


    private Spinner spin_proje, spin_bolge, spin_alan, spin_bina, spin_cihaz, spin_isemriTipi, spin_isemriTalebi, spin_kisiler;
    private String get_userID;
    View view;

    private ProjectsTask projectsTask = null;
    private TerritoryTask territoryTask = null;
    private BuildingTask buildingTask = null;
    private AreaTask areaTask = null;
    private DeviceTask deviceTask = null;

    private ProgressDialog progressDialog;
    private List<String> list_proje;
    private List<String> list_bolge;
    private List<String> list_bina;
    private List<String> list_alan;
    private List<String> list_cihaz;
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

        //Paper init
        Paper.init(getActivity());
        //get UserID from Login
        get_userID = Paper.book().read("user_id");

        //Proje Spinner
        spin_proje = view.findViewById(R.id.spinner_proje);
        list_proje = new ArrayList<String>();


        //Bölge
        spin_bolge = view.findViewById(R.id.spinner_bolge);
        list_bolge = new ArrayList<String>();
        list_bolge.add("Bölge Seçiniz");
        territorySpinnerAction();

        //Bina
        spin_bina = view.findViewById(R.id.spinner_bina);
        list_bina = new ArrayList<String>();
        list_bina.add("Bina Seçiniz");
        buildingSpinnerAction();



        //Alan
        spin_alan = view.findViewById(R.id.spinner_alan);
        list_alan = new ArrayList<String>();
        list_alan.add("Alan Seçiniz");
        areaSpinnerAction();



        //Cihaz
        spin_cihaz = view.findViewById(R.id.spinner_cihaz);
        list_cihaz = new ArrayList<String>();
        list_cihaz.add("Cihaz Seçiniz");
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
                    Toast.makeText
                            (getActivity(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
                            .show();
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
                    Toast.makeText
                            (getActivity(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //Kişiler
        spin_kisiler = view.findViewById(R.id.spinner_kisiler);
        List<String> list_kisiler = new ArrayList<String>();
        list_kisiler.add("Kişi Seçiniz");
        list_kisiler.add("Yavuz");
        list_kisiler.add("Emre");
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
                    // Notify the selected item text
                    Toast.makeText
                            (getActivity(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


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
                        projectID = projectList.get(position-1).getProjectId();
                        Log.i("Tag:ProjectID:",""+projectID);

                      //Territory Service Running
                        territoryTask= new TerritoryTask(projectID);
                        territoryTask.execute((Void) null);


                        Toast.makeText
                                (getActivity(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
                                .show();
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
              //Clear list Bina
                list_bina.clear();
                list_bina.add("Bina Seçiniz");
                buildingSpinnerAction();
              //Clear List Alan
                list_alan.clear();
                list_alan.add("Alan Seçiniz");
                areaSpinnerAction();
              //Clear List Cihaz
                list_cihaz.clear();
                list_cihaz.add("Cihaz Seçiniz");
                deviceSpinnerAction();

                if (territoryList.size() > 0) {
                    for (int i = 0; i < territoryList.size(); i++) {

                        //Getting Project Name

                        list_bolge.add(territoryList.get(i).getName());


                    }
                }
                territorySpinnerAction();
            }else{
                //Clear list_Bölge
                list_bolge.clear();
                list_bolge.add("Bölge Seçiniz");
                territorySpinnerAction();
                //Clear list Bina
                list_bina.clear();
                list_bina.add("Bina Seçiniz");
                buildingSpinnerAction();
                //Clear List Alan
                list_alan.clear();
                list_alan.add("Alan Seçiniz");
                areaSpinnerAction();
                //Clear List Cihaz
                list_cihaz.clear();
                list_cihaz.add("Cihaz Seçiniz");
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
                    territoryID = territoryList.get(position-1).getTerritoryId();
                    Log.i("Tag:ProjectID:",""+projectID);

                    //Building Service Running
                    buildingTask = new BuildingTask(territoryID);
                    buildingTask.execute((Void) null);

                    // Notify the selected item text
                    Toast.makeText
                            (getActivity(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
                            .show();
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
              //Clear Alan
                list_alan.clear();
                list_alan.add("Alan Seçiniz");
                areaSpinnerAction();
                //Clear List Cihaz
                list_cihaz.clear();
                list_cihaz.add("Cihaz Seçiniz");
                deviceSpinnerAction();

                if (buildingList.size() > 0) {
                    for (int i = 0; i < buildingList.size(); i++) {

                        //Getting Project Name

                        list_bina.add(buildingList.get(i).getName());


                    }
                }
                buildingSpinnerAction();
            }else{
              //Clear Bina
                list_bina.clear();
                list_bina.add("Bina Seçiniz");
                buildingSpinnerAction();
              //Clear Alan
                list_alan.clear();
                list_alan.add("Alan Seçiniz");
                areaSpinnerAction();
                //Clear List Cihaz
                list_cihaz.clear();
                list_cihaz.add("Cihaz Seçiniz");
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
                    buildingID = buildingList.get(position-1).getBuildingId();
                    Log.i("Tag:ProjectID:",""+buildingID);

                    //Area Service Running
                    areaTask = new AreaTask(buildingID);
                    areaTask.execute((Void) null);

                    // Notify the selected item text
                    Toast.makeText
                            (getActivity(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
                            .show();
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
                //Clear List Cihaz
                list_cihaz.clear();
                list_cihaz.add("Cihaz Seçiniz");
                deviceSpinnerAction();
                if (areaList.size() > 0) {
                    for (int i = 0; i < areaList.size(); i++) {

                        //Getting Project Name

                        list_alan.add(areaList.get(i).getName());


                    }
                }
                areaSpinnerAction();
            }else{
              //Clear List Alan
                list_alan.clear();
                list_alan.add("Alan Seçiniz");
                areaSpinnerAction();
              //Clear List Cihaz
                list_cihaz.clear();
                list_cihaz.add("Cihaz Seçiniz");
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
                    areaID = areaList.get(position-1).getAreaId();
                    Log.i("Tag:AreaID:",""+areaID);

                    //Area Service Running
                    deviceTask = new DeviceTask(areaID);
                    deviceTask.execute((Void) null);

                    // Notify the selected item text
                    Toast.makeText
                            (getActivity(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
                            .show();
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
                if (deviceList.size() > 0) {
                    for (int i = 0; i < deviceList.size(); i++) {

                        //Getting Device Name

                        list_cihaz.add(deviceList.get(i).getName());

                    }
                }
                deviceSpinnerAction();
            }else{
                list_cihaz.clear();
                list_cihaz.add("Cihaz Seçiniz");
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
                    Toast.makeText
                            (getActivity(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}