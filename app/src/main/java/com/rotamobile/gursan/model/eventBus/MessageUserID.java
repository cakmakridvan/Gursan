package com.rotamobile.gursan.model.eventBus;

public class MessageUserID {

    private String user_id = "";

    public MessageUserID(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_id() {
        return user_id;
    }
}
