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
                $(selector).html(resp["currentChar"]);
            }
        });
    });
});