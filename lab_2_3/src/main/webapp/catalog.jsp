<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Catalog</title>

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

    <h1>Catalog</h1>

    <form class="filter-form" method="get" action="/catalog">
    <ul class="filters-wrapper">
        <li>
            <label for="categoryFilter">Category</label><br>
            <select size="3" multiple name="category[]" id="categoryFilter">
                <c:forEach var="category" items="${requestScope.categorySet}">
                    <option value="${category}">${category}</option>
                </c:forEach>
            </select>
        </li>
        <li>
            <label for="sizeFilter">Size</label><br>
            <select size="3" multiple name="size[]" id="sizeFilter">
                <option value="SMALL">SMALL</option>
                <option value="MEDIUM">MEDIUM</option>
                <option value="LARGE">LARGE</option>
            </select>
        </li>
        <li>
            <label for="minPrice">minPrice</label><br>
            <input type="number" name="minPrice" id="minPrice"><br>

            <label for="maxPrice">maxPrice</label><br>
            <input type="number" name="maxPrice" id="maxPrice">
        </li>
    </ul>
    <input type="submit" value="Filter">
    </form>

    <form class="sort-form" method="get" action="/catalog/sort">
        <select required name="sortField">
            <option value="" hidden>Sort by</option>
            <option value="name">Name</option>
            <option value="topPrice">Top Price first</option>
            <option value="lowPrice">Low Price first</option>
            <option value="date">Date</option>
        </select>
        <input type="submit" value="Sort">
    </form>

    <c:if test="${sessionScope.role.equals('ADMIN')}">
        <form class="create-product" method="get" action="/admin/productManagement">
            <input type="Submit" value="Create new Product">
        </form>
    </c:if>

    <c:forEach var="product" items="${sessionScope.productList}">
        <div class="product-wrapper">
            <p class="productName"><c:out value="${product.name}" /></p>
            <p class="productPrice"><c:out value="Price: ${product.price} " /></p>
            <p class="productCategory"><c:out value="Category: ${product.category}" /></p>
            <p class="productSize"><c:out value="Size: ${product.size}" /></p>
            <p class="productStartDate"><c:out value="Date: ${product.startDate}" /></p>

            <c:if test="${!sessionScope.role.toString().equals('ADMIN')}">
                <c:if test="${!sessionScope.basket.contains(product)}">
                    <form class="save-to-basket" method="post" action="/catalog/saveToBasket">
                        <input type="text" name="id" value="${product.id}" hidden>
                        <input type="Submit" value="Save to basket">
                    </form>
                </c:if>

                <c:if test="${sessionScope.basket.contains(product)}">
                    <div class="saved-block">Saved</div>
                </c:if>
            </c:if>

            <c:if test="${sessionScope.role.toString().equals('ADMIN')}">
                <form class="update-product" method="post" action="/admin/productManagement">
                    <input type="text" name="method" value="PUT" hidden>
                    <input type="text" name="id" value="${product.id}" hidden>
                    <input type="Submit" value="Update">
                </form>

                <form class="delete-product" method="post" action="/admin/productManagement">
                    <input type="text" name="method" value="DELETE" hidden>
                    <input type="text" name="id" value="${product.id}" hidden>
                    <input type="Submit" value="Delete">
                </form>
            </c:if>

        </div>
    </c:forEach>

</body>
</html>
