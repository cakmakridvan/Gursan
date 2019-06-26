package com.rotamobile.gursan.ui.documents;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.google.gson.Gson;
import com.rotamobile.gursan.Main;
import com.rotamobile.gursan.R;
import com.rotamobile.gursan.data.Server;
import com.rotamobile.gursan.model.productUnitSpinner.DataProductUnit;
import com.rotamobile.gursan.model.productUnitSpinner.ModelProductUnit;
import com.rotamobile.gursan.ui.activity.DisServisTalepList;

import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

import io.fabric.sdk.android.Fabric;

public class DisServisForm extends AppCompatActivity {

    private Toolbar toolbar_dis_servis;
    private TextView title;
    private ImageButton back_dis_Servis,talep_form_listeleme;
    private LinearLayout parentLinearLayout;
    private Integer get_workerID = 0;
    private Integer get_InsertUser_id = 0;
    Bundle extras;

    private DataProductUnit response_ProductUnit;
    private ArrayList<ModelProductUnit> defined_productUnit;
    private String get_mesaj_productUnit= "";
    private Spinner spin;
    private List<String> product_cinsi;
    private ProgressDialog progressDialog,progressDialogAdd;
    private ProductUnit productUnitTask = null;
    private RequestAdd requestAddTask = null;
    private Boolean get_mesajRequestAdd;
    private String get_mesajRequest = "";
    private EditText edt_konu,edt_aciklama,edt_adet;
    private Integer get_Selected_id = 0;
    private Integer get_Selected_amount = 0;

    private ImageButton check,delete;
    private TextView empty_list;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(DisServisForm.this, new Crashlytics());
        setContentView(R.layout.dis_servis_form);

        //Progress Diaolog initialize
        progressDialog = new ProgressDialog(DisServisForm.this);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressDialog.setIndeterminate(true);

        progressDialogAdd = new ProgressDialog(DisServisForm.this);
        progressDialogAdd.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressDialogAdd.setIndeterminate(true);


        //ToolBar init
        toolbar_dis_servis = findViewById(R.id.toolbar_dis_servis);
        setSupportActionBar(toolbar_dis_servis);

        title = findViewById(R.id.toolbar_title_dis_servis);
        title.setText("Dış Servis Talep Formu");

        empty_list = findViewById(R.id.empty_talep_dis_servis_form);

        back_dis_Servis = findViewById(R.id.back_button_dis_servis);
        back_dis_Servis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        talep_form_listeleme = findViewById(R.id.imgbtn_dis_servis_listeleme);
        talep_form_listeleme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent go_disServisTalep = new Intent(DisServisForm.this,DisServisTalepList.class);
                go_disServisTalep.putExtra("workerID",get_workerID);
                startActivity(go_disServisTalep);

            }
        });

        parentLinearLayout = (LinearLayout) findViewById(R.id.lyt_scroll_dis_servis);

        //get Values from ListItemAdapter
        extras = getIntent().getExtras();
        if(extras != null) {
            get_workerID = extras.getInt("id"); //get WorkID
            get_InsertUser_id = extras.getInt("insert_user_id"); //get InsertUserID
        }

     //ProductUnit Service
        productUnitTask = new ProductUnit();
        productUnitTask.execute((Void) null);

        product_cinsi = new ArrayList<String>();
        product_cinsi.add("Cinsi");

    }

    public void onAddFieldDisServis(View v){
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.field_disservis, null);
        spin = (Spinner) rowView.findViewById(R.id.spin_cinsi);
        productCinsi();

        // Add the new row before the add field button.
        parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount());

    }

    public void onDeleteDisServis(View v) {
        parentLinearLayout.removeView((View) v.getParent());
    }
    public void onGetFieldDisServis(View v){

        edt_konu = (EditText)((View) v.getParent()).findViewById(R.id.dis_servis_konu);
        edt_aciklama = (EditText)((View) v.getParent()).findViewById(R.id.dis_servis_aciklama);
        spin = (Spinner)((View) v.getParent()).findViewById(R.id.spin_cinsi);
        edt_adet = (EditText)((View) v.getParent()).findViewById(R.id.dis_servis_adet);

        check = (ImageButton)((View) v.getParent()).findViewById(R.id.send_button_dis_Servis);
        delete = (ImageButton)((View) v.getParent()).findViewById(R.id.delete_button_dis_servis);


        String get_konu = edt_konu.getText().toString();
        String get_aciklama = edt_aciklama.getText().toString();
        String get_amount = edt_adet.getText().toString();

        if(!get_amount.equals("")){
            get_Selected_amount = Integer.parseInt(get_amount);
        }

     //Get Selected id from Spinner Name
        if(spin.getSelectedItem() != null){
            for(int i=0;i<defined_productUnit.size();i++){
                if(spin.getSelectedItem().toString().equals(defined_productUnit.get(i).getName())){
                    get_Selected_id = defined_productUnit.get(i).getCode();
                }
            }
        }

        //Send RequestADD
        requestAddTask = new RequestAdd(get_workerID,get_konu,get_aciklama,get_Selected_id,get_Selected_amount,get_InsertUser_id);
        requestAddTask.execute((Void) null);

    }

    public class ProductUnit extends AsyncTask<Void,Void,Boolean>{

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
                String getProductUnit_result = Server.ProductUnit();
                if(!getProductUnit_result.trim().equalsIgnoreCase("false")){

                    try{

                        response_ProductUnit = new Gson().fromJson(getProductUnit_result,DataProductUnit.class);
                        defined_productUnit = response_ProductUnit.getData_list();
                        Log.i("Tag:definedProductUnit",""+defined_productUnit);
                        get_mesaj_productUnit = "true";

                    }catch(Exception e){
                        Log.i("Exception: ",e.getMessage());
                    }
                }else{
                    get_mesaj_productUnit = "false";
                }

            }catch(Exception e){
                Log.i("Exception: ",e.getMessage());
            }

            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(!get_mesaj_productUnit.equals("false")){
                progressDialog.dismiss();
                if(defined_productUnit.size()>0){
                    for(int i=0;i< defined_productUnit.size();i++) {

                        product_cinsi.add(defined_productUnit.get(i).getName());
                    }
                }else{

                    empty_list.setVisibility(View.VISIBLE);
                }

            }else{
                progressDialog.dismiss();
            }
        }

        @Override
        protected void onCancelled() {
            productUnitTask = null;
            progressDialog.dismiss();
        }
    }

    private void productCinsi() {

        ArrayAdapter<String> dataAdapter_alan = new ArrayAdapter<String>(DisServisForm.this,
                R.layout.detail_spinner_text_color, product_cinsi) {

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
        spin.setAdapter(dataAdapter_alan);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint
                if (position > 0) {

                    //Getting AreaID to get data from Device
                    Integer get_selectedProductID = defined_productUnit.get(position-1).getCode();
                    Log.i("Tag:ProductUnitID:",""+get_selectedProductID);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public class RequestAdd extends AsyncTask<Void,Void,Boolean>{

        private Integer workOrder_id;
        private String subject;
        private String description;
        private Integer unit_id;
        private Integer amount;
        private Integer insertUser_id;

        RequestAdd(Integer workOrder_id,String subject,String description,Integer unit_id,Integer amount,Integer insertUser_id){

            this.workOrder_id = workOrder_id;
            this.subject = subject;
            this.description = description;
            this.unit_id = unit_id;
            this.amount = amount;
            this.insertUser_id = insertUser_id;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialogAdd.setMessage("\tLoading...");
            progressDialogAdd.setCancelable(false);
            progressDialogAdd.show();
            progressDialogAdd.setContentView(R.layout.custom_progress);
        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            try {
                String getRequestAdd_result = Server.RequestAdd(workOrder_id,subject,description,unit_id,amount,insertUser_id);
                if(!getRequestAdd_result.trim().equalsIgnoreCase("false")){

                    try{

                        JSONObject jObject = new JSONObject(getRequestAdd_result);
                        get_mesajRequestAdd = jObject.getBoolean("Successful");
                        Log.i("mesajRequestAdd",""+get_mesajRequestAdd);

                    }catch(Exception e){
                        Log.i("Exception: ",e.getMessage());
                    }

                }else{
                       get_mesajRequest = "false";
                }

            }catch(Exception e){
                Log.i("Exception: ",e.getMessage());
            }

            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(get_mesajRequestAdd.equals(true)){
                progressDialogAdd.dismiss();

                delete.setImageResource(R.drawable.tick);
                delete.setClickable(false);
                check.setClickable(false);

            }else{
                progressDialogAdd.dismiss();
            }
        }

        @Override
        protected void onCancelled() {
            requestAddTask = null;
            progressDialogAdd.dismiss();
        }
    }


}
