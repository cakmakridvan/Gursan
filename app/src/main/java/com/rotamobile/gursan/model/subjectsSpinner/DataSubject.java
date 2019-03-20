package com.rotamobile.gursan.model.subjectsSpinner;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rotamobile.gursan.model.deviceSpinner.ModelDevice;

import java.util.ArrayList;

public class DataSubject {

    @SerializedName("Data")
    @Expose
    private ArrayList<ModelSubject> data_list;

    public ArrayList<ModelSubject> getData_list() {
        return data_list;
    }
}
