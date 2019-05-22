package com.rotamobile.gursan.ui.details;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.rotamobile.gursan.R;
import com.rotamobile.gursan.data.Server;
import com.rotamobile.gursan.model.productUnitSpinner.DataProductUnit;
import com.rotamobile.gursan.model.productUnitSpinner.ModelProductUnit;
import com.rotamobile.gursan.ui.documents.DisServisForm;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class DisServisDetail extends AppCompatActivity implements View.OnClickListener {

    private Bundle extras;
    private EditText topic,description,amount;
    private String get_topic,get_description,get_cinsi;
    private Integer get_amount;
    private DataProductUnit response_ProductUnit;
    private ArrayList<ModelProductUnit> defined_productUnit;
    private String get_mesaj_productUnit= "";
    private List<String> product_cinsi;
    private ProductUnit productUnitTask = null;
    private ProgressDialog progressDialog,progressDialogAdd_update;
    private Spinner spinner;
    private Integer get_cinsiCode;
    private ImageButton back;
    private TextView title;
    private Button guncelle;
    private String getUserID = "";
    private RequestUpdate requestUpdate = null;
    private boolean get_mesajRequestUpdate = false;
    private Integer get_id = 0;
    private Integer get_selectedProductID = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_dis_servis);
        Paper.init(DisServisDetail.this);

        extras = getIntent().getExtras();
        if(extras != null){

           get_topic = extras.getString("konu");
           get_description = extras.getString("aciklama");
           get_amount = extras.getInt("amount");
           get_cinsi = extras.getString("cinsi");
           get_id = extras.getInt("detailId");
        }
      //Get User id from Login
        getUserID = Paper.book().read("user_id");

        topic = findViewById(R.id.edt_dis_detail_konu);
        description = findViewById(R.id.edt_dis_detail_aciklama);
        amount = findViewById(R.id.edt_dis_detail_amount);
        spinner = findViewById(R.id.spin_dis_detail);

        title = findViewById(R.id.toolbar_title_dis_servis_detail);
        title.setText("Dış Servis Detay");

        back = findViewById(R.id.back_button_dis_servis_detail);
        back.setOnClickListener(this);
        guncelle = findViewById(R.id.btn_dis_detail_update);
        guncelle.setOnClickListener(this);

        topic.setText(get_topic);
        description.setText(get_description);
        amount.setText(String.valueOf(get_amount));


        //Progress Diaolog initialize
        progressDialog = new ProgressDialog(DisServisDetail.this);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressDialog.setIndeterminate(true);

        progressDialogAdd_update = new ProgressDialog(DisServisDetail.this);
        progressDialogAdd_update.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressDialogAdd_update.setIndeterminate(true);

        //ProductUnit Service
        productUnitTask = new ProductUnit();
        productUnitTask.execute((Void) null);

        product_cinsi = new ArrayList<String>();
        product_cinsi.add("Cinsi");


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.back_button_dis_servis_detail:

                finish();

                break;

            case R.id.btn_dis_detail_update:

                get_description = description.getText().toString();
                get_topic = topic.getText().toString();
                String miktar = amount.getText().toString();
                get_amount = Integer.parseInt(miktar);

                requestUpdate = new RequestUpdate(get_id,Integer.parseInt(getUserID),get_amount,get_topic,get_description,get_selectedProductID);
                requestUpdate.execute((Void) null);

                break;
        }
    }

    public class ProductUnit extends AsyncTask<Void,Void,Boolean> {

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

                        if(get_cinsi.equals(defined_productUnit.get(i).getName())){

                            get_cinsiCode = i+1;
                            get_selectedProductID = defined_productUnit.get(i).getCode();
                        }
                    }

                    productCinsi();
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

        ArrayAdapter<String> dataAdapter_alan = new ArrayAdapter<String>(getApplicationContext(),
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
        spinner.setAdapter(dataAdapter_alan);
        spinner.setSelection(get_cinsiCode);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint
                if (position > 0) {

                    //Getting AreaID to get data from Device
                    get_selectedProductID = defined_productUnit.get(position-1).getCode();
                    Log.i("Tag:ProductUnitID:",""+get_selectedProductID);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    public class RequestUpdate extends AsyncTask<Void,Void,Boolean>{

        private Integer workOrder_id;
        private Integer update_id;
        private String description;
        private Integer unit_id;
        private Integer amount;
        private String subject;

        RequestUpdate(Integer workOrder_id,Integer update_id,Integer amount,String subject,String description,Integer unit_id){

            this.workOrder_id = workOrder_id;
            this.update_id = update_id;
            this.amount = amount;
            this.subject = subject;
            this.description = description;
            this.unit_id = unit_id;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialogAdd_update.setMessage("\tLoading...");
            progressDialogAdd_update.setCancelable(false);
            progressDialogAdd_update.show();
            progressDialogAdd_update.setContentView(R.layout.custom_progress);
        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            try {
                String getRequestAdd_result = Server.RequestUpdate(workOrder_id,update_id,amount,subject,description,unit_id);
                if(!getRequestAdd_result.trim().equalsIgnoreCase("false")){

                    try{

                        JSONObject jObject = new JSONObject(getRequestAdd_result);
                        get_mesajRequestUpdate = jObject.getBoolean("Successful");
                        Log.i("mesajRequestAdd",""+get_mesajRequestUpdate);

                    }catch(Exception e){
                        Log.i("Exception: ",e.getMessage());
                    }

                }else{
                    get_mesajRequestUpdate = false;
                }

            }catch(Exception e){
                Log.i("Exception: ",e.getMessage());
            }

            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(get_mesajRequestUpdate == true){
                progressDialogAdd_update.dismiss();


            }else{
                progressDialogAdd_update.dismiss();
            }
        }

        @Override
        protected void onCancelled() {
            requestUpdate = null;
            progressDialogAdd_update.dismiss();
        }
    }

}
