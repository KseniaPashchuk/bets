<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : 'ru_RU'}"/>
<fmt:setBundle basename="pagelocale"/>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="../../resources/css/error.css">
    <title><fmt:message key="common.error.some_trouble"/> | 500 - <fmt:message key="common.error.unavailable"/></title>
</head>
<body>
<div class="cover">
    <h1><fmt:message key="common.error.smth_wrong"/>
        <small>Error 500</small>
    </h1>
    <p class="lead"><fmt:message key="common.error.unavailable"/><br/><fmt:message key="common.error.try_later"/></p>
</div>
<footer>
    <p><fmt:message key="common.error.tech_contact"/>: <a href=mailto:x@example.com>x@example.com</a></p>
</footer>

</body>
</html>
