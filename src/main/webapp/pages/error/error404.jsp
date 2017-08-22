<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : 'ru_RU'}"/>
<fmt:setBundle basename="pagelocale"/>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="../../resources/css/error.css">
    <title>><fmt:message key="common.error.some_trouble"/> | 400 - <fmt:message key="common.error.not_found"/></title>
</head>
<body>
<div class="cover">
    <h1><fmt:message key="common.error.not_found"/>
        <small>Error 404</small>
    </h1>
    <p class="lead"><fmt:message key="common.error.not_found_expl"/><br/><fmt:message key="common.error.try_later"/></p>
</div>
<footer>
    <p><fmt:message key="common.error.tech_contact"/>: <a href=mailto:x@example.com>x@example.com</a></p>
</footer>
</body>
</html>
