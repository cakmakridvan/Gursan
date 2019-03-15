
package com.rotamobile.gursan.model.projectSpinner;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelProject {


    @SerializedName("ProjectId")
    @Expose
    private Integer ProjectId;
    @SerializedName("Name")
    @Expose
    private String Name;

    public Integer getProjectId() {
        return ProjectId;
    }

    public void setProjectId(Integer projectId) {
        ProjectId = projectId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }



}
