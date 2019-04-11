package com.rotamobile.gursan.model.userTypeWithProject;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DataUserType {

    @SerializedName("Data")
    @Expose
    private ArrayList<ModelUserType> data_list;

    public ArrayList<ModelUserType> getData_list() {
        return data_list;
    }
}
