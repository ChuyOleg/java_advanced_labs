<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Account</title>

    <style>
        <%@include file="/css/style.css"%>
    </style>
</head>
<body>
    <div class="login-logout-block">
        <a href="/logout">Log out</a>
    </div>

    <ul class="header user-header ">
        <li><a href="/catalog">Catalog</a></li>
        <li><a href="/basket">Basket</a></li>
        <li><a href="/account">Account</a></li>
    </ul>
    <h1>Hello, <c:out value="${requestScope.person.login}"/></h1>

    <c:forEach var="order" items="${requestScope.orderingList}">
        <div class="product-wrapper">
            <p class="productName"><c:out value="${requestScope.productMapByOrderingId.get(order.id).name}" /></p>
            <p class="productPrice"><c:out value="Price: ${requestScope.productMapByOrderingId.get(order.id).price} " /></p>
            <p class="productCategory"><c:out value="Category: ${requestScope.productMapByOrderingId.get(order.id).category}" /></p>
            <p class="productSize"><c:out value="Size: ${requestScope.productMapByOrderingId.get(order.id).size}" /></p>
            <p class="productStartDate"><c:out value="Date: ${requestScope.productMapByOrderingId.get(order.id).startDate}" /></p>
            <p class="status status-${order.status}"><c:out value="${order.status}"/></p>
        </div>
    </c:forEach>
</body>
</html>
