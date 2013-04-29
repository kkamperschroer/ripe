/**
(C) Kyle Kamperschroer 2013
 */

package com.kylek.ripe.core;

import java.util.Vector;

public class IngredientsList {

    /////////////////////////////////////
    // Members
    /////////////////////////////////////

    // The vector of measurements and ingredients
    private Vector<MeasurementAndIngredient> mIngredients;

    /////////////////////////////////////
    // Constructors
    /////////////////////////////////////

    // Default constructor
    public IngredientsList()
    {
        mIngredients = new Vector<MeasurementAndIngredient>();
    }

    // All members
    public IngredientsList(Vector<MeasurementAndIngredient> ingredients)
    {
        mIngredients = ingredients;
    }

    /////////////////////////////////////
    // Setters and getters
    /////////////////////////////////////

    public Vector<MeasurementAndIngredient> getIngredients()
    { return mIngredients; }

    public void setIngredients(Vector<MeasurementAndIngredient> ingredients)
    { mIngredients = ingredients; }

    /////////////////////////////////////
    // Other methods
    /////////////////////////////////////

    public boolean addIngredient(MeasurementAndIngredient ingredient)
    {
        if (mIngredients.contains(ingredient))
        {
            assert false; // Caller is mistaken! Fix it!
            return false;
        }
        // else
        return mIngredients.add(ingredient);
    }
}
