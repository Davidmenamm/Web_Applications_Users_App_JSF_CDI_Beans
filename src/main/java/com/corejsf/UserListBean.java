// package(s)
package com.corejsf;

// import(s)
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

@SessionScoped
@Named("userList")
public class UserListBean implements Serializable {
    // atributes
    private String selectedUser;
    @Inject
    private ServerBean sb;

    private static Map<String,Object> userList = new LinkedHashMap<String, Object>();
    private List<String> users = new ArrayList<>();

    // getter and setter
    public Map<String,Object> getUserList() {
        try{
            System.out.println("TEST!");
            // get users list keys / names
            userList.clear();
            HashMap<String, Boolean> userOnlineList = sb.getUsersOnlineList();
            for ( String name : userOnlineList.keySet() ){
                userList.put(name,userOnlineList.get(name));
            }
        } catch(Exception e){
            System.out.println(e);
            e.printStackTrace();
        }
        return userList;
    }
    public String getSelectedUser() {
        return selectedUser;
    }
    public void setSelectedUser(String selectedUser) {
        System.out.println(selectedUser);
        this.selectedUser = selectedUser;
    }
//    public void selectionChanged(ValueChangeEvent e){
//        setSelectedUser((String) e.getNewValue());
//    }
    public List<String> getUsers() {
        Map<String,Object> usersMap = getUserList();
        this.users = new ArrayList<>(usersMap.keySet());
        return this.users;
    }
    public void setUsers(List<String> users) {
        this.users = users;
    }
}

