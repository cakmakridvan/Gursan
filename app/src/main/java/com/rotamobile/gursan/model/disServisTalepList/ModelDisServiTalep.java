package com.rotamobile.gursan.model.disServisTalepList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelDisServiTalep {


    @SerializedName("ID")
    @Expose
    private Integer ID;
    @SerializedName("ProductAndService")
    @Expose
    private String ProductAndService;
    @SerializedName("Description")
    @Expose
    private String Description;
    @SerializedName("Amount")
    @Expose
    private Integer Amount;
    @SerializedName("UnitName")
    @Expose
    private String UnitName;

    public String getUnitName() {
        return UnitName;
    }

    public void setUnitName(String unitName) {
        UnitName = unitName;
    }

    public Integer getAmount() {
        return Amount;
    }

    public void setAmount(Integer amount) {
        Amount = amount;
    }

    public ModelDisServiTalep(Integer ID,String productAndService, String description, Integer amount, String unitName) {
        this.ID = ID;
        ProductAndService = productAndService;
        Description = description;
        Amount = amount;
        UnitName = unitName;
    }

    public String getProductAndService() {
        return ProductAndService;
    }

    public void setProductAndService(String productAndService) {
        ProductAndService = productAndService;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }




}
