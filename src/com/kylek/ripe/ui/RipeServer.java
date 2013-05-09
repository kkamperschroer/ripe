/**
   (C) Kyle Kamperschroer
*/
package com.kylek.ripe.ui;

import java.util.*;
import java.io.*;
import com.kylek.ripe.core.*;

public class RipeServer extends NanoHTTPD{

   ////////////////////////////////////
   // Members
   ////////////////////////////////////
    
   // The ripe controller
   private RIPEController mRipe;

   // The db file loc (BAD KYLE!)
   private static final String mDbLoc =
      "/media/Warehouse/Dropbox/School/Eclipse/workspace/ripe_v1_java/ripeDB.db";

   // A constant for the port we will be using
   private static final int PORT = 8778;

   // A string that we will replace
   private static final String CONTENT_STR = "#{CONTENT}#";

   ////////////////////////////////////
   // Constructors
   ////////////////////////////////////

   // The default constructor
   public RipeServer() throws IOException{
      super(PORT, new File("."));

      // This will take a second
      System.out.print("Initializing RIPE...");
      mRipe = new RIPEController(mDbLoc);
      System.out.println(" done!");
   }

   ////////////////////////////////////
   // Setters and getters
   ////////////////////////////////////

   // None for now

   ////////////////////////////////////
   // Other methods
   ////////////////////////////////////

   // Main entry point
   public static void main( String[] args ){
      try{
         new RipeServer();
      }
      catch( IOException ioe )
      {
         System.err.println("Couldn't start server:\n" + ioe);
         System.exit(-1);
      }
      System.out.println("Listening on port " + PORT + ". Hit Enter to stop.\n");
      try
      {
         System.in.read(); 
      }
      catch( Throwable t ) {};
   }


   // Serve up a page!
   public Response serve(
      String uri,
      String method,
      Properties header,
      Properties parms,
      Properties files){

      // This string will ultimately be the entire page. We will do a find/replace
      // for the string CONTENT_STR, once we have the rest of the page rendered.
      String page = renderPageFramework();
      String content = "";

      try{
         // Base the page on what is specified.
         String requestedPage = parms.getProperty("page");
	
         // The recipe listing page
         if (requestedPage == null ||
             requestedPage.equals("recipes")){
            content = renderRecipes();
         }
         // The view recipe page
         else if (requestedPage.equals("view")){
            // Get the recipe id
            String recipeId = parms.getProperty("recipe");
            int recId = -1;
            if (recipeId != null){
               recId = Integer.parseInt(recipeId);
            }

            content = renderRecipe(recId);
         }
         else if(requestedPage.equals("add_recipe")){
            // Oh boy!
	            
            // Headers
            content +=
               "<h2>Add Recipe</h2>\n" +
               "<p>Just paste in the recipe!</p>\n";
	
            // Build the form
            content +=
               "<form action='?page=add_recipe_go' method='post'>\n" +
               "    Recipe:\n<br/>\n" +
               "    <textarea cols='80' rows='30' name='raw_recipe'>" +
               "</textarea>\n<br/>\n" +
               "    <input type='button' value='Upload from file'/>\n" +
               "    <input type='submit' value='Parse it!'/>\n" +
               "</form>\n";

                    
            // Link back to the listing
            content +=
               "<br/><br/><br/><a href='/'>Back to listing</a>\n";
         }
         else if(requestedPage.equals("add_recipe_go")){
            // Get the recipe from the post
            String recipe = parms.getProperty("raw_recipe");
		
            // Ask mRipe to parse the recipe
            Recipe parsed = mRipe.parseRecipe(recipe);
	
            // Hopefully that worked! 
            // Add the recipe to our db
            boolean retVal = false;
            if (parsed != null){
               retVal = mRipe.addRecipe(parsed);
            }
	
            if (retVal){
               content +=
                  "Recipe parsed successfully. Click " +
                  "<a href='?page=view&recipe=" + (mRipe.getAllRecipes().size()-1) +
                  "'>here</a>.";
            }
            else{
               content += mRipe.getErrorMessage();
            }
            // Link back to the listing
            content +=
               "<br/><br/><br/>\n<a href='/'>Back to listing</a>\n";
	
         }
         else if(requestedPage.equals("edit")){
            content += "Under construction.";
         }
         else if(requestedPage.equals("remove")){
            // Get the current recipe id
            String recipeId = parms.getProperty("recipe");
            int recId = -1;
            if (recipeId != null){
               recId = Integer.parseInt(recipeId);
            }
	            
            if (recId < mRipe.getAllRecipes().size() &&
                recId >= 0){
               // Remove the recipe
               boolean retVal = mRipe.removeRecipe(mRipe.getAllRecipes().get(recId));
	                
               // Display a link back to the recipes page
               if (retVal){
                  content +=
                     "Recipe deleted!\n<br/>\n";
               }
               else{
                  content +=
                     "Error removing recipe!\n<br/>\n";
               }
	                
               // Link back to the listing
               content +=
                  "<a href='/'>Back to listing</a>\n";
            }
            else{
               content +=
                  "Invalid recipe id: " + recipeId;
            }
         }
         else{
            content += "<p>Unknown page: " + requestedPage + "</p>\n";
         }
      }
      catch (Exception e){
         // Yikes. Keep running!
         e.printStackTrace();
         System.out.println(e);
         content += "An unexpected error occured. Continuing anyways!";
      }

      page = page.replace(CONTENT_STR, content);
      
      return new NanoHTTPD.Response( HTTP_OK, MIME_HTML, page );
   }

   //// Private methods ////
    
   // Add the header to our output page
   private String renderPageFramework(){
      String page = "<html>\n";
      page += addHead();
      page +=
         "    <body>\n" +
         "        <div id='ripe_header'>\n" +
         "            <h1>Kyle's Recipe Parser</h1>\n" +
         "        </div>\n" +
         "        <div id='ripe_content'>\n" +
                      CONTENT_STR + "\n" +            
         "        </div>\n";
      page += addFooter();
      page +=
         "    </body>\n" +
         "</html>\n";
      return page;
   }

   // Add the head section
   private String addHead(){
      return
         "    <head>\n" +
         "        <title>RIPE: Recipe Parsing Engine</title>\n" +
         "    </head>\n";
   }

   // Add the footer to our output page
   private String addFooter(){
      return
         "    <div id='ripe_footer'>\n" +
         "        <p>(C) Kyle Kamperschroer 2013</p>\n" +
         "    </div>\n";
   }

   // Render the sub page header
   private String renderContentHeader(String title){
      return
         "    <div id='ripe_content_header'>\n" +
         "        <h2>" + title + "</h2>\n" +
         "    </div>\n";
   }

   // Render the content for viewing all recipes
   private String renderRecipes(){
      String content = renderContentHeader("Listing Recipes");
	
      // Build a table of our recipes
      content += 
         "<table border=1>\n" +
         "    <tr>\n" +
         "        <th>Recipe Name</th>\n" +
         "        <th colspan=2>Modify</th>\n" +
         "    </tr>\n";
	
      // Add the recipes
      for (int i=0; i<mRipe.getAllRecipes().size(); i++){
         Recipe cur = mRipe.getAllRecipes().get(i);
         content +=
            "<tr>\n" +
            "   <td><a href=\"?page=view&recipe=" + i + "\">" + cur.getName() + "</a></td>\n" +
            "   <td><a href=\"/?page=edit&recipe=" + i + "\">Edit</a>\n" +
            "   <td><a href=\"/?page=remove&recipe=" + i + "\">X</a>\n" +
            "</tr>\n";
      }
	
      content +=
         "</table>\n" +
         "<br/>\n" +
         "<a href=\"/?page=add_recipe\">Add a Recipe</a>\n";

      return content;
   }

   // Render a single recipe
   private String renderRecipe(final int recId){
      String content = "";
      if (recId < mRipe.getAllRecipes().size() &&
          recId >= 0){

         // Get our current recipe
         Recipe recipe = mRipe.getAllRecipes().get(recId);
	
         // The recipe name
         content += renderContentHeader(recipe.getName());

         // The attributes section
         content += renderRecipeAttributes(recipe);

         // The ingredients list section
         content += renderRecipeIngredientsList(recipe);

         // The directions section
         content += renderRecipeDirections(recipe);
         
         // Some recipe links
         content += renderEndRecipeLinks(recId);
      }
      else{
         content += "<p>Invalid recipe: " + recId + "</p>\n";
      }
      return content;
   }

   // Render the attributes section of the recipe
   private String renderRecipeAttributes(Recipe recipe){
      // The attributes section
      String content = "<div id='recipe_attributes'>\n";
         
      // The number of servings
      Yield yield = recipe.getYield();
      if (yield != null){
         double value = yield.getValue();
         String unit = yield.getUnit();
         String yieldStr = "";
         if (value > 0 &&
             value % 1.0 == 0){
            yieldStr += (int) value;
         }
         else{
            yieldStr += value;
         }
         yieldStr += " " + unit;

         content +=
            renderRecipeAttribute("Yield:",
                                  yieldStr);
      }

      // TODO : You could calculate any missing times, automatically!
         
      // The cook and prep time
      String cookTime = recipe.getCookTime();
      String prepTime = recipe.getPrepTime();
      String overallTime = recipe.getOverallTime();
      
      if (prepTime != null){
         content +=
            renderRecipeAttribute("Prep Time:",
                                  prepTime);
      }
      if (cookTime != null){
         content +=
            renderRecipeAttribute("Cook Time:",
                                  cookTime);
      }
      if (overallTime != null){
         content +=
            renderRecipeAttribute("Overall Time:",
                                  overallTime);
      }

      content += "</div>\n"; // The recipe_attributes div.
      return content;
   }

   // Render a single attribute
   private String renderRecipeAttribute(String attrName, String attrValue){
      return 
         "<div class='recipe_attribute'>\n" +
         "    <div class='recipe_attribute_name'>" + attrName + "</div>\n" +
         "    <div class='recipe_attribute_value'>\n" +
                  attrValue + "\n" +
         "    </div>\n" +
         "</div>\n";
   }

   // Render the ingredients list
   private String renderRecipeIngredientsList(Recipe recipe){
      String content = "<div id='recipe_ingredients_list'>\n";

      // The logical "ingredients" separator
      content += "<div class='recipe_separator'>Ingredients:</div>\n";
      
      // List the ingredients
      IngredientsList ings = recipe.getIngredients();
      Vector<MeasurementAndIngredient> meaIngs = ings.getIngredients();

      // Iterate through the ingredients, rendering each one.
      for (int i=0; i<meaIngs.size(); i++){
         // Get the individual components out
         MeasurementAndIngredient recipeIng = meaIngs.get(i);

         content += renderRecipeIngredient(recipeIng);

      }
      content += "</div>\n"; // Closing dive for recipe_ingredients_list
      return content;
   }

   // Render an individual measurement and ingredient
   private String renderRecipeIngredient(MeasurementAndIngredient recipeIng){
      String content = "<div class='recipe_ingredient'>\n";
      
      Measurement mea = recipeIng.getMeasurement();
      Measurement mea2 = recipeIng.getMeasurement2();
      Ingredient ing = recipeIng.getIngredient();

      if (mea != null){
         content += renderRecipeIngredientMeasurement(mea);
      }
      if (mea2 != null){
         content += " plus " +
            renderRecipeIngredientMeasurement(mea2);
      }
      if (ing != null){
         content += renderRecipeIngredientProduct(ing);
      }

      content += "\n</div>\n"; // Closing div for recipe_ingredient

      return content;
   }

   // Render an individual measurement
   private String renderRecipeIngredientMeasurement(Measurement meas){
      String content = "";
      // Get the individual components
      String amount, specifier, unit;
      amount = meas.getAmount().toString();
      specifier = meas.getSpecifier().toString();
      unit = meas.getUnit().toString();

      if (!amount.equals("")){
         content += amount + " ";
      }
      if (!specifier.equals("")){
         content += specifier + " ";
      }
      if (!unit.equals("")){
         content += unit + " ";
      }

      return content;
   }

   // Render an individual ingredients product
   private String renderRecipeIngredientProduct(Ingredient ing){
      String content = "";
      String amazonUrl = ing.getAmazonUrl(); // Might be empty
      String productName = ing.getName();
      
      if (!amazonUrl.equals("")){
         // Use the amazon url
         content +=
            "<a href='" +
            ing.getAmazonUrl() +
            "'>" + productName + "</a>";
      }
      else{
         // No url, so just text
         content += productName;
      }

      // Get the special directions
      String specialDirections = ing.getSpecialDirections();
      if (!specialDirections.equals("")){
         // Don't add a space if we are just adding 
         if (specialDirections.charAt(0) != ','){
            content += " ";
         }
         content += specialDirections;
      }

      return content;
   }

   // Render the recipes directions
   private String renderRecipeDirections(Recipe recipe){
      String content = "<div id='recipe_directions'>\n";

      // The logical "Directions" separator
      content += "<div class='recipe_separator'>Directions:</div>\n";
      
      String directions = recipe.getDirections();

      // Make the newlines html friendly
      directions = directions.replaceAll("(\r\n|\n)", "<br/>");
      content += directions;

      content += "</div>\n"; // Closing div of recipe_directions
      
      return content;
   }

   // Render some links that we want under the recipes page
   private String renderEndRecipeLinks(int recId){
      String content = "<div id='recipe_links'>\n";

      // Link to the edit content for this recipe
      content += renderEndRecipeLink("edit", "Edit", recId);

      // Link to the remove page for this recipe
      content += renderEndRecipeLink("remove", "Remove", recId);
	
      // Link back to the listing
      content += renderEndRecipeLink("/", "Back to Listing", -1);

      content += "</div>\n"; // The closing div of recipe_links
      return content;
   }

   // Render an individual recipe link
   private String renderEndRecipeLink(String page, String visibleText, int recId){
      String content = "<div class='recipe_link'>\n";
      
      content += "<a href='?page=" + page;
      if (recId >= 0){
         content += "&recipe=" + recId;
      }
      content += "'>" + visibleText + "</a>\n";

      content += "</div>\n"; // The closing div of recipe_link
      return content;
   }
   
}
