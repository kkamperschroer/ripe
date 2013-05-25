/**
(C) Kyle Kamperschroer 2013
 */

package com.kylek.ripe.core;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Vector;

public class RIPEController {

   ////////////////////////////////////////
   // Members
   ////////////////////////////////////////

   // Our database manager
   private DBManager mDb;

   // Our parser
   private WaxeyeParserManager mRecipeParser;

   // The list of user
   private List<User> mUsers;

   // The user session manager
   private SessionManager mSessionManager;

   ////////////////////////////////////////
   // Constructors
   ////////////////////////////////////////

   public RIPEController(String dbFilename){
      // Setup the database
      mDb = new DBManager(dbFilename);

      // Setup the recipe parser
      mRecipeParser = new WaxeyeParserManager();

      // Get all of the users
      mUsers = mDb.getAllUsers();

      // Start up the session manager
      mSessionManager = new SessionManager();

      // Populate the session id's for the session manager so
      // everything is persistent across system restarts.
      User curUser;
      for (int i=0; i<mUsers.size(); i++){
         curUser = mUsers.get(i);
         mSessionManager.setUserSessionId(
            curUser.getUsername(),
            curUser.getSessionId());
      }
   }

   ////////////////////////////////////////
   // Accessors
   ////////////////////////////////////////

   // None

   ////////////////////////////////////////
   // Other methods
   ////////////////////////////////////////

   // Add a new user
   public boolean addUser(String username, String rawPassword){
      // To start, verify the username is unique
      for (int i=0; i<mUsers.size(); i++){
         if (mUsers.get(i).getUsername().equals(username)){
            // This username is taken
            return false;
         }
      }
      // This username is available, so continue.
      
      // Create a new user object
      User newUser = new User();

      // Set the username
      newUser.setUsername(username);

      // Set the password
      newUser.setPassword(hashPassword(rawPassword));

      // Generate a new session id for this user (updates the object)
      mSessionManager.generateSessionId(newUser);

      // Save the user in the database
      addUserToDb(newUser);
      
      // All is fine and dandy
      return true;
   }
   
   // Add a new recipe for a user
   public boolean addRecipeForUser(Recipe recipe, User user){
      // Add it to this user
      boolean retVal = user.addRecipe(recipe);

      // Update this user in the db
      updateUser(user);
      
      // Everything worked out
      return retVal;
   }

   // Remove a recipe for a user
   public boolean removeRecipeWithIdForUser(int recipeId, User user){
      // Remove this users recipe
      boolean retVal = user.removeRecipe(recipeId);

      // Update this user in the db
      updateUser(user);
      
      // Everything worked
      return retVal;
   }

   // Update a user
   public void updateUser(User user){
      // Add = update in db4o, if objects are equal.
      addUserToDb(user);
   }

   // Remove a user
   public boolean removeUser(User user){
      if (!mUsers.contains(user)){
         return false;
      }

      // Remove it
      removeUserFromDb(user);

      // Looks good!
      return true;
   }

   // Get the user object for a given username
   public User getUserForUsername(String username){
      // Iterate through our users, looking for one with the same username
      User curUser = null;
      for (int i=0; i<mUsers.size(); i++){
         curUser = mUsers.get(i);
         if (curUser.getUsername().equals(username)){
            return curUser;
         }
      }

      // Didn't find the user you were looking for!
      return null;
   }

   // Validate a user's current session id with one provided
   public boolean isUserSessionIdValid(User user){
      return (mSessionManager.getUserPreviousSessionId(
                 user.getUsername()).equals(
                    user.getSessionId()));
   }

   // Update a user's session id
   public void updateUserSession(User user){
      // Update the session
      mSessionManager.generateSessionId(user);

      // Save off this user
      updateUser(user);
   }

   // Parse a plain text recipe
   public Recipe parseRecipe(String plainTextRecipe){
      // Run it through the recipe parser
      final boolean success = mRecipeParser.parseString(plainTextRecipe);

      Recipe r = null;
      if (success)
      {
         try {
            r = mRecipeParser.getParsedRecipe();
            // Useful for debugging.
            System.out.println(r.toString());
         } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
      }

      return r;
   }

   // Get the error message from the parser
   public String getErrorMessage(){ return mRecipeParser.getErrorMessage(); }

   // Close
   public void close(){
      mDb.close();
   }

   //// Private methods ////

   private void addUserToDb(User newUser){
      // Attempt to add the user to our db
      mDb.storeUser(newUser);

      // Refresh our list of users
      mUsers = mDb.getAllUsers();
   }

   // Remove the user from the DB
   private void removeUserFromDb(User user){
      // Attempt to remove the recipe from our db
      mDb.removeUser(user);

      // Refresh our list of recipes
      mUsers = mDb.getAllUsers();
   }

   // Use the Amazon scrapper to set products for our recipe
   public void setProductsForRecipe(Recipe r){
      // Iterate through each ingredient
      Vector<MeasurementAndIngredient> ingsList = r.getIngredients().getIngredients();
      for (int i=0; i<ingsList.size(); i++){
         Ingredient curIng = ingsList.get(i).getIngredient();
         curIng.setAmazonUrl(AmazonScraper.getProductUrl(curIng.getName()));
      }
   }

   // Hash a given password
   private String hashPassword(String rawPassword){
      // Hash the rawPassword
      try{
         MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
         byte[] passBytes = (rawPassword + "silly open source salting").getBytes();
         byte[] passHash = sha256.digest(passBytes);

         // Return our new hash
         return new String(passHash);

      }catch(NoSuchAlgorithmException e){
         // Uhhh, crap. Just use raw password, I guess?
         System.err.println("Serious problem. Couldn't use SHA-256.");
         return rawPassword;
      }
   }
}
