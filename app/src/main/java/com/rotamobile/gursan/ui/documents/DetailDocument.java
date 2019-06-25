package com.rotamobile.gursan.ui.documents;

import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.rotamobile.gursan.R;
import com.rotamobile.gursan.data.Server;
import com.rotamobile.gursan.model.commentGet.DataComment;
import com.rotamobile.gursan.model.commentGet.ModelComment;
import com.rotamobile.gursan.model.documentList.DataDocument;
import com.rotamobile.gursan.model.documentList.ModelDocument;
import com.rotamobile.gursan.ui.adapters.DocumentAdapter;

import java.util.ArrayList;
import java.util.List;

public class DetailDocument extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView title;
    private ProgressDialog progressDialog;
    private RecyclerView recyclerView;
    private ImageButton back_btn;
    private TextView codeListe_bos;
    private List<ModelComment> list_document;

    private DataComment response_dataDocument;
    private ArrayList<ModelComment> document_list;
    private String get_mesaj_document_list;
    private DocumentAdapter document_adapter;
    private DocumentListTask documentListTask = null;
    private Bundle extras;
    private Integer getWorkOrderId = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_document);

        extras = getIntent().getExtras();
        if(extras != null){
            getWorkOrderId = extras.getInt("workOrder_id");
        }


        toolbar = findViewById(R.id.detail_document_toolbar_top);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.detail_document_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(DetailDocument.this));
        //icerik = findViewById(R.id.txt_icerik);
        title = findViewById(R.id.detail_document_toolbar_title);
        title.setText("Döküman Listeleme");
        codeListe_bos = findViewById(R.id.detail_document_empty_listcode);

        list_document = new ArrayList<>();

        //Progress Diaolog initialize
        progressDialog = new ProgressDialog(DetailDocument.this);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressDialog.setIndeterminate(true);

        documentListTask = new DocumentListTask(getWorkOrderId);
        documentListTask.execute((Void) null);

        back_btn = findViewById(R.id.detail_document_back_button);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public class DocumentListTask extends AsyncTask<Void, Void, Boolean> {

        private Integer code;

        DocumentListTask(Integer code){

            this.code = code;
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
                String codeResult = Server.CommentService(code);
                if(!codeResult.trim().equalsIgnoreCase("false")){

                    response_dataDocument = new Gson().fromJson(codeResult, DataComment.class);
                    document_list = response_dataDocument.getData_list();
                    Log.i("Tag:DeviceHistoryList",""+document_list);
                    get_mesaj_document_list = "true";
                }else{
                    get_mesaj_document_list = "false";
                }

            }catch(Exception e){

            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            if(!get_mesaj_document_list.equals("false")){
                progressDialog.dismiss();
                if(document_list.size() > 0){
                    for(int i=0; i<document_list.size(); i++){

                        ModelComment modeldocument = new ModelComment(document_list.get(i).getCommentText(),document_list.get(i).getInsertDateString(),document_list.get(i).getAddUserName(),document_list.get(i).getID());
                        list_document.add(modeldocument);
                    }
                    document_adapter = new DocumentAdapter(list_document,DetailDocument.this);
                    recyclerView.setAdapter(document_adapter);
                }else{
                    progressDialog.dismiss();
                    codeListe_bos.setVisibility(View.VISIBLE);
                }
            }else{

                progressDialog.dismiss();
                codeListe_bos.setVisibility(View.VISIBLE);
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();

            progressDialog.dismiss();
            document_adapter = null;

        }
    }
}
