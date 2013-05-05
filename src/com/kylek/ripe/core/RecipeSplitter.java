/**
(C) Kyle Kamperschroer
 */

package com.kylek.ripe.core;

import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

// This class is responsible for splitting a recipe into it's
// major components so they can be futher picked apart by the
// Waxeye parsers
public class RecipeSplitter {

   ////////////////////////////////////////
   // Members
   ////////////////////////////////////////

   // The title of the recipe
   private String mTitle;

   // The attributes section of the recipe
   private String mAttributes;

   // The ingredients list section of the recipe
   private String mIngredientsList;

   // The directions portion of the recipe
   private String mDirections;

   ////////////////////////////////////////
   // Constructors
   ////////////////////////////////////////

   // Constructor with input string
   public RecipeSplitter(){
      mTitle = "";
      mAttributes = "";
      mIngredientsList = "";
      mDirections = "";
   }

   ////////////////////////////////////////
   // Accessors
   ////////////////////////////////////////

   public String getTitle() { return mTitle; }
   public String getAttributes() { return mAttributes; }
   public String getIngredientsList() { return mIngredientsList; }
   public String getDirections() { return mDirections; }

   ////////////////////////////////////////
   // Other methods
   ////////////////////////////////////////

   // Public method for splitting the recipe
   public void splitRecipe(String inputRecipe) throws Exception{
      // Start by trimming the recipe to get rid of any extra whitespace
      inputRecipe = inputRecipe.trim();

      // The title will be the first line of the recipe.
      inputRecipe = getTitle(inputRecipe);
      inputRecipe = getAttributes(inputRecipe);
      inputRecipe = getIngredientsList(inputRecipe);

      // All that remains we will call directions!
      mDirections = inputRecipe.trim(); 
   }

   // Get the title out of the input string. Modifies the provided input
   private String getTitle(String inputRecipe) throws Exception{
      // For now, the title will always be the first line
      int firstNewline = inputRecipe.indexOf('\n');
      if (firstNewline < 0)
      {
         // Hmm, this isn't a real recipe at all!
         throw new Exception("Invalid recipe! No newline character found.");
      }
      mTitle = inputRecipe.substring(0, firstNewline).trim();

      // Now modify the inputRecipe to shave off the title
      inputRecipe = inputRecipe.substring(firstNewline).trim();
      return inputRecipe;
   }

   // Get the attributes out of the input string. Modifies the provided input.
   private String getAttributes(String inputRecipe) throws Exception{
      // There are a few places we want to split here.
      // Options:
      //    1) The word "ingredient" or "ingredients"
      //    2) 2 newline characters back to back, creating a visual break
      //    3) A line that begins with a number (an amount)
      
      // First, look for the word "ingredients" or "ingredient"
      String ingredientsBreakRegex = "([I|i]ngredient(s)? ([L|l]ist)?(:)?)|(\\n\\n)";
      Pattern ingredientsBreakPattern = Pattern.compile(ingredientsBreakRegex);
      Matcher matcher = ingredientsBreakPattern.matcher(inputRecipe);

      // Let's see if we have a match!
      int matchStart = -1;
      int matchEnd = -1;
      if (matcher.find()){
         // Cool, let's get the match results.
         MatchResult matchResult = matcher.toMatchResult();
         matchStart = matchResult.start();
         matchEnd = matchResult.end();

         // The attributes section will be up to the match start
         mAttributes = inputRecipe.substring(0, matchStart).trim();

         // Now shave the input recipe to start after the end of the match,
         // so we are cutting out the actual break, such as "Ingredients"
         inputRecipe = inputRecipe.substring(matchEnd).trim();

         // Now return our attributes section
         return inputRecipe;
      }

      // Crap, no match. Try to find a line that starts with a number and
      // call that the beginning of the ingredients list.
      String numRegex = "^[0-9]";
      ingredientsBreakPattern = Pattern.compile(numRegex, Pattern.MULTILINE);
      matcher = ingredientsBreakPattern.matcher(inputRecipe);

      // See if we have a match!
      if (matcher.find()){
         // Yes! Found a line that begins with a number. Let's
         // call it the start of the ingredients list.
         MatchResult matchResult = matcher.toMatchResult();
         matchStart = matchResult.start();

         // The attributes are everything up to this point
         mAttributes = inputRecipe.substring(0, matchStart).trim();

         // Now shave off the attributes from the input recipe
         inputRecipe = inputRecipe.substring(matchStart).trim();

         // Return our shaved input recipe
         return inputRecipe;
      }


      // Well, this isn't good. We failed to find a break. Let's call
      // it invalid.
      throw new Exception("Invalid recipe! Unable to find the attributes section.");
   }

   // This method is used to find the string block for the ingredients list
   private String getIngredientsList(String inputRecipe) throws Exception{
      // There are a few places we want to split here.
      // Options:
      //    1) The word "direction" or "directions"
      //    2) 2 newline characters back to back, creating a visual break

      // First, look for the word "direction" or "directions" or "preparation"
      String directionsBreakRegex =
         "(([D|d]irection(s)?)|([P|p]rep(aration)?(s)?)(:)?)|(\\n\\n)";
      Pattern directionsBreakPattern = Pattern.compile(directionsBreakRegex);
      Matcher matcher = directionsBreakPattern.matcher(inputRecipe);

      // Let's see if we have a match!
      int matchStart = -1;
      int matchEnd = -1;
      if (matcher.find()){
         // Cool, let's get the match results.
         MatchResult matchResult = matcher.toMatchResult();
         matchStart = matchResult.start();
         matchEnd = matchResult.end();

         // The ingredients list section will be up to the match start
         mIngredientsList = inputRecipe.substring(0, matchStart).trim();

         // Now shave the input recipe to start after the end of the match,
         // so we are cutting out the actual break, such as "Directions"
         inputRecipe = inputRecipe.substring(matchEnd).trim();

         // Now return our shaved input recipe.
         return inputRecipe;
      }
      
      // Well, this isn't good. Failed to find a break again. Calling the
      // recipe invalid!
      throw new Exception("Invalid recipe! Unable to find the ingredients list.");
   }
   
}
