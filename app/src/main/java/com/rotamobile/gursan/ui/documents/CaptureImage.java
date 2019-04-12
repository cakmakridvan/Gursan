package com.rotamobile.gursan.ui.documents;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
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

public class CaptureImage extends AppCompatActivity {

    ImageView imageView;
    public  static final int RequestPermissionCode  = 1 ;
    Intent intent ;
    String fileName = "";
    private Toolbar toolbar;
    private TextView toolbar_text;
    private ImageButton back_button;
    private Button sendPhoto;
    private String encoded = "";
    private EditText comment;
    private String get_comment = "";
    private ProgressDialog progressDialog_document;
    private String get_mesaj_document = "";
    private DocumentADD documentADD_task = null;
    Bundle extras;
    private Integer get_workerID = 0;
    private Integer get_userID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_capture);

     //get Values from ListItemAdapter
        extras = getIntent().getExtras();
        get_workerID = extras.getInt("id");
        get_userID = extras.getInt("insert_user_id");

        imageView = findViewById(R.id.imageView);
        comment = findViewById(R.id.edt_imgCapture_yorum);
        toolbar_text = findViewById(R.id.toolbar_capture_title);
        toolbar_text.setText("Resim Ekle");

     //ToolBar init
        toolbar = findViewById(R.id.capture_toolbar);
        setSupportActionBar(toolbar);

     //Back Button action
        back_button = findViewById(R.id.capture_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

     //SendPhoto Button action
        sendPhoto = findViewById(R.id.btn_sendPhoto);
        sendPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                get_comment = comment.getText().toString();

                if(get_comment == null && get_comment.isEmpty()){

                    get_comment = "";
                }

                if(encoded == null && encoded.isEmpty()){

                    encoded = "Hatalı Resim";
                    showToasty("Resim Dönüştürme Başarısız");
                }else{

                    documentADD_task = new DocumentADD(get_workerID,Enums.resim,true,encoded,get_comment,get_userID,get_userID);
                    documentADD_task.execute((Void)null);

                }

            }
        });




        EnableRuntimePermission();

        //Progress Diaolog initialize
        progressDialog_document = new ProgressDialog(CaptureImage.this);
        progressDialog_document.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressDialog_document.setIndeterminate(true);

    }

    public void EnableRuntimePermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(CaptureImage.this,
                Manifest.permission.CAMERA))
        {

        } else {

            ActivityCompat.requestPermissions(CaptureImage.this,new String[]{
                    Manifest.permission.CAMERA}, RequestPermissionCode);

        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 7 && resultCode == RESULT_OK) {

            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);
          //Convert Bitmap to Byte Array
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
         //to encode base64 from byte array
            encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
         //Create File
            fileName = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "byte.txt";
            String path = Environment.getExternalStorageDirectory() + File.separator  + "yourFolder";
            // Create the folder.
            File folder = new File(path);
            if(!folder.exists()) {
                folder.mkdirs();
            }
            File file2 = new File(folder, "capture_image.txt");
         //Write Byte Array to Txt
            try {
                FileOutputStream fOut = new FileOutputStream(file2);
                OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
                myOutWriter.append(encoded);
                myOutWriter.close();
                fOut.flush();
                fOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }



         //Bitmap makes clear
            //bitmap.recycle();
/*         if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
                bitmap = null;
            }*/
        }else{

            finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] PResult) {

        switch (RC) {

            case RequestPermissionCode:

                if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {

                    //Toast.makeText(CaptureImage.this,"Permission Granted, Now your application can access CAMERA.", Toast.LENGTH_LONG).show();

                    intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

                    startActivityForResult(intent, 7);

                } else {

                    Toast.makeText(CaptureImage.this,"Permission Canceled, Now your application cannot access CAMERA.", Toast.LENGTH_LONG).show();

                }
                break;
        }
    }
   //Write Byte Array to file
/*    public void writeToFile(byte[] data, String fileName) throws IOException {
        FileOutputStream out = new FileOutputStream(fileName);
        out.write(data);
        out.close();
    }*/

    private void showToasty(String mesaj) {

        Toasty.error(getApplicationContext(), mesaj, Toast.LENGTH_SHORT, true).show();
    }

    public class DocumentADD extends AsyncTask<Void, Void, Boolean> {

        private final Integer workOrderID;
        private final Integer documentTypeID;
        private final Boolean active;
        private final String documentContent;
        private final String commentText;
        private final Integer insertUserID;
        private final Integer updateUserID;


        DocumentADD(Integer workOrderID,Integer documentTypeID,Boolean active,String documentContent,String commentText,Integer insertUserID,Integer updateUserID){

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

            progressDialog_document.setMessage("\tLoading...");
            progressDialog_document.setCancelable(false);
            progressDialog_document.show();
            progressDialog_document.setContentView(R.layout.custom_progress);
        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            String todoListUpdate_service = Server.DocumentAdd(workOrderID,documentTypeID,active,documentContent,commentText,insertUserID,updateUserID);
            if(!todoListUpdate_service.trim().equalsIgnoreCase("false")){

                try {

                    JSONObject jObject = new JSONObject(todoListUpdate_service);
                    get_mesaj_document = jObject.getString("Successful");
                    String get_mesaj_data = jObject.getString("Data");

                    Log.i("msjTodoListUpdate",get_mesaj_document);
                    Log.i("msjData",get_mesaj_data);

                } catch (JSONException e) {
                    Log.i("Exception: ",e.getMessage());

                }
            }

            else{
                get_mesaj_document = "false";
            }

            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            if(get_mesaj_document.equals("true")) {

                progressDialog_document.dismiss();

                new SweetAlertDialog(CaptureImage.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("İşlem Mesajı")
                        .setContentText("İşlem Başarılı,Resim Gönderilmiştir")
                        .setConfirmText("Tamam")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismissWithAnimation();

                                Intent go_home = new Intent(CaptureImage.this,MainBottomNavigation.class);
                                startActivity(go_home);
                            }
                        })
                        .show();

            }
            else{

                progressDialog_document.dismiss();

                Toasty.error(getApplicationContext(), "İşlem Başarısız,tekrar deneyiniz", Toast.LENGTH_SHORT, true).show();

            }

        }
    }



}
