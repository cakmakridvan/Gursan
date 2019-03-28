package com.rotamobile.gursan.ui.documents;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rotamobile.gursan.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class CaptureImage extends AppCompatActivity {

    ImageView imageView;
    public  static final int RequestPermissionCode  = 1 ;
    Intent intent ;
    String fileName = "";
    private Toolbar toolbar;
    private TextView toolbar_text;
    private ImageButton back_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_capture);

        imageView = findViewById(R.id.imageView);
        back_button = findViewById(R.id.capture_button);
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


        EnableRuntimePermission();

    }

    public void EnableRuntimePermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(CaptureImage.this,
                Manifest.permission.CAMERA))
        {

            Toast.makeText(CaptureImage.this,"CAMERA permission allows us to Access CAMERA app", Toast.LENGTH_LONG).show();



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
            String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
         //Create File
            fileName = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "byte.txt";
            String path = Environment.getExternalStorageDirectory() + File.separator  + "yourFolder";
            // Create the folder.
            File folder = new File(path);
            folder.mkdirs();
            File file2 = new File(folder, "config.txt");
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
        }
        else{

            finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] PResult) {

        switch (RC) {

            case RequestPermissionCode:

                if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(CaptureImage.this,"Permission Granted, Now your application can access CAMERA.", Toast.LENGTH_LONG).show();

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



}
