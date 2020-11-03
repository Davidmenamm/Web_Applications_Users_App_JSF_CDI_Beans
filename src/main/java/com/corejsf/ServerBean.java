package com.corejsf;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.push.Push;
import javax.faces.push.PushContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@ApplicationScoped
@Named("server") // or @ManagedBean(name="login")
public class ServerBean implements Serializable {
   // Static field needed
   private final HashMap<String, UserBean> users;
   private final HashMap<String, ArrayList<String[]>> messages;
   @Inject
   private DatabaseBean db;
   @EJB
   private UserListUpdate updList;

   @Inject
   @Push
   private PushContext UserListChannel;


//   @PostConstruct
//   private void init(){}

   public ServerBean(){
      users = new HashMap<>();
      messages = new HashMap<>();
//      if(db  != null){
         Set<String> registeredUsers = db.getUsers();
         for (String u: registeredUsers){
            users.put(u, new UserBean(u));
         }
//      }
   }

   public HashMap<String,UserBean> getUsers(){
      return users;
   }

   public ArrayList<String[]> getMessagesTo(String userReq){
      return messages.get(userReq);
   }

   public void sendMessage(String[] newMessage){
      getMessagesTo(newMessage[1]).add(newMessage);
   }

   public void deleteMessage(String[] oldMessage){
      getMessagesTo(oldMessage[1]).remove(oldMessage);
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
//      updList.putList(new ArrayList<>(users.keySet()));
      updList.putMap(users);
      UserListChannel.send("UpdateListEvent");
   }
}