package com.rotamobile.gursan.ui.adapters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rotamobile.gursan.R;
import com.rotamobile.gursan.model.icServisTalepList.ModelIcServiTalep;
import com.rotamobile.gursan.ui.details.IcServisDetail;

import java.util.List;

public class IcServisAdapter extends RecyclerView.Adapter<IcServisAdapter.ViewHolder> {

    private List<ModelIcServiTalep> modelIcServisTalepList;
    private ModelIcServiTalep modelIcServiTalep;
    private Activity context;
    private ProgressDialog progressDialog;
    private int row_index = -1;

    public IcServisAdapter(List<ModelIcServiTalep> modelIcServisTalepList, Activity context){

        this.modelIcServisTalepList = modelIcServisTalepList;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ic_servis_talep_list,viewGroup,false);
        //Progress Diaolog initialize
        progressDialog = new ProgressDialog(context);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressDialog.setIndeterminate(true);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        modelIcServiTalep = modelIcServisTalepList.get(i);
        viewHolder.konu.setText(modelIcServiTalep.getProductName_ic());
        viewHolder.aciklama.setText(String.valueOf(modelIcServiTalep.getAmount_ic()));

        viewHolder.lyt_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                row_index = i;
                modelIcServiTalep = modelIcServisTalepList.get(i);
                notifyDataSetChanged();
                Intent go_detailIcServis = new Intent(context,IcServisDetail.class);
                go_detailIcServis.putExtra("ProductName",modelIcServiTalep.getProductName_ic());
                go_detailIcServis.putExtra("amount",modelIcServiTalep.getAmount_ic());
                go_detailIcServis.putExtra("id",modelIcServiTalep.getID_ic());
                context.startActivity(go_detailIcServis);

            }
        });

    }

    @Override
    public int getItemCount() {
        return modelIcServisTalepList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView konu;
        public TextView aciklama;
        public LinearLayout lyt_view;

        public ViewHolder(View itemView){

            super(itemView);

            konu = itemView.findViewById(R.id.txt_talep_ic_list_konu);
            aciklama = itemView.findViewById(R.id.txt_talep_ic_list_aciklama);
            lyt_view = itemView.findViewById(R.id.lyt_ic_servis_adapter);

        }

    }
}