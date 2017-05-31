$(document).ready(function () {
    $(".table-buttons").click(function () {
        var x = $(this).attr("value");
        var y = $(this).attr("name");
        $.ajax({
            url: "/retrieve_data",
            type: "POST",
            async: true,
            data: {x: x, y: y},
            success: function (resp) {
                var selector = '#' + resp["coords"][0] + resp["coords"][1];
                if(resp["currentChar"] == '9') {
                    $(selector).parent().html("<p class='mine-cell glyphicon glyphicon-certificate'></p>");
                }else {
                    $(selector).html(resp["currentChar"]);
                    $(selector).addClass("button-value" + resp["currentChar"])
                }
            }
        });
    });
});