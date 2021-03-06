package com.corejsf;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
// or import javax.faces.bean.SessionScoped;

@Named("user") // or @ManagedBean(name="login")
@SessionScoped
public class UserBean implements Serializable {
   private String username;
   private Boolean online = false;
   private Boolean hasMessage = false;
   @Inject
   ServerBean server;
//   @Inject
//   HomeBean home;

   public UserBean(){

   }

   public UserBean(String username){
      this.username = username;

   }

   public UserBean(String username, Boolean online){
      this.username = username;
      this.online = online;
   }

   public String getUsername() { return username; }
   public void setUsername(String newValue) { username = newValue; }


   public Boolean getOnline() { return online;   }
   public void setOnline(Boolean online) { this.online = online;   }

   public Boolean getHasMessage() { return hasMessage;}
   public void setHasMessage(Boolean hasMessage) { this.hasMessage = hasMessage;}
   public void setHasMessageTrue() { this.hasMessage = true;}

   public void ignoreNotification(){
      hasMessage = false;
      server.markAllAsRead(username);
   }
   public void acceptNotification(){
      hasMessage = false;
   }

}