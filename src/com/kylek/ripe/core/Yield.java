/**
(C) Kyle Kamperschroer 2013
 */

package com.kylek.ripe.core;

public class Yield {

   ////////////////////////////////////////
   // Members
   ////////////////////////////////////////

   // The value
   private String mValue;

   // The unit
   private String mUnit;

   ////////////////////////////////////////
   // Constructor
   ////////////////////////////////////////

   // Base constructor
   public Yield(){
      mValue = "";
      mUnit = "";
   }

   // Other constructor with all members
   public Yield(String value, String unit){
      mValue = value;
      mUnit = unit;
   }

   ////////////////////////////////////////
   // Accessors
   ////////////////////////////////////////

   // Gets
   public String getValue() { return mValue; }
   public String getUnit() { return mUnit; }

   // Sets
   public void setValue(String value) { mValue = value; }
   public void setUnit(String unit) { mUnit = unit; }
}
