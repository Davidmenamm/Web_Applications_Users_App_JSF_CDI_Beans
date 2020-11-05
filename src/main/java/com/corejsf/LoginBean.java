package com.corejsf;

import com.sun.security.ntlm.Server;

import java.io.Serializable;

import javax.inject.Inject;
import javax.inject.Named;
   // or import javax.faces.bean.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.xml.crypto.Data;
// or import javax.faces.bean.SessionScoped;

@Named("login") // or @ManagedBean(name="login")
@SessionScoped
public class LoginBean implements Serializable {
   private String username;
   private String password;
   private Boolean loginError = false;
   @Inject
   private ServerBean server;
   @Inject
   private DatabaseBean db;
   @Inject
   private UserBean user;
   @Inject
   private HomeBean home;
   
   public String getUsername() { return username; }
   public void setUsername(String newValue) { username = newValue; }
   
   public String getPassword() { return password; }
   public void setPassword(String newValue) { password = newValue; }

   public Boolean getLoginError() {      return loginError;   }
   public void setLoginError(Boolean loginError) {      this.loginError = loginError;   }

   public String doLogin(){
      if (db.checkLogin(username, password)) {
         loginError = false;
         // Tell server that the user logged in
         server.login(username);

         // Set this logged user to the UserBean
         user.setUsername(username);
         user.setOnline(true);

         if (server.checkUnreadMessages(this.username)){
            user.setHasMessageTrue();
         }

         home.setJustRegistered(false);

         return "home?faces-redirect=true";
      }
      else {
         loginError = true;
         return "login";
      }
   }

}