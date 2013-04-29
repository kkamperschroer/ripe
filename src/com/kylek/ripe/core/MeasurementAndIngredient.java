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

    // The ingredient
    private Ingredient mIngredient;

    /////////////////////////////////////
    // Constructors
    /////////////////////////////////////

    // Only id (required)
    public MeasurementAndIngredient()
    {
        mMeasurement = null;
        mIngredient = null;
    }

    // All members
    public MeasurementAndIngredient(Measurement measurement,
                                    Ingredient ingredient)
    {
        mMeasurement = measurement;
        mIngredient = ingredient;
    }

    /////////////////////////////////////
    // Setters and getters
    /////////////////////////////////////

    public Measurement getMeasurement() { return mMeasurement; }
    public Ingredient getIngredient() { return mIngredient; }

    public void setMeasurement(Measurement measurement)
    { mMeasurement = measurement; }
    public void setIngredient(Ingredient ingredient)
    { mIngredient = ingredient; }
}
