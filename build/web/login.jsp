<%-- 
    Document   : Login
    Created on : May 24, 2020, 6:50:03 PM
    Author     : TNM
--%>

<!DOCTYPE html>
<%@taglib prefix="s" uri="/struts-tags" %>
<html>
    <head>
        <title>Login Page</title>
        <meta charset="utf-8">
        <link type="text/css" rel="stylesheet" href="css/style.css">
    </head>
    <body>
        <header class="header">
            <div class="top">
            </div>
            <div class="head">
                <img src="img/Loginlogo.jpg" width="414" height="110">
            </div>
        </header>

        <div class="main">
            <section class="user-main">
                <div class="info" style="margin-left: 100px">
                    <h1>Log in to your account!</h1>
                    <form action="LoginAction" method="POST">
                        <input type="text" name="id" placeholder="Username"/></br>
                        <input type="password" name="password" placeholder="Password"/></br>
                        <input type="hidden" id="recaptchaResult" name="recaptchaResult"/>
                        <input type="submit" value="Login" onclick="checkReCaptCha()"/>
                    </form>
                    </br>
                    <div id="reCaptCha"></div>
                    <p>
                        <font color="red">
                        <s:property value="%{#request.ERROR}" />
                        </font>
                    </p>
                    <script>
                        var onloadCallback = function () {
                            grecaptcha.render('reCaptCha', {
                                'sitekey': '6Lc9A6sZAAAAAHIe-yKkBzwzKV4Jqba3lrJIuXHN'
                            });
                        };
                        function checkReCaptCha() {
                            var response = grecaptcha.getResponse();
                            if (response.length == 0) {
                                document.getElementById("recaptchaResult").value = 'False';
                            } else {
                                document.getElementById("recaptchaResult").value = 'True';
                            }
                        }
                    </script>
                </div>
            </section>
        </div>
        <script src="https://www.google.com/recaptcha/api.js?onload=onloadCallback&render=explicit"
                async defer>
        </script>
    </body>
</html>