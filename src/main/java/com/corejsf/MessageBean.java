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
        ArrayList<String> target = userListBean.getSelectedUsers();
        if(target.isEmpty()){
            home.setJustRegistered(false);
            home.setMessageSent(false);
            return;
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        server.sendMessage(new MessageData(user.getUsername(), target,dtf.format(now),enteredMessage));

        enteredMessage = "";
        home.setJustRegistered(false);
        home.setMessageSent(true);
        userListBean.setSelectedUsers(null);
        home.hideNewMessage();
    }

    public String getEnteredMessage() {
        return enteredMessage;
    }

    public void setEnteredMessage(String enteredMessage) {
        this.enteredMessage = enteredMessage;
    }
}