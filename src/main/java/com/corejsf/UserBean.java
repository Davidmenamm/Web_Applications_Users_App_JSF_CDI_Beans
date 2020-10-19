package com.corejsf;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
// or import javax.faces.bean.SessionScoped;

@Named("user") // or @ManagedBean(name="login")
@SessionScoped
public class UserBean implements Serializable {
   private String username;
   private Boolean online = false;

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


   public Boolean getOnline() {      return online;   }
   public void setOnline(Boolean online) {      this.online = online;   }


}