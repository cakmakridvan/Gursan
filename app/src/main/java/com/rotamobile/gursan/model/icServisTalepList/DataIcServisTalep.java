package com.rotamobile.gursan.model.icServisTalepList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


import java.util.ArrayList;

public class DataIcServisTalep {

    @SerializedName("Data")
    @Expose
    private ArrayList<ModelIcServiTalep> data_list_ic;

    public ArrayList<ModelIcServiTalep> getData_list_ic() {
        return data_list_ic;
    }
}
