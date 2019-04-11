package com.rotamobile.gursan.model.workStatus;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelWorkStatus {

    @SerializedName("AssignedName")
    @Expose
    private String AssignedName;
    @SerializedName("AssignsName")
    @Expose
    private String AssignsName;
    @SerializedName("WorkOrderStatusID")
    @Expose
    private Integer WorkOrderStatusID;

    public String getAssignedName() {
        return AssignedName;
    }

    public void setAssignedName(String assignedName) {
        AssignedName = assignedName;
    }

    public String getAssignsName() {
        return AssignsName;
    }

    public void setAssignsName(String assignsName) {
        AssignsName = assignsName;
    }

    public Integer getWorkOrderStatusID() {
        return WorkOrderStatusID;
    }

    public void setWorkOrderStatusID(Integer workOrderStatusID) {
        WorkOrderStatusID = workOrderStatusID;
    }

    public ModelWorkStatus(String AssignedName,String AssignsName){

        this.AssignedName = AssignedName;
        this.AssignsName = AssignsName;

    }

}
