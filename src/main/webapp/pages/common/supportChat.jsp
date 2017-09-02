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

    <div class="container">
        <div class="row " style="padding-top:40px;">
            <div class=" chat-box col-md-10">
                <div class="panel panel-info">
                    <div class="panel-heading">
                        ${param.email != null ? param.email : sessionScope.login}
                    </div>
                    <div class="panel-body">
                        <ul class="media-list">
                            <c:forEach var="item" items="${allMail}">
                                <li class="media">
                                    <div class="media-body">
                                        <div class="media">
                                            <a class="pull-left" href="#">
                                                <c:choose>
                                                    <c:when test="${item.type eq 'OUT'}">
                                                        <img class="media-object img-circle"
                                                             src="../../resources/images/user.jpg"/>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <img class="media-object img-circle"
                                                             src="../../resources/images/techSupport.jpg"/>
                                                    </c:otherwise>
                                                </c:choose>
                                            </a>
                                            <div class="media-body">
                                                    ${item.mailText}
                                                <br/>
                                                <small class="text-muted">
                                                    <c:choose>
                                                        <c:when test="${item.type eq 'OUT'}">
                                                            ${item.userEmail} | ${item.mailDate}
                                                        </c:when>
                                                        <c:otherwise>
                                                            techSupport | ${item.mailDate}
                                                        </c:otherwise>
                                                    </c:choose>
                                                </small>
                                                <hr/>
                                            </div>
                                        </div>

                                    </div>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                    <div class="panel-footer">
                        <form action="${pageContext.servletContext.contextPath}/controller" method="POST">
                            <input type="hidden" name="command" value="send_support_mail">
                            <input type="hidden" name="email" value="${param.email != null ? param.email : sessionScope.login}">
                            <textarea name="text" class="form-control"></textarea>
                            <input type="submit" value="<fmt:message key="common.btn.send"/>"/>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="../common/jspf/footer.jspf" %>
<%@ include file="../common/jspf/logout.jspf" %>
</div>
</body>
</html>
