package com.kylek.ripe.core;

import java.util.HashMap;
import java.util.UUID;

public class SessionManager {

   ////////////////////////////////////////
   // Members
   ////////////////////////////////////////

   // A hash map keyed off user accounts, with session as the value
   private HashMap<String, String> mUserSessions;

   ////////////////////////////////////////
   // Constructor
   ////////////////////////////////////////

   // Default constructor
   public SessionManager(){
      mUserSessions = new HashMap<String, String>();
   }

   ////////////////////////////////////////
   // Accessors
   ////////////////////////////////////////

   // Set a user's session id manually (for persistent session ids)
   public void setUserSessionId(String username, String sessionId){
      mUserSessions.put(username, sessionId);
   }   

   ////////////////////////////////////////
   // Methods
   ////////////////////////////////////////

   // Get a particular user session id
   public String getUserPreviousSessionId(String username){
      return mUserSessions.get(username);
   }

   // Generate a new session for a particular user
   public String generateSessionId(User user){
      // Generate a new random session id
      String newUuid = UUID.randomUUID().toString();

      // Update the user account
      user.setSessionId(newUuid);

      // Update the map with session ids
      mUserSessions.put(user.getUsername(), newUuid);

      // Return the new UUID
      return newUuid;
   }
}
