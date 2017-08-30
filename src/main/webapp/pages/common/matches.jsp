<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib uri="bettags" prefix="btg" %>
<fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : 'en_EN'}"/>
<fmt:setBundle basename="pagelocale"/>

<html>
<head>
    <link rel="stylesheet" type="text/css" href="../../resources/css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="../../resources/css/bootstrap-datetimepicker.css">
    <link rel="stylesheet" href="../../resources/css/font-awesome.css">
    <link rel="stylesheet" type="text/css" href="../../resources/css/dataTables.bootstrap.min.css">
    <link href="https://fonts.googleapis.com/css?family=Fira+Mono:400,500&amp;subset=cyrillic" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="../../resources/css/bets-style.css">

    <script type="text/javascript" src="../../resources/js/lib/jquery-3.2.1.js"></script>
    <script type="text/javascript" src="../../resources/js/lib/bootstrap.js"></script>
    <script type="text/javascript" src="../../resources/js/lib/moment.js"></script>
    <script type="text/javascript" src="../../resources/js/lib/bootstrap-datetimepicker.js"></script>
    <script type="text/javascript" src="../../resources/js/lib/jquery.dataTables.min.js"></script>
    <script type="text/javascript" src="../../resources/js/lib/dataTables.bootstrap.min.js"></script>
    <script type="text/javascript" src="../../resources/js/logout.js"></script>

    <c:choose>
        <c:when test="${role == 'BOOKMAKER'}">
            <script type="text/javascript" src="../../resources/js/bookmaker/matchSettingHelper.js"></script>
        </c:when>
        <c:otherwise>
            <script type="text/javascript" src="../../resources/js/matchHelper.js"></script>
        </c:otherwise>
    </c:choose>
</head>
<body>
<div class="full-container">
    <c:choose>
        <c:when test="${role == 'ADMIN'}">
            <%@include file="../admin/jspf/header.jspf" %>
        </c:when>
        <c:when test="${role == 'BOOKMAKER'}">
            <%@include file="../bookmaker/jspf/header.jsp" %>
        </c:when>
        <c:otherwise>
            <%@include file="../user/jspf/header.jsp" %>
        </c:otherwise>
    </c:choose>

    <section class="content clearfix">
        <c:choose>
            <c:when test="${role == 'ADMIN'}">
                <%@include file="../admin/matches.jsp" %>
            </c:when>
            <c:when test="${role == 'BOOKMAKER'}">
                <%@include file="../bookmaker/matchSetting.jsp" %>
            </c:when>
            <c:otherwise>
                <%@include file="../user/matches.jsp" %>
            </c:otherwise>
        </c:choose>
    </section>

    <%@ include file="jspf/footer.jspf" %>
    <%@ include file="jspf/logout.jspf" %>
</div>
</body>
</html>
