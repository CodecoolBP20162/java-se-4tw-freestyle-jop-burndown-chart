function field_focus(field, email)
  {
    if(field.value == email)
    {
      field.value = '';
    }
  }

  function field_blur(field, email)
  {
    if(field.value == '')
    {
      field.value = email;
    }
  }

//Fade in dashboard box
$(document).ready(function(){
    $('.box').hide().fadeIn(1000);


    $('#register').click(function (event) {
      event.preventDefault();
      var name=document.getElementById("name").value;
      var psw=document.getElementById("psw").value;
      var mail=document.getElementById("mail").value;

      $.ajax({

        url:"/register_user",
        type:"POST",
        data:{"name":name,"psw":psw,"mail":mail},
        dataType:"html",
        success:function () {
            location.href ="/";
        }
          });
    })

    $('.btn').click(function (event) {
        event.preventDefault();
        var name=document.getElementById("login_name").value;
        var psw=document.getElementById("login_psw").value;
        $.ajax({

            url:"/login_user",
            type:"POST",
            data:{"name":name,"psw":psw},
            dataType:"html",
            success:function () {
                location.href ="/";
            },
            error:function () {
                location.href="/login"

            }


        });
    })


    });


