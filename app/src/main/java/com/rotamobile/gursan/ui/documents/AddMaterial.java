package com.rotamobile.gursan.ui.documents;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
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
import android.widget.Toast;

import com.androidbuts.multispinnerfilter.KeyPairBoolData;
import com.crashlytics.android.Crashlytics;
import com.google.gson.Gson;
import com.rotamobile.gursan.Main;
import com.rotamobile.gursan.R;
import com.rotamobile.gursan.data.Server;
import com.rotamobile.gursan.model.allWithStock.DataStock;
import com.rotamobile.gursan.model.allWithStock.ModelStock;
import com.rotamobile.gursan.model.productUnitSpinner.DataProductUnit;
import com.rotamobile.gursan.model.productUnitSpinner.ModelProductUnit;
import com.rotamobile.gursan.ui.activity.DisServisTalepList;
import com.rotamobile.gursan.ui.activity.IcServisTalepList;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import io.fabric.sdk.android.Fabric;
import io.paperdb.Paper;

import static com.rotamobile.gursan.data.Server.GetAllMaterial;
import static com.rotamobile.gursan.data.Server.MaterialAdd;


public class AddMaterial extends AppCompatActivity {

    private LinearLayout parentLinearLayout;
    EditText edittext_var;
    List<KeyPairBoolData> listArray2;
    List<String> list;

    private DataStock response_materials;
    private ArrayList<ModelStock> definedMaterials;
    private String get_mesaj_materials = "";

    private GetMaterials getMaterialsTask = null;
    private AddMaterials addMaterialTask = null;

    private ProgressDialog progressDialog,progressDialog_product;
    private Toolbar toolbar_material;
    private TextView textView_title;
    private ImageButton back_malzeme;
    SearchableSpinner get_SelectedName,searchSingleSpinner;
    ArrayAdapter adapter;
    ArrayList arrayName;
    private Boolean get_mesaj_addMaterial;
    private Integer get_workerID = 0;
    private String get_insertUserID;
    Bundle extras;
    private Integer get_amount = 0;
    private Integer get_Selected_id = 0;
    private ImageButton delete,check,malzeme_listele;
    //private Spinner spin_material;
    private List<String> product_spin_cinsi;

    private DataProductUnit response_ProductUnit;
    private ArrayList<ModelProductUnit> defined_productUnit;
    private String get_mesaj_productUnit= "";
    //private ProductUnit productUnitTask = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(AddMaterial.this, new Crashlytics());
        setContentView(R.layout.add_material);
    //ToolBar init
        toolbar_material = findViewById(R.id.toolbar_material);
        setSupportActionBar(toolbar_material);
        Paper.init(AddMaterial.this);

        //get UserID from Login
        get_insertUserID = Paper.book().read("user_id");

        textView_title = findViewById(R.id.toolbar_title_malzeme);
        textView_title.setText("Malzeme Ekle");

        back_malzeme = findViewById(R.id.back_button_malzeme);
        back_malzeme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        parentLinearLayout = (LinearLayout) findViewById(R.id.lyt_scroll);

        //Progress Diaolog initialize
        progressDialog = new ProgressDialog(AddMaterial.this);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressDialog.setIndeterminate(true);

        progressDialog_product = new ProgressDialog(AddMaterial.this);
        progressDialog_product.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressDialog_product.setIndeterminate(true);

        getMaterialsTask = new GetMaterials();
        getMaterialsTask.execute((Void) null);

        arrayName = new ArrayList<String>();

        product_spin_cinsi = new ArrayList<String>();
        product_spin_cinsi.add("Cinsi");

     //get Values from ListItemAdapter
        extras = getIntent().getExtras();
        if(extras != null) {
            get_workerID = extras.getInt("id"); //get WorkID
            //get_insertUserID = extras.getInt("insert_user_id"); //get InsertUserID
        }

        malzeme_listele = findViewById(R.id.imgbtn_ic_servis_listeleme);
        malzeme_listele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent go_icServisTalep = new Intent(AddMaterial.this,IcServisTalepList.class);
                go_icServisTalep.putExtra("workerID",get_workerID);
                go_icServisTalep.putExtra("operation","editable");
                startActivity(go_icServisTalep);
            }
        });
    }


        public void onAddField(View v) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View rowView = inflater.inflate(R.layout.field, null);
            // Add the new row before the add field button.
            parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount());

            searchSingleSpinner = (SearchableSpinner) rowView.findViewById(R.id.searchSingle);
            adapter = new ArrayAdapter(AddMaterial.this,android.R.layout.simple_list_item_1,arrayName);
            searchSingleSpinner.setAdapter(adapter);
            searchSingleSpinner.setTitle("Malzeme Seçiniz");
            searchSingleSpinner.setPositiveButton("Kapat");

/*            spin_material = (Spinner) rowView.findViewById(R.id.spin_cinsi_malzeme);
              productSpinCinsi();*/
        }

    /*private void productSpinCinsi() {

        ArrayAdapter<String> dataAdapter_alan = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.detail_spinner_text_color, product_spin_cinsi) {

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
        spin_material.setAdapter(dataAdapter_alan);
        spin_material.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint
                if (position > 0) {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }*/

    public void onDelete(View v) {
            parentLinearLayout.removeView((View) v.getParent());
        }

        public void onGetField(View v){

            int selectedID = 0;

            get_SelectedName = (SearchableSpinner) ((View) v.getParent()).findViewById(R.id.searchSingle);
            edittext_var = (EditText)((View) v.getParent()).findViewById(R.id.number_edit_text);
            //spin_material = (Spinner) ((View) v.getParent()).findViewById(R.id.spin_cinsi_malzeme);
            check = (ImageButton) ((View) v.getParent()).findViewById(R.id.send_button);
            delete = (ImageButton) ((View) v.getParent()).findViewById(R.id.delete_button);

         //Check if amount set or not
            if(!edittext_var.getText().toString().equals("")) {
                get_amount = Integer.parseInt(edittext_var.getText().toString());
            }

         //Get Selected id from Spinner Name
/*            if(spin_material.getSelectedItem() != null){
                for(int i=0;i<defined_productUnit.size();i++){
                    if(spin_material.getSelectedItem().toString().equals(defined_productUnit.get(i).getName())){
                        get_Selected_id = defined_productUnit.get(i).getID();
                    }
                }
            }*/

         //Getting ID according to the Selected Materials Name
            if(get_SelectedName.getSelectedItem() != null) {
                for (int i = 0; i < definedMaterials.size(); i++) {
                 //Check if material Select or not
                     if (definedMaterials.get(i).getName().equals(get_SelectedName.getSelectedItem().toString())) {

                     selectedID = definedMaterials.get(i).getID();
                  //Send AddMaterial
                     addMaterialTask = new AddMaterials(get_amount,get_workerID,selectedID,Integer.parseInt(get_insertUserID));
                     addMaterialTask.execute((Void) null);
                 }
             }
         }else if(get_SelectedName.getSelectedItem() == null){


                Toasty.info(AddMaterial.this, "Malzeme Seçiniz", Toast.LENGTH_SHORT, true).show();
         }

            //Toast.makeText(getApplicationContext(),""+selectedID + edittext_var.getText().toString(),Toast.LENGTH_LONG).show();
        }




    public class GetMaterials extends AsyncTask<Void, Void, Boolean> {

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
                String getMaterials_result = GetAllMaterial();
                if(!getMaterials_result.trim().equalsIgnoreCase("false")){

                    try {

                        response_materials = new Gson().fromJson(getMaterials_result, DataStock.class);
                        definedMaterials = response_materials.getData_list();
                        Log.i("Tag:definedJobList",""+definedMaterials);
                        get_mesaj_materials = "true";

                    } catch (Exception e) {
                        Log.i("Exception: ",e.getMessage());

                    }
                }else{
                    get_mesaj_materials = "false";
                }

            } catch (Exception e) {

            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            if(!get_mesaj_materials.equals("false")){
                progressDialog.dismiss();
                if(definedMaterials.size() > 0){

                    list = new ArrayList<String>();
                    listArray2 = new ArrayList<>();

                    for (int i = 0; i < definedMaterials.size(); i++) {
                        //Getting Device Name
                        list.add(definedMaterials.get(i).getName());

                        h = new KeyPairBoolData();
                        h.setId(definedMaterials.get(i).getID());
                        h.setName(definedMaterials.get(i).getName());
                        //if definedJobList.get(i).getSelected() false write it
                        h.setSelected(false);
                        arrayName.add(definedMaterials.get(i).getName());


                        listArray2.add(h);
                    }

                    adapter = new ArrayAdapter(AddMaterial.this,android.R.layout.simple_list_item_1,arrayName);
                    //sp_main.setAdapter(adapter);

                 //ProductUnit Service
/*                    productUnitTask = new ProductUnit();
                      productUnitTask.execute((Void) null);*/

                }


            }else{
                progressDialog.dismiss();
                //deviceSpinnerAction();

            }
        }

        @Override
        protected void onCancelled() {
            getMaterialsTask = null;
            progressDialog.dismiss();

        }
    }

    public class AddMaterials extends AsyncTask<Void, Void, Boolean> {

        private final Integer amount;
        private final Integer workID;
        private final Integer product_id;
        private final Integer insertUser_id;

        AddMaterials(Integer amount, Integer workID, Integer product_id,Integer insertUser_id){

            this.amount = amount;
            this.workID = workID;
            this.product_id = product_id;
            this.insertUser_id = insertUser_id;
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
                String getMaterials_result = MaterialAdd(amount,workID,product_id,insertUser_id);
                if(!getMaterials_result.trim().equalsIgnoreCase("false")){

                    try {

                        JSONObject jObject = new JSONObject(getMaterials_result);
                        get_mesaj_addMaterial = jObject.getBoolean("Successful");

                        Log.i("msjAddMaterial",""+get_mesaj_addMaterial);

                    } catch (JSONException e) {
                        Log.i("Exception: ",e.getMessage());

                    }


                }else{
                    get_mesaj_materials = "false";
                }

            } catch (Exception e) {
                Log.i("Exception: ",e.getMessage());
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);


                if(get_mesaj_addMaterial.equals(true)){
                    progressDialog.dismiss();

                    delete.setImageResource(R.drawable.tick);
                    delete.setClickable(false);
                    check.setClickable(false);
                }


            else{
                progressDialog.dismiss();
                //deviceSpinnerAction();
            }
        }

        @Override
        protected void onCancelled() {
            addMaterialTask = null;
            progressDialog.dismiss();
        }
    }

    /*public class ProductUnit extends AsyncTask<Void,Void,Boolean>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog_product.setMessage("\tLoading...");
            progressDialog_product.setCancelable(false);
            progressDialog_product.show();
            progressDialog_product.setContentView(R.layout.custom_progress);
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
                progressDialog_product.dismiss();
                if(defined_productUnit.size()>0){
                    for(int i=0;i< defined_productUnit.size();i++) {

                        product_spin_cinsi.add(defined_productUnit.get(i).getName());
                    }
                }

            }else{
                progressDialog_product.dismiss();
            }
        }

        @Override
        protected void onCancelled() {
            productUnitTask = null;
            progressDialog_product.dismiss();
        }
    }*/


    }
