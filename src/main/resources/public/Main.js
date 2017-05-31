$(document).ready(function () {

    $('.table-buttons').bind("contextmenu",function(e) {
        if(!($(this).hasClass("revealed"))){
        if ($(this).hasClass("glyphicon")) {
            $(this).removeClass("glyphicon");
            $(this).removeClass("glyphicon-eye-open")
        } else {
        $(this).addClass("glyphicon glyphicon-eye-open");
    }
        }
        return false;
    });

    $(".table-buttons").click(function () {
        var x = $(this).attr("value");
        var y = $(this).attr("name");
        $.ajax({
            url: "/retrieve_data",
            type: "POST",
            async: true,
            data: {x: x, y: y},
            success: function (resp) {
                for (var i = 0; i < resp["currentChars"].length; i++) {
                    var selector = '#' + resp["coords"][i][0] + resp["coords"][i][1];
                    if(resp["currentChars"][i] == '9') {
                        //$(".button-container").html("<p class='glyphicon glyphicon-remove'></p>")
                        $(".table-buttons").prop('disabled', true);
                        $(selector).parent().html("<p class='mine-cell glyphicon glyphicon-certificate'></p>");
                        $("#evaluator-button").remove();
                        $("#game-over").html("<p>GAME OVER</p><form action='/'><button type='submit'>NEW GAME</button></form> ")
                    }else {
                        $(selector).html(resp["currentChars"][i]);
                        $(selector).addClass("revealed button-value" + resp["currentChars"][i])
                        $(selector).removeClass("unchecked");
                        $(selector).removeClass("glyphicon-eye-open");
                        $(selector).removeClass("glyphicon");
                    }
                }
            }
        });
    });

    $("#evaluator-button").click(function () {
        $.ajax({
            url: "/evaluate",
            type: "GET",
            async: true,
            success: function (resp) {
                if($(".button-container").length - $(".revealed").length == resp["numberOfMines"]){
                    $("#evaluator-button").remove();
                    $(".table-buttons").prop('disabled', true);
                    $("#game-over").html("<p>You survive the minefield!</p><form action='/'><button type='submit'>NEW GAME</button></form>");
                }else{
                    $("#game-over").html("<p>You are not passed the minefield yet, but if you want a new game just push the button, chicken!</p><form action='/'><button type='submit'>NEW GAME</button></form>");

                }
            }
        });
    });

});