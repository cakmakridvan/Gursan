package com.rotamobile.gursan.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.rotamobile.gursan.Main;
import com.rotamobile.gursan.R;

import static android.media.MediaRecorder.VideoSource.CAMERA;

public class CodeReader extends AppCompatActivity {

    private TextView icerik;
    private Toolbar toolbar;
    private TextView title;
    private ImageButton back_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.code_reader);

        toolbar = findViewById(R.id.toolbar_top);
        setSupportActionBar(toolbar);

        icerik = findViewById(R.id.txt_icerik);
        title = findViewById(R.id.toolbar_title);
        title.setText("QR Code Okuyucu");

        back_btn = findViewById(R.id.back_button);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        askForPermission(Manifest.permission.CAMERA,CAMERA);
    }

    private void askForPermission(String permission, Integer requestCode) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (this.checkSelfPermission(Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA},
                        CAMERA);
            }else{

                startingScanner();
            }
        }else{

            startingScanner();
        }

    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();

                startingScanner();

            } else {

                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();

            }

        }}//end onRequestPermissionsResult

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        final IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if (result != null){
            if(result.getContents() == null){
                Toast.makeText(this,"Kod Okuma İptal Edildi",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this,result.getContents(),Toast.LENGTH_LONG).show();
                icerik.setText(result.getContents());
            }
        }
        else{
            super.onActivityResult(requestCode,resultCode,data);
        }

    }


    public void startingScanner(){

        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("Kodu Okutunuz");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(false);
        integrator.initiateScan();
    }

/*    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent go_main = new Intent(getApplicationContext(),Main.class);
        go_main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        go_main.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(go_main);
    }*/

}
