package com.rotamobile.gursan.ui.documents;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.androidbuts.multispinnerfilter.KeyPairBoolData;
import com.androidbuts.multispinnerfilter.SingleSpinnerSearch;
import com.androidbuts.multispinnerfilter.SpinnerListener;
import com.google.gson.Gson;
import com.rotamobile.gursan.R;
import com.rotamobile.gursan.model.allWithStock.DataStock;
import com.rotamobile.gursan.model.allWithStock.ModelStock;
import java.util.ArrayList;
import java.util.List;
import static com.rotamobile.gursan.data.Server.GetAllMaterial;


public class AddMaterial extends AppCompatActivity {

    private LinearLayout parentLinearLayout;
    EditText edittext_var ;
    SingleSpinnerSearch searchSingleSpinner;
    List<KeyPairBoolData> listArray2;
    List<String> list;

    private DataStock response_materials;
    private ArrayList<ModelStock> definedMaterials;
    private String get_mesaj_materials = "";
    private GetMaterials getMaterialsTask = null;
    private ProgressDialog progressDialog;
    private Toolbar toolbar_material;
    private TextView textView_title;
    private ImageButton back_malzeme;

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


//        list = Arrays.asList(getResources().getStringArray(R.array.sports_array));
        searchSingleSpinner = (SingleSpinnerSearch) findViewById(R.id.searchSingleSpinner);
    }




        public void onAddField(View v) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View rowView = inflater.inflate(R.layout.field, null);



            searchSingleSpinner = (SingleSpinnerSearch) rowView.findViewById(R.id.searchSingle);
            searchSingleSpinner.setItems(listArray2, -1, new SpinnerListener() {

                @Override
                public void onItemsSelected(List<KeyPairBoolData> items) {

                    for (int i = 0; i < items.size(); i++) {
                        if (items.get(i).isSelected()) {
                            Log.i("TAG", i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                        }
                    }
                }
            });

            // Add the new row before the add field button.
            parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount() - 1);
        }

        public void onDelete(View v) {
            parentLinearLayout.removeView((View) v.getParent());
        }

        public void onGetField(View v){



            edittext_var = (EditText)((View) v.getParent()).findViewById(R.id.number_edit_text) ;

            Toast.makeText(getApplicationContext(),edittext_var.getText().toString(),Toast.LENGTH_LONG).show();
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


                        listArray2.add(h);
                    }

                    searchSingleSpinner.setItems(listArray2, -1, new SpinnerListener() {

                        @Override
                        public void onItemsSelected(List<KeyPairBoolData> items) {

                            for (int i = 0; i < items.size(); i++) {

                                    Log.i("TAG", i + " : " + items.get(i).getName() + " : " + items.get(i).getId());

                            }
                        }
                    });

                }


            }else{
                progressDialog.dismiss();
                //deviceSpinnerAction();

            }
        }

    }


    }
