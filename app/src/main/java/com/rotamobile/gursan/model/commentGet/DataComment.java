package com.rotamobile.gursan.model.commentGet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DataComment {

    @SerializedName("Data")
    @Expose
    private ArrayList<ModelComment> data_list;

    public ArrayList<ModelComment> getData_list() {
        return data_list;
    }
}
