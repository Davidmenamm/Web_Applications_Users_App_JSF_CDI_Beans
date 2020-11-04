package com.corejsf;

import javax.enterprise.context.SessionScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

@Named("writeMessage")
@SessionScoped
public class MessageBean implements Serializable {
    @Inject
    private ServerBean server;
    @Inject
    private UserBean user;
    @Inject
    private UserListBean userListBean;
    @Inject
    private HomeBean home;

    private String enteredMessage;

    public void postNewMessage(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        ArrayList<String> target = userListBean.getSelectedUsers();
        server.sendMessage(new MessageData(user.getUsername(), target,dtf.format(now),enteredMessage));

        enteredMessage = "";
        home.setJustRegistered(false);
        home.setMessageSent(true);
    }

    public ArrayList<MessageData> getMyMessages(){
        return server.getMessagesTo(user.getUsername());
    }

    public void deleteMyMessage(MessageData myMessage){
        server.deleteMessage(myMessage);
    }

    public String getEnteredMessage() {
        return enteredMessage;
    }

    public void setEnteredMessage(String enteredMessage) {
        this.enteredMessage = enteredMessage;
    }
}