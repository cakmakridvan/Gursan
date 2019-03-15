package com.rotamobile.gursan.model.areaSpinner;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rotamobile.gursan.model.projectSpinner.ModelProject;

import java.util.ArrayList;

public class DataArea {

    @SerializedName("Data")
    @Expose
    private ArrayList<ModelArea> data_list;

    public ArrayList<ModelArea> getData_list() {
        return data_list;
    }
}
