<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Shop</title>

    <style>
        <%@include file="/css/style.css"%>
    </style>
</head>
<body>
    <div class="login-logout-block">
        <c:if test="${sessionScope.role.toString().equals('UNKNOWN')}">
            <a href="/login">Log in</a>
        </c:if>
        <c:if test="${!sessionScope.role.toString().equals('UNKNOWN')}">
            <a href="/logout">Log out</a>
        </c:if>
    </div>

    <c:if test="${sessionScope.role.toString().equals('ADMIN')}">
        <ul class="header admin-header">
            <li><a href="/catalog">Catalog</a></li>
            <li><a href="/admin/users">Users</a></li>
        </ul>
    </c:if>

    <h2>Create new Product</h2>
    <form class="create-product-form" method="post" action="/admin/productManagement">
        <input type="text" name="method" value="POST" hidden>

        <label for="name">Name</label><br>
        <input type="text" required name="name" maxlength="64" placeholder="Name" id="name"><br>

        <label for="price">Price</label><br>
        <input type="text" required name="price" placeholder="Price" id="price"><br>

        <label for="category">Category</label><br>
        <input type="text" required name="category" maxlength="64" placeholder="Category" id="category"><br>

        <label for="size">Size</label><br>
        <select size="3" required name="size" id="size">
            <option value="SMALL">SMALL</option>
            <option value="MEDIUM">MEDIUM</option>
            <option value="LARGE">LARGE</option>
        </select><br><br>

        <input type="submit" value="Create">

    </form>

</body>
</html>
