<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="bettags" prefix="btg" %>
<fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : 'en_EN'}"/>
<fmt:setBundle basename="pagelocale"/>
<html>
<head>
    <title><fmt:message key="company_name"/></title>
    <link rel="stylesheet" type="text/css" href="../../resources/css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="../../resources/css/bets-style.css">
    <script type="text/javascript" src="../../resources/js/lib/jquery-3.2.1.js"></script>
    <script type="text/javascript" src="../../resources/js/lib/moment.js"></script>
    <script type="text/javascript" src="../../resources/js/logout.js"></script>
    <link href="https://fonts.googleapis.com/css?family=Fira+Mono:400,500&amp;subset=cyrillic" rel="stylesheet">
</head>
<body>
<div class="full-container">
    <c:choose>
        <c:when test="${role == 'BOOKMAKER'}">
            <%@include file="../bookmaker/jspf/header.jsp" %>
        </c:when>
        <c:otherwise>
            <%@include file="../user/jspf/header.jsp" %>
        </c:otherwise>
    </c:choose>

    <section class="content clearfix">
        <div class="row send-mail">
            <h2 class="text-center">Write to our tech support</h2>
            <form class="send-mail-form" action="${pageContext.servletContext.contextPath}/controller" method="POST">
                <input type="text" class="input-text" name="email" value="${login}">
                <input type="text" class="input-text" name="subject" placeholder="subject">
                <textarea class="input-text text-area" name="mailText" cols="0" rows="0"
                          onfocus="if(this.value==this.defaultValue)this.value='';"
                          onblur="if(this.value=='')this.value=this.defaultValue;">Text</textarea>
                <input class="button-enter" type="submit" value='send'>
            </form>
        </div>
    </section>

    <%@ include file="jspf/footer.jspf" %>
    <%@ include file="jspf/logout.jspf" %>
</div>
</body>
</html>
