<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Catalog</title>
</head>
<body>
    <h1 style="text-align: center">Catalog</h1>

    <c:forEach var="product" items="${requestScope.productList}">
        <un>
            <li><c:out value="${product.name}" /></li>
            <li><c:out value="${product.category}" /></li>
            <li><c:out value="${product.price}" /></li>
            <li><c:out value="${product.size}" /></li>
            <li><c:out value="${product.startDate}" /></li>
        </un>
    </c:forEach>
</body>
</html>
