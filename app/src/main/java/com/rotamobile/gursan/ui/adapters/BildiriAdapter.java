package com.rotamobile.gursan.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rotamobile.gursan.Main;
import com.rotamobile.gursan.R;
import com.rotamobile.gursan.model.bildirim.BildirimModel;

import java.util.List;

public class BildiriAdapter extends RecyclerView.Adapter<BildiriAdapter.ViewHolder> {

    private List<BildirimModel> bildirimModelList;
    private BildirimModel bildirimModel;
    private Context context;


    public BildiriAdapter(List<BildirimModel> bildirimModelList, Context context){

        this.bildirimModelList = bildirimModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_bildiri,viewGroup,false);


        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        bildirimModel = bildirimModelList.get(i);
        viewHolder.subj.setText(bildirimModel.getSubjectText());
        viewHolder.txt.setText(bildirimModel.getText());

        viewHolder.linear_bildiri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent go_anaSayfa = new Intent(context,Main.class);
                context.startActivity(go_anaSayfa);
                ((Activity)context).finish();
            }
        });

    }

    @Override
    public int getItemCount() {
        return bildirimModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView subj;
        public TextView txt;
        public LinearLayout linear_bildiri;


        public ViewHolder(View itemView) {
            super(itemView);

            subj = itemView.findViewById(R.id.txt_bildiriSubject);
            txt = itemView.findViewById(R.id.txt_bildiriText);
            linear_bildiri = itemView.findViewById(R.id.linearLayoutBildiri);
        }
    }
}
