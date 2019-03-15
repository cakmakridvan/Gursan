package com.rotamobile.gursan.model.buildingSpinner;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DataBuilding {

    @SerializedName("Data")
    @Expose
    private ArrayList<ModelBuilding> data_list;

    public ArrayList<ModelBuilding> getData_list() {
        return data_list;
    }
}
