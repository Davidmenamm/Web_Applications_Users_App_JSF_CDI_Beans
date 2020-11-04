package com.corejsf;

import java.util.ArrayList;

public class MessageData {
    private final String sender;
    private String target;
    private final String displayfullTarget;
    private final String postDate;
    private final String message;
    private final ArrayList<String> fullTarget;
    private boolean read;

    public MessageData(String source, ArrayList<String> ends, String d, String data){
        this.sender = source;
        this.fullTarget = ends;
        this.displayfullTarget = String.join("; ", ends);
        this.message = data;
        this.postDate = d;
        this.read = false;
    }

    public MessageData(MessageData msg){
        this.sender = msg.sender;
        this.fullTarget = msg.fullTarget;
        this.displayfullTarget = msg.displayfullTarget;
        this.message = msg.message;
        this.postDate = msg.postDate;
        this.read = msg.read;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public ArrayList<String> getFullTarget() {
        return fullTarget;
    }

    public String getTarget(){
        return target;
    }

    public boolean isRead(){
        return read;
    }

    public void markRead(){
        this.read = true;
    }
}
