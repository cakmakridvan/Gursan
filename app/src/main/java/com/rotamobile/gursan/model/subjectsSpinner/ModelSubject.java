package com.rotamobile.gursan.model.subjectsSpinner;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelSubject {

    @SerializedName("ID")
    @Expose
    private Integer ID;
    @SerializedName("SubjectText")
    @Expose
    private String SubjectText;

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getSubjectText() {
        return SubjectText;
    }

    public void setSubjectText(String subjectText) {
        SubjectText = subjectText;
    }

}
