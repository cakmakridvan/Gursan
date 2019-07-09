package com.rotamobile.gursan.model.eventBus;

public class MessageEvent {

    private int counter = 0;

    public MessageEvent(int counter){
        this.counter = counter;
    }

    public int getCounter() {
        return counter;
    }




}
