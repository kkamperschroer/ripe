/**
(C) Kyle Kamperschroer 2013
 */

package com.kylek.ripe.core;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.waxeye.input.InputBuffer;
import org.waxeye.parser.ParseError;
import org.waxeye.parser.ParseResult;
import org.waxeye.ast.IAST;

// Import our generated parser and types
import com.kylek.ripe.waxeye.*;

public class WaxeyeParserManager {

   ////////////////////////////////////////
   // Members
   ////////////////////////////////////////

   // Our waxeye parser, generated from the grammar
   private Parser mParser;

   // Our parse result tree that we can iterate through
   private ParseResult<Type> mResults;

   // The input string we need to pull pieces out of
   private String mInputString;

   // A bool to determine if the last parse was successful
   private boolean mPreviousSuccess;

   // A string that represents our error message
   private String mErrorMessage;

   ////////////////////////////////////////
   // Constructors
   ////////////////////////////////////////

   // Default constructor
   public WaxeyeParserManager(){
      mParser = new Parser();
      mInputString = "";
      mResults = null;
      mPreviousSuccess = false;
      mErrorMessage = "";
   }

   ////////////////////////////////////////
   // Setters and getters
   ////////////////////////////////////////

   // Get the error message
   public String getErrorMessage() { return mErrorMessage; }
   
   ////////////////////////////////////////
   // Other methods
   ////////////////////////////////////////

   // A method for parsing
   public boolean parseString(String input){
      mInputString = input;
      InputBuffer inputBuffer = new InputBuffer(input.toCharArray());
      mResults = mParser.parse(inputBuffer);
      ParseError error = mResults.getError();
      if (error != null){
         // Get an error message ready
         mErrorMessage = "Parse Error:<br/><br/><pre>";
         
         // Cycle through some of the input string
         Scanner scanner = new Scanner(input);
         int count = 1; // Waxeye starts at 1
         int errLine = error.getLine();
         mErrorMessage += "<font color=\"gray\">\n";
         while (scanner.hasNextLine()) {
            String curLine = scanner.nextLine();
            if (count == errLine){
               // We want this line in red
               mErrorMessage += "</font>" +
                                "<font color=\"red\">" +
                                curLine +
                                "</font>\n";

               // Now we need to add a line to get to the correct column
               for (int i=1; i<error.getColumn(); i++){
                  mErrorMessage += " ";
               }

               mErrorMessage += "^ should be \"" +
                                error.getNT() +
                                "\"\n<font color=\"gray\">\n";
            }
            else{
               mErrorMessage += curLine + "\n";
            }
            count++;
         }
         mErrorMessage += "</pre>";
         
         mPreviousSuccess = false;
         return mPreviousSuccess;
      }else{
         System.out.println(mResults.toString());
         mErrorMessage = "";
         mPreviousSuccess = true;
         return mPreviousSuccess;
      }
   }

   // A method for getting out the built recipe object
   public Recipe getParsedRecipe() throws Exception{
      // Return null if the previous parse was not a success.
      if (!mPreviousSuccess){
         return null;
      }
      
      // Build the return recipe object
      Recipe retRecipe = new Recipe();

      // Navigate through the tree and build the Recipe.
      IAST<Type> root = mResults.getAST();

      // Get all of the children
      List<IAST<Type>> children = root.getChildren();

      // So now we have our children. Iterate through them, taking
      // care of each type as we see it.
      IAST<Type> curChild;
      for (int i=0; i<children.size(); i++){
         curChild = children.get(i);
         Type curType = curChild.getType();

         // Determine which high level type this is
         if (curType == Type.TITLE){
            retRecipe.setName(getTitle(curChild));
         }
         else if (curType == Type.ATTRIBUTES){
            // In the following call, retRecipe is passed by reference,
            // and updated by the method.
            getAttributes(curChild, retRecipe);
         }
         else if (curType == Type.INGREDIENTS_LIST){
            // In the following call, retRecipe is passed by reference,
            // and updated by the method.
            retRecipe.setIngredients(getIngredientsList(curChild));
         }
         else if (curType == Type.DIRECTIONS){
            retRecipe.setDirections(getDirections(curChild));
         }
      }

      // Return the built recipe
      return retRecipe;
   }

   // Get the title out of the parse
   private String getText(IAST<Type> iAST){
      // Get the start and end indices
      final int startIdx = iAST.getPosition().getStartIndex();
      final int endIdx = iAST.getPosition().getEndIndex();

      // Now return the information from the parsed string
      return mInputString.substring(startIdx, endIdx);
   }

   // Get the directions out of the recipe
   private String getDirections(IAST<Type> directionsChild){
      // Cycle through the children, looking for DIRECTIONS_TEXT
      List<IAST<Type>> children = directionsChild.getChildren();
      IAST<Type> curChild;
      for (int i=0; i<children.size(); i++){
         curChild = children.get(i);
         Type curChildType = curChild.getType();
         curChildType = curChild.getType();
         if (curChildType == Type.DIRECTIONS_TEXT){
            // Grab the text and return it
            return getText(curChild);
         }
         // The only other child is the directions header,
         // which we don't want to include, so ignore it.
      }
      // Shouldn't get here!
      return "Error parsing directions!";
   }

   // Get the title out of the recipe
   private String getTitle(IAST<Type> titleChild){
      // Grab the text from the node
      return getText(titleChild);
   }

   // Get the attributes out of the list and update the recipe
   private void getAttributes(IAST<Type> attributesChild,
                              Recipe recipe){
      // Cycle though the attributes, getting each one out
      List<IAST<Type>> curAttributesChildren = attributesChild.getChildren();
      List<IAST<Type>> attributesChildrensChildren;
      IAST<Type> curAttributesChild;
      IAST<Type> theActualAttributesAst;
      Type theActualAttributesType;
      // Iterate through the children
      for (int i=0; i<curAttributesChildren.size(); i++){
         curAttributesChild = curAttributesChildren.get(i);
         
         // Think of a better name yet?
         attributesChildrensChildren = curAttributesChild.getChildren();
         assert attributesChildrensChildren.size() == 1;
         theActualAttributesAst = attributesChildrensChildren.get(0);
         theActualAttributesType = theActualAttributesAst.getType();

         // Do whatever is necessary for this type
         if (theActualAttributesType == Type.YIELD){
            recipe.setYield(getYield(theActualAttributesAst));
         } else if (theActualAttributesType == Type.COOK_TIME){
            recipe.setCookTime(getCookTime(theActualAttributesAst));
         } else if (theActualAttributesType == Type.PREP_TIME){
            recipe.setPrepTime(getPrepTime(theActualAttributesAst));
         } else if (theActualAttributesType == Type.OVERALL_TIME){
            recipe.setOverallTime(getOverallTime(theActualAttributesAst));
         }
      }
   }

   // Get the yield information out of the current piece
   private Yield getYield(IAST<Type> yieldChild){
      // Yield tree looks like this for the phrase "Serves: 10 persons"
      // YIELD
      // - YIELD_PREFIX
      // -- Serves
      // - :
      // - AMOUNT
      // -- NUMBER_OR_FRACTION
      // --- 10
      // - UNIT
      // -- persons

      // Create our Yield object
      Yield yield = new Yield();

      // Get the yield children
      List<IAST<Type>> yieldChildren = yieldChild.getChildren();

      // Iterate through the children
      IAST<Type> curChild;
      for (int i=0; i<yieldChildren.size(); i++){
         curChild = yieldChildren.get(i);
         Type curType = curChild.getType();
         if (curType == Type.AMOUNT){
            String amountStr = getText(curChild);
            double amountDbl = Double.parseDouble(amountStr);
            yield.setValue(amountDbl);
         }
         else if (curType == Type.UNIT){
            yield.setUnit(getText(curChild));
         }
      }

      // Return out built yield.
      return yield;
   }

   // Get the cook time out of the current piece
   private String getCookTime(IAST<Type> cookTimeChild){
      // Cook time tree looks like this for "Cook time: 20 Minutes"
      // COOK_TIME
      // - COOK_TIME_PREFIX
      // -- CookTime
      // - :
      // - AMOUNT
      // -- NUMBER_OR_FRACTION
      // --- 20
      // - TIME_UNITS
      // -- Minutes
      List<IAST<Type>> cookTimeChildren = cookTimeChild.getChildren();

      // Ignore the prefix. All we want is the concatenation of amount and units
      assert(cookTimeChildren.size() >= 4);
      String cookTime = getText(cookTimeChildren.get(2));
      cookTime += " " + getText(cookTimeChildren.get(3));

      // Return our built time
      return cookTime;
   }

   // Get the prep time out of the current piece
   private String getPrepTime(IAST<Type> prepTimeChild){
      // The tree for prep is the same as cook, so use the same
      // method
      return getCookTime(prepTimeChild);
   }

   // Get the overall time out of the current piece
   private String getOverallTime(IAST<Type> overallTimeChild){
      // The tree for overall is the same as cook, so use the
      // same method.
      return getCookTime(overallTimeChild);
   }

   // Get the ingredients list and update the given recipe
   private IngredientsList getIngredientsList(IAST<Type> ingredientsListChild){
      // The ingredients list tree looks something like this:
      // - INGREDIENTS_LIST
      // -- INGREDIENT
      // --- MEASUREMENT
      // ---- AMOUNT
      // ----- 2
      // ---- SPECIFIER (opt)
      // ----- ( (opt)
      // ------ MEASUREMENT
      // ------- AMOUNT
      // -------- NUMBER_OR_FRACTION
      // --------- 26
      // ------- UNIT
      // -------- ounce
      // ------ ) (opt)
      // ---- UNIT (opt)
      // ----- jars
      // --- PRODUCT
      // ---- WORDS
      // ----- spaghetti sauce
      // -- INGREDIENT
      // ... repeats

      // Build the ingredients list object, which will contain
      // all of the ingredients.
      IngredientsList ingList = new IngredientsList();
      
      // Let's go through each ingredient, getting each part
      List<IAST<Type>> ingredients = ingredientsListChild.getChildren();

      // Iterate through, grabbing the ingredient for each
      // building up our list.
      IAST<Type> curIngredient;
      for (int i=0; i<ingredients.size(); i++){
         curIngredient = ingredients.get(i);
         if (curIngredient.getType() == Type.INGREDIENT){
            // Now get the actual ingredient from this child.
            MeasurementAndIngredient measAndIng =
               getMeasurementAndIngredient(curIngredient);
            if (measAndIng != null){
               ingList.addIngredient(measAndIng);
            }
         }
      }
      return ingList;
   }

   // A method for grabbing a MeasurementAndIngredient from a given child.
   private MeasurementAndIngredient getMeasurementAndIngredient(
      IAST<Type> measurementAndIngredientChild){
      // Just for reference, here is what our tree is at this point:
      // - INGREDIENT
      // -- MEASUREMENT
      // --- AMOUNT
      // ---- 2
      // --- SPECIFIER (opt)
      // ---- ( (opt)
      // ----- MEASUREMENT
      // ------ AMOUNT
      // ------- NUMBER_OR_FRACTION
      // -------- 26
      // ------ UNIT
      // ------- ounce
      // ----- ) (opt)
      // --- UNIT (opt)
      // ---- jars
      // -- PRODUCT
      // --- WORDS
      // ---- spaghetti sauce

      // Build the MeasurementAndIngredient object we will return
      MeasurementAndIngredient measAndIng = new MeasurementAndIngredient();
      
      // Get the children of our current node
      List<IAST<Type>> children = measurementAndIngredientChild.getChildren();

      // Iterate through!
      IAST<Type> curChild;
      for (int i=0; i<children.size(); i++){
         curChild = children.get(i);
         Type curChildType = curChild.getType();

         if (curChildType == Type.MEASUREMENT){
            measAndIng.setMeasurement(getMeasurement(curChild));
         }
         else if (curChildType == Type.PRODUCT){
            Ingredient retIng = getIngredient(curChild);
            // It's possible it's null, if the "ingredient" is something
            // like "Directions" or "Ingredients"
            if (retIng != null){
               measAndIng.setIngredient(retIng);
            }else{
               // This likely isn't a real ingredient, so ignore it.
               return null;
            }
         }else if (curChildType == Type.SPECIAL_DIRECTIONS){
            measAndIng.getIngredient().setSpecialDirections(getText(curChild));
         }
      }

      // Return our built MeasurementAndIngredient
      return measAndIng;
   }

   // A method for getting the measurement from a given child
   private Measurement getMeasurement(IAST<Type> measurementChild){
      // At this point, our tree looks something like this:
      // - MEASUREMENT
      // -- AMOUNT
      // --- 2
      // -- SPECIFIER (opt)
      // --- ( (opt)
      // ---- MEASUREMENT
      // ----- AMOUNT
      // ------ NUMBER_OR_FRACTION
      // ------- 26
      // ----- UNIT
      // ------ ounce
      // ---- ) (opt)
      // -- UNIT (opt)
      // --- jars

      // Build the Measurement object we will return
      Measurement meas = new Measurement();

      // Get our children
      List<IAST<Type>> children = measurementChild.getChildren();

      // With certain children being optional, it's likely best
      // to iterate through, dealing with each type we see instead
      // of making assumptions about positioning.

      IAST<Type> curChild;
      for (int i=0; i<children.size(); i++){
         curChild = children.get(i);
         Type curChildType = curChild.getType();

         if (curChildType == Type.AMOUNT){
            meas.setAmount(getText(curChild));
         }
         else if (curChildType == Type.SPECIFIER){
            meas.setSpecifier(getText(curChild));
         }
         else if (curChildType == Type.UNIT){
            meas.setUnit(getText(curChild));
         }
      }

      // Return our built up Measurement
      return meas;
   }

   // A method for getting the product/ingredient from a child
   private Ingredient getIngredient(IAST<Type> ingredientChild){
      // At this point, our tree looks something like this:
      // - PRODUCT
      // -- WORDS
      // --- spaghetti sauce

      // Build our ingredient object that we will return
      Ingredient ing = new Ingredient();

      // Get all of the children
      List<IAST<Type>> children = ingredientChild.getChildren();

      // Iterate through the children
      IAST<Type> curChild;
      for (int i=0; i<children.size(); i++){
         curChild = children.get(i);
         Type curChildType = curChild.getType();

         if (curChildType == Type.WORDS_NO_BREAK){
            String curName = getText(curChild);
            if (shouldIgnoreIngredient(curName)){
               return null; // We don't want this so called ingredient.
            }
            ing.setName(curName);
         }
      }

      // Get the product from Amazon, if we can
      ing.setAmazonUrl(AmazonScraper.getProductUrl(ing.getName()));

      // Return our built ingredient
      return ing;
   }

   // A method to check if this "ingredient" matches something we want to
   // ignore.
   private boolean shouldIgnoreIngredient(String curIng){
      final String ingredientsPattern =
         "[I|i]ngriedient(s)?(:)? (List(:)?)?";
      final String directionsPattern =
         "[D|d]irection(s)?(:)?";

      Pattern pattern = Pattern.compile(ingredientsPattern);
      Matcher matcher = pattern.matcher(curIng);
      if (matcher.find()){
         return true; // We should ignore this "ingredient"
      }
      pattern = Pattern.compile(directionsPattern);
      matcher = pattern.matcher(curIng);
      if (matcher.find()){
         return true; // Again, we should ignore this.
      }

      // Ok, it seems legitimate. Let it through.
      return false;
   }
}
