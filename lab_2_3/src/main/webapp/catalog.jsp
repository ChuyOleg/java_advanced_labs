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
    <h1>Catalog</h1>

    <form class="filter-form" method="get" action="/catalog/filter">
    <ul class="filters-wrapper">
        <li>
            <label for="categoryFilter">Category</label><br>
            <select size="3" multiple name="category[]" id="categoryFilter">
                <option value="Books">Books</option>
                <option value="Alcohol">Alcohol</option>
                <option value="Animal">Animal</option>
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

    <c:forEach var="product" items="${sessionScope.productList}">
        <div class="product-wrapper">
            <p class="productName"><c:out value="${product.name}" /></p>
            <p class="productPrice"><c:out value="Price: ${product.price} " /></p>
            <p class="productCategory"><c:out value="Category: ${product.category}" /></p>
            <p class="productSize"><c:out value="Size: ${product.size}" /></p>
            <p class="productStartDate"><c:out value="Date: ${product.startDate}" /></p>

            <c:if test="${!sessionScope.basket.contains(product)}">
                <form class="save-to-basket" method="get" action="/catalog/saveToBasket">
                    <input type="text" name="id" value="${product.id}" hidden>
                    <input type="Submit" value="Save to basket">
                </form>
            </c:if>

            <c:if test="${sessionScope.basket.contains(product)}">
                <div class="saved-block">Saved</div>
            </c:if>
        </div>
    </c:forEach>

</body>
</html>
