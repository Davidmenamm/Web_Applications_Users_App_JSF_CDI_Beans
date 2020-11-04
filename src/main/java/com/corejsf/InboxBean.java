package com.corejsf;

import javax.enterprise.context.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SessionScoped
@Named("inboxBean")
public class InboxBean implements Serializable {
    private List<Inbox> data; // list of data
    private String todo = "test"; // the currently selected item value
    private String msg; // status message
    public InboxBean() {
        System.out.println("TODOS BEAN");
        loadData();
        // select the first item in the list
        Inbox t = data.get(0);
        setTodo(t.getName());
        setMessage(t.getDesc());
    }
    private void loadData() {
        this.data = new ArrayList<>();
        Inbox t = new Inbox("Msg 1", "Hola, como te llamas?");
        this.data.add(t);
        t = new Inbox("Msg 2", "Cuantos años tienes?");
        this.data.add(t);
        t = new Inbox("Msg 3", "Deporte favorito?");
        this.data.add(t);
        t = new Inbox("Msg 4", "Música preferidad?");
        this.data.add(t);
    }
    public List<Inbox> getData() {
        System.out.println("data is");
        System.out.println(data);
        return data;
    }
    public String getTodo() {
        return todo;
    }
    public void setTodo(String t) {
        todo = t;
    }
    // value change listener for list item selection
    public void valueChanged(ValueChangeEvent e) {
        String id = (String) e.getNewValue();
        // identify the object index of the given event name
        List<String> names = new ArrayList<>();
        for (int i = 0; i < data.toArray().length; i++){
            names.add(data.get(i).getName());
        }
        int wantedIdx = names.indexOf(id);
        // get description for object at that idx
        String desiredDesc = data.get(wantedIdx).getDesc();
        System.out.println("desiredDesc");
        System.out.println(desiredDesc);
        setMessage(desiredDesc);
    }
    public void setMessage(String s) {
        msg = s;
    }
    public String getMessage() {
        return msg;
    }
}