<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Registration</title>

    <style>
        <%@include file="/css/style.css"%>
    </style>
</head>
<body>

    <div class="registration-form-wrapper">
        <div class="registration-form">
            <h3>
                Hello, create new account or <a href="/login">log in</a>
            </h3>
            <c:if test="${requestScope.error}">
                <p class="error">Oops, user with this login is already registered.</p>
            </c:if>
            <form method="post" action="/registration">

                <label for="login">Login</label><br>
                <input type="text" required name="login" minlength="3" maxlength="64" placeholder="Login" id="login"><br>

                <label for="password">Password</label><br>
                <input type="password" required name="password" minlength="4" maxlength="64" placeholder="Password" id="password"><br>

                <label for="email">Email</label><br>
                <input type="email" required name="email" minlength="4" maxlength="64" placeholder="Email" id="email"><br><br>

                <input type="submit" value="Create account" class="login-button">

            </form>
        </div>
    </div>

</body>
</html>
