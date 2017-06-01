$(document).ready(function () {
    $('.box').hide().fadeIn(1000);


    $('#register').click(function (event) {
        event.preventDefault();
        var name = document.getElementById("name").value;
        var psw = document.getElementById("psw").value;

        $.ajax({

            url: "/register",
            type: "POST",
            data: {"username": name, "password": psw},
            dataType: "html",
            success: function () {
                location.href = "/";
            }
        });
    })

    $('.btn').click(function (event) {
        event.preventDefault();
        var name = document.getElementById("login_name").value;
        var psw = document.getElementById("login_psw").value;
        $.ajax({

            url: "/login",
            type: "POST",
            data: {"username": name, "password": psw},
            dataType: "html",
            success: function () {
                location.href = "/";
            },
            error: function () {
                location.href = "/login"

            }
        });
    })

});


