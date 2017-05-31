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
                for (var i = 0; i < resp["currentChars"].length; i++) {
                    var selector = '#' + resp["coords"][i][0] + resp["coords"][i][1];
                    $(selector).html(resp["currentChars"][i]);
                }

            }
        });
    });
});