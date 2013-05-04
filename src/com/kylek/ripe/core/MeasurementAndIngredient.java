/**
   (C) Kyle Kamperschroer 2013
*/

package com.kylek.ripe.core;

public class MeasurementAndIngredient {

   /////////////////////////////////////
   // Members
   /////////////////////////////////////

   // The measurement
   private Measurement mMeasurement;

   // The (optional) second measurement
   private Measurement mMeasurement2;

   // The ingredient
   private Ingredient mIngredient;

   /////////////////////////////////////
   // Constructors
   /////////////////////////////////////

   // Only id (required)
   public MeasurementAndIngredient(){
      mMeasurement = null;
      mMeasurement2 = null;
      mIngredient = null;
   }

   // All members
   public MeasurementAndIngredient(Measurement measurement,
                                   Measurement measurement2,
                                   Ingredient ingredient){
      mMeasurement = measurement;
      mMeasurement2 = measurement2;
      mIngredient = ingredient;
   }

   /////////////////////////////////////
   // Setters and getters
   /////////////////////////////////////

   public Measurement getMeasurement() { return mMeasurement; }
   public Measurement getMeasurement2() { return mMeasurement2; }
   public Ingredient getIngredient() { return mIngredient; }

   public void setMeasurement(Measurement measurement)
      { mMeasurement = measurement; }
   public void setMeasurement2(Measurement measurement)
      { mMeasurement2 = measurement; }
   public void setIngredient(Ingredient ingredient)
      { mIngredient = ingredient; }
}
