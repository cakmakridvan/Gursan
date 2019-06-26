package com.rotamobile.gursan.model.commentGet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelComment {

    @SerializedName("CommentText")
    @Expose
    private String CommentText;
    @SerializedName("InsertDateString")
    @Expose
    private String InsertDateString;
    @SerializedName("AddUserName")
    @Expose
    private String AddUserName;
    @SerializedName("ID")
    @Expose
    private Integer ID;
    @SerializedName("DocumentExists")
    @Expose
    private boolean DocumentExists;

    public boolean isDocumentExists() {
        return DocumentExists;
    }

    public void setDocumentExists(boolean documentExists) {
        DocumentExists = documentExists;
    }

    public String getCommentText() {
        return CommentText;
    }

    public void setCommentText(String commentText) {
        CommentText = commentText;
    }

    public String getInsertDateString() {
        return InsertDateString;
    }

    public void setInsertDateString(String insertDateString) {
        InsertDateString = insertDateString;
    }

    public String getAddUserName() {
        return AddUserName;
    }

    public void setAddUserName(String addUserName) {
        AddUserName = addUserName;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public ModelComment(String commentText, String insertDateString, String addUserName, Integer id, boolean documentExists) {
        CommentText = commentText;
        InsertDateString = insertDateString;
        AddUserName = addUserName;
        ID = id;
        DocumentExists = documentExists;
    }


}
