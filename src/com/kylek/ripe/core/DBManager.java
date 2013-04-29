/**
(C) Kyle Kamperschroer 2013
 */

package com.kylek.ripe.core;

import java.util.List;

import com.db4o.*;

public class DBManager {

    /////////////////////////////////////
    // Members
    /////////////////////////////////////
    
    // The database reference
    private ObjectContainer mDb;

    /////////////////////////////////////
    // Constructors
    /////////////////////////////////////
    
    // The one and only constructor.
    public DBManager(String fileLocation)
    {
        mDb = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), fileLocation);
    }

    /////////////////////////////////////
    // Setters and getters
    /////////////////////////////////////
    
    // None needed. We don't want to allow much access here.

    /////////////////////////////////////
    // Other methods
    /////////////////////////////////////

    //// Public methods ////

    // Store a given recipe in the database
    public void storeRecipe(Recipe recipe)
    {
        mDb.store(recipe);
        mDb.commit();
    }
    
    // Remove a given recipe from the database
    public void removeRecipe(Recipe recipe)
    {
    	mDb.delete(recipe);
    	mDb.commit();
    }

    // Get all of the recipes
    public List<Recipe> getAllRecipes()
    {
        return mDb.query(Recipe.class);
    }

    /** Remove? 
    // Store a product in the database
    public void storeProduct(Product product)
    {
        mDb.store(product);
        mDb.commit();
    }

    // Remove a given recipe from the database
    public void removeProduct(Product product)
    {
    	mDb.delete(product);
    	mDb.commit();
    }
    
    // Get all of the products we have
    public List<Product> getAllProducts()
    {
        return mDb.query(Product.class);
    }
    **/

    // Close the db
    public void close()
    {
    	mDb.commit();
        mDb.close();
    }

    //// Private methods ////

    // None

}
