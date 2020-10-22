// package(s)
package com.corejsf;

// import(s)
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

@SessionScoped
@Named("userList")
public class UserListBean implements Serializable {
    // atributes
    private String selectedUser;
    private static Map<String,Object> userList;
    static{
        try{
            System.out.println("TEST!");
            // get users list keys / names
            ArrayList<String> userNames = DatabaseBean.getUsers();
            // set users list visual map
            userList = new LinkedHashMap<String,Object>();
            for (String name : userNames){
                userList.put(name,name);
            }
        } catch(Exception e){
            System.out.println(e);
            e.printStackTrace();
        }
    }

    // getter and setter
    public Map<String,Object> getUserList() {
        return userList;
    }
    public String getSelectedUser() {
        return selectedUser;
    }
    public void setSelectedUser(String selectedUser) {
        this.selectedUser = selectedUser;
    }
}

