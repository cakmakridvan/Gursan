package com.rotamobile.gursan.model.deviceSpinner;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DataDevice {

    @SerializedName("Data")
    @Expose
    private ArrayList<ModelDevice> data_list;

    public ArrayList<ModelDevice> getData_list() {
        return data_list;
    }
}
