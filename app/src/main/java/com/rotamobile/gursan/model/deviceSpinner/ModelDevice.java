package com.rotamobile.gursan.model.deviceSpinner;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelDevice {

    @SerializedName("DeviceId")
    @Expose
    private Integer DeviceId;
    @SerializedName("Name")
    @Expose
    private String Name;

    public Integer getDeviceId() {
        return DeviceId;
    }

    public void setDeviceId(Integer deviceId) {
        DeviceId = deviceId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }


}
