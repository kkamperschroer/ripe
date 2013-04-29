/**
(C) Kyle Kamperschroer 2013
 */

package com.kylek.ripe.core;

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
    
    // The overall cook time
    private String mOverallTime;

    // The yield
    private Yield mYield;

    /////////////////////////////////////
    // Constructors
    /////////////////////////////////////

    // Default constructor
    public Recipe()
    {
        mName = "";
        mIngredients = null;
        mDirections = "";
        mPrepTime = "";
        mCookTime = "";
        mYield = null;;
    }

    // All members
    public Recipe(String name,
                  IngredientsList ingredients,
                  String directions,
                  String prepTime,
                  String cookTime,
                  Yield yield)
    {
        mName = name;
        mIngredients = ingredients;
        mDirections = directions;
        mPrepTime = prepTime;
        mCookTime = cookTime;
        mYield = yield;
    }

    /////////////////////////////////////
    // Setters and getters
    /////////////////////////////////////
    
    public String getName() { return mName; }
    public IngredientsList getIngredients() { return mIngredients; }
    public String getDirections() { return mDirections; }
    public String getPrepTime() { return mPrepTime; }
    public String getCookTime() { return mCookTime; }
    public String getOverallTime() { return mOverallTime; }
    public Yield getYield() { return mYield; }

    public void setName(String name) { mName = name; }
    public void setIngredients(IngredientsList ingredients)
    { mIngredients = ingredients; }
    public void setDirections(String directions) { mDirections = directions; }
    public void setPrepTime(String prepTime) { mPrepTime = prepTime; }
    public void setCookTime(String cookTime) { mCookTime = cookTime; }
    public void setOverallTime(String overallTime) { mOverallTime = overallTime; }
    public void setYield(Yield yield) { mYield = yield; }

    /////////////////////////////////////
    // Other methods
    /////////////////////////////////////

    // TODO - Validation

    // Equals
    public boolean equals(Recipe r)
    {
        return mName.equals(r.getName());
    }
}
