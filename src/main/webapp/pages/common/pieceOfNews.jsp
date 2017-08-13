<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : 'en_EN'}"/>
<fmt:setBundle basename="locale"/>

<html>
<head>

    <link rel="stylesheet" type="text/css" href="../../css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="../../css/bets-style.css">
    <link rel="stylesheet" href="../../css/font-awesome.css">
    <link href="https://fonts.googleapis.com/css?family=Fira+Mono:400,500&amp;subset=cyrillic" rel="stylesheet">
    <script type="text/javascript" src="../../js/jquery-3.2.1.js"></script>
    <script type="text/javascript" src="../../js/logout.js"></script>
    <c:if test="${role == 'ADMIN'}">
        <script type="text/javascript" src="../../js/pieceOfNewsSettingHelper.js"></script>
    </c:if>


</head>
<body>
<div class="full-container">
    <header class="header-wrap row clearfix">

        <div class="logo-wrap colored-block col-lg-3">
            <a href="${pageContext.servletContext.contextPath}/pages/common/main.jsp"><img class="logo-img"
                                                                                           src="../../images/logo-1.png"
                                                                                           alt="matchCoefficients"></a>
        </div>
        <div class="menu-wrap colored-block ">
            <ul class="main-menu clearfix">
                <li class="item-main"><a href="${pageContext.servletContext.contextPath}/pages/common/news.jsp"
                                         class="main-link">Новости</a>
                </li>
                <li class="item-main"><a href="games.html" class="main-link">Игры</a>
                </li>
                <li class="item-main">
                    <a href="${pageContext.servletContext.contextPath}/controller?command=show_faq" class="main-link">FAQ</a>
                </li>
                <li class="item-main">
                    <a href="${pageContext.request.contextPath}/controller?command=show_profile" class="main-link">Профиль</a>
                </li>
            </ul>
        </div>
        <div class="lang-wrap colored-block ">
            <a href="@" class="lang">RU</a>

        </div>
        <div class="user-action colored-block col-lg-3">
            <div class="authorization">
                <a href="javascript:showLogoutPopup()" class="btn-enter">Выход</a>
            </div>
        </div>
    </header>

    <section class="content clearfix">
        <article class="row">
            <div class="news colored-block">
                <c:choose>
                    <c:when test="${role == 'ADMIN'}">
                        <%@include file="../admin/pieceOfNewsSetting.jsp" %>
                    </c:when>
                    <c:otherwise>
                        <%@include file="../user/pieceOfNews.jsp" %>
                    </c:otherwise>
                </c:choose>
            </div>
        </article>
    </section>
    <%@ include file="jspf/footer.jspf" %>
    <%@ include file="jspf/logout.jspf" %>
</div>

</body>
</html>