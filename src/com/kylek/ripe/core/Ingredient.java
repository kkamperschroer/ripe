/**
(C) Kyle Kamperschroer 2013
 */

package com.kylek.ripe.core;

public class Ingredient {

   ////////////////////////////////////
   // Members
   ////////////////////////////////////

   // The ingredients corresponding product (if any)
   private String mAmazonUrl;

   // The ingredient name
   private String mName;

   // Any special directions for this ingredient, like softened
   private String mSpecialDirections;

   ////////////////////////////////////
   // Constructor
   ////////////////////////////////////

   // Default constructor
   public Ingredient(){
      mAmazonUrl = "";
      mName = "";
      mSpecialDirections = "";
   }

   // All members
   public Ingredient(String amazonUrl, String name, String specialDirections){
      mAmazonUrl = amazonUrl;
      mName = name;
      mSpecialDirections = specialDirections;
   }

   // Just the ingredient and special directions
   public Ingredient(String name, String specialDirections){
      mName = name;
      mSpecialDirections = specialDirections;
   }

   ////////////////////////////////////
   // Setters and getters
   ////////////////////////////////////

   public String getAmazonUrl()             { return mAmazonUrl; }
   public String getName()                 { return mName; }
   public String getSpecialDirections()    { return mSpecialDirections; }

   public void setAmazonUrl(String amazonUrl) { mAmazonUrl = amazonUrl; }
   public void setName(String name)         { mName = name; }
   public void setSpecialDirections(String specialDirections)
      { mSpecialDirections = specialDirections; }

   ////////////////////////////////////
   // Other methods
   ////////////////////////////////////

   public boolean hasProduct(){
      return !mAmazonUrl.equals("");
   }
}
