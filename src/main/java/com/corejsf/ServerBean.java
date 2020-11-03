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
import java.util.Set;

@ApplicationScoped
@Named("server") // or @ManagedBean(name="login")
public class ServerBean implements Serializable {
   // Static field needed
   private final HashMap<String, UserBean> users;
   private final HashMap<String, ArrayList<MessageData>> messages;
   @Inject
   private DatabaseBean db;
   @EJB
   private UserListUpdate updList;

   @Inject
   @Push
   private PushContext UserListChannel;

   public ServerBean(){
      users = new HashMap<>();
      messages = new HashMap<>();
      Set<String> registeredUsers = db.getUsers();
      for (String u: registeredUsers){
         users.put(u, new UserBean(u));
      }
   }

   public ArrayList<MessageData> getMessagesTo(String userReq){
      return messages.get(userReq);
   }

   public void sendMessage(MessageData newMessage){
      for(String t: newMessage.getFullTarget()){
         try {
            getMessagesTo(t).add(newMessage.getTowards(t));
         } catch (CloneNotSupportedException e) {
            e.printStackTrace();
         }
      }
   }

   public void deleteMessage(MessageData oldMessage){
      getMessagesTo(oldMessage.getTarget()).remove(oldMessage);
   }

   public void register(String username){
      users.put(username, new UserBean(username));
      messages.put(username, new ArrayList<>());
   }

   public void login(String username){
      users.get(username).setOnline(true);
      onChangeUserList();
   }

   public void logout(String username){
      users.get(username).setOnline(false);
      onChangeUserList();
   }

   public void onChangeUserList() {
      updList.putMap(users);
      UserListChannel.send("UpdateListEvent");
   }
}