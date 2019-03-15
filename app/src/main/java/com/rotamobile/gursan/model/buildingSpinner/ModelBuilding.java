package com.rotamobile.gursan.model.buildingSpinner;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelBuilding {

    @SerializedName("BuildingId")
    @Expose
    private Integer BuildingId;
    @SerializedName("Name")
    @Expose
    private String Name;

    public Integer getBuildingId() {
        return BuildingId;
    }

    public void setBuildingId(Integer buildingId) {
        BuildingId = buildingId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }


}
