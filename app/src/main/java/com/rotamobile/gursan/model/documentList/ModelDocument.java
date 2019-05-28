package com.rotamobile.gursan.model.documentList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelDocument {

    @SerializedName("DocumentContent")
    @Expose
    private String DocumentContent;
    @SerializedName("CommentText")
    @Expose
    private String CommentText;

    public ModelDocument(String documentContent, String commentText) {
        DocumentContent = documentContent;
        CommentText = commentText;
    }

    public String getDocumentContent() {
        return DocumentContent;
    }

    public void setDocumentContent(String documentContent) {
        DocumentContent = documentContent;
    }

    public String getCommentText() {
        return CommentText;
    }

    public void setCommentText(String commentText) {
        CommentText = commentText;
    }
}
