package com.rotamobile.gursan.model.homeItemClick;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ItemClickCheck extends RealmObject {

    @PrimaryKey
    private int id;
    private String workID;
    private boolean checable;

    public String getWorkID() {
        return workID;
    }

    public void setWorkID(String workID) {
        this.workID = workID;
    }

    public ItemClickCheck(){}

    public ItemClickCheck(String workID, boolean checable) {
        this.workID = workID;
        this.checable = checable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public boolean isChecable() {
        return checable;
    }

    public void setChecable(boolean checable) {
        this.checable = checable;
    }


}
