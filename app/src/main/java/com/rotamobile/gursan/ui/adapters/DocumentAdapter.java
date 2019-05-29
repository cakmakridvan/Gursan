package com.rotamobile.gursan.ui.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rotamobile.gursan.R;
import com.rotamobile.gursan.model.documentList.ModelDocument;

import java.util.List;

public class DocumentAdapter extends RecyclerView.Adapter<DocumentAdapter.ViewHolder>{

    private List<ModelDocument> modelDocumentList;
    private ModelDocument modelDocument;
    private Context context;

    public DocumentAdapter(List<ModelDocument> modelDocumentList, Context context){

        this.modelDocumentList = modelDocumentList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_document,viewGroup,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {

        modelDocument = modelDocumentList.get(i);
      //Decode image from Base64 to Bitmap
        byte[] decodeString = Base64.decode(modelDocument.getDocumentContent(),Base64.DEFAULT);
        final Bitmap decodeType = BitmapFactory.decodeByteArray(decodeString,0,decodeString.length);
        viewHolder.img_documen.setImageBitmap(decodeType);
        viewHolder.txt_comment.setText(modelDocument.getCommentText());
        viewHolder.txt_tarih.setText(modelDocument.getInsetDateDocumentString());

        viewHolder.lyt_document.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

                ImageView imageView = builder.findViewById(R.id.img_custom_document);
                imageView.setImageBitmap(decodeType);
                builder.show();


            }
        });

    }

    @Override
    public int getItemCount() {
        return modelDocumentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView img_documen;
        public TextView txt_comment,txt_tarih;
        public LinearLayout lyt_document;

        public ViewHolder(View itemView) {
            super(itemView);

            img_documen = itemView.findViewById(R.id.imgview_document);
            txt_comment = itemView.findViewById(R.id.txt_document_comment);
            txt_tarih = itemView.findViewById(R.id.document_list_tarih);
            lyt_document = itemView.findViewById(R.id.linearLayoutDocument);
        }
    }
}
