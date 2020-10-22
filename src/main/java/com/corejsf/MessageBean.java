package com.corejsf;

import javax.enterprise.context.SessionScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

@Named("message_rw")
@ViewScoped
public class MessageBean implements Serializable {
    @Inject
    private ServerBean server;
    @Inject
    private UserBean user;
    @Inject
    private UserListBean userListBean;

    private String enteredMessage;

    public void postNewMessage(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        server.sendMessage(new String[]{user.getUsername(),userListBean.getSelectedUser(),dtf.format(now),enteredMessage});
    }

    public ArrayList<String[]> getMyMessages(){
        return server.getMessagesTo(user.getUsername());
    }

    public void deleteMyMessage(String[] myMessage){
        server.deleteMessage(myMessage);
    }

    public String getEnteredMessage() {
        return enteredMessage;
    }

    public void setEnteredMessage(String enteredMessage) {
        this.enteredMessage = enteredMessage;
    }
}