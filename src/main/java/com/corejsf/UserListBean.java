// package(s)
package com.corejsf;

// import(s)
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jws.soap.SOAPBinding;
import java.io.Serializable;
import java.util.*;


@SessionScoped
@Named("userListBean")
public class UserListBean implements Serializable {
    private ArrayList<String> selectedUsers;
    @EJB
    private UserListUpdate updList;
    @Inject
    private UserBean userBean;

    private List<UserBean> namesList;
    private Map<String,UserBean> usersOnline;

    public ArrayList<String> getSelectedUsers() {
        return selectedUsers;
    }
    public void setSelectedUsers(ArrayList<String> selectedUser) {
        this.selectedUsers = selectedUser;
    }
    public void getUserListResponse(){
        usersOnline = updList.getMap();
        namesList = new ArrayList<>(usersOnline.values());
        namesList.remove(usersOnline.get(userBean.getUsername()));
    }
    public List<UserBean> getNamesList(){
        getUserListResponse();
        return namesList;
    }
    public String getMyName(){
        return userBean.getUsername();
    }
}

