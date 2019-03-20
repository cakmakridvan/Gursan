package com.rotamobile.gursan.ui.details;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.rotamobile.gursan.R;

import java.util.ArrayList;
import java.util.List;

public class Details extends AppCompatActivity {

    private Spinner project_name,territory_name,building_name,area_name,device_name,subject_name,start_date,end_date,kullanici_adi;
    private String get_project_name,get_territory_name,get_building_name,get_area_name,get_device_name,get_subject_name,get_start_date,get_end_date,get_kullanici_adi;
    Bundle extras;
    private ImageButton back_imagebutton;
    private List<String> list_proje,list_territory,list_building,list_area,list_device,list_subject,list_start_date,list_end_date,list_kullanici_adi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);

        initialize();
    }

    private void initialize() {

        project_name = findViewById(R.id.txt_name_project);
        territory_name = findViewById(R.id.txt_name_territory);
        building_name = findViewById(R.id.txt_name_building);
        area_name = findViewById(R.id.txt_name_area);
        device_name = findViewById(R.id.txt_name_device_name);
        subject_name = findViewById(R.id.txt_name_subject);
        start_date = findViewById(R.id.txt_start_date);
        end_date = findViewById(R.id.txt_send_date);
        kullanici_adi = findViewById(R.id.txt_kullanici_adi);

        //get Data From All Mesage
        extras = getIntent().getExtras();
        get_project_name = extras.getString("proje_name");
        get_territory_name = extras.getString("teritory_name");
        get_building_name = extras.getString("building_name");
        get_area_name = extras.getString("area_name");
        get_subject_name = extras.getString("subject_name");
        get_device_name = extras.getString("device_name");
        get_kullanici_adi = extras.getString("work_user");
        get_start_date = extras.getString("start_date");
        get_end_date = extras.getString("end_date");


        //Proje Spinner
        list_proje = new ArrayList<String>();
        list_proje.add(get_project_name);
        ArrayAdapter<String> dataAdapter_proje = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.detail_spinner_text_color, list_proje);
        dataAdapter_proje.setDropDownViewResource(R.layout.detail_spinner_text_clicked);
        project_name.setAdapter(dataAdapter_proje);

        //Territory Spinner
        list_territory = new ArrayList<String>();
        list_territory.add(get_territory_name);
        ArrayAdapter<String> dataAdapter_territory = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.detail_spinner_text_color, list_territory);
        dataAdapter_territory.setDropDownViewResource(R.layout.detail_spinner_text_clicked);
        territory_name.setAdapter(dataAdapter_territory);

        //Building Spinner
        list_building = new ArrayList<String>();
        list_building.add(get_building_name);
        ArrayAdapter<String> dataAdapter_building = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.detail_spinner_text_color, list_building);
        dataAdapter_building.setDropDownViewResource(R.layout.detail_spinner_text_clicked);
        building_name.setAdapter(dataAdapter_building);

        //Area Spinner
        list_area = new ArrayList<String>();
        list_area.add(get_area_name);
        ArrayAdapter<String> dataAdapter_area = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.detail_spinner_text_color, list_area);
        dataAdapter_area.setDropDownViewResource(R.layout.detail_spinner_text_clicked);
        area_name.setAdapter(dataAdapter_area);

        //Device Spinner
        list_device = new ArrayList<String>();
        list_device.add(get_device_name);
        ArrayAdapter<String> dataAdapter_device = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.detail_spinner_text_color, list_device);
        dataAdapter_device.setDropDownViewResource(R.layout.detail_spinner_text_clicked);
        device_name.setAdapter(dataAdapter_device);

        //Subject Spinner
        list_subject = new ArrayList<String>();
        list_subject.add(get_subject_name);
        ArrayAdapter<String> dataAdapter_subject = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.detail_spinner_text_color, list_subject);
        dataAdapter_subject.setDropDownViewResource(R.layout.detail_spinner_text_clicked);
        subject_name.setAdapter(dataAdapter_subject);

        //StartDate Spinner
        list_start_date = new ArrayList<String>();
        list_start_date.add(get_start_date);
        ArrayAdapter<String> dataAdapter_startDate = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.detail_spinner_text_color, list_start_date);
        dataAdapter_startDate.setDropDownViewResource(R.layout.detail_spinner_text_clicked);
        start_date.setAdapter(dataAdapter_startDate);

        //EndDate Spinner
        list_end_date = new ArrayList<String>();
        list_end_date.add(get_start_date);
        ArrayAdapter<String> dataAdapter_endDate = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.detail_spinner_text_color, list_end_date);
        dataAdapter_endDate.setDropDownViewResource(R.layout.detail_spinner_text_clicked);
        end_date.setAdapter(dataAdapter_endDate);

        //Kullanici Spinner
        list_kullanici_adi = new ArrayList<String>();
        list_kullanici_adi.add(get_kullanici_adi);
        ArrayAdapter<String> dataAdapter_kullanici = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.detail_spinner_text_color, list_kullanici_adi);
        dataAdapter_kullanici.setDropDownViewResource(R.layout.detail_spinner_text_clicked);
        kullanici_adi.setAdapter(dataAdapter_kullanici);



        //BackButton Action
        back_imagebutton = findViewById(R.id.back_button);
        back_imagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });


    }
}
