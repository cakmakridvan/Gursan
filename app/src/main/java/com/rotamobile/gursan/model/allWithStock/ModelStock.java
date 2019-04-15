package com.rotamobile.gursan.model.allWithStock;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelStock {

    @SerializedName("ID")
    @Expose
    private Integer ID;
    @SerializedName("Name")
    @Expose
    private String Name;
    @SerializedName("Code")
    @Expose
    private String Code;
    @SerializedName("Amount")
    @Expose
    private Integer Amount;

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

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public Integer getAmount() {
        return Amount;
    }

    public void setAmount(Integer amount) {
        Amount = amount;
    }


}
