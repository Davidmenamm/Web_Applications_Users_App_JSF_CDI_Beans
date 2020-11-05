package com.corejsf;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.push.Push;
import javax.faces.push.PushContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.lang.reflect.Array;
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

         getMessagesTo(t).add(new MessageData(newMessage,t));

      }
      ////notify those users
      onNotifyMessage(usernames);
      ////
   }

   public void deleteMessage(MessageData oldMessage){
      getMessagesTo(oldMessage.getTarget()).remove(oldMessage);
   }

   public void markAsRead(MessageData oldMessage){
      ArrayList<MessageData> myMsgs = getMessagesTo(oldMessage.getTarget());
      int wantedIdx = myMsgs.indexOf(oldMessage);
      MessageData currMsg = myMsgs.get(wantedIdx);
      currMsg.markRead();
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

   private boolean hasUnreadMessages(String username) {
      ArrayList<MessageData> msgList = messages.get(username);
      boolean hasUnread = false;

      for(MessageData msg: msgList){
         if (!msg.getRead()) {
            hasUnread = true;
            break;
         }
      }

      return hasUnread;
   }

   public boolean checkUnreadMessages(String username){
      return hasUnreadMessages(username);
   }

}