package com.rotamobile.gursan.model.projectSpinner;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DataProject {

    @SerializedName("Data")
    @Expose
    private ArrayList<ModelProject> data_list;

    public ArrayList<ModelProject> getData_list() {
        return data_list;
    }


}
