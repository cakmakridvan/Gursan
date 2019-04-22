package com.rotamobile.gursan.model.devicehistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelHistory {

    @SerializedName("SubjectText")
    @Expose
    private String SubjectText;
    @SerializedName("Description")
    @Expose
    private String Description;
    @SerializedName("InsetDateString")
    @Expose
    private String InsetDateString;

    public ModelHistory(String SubjectText, String Description, String InsetDateString){

        this.SubjectText = SubjectText;
        this.Description = Description;
        this.InsetDateString = InsetDateString;
    }

    public String getSubjectText() {
        return SubjectText;
    }

    public void setSubjectText(String subjectText) {
        SubjectText = subjectText;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getInsertDateString() {
        return InsetDateString;
    }

    public void setInsertDateString(String insertDate) {
        InsetDateString = insertDate;
    }

}
