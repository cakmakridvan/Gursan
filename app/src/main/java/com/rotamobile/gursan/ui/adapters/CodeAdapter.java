package com.rotamobile.gursan.ui.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rotamobile.gursan.R;
import com.rotamobile.gursan.model.devicehistory.ModelHistory;

import java.util.List;

public class CodeAdapter extends RecyclerView.Adapter<CodeAdapter.ViewHolder> {

    private List<ModelHistory> modelHistoryList;
    private ModelHistory modelHistory;
    private Context context;
    private ProgressDialog progressDialog;

    public CodeAdapter(List<ModelHistory> modelHistoryList, Context context){

        this.modelHistoryList = modelHistoryList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_code,viewGroup,false);
        //Progress Diaolog initialize
        progressDialog = new ProgressDialog(context);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressDialog.setIndeterminate(true);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        modelHistory = modelHistoryList.get(i);
        viewHolder.textViewDescCode.setText(modelHistory.getSubjectText());
        viewHolder.textViewHeadCode.setText(modelHistory.getDescription());
        viewHolder.textViewDate.setText(modelHistory.getInsertDateString());

    }

    @Override
    public int getItemCount() {
        return modelHistoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

    public TextView textViewHeadCode;
    public TextView textViewDescCode;
    public TextView textViewDate;
    public LinearLayout linearCode;

    public ViewHolder(View itemView) {
        super(itemView);

        textViewHeadCode = itemView.findViewById(R.id.txt_headCode);
        textViewDescCode = itemView.findViewById(R.id.txt_descCode);
        textViewDate = itemView.findViewById(R.id.txt_date);
        linearCode = itemView.findViewById(R.id.linearLayoutCode);
    }
}
}
