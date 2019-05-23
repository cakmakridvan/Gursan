package com.rotamobile.gursan.model.icServisTalepList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelIcServiTalep {

    @SerializedName("ID")
    @Expose
    private Integer ID;
    @SerializedName("ProductID")
    @Expose
    private Integer ProductID;
    @SerializedName("Amount")
    @Expose
    private Integer Amount;
    @SerializedName("UnitPrice")
    @Expose
    private double UnitPrice;
    @SerializedName("ProductName")
    @Expose
    private String ProductName;
    @SerializedName("UnitName")
    @Expose
    private String UnitName;

    public ModelIcServiTalep(Integer ID, Integer productID, Integer amount, double unitPrice, String productName, String unitName) {
        this.ID = ID;
        ProductID = productID;
        Amount = amount;
        UnitPrice = unitPrice;
        ProductName = productName;
        UnitName = unitName;
    }

    public Integer getID_ic() {
        return ID;
    }

    public void setID_ic(Integer ID) {
        this.ID = ID;
    }

    public Integer getProductID_ic() {
        return ProductID;
    }

    public void setProductID_ic(Integer productID) {
        ProductID = productID;
    }

    public Integer getAmount_ic() {
        return Amount;
    }

    public void setAmount_ic(Integer amount) {
        Amount = amount;
    }

    public double getUnitPrice_ic() {
        return UnitPrice;
    }

    public void setUnitPrice_ic(double unitPrice) {
        UnitPrice = unitPrice;
    }

    public String getProductName_ic() {
        return ProductName;
    }

    public void setProductName_ic(String productName) {
        ProductName = productName;
    }

    public String getUnitName_ic() {
        return UnitName;
    }

    public void setUnitName_ic(String unitName) {
        UnitName = unitName;
    }






}
