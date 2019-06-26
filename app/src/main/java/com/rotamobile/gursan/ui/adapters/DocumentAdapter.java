package com.rotamobile.gursan.ui.adapters;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rotamobile.gursan.R;
import com.rotamobile.gursan.data.Server;
import com.rotamobile.gursan.model.commentGet.ModelComment;
import com.rotamobile.gursan.model.documentList.DataDocument;
import com.rotamobile.gursan.model.documentList.ModelDocument;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DocumentAdapter extends RecyclerView.Adapter<DocumentAdapter.ViewHolder>{

    private List<ModelComment> modelCommentList;
    private ModelComment modelComment;
    private DataDocument responce_dataDocument;
    private ArrayList<ModelDocument> document_lst;
    private Context context;
    private ProgressDialog progressDialog;
    private DocumentService documentService = null;
    private List<ModelDocument> list_documan;
    private String imageData = "";

    public DocumentAdapter(List<ModelComment> modelCommentList, Context context){

        this.modelCommentList = modelCommentList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_document,viewGroup,false);

        //Progress Diaolog initialize
        progressDialog = new ProgressDialog(context);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressDialog.setIndeterminate(true);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {

        modelComment = modelCommentList.get(i);
/*      //Decode image from Base64 to Bitmap
        byte[] decodeString = Base64.decode(modelComment.getDocumentContent(),Base64.DEFAULT);
        final Bitmap decodeType = BitmapFactory.decodeByteArray(decodeString,0,decodeString.length);
        viewHolder.img_documen.setImageBitmap(decodeType);*/
        viewHolder.txt_comment.setText(modelComment.getCommentText());
        viewHolder.txt_tarih.setText(modelComment.getInsertDateString());
        viewHolder.txt_created.setText(modelComment.getAddUserName());

        if(modelComment.isDocumentExists() == true){
            viewHolder.img_documen.setImageResource(R.drawable.imagelist);
        }else{
            viewHolder.img_documen.setImageResource(R.drawable.no_image);
        }


        viewHolder.lyt_document.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // get position
                int pos = viewHolder.getAdapterPosition();
                modelComment = modelCommentList.get(pos);

                documentService = new DocumentService(modelComment.getID());
                documentService.execute((Void) null);

            }
        });

    }

    @Override
    public int getItemCount() {
        return modelCommentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView img_documen;
        public TextView txt_comment,txt_tarih,txt_created;
        public LinearLayout lyt_document;

        public ViewHolder(View itemView) {
            super(itemView);

            img_documen = itemView.findViewById(R.id.imgview_document);
            txt_comment = itemView.findViewById(R.id.txt_document_comment);
            txt_tarih = itemView.findViewById(R.id.document_list_tarih);
            txt_created = itemView.findViewById(R.id.txt_document_created);
            lyt_document = itemView.findViewById(R.id.linearLayoutDocument);
        }
    }

    public class DocumentService extends AsyncTask<Void, Void, Boolean> {

        private final Integer id;
        String get_mesaj = "";

        DocumentService(Integer id){

            this.id = id;

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
                String getWorkStatusAdd_service = Server.DocumentSingleGet(id);
                if(!getWorkStatusAdd_service.trim().equalsIgnoreCase("false")){

                    JSONObject jsonObject = new JSONObject(getWorkStatusAdd_service);
                    JSONObject jsonObject1 = jsonObject.getJSONObject("Data");
                    if(jsonObject1 == null){
                        imageData ="";
                    }else {
                        imageData = jsonObject1.getString("DocumentContent");
                    }

                    String get_Jsom = jsonObject.getString("Successful");

/*                   responce_dataDocument = new Gson().fromJson(getWorkStatusAdd_service,DataDocument.class);
                   document_lst = responce_dataDocument.getData_list();*/
                   Log.i("Tag:document_lst",""+document_lst);
                    get_mesaj = "true";
                }else{
                    get_mesaj = "false";
                }

            } catch (Exception e) {

            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            if(!get_mesaj.equals("false")) {
                progressDialog.dismiss();

                Dialog builder = new Dialog(context);
                builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
                builder.setContentView(R.layout.custom_document_detail);
                builder.getWindow().setBackgroundDrawable(
                        new ColorDrawable(android.graphics.Color.TRANSPARENT));
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        //nothing;
                    }
                });

             //Convert Base64 image to Bitmap
                //imageData.substring(22): for removing data:image/png;base64, in Base64 String
             if(!imageData.equals("")) {
                 byte[] decodedString = Base64.decode(imageData.substring(22), Base64.DEFAULT);
                 Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                 ImageView imageView = builder.findViewById(R.id.img_custom_document);
                 imageView.setImageBitmap(decodedByte);
                 builder.show();
             }


            }else{

                progressDialog.dismiss();
                Toast.makeText(context,"Başarısız",Toast.LENGTH_LONG).show();
            }

        }

        @Override
        protected void onCancelled() {
            super.onCancelled();

            documentService = null;
            progressDialog.dismiss();
        }
    }
}
