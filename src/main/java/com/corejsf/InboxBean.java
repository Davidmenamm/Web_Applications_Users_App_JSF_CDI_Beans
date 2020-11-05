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
    public InboxBean() {
//        loadData();
        data = new ArrayList<>();
    }
    private void loadData() {
        this.data = server.getMessagesTo(userBean.getUsername());
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
//        String id = (String) e.getNewValue();
//        // identify the object index of the given event name
//        List<String> names = new ArrayList<>();
//        for (int i = 0; i < data.toArray().length; i++){
//            names.add(data.get(i).getName());
//        }
//        int wantedIdx = names.indexOf(id);
//        // get description for object at that idx
//        String desiredDesc = data.get(wantedIdx).getDesc();
//        setMessage(desiredDesc);
    }
    public void setMessage(MessageData s) {
        messageData = s;
    }
    public MessageData getMessage() {
        return messageData;
    }

    public void deleteMessage(){
        server.deleteMessage(messageData);
        loadData();
    }
}