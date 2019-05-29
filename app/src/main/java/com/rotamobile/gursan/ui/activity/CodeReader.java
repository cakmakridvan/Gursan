package com.rotamobile.gursan.ui.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.rotamobile.gursan.Main;
import com.rotamobile.gursan.R;
import com.rotamobile.gursan.data.Server;
import com.rotamobile.gursan.model.devicehistory.DataHistory;
import com.rotamobile.gursan.model.devicehistory.ModelHistory;
import com.rotamobile.gursan.ui.adapters.CodeAdapter;

import java.util.ArrayList;
import java.util.List;

import static android.media.MediaRecorder.VideoSource.CAMERA;

public class CodeReader extends AppCompatActivity {

    //private TextView icerik;
    private Toolbar toolbar;
    private TextView title;
    private ImageButton back_btn;
    private ProgressDialog progressDialog;

    private DataHistory response_dataHistory;
    private ArrayList<ModelHistory> history_list;
    private String get_mesaj_history_list;

    private CodeAdapter code_adapter;
    private List<ModelHistory> list_history;
    private RecyclerView recyclerView;

    private TextView codeListe_bos;
    private CodeReaderTask codeReaderTask = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.code_reader);

        toolbar = findViewById(R.id.toolbar_top);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recycler_codeReader);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(CodeReader.this));
        //icerik = findViewById(R.id.txt_icerik);
        title = findViewById(R.id.toolbar_title);
        title.setText("QR Code Okuyucu");
        codeListe_bos = findViewById(R.id.empty_listcode);

        list_history = new ArrayList<>();

        //Progress Diaolog initialize
        progressDialog = new ProgressDialog(CodeReader.this);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressDialog.setIndeterminate(true);

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
                //Toast.makeText(this,result.getContents(),Toast.LENGTH_LONG).show();

             //CodeReader Service Running
                codeReaderTask = new CodeReaderTask(Integer.parseInt(result.getContents()));
                codeReaderTask.execute((Void) null);
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

    public class CodeReaderTask extends AsyncTask<Void, Void, Boolean>{

        private Integer code;

        CodeReaderTask(Integer code){

            this.code = code;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

/*            progressDialog.setMessage("\tLoading...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            progressDialog.setContentView(R.layout.custom_progress);*/
        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            try {
                String codeResult = Server.GetHistoryDevice(code);
                if(!codeResult.trim().equalsIgnoreCase("false")){

                    response_dataHistory = new Gson().fromJson(codeResult, DataHistory.class);
                    history_list = response_dataHistory.getData_list();
                    Log.i("Tag:DeviceHistoryList",""+history_list);
                    get_mesaj_history_list = "true";
                }else{
                    get_mesaj_history_list = "false";
                }

            }catch(Exception e){

            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            if(!get_mesaj_history_list.equals("false")){

              //progressDialog.dismiss();
             if(history_list.size() > 0){

             for(int i=0; i<history_list.size(); i++){

                ModelHistory modelHistory = new ModelHistory(history_list.get(i).getSubjectText(),history_list.get(i).getDescription(),history_list.get(i).getInsertDateString());
                list_history.add(modelHistory);
              }
                 code_adapter = new CodeAdapter(list_history,CodeReader.this);
                 recyclerView.setAdapter(code_adapter);
             }else{
                 //progressDialog.dismiss();
                 codeListe_bos.setVisibility(View.VISIBLE);
                 }
            }else{

                //progressDialog.dismiss();
                codeListe_bos.setVisibility(View.VISIBLE);
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();

            codeReaderTask = null;

        }
    }

}
