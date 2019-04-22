package com.rotamobile.gursan.model.devicehistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DataHistory {

    @SerializedName("Data")
    @Expose
    private ArrayList<ModelHistory> data_list;

    public ArrayList<ModelHistory> getData_list() {
        return data_list;
    }
}
