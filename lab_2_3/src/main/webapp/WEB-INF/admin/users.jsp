<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Users</title>

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

    <c:forEach var="person" items="${requestScope.personList}">
        <div class="person-wrapper person-blocked-${person.blocked}">
            <p class="personLogin"><c:out value="${person.login}" /></p>
            <p class="personEmail"><c:out value="${person.email}" /></p>
            <form class="block-unblock-form" method="post" action="">
                <input type="text" name="id" value="${person.id}" hidden>
                <c:if test="${person.blocked}">
                    <input type="submit" name="action" value="Unblock">
                </c:if>
                <c:if test="${!person.blocked}">
                    <input type="submit" name="action" value="Block">
                </c:if>
            </form>
        </div>
    </c:forEach>

</body>
</html>
