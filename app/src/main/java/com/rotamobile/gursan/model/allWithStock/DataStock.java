package com.rotamobile.gursan.model.allWithStock;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DataStock {

    @SerializedName("Data")
    @Expose
    ArrayList<ModelStock> data_list;

    public ArrayList<ModelStock> getData_list() {
        return data_list;
    }

}
