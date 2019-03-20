package com.rotamobile.gursan.model.todoList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rotamobile.gursan.model.deviceSpinner.ModelDevice;

import java.util.ArrayList;

public class DataList {

    @SerializedName("Data")
    @Expose
    private ArrayList<ListItemAllMessages> data_list;

    public ArrayList<ListItemAllMessages> getData_list() {
        return data_list;
    }
}
