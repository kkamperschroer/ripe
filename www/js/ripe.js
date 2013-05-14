
$(function() {

    // Functionality for adding another recipe to a form
    $("#add_ingredient").click(function(){
        var num = $(".ingredient_form").length - 1;
        var newNum = new Number(num + 1);
        var newElem = $('#ingredient' + num)
            .clone().attr('id', 'ingredient' + newNum).fadeIn('slow');

        // Update some of the portions of the new element
        newElem.children(":text").attr("value", "");
        newElem.children("legend").text("Ingredient " + (newNum+1));
        
        // Insert the new element in
        $('#ingredient' + num).after(newElem);
    });

    // Functionality for removing a specific ingredient from
    // the page
    $(".remove_ingredient").click(function(){
        var num = $(this).attr("id").split("_")[1];

        // Let's delete it if the user is positive that's what they want
        if (confirm("Are you sure you want to remove this ingredient?")){
            $("#ingredient" + num).slideUp('slow', function () {
                $(this).remove();
            });
        }

    });
    
});
