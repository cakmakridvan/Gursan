package com.rotamobile.gursan.ui.documents;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import com.google.gson.Gson;
import com.rotamobile.gursan.R;
import com.rotamobile.gursan.data.Server;
import com.rotamobile.gursan.model.productUnitSpinner.DataProductUnit;
import com.rotamobile.gursan.model.productUnitSpinner.ModelProductUnit;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class DisServisForm extends AppCompatActivity {

    private Toolbar toolbar_dis_servis;
    private TextView title;
    private ImageButton back_dis_Servis;
    private LinearLayout parentLinearLayout;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        back_dis_Servis = findViewById(R.id.back_button_dis_servis);
        back_dis_Servis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        parentLinearLayout = (LinearLayout) findViewById(R.id.parent_dis_servis);

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
        spin.setAdapter(dataAdapter_alan);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint
                if (position > 0) {

                    //Getting AreaID to get data from Device
                    Integer get_selectedProductID = defined_productUnit.get(position-1).getID();
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
                if(getRequestAdd_result.trim().equalsIgnoreCase("false")){

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
