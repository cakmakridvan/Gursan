package com.rotamobile.gursan.model.territorySpinner;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelTerritory {

    @SerializedName("ID")
    @Expose
    private Integer ID;
    @SerializedName("Name")
    @Expose
    private String Name;


    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }


}
