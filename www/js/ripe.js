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
        newElem.children(":text").attr("value", "");
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

});
