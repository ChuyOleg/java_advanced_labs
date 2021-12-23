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
