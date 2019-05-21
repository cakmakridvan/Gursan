package com.rotamobile.gursan.model.disServisTalepList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DataDisServisTalep {

    @SerializedName("Data")
    @Expose
    private ArrayList<ModelDisServiTalep> data_list;

    public ArrayList<ModelDisServiTalep> getData_list() {
        return data_list;
    }

}
