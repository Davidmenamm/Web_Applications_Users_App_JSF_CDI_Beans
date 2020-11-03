// package(s)
package com.corejsf;

// import(s)
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Singleton
//@SessionScoped
public class UserListUpdate implements Serializable {
    private List<String> usernames;
    private Map<String,UserBean> usersOnline;
    @PostConstruct
    private void init() {}
    public void putList(List<String> userList) {
        usernames = userList;
    }
    public void putMap(Map<String,UserBean> usersMap){usersOnline = usersMap;}
    public Map<String,UserBean> getMap(){return usersOnline;}
    public List<String> getList(){
        return usernames;
    }
}
