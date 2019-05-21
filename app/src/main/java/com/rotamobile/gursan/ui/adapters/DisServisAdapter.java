package com.rotamobile.gursan.ui.adapters;

import android.app.ProgressDialog;
import android.content.Context;
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
import com.rotamobile.gursan.model.disServisTalepList.ModelDisServiTalep;
import com.rotamobile.gursan.ui.details.DisServisDetail;

import java.util.List;

public class DisServisAdapter extends RecyclerView.Adapter<DisServisAdapter.ViewHolder> {

    private List<ModelDisServiTalep> modelDisServisTalepList;
    private ModelDisServiTalep modelDisServiTalep;
    private Context context;
    private ProgressDialog progressDialog;
    private int row_index = -1;

    public DisServisAdapter(List<ModelDisServiTalep> modelDisServisTalepList, Context context){

        this.modelDisServisTalepList = modelDisServisTalepList;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dis_servis_talep_list,viewGroup,false);
        //Progress Diaolog initialize
        progressDialog = new ProgressDialog(context);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressDialog.setIndeterminate(true);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        modelDisServiTalep = modelDisServisTalepList.get(i);
        viewHolder.konu.setText(modelDisServiTalep.getProductAndService());
        viewHolder.aciklama.setText(modelDisServiTalep.getDescription());

        viewHolder.lyt_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

/*                row_index = i;
                modelDisServiTalep = modelDisServisTalepList.get(i);
                notifyDataSetChanged();
                Intent go_detailDisServis = new Intent(context,DisServisDetail.class);
                go_detailDisServis.putExtra("konu",modelDisServiTalep.getProductAndService());
                go_detailDisServis.putExtra("aciklama",modelDisServiTalep.getDescription());
                context.startActivity(go_detailDisServis);*/

            }
        });

    }

    @Override
    public int getItemCount() {
        return modelDisServisTalepList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView konu;
        public TextView aciklama;
        public LinearLayout lyt_view;

    public ViewHolder(View itemView){

        super(itemView);

        konu = itemView.findViewById(R.id.txt_talep_list_konu);
        aciklama = itemView.findViewById(R.id.txt_talep_list_aciklama);
        lyt_view = itemView.findViewById(R.id.lyt_dis_servis_adapter);

    }

    }
}
