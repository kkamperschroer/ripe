/**
(C) Kyle Kamperschroer 2013
 */

package com.kylek.ripe.core;

public class Yield {

   ////////////////////////////////////////
   // Members
   ////////////////////////////////////////

   // The value
   private double mValue;

   // The unit
   private String mUnit;

   ////////////////////////////////////////
   // Constructor
   ////////////////////////////////////////

   // Base constructor
   public Yield(){
      mValue = 0.0;
      mUnit = "";
   }

   // Other constructor with all members
   public Yield(double value, String unit){
      mValue = value;
      mUnit = unit;
   }

   ////////////////////////////////////////
   // Accessors
   ////////////////////////////////////////

   // Gets
   public double getValue() { return mValue; }
   public String getUnit() { return mUnit; }

   // Sets
   public void setValue(double value) { mValue = value; }
   public void setUnit(String unit) { mUnit = unit; }
}
