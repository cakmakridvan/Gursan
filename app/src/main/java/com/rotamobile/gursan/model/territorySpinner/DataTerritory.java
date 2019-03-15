package com.rotamobile.gursan.model.territorySpinner;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DataTerritory {

    @SerializedName("Data")
    @Expose
    private ArrayList<ModelTerritory> data_list;

    public ArrayList<ModelTerritory> getData_list() {
        return data_list;
    }
}
