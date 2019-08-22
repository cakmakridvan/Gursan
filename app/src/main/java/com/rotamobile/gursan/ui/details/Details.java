package com.rotamobile.gursan.ui.details;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.androidbuts.multispinnerfilter.KeyPairBoolData;
import com.androidbuts.multispinnerfilter.MultiSpinnerSearch;
import com.androidbuts.multispinnerfilter.SpinnerListener;
import com.crashlytics.android.Crashlytics;
import com.google.gson.Gson;
import com.rotamobile.gursan.Main;
import com.rotamobile.gursan.R;
import com.rotamobile.gursan.data.Server;
import com.rotamobile.gursan.model.areaSpinner.DataArea;
import com.rotamobile.gursan.model.areaSpinner.ModelArea;
import com.rotamobile.gursan.model.buildingSpinner.DataBuilding;
import com.rotamobile.gursan.model.buildingSpinner.ModelBuilding;
import com.rotamobile.gursan.model.definedJobSpinner.DataDefinedJob;
import com.rotamobile.gursan.model.definedJobSpinner.ModelDefinedJob;
import com.rotamobile.gursan.model.deviceSpinner.DataDevice;
import com.rotamobile.gursan.model.deviceSpinner.ModelDevice;
import com.rotamobile.gursan.model.projectSpinner.DataProject;
import com.rotamobile.gursan.model.projectSpinner.ModelProject;
import com.rotamobile.gursan.model.subjectsSpinner.DataSubject;
import com.rotamobile.gursan.model.subjectsSpinner.ModelSubject;
import com.rotamobile.gursan.model.territorySpinner.DataTerritory;
import com.rotamobile.gursan.model.territorySpinner.ModelTerritory;
import com.rotamobile.gursan.model.userTypeWithProject.DataUserType;
import com.rotamobile.gursan.model.userTypeWithProject.ModelUserType;
import com.rotamobile.gursan.ui.bottom_navigation.MainBottomNavigation;
import com.rotamobile.gursan.ui.dialog_customize.CustomDialogAssigns;
import com.rotamobile.gursan.ui.dialog_customize.CustomDialogClass;
import com.rotamobile.gursan.ui.documents.DetailDocument;
import com.rotamobile.gursan.utils.enums.Enums;


import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;
import io.fabric.sdk.android.Fabric;
import io.paperdb.Paper;

import static com.rotamobile.gursan.data.Server.GetArea;
import static com.rotamobile.gursan.data.Server.GetBuilding;
import static com.rotamobile.gursan.data.Server.GetDefinedList;
import static com.rotamobile.gursan.data.Server.GetDevice;
import static com.rotamobile.gursan.data.Server.GetProjects;
import static com.rotamobile.gursan.data.Server.GetSubjects;
import static com.rotamobile.gursan.data.Server.GetTerritory;
import static com.rotamobile.gursan.data.Server.PostDefinedList;

public class Details extends AppCompatActivity {

    private Spinner project_name,territory_name,building_name,area_name,device_name,subject_name,servis_name,assigned_user,workOrdertype_name;
    private String get_project_name,get_territory_name,get_building_name,get_area_name,get_device_name,get_subject_name,get_start_date,get_end_date,get_kullanici_adi,get_descriptionUpdate;
    private Integer get_proje_id,get_territory_id,get_building_id,get_area_id,get_device_id,get_subject_id,get_insert_user_id,get_id,get_assigned_user_id,get_work_id,get_status,get_MoveTyoe_id,get_workOrderService_id;
    private Integer get_workCategory_id,get_workOrderType_id,get_workImportance_id;
    private Boolean get_authorizaUpdate;
    private TextView detail_user,detail_proje,detail_teritory,detail_building,detail_area,detail_device,detail_subject,detail_isEmriTipi,detail_aciklama,update_user;
    private EditText aciklama;

    Bundle extras;
    private ImageButton back_imagebutton;
    private List<String> list_proje,list_territory,list_building,list_area,list_device,list_subject,list_service,list_definedJob,list_assignedUSer,list_workOrderTypeID;
    private ProgressDialog progressDialog,progressDialog_todoListUpdate;
    private String get_userID;

    private ProjectsTask projectsTask = null;
    private TerritoryTask territoryTask = null;
    private BuildingTask buildingTask = null;
    private AreaTask areaTask = null;
    private DeviceTask deviceTask = null;
    private SubjectTask subjectTask = null;
    private TodoListUpdate todoListUpdateTask = null;
    private DefinedJobTask definedJobTask = null;
    private PostDefinedJobTask postDefinedJobTask = null;


    ArrayAdapter<String> dataAdapter_proje;
    ArrayAdapter<String> dataAdapter_territory;
    ArrayAdapter<String> dataAdapter_building;
    ArrayAdapter<String> dataAdapter_area;
    ArrayAdapter<String> dataAdapter_device;
    ArrayAdapter<String> dataAdapter_subject;
    ArrayAdapter<String> dataAdapter_service;
    ArrayAdapter<String> dataAdapter_workOrderTypeSpinner;

    //Proje
    private Integer projectID = 0;

    //Bölge
    private DataTerritory response_territory;
    private ArrayList<ModelTerritory> territoryList;
    private String get_mesaj_territory = "";
    private Integer territoryID = 0;

    //Bina
    private DataBuilding response_building;
    private ArrayList<ModelBuilding> buildingList;
    private String get_mesaj_building = "";
    private Integer buildingID = 0;

    //Area
    private DataArea response_area;
    private ArrayList<ModelArea> areaList;
    private String get_mesaj_area = "";
    private Integer areaID = 0;

    //Cihaz
    private DataDevice response_device;
    private ArrayList<ModelDevice> deviceList;
    private String get_mesaj_device = "";
    private Integer deviceID = 0;

    //Subject
    private DataSubject response_subject;
    private ArrayList<ModelSubject> subjectList;
    private String get_mesaj_subject = "";
    private Integer subjectID = 0;

    //DefinedJob
    private DataDefinedJob response_definedJob;
    private ArrayList<ModelDefinedJob> definedJobList;
    private String get_mesaj_definedJob = "";


    NestedScrollView lyt_update,lyt_detail;
    CollapsingToolbarLayout collapsingToolbarLayout;
    private Integer get_LoginID = 0;

    Button update;
    private String get_mesaj_todoListUpdate ="";

    private Integer workOrderServiceID = 0;

    private LinearLayout tanimli_lyt,girilen_lyt;
    List<KeyPairBoolData> listArray0;
    private EditText edt_girilen_is;
    private int[] get_selected_id;
    private String get_mesaj_postDefined = "";
    private String get_userTypeID = "";
    private DataUserType response_dataUserType;
    private ArrayList<ModelUserType> dataUSerType;
    private String get_mesaj_dataUserType = "";
    private Integer get_assgnedID = 0;
    private TextView is_ata,is_atamalari_gor,txt_servis_Tipi;
    private LinearLayout lyt_servis_Tipi;
    private ImageButton imglist,detail_imglist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(Details.this, new Crashlytics());
        setContentView(R.layout.details);

     //Paper initialize
        Paper.init(Details.this);

        //get UserID from Login
        get_userID = Paper.book().read("user_id");
        get_LoginID = Integer.parseInt(get_userID);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar);

        //get Data From All Message
        extras = getIntent().getExtras();
        get_id = extras.getInt("id");// WorkOrder ID
        get_project_name = extras.getString("proje_name");
        get_territory_name = extras.getString("teritory_name");
        get_building_name = extras.getString("building_name");
        get_area_name = extras.getString("area_name");
        get_subject_name = extras.getString("subject_name");
        get_device_name = extras.getString("device_name");
        get_kullanici_adi = extras.getString("work_user");
        get_start_date = extras.getString("start_date");
        get_end_date = extras.getString("end_date");
        get_proje_id = extras.getInt("proje_id");
        get_territory_id = extras.getInt("teritory_id");
        get_building_id = extras.getInt("building_id");
        get_area_id = extras.getInt("area_id");
        get_device_id = extras.getInt("device_id");
        get_subject_id = extras.getInt("subject_id");
        get_insert_user_id = extras.getInt("insert_user_id");    //Who create work order ID
        get_assigned_user_id = extras.getInt("assigned_user_id");//Selected User who doing work Order
        get_authorizaUpdate = extras.getBoolean("auhorizate_update");
        get_descriptionUpdate = extras.getString("description_update");
        get_work_id = extras.getInt("work_id");
        get_status = extras.getInt("status");//Get MoveType ID
        get_MoveTyoe_id = extras.getInt("moveType");
        get_workCategory_id = extras.getInt("workOrderCategory_id");
        get_workOrderType_id = extras.getInt("workOrderType_id");
        get_workImportance_id = extras.getInt("WorkImportance_id");
        get_workOrderService_id = extras.getInt("WorkOrderService_id");

        Log.i("get_proje_id",""+get_proje_id);
        Log.i("get_authorizaUpdate",""+get_authorizaUpdate);
        Log.i("get_description",""+get_descriptionUpdate);

      //İş Atama Action
        is_ata = findViewById(R.id.is_atama);
        is_ata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialogClass cdd = new CustomDialogClass(Details.this,get_proje_id,get_id,Integer.parseInt(get_userID));
                //cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                cdd.show();
                Window window = cdd.getWindow();
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            }
        });

      //İş Atamaları Gör Action
        is_atamalari_gor = findViewById(R.id.visible_is_atama);
        is_atamalari_gor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CustomDialogAssigns cdd = new CustomDialogAssigns(Details.this,get_proje_id,get_id,get_insert_user_id);
                //cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                cdd.show();
                Window window = cdd.getWindow();
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
        });


     //Assign operation
        projectID = get_proje_id;
        territoryID = get_territory_id;
        buildingID = get_building_id;
        areaID = get_area_id;
        deviceID = get_device_id;
        subjectID = get_subject_id;
    //Layouts Define
        lyt_update = findViewById(R.id.layout_update);
        lyt_detail = findViewById(R.id.layout_detail);
        //tanimli_lyt = findViewById(R.id.lyt_tanimli_isler);
        girilen_lyt = findViewById(R.id.lyt_girilen_isler);

        //BackButton Action
        back_imagebutton = findViewById(R.id.back_button);
        back_imagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });


     //Check workOrder is Detail or Uptade
        if(get_authorizaUpdate && !(get_MoveTyoe_id.equals(Enums.msj_kapali))){

            lyt_update.setVisibility(View.VISIBLE);
            is_ata.setVisibility(View.VISIBLE);
            initialize_update();
        }else{

            lyt_detail.setVisibility(View.VISIBLE);
            is_atamalari_gor.setVisibility(View.VISIBLE);
            initialize_detail();
        }


    }


    private void initialize_update() {

        //Progress Diaolog initialize
        progressDialog_todoListUpdate = new ProgressDialog(Details.this);
        progressDialog_todoListUpdate.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressDialog_todoListUpdate.setIndeterminate(true);

        collapsingToolbarLayout.setTitle("Güncelleme");
       //Spinners name
        project_name = findViewById(R.id.txt_name_project);
        territory_name = findViewById(R.id.txt_name_territory);
        building_name = findViewById(R.id.txt_name_building);
        area_name = findViewById(R.id.txt_name_area);
        device_name = findViewById(R.id.txt_name_device_name);
        subject_name = findViewById(R.id.txt_name_subject);
        servis_name = findViewById(R.id.txt_name_servis_tipi);
        update_user = findViewById(R.id.update_userName);
        assigned_user = findViewById(R.id.txt_assing_user);
        workOrdertype_name = findViewById(R.id.txt_workOrderType_id);
        lyt_servis_Tipi = findViewById(R.id.lyt_servisTipi);
        txt_servis_Tipi = findViewById(R.id.txt_servisTipi);
        imglist = findViewById(R.id.update_imagelist);

        imglist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent go_detailDocument = new Intent(Details.this,DetailDocument.class);
                go_detailDocument.putExtra("workOrder_id",get_id); //Send WorkOrderId
                startActivity(go_detailDocument);

            }
        });

        //Visible Servis Tipi
        txt_servis_Tipi.setVisibility(View.VISIBLE);
        lyt_servis_Tipi.setVisibility(View.VISIBLE);

        //set UserName
        update_user.setText(get_kullanici_adi);

        Log.i("Log:Proje:DropdownView","Clicked");

        //Progress Diaolog initialize
        progressDialog = new ProgressDialog(Details.this);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressDialog.setIndeterminate(true);

        //Project Service Running
        projectsTask = new ProjectsTask(get_userID);
        projectsTask.execute((Void) null);

        //Project Service Running
        subjectTask = new SubjectTask();
        subjectTask.execute((Void) null);

        //get UserTypeID
        get_userTypeID = Paper.book().read("user_type_id");

        //GetByUserType
        list_assignedUSer = new ArrayList<String>();
        list_assignedUSer.add("Kişiyi seçiniz");

        //Update Button Event Listener
        update = findViewById(R.id.btn_jobUpdate);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                  String get_bolge = territory_name.getSelectedItem().toString();
                  String get_bina = building_name.getSelectedItem().toString();
                  String get_area = area_name.getSelectedItem().toString();
                  String get_device = device_name.getSelectedItem().toString();
                  String get_aciklama = aciklama.getText().toString();

                  if(get_aciklama == null && get_aciklama.isEmpty()){

                      get_aciklama = "";
                  }
                  if(get_assgnedID != 0){


                  }

                if(get_bolge.equals("Bölge Seçiniz")){
                    showToasty("Bölge Seçiniz");
                }else if(get_bina.equals("Bina Seçiniz")){
                    showToasty("Bina Seçiniz");
                }else if(get_area.equals("Alan Seçiniz")){
                    showToasty("Alan Seçiniz");
                }else if(get_device.equals("Cihaz Seçiniz")){
                    showToasty("Cihaz Seçiniz");
                }else{

                    todoListUpdateTask = new TodoListUpdate(get_id,projectID,territoryID,buildingID,areaID,deviceID,subjectID,get_assigned_user_id,get_LoginID,workOrderServiceID,get_aciklama,get_status,
                            get_workCategory_id,get_workOrderType_id,get_workImportance_id);
                    todoListUpdateTask.execute((Void)null);
                }
            }
        });


        //Proje Spinner
        if(get_project_name != null && !get_project_name.isEmpty()) {
            list_proje = new ArrayList<String>();
            list_proje.add(get_project_name);
            dataAdapter_proje = new ArrayAdapter<String>(Details.this,
                    R.layout.detail_spinner_text_color, list_proje){

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
            dataAdapter_proje.setDropDownViewResource(R.layout.detail_spinner_text_clicked);
            project_name.setAdapter(dataAdapter_proje);
        }

        //Territory Spinner
        if(get_territory_name != null && !get_territory_name.isEmpty()) {
            list_territory = new ArrayList<String>();
            list_territory.add(get_territory_name);
            territory_name.setEnabled(false);
            //territorySpinnerAction();
            dataAdapter_territory = new ArrayAdapter<String>(Details.this,
                    R.layout.detail_spinner_text_color, list_territory);
            dataAdapter_territory.setDropDownViewResource(R.layout.detail_spinner_text_clicked);
            territory_name.setAdapter(dataAdapter_territory);
        }
        //Building Spinner
        if(get_building_name != null && !get_building_name.isEmpty()) {
            list_building = new ArrayList<String>();
            list_building.add(get_building_name);
            building_name.setEnabled(false);
            //buildingSpinnerAction();
            dataAdapter_building = new ArrayAdapter<String>(Details.this,
                    R.layout.detail_spinner_text_color, list_building);
            dataAdapter_building.setDropDownViewResource(R.layout.detail_spinner_text_clicked);
            building_name.setAdapter(dataAdapter_building);

        }
        //Area Spinner
        if(get_area_name != null && !get_area_name.isEmpty()) {
            list_area = new ArrayList<String>();
            list_area.add(get_area_name);
            area_name.setEnabled(false);
            //areaSpinnerAction();
            dataAdapter_area = new ArrayAdapter<String>(Details.this,
                    R.layout.detail_spinner_text_color, list_area);
            dataAdapter_area.setDropDownViewResource(R.layout.detail_spinner_text_clicked);
            area_name.setAdapter(dataAdapter_area);
        }
        //Device Spinner
        if(get_device_name != null && !get_device_name.isEmpty()) {
            list_device = new ArrayList<String>();
            list_device.add(get_device_name);
            //deviceSpinnerAction();
            device_name.setEnabled(false);
            dataAdapter_device = new ArrayAdapter<String>(Details.this,
                    R.layout.detail_spinner_text_color, list_device);
            dataAdapter_device.setDropDownViewResource(R.layout.detail_spinner_text_clicked);
            device_name.setAdapter(dataAdapter_device);
        }else if(get_device_name == null || get_device_name.isEmpty()) {
            list_device = new ArrayList<String>();
            get_device_name = "Cihaz Boş";
            list_device.add(get_device_name);
            //deviceSpinnerAction();
            device_name.setEnabled(false);
            dataAdapter_device = new ArrayAdapter<String>(Details.this,
                    R.layout.detail_spinner_text_color, list_device);
            dataAdapter_device.setDropDownViewResource(R.layout.detail_spinner_text_clicked);
            device_name.setAdapter(dataAdapter_device);
        }

        //Subject Spinner
        if(get_subject_name != null && !get_subject_name.isEmpty()) {
            list_subject = new ArrayList<String>();
            list_subject.add(get_subject_name);
            //subjectSpinnerAction();
            dataAdapter_subject = new ArrayAdapter<String>(Details.this,
                    R.layout.detail_spinner_text_color, list_subject);
            dataAdapter_subject.setDropDownViewResource(R.layout.detail_spinner_text_clicked);
            subject_name.setAdapter(dataAdapter_subject);
        }

        //Aciklama
        aciklama = findViewById(R.id.edt_update_aciklama);
        if(get_descriptionUpdate != null && !get_descriptionUpdate.isEmpty()){

            aciklama.setText(get_descriptionUpdate);

        }

        //Service Tip Spinner

        if(get_workOrderService_id.equals(Enums.ic_Servis)){
            list_service = new ArrayList<String>();
            list_service.add("İç Servis");
            list_service.add("İç Servis");
            list_service.add("Dış Servis");
            serviceTipSpinner();

            //İç Servis Selected
            workOrderServiceID = Enums.ic_Servis;
/*            definedJobTask = new DefinedJobTask();
            definedJobTask.execute((Void) null);
            tanimli_lyt.setVisibility(View.VISIBLE);*/

        }else if(get_workOrderService_id.equals(Enums.dis_Servis)){
            list_service = new ArrayList<String>();
            list_service.add("Dış Servis");
            list_service.add("İç Servis");
            list_service.add("Dış Servis");
            serviceTipSpinner();

            //Dış Servis Selected
            workOrderServiceID = Enums.dis_Servis;
            //tanimli_lyt.setVisibility(View.GONE);

            if(girilen_lyt.getVisibility() == View.VISIBLE){

                girilen_lyt.setVisibility(View.GONE);
            }

        }else{
            list_service = new ArrayList<String>();
            list_service.add("Servis Tipini Seçiniz");
            list_service.add("İç Servis");
            list_service.add("Dış Servis");
            serviceTipSpinner();
        }

        //WorkOrderTypeId Spinner
        if(get_workOrderType_id.equals(Enums.talep)){
            list_workOrderTypeID = new ArrayList<String>();
            list_workOrderTypeID.add("Talep");
            list_workOrderTypeID.add("Talep");
            list_workOrderTypeID.add("Arıza");
            orderTypeSpinner();
        }else if(get_workOrderType_id.equals(Enums.ariza)){
            list_workOrderTypeID = new ArrayList<String>();
            list_workOrderTypeID.add("Arıza");
            list_workOrderTypeID.add("Talep");
            list_workOrderTypeID.add("Arıza");
            orderTypeSpinner();
        }else if(get_workOrderType_id.equals(Enums.periyodik)){
            list_workOrderTypeID = new ArrayList<String>();
            list_workOrderTypeID.add("Periyodik");
            //orderTypeSpinner();
            dataAdapter_workOrderTypeSpinner = new ArrayAdapter<String>(Details.this,
                    R.layout.detail_spinner_text_color, list_workOrderTypeID);
            dataAdapter_workOrderTypeSpinner.setDropDownViewResource(R.layout.detail_spinner_text_clicked);
            workOrdertype_name.setAdapter(dataAdapter_workOrderTypeSpinner);

            txt_servis_Tipi.setVisibility(View.GONE);
            lyt_servis_Tipi.setVisibility(View.GONE);
        }


        /**
         * Search MultiSelection Spinner (With Search/Filter Functionality)
         *
         *  Using MultiSpinnerSearch class
         */

        /**
         * Getting array of String to Bind in Spinner
         */
        //final List<String> list = Arrays.asList(getResources().getStringArray(R.array.sports_array));


/*        for (int i = 0; i < list.size(); i++) {
            KeyPairBoolData h = new KeyPairBoolData();
            h.setId(i + 1);
            h.setName(list.get(i));
            h.setSelected(false);
            listArray0.add(h);
        }*/

    }

    private void initialize_detail(){

        collapsingToolbarLayout.setTitle("Detay");

        detail_user = findViewById(R.id.txt_userName);
        detail_proje = findViewById(R.id.txt_projeName);
        detail_teritory = findViewById(R.id.txt_teritoryName);
        detail_building = findViewById(R.id.txt_buildingName);
        detail_area = findViewById(R.id.txt_areaName);
        detail_device = findViewById(R.id.txt_deviceName);
        detail_subject = findViewById(R.id.txt_subjectName);
        detail_isEmriTipi = findViewById(R.id.txt_workOrder_type_id);
        detail_aciklama = findViewById(R.id.txt_aciklama);
        detail_imglist = findViewById(R.id.detail_imagelist);

        detail_imglist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent go_detailDocument = new Intent(Details.this,DetailDocument.class);
                go_detailDocument.putExtra("workOrder_id",get_id); //Send WorkOrderId
                startActivity(go_detailDocument);

            }
        });

        detail_user.setText(get_kullanici_adi);
      //Get First 25 characters from String
        String upToNCharacters = get_project_name.substring(0, Math.min(get_project_name.length(), 25));
        detail_proje.setText(upToNCharacters + "-");

        detail_teritory.setText(get_territory_name);
        detail_building.setText(get_building_name);
        detail_area.setText(get_area_name);

        if(get_workOrderType_id.equals(Enums.talep)){
            detail_isEmriTipi.setText("Talep");
        }else if(get_workOrderType_id.equals(Enums.ariza)){
            detail_isEmriTipi.setText("Arıza");
        }else if(get_workOrderType_id.equals(Enums.periyodik)){
            detail_isEmriTipi.setText("Periyodik");
        }

        if(get_device_name != null && !get_device_name.isEmpty()) {
            detail_device.setText(get_device_name);
        }else{
            detail_device.setText("....");
        }

        detail_subject.setText(get_subject_name);
        detail_aciklama.setText(get_descriptionUpdate);
    }

    private void showToasty(String mesaj) {

        Toasty.info(Details.this, "Lütfen " + mesaj, Toast.LENGTH_SHORT, true).show();
    }

    public class ProjectsTask extends AsyncTask<Void, Void, Boolean> {

        private final String id;
        private DataProject response;
        private List<ModelProject> projectList;
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

            projectList = new ArrayList<ModelProject>();


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
                //Bölge set clear
                Log.i("Size_territory",""+list_territory.size());
                list_territory.clear();
                list_territory.add(get_territory_name);
                territory_name.setEnabled(false);
                territorySpinnerAction();

                //Bina set clear
                Log.i("Size_building",""+list_building.size());
                list_building.clear();
                list_building.add(get_building_name);
                building_name.setEnabled(false);
                buildingSpinnerAction();

                //Alan set clear
                Log.i("Size_area",""+list_area.size());
                list_area.clear();
                list_area.add(get_area_name);
                area_name.setEnabled(false);
                areaSpinnerAction();

                //Device set clear
                Log.i("Size_device",""+list_device.size());
                list_device.clear();
                list_device.add(get_device_name);
                device_name.setEnabled(false);
                deviceSpinnerAction();

                progressDialog.dismiss();

                if (projectList.size() > 0) {
                    list_proje.clear();
                    list_proje.add(get_project_name);
                    for (int i = 0; i < projectList.size(); i++) {

                        //Getting Project Name
                        list_proje.add(projectList.get(i).getName());
                    }
                }else{
                    list_proje.clear();
                    list_proje.add("Proje Bulunamadı");
                }
                projeSpinnerAction();
            }else {

                progressDialog.dismiss();
                projeSpinnerAction();
            }

        }

        private void projeSpinnerAction() {

            dataAdapter_proje.setDropDownViewResource(R.layout.detail_spinner_text_clicked);
            project_name.setAdapter(dataAdapter_proje);
            dataAdapter_proje.notifyDataSetChanged();

            project_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String selectedItemText = (String) parent.getItemAtPosition(position);
                    if (position > 0) {
                        //Getting Project ID
                        projectID = projectList.get(position-1).getID();
                        Log.i("Tag:ProjectID:", "" + projectID);

                        territoryTask = new TerritoryTask(projectID);
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

            progressDialog.setMessage("\tLoading...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            progressDialog.setContentView(R.layout.custom_progress);
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                String territory_result = GetTerritory(project_id);
                if(!territory_result.trim().equalsIgnoreCase("false")){

                    response_territory = new Gson().fromJson(territory_result, DataTerritory.class);
                    territoryList = response_territory.getData_list();
                    Log.i("Tag:territoryList",""+territoryList);
                    get_mesaj_territory = "true";


                }else{
                    get_mesaj_territory = "false";
                }

            } catch (Exception e) {

            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            if(!get_mesaj_territory.equals("false")){
                progressDialog.dismiss();

                if (territoryList.size() > 0) {
                    list_territory.clear();
                    list_territory.add("Bölge Seçiniz");
                    //Bina set clear
                    list_building.clear();
                    list_building.add("Bina Seçiniz");
                    buildingSpinnerAction();
                    //Area set clear
                    list_area.clear();
                    list_area.add("Alan Seçiniz");
                    areaSpinnerAction();
                    //Device set clear
                    list_device.clear();
                    list_device.add("Cihaz Seçiniz");
                    deviceSpinnerAction();
                    for (int i = 0; i < territoryList.size(); i++) {

                        //Getting Project Name
                        list_territory.add(territoryList.get(i).getName());
                        territory_name.setEnabled(true);

                    }
                }else{
                    list_territory.clear();
                    list_territory.add("Bölge Bulunamadı");

                }
                territorySpinnerAction();
            }else{
                progressDialog.dismiss();
                territorySpinnerAction();

            }
        }


    }

    private void territorySpinnerAction() {

        dataAdapter_territory = new ArrayAdapter<String>(Details.this,
                R.layout.detail_spinner_text_color, list_territory){

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
        dataAdapter_territory.setDropDownViewResource(R.layout.detail_spinner_text_clicked);
        territory_name.setAdapter(dataAdapter_territory);

        territory_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                if(position>0) {
                    //Getting Teritory ID
                    territoryID = territoryList.get(position-1).getID();
                    Log.i("Tag:TerritoryID:", "" + territoryID);

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

            progressDialog.setMessage("\tLoading...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            progressDialog.setContentView(R.layout.custom_progress);
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                String building_result = GetBuilding(territory_id);
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
                progressDialog.dismiss();

                if (buildingList.size() > 0) {
                    list_building.clear();
                    list_building.add("Bina Seçiniz");

                    //Area set clear
                    list_area.clear();
                    list_area.add("Alan Seçiniz");
                    areaSpinnerAction();
                    //Device set clear
                    list_device.clear();
                    list_device.add("Cihaz Seçiniz");
                    deviceSpinnerAction();
                    for (int i = 0; i < buildingList.size(); i++) {

                        //Getting Building Name
                        list_building.add(buildingList.get(i).getName());
                        building_name.setEnabled(true);

                    }
                }else{
                    list_building.clear();
                    list_building.add("Bina Bulunamadı");

                }
                buildingSpinnerAction();
            }else{
                progressDialog.dismiss();
                buildingSpinnerAction();

            }
        }


    }

    private void buildingSpinnerAction(){

        dataAdapter_building = new ArrayAdapter<String>(Details.this,
                R.layout.detail_spinner_text_color, list_building){

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
        dataAdapter_building.setDropDownViewResource(R.layout.detail_spinner_text_clicked);
        building_name.setAdapter(dataAdapter_building);

        building_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                if(position>0) {
                    //Getting Building ID
                    buildingID = buildingList.get(position-1).getID();
                    Log.i("Tag:BuildingID:", "" + buildingID);

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
            progressDialog.setMessage("\tLoading...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            progressDialog.setContentView(R.layout.custom_progress);
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
                progressDialog.dismiss();

                if (areaList.size() > 0) {
                    list_area.clear();
                    list_area.add("Alan Seçiniz");
                    //Device set clear
                    list_device.clear();
                    list_device.add("Cihaz Seçiniz");
                    deviceSpinnerAction();
                    for (int i = 0; i < areaList.size(); i++) {

                        //Getting Area Name
                        list_area.add(areaList.get(i).getName());
                        area_name.setEnabled(true);

                    }
                }else{
                    list_area.clear();
                    list_area.add("Alan Bulunamadı");

                }
                areaSpinnerAction();
            }else{
                progressDialog.dismiss();
                areaSpinnerAction();

            }
        }


    }

    private void areaSpinnerAction(){

        dataAdapter_area = new ArrayAdapter<String>(Details.this,
                R.layout.detail_spinner_text_color, list_area){

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
        dataAdapter_area.setDropDownViewResource(R.layout.detail_spinner_text_clicked);
        area_name.setAdapter(dataAdapter_area);

        area_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                if(position>0) {
                    //Getting Teritory ID
                    areaID = areaList.get(position-1).getID();
                    Log.i("Tag:AreaID:", "" + areaID);

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
            progressDialog.setMessage("\tLoading...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            progressDialog.setContentView(R.layout.custom_progress);
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
                progressDialog.dismiss();

                if (deviceList.size() > 0) {
                    list_device.clear();
                    list_device.add("Cihaz Seçiniz");
                    for (int i = 0; i < deviceList.size(); i++) {

                        //Getting Device Name
                        list_device.add(deviceList.get(i).getName());
                        device_name.setEnabled(true);

                    }
                }else{
                    list_device.clear();
                    list_device.add("Cihaz Bulunamadı");

                }
                deviceSpinnerAction();
            }else{
                progressDialog.dismiss();
                deviceSpinnerAction();

            }
        }


    }

    private void deviceSpinnerAction(){

        dataAdapter_device = new ArrayAdapter<String>(Details.this,
                R.layout.detail_spinner_text_color, list_device){

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
        dataAdapter_device.setDropDownViewResource(R.layout.detail_spinner_text_clicked);
        device_name.setAdapter(dataAdapter_device);

        device_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                if(position>0) {
                    //Getting Teritory ID
                    deviceID = deviceList.get(position-1).getID();
                    Log.i("Tag:AreaID:", "" + deviceID);

/*                    DeviceTask = new DeviceTask(areaID);
                    deviceTask.execute((Void) null);*/
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public class SubjectTask extends AsyncTask<Void, Void, Boolean>{


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                String subjects_result = GetSubjects();
                if(!subjects_result.trim().equalsIgnoreCase("false")){

                    response_subject = new Gson().fromJson(subjects_result, DataSubject.class);
                    subjectList = response_subject.getData_list();
                    Log.i("Tag:subjectList",""+subjectList);
                    get_mesaj_subject = "true";


                }else{
                    get_mesaj_subject = "false";
                }

            } catch (Exception e) {

            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            if(!get_mesaj_subject.equals("false")){
                //progressDialog.dismiss();

                if (subjectList.size() > 0) {
/*                    list_device.clear();
                    list_device.add("Alan Seçiniz");*/
                    for (int i = 0; i < subjectList.size(); i++) {

                        //Getting Device Name
                        list_subject.add(subjectList.get(i).getSubjectText());

                    }
                }else{
                    list_device.clear();
                    list_device.add(get_subject_name);

                }
                subjectSpinnerAction();
            }else{
                subjectSpinnerAction();

            }
        }


    }

    private void subjectSpinnerAction(){

        dataAdapter_subject = new ArrayAdapter<String>(Details.this,
                R.layout.detail_spinner_text_color, list_subject){

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
        dataAdapter_subject.setDropDownViewResource(R.layout.detail_spinner_text_clicked);
        subject_name.setAdapter(dataAdapter_subject);

        subject_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                if(position>0) {
                    //Getting Teritory ID
                    subjectID = subjectList.get(position-1).getID();
                    Log.i("Tag:SubjectID:", "" + subjectID);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public class TodoListUpdate extends AsyncTask<Void, Void, Boolean>{

        private final Integer id;
        private final Integer proje_id;
        private final Integer territory_id;
        private final Integer building_id;
        private final Integer area_id;
        private final Integer device_id;
        private final Integer workTopic_id;
        private final Integer assigned_user_id;
        private final Integer insert_update_id;
        private final Integer workOrderService_id;
        private final String description;
        private final Integer moveType_id;
        private final Integer WorkOrderCategoryID;
        private final Integer WorkOrderTypeID;
        private final Integer WorkImportanceID;

        TodoListUpdate(Integer id,Integer proje_id,Integer territory_id,Integer building_id,Integer area_id,
                Integer device_id,Integer workTopic_id,Integer assigned_user_id,Integer insert_update_id,Integer work_Order_Service_id,String description, Integer moveType_id,
                Integer WorkOrderCategoryID, Integer WorkOrderTypeID, Integer WorkImportanceID){

            this.id = id;
            this.proje_id = proje_id;
            this.territory_id = territory_id;
            this.building_id = building_id;
            this.area_id = area_id;
            this.device_id = device_id;
            this.workTopic_id = workTopic_id;
            this.assigned_user_id = assigned_user_id;
            this.insert_update_id = insert_update_id;
            this.workOrderService_id = work_Order_Service_id;
            this.description = description;
            this.moveType_id = moveType_id;
            this.WorkOrderCategoryID = WorkOrderCategoryID;
            this.WorkOrderTypeID = WorkOrderTypeID;
            this.WorkImportanceID = WorkImportanceID;
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

            String todoListUpdate_service = Server.TodoListUpdate(id,proje_id,territory_id,building_id,area_id,device_id,
                    workTopic_id,assigned_user_id,insert_update_id,workOrderService_id,description,moveType_id,WorkOrderCategoryID,WorkOrderTypeID,WorkImportanceID);
            if(!todoListUpdate_service.trim().equalsIgnoreCase("false")){

                try {

                    JSONObject jObject = new JSONObject(todoListUpdate_service);
                    get_mesaj_todoListUpdate = jObject.getString("Successful");
                    String get_mesaj_data = jObject.getString("Data");

                    Log.i("msjTodoListUpdate",get_mesaj_todoListUpdate);
                    Log.i("msjData",get_mesaj_data);

                } catch (JSONException e) {
                    Log.i("Exception: ",e.getMessage());

                }
            }

            else{
                get_mesaj_todoListUpdate = "false";
            }

            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            if(get_mesaj_todoListUpdate.equals("true")) {

                //Send PstDefinedServices
                postDefinedJobTask = new PostDefinedJobTask();
                postDefinedJobTask.execute((Void) null);

                progressDialog_todoListUpdate.dismiss();

                new SweetAlertDialog(Details.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("İşlem Mesajı")
                        .setContentText("İşlem Başarılı,Güncelleme Yapılmıştır")
                        .setConfirmText("Tamam")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismissWithAnimation();

                                Intent go_home = new Intent(Details.this,MainBottomNavigation.class);
                                startActivity(go_home);
                            }
                        })
                        .show();

            }
            else{

                progressDialog_todoListUpdate.dismiss();

                Toasty.error(Details.this, "İşlem Başarısız,tekrar deneyiniz", Toast.LENGTH_SHORT, true).show();

            }

        }
    }

    private void serviceTipSpinner(){

        dataAdapter_service = new ArrayAdapter<String>(Details.this,
                R.layout.detail_spinner_text_color, list_service) {

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
        dataAdapter_service.setDropDownViewResource(R.layout.detail_spinner_text_clicked);
        servis_name.setAdapter(dataAdapter_service);
        servis_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint
                if (position > 0) {

                    if(position == 1){
                    //İç Servis Selected
                        workOrderServiceID = Enums.ic_Servis;
/*                        definedJobTask = new DefinedJobTask();
                        definedJobTask.execute((Void) null);
                        tanimli_lyt.setVisibility(View.VISIBLE);*/

                    }else if(position == 2){
                    //Dış Servis Selected
                        workOrderServiceID = Enums.dis_Servis;
                        //tanimli_lyt.setVisibility(View.GONE);

                        if(girilen_lyt.getVisibility() == View.VISIBLE){

                            girilen_lyt.setVisibility(View.GONE);
                        }
                    }


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void orderTypeSpinner(){

        dataAdapter_workOrderTypeSpinner = new ArrayAdapter<String>(Details.this,
                R.layout.detail_spinner_text_color, list_workOrderTypeID) {

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
        dataAdapter_workOrderTypeSpinner.setDropDownViewResource(R.layout.detail_spinner_text_clicked);
        workOrdertype_name.setAdapter(dataAdapter_workOrderTypeSpinner);
        workOrdertype_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint
                if (position > 0) {

                    if(position == 1){
                        //Talep Selected
                        get_workOrderType_id = Enums.talep;
                    }else if(position == 2){
                        //Arıza Selected
                        get_workOrderType_id = Enums.ariza;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public class DefinedJobTask extends AsyncTask<Void, Void, Boolean>{

        KeyPairBoolData h;

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
                String definedJob_result = GetDefinedList(get_work_id);
                if(!definedJob_result.trim().equalsIgnoreCase("false")){

                    response_definedJob = new Gson().fromJson(definedJob_result, DataDefinedJob.class);
                    definedJobList = response_definedJob.getData_list();
                    Log.i("Tag:definedJobList",""+definedJobList);
                    get_mesaj_definedJob = "true";


                }else{
                    get_mesaj_definedJob = "false";
                }

            } catch (Exception e) {

            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);


            edt_girilen_is = findViewById(R.id.edt_girilen_isler);
            edt_girilen_is.setText("");

            if(!get_mesaj_definedJob.equals("false")){
                progressDialog.dismiss();

                if (definedJobList.size() > 0) {

                    list_definedJob = new ArrayList<String>();
                    listArray0 = new ArrayList<>();

/*                  list_device.clear();
                    list_device.add("Cihaz Seçiniz");*/
                    for (int i = 0; i < definedJobList.size(); i++) {
                     //Getting Device Name
                        list_definedJob.add(definedJobList.get(i).getName());

                        h = new KeyPairBoolData();
                        h.setId(definedJobList.get(i).getID());
                        h.setName(definedJobList.get(i).getName());
                     //if definedJobList.get(i).getSelected() false write it
                        h.setSelected(false);

                     if(definedJobList.get(i).getSelected() == true){

                         if(girilen_lyt.getVisibility() != View.VISIBLE){

                             girilen_lyt.setVisibility(View.VISIBLE);
                         }

                         edt_girilen_is.append(definedJobList.get(i).getName());
                         edt_girilen_is.append(",");
                     }

                        listArray0.add(h);
                    }

                    final MultiSpinnerSearch searchMultiSpinnerUnlimited = (MultiSpinnerSearch) findViewById(R.id.searchMultiSpinnerUnlimited);

                    searchMultiSpinnerUnlimited.setItems(listArray0, -1, new SpinnerListener() {

                        @Override
                        public void onItemsSelected(List<KeyPairBoolData> items) {
                            get_selected_id = new int[20];
                            int sayac = 0;

                            for (int i = 0; i < items.size(); i++) {
                                    Log.i("TAG_DefinedJob", i + " : " + items.get(i).getName() + items.get(i).getId() + " : " + items.get(i).isSelected());
                                    if(items.get(i).isSelected() == true) {

                                        get_selected_id [sayac] = (int) items.get(i).getId();
                                        //get_selected_id.add((int) items.get(i).getId());
                                        //get_selected_id.length;
                                        sayac++;
                                    }
                            }

                        }
                    });



                }else{
                    //list_device.clear();
                    //list_device.add("Cihaz Bulunamadı");

                }
                //deviceSpinnerAction();
            }else{
                progressDialog.dismiss();
                //deviceSpinnerAction();

            }
        }

    }

    public class PostDefinedJobTask extends AsyncTask<Void, Void, Boolean>{

/*        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("\tLoading...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            progressDialog.setContentView(R.layout.custom_progress);
        }*/


        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                String postdefinedJob_result = PostDefinedList(get_work_id,get_LoginID,get_selected_id);
                if(!postdefinedJob_result.trim().equalsIgnoreCase("false")){

                    try {

                        JSONObject jObject = new JSONObject(postdefinedJob_result);
                        get_mesaj_postDefined = jObject.getString("Successful");
                        //String get_mesaj_data = jObject.getString("Data");

                        Log.i("msjTodoAdd",get_mesaj_postDefined);
                        //Log.i("msjData",get_mesaj_data);

                    } catch (JSONException e) {
                        Log.i("Exception: ",e.getMessage());

                    }


                }else{
                    get_mesaj_postDefined = "false";
                }

            } catch (Exception e) {

            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            if(!get_mesaj_postDefined.equals("false")){
                //progressDialog.dismiss();


            }else{
                //progressDialog.dismiss();
                //deviceSpinnerAction();

            }
        }

    }






}
