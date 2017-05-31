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
                        $(".game-over").html("<h1 class='game-over-message'>GAME OVER</h1><form action='/'><button type='submit'  class='btn btn-primary active'>NEW GAME</button></form> ")
                        $(".game-over-10").html("<h1 class='game-over-message'>GAME OVER</h1><form action='/'><button type='submit' class='btn btn-primary active'>NEW GAME</button></form> ")
                        $(".game-over-15").html("<h1 class='game-over-message'>GAME OVER</h1><form action='/'><button type='submit' class='btn btn-primary active'>NEW GAME</button></form> ")
                    }else {
                        $(selector).html(resp["currentChars"][i]);
                        $(selector).addClass("revealed button-value" + resp["currentChars"][i])
                        $(selector).parent().removeClass("unchecked");
                        $(selector).removeClass("glyphicon-eye-open");
                        $(selector).removeClass("glyphicon");
                    }
                   evaluator();
                }
            }
        });
    });

    function evaluator() {
        $.ajax({
            url: "/evaluate",
            type: "GET",
            async: true,
            success: function (resp) {
                if($(".button-container").length - $(".revealed").length == resp["numberOfMines"]){
                    $(".table-buttons").prop('disabled', true);
                    $(".unchecked").html("<p class='mine-cell glyphicon glyphicon-certificate'></p>");
                    $(".game-over").html("<h2 class='congratulation-message'>Congratulation!</h2><p class='congratulation-message'>You survived the minefield!</p><form action='/'><button type='submit'class='btn btn-primary active'>NEW GAME</button></form>");
                    $(".game-over-10").html("<h1 class='congratulation-message'>Congratulation!</h1><p class='congratulation-message'>You survived the minefield!</p><form action='/'><button type='submit' class='btn btn-primary active'>NEW GAME</button></form>");
                    $(".game-over-15").html("<h1 class='congratulation-message'>Congratulation!</h1><p class='congratulation-message'>You survived the minefield!</p><form action='/'><button type='submit' class='btn btn-primary active'>NEW GAME</button></form>");
                }
            },
            error: function () {
                alert("something went wrong")
            }
        });
    }

});