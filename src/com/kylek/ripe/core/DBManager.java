/**
   (C) Kyle Kamperschroer 2013
*/

package com.kylek.ripe.core;

import java.util.List;

import com.db4o.*;

public class DBManager {

   /////////////////////////////////////
   // Members
   /////////////////////////////////////
    
   // The database reference
   private ObjectContainer mDb;

   /////////////////////////////////////
   // Constructors
   /////////////////////////////////////
    
   // The one and only constructor.
   public DBManager(String fileLocation){
      mDb = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), fileLocation);
   }

   /////////////////////////////////////
   // Setters and getters
   /////////////////////////////////////
    
   // None needed. We don't want to allow much access here.

   /////////////////////////////////////
   // Other methods
   /////////////////////////////////////

   //// Public methods ////

   // Store a user in the database
   public void storeUser(User user){
      mDb.store(user);
      mDb.commit();
   }

   // Remove a user from the database
   public void removeUser(User user){
      mDb.delete(user);
      mDb.commit();
   }

   // Get all of the users
   public List<User> getAllUsers(){
      return mDb.query(User.class);
   }

   // Close the db
   public void close(){
      mDb.commit();
      mDb.close();
   }

   //// Private methods ////

   // None

}
