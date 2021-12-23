<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Basket</title>

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

    <c:if test="${!sessionScope.role.toString().equals('ADMIN')}">
        <ul class="header user-header ">
            <li><a href="/catalog">Catalog</a></li>
            <li><a href="/basket">Basket</a></li>
            <li><a href="/account">Account</a></li>
        </ul>
    </c:if>

    <h1>Basket</h1>

    <c:forEach var="product" items="${sessionScope.basket}">
        <div class="product-wrapper">
            <p class="productName"><c:out value="${product.name}" /></p>
            <p class="productPrice"><c:out value="Price: ${product.price} " /></p>
            <p class="productCategory"><c:out value="Category: ${product.category}" /></p>
            <p class="productSize"><c:out value="Size: ${product.size}" /></p>
            <p class="productStartDate"><c:out value="Date: ${product.startDate}" /></p>
            <c:if test="${!requestScope.idList.contains(product.id)}">
                <form class="createOrdering" method="post" action="/basket">
                    <input type="text" name="method" value="POST" hidden>
                    <input type="text" name="id" value="${product.id}" hidden>
                    <input type="Submit" value="Order">
                </form>
            </c:if>
            <c:if test="${requestScope.idList.contains(product.id)}">
                <div class="ordered-block">Ordered</div>
            </c:if>
        </div>
    </c:forEach>
</body>
</html>
