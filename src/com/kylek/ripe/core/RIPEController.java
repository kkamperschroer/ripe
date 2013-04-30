/**
(C) Kyle Kamperschroer 2013
 */

package com.kylek.ripe.core;

import java.util.List;

public class RIPEController {

   ////////////////////////////////////////
   // Members
   ////////////////////////////////////////

   // Our database manager
    private DBManager mDb;

   // Our parser
   private WaxeyeParserManager mRecipeParser;

   // The list of recipes
   private List<Recipe> mRecipes;

   ////////////////////////////////////////
   // Constructors
   ////////////////////////////////////////

   public RIPEController(String dbFilename){
      // Setup the database
      mDb = new DBManager(dbFilename);

      // Setup the recipe parser
      mRecipeParser = new WaxeyeParserManager();

      // Get all of the recipes
      mRecipes = mDb.getAllRecipes();
   }

   ////////////////////////////////////////
   // Accessors
   ////////////////////////////////////////

   // None

   ////////////////////////////////////////
   // Other methods
   ////////////////////////////////////////

   // Add a new recipe
   public boolean addRecipe(Recipe r){
      // TODO - Fix check - Can't just look at object equality
      if (mRecipes.contains(r))
         return false;

      // Add it.
      addRecipeToDb(r);

      // Looks good!
      return true;
   }

   // Remove a recipe
   public boolean removeRecipe(Recipe r){
      if (!mRecipes.contains(r))
         return false;

      // Remove it
      removeRecipeFromDb(r);

      // Looks good!
      return true;
   }

   // Get all of the recipes
   public List<Recipe> getAllRecipes(){
      return mRecipes;
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

         // Attempt to map products to each ingredient
         setProductsForRecipe(r);
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

   private void addRecipeToDb(Recipe newRecipe){
      // Attempt to add the recipe to our db
      mDb.storeRecipe(newRecipe);

      // Refresh our list
      mRecipes = mDb.getAllRecipes();
   }

   // Remove the recipe from the DB
   private void removeRecipeFromDb(Recipe r){
      // Attempt to remove the recipe from our db
      mDb.removeRecipe(r);

      // Refresh our list of recipes
      mRecipes = mDb.getAllRecipes();
   }

   // Use the Amazon scrapper to set products for our recipe
   private void setProductsForRecipe(Recipe r){
      // todo
   }
   
}
