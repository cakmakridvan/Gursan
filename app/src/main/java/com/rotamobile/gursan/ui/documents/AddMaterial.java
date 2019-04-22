package com.rotamobile.gursan.ui.documents;

import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidbuts.multispinnerfilter.KeyPairBoolData;
import com.google.gson.Gson;
import com.rotamobile.gursan.R;
import com.rotamobile.gursan.model.allWithStock.DataStock;
import com.rotamobile.gursan.model.allWithStock.ModelStock;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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

    private ProgressDialog progressDialog;
    private Toolbar toolbar_material;
    private TextView textView_title;
    private ImageButton back_malzeme;
    SearchableSpinner get_SelectedName,searchSingleSpinner;
    ArrayAdapter adapter;
    ArrayList arrayName;
    private Boolean get_mesaj_addMaterial;
    private Integer get_workerID = 0;
    Bundle extras;
    private Integer get_amount = 0;
    private Button check,delete;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_material);
    //ToolBar init
        toolbar_material = findViewById(R.id.toolbar_material);
        setSupportActionBar(toolbar_material);

        textView_title = findViewById(R.id.toolbar_title_malzeme);
        textView_title.setText("Malzeme Ekle");



        back_malzeme = findViewById(R.id.back_button_malzeme);
        back_malzeme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        parentLinearLayout = (LinearLayout) findViewById(R.id.parent_linear_layout);

        //Progress Diaolog initialize
        progressDialog = new ProgressDialog(AddMaterial.this);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressDialog.setIndeterminate(true);

        getMaterialsTask = new GetMaterials();
        getMaterialsTask.execute((Void) null);

        arrayName = new ArrayList<String>();

     //get Values from ListItemAdapter
        extras = getIntent().getExtras();
        if(extras != null) {
            get_workerID = extras.getInt("id"); //get WorkID
        }

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


        }

        public void onDelete(View v) {
            parentLinearLayout.removeView((View) v.getParent());
        }

        public void onGetField(View v){

            int selectedID = 0;

            get_SelectedName = (SearchableSpinner) ((View) v.getParent()).findViewById(R.id.searchSingle);
            edittext_var = (EditText)((View) v.getParent()).findViewById(R.id.number_edit_text);
         //Check if amount set or not
            if(!edittext_var.getText().toString().equals("")) {
                get_amount = Integer.parseInt(edittext_var.getText().toString());
            }

            check = (Button) ((View) v.getParent()).findViewById(R.id.send_button);
            delete = (Button) ((View) v.getParent()).findViewById(R.id.delete_button);



         //Getting ID according to the Selected Materials Name
         if(get_SelectedName.getSelectedItem() != null) {
             for (int i = 0; i < definedMaterials.size(); i++) {
                 //Check if material Select or not
                 if (definedMaterials.get(i).getName().equals(get_SelectedName.getSelectedItem().toString())) {

                     selectedID = definedMaterials.get(i).getID();
                  //Send AddMaterial
                     addMaterialTask = new AddMaterials(selectedID,get_workerID,get_amount);
                     addMaterialTask.execute((Void) null);

                 }
             }
         }else if(get_SelectedName.getSelectedItem() == null){

             Toast.makeText(getApplicationContext(),"Malzeme Seçiniz",Toast.LENGTH_LONG).show();
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

        private final Integer id;
        private final Integer workID;
        private final Integer adet;

        AddMaterials(Integer id, Integer workID, Integer adet){

            this.id = id;
            this.workID = id;
            this.adet = adet;
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
                String getMaterials_result = MaterialAdd(id,workID,adet);
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

                    delete.setBackgroundResource(R.drawable.check);
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


    }