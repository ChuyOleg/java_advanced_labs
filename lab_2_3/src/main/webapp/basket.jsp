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
    <h1>Basket</h1>

    <c:forEach var="product" items="${sessionScope.basket}">
        <div class="product-wrapper">
            <p class="productName"><c:out value="${product.name}" /></p>
            <p class="productPrice"><c:out value="Price: ${product.price} " /></p>
            <p class="productCategory"><c:out value="Category: ${product.category}" /></p>
            <p class="productSize"><c:out value="Size: ${product.size}" /></p>
            <p class="productStartDate"><c:out value="Date: ${product.startDate}" /></p>
<%--            <form class="save-to-basket" method="get" action="/catalog/saveToBasket">--%>
<%--                <input type="text" name="id" value="${product.id}" hidden>--%>
<%--                <input type="Submit" value="Save to basket">--%>
<%--            </form>--%>
        </div>
    </c:forEach>
</body>
</html>
