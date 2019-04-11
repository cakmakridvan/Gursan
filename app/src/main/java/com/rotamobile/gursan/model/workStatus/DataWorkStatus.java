package com.rotamobile.gursan.model.workStatus;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DataWorkStatus {

    @SerializedName("Data")
    @Expose
    private ArrayList<ModelWorkStatus> data_list;

    public ArrayList<ModelWorkStatus> getData_list() {
        return data_list;
    }


}
