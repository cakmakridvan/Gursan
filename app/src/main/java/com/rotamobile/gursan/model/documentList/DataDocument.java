package com.rotamobile.gursan.model.documentList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class DataDocument {

    @SerializedName("Data")
    @Expose
    private ArrayList<ModelDocument> data_list;

    public ArrayList<ModelDocument> getData_list() {
        return data_list;
    }
}
