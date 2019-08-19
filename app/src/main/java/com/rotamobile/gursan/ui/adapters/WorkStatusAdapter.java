package com.rotamobile.gursan.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rotamobile.gursan.R;
import com.rotamobile.gursan.model.workStatus.ModelWorkStatus;

import java.util.List;

public class WorkStatusAdapter extends RecyclerView.Adapter<WorkStatusAdapter.ViewHolder> {

    private List<ModelWorkStatus> list_workStatus;
    private ModelWorkStatus modelWorkStatus;
    private Context context;

    public WorkStatusAdapter(List<ModelWorkStatus> list_workStatus,Context context){

        this.list_workStatus = list_workStatus;
        this.context = context;

    }

    @NonNull
    @Override
    public WorkStatusAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.work_status_item,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        modelWorkStatus = list_workStatus.get(i);

        viewHolder.assigned.setText(modelWorkStatus.getAssignsName());
        viewHolder.assigns.setText(modelWorkStatus.getAssignedName());
    }

    @Override
    public int getItemCount() {
        return list_workStatus.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView assigned;
        public TextView assigns;

        public ViewHolder(View itemView){

            super(itemView);
            assigned = itemView.findViewById(R.id.txt_assignedName);
            assigns = itemView.findViewById(R.id.txt_assignsName);

        }

    }
}
