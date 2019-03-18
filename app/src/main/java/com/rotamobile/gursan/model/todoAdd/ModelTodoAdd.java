package com.rotamobile.gursan.model.todoAdd;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelTodoAdd {

    @SerializedName("Data")
    @Expose
    private Boolean data_deger;
    @SerializedName("Successful")
    @Expose
    private Boolean success_mesaj;
}
