package com.rotamobile.gursan.model.areaSpinner;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelArea {

    @SerializedName("AreaId")
    @Expose
    private Integer AreaId;
    @SerializedName("Name")
    @Expose
    private String Name;

    public Integer getAreaId() {
        return AreaId;
    }

    public void setAreaId(Integer areaId) {
        AreaId = areaId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }


}
