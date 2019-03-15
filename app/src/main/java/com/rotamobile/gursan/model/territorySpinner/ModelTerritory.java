package com.rotamobile.gursan.model.territorySpinner;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelTerritory {

    @SerializedName("TerritoryId")
    @Expose
    private Integer TerritoryId;
    @SerializedName("Name")
    @Expose
    private String Name;


    public Integer getTerritoryId() {
        return TerritoryId;
    }

    public void setTerritoryId(Integer territoryId) {
        TerritoryId = territoryId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }


}
