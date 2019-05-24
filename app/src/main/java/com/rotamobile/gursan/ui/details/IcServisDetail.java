package com.rotamobile.gursan.ui.details;

import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.rotamobile.gursan.R;
import com.rotamobile.gursan.data.Server;

import org.json.JSONObject;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.paperdb.Paper;


public class IcServisDetail extends AppCompatActivity implements View.OnClickListener {

    private ImageButton back;
    private TextView title;
    private Button guncelle,sil;
    private Bundle extras;
    private String get_ProductName = "", getUserID = "", get_last_amount = "";
    private Integer get_Amount = 0, get_id = 0;
    private EditText amount,malzeme;
    private ProgressDialog progressDialog,progressDialog_material_update,progressDialog_delete;

    ArrayList arrayName;
    private boolean get_mesajRequestUpdate = false, get_mesajRequestDelete = false;
    private MaterialUpdate materialUpdate = null;
    private MaterialDelete materialDelete = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_ic_servis);

        Paper.init(IcServisDetail.this);

        //Progress Diaolog initialize
        progressDialog = new ProgressDialog(IcServisDetail.this);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressDialog.setIndeterminate(true);

        progressDialog_material_update = new ProgressDialog(IcServisDetail.this);
        progressDialog_material_update.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressDialog_material_update.setIndeterminate(true);

        progressDialog_delete = new ProgressDialog(IcServisDetail.this);
        progressDialog_delete.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressDialog_delete.setIndeterminate(true);

        arrayName = new ArrayList<String>();

        back = findViewById(R.id.back_button_ic_servis_detail);
        back.setOnClickListener(this);

        amount = findViewById(R.id.edt_ic_detail_amount);
        malzeme = findViewById(R.id.edt_detail_malzeme);
        malzeme.setKeyListener(null);
/*        getMaterialsTask = new GetMaterials();
        getMaterialsTask.execute((Void) null);*/


        title = findViewById(R.id.toolbar_title_ic_servis_detail);
        title.setText("İç Servis Detay");

        guncelle = findViewById(R.id.btn_ic_detail_update);
        guncelle.setOnClickListener(this);
        sil = findViewById(R.id.btn_ic_detail_delete);
        sil.setOnClickListener(this);

        extras = getIntent().getExtras();
        if(extras != null){

            get_ProductName = extras.getString("ProductName");
            get_Amount = extras.getInt("amount");
            get_id = extras.getInt("id");
        }

        //Get User id from Login
        getUserID = Paper.book().read("user_id");

        amount.setText(String.valueOf(get_Amount));
        malzeme.setText(get_ProductName);

    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){

            case R.id.back_button_ic_servis_detail:
                finish();
                break;

            case R.id.btn_ic_detail_update:

                int selectedID = 0;

                get_last_amount = amount.getText().toString();

                materialUpdate = new MaterialUpdate(get_id,Integer.parseInt(getUserID),Integer.parseInt(get_last_amount));
                materialUpdate.execute((Void) null);

                break;

            case R.id.btn_ic_detail_delete:

                materialDelete = new MaterialDelete(get_id,Integer.parseInt(getUserID));
                materialDelete.execute((Void) null);

                break;
        }
    }

    /*public class GetMaterials extends AsyncTask<Void, Void, Boolean> {

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

                    adapter = new ArrayAdapter(IcServisDetail.this,android.R.layout.simple_list_item_1,arrayName);
                    searchableSpinner.setAdapter(adapter);
                    searchableSpinner.setTitle("Malzeme Seçiniz");
                    searchableSpinner.setPositiveButton("Kapat");
                    //sp_main.setAdapter(adapter);

                    //ProductUnit Service
*//*                    productUnitTask = new ProductUnit();
                      productUnitTask.execute((Void) null);*//*

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
    }*/

    public class MaterialUpdate extends AsyncTask<Void,Void,Boolean>{

        private Integer _id;
        private Integer update_id;
        private Integer amount;

        MaterialUpdate(Integer _id,Integer update_id,Integer amount){

            this._id = _id;
            this.update_id = update_id;
            this.amount = amount;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog_material_update.setMessage("\tLoading...");
            progressDialog_material_update.setCancelable(false);
            progressDialog_material_update.show();
            progressDialog_material_update.setContentView(R.layout.custom_progress);
        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            try {
                String getRequestAdd_result = Server.MaterialUpdate(_id,update_id,amount);
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
                progressDialog_material_update.dismiss();

                new SweetAlertDialog(IcServisDetail.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("İşlem Mesajı")
                        .setContentText("İşlem Başarılı,Güncelleme Yapılmıştır")
                        .setConfirmText("Tamam")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismissWithAnimation();
                                finish();
                            }
                        })
                        .show();


            }else{
                progressDialog_material_update.dismiss();
            }
        }

        @Override
        protected void onCancelled() {
            materialUpdate = null;
            progressDialog_material_update.dismiss();
        }
    }

    public class MaterialDelete extends AsyncTask<Void,Void,Boolean>{

        private Integer _id;
        private Integer updateUser_id;

        MaterialDelete(Integer _id,Integer updateUser_id){

            this._id = _id;
            this.updateUser_id = updateUser_id;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog_delete.setMessage("\tLoading...");
            progressDialog_delete.setCancelable(false);
            progressDialog_delete.show();
            progressDialog_delete.setContentView(R.layout.custom_progress);
        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            try {
                String getRequestDelete_result = Server.MaterialDelete(_id,updateUser_id);
                if(!getRequestDelete_result.trim().equalsIgnoreCase("false")){

                    try{

                        JSONObject jObject = new JSONObject(getRequestDelete_result);
                        get_mesajRequestDelete = jObject.getBoolean("Successful");
                        Log.i("mesajRequestAdd",""+get_mesajRequestDelete);

                    }catch(Exception e){
                        Log.i("Exception: ",e.getMessage());
                    }

                }else{
                    get_mesajRequestDelete = false;
                }

            }catch(Exception e){
                Log.i("Exception: ",e.getMessage());
            }

            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(get_mesajRequestDelete == true){
                progressDialog_delete.dismiss();

                new SweetAlertDialog(IcServisDetail.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("İşlem Mesajı")
                        .setContentText("İşlem Başarılı,Silme İşlemi Yapılmıştır")
                        .setConfirmText("Tamam")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismissWithAnimation();
                                finish();
                            }
                        })
                        .show();


            }else{
                progressDialog_delete.dismiss();
            }
        }

        @Override
        protected void onCancelled() {
            materialDelete = null;
            progressDialog_delete.dismiss();
        }
    }
}
