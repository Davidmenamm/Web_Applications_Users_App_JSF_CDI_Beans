package com.corejsf;

import java.io.Serializable;

import javax.inject.Named; 
   // or import javax.faces.bean.ManagedBean;
import javax.enterprise.context.SessionScoped; 
   // or import javax.faces.bean.SessionScoped;

@Named("login") // or @ManagedBean(name="login")
@SessionScoped
public class LoginBean implements Serializable {
   private String username;
   private String password;
   
   public String getUsername() { return username; }
   public void setUsername(String newValue) { username = newValue; }
   
   public String getPassword() { return password; }
   public void setPassword(String newValue) { password = newValue; }

}