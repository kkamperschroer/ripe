/**
   (C) Kyle Kamperschroer 2013
*/

package com.kylek.ripe.core;

import java.util.Vector;

class User {

   ////////////////////////////////////////
   // Members
   ////////////////////////////////////////

   // This user's username
   private String mUsername;

   // This user's passhash
   private String mPassword;

   // This user's current session id
   private String mSessionId;
   
   // A vector of this user's recipes
   private Vector<Recipe> mRecipes;

   ////////////////////////////////////////
   // Constructor
   ////////////////////////////////////////

   // Default constructor
   public User(){
      mUsername = "";
      mPassword = "";
      mSessionId = "";
      mRecipes = null;
   }

   // Constructor with params
   public User(String username,
               String password,
               String sessionId,
               Vector<Recipe> recipes){
      mUsername = username;
      mPassword = password;
      mSessionId = sessionId;
      mRecipes = recipes;
   }

   ////////////////////////////////////////
   // Accessors
   ////////////////////////////////////////

   public String getUsername() { return mUsername; }
   public String getPassword() { return mPassword; }
   public String getSessionId() { return mSessionId; }
   public Vector<Recipe> getRecipes() { return mRecipes; }

   public void setUsername(String username) { mUsername = username; }
   public void setPassword(String password) { mPassword = password; }
   public void setSessionId(String sessionId) { mSessionId = sessionId; }
   public void setRecipes(Vector<Recipe> recipes) { mRecipes = recipes; }

   ////////////////////////////////////////
   // Other methods
   ////////////////////////////////////////

   // None for now.
}
