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
    @SerializedName("InsetDateDocumentString")
    @Expose
    private String InsetDateDocumentString;

    public ModelDocument(String documentContent, String commentText, String insetDateDocumentString) {
        DocumentContent = documentContent;
        CommentText = commentText;
        InsetDateDocumentString = insetDateDocumentString;
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

    public String getInsetDateDocumentString() {
        return InsetDateDocumentString;
    }

    public void setInsetDateDocumentString(String insetDateDocumentString) {
        InsetDateDocumentString = insetDateDocumentString;
    }

}
