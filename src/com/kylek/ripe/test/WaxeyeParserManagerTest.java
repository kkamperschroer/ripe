package com.kylek.ripe.test;

import com.kylek.ripe.core.Recipe;
import com.kylek.ripe.core.WaxeyeParserManager;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class WaxeyeParserManagerTest {

   private final static String[][] recipes = {
      /** Recipe 1 **/
      {
         /** Input string **/
         
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
         "4. Bake for 30 minutes in the preheated oven, or until cheeses are melted.\n",

         /** Expected toString **/

         "Name: Baked Ziti I\n" +
         "Yield: 10.0 \n" +
         "Prep time: 35 Minutes\n" +
         "Cook time: 20 Minutes\n" +
         "Overall time: 55 Minutes\n" +
         "Ingredients list: \n" +
         "0) Measurment = 1  pound    Ingredient = dry ziti pasta \n" +
         "1) Measurment = 1      Ingredient = onion, chopped\n" +
         "2) Measurment = 1  pound    Ingredient = lean ground beef \n" +
         "3) Measurment = 2 (26 ounce) jars    Ingredient = spaghetti sauce \n" +
         "4) Measurment = 6  ounces    Ingredient = provolone cheese, sliced\n" +
         "5) Measurment = 1 1/2  cups    Ingredient = sour cream \n" +
         "6) Measurment = 6  ounces    Ingredient = mozzarella cheese, shredded\n" +
         "7) Measurment = 2  tablespoons    Ingredient = grated Parmesan cheese \n" +
         "Directions: \n" +
         "1. Bring a large pot of lightly salted water to a boil. Add ziti pasta, and cook until al dente, about 8 minutes; drain.\n" +
         "2. In a large skillet, brown onion and ground beef over medium heat. Add spaghetti sauce, and simmer 15 minutes.\n" +
         "3. Preheat the oven to 350 degrees F (175 degrees C). Butter a 9x13 inch baking dish. Layer as follows: 1/2 of the ziti, Provolone cheese, sour cream, 1/2 sauce mixture, remaining ziti, mozzarella cheese and remaining sauce mixture. Top with grated Parmesan cheese.\n" +
         "4. Bake for 30 minutes in the preheated oven, or until cheeses are melted."
      },        

      /** Recipe 2 **/
      {

         /** Input string **/
         "Shiitake Mushroom and Potato Enchiladas\n" +
         "\n" +
         "Serves 4| Hands-On Time: 30m| Total Time: 45m\n" +
         "\n" +
         "Ingredients\n" +
         "1 	 ounce 	 dried shiitake mushrooms (about 1 cup)\n" +
         "2 	 tablespoons 	 olive oil\n" +
         "1 	 white onion, chopped\n" +
         "1 	 large russet potato (about 8 ounces), peeled and diced into 1/4-inch pieces\n" +
         "1 	 cup 	 frozen corn, thawed\n" +
         "kosher salt and black pepper\n" +
         "2 	 cups 	 grated Monterey Jack (about 8 ounces)\n" +
         "1 	 14-ounce can green chili enchilada sauce (about 1 1/2 cups)\n" +
         "8 	 corn tortillas, warmed\n" +
         "fresh cilantro, for serving\n" +
         "\n" +
         "Directions\n" +
         "Heat oven to 400° F. Combine the mushrooms and 2 cups boiling water in a small bowl and let sit until softened, 20 to 30 minutes. Remove the mushrooms and chop; reserve the soaking liquid.\n" +
         "Meanwhile, heat the oil in a large nonstick skillet over medium-high heat. Add the onion and potato and cook, tossing often, until the potato begins to soften, 6 to 8 minutes. Add the corn, mushrooms, 1 cup of the soaking liquid, ½ teaspoon salt, and ¼ teaspoon pepper. Reduce heat to medium and cook, stirring often, until the potatoes are tender and most of the liquid is absorbed, 6 to 8 minutes. Transfer to a large bowl and stir in 1 cup of the Monterey Jack.\n" +
         "Spread ½ cup of the enchilada sauce in the bottom of a 9-by-13-inch baking dish. Dividing evenly, roll up the mushroom mixture in the tortillas and place seam-side down in the dish. Top with the remaining enchilada sauce and sprinkle with the remaining Monterey Jack. Bake until the sauce is bubbling and the Monterey Jack is melted, 12 to 15 minutes. Sprinkle with the cilantro.\n",

         /** Expected output **/
         "Name: Shiitake Mushroom and Potato Enchiladas\n" +
         "Yield: 4.0 \n" +
         "Prep time: 30 m\n" +
         "Cook time: \n" +
         "Overall time: 45 m\n" +
         "Ingredients list: \n" +
         "0) Measurment = 1  ounce    Ingredient = dried shiitake mushrooms (about 1 cup)\n" +
         "1) Measurment = 2  tablespoons    Ingredient = olive oil \n" +
         "2) Measurment = 1      Ingredient = white onion, chopped\n" +
         "3) Measurment = 1  large    Ingredient = russet potato (about 8 ounces), peeled and diced into 1/4-inch pieces\n" +
         "4) Measurment = 1  cup    Ingredient = frozen corn, thawed\n" +
         "5)     Ingredient = kosher salt and black pepper \n" +
         "6) Measurment = 2  cups    Ingredient = grated Monterey Jack (about 8 ounces)\n" +
         "7) Measurment = 1 14-ounce can    Ingredient = green chili enchilada sauce (about 1 1/2 cups)\n" +
         "8) Measurment = 8      Ingredient = corn tortillas, warmed\n" +
         "9)     Ingredient = fresh cilantro, for serving\n" +
         "Directions: \n" +
         "Heat oven to 400° F. Combine the mushrooms and 2 cups boiling water in a small bowl and let sit until softened, 20 to 30 minutes. Remove the mushrooms and chop; reserve the soaking liquid.\n" +
         "Meanwhile, heat the oil in a large nonstick skillet over medium-high heat. Add the onion and potato and cook, tossing often, until the potato begins to soften, 6 to 8 minutes. Add the corn, mushrooms, 1 cup of the soaking liquid, ½ teaspoon salt, and ¼ teaspoon pepper. Reduce heat to medium and cook, stirring often, until the potatoes are tender and most of the liquid is absorbed, 6 to 8 minutes. Transfer to a large bowl and stir in 1 cup of the Monterey Jack.\n" +
         "Spread ½ cup of the enchilada sauce in the bottom of a 9-by-13-inch baking dish. Dividing evenly, roll up the mushroom mixture in the tortillas and place seam-side down in the dish. Top with the remaining enchilada sauce and sprinkle with the remaining Monterey Jack. Bake until the sauce is bubbling and the Monterey Jack is melted, 12 to 15 minutes. Sprinkle with the cilantro."
      },
      
      /** Recipe 3 **/
      {
         /** Input string **/
         
         "Quick and Easy Green Chile Chicken Enchilada Casserole\n" +
         "\n" +
         "Prep Time: 30 Minutes\n" +
         "Cook Time: 1 Hour 30 Minutes\n" +
         "Ready In: 2 Hours\n" +
         "Servings: 8\n" +
         "\n" +
         "INGREDIENTS:\n" +
         "4 skinless, boneless chicken breast halves\n" +
         "garlic salt to taste\n" +
         "18 (6 inch) corn tortillas, torn in half\n" +
         "1 (28 ounce) can green chile enchilada sauce\n" +
         "1 (16 ounce) package shredded Monterey Jack cheese\n" +
         "1 (8 ounce) container reduced fat sour cream\n" +
         "DIRECTIONS:\n" +
         "1.	Preheat oven to 350 degrees F (175 degrees C). Lightly grease a medium baking dish.\n" +
         "2.	Season chicken with garlic salt. Arrange in the prepared baking dish. Bake 45 minutes in the preheated oven, until no longer pink and juices run clear. Cool, shred, and set aside.\n" +
         "3.	With metal tongs, char each tortilla half over the open flame of a gas stove burner for about 1 minute, until lightly puffed.\n" +
         "4.	Pour about 1/2 inch enchilada sauce in the bottom of a medium baking dish, and arrange 6 tortillas in a single layer. Top with 1/2 the chicken, 1/3 cheese, 1/2 the sour cream, and 1/3 of the remaining enchilada sauce. Repeat. Coat remaining tortillas thoroughly with remaining enchilada sauce, and arrange on top of the layers. Sprinkle with remaining cheese, and top with any remaining enchilada sauce\n" +
         "5.	Cover, and bake 45 minutes in the preheated oven. Cool slightly before serving.\n",

         /** Expected output **/

         "Name: Quick and Easy Green Chile Chicken Enchilada Casserole\n" +
         "Yield: 8.0 \n" +
         "Prep time: 30 Minutes\n" +
         "Cook time: 1 Hour 30 Minutes\n" +
         "Overall time: 2 Hours\n" +
         "Ingredients list: \n" +
         "0) Measurment = 4      Ingredient = skinless, boneless chicken breast halves\n" +
         "1)     Ingredient = garlic salt to taste \n" +
         "2) Measurment = 18 (6 inch)     Ingredient = corn tortillas, torn in half\n" +
         "3) Measurment = 1 (28 ounce) can    Ingredient = green chile enchilada sauce \n" +
         "4) Measurment = 1 (16 ounce) package    Ingredient = shredded Monterey Jack cheese \n" +
         "5) Measurment = 1 (8 ounce) container    Ingredient = reduced fat sour cream \n" +
         "Directions: \n" +
         "1.	Preheat oven to 350 degrees F (175 degrees C). Lightly grease a medium baking dish.\n" +
         "2.	Season chicken with garlic salt. Arrange in the prepared baking dish. Bake 45 minutes in the preheated oven, until no longer pink and juices run clear. Cool, shred, and set aside.\n" +
         "3.	With metal tongs, char each tortilla half over the open flame of a gas stove burner for about 1 minute, until lightly puffed.\n" +
         "4.	Pour about 1/2 inch enchilada sauce in the bottom of a medium baking dish, and arrange 6 tortillas in a single layer. Top with 1/2 the chicken, 1/3 cheese, 1/2 the sour cream, and 1/3 of the remaining enchilada sauce. Repeat. Coat remaining tortillas thoroughly with remaining enchilada sauce, and arrange on top of the layers. Sprinkle with remaining cheese, and top with any remaining enchilada sauce\n" +
         "5.	Cover, and bake 45 minutes in the preheated oven. Cool slightly before serving."
      },

      /** Recipe 4 **/
      {
         /** Input string **/
         "Pan-Seared Salmon with Pumpkin Seed-Cilantro Pesto\n" +
         "yield: Makes 4 servings\n" +
         "active time: 25 minutes\n" +
         "total time: 25 minutes\n" +
         "\n" +
         "ingredients\n" +
         "2 1/2 teaspoons plus 1/4 cup extra-virgin olive oil, divided\n" +
         "1/2 cup shelled pumpkin seeds (pepitas)\n" +
         "1/2 cup (tightly packed) cilantro leaves and stems\n" +
         "1/2 teaspoon cracked coriander seeds\n" +
         "1/2 garlic clove, coarsely chopped\n" +
         "1 tablespoon (or more) fresh lime juice\n" +
         "Kosher salt and freshly ground black pepper\n" +
         "4 6-ounce salmon fillets (preferably wild)\n" +
         "1 lime, cut into 4 wedges\n" +
         "\n" +
         "preparation\n" +
         "Heat 1 1/2 teaspoons oil in a large nonstick skillet over medium-high heat. Add pumpkin seeds; sauté until beginning to brown and pop, about 2 minutes. Transfer seeds to paper towels to drain; let cool. Reserve skillet.\n" +
         "Pulse 6 tablespoons pumpkin seeds, cilantro, coriander seeds, and garlic in a food processor until coarsely chopped. With machine running, gradually add 1 tablespoon lime juice, 1/4 cup oil, then 1/4 cup water, blending until coarse purée forms. Season pesto to taste with salt, pepper, and more lime juice, if desired.\n" +
         "Heat remaining 1 teaspoon oil in reserved skillet over medium heat. Season salmon fillets with salt and pepper. Add to skillet and cook until just opaque in center, 3-4 minutes per side. Place fillets on plates. Spoon pesto over. Garnish with remaining pumpkin seeds. Serve with lime wedges.",

         /** Expected output **/
         "Name: Pan-Seared Salmon with Pumpkin Seed-Cilantro Pesto\n" +
         "Yield: 4.0 servings\n" +
         "Prep time: 25 minutes\n" +
         "Cook time: \n" +
         "Overall time: 25 minutes\n" +
         "Ingredients list: \n" +
         "0) Measurment = 2 1/2  teaspoonsMeasurment 2 = 1/4  cup    Ingredient = extra-virgin olive oil, divided\n" +
         "1) Measurment = 1/2  cup    Ingredient = shelled pumpkin seeds (pepitas)\n" +
         "2) Measurment = 1/2  cup    Ingredient = cilantro leaves and stems \n" +
         "3) Measurment = 1/2  teaspoon    Ingredient = cracked coriander seeds \n" +
         "4) Measurment = 1/2      Ingredient = garlic clove, coarsely chopped\n" +
         "5) Measurment = 1  tablespoon    Ingredient = fresh lime juice \n" +
         "6)     Ingredient = Kosher salt and freshly ground black pepper \n" +
         "7) Measurment = 4 6-ounce     Ingredient = salmon fillets (preferably wild)\n" +
         "8) Measurment = 1      Ingredient = lime, cut into 4 wedges\n" +
         "Directions: \n" +
         "Heat 1 1/2 teaspoons oil in a large nonstick skillet over medium-high heat. Add pumpkin seeds; sauté until beginning to brown and pop, about 2 minutes. Transfer seeds to paper towels to drain; let cool. Reserve skillet.\n" +
         "Pulse 6 tablespoons pumpkin seeds, cilantro, coriander seeds, and garlic in a food processor until coarsely chopped. With machine running, gradually add 1 tablespoon lime juice, 1/4 cup oil, then 1/4 cup water, blending until coarse purée forms. Season pesto to taste with salt, pepper, and more lime juice, if desired.\n" +
         "Heat remaining 1 teaspoon oil in reserved skillet over medium heat. Season salmon fillets with salt and pepper. Add to skillet and cook until just opaque in center, 3-4 minutes per side. Place fillets on plates. Spoon pesto over. Garnish with remaining pumpkin seeds. Serve with lime wedges."
      },

      /** Recipe 5 **/
      {
         "Mom's Chicken Cacciatore\n" +
         "Servings: 8\n" +
         "INGREDIENTS:\n" +
         "2 cups all-purpose flour for coating\n" +
         "1/2 teaspoon salt\n" +
         "1/4 teaspoon ground black pepper\n" +
         "1 (4 pound) chicken, cut into pieces\n" +
         "2 tablespoons vegetable oil\n" +
         "1 onion, chopped\n" +
         "2 cloves garlic, minced\n" +
         "1 green bell pepper, chopped\n" +
         "1 (14.5 ounce) can diced tomatoes\n" +
         "1/2 teaspoon dried oregano\n" +
         "1/2 cup white wine\n" +
         "2 cups fresh mushrooms, quartered\n" +
         "salt and pepper to taste\n" +
         "DIRECTIONS:\n" +
         "1.	Combine the flour, salt and pepper in a plastic bag. Shake the chicken pieces in flour until coated. Heat the oil in a large skillet (one that has a cover/lid). Fry the chicken pieces until they are browned on both sides. Remove from skillet.\n" +
         "2.	Add the onion, garlic and bell pepper to the skillet and saute until the onion is slightly browned. Return the chicken to the skillet and add the tomatoes, oregano and wine. Cover and simmer for 30 minutes over medium low heat.\n" +
         "3.	Add the mushrooms and salt and pepper to taste. Simmer for 10 more minutes.\n",

         /** Expected output **/

         "Name: Mom's Chicken Cacciatore\n" +
         "Yield: 8.0 \n" +
         "Prep time: \n" +
         "Cook time: \n" +
         "Overall time: \n" +
         "Ingredients list: \n" +
         "0) Measurment = 2  cups    Ingredient = all-purpose flour for coating\n" +
         "1) Measurment = 1/2  teaspoon    Ingredient = salt \n" +
         "2) Measurment = 1/4  teaspoon    Ingredient = ground black pepper \n" +
         "3) Measurment = 1 (4 pound)     Ingredient = chicken, cut into pieces\n" +
         "4) Measurment = 2  tablespoons    Ingredient = vegetable oil \n" +
         "5) Measurment = 1      Ingredient = onion, chopped\n" +
         "6) Measurment = 2      Ingredient = cloves garlic, minced\n" +
         "7) Measurment = 1      Ingredient = green bell pepper, chopped\n" +
         "8) Measurment = 1 (14.5 ounce) can    Ingredient = diced tomatoes \n" +
         "9) Measurment = 1/2  teaspoon    Ingredient = dried oregano \n" +
         "10) Measurment = 1/2  cup    Ingredient = white wine \n" +
         "11) Measurment = 2  cups    Ingredient = fresh mushrooms, quartered\n" +
         "12)     Ingredient = salt and pepper to taste \n" +
         "Directions: \n" +
         "1.	Combine the flour, salt and pepper in a plastic bag. Shake the chicken pieces in flour until coated. Heat the oil in a large skillet (one that has a cover/lid). Fry the chicken pieces until they are browned on both sides. Remove from skillet.\n" +
         "2.	Add the onion, garlic and bell pepper to the skillet and saute until the onion is slightly browned. Return the chicken to the skillet and add the tomatoes, oregano and wine. Cover and simmer for 30 minutes over medium low heat.\n" +
         "3.	Add the mushrooms and salt and pepper to taste. Simmer for 10 more minutes.\n"
      },

      /** Recipe 6 **/
      {
         "Wazzu Tailgate Chili\n" +
         "Prep Time: 30 Minutes\n" +
         "Cook Time: 12 Hours 30 Minutes\n" +
         "Ready In: 23 Hours\n" +
         "Servings: 8\n" +
         "INGREDIENTS:\n" +
         "1 pound ground beef\n" +
         "1 pound ground pork\n" +
         "2 tablespoons olive oil\n" +
         "1 large onion, chopped, divided\n" +
         "1 green bell pepper, chopped\n" +
         "1 habanero peppers, seeded and minced\n" +
         "2 jalapeno pepper, seeded and minced\n" +
         "3 cloves garlic, minced\n" +
         "3 tablespoons chopped green onion\n" +
         "3 (15 ounce) cans chili beans\n" +
         "1 (14.5 ounce) can diced tomatoes\n" +
         "1 (6 ounce) can tomato paste\n" +
         "1 (8 ounce) can tomato sauce\n" +
         "1 (12 ounce) bottle lager-style beer\n" +
         "2 tablespoons cornmeal\n" +
         "1 cup water\n" +
         "1/4 cup chili powder\n" +
         "1 tablespoon ground cumin\n" +
         "1 teaspoon garlic powder\n" +
         "1/2 teaspoon cayenne pepper\n" +
         "1 tablespoon salt\n" +
         "1 1/2 teaspoons ground black pepper\n" +
         "1 cup shredded Cheddar cheese\n" +
         "DIRECTIONS:\n" +
         "1.	Cook ground beef and pork in a large skillet over medium-high heat until the meat is crumbly, evenly browned, and no longer pink. Drain and discard any excess grease.\n" +
         "2.	Meanwhile, heat the olive oil in a large pot over medium heat. Stir in 3/4 of the onion and all of the green pepper, habanero pepper, jalapeno pepper, and garlic. Cook and stir until the onion has softened and turned translucent, about 5 minutes. Stir the drained meat into the onion mixture along with the green onion, chili beans, diced tomatoes, tomato paste, tomato sauce, beer, and water. Sprinkle with the cornmeal, then season with chili powder, cumin, garlic powder, cayenne pepper, salt, and black pepper.\n" +
         "3.	Bring to a simmer over medium heat, then reduce heat to medium-low. Simmer at least 2 hours, stirring occasionally. Refrigerate overnight.\n" +
         "4.	Reheat the chili over medium heat until it begins to simmer again. Top individual servings of chili with cheese and remaining chopped onion.\n",

         /** Expected output **/
         "Name: Wazzu Tailgate Chili\n" +
         "Yield: 8.0 \n" +
         "Prep time: 30 Minutes\n" +
         "Cook time: 12 Hours 30 Minutes\n" +
         "Overall time: 23 Hours\n" +
         "Ingredients list: \n" +
         "0) Measurment = 1  pound    Ingredient = ground beef\n" +
         "1) Measurment = 1  pound    Ingredient = ground pork\n" +
         "2) Measurment = 2  tablespoons    Ingredient = olive oil\n" +
         "3) Measurment = 1  large    Ingredient = onion, chopped, divided\n" +
         "4) Measurment = 1      Ingredient = green bell pepper, chopped\n" +
         "5) Measurment = 1      Ingredient = habanero peppers, seeded and minced\n" +
         "6) Measurment = 2      Ingredient = jalapeno pepper, seeded and minced\n" +
         "7) Measurment = 3      Ingredient = cloves garlic, minced\n" +
         "8) Measurment = 3  tablespoons    Ingredient = chopped green onion\n" +
         "9) Measurment = 3 (15 ounce) cans    Ingredient = chili beans\n" +
         "10) Measurment = 1 (14.5 ounce) can    Ingredient = diced tomatoes\n" +
         "11) Measurment = 1 (6 ounce) can    Ingredient = tomato paste\n" +
         "12) Measurment = 1 (8 ounce) can    Ingredient = tomato sauce\n" +
         "13) Measurment = 1 (12 ounce)     Ingredient = bottle lager-style beer\n" +
         "14) Measurment = 2  tablespoons    Ingredient = cornmeal\n" +
         "15) Measurment = 1  cup    Ingredient = water\n" +
         "16) Measurment = 1/4  cup    Ingredient = chili powder\n" +
         "17) Measurment = 1  tablespoon    Ingredient = ground cumin\n" +
         "18) Measurment = 1  teaspoon    Ingredient = garlic powder\n" +
         "19) Measurment = 1/2  teaspoon    Ingredient = cayenne pepper\n" +
         "20) Measurment = 1  tablespoon    Ingredient = salt\n" +
         "21) Measurment = 1 1/2  teaspoons    Ingredient = ground black pepper\n" +
         "22) Measurment = 1  cup    Ingredient = shredded Cheddar cheese\n" +
         "Directions: \n" +
         "1.	Cook ground beef and pork in a large skillet over medium-high heat until the meat is crumbly, evenly browned, and no longer pink. Drain and discard any excess grease.\n" +
         "2.	Meanwhile, heat the olive oil in a large pot over medium heat. Stir in 3/4 of the onion and all of the green pepper, habanero pepper, jalapeno pepper, and garlic. Cook and stir until the onion has softened and turned translucent, about 5 minutes. Stir the drained meat into the onion mixture along with the green onion, chili beans, diced tomatoes, tomato paste, tomato sauce, beer, and water. Sprinkle with the cornmeal, then season with chili powder, cumin, garlic powder, cayenne pepper, salt, and black pepper.\n" +
         "3.	Bring to a simmer over medium heat, then reduce heat to medium-low. Simmer at least 2 hours, stirring occasionally. Refrigerate overnight.\n" +
         "4.	Reheat the chili over medium heat until it begins to simmer again. Top individual servings of chili with cheese and remaining chopped onion."
      }
   };

      
   @Test
   public void testRecipes(){
      // Create the parser
      WaxeyeParserManager wpm = new WaxeyeParserManager();

      // Loop through, parsing each and validating the strings (char by char)
      Recipe curRec = null;
      for (int i=0; i<recipes.length; i++){
         System.out.println("Testing recipe " + i);
         
         // Assert the recipe parses correctly
         final boolean result = wpm.parseString(recipes[i][0]);
         if (!result){
        	 System.out.println(wpm.getErrorMessage());
         }
         assertTrue(result);

         // Build the recipe doesn't fail
         try {
            curRec = wpm.getParsedRecipe();
         } catch (Exception e) {
            System.err.println("Error parsing recipe tree");
            e.printStackTrace();
            assertTrue(false);
         }

         String curRecToString = curRec.toString();
         System.out.println(curRecToString + "\n-----Comparing to the following expected recipe:");
         System.out.println(recipes[i][1]);
         
         // Now assert the recipes toString is equal to the expected.
         String[] expectedArr = recipes[i][1].trim().split("\n");
         String[] outputArr = curRecToString.trim().split("\n");
         for (int j=0; j<expectedArr.length; j++){
            System.out.println("Expected:" + expectedArr[j]);
            System.out.println("Actual:  " + outputArr[j]);
            if (!expectedArr[j].trim().equals(outputArr[j].trim())){
            	// SET A BREAKPOINT HERE!
            	int x = 0;
            }
            assertTrue(expectedArr[j].trim().equals(outputArr[j].trim()));
         }
      }
   }
}
