package com.rotamobile.gursan.model.productUnitSpinner;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DataProductUnit {

    @SerializedName("Data")
    @Expose
    private ArrayList<ModelProductUnit> data_list;

    public ArrayList<ModelProductUnit> getData_list() {
        return data_list;
    }
}
