/**
(C) Kyle Kamperschroer 2013
 */

package com.kylek.ripe.core;

public class Measurement {

   ////////////////////////////////////
   // Members
   ////////////////////////////////////
    
   // The measurement amount
   private String mAmount;

   // The specifier
   private String mSpecifier;

   // The measurement unit
   private String mUnit;

   ////////////////////////////////////
   // Constructors
   ////////////////////////////////////

   // Default constructor
   public Measurement(){
      mAmount = "";
      mSpecifier = "";
      mUnit = "";
   }

   // All members
   public Measurement(String amount, String specifier, String unit){
      mAmount = amount;
      mSpecifier = specifier;
      mUnit = unit;
   }

   ////////////////////////////////////
   // Setters and getters
   ////////////////////////////////////

   public String getAmount() { return mAmount; }
   public String getSpecifier() { return mSpecifier; }
   public String getUnit() { return mUnit; }

   public void setAmount(String amount) { mAmount = amount; }
   public void setSpecifier(String specifier) { mSpecifier = specifier; }
   public void setUnit(String unit) { mUnit = unit; }

   ////////////////////////////////////
   // Other methods
   ////////////////////////////////////

   // None

   // TODO: Validate measurements
}
