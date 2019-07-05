package com.rotamobile.gursan.model.deviceId;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelDeviceId {

    @SerializedName("Successful")
    @Expose
    private String Successful;

    public String getSuccessful() {
        return Successful;
    }

    public void setSuccessful(String successful) {
        Successful = successful;
    }

}
