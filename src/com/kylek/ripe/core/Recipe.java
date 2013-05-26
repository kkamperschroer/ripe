/**
   (C) Kyle Kamperschroer 2013
*/

package com.kylek.ripe.core;

import java.util.Vector;

public class Recipe {

   /////////////////////////////////////
   // Members
   /////////////////////////////////////

   // The recipe name
   private String mName;

   // The recipe ingredients list
   private IngredientsList mIngredients;

   // The recipe directions
   private String mDirections;

   // The recipe prep time
   private String mPrepTime;

   // The recipe cook time
   private String mCookTime;

   // The cool time
   private String mCoolTime;
    
   // The overall cook time
   private String mOverallTime;

   // The yield
   private Yield mYield;

   // Is this recipe public or private?
   private boolean mIsPublic;

   /////////////////////////////////////
   // Constructors
   /////////////////////////////////////

   // Default constructor
   public Recipe(){
      mName = "";
      mIngredients = new IngredientsList();
      mDirections = "";
      mPrepTime = "";
      mCookTime = "";
      mCoolTime = "";
      mOverallTime = "";
      mYield = new Yield();
      mIsPublic = false;
   }

   // All members
   public Recipe(String name,
                 IngredientsList ingredients,
                 String directions,
                 String prepTime,
                 String cookTime,
                 String coolTime,
                 String overallTime,
                 Yield yield,
                 boolean isPublic){
      mName = name;
      mIngredients = ingredients;
      mDirections = directions;
      mPrepTime = prepTime;
      mCookTime = cookTime;
      mCoolTime = coolTime;
      mOverallTime = overallTime;
      mYield = yield;
      mIsPublic = isPublic;
   }

   /////////////////////////////////////
   // Setters and getters
   /////////////////////////////////////
    
   public String getName() { return mName; }
   public IngredientsList getIngredients() { return mIngredients; }
   public String getDirections() { return mDirections; }
   public String getPrepTime() { return mPrepTime; }
   public String getCookTime() { return mCookTime; }
   public String getCoolTime() { return mCoolTime; }
   public String getOverallTime() { return mOverallTime; }
   public Yield getYield() { return mYield; }
   public boolean isPublic() { return mIsPublic; }

   public void setName(String name) { mName = name; }
   public void setIngredients(IngredientsList ingredients)
      { mIngredients = ingredients; }
   public void setDirections(String directions) { mDirections = directions; }
   public void setPrepTime(String prepTime) { mPrepTime = prepTime; }
   public void setCookTime(String cookTime) { mCookTime = cookTime; }
   public void setCoolTime(String coolTime) { mCoolTime = coolTime; }
   public void setOverallTime(String overallTime) { mOverallTime = overallTime; }
   public void setYield(Yield yield) { mYield = yield; }
   public void setIsPublic(boolean isPublic) { mIsPublic = isPublic; }

   /////////////////////////////////////
   // Other methods
   /////////////////////////////////////

   // TODO - Validation

   // Equals
   public boolean equals(Recipe r){
      return mName.equals(r.getName());
   }

   public String toString(){
      String str = "";
      
      str +=
         "Name: " + mName + "\n" +
         "Yield: " +  mYield.getValue() + " " + mYield.getUnit() + "\n" +
         "Prep time: " + mPrepTime + "\n" +
         "Cook time: " + mCookTime + "\n" +
         "Overall time: " + mOverallTime + "\n" +
         "Ingredients list: \n";

      // Iterate through the ingredients
      Vector<MeasurementAndIngredient> measAndIngs =
         mIngredients.getIngredients();

      MeasurementAndIngredient curMeasAndIng;
      Measurement curMeas;
      Measurement curMeas2;
      Ingredient curIng;
      for (int i=0; i<measAndIngs.size(); i++){
         curMeasAndIng = measAndIngs.get(i);
         curMeas = curMeasAndIng.getMeasurement();
         curMeas2 = curMeasAndIng.getMeasurement2();
         curIng = curMeasAndIng.getIngredient();
         str += i + ") ";
         if (curMeas != null){
            str +=
               "Measurment = " +
               curMeas.getAmount() + " " +
               curMeas.getSpecifier() + " " +
               curMeas.getUnit();
         }
         if (curMeas2 != null){
            str +=
               "Measurment 2 = " +
               curMeas2.getAmount() + " " +
               curMeas2.getSpecifier() + " " +
               curMeas2.getUnit();
         }
         if (curIng != null){
            str +=
               "    Ingredient = " +
               curIng.getName();
            String specDir = curIng.getSpecialDirections();
            if (specDir != null &&
                !specDir.equals("")){
               if (specDir.charAt(0) == ','){
                  str += curIng.getSpecialDirections();
               }
               else{
                  str += " " + curIng.getSpecialDirections();
               }
            }
               str += "\n";
         }
      }

      // Cool! All that's left is directions.
      str += "Directions: \n" + mDirections;

      // Return that new string.
      return str;
   }
}
