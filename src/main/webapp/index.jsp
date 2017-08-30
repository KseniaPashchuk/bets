<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head><title>Index second</title></head>
<body>
<c:set var="role" value="${sessionScope.role}"/>
<c:choose>
    <c:when test="${role == 'USER' || role == 'ADMIN'|| role == 'BOOKMAKER'}">
        <jsp:forward page="/controller?command=show_main_page"/>
    </c:when>
    <c:otherwise>
        <jsp:forward page="/pages/guest/registration.jsp"/>
    </c:otherwise>
</c:choose>
</body>
</html>