package com.corejsf;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;

@ApplicationScoped
@Named("database") // or @ManagedBean(name="login")
public class DatabaseBean implements Serializable {
   // Static field needed
   private static final HashMap<String, String> userPasswords = new HashMap<>();

   public static Set<String> getUsers(){
      return userPasswords.keySet();
   }

   public static Boolean checkLogin(String user, String password){
      // Check login credentials
      if(userPasswords.containsKey(user)) {
         return userPasswords.get(user).equals(password);
      }
      else {
         return false;
      }
   }

   public static Boolean doRegister(String user, String password){
      // Check credentials and perform registration
      // Check if user exists
      if(userPasswords.containsKey(user))
         return false;
      else {
         userPasswords.put(user, password);
         return true;
      }
   }
}