package com.rotamobile.gursan.model.userSpinner;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelUser {

    @SerializedName("Name")
    @Expose
    private String Name;
    @SerializedName("Password")
    @Expose
    private String Password;
    @SerializedName("ID")
    @Expose
    private Integer ID;

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

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }







}
