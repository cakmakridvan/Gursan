package com.rotamobile.gursan.model.definedJobSpinner;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DataDefinedJob {

    @SerializedName("Data")
    @Expose
    private ArrayList<ModelDefinedJob> data_list;

    public ArrayList<ModelDefinedJob> getData_list(){

        return  data_list;
    }
}
