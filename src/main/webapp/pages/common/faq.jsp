<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib uri="bettags" prefix="btg" %>
<fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : 'en_EN'}"/>
<fmt:setBundle basename="pagelocale"/>
<html>
<head>
    <title><fmt:message key="company_name"/></title>
    <link rel="stylesheet" type="text/css" href="../../resources/css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="../../resources/css/bets-style.css">
    <link href="https://fonts.googleapis.com/css?family=Fira+Mono:400,500&amp;subset=cyrillic" rel="stylesheet">
    <script type="text/javascript" src="../../resources/js/lib/jquery-3.2.1.js"></script>

    <c:choose>
        <c:when test="${role == 'ADMIN'}">
            <script type="text/javascript" src="../../resources/js/admin/FAQSettingHelper.js"></script>
        </c:when>
        <c:otherwise>
            <script type="text/javascript" src="../../resources/js/FAQHelper.js"></script>
        </c:otherwise>
    </c:choose>

</head>
<body>
<c:set var="role" value="${sessionScope.role}"/>

<div class="full-container">
    <c:choose>
        <c:when test="${role == 'ADMIN'}">
            <%@include file="../admin/jspf/header.jspf" %>
        </c:when>
        <c:otherwise>
            <%@include file="../user/jspf/header.jsp" %>
        </c:otherwise>
    </c:choose>

        <c:choose>
            <c:when test="${role == 'ADMIN'}">
                <%@include file="../admin/FAQSetting.jsp" %>
            </c:when>
            <c:otherwise>
                <%@include file="../user/faq.jsp" %>
            </c:otherwise>
        </c:choose>

    </section>
    <%@ include file="jspf/footer.jspf" %>
    <%@ include file="jspf/logout.jspf" %>
</div>
</body>
</html>