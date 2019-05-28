package com.rotamobile.gursan.ui.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rotamobile.gursan.R;
import com.rotamobile.gursan.model.documentList.ModelDocument;

import java.util.List;

public class DocumentAdapter extends RecyclerView.Adapter<DocumentAdapter.ViewHolder>{

    private List<ModelDocument> modelDocumentList;
    private ModelDocument modelDocument;
    private Context context;

    public DocumentAdapter(List<ModelDocument> modelDocument, Context context){

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
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        modelDocument = modelDocumentList.get(i);
        byte[] decodeString = Base64.decode(modelDocument.getDocumentContent(),Base64.DEFAULT);
        Bitmap decodeType = BitmapFactory.decodeByteArray(decodeString,0,decodeString.length);
        viewHolder.img_documen.setImageBitmap(decodeType);
        viewHolder.txt_comment.setText(modelDocument.getCommentText());

    }

    @Override
    public int getItemCount() {
        return modelDocumentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView img_documen;
        public TextView txt_comment;


        public ViewHolder(View itemView) {
            super(itemView);

            img_documen = itemView.findViewById(R.id.imgview_document);
            txt_comment = itemView.findViewById(R.id.imgview_document);
        }
    }
}
