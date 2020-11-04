package com.corejsf;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.push.Push;
import javax.faces.push.PushContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.*;

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
   @Inject
   @Push
   private PushContext NewMessageChannel;

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
      ArrayList<String> usernames = new ArrayList<>();

      for(String t: newMessage.getFullTarget()){
         usernames.add(t);
         try {
            getMessagesTo(t).add(newMessage.getTowards(t));
         } catch (CloneNotSupportedException e) {
            e.printStackTrace();
         }
      }
      ////notify those users
      onNotifyMessage(usernames);
      ////
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
      if(hasUnreadMessages(username)){
         ArrayList<String> userToNotify = new ArrayList<>();
         userToNotify.add(username);
         onNotifyMessage(userToNotify);
      }
   }

   public void logout(String username){
      users.get(username).setOnline(false);
      onChangeUserList();
   }

   public void onChangeUserList() {
      updList.putMap(users);
      UserListChannel.send("UpdateListEvent");
   }

   public void onNotifyMessage(Collection<String> usernames) {
      NewMessageChannel.send("NewMessageEvent", usernames);
   }

   // Call when user dismisses message notification
   public void markAllAsRead(String username){
      ArrayList<MessageData> msgList = messages.get(username);

      for(MessageData msg: msgList){
         msg.markRead();
      }
   }

   private Boolean hasUnreadMessages(String username) {
      ArrayList<MessageData> msgList = messages.get(username);
      Boolean hasUnread = false;

      for(MessageData msg: msgList){
         if(!msg.isRead())
            hasUnread = true;
      }

      return hasUnread;
   }

}