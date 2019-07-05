package com.rotamobile.gursan.model.bildirim;

import com.rotamobile.gursan.ui.activity.Bildirimler;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class BildirimModel extends RealmObject {

    private String subjectText;
    private String insertTime;
    private String text;
    private Integer type;
    private Integer userId;
    private String workId;
    @PrimaryKey
    private int id;

    public BildirimModel(){}

    public BildirimModel(String insertTime, String subjectText, String text, Integer type, Integer userId, String workId) {
        this.subjectText = subjectText;
        this.insertTime = insertTime;
        this.text = text;
        this.type = type;
        this.userId = userId;
        this.workId = workId;
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getSubjectText() {
        return subjectText;
    }

    public void setSubjectText(String subjectText) {
        this.subjectText = subjectText;
    }

    public String getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(String insertTime) {
        this.insertTime = insertTime;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }


}
