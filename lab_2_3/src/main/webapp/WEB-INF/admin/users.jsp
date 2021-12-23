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
    <div class="person-wrapper">
        <p class="personLogin"><c:out value="${person.login}" /></p>
        <p class="personEmail"><c:out value="${person.email}" /></p>
        <c:if test="${person.blocked}">
            <div class="unblock-button">Unblock</div>
        </c:if>
        <c:if test="${!person.blocked}">
            <div class="block-button">Block</div>
        </c:if>
    </div>
</c:forEach>

</body>
</html>
