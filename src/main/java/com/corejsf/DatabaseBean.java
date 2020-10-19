package com.corejsf;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
// or import javax.faces.bean.SessionScoped;
@ApplicationScoped
@Named("database") // or @ManagedBean(name="login")
public class DatabaseBean implements Serializable {
   // Static field needed
   private static HashMap<String, String> userPasswords = new HashMap<>();


   public static Boolean checkLogin(String user, String password){
      // Check login credentials
      if(userPasswords.containsKey(user)) {
         if(userPasswords.get(user).equals(password))
            return true;
         else
            return false;
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

   public static ArrayList<String> getUsers(){
      String[] list = (String[])userPasswords.keySet().toArray(new String[userPasswords.size()]);
      return new ArrayList<String>(Arrays.asList(list));
   }

}