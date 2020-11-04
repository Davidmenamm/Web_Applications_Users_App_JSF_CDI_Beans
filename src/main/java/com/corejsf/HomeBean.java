package com.corejsf;

import javax.enterprise.context.SessionScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
// or import javax.faces.bean.SessionScoped;

@Named("home") // or @ManagedBean(name="login")
@SessionScoped
public class HomeBean implements Serializable {
    Boolean justRegistered = false;
    Boolean messageSent = false;
    @Inject
    private ServerBean server;
    @Inject
    private UserBean user;

    public Boolean getJustRegistered() {return justRegistered;}
    public void setJustRegistered(Boolean justRegistered) { this.justRegistered = justRegistered;}

    public Boolean getMessageSent() {        return messageSent;    }
    public void setMessageSent(Boolean messageSent) {        this.messageSent = messageSent;    }

    public String doLogout(){
        server.logout(user.getUsername());
        return "login?faces-redirect=true";
    }
}