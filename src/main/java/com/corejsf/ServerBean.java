package com.corejsf;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.push.Push;
import javax.faces.push.PushContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// or import javax.faces.bean.SessionScoped;
@ApplicationScoped
@Named("server") // or @ManagedBean(name="login")
public class ServerBean implements Serializable {
   // Static field needed
   private HashMap<String, UserBean> users;
   @Inject
   private DatabaseBean db;
   @EJB
   private UserListUpdate updList;
   @Inject
   @Push
   private PushContext incoming;

   public ServerBean(){
      users = new HashMap<>();
      ArrayList<String> registeredUsers = db.getUsers();

      for(String u:registeredUsers) {
         users.put(u, new UserBean(u));
      }
   }

   public HashMap<String, Boolean> getUsersOnlineList() {
      HashMap<String, Boolean> userList = new HashMap<>();

      for(HashMap.Entry<String, UserBean> entry : users.entrySet()){
         String username = entry.getKey();
         UserBean userBean = entry.getValue();

         userList.put(username, userBean.getOnline());
      }

      return userList;
   }

   public void register(String username){
      users.put(username, new UserBean(username));
   }

   public void login(String username){
      users.get(username).setOnline(true);
   }

   public void logout(String username){
      users.get(username).setOnline(false);
   }

   // list re-render
   private String enteredMessage;
   public List<String> getMessages() {
      return updList.getMessages();
   }
   public void onSendMessage() {
      updList.add(enteredMessage);
      incoming.send("newmessage");
   }
   public void setEnteredMessage(String inputMessage){
      enteredMessage = inputMessage;
   }
   public String getEnteredMessage(){
      return enteredMessage;
   }


}