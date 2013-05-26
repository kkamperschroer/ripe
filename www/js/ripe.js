function removeIngredient(){
    var num = $(this).attr("id").split("_")[1];

    // Let's delete it if the user is positive that's what they want
    if (confirm("Are you sure you want to remove this ingredient?")){
        $("#ingredient_" + num).slideUp('slow', function () {
            $(this).remove();

            // Now, it's unfortunately necessary to go through each of
            // the other ingredients in the form and update their actual number
            var currentNumber = 0;
            $("#edit_recipe_form_ingredients_list").children("fieldset").each(function(){
                var numStr = $(this).attr("id").split('_')[1];
                var num = new Number(parseInt(numStr));
                if (num != currentNumber){
                    // this needs to be changed from 'num' to 'currentNum'

                    // First, change the fieldset id
                    $(this).attr("id", "ingredient_" + currentNumber);

                    //// Now start changing children
                    // legend
                    $(this).children("legend").text("Ingredient " + (currentNumber+1));
                    // inputs (text)
                    $(this).children("input").each(function() {
                        // Get the current name
                        var name = $(this).attr("name");

                        if (name != undefined){
                            // Split the string
                            var name_sub = name.split('_')

                            // Now name_sub[0] contains the attribute and
                            //     name_sub[1] contains the number
                            // Just build the new string using newNum
                            $(this).attr("name", name_sub[0] + "_" + currentNumber);
                        }else{
                            // Check if this is a button
                            if ($(this).attr("type") == "button"){
                                // Update the text of the 'id' field
                                var id_sub = $(this).attr("id").split('_');
                                $(this).attr("id", id_sub[0] + "_" + currentNumber);
                            }
                        }
                    });
                }
                currentNumber++;
            });
            // Now we need to update our hidden field containing the total
            // count of recipes
            $("#hidden_total_ingredients").attr("value", currentNumber);
        });
    }

}

$(function() { // Doc ready
    // Functionality for adding another recipe to a form
    $("#add_ingredient").click(function(){
        var num = $(".ingredient_form").length - 1;
        var newNum = new Number(num + 1);
        var oldElem = $('#ingredient_' + num);
        var newElem = oldElem.clone().attr('id', 'ingredient_' + newNum).fadeIn('slow');

        // Update some of the portions of the new element
        newElem.children(":text").val("");
        newElem.children("legend").text("Ingredient " + (newNum+1));

        // Update each of the input field names
        newElem.children("input").each(function() {
            // Get the current name
            var name = $(this).attr("name");

            if (name != undefined){
                // Split the string
                var name_sub = name.split('_')

                // Now name_sub[0] contains the attribute and
                //     name_sub[1] contains the number
                // Just build the new string using newNum
                $(this).attr("name", name_sub[0] + "_" + newNum);
            }else{
                // Check if this is a button
                if ($(this).attr("type") == "button"){
                    // Update the text of the 'id' field
                    var id_sub = $(this).attr("id").split('_');
                    $(this).attr("id", id_sub[0] + "_" + newNum);

                    // Update the click event for this new button
                    $(this).click(removeIngredient);
                }
            }
        });
        
        // Insert the new element in
        $('#ingredient_' + num).after(newElem);

        // Update our total counts
        $("#hidden_total_ingredients").attr("value", newNum+1);

    });

    // Functionality for removing a specific ingredient from
    // the page
    $(".remove_ingredient").click(removeIngredient);


    // We want to prompt before a user removes a recipe
    $("a.remove_rec").click(function(event){
        var link = this;
        event.preventDefault();

        if (confirm("Are you sure you want to delete this recipe?")){
            window.location = link.href;
        }
    });

    // Same as the last block, but for when there's only a single link with
    // a given id. TODO -> Do this without duplicating code.
    $("a#remove_rec").click(function(event){
        var link = this;
        event.preventDefault();

        if (confirm("Are you sure you want to delete this recipe?")){
            window.location = link.href;
        }
    });

    $("#back_link").click(function(event){
        event.preventDefault();
        history.back(-1); 
    });

    $("#tip_example_rec1").click(function(event){
        // Update the textarea value to contain this recipe
        $("#recipe_textarea").text(
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
                "4. Bake for 30 minutes in the preheated oven, or until cheeses are melted.\n"
        );
    });

    $("#tip_example_rec2").click(function(event){
        // Update the textarea value to contain this recipe
        $("#recipe_textarea").text(
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
                "Spread ½ cup of the enchilada sauce in the bottom of a 9-by-13-inch baking dish. Dividing evenly, roll up the mushroom mixture in the tortillas and place seam-side down in the dish. Top with the remaining enchilada sauce and sprinkle with the remaining Monterey Jack. Bake until the sauce is bubbling and the Monterey Jack is melted, 12 to 15 minutes. Sprinkle with the cilantro.\n"
        );
    });

    $("#tip_example_rec3").click(function(event){
        // Update the textarea value to contain this recipe
        $("#recipe_textarea").text(
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
                "5.	Cover, and bake 45 minutes in the preheated oven. Cool slightly before serving.\n"
        );
    });
    
});
