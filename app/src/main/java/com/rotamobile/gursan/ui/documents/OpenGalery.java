package com.rotamobile.gursan.ui.documents;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rotamobile.gursan.R;
import com.rotamobile.gursan.data.Server;
import com.rotamobile.gursan.ui.bottom_navigation.MainBottomNavigation;
import com.rotamobile.gursan.utils.enums.Enums;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;


public class OpenGalery extends AppCompatActivity {

    public static final int PICK_IMAGE = 1;
    private Button send;
    private ImageView img;
    private Toolbar toolbar_galeri;
    private TextView txt_toolbar_galeri;
    private EditText galeri_yorum;
    private ImageButton back_galeri;
    private String get_comment_galery = "";
    private String encoded_galery = "";
    Bundle extras;
    private Integer get_workerID = 0;
    private Integer get_userID = 0;
    private DocomentOpenGalery documentOpenGalery_task = null;
    private ProgressDialog progressDialog_galeri;
    private String get_mesaj_galeri = "";
    String fileName_galeri = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.open_galery);

        //get Values from ListItemAdapter
        extras = getIntent().getExtras();
        get_workerID = extras.getInt("id");
        get_userID = extras.getInt("insert_user_id");

        galeri_yorum = findViewById(R.id.edt_imgGalery_yorum);
        txt_toolbar_galeri = findViewById(R.id.toolbar_title_open_galery);
        txt_toolbar_galeri.setText("Galeriden Ekle");
     //ToolBar init
        toolbar_galeri = findViewById(R.id.toolbar_open_galery);
        setSupportActionBar(toolbar_galeri);
        img = findViewById(R.id.imageView_galery);

     //Back Button Action
        back_galeri = findViewById(R.id.back_button_open_galery);
        back_galeri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

     //Permissions
        EnableRuntimePermission();

        //Progress Diaolog initialize
        progressDialog_galeri = new ProgressDialog(OpenGalery.this);
        progressDialog_galeri.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressDialog_galeri.setIndeterminate(true);

     //Send Button Action
        send = findViewById(R.id.btn_openGalery);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                get_comment_galery = galeri_yorum.getText().toString();
                if(get_comment_galery == null && get_comment_galery.isEmpty()){

                    get_comment_galery = "";
                }

                if(encoded_galery == null && encoded_galery.isEmpty()){
                    encoded_galery = "Hatalı Resim";
                    showToasty("Resim Dönüştürme Başarısız");
                }else{


                    documentOpenGalery_task = new DocomentOpenGalery(get_workerID, Enums.resim,true,encoded_galery,get_comment_galery,get_userID,get_userID);
                    documentOpenGalery_task.execute((Void)null);

                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 200 && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                int h = 200; // height in pixels
                int w = 200; // width in pixels
                Bitmap scaled = Bitmap.createScaledBitmap(bitmap, h, w, true);
                img.setImageBitmap(scaled);

                //Convert Bitmap to Byte Array
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                scaled.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                //to encode base64 from byte array
                encoded_galery = Base64.encodeToString(byteArray, Base64.DEFAULT);

                //Create File
                fileName_galeri = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "byte.txt";
                String path = Environment.getExternalStorageDirectory() + File.separator  + "yourFolder";
                // Create the folder.
                File folder = new File(path);
                if(!folder.exists()) {
                    folder.mkdirs();
                }
                File file2 = new File(folder, "galeri_image.txt");
                //Write Byte Array to Txt
                try {
                    FileOutputStream fOut = new FileOutputStream(file2);
                    OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
                    myOutWriter.append(encoded_galery);
                    myOutWriter.close();
                    fOut.flush();
                    fOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }else{
            finish();
        }
    }

    public void EnableRuntimePermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(OpenGalery.this,
                Manifest.permission.READ_EXTERNAL_STORAGE))
        {

        } else {

            ActivityCompat.requestPermissions(OpenGalery.this,new String[]{
                    Manifest.permission.CAMERA}, 1);

        }
    }

    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] PResult) {

        switch (RC) {

            case PICK_IMAGE:

                if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(OpenGalery.this,"Permission Granted, Now your application can access Read External.", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 200);

                } else {

                    Toast.makeText(OpenGalery.this,"Permission Canceled, Now your application cannot access Read External.", Toast.LENGTH_LONG).show();

                }
                break;
        }
    }

    private void showToasty(String mesaj) {

        Toasty.error(getApplicationContext(), mesaj, Toast.LENGTH_SHORT, true).show();
    }

    public class DocomentOpenGalery extends AsyncTask<Void, Void, Boolean>{

        private final Integer workOrderID;
        private final Integer documentTypeID;
        private final Boolean active;
        private final String documentContent;
        private final String commentText;
        private final Integer insertUserID;
        private final Integer updateUserID;

        DocomentOpenGalery(Integer workOrderID,Integer documentTypeID,Boolean active,String documentContent,String commentText,Integer insertUserID,Integer updateUserID){

            this.workOrderID = workOrderID;
            this.documentTypeID = documentTypeID;
            this.active = active;
            this.documentContent = documentContent;
            this.commentText = commentText;
            this.insertUserID = insertUserID;
            this.updateUserID = updateUserID;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog_galeri.setMessage("\tLoading...");
            progressDialog_galeri.setCancelable(false);
            progressDialog_galeri.show();
            progressDialog_galeri.setContentView(R.layout.custom_progress);
        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            String todoListUpdate_service = Server.DocumentAdd(workOrderID,documentTypeID,active,documentContent,commentText,insertUserID,updateUserID);
            if(!todoListUpdate_service.trim().equalsIgnoreCase("false")){

                try {

                    JSONObject jObject = new JSONObject(todoListUpdate_service);
                    get_mesaj_galeri = jObject.getString("Successful");
                    String get_mesaj_data = jObject.getString("Data");

                    Log.i("msjTodoListUpdate",get_mesaj_galeri);
                    Log.i("msjData",get_mesaj_data);

                } catch (JSONException e) {
                    Log.i("Exception: ",e.getMessage());

                }
            }

            else{
                get_mesaj_galeri = "false";
            }

            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            if(get_mesaj_galeri.equals("true")) {

                progressDialog_galeri.dismiss();

                new SweetAlertDialog(OpenGalery.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("İşlem Mesajı")
                        .setContentText("İşlem Başarılı,Resim Gönderilmiştir")
                        .setConfirmText("Tamam")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismissWithAnimation();

                                Intent go_home = new Intent(OpenGalery.this,MainBottomNavigation.class);
                                startActivity(go_home);
                            }
                        })
                        .show();

            }
            else{

                progressDialog_galeri.dismiss();

                Toasty.error(getApplicationContext(), "İşlem Başarısız,tekrar deneyiniz", Toast.LENGTH_SHORT, true).show();

            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();

            documentOpenGalery_task = null;
            progressDialog_galeri.dismiss();
        }
    }

}