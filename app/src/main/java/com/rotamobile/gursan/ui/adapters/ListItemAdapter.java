package com.rotamobile.gursan.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rotamobile.gursan.R;
import com.rotamobile.gursan.model.todoList.ListItemAllMessages;
import com.rotamobile.gursan.ui.details.Details;

import java.util.List;

public class ListItemAdapter extends RecyclerView.Adapter<ListItemAdapter.ViewHolder> {

    List<ListItemAllMessages> list_allmesaj;
    ListItemAllMessages listItemAllMessages;
    private Context context;
    private int row_index = -1;

    public ListItemAdapter(List<ListItemAllMessages> list_allmesaj, Context context) {
        this.list_allmesaj = list_allmesaj;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_allmesaj,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

        listItemAllMessages = list_allmesaj.get(position);

        viewHolder.textViewHead.setText(listItemAllMessages.getProjectName());
        viewHolder.textViewDesc.setText(listItemAllMessages.getSubjectText());

        viewHolder.linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                row_index = position;
                listItemAllMessages = list_allmesaj.get(position);
                notifyDataSetChanged();
                Intent goDetails = new Intent(context,Details.class);
                goDetails.putExtra("id",listItemAllMessages.getID());// WorkOrder ID
                goDetails.putExtra("proje_name",listItemAllMessages.getProjectName());
                goDetails.putExtra("teritory_name",listItemAllMessages.getTerritoryName());
                goDetails.putExtra("building_name",listItemAllMessages.getBuildingName());
                goDetails.putExtra("area_name",listItemAllMessages.getAreaName());
                goDetails.putExtra("subject_name",listItemAllMessages.getSubjectText());
                goDetails.putExtra("device_name",listItemAllMessages.getDeviceName());
                goDetails.putExtra("start_date",listItemAllMessages.getStartDate());
                goDetails.putExtra("end_date",listItemAllMessages.getEndDate());
                goDetails.putExtra("work_user",listItemAllMessages.getWorkUser());
                goDetails.putExtra("proje_id",listItemAllMessages.getProjectID());
                goDetails.putExtra("teritory_id",listItemAllMessages.getTerritoryID());
                goDetails.putExtra("building_id",listItemAllMessages.getBuildingID());
                goDetails.putExtra("area_id",listItemAllMessages.getAreaID());
                goDetails.putExtra("device_id",listItemAllMessages.getDeviceID());
                goDetails.putExtra("subject_id",listItemAllMessages.getSubjectID());
                goDetails.putExtra("insert_user_id",listItemAllMessages.getInsertUserID());
                goDetails.putExtra("assigned_user_id",listItemAllMessages.getAssignedUserID());
                context.startActivity(goDetails);

            }
        });

        if(row_index == position){
            viewHolder.linear.setBackgroundColor(Color.parseColor("#6BE9F6"));

        }
        else
        {
            viewHolder.linear.setBackgroundColor(Color.parseColor("#ffffff"));

        }
    }

    @Override
    public int getItemCount() {
        return list_allmesaj.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewHead;
        public TextView textViewDesc;
        public LinearLayout linear;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewHead = itemView.findViewById(R.id.txt_head);
            textViewDesc = itemView.findViewById(R.id.txt_desc);
            linear = itemView.findViewById(R.id.linearLayout);
        }
    }
}
