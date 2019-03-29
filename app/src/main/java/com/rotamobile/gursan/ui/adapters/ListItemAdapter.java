package com.rotamobile.gursan.ui.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rotamobile.gursan.R;
import com.rotamobile.gursan.model.todoList.ListItemAllMessages;
import com.rotamobile.gursan.ui.details.Details;
import com.rotamobile.gursan.ui.documents.CaptureImage;
import com.rotamobile.gursan.ui.documents.OpenGalery;

import java.util.ArrayList;
import java.util.List;

public class ListItemAdapter extends RecyclerView.Adapter<ListItemAdapter.ViewHolder> implements Filterable {

    private List<ListItemAllMessages> list_allmesaj;
    private List<ListItemAllMessages> list_allmesajFull;

    ListItemAllMessages listItemAllMessages;
    private Context context;
    private int row_index = -1;

    public ListItemAdapter(List<ListItemAllMessages> list_allmesaj, Context context) {
        this.list_allmesaj = list_allmesaj;
        list_allmesajFull = new ArrayList<>(list_allmesaj);
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
                goDetails.putExtra("auhorizate_update",listItemAllMessages.getAuthorizationUpdate());
                goDetails.putExtra("description_update",listItemAllMessages.getDescription());
                context.startActivity(goDetails);

            }
        });

        viewHolder.dot_icon.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {

                listItemAllMessages = list_allmesaj.get(position);
              //for Documents and matarial added
                PopupMenu popupMenu = new PopupMenu(context, viewHolder.dot_icon);
                popupMenu.inflate(R.menu.list_item_option_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()){

                            case R.id.menu_item_add_documen:
                                //Toast.makeText(context,listItemAllMessages.getProjectName(),Toast.LENGTH_SHORT).show();
                                Intent go_capture = new Intent(context,CaptureImage.class);
                                go_capture.putExtra("id",listItemAllMessages.getID());// WorkOrder ID
                                go_capture.putExtra("insert_user_id",listItemAllMessages.getInsertUserID());
                                context.startActivity(go_capture);
                                break;

                            case R.id.menu_item_add_mataria:
                                //Toast.makeText(context,listItemAllMessages.getSubjectText(),Toast.LENGTH_SHORT).show();

                                Intent go_galery = new Intent(context,OpenGalery.class);
                                go_galery.putExtra("id",listItemAllMessages.getID());// WorkOrder ID
                                go_galery.putExtra("insert_user_id",listItemAllMessages.getInsertUserID());
                                context.startActivity(go_galery);

                                break;


                        }

                        return false;
                    }
                });
                @SuppressLint("RestrictedApi") MenuPopupHelper menuHelper = new MenuPopupHelper(context, (MenuBuilder) popupMenu.getMenu(), viewHolder.dot_icon);
                menuHelper.setForceShowIcon(true);
                menuHelper.show();
                /*popupMenu.show();*/
            }
        });

/*        if(row_index == position){
            viewHolder.linear.setBackgroundColor(Color.parseColor("#6BE9F6"));

        }
        else
        {
            viewHolder.linear.setBackgroundColor(Color.parseColor("#ffffff"));

        }*/
    }

    @Override
    public int getItemCount() {
        return list_allmesaj.size();
    }

  //for SearchView in RecyclerView
    @Override
    public Filter getFilter() {
        return list_allmesaj_filter;
    }

    private Filter list_allmesaj_filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<ListItemAllMessages> filteredList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0){

                filteredList.addAll(list_allmesajFull);
            }else{

                String filterPattern = constraint.toString().toLowerCase().trim();

                for (ListItemAllMessages item : list_allmesajFull ){

                    if(item.getProjectName().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            list_allmesaj.clear();
            list_allmesaj.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewHead;
        public TextView textViewDesc;
        public ImageButton dot_icon;
        public LinearLayout linear;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewHead = itemView.findViewById(R.id.txt_head);
            textViewDesc = itemView.findViewById(R.id.txt_desc);
            dot_icon = itemView.findViewById(R.id.txt_option_item);
            linear = itemView.findViewById(R.id.linearLayout);
        }
    }
}
