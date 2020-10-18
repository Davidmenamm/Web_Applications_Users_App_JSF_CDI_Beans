package com.corejsf;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.HashMap;
// or import javax.faces.bean.SessionScoped;
@ApplicationScoped
@Named("database") // or @ManagedBean(name="login")
public class DatabaseBean implements Serializable {
   private HashMap<String, String> userPasswords = new HashMap<>();


   public String checkLogin(String user, String password){
      // Check login credentials
      if(userPasswords.containsKey(user)) {
         if(userPasswords.get(user).equals(password))
            return "home";
         else
            return "login";
      }
      else {
         return "login";
      }
   }

   public String register(String user, String password){
      // Check credentials and perform registration

      // Check if user exists
      if(userPasswords.containsKey(user))
         return "register";
      else {
         userPasswords.put(user, password);
         return "home";
      }
   }

}