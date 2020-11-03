// package(s)
package com.corejsf;

// import(s)
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.*;


@SessionScoped
@Named("userListBean")
public class UserListBean implements Serializable {
    private String selectedUser;
    @EJB
    private UserListUpdate updList;

    private List<String> namesList;
    private Map<String,UserBean> usersOnline;
    public String getSelectedUser() {
        return selectedUser;
    }
    public void setSelectedUser(String selectedUser) {
        System.out.println("selector: " +selectedUser);
        this.selectedUser = selectedUser;
    }
    public void getUserListResponse(){
        usersOnline = updList.getMap();
        namesList = new ArrayList<>(usersOnline.keySet());
//        namesList = updList.getList();
    }
    public boolean getUserOnline(String reqUser){
        return usersOnline.get(reqUser).getOnline();
    }
    public List<String> getNamesList(){
        getUserListResponse();
        return namesList;
    }
}

