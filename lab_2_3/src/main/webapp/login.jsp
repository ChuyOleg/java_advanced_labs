<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Login</title>

    <style>
        <%@include file="/css/style.css"%>
    </style>
</head>
<body>

    <div class="login-form-wrapper">
        <div class="login-form">
            <h3>
                Hello, log in or <a href="/registration">create new account</a>
            </h3>
            <c:if test="${requestScope.error}">
                <p class="error">Oops, that's not a match.</p>
            </c:if>
            <form method="post" action="/login">

                <label for="login">Login</label><br>
                <input type="text" required name="login" minlength="3" maxlength="64" placeholder="Login" id="login"><br>

                <label for="password">Password</label><br>
                <input type="password" required name="password" minlength="4" maxlength="64" placeholder="Password" id="password"><br><br>

                <input type="submit" value="Log in" class="login-button">

            </form>

            <a class="login-guest" href="/catalog">Log in as a Guest</a>
        </div>
    </div>

</body>
</html>
