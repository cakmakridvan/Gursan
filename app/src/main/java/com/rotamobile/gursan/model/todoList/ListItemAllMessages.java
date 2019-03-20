package com.rotamobile.gursan.model.todoList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListItemAllMessages {

    @SerializedName("ProjectName")
    @Expose
    private String ProjectName;
    @SerializedName("SubjectText")
    @Expose
    private String SubjectText;
    @SerializedName("BuildingName")
    @Expose
    private String BuildingName;
    @SerializedName("DeviceName")
    @Expose
    private String DeviceName;
    @SerializedName("StartDate")
    @Expose
    private String StartDate;
    @SerializedName("EndDate")
    @Expose
    private String EndDate;
    @SerializedName("WorkUser")
    @Expose
    private String WorkUser;
    @SerializedName("TerritoryName")
    @Expose
    private String TerritoryName;
    @SerializedName("AreaName")
    @Expose
    private String AreaName;

    public ListItemAllMessages(String projectName, String subjectText, String buildingName, String deviceName, String startDate, String endDate, String workUser
            , String territoryName, String areaName) {
        ProjectName = projectName;
        SubjectText = subjectText;
        BuildingName = buildingName;
        DeviceName = deviceName;
        StartDate = startDate;
        EndDate = endDate;
        WorkUser = workUser;
        TerritoryName = territoryName;
        AreaName = areaName;
    }

    public String getBuildingName() {
        return BuildingName;
    }

    public void setBuildingName(String buildingName) {
        BuildingName = buildingName;
    }

    public String getDeviceName() {
        return DeviceName;
    }

    public void setDeviceName(String deviceName) {
        DeviceName = deviceName;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getWorkUser() {
        return WorkUser;
    }

    public void setWorkUser(String workUser) {
        WorkUser = workUser;
    }

    public String getTerritoryName() {
        return TerritoryName;
    }

    public void setTerritoryName(String territoryName) {
        TerritoryName = territoryName;
    }

    public String getAreaName() {
        return AreaName;
    }

    public void setAreaName(String areaName) {
        AreaName = areaName;
    }

    public String getProjectName() {
        return ProjectName;
    }

    public void setProjectName(String projectName) {
        ProjectName = projectName;
    }

    public String getSubjectText() {
        return SubjectText;
    }

    public void setSubjectText(String subjectText) {
        SubjectText = subjectText;
    }














}
