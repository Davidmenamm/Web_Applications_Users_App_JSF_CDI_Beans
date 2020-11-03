// package(s)
package com.corejsf;

// import(s)
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import java.io.Serializable;
import java.util.Map;

@Singleton
public class UserListUpdate implements Serializable {
    private Map<String,UserBean> usersOnline;
    @PostConstruct
    private void init() {}
    public void putMap(Map<String,UserBean> usersMap){usersOnline = usersMap;}
    public Map<String,UserBean> getMap(){return usersOnline;}
}
