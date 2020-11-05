package com.corejsf;

import javax.enterprise.context.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SessionScoped
@Named("inboxBean")
public class InboxBean implements Serializable {
    private List<MessageData> data;
    private int todo; // the currently selected item value
    private MessageData messageData;
    @Inject
    private ServerBean server;
    @Inject
    private UserBean userBean;
    @Inject
    private HomeBean home;

    public void loadData() {
        this.data = server.getMessagesTo(userBean.getUsername());
        showOne();
        userBean.acceptNotification();
        home.hideNewMessage();
    }

    private void showOne(){
        if(data.isEmpty()){
            this.messageData = new MessageData("Sistema",new ArrayList<>(),"","No tienes mensajes.");
        } else {
            MessageData t = data.get(0);
            setTodo(0);
            setMessage(t);
        }
    }
    public List<MessageData> getData() {
        return data;
    }
    public int getTodo() {
        return todo;
    }
    public void setTodo(int t) {
        todo = t;
    }
    // value change listener for list item selection
    public void valueChanged(ValueChangeEvent e) {
        int id = (int) e.getNewValue();
        setMessage(data.get(id));
    }
    public void setMessage(MessageData s) {
        messageData = s;
        server.markAsRead(s);
        messageData.markRead();
    }
    public MessageData getMessage() {
        return messageData;
    }

    public void deleteMessage(){
        server.deleteMessage(messageData);
        data.remove(messageData);
        showOne();
    }
}