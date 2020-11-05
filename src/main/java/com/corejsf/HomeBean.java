package com.corejsf;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
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

    private boolean showNewMessage = false;

    public Boolean getJustRegistered() {return justRegistered;}
    public void setJustRegistered(Boolean justRegistered) { this.justRegistered = justRegistered;}

    public Boolean getMessageSent() {        return messageSent;    }
    public void setMessageSent(Boolean messageSent) {        this.messageSent = messageSent;    }

    public String doLogout(){
        server.logout(user.getUsername());
        ExternalContext ectx = FacesContext.getCurrentInstance().getExternalContext();
        messageSent = false;
        justRegistered = false;
        ectx.invalidateSession();
        return "login?faces-redirect=true";
    }

    public void hideNewMessage(){
        showNewMessage = false;
    }
    public void doShowNewMessage(){
        showNewMessage = true;
    }
    public boolean getShowNewMessage(){
        return showNewMessage;
    }
}