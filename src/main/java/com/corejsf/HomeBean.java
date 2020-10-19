package com.corejsf;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
// or import javax.faces.bean.SessionScoped;

@Named("home") // or @ManagedBean(name="login")
@SessionScoped
public class HomeBean implements Serializable {
    Boolean justRegistered = false;
    @Inject
    private ServerBean server;
    @Inject
    private UserBean user;

    public Boolean getJustRegistered() {        return justRegistered;    }
    public void setJustRegistered(Boolean justRegistered) {        this.justRegistered = justRegistered;    }

    public String doLogout(){
        server.logout(user.getUsername());
        return "login";
    }
}