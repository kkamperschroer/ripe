/**
(C) Kyle Kamperschroer 2013
 */

package com.kylek.ripe.core;

public class TestDriver {

   // The hard coded recipe for testing things out
   private static final String TEST_RECIPE =
      "Baked Ziti I\n" +
      "Serves: 10\n" +
      "Prep Time: 35 Minutes\n" +
      "Cook Time: 20 Minutes\n" +
      "Ready In: 55 Minutes\n" +
      "1 pound dry ziti pasta\n" +
      "1 onion, chopped\n" +
      "1 pound lean ground beef\n" +
      "2 (26 ounce) jars spaghetti sauce\n" +
      "6 ounces provolone cheese, sliced\n" +
      "1 1/2 cups sour cream\n" +
      "6 ounces mozzarella cheese, shredded\n" +
      "2 tablespoons grated Parmesan cheese\n" +
      "Directions\n" +
      "1. Bring a large pot of lightly salted water to a boil. Add ziti pasta, and cook until al dente, about 8 minutes; drain.\n" +
      "2. In a large skillet, brown onion and ground beef over medium heat. Add spaghetti sauce, and simmer 15 minutes.\n" +
      "3. Preheat the oven to 350 degrees F (175 degrees C). Butter a 9x13 inch baking dish. Layer as follows: 1/2 of the ziti, Provolone cheese, sour cream, 1/2 sauce mixture, remaining ziti, mozzarella cheese and remaining sauce mixture. Top with grated Parmesan cheese.\n" +
      "4. Bake for 30 minutes in the preheated oven, or until cheeses are melted.\n";

/**
      "this tring of text should be the title\n" + 
      "serves 6 cups\n" +
      "cook time is 20 minutes\n" +
      "prep time is 1h overall time is 80mins\n" +
      "4 cups sweet potato, cubed\n" +
      "1/2 cup white sugar\n" +
      "2 eggs, beaten\n" +
      "1/2 teaspoon salt\n" +
      "4 tablespoons butter, softened\n" +
      "1/2 cup milk\n" +
      "1/2 teaspoon vanilla extract\n" +
      "1/2 cup packed brown sugar\n" +
      "1/3 cup all-purpose flour\n" +
      "3 tablespoons butter, softened\n" +
      "1/2 cup chopped pecans\n" +
      "directions and then some\neh?\n\nok then";
**/
                                                                                                                        
   public static void main(String[] args){
      // Get our WaxeyeParserManager
      WaxeyeParserManager wpm = new WaxeyeParserManager();

      final boolean success = wpm.parseString(TEST_RECIPE);
      
      Recipe builtRecipe;
      try {
         builtRecipe = wpm.getParsedRecipe();
      } catch (Exception e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }

      if (success){
         System.out.println("Recipe parsed successfully!");
      }else{
         System.out.println("Recipe did NOT parse successfully.");
      }

      // Try out the scaper
      String input[] = { "spaghetti sauce",
                         "milk",
                         "shredded mozzarella cheese"};
      for (int i=0; i<input.length; i++){
         System.out.println(input[i] + ": " + AmazonScraper.getProductUrl(input[i]));
      }
   }	
}
