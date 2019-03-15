package com.rotamobile.gursan.model.userSpinner;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DataUser {

    @SerializedName("Data")
    @Expose
    private ArrayList<ModelUser> data_list;

    public ArrayList<ModelUser> getData_list(){

        return data_list;
    }


}
