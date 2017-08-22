<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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

    <section class="content clearfix">
        <div class="info-wrap clearfix">
            <div class="info row">
                <div class="pic-wrap col-lg-7">
                    <img class="pic" src="../../resources/images/5.jpg" alt="football">
                </div>
                <div class="info-text colored-block col-lg-5">
                    <article class="article-wrap">
                        <h1><fmt:message key="common.main.article_title"/></h1>
                        <p><fmt:message key="common.main.article"/></p>
                    </article>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="last-news">
                <div class="last-news-title"><fmt:message key="common.main.last_news"/></div>
                <div class="news-wrap">
                    <div class="news-wrap">
                        <div class="news-row row">
                            <c:forEach var="item" items="${lastNews}">
                                <form class=" col-md-4 news-item  colored-block"
                                      action="${pageContext.servletContext.contextPath}/controller" method="POST">
                                    <input type="hidden" name="command" value="show_piece_of_news"/>
                                    <a href="#"><img class="img-responsive"
                                                     src="${pageContext.request.contextPath}/image/news/${item.pictureUrl}"
                                                     alt=""></a>
                                    <input class="news-title" type="submit" name="title" value="${item.title}">
                                    <span class="fa fa-clock-o"><i class="icon-time news-time-icon"></i>
                                    <time class="news-date entry-date published">
                                        <fmt:parseDate value="${item.date}" pattern="yyyy-MM-dd" var="parsedDate"
                                                       type="date"/>
                                         <fmt:formatDate value="${parsedDate}" type="date" var="formattedDate" pattern="dd/MM/yy"/>
                                   ${formattedDate}
                                    </time>
                                    </span>
                                </form>
                            </c:forEach>

                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="payment row  clearfix">
            <div class="payment-wrap colored-block clearfix ">
                <div class="pay-title col-lg-2"><fmt:message key="common.main.payment_methods"/></div>
                <ul class="pay-systems">
                    <li>
                        <a href="javascript://" class="webmoney"></a>
                    </li>
                    <li>
                        <a href="javascript://" class="scrill"></a>
                    </li>
                    <li>
                        <a href="javascript://" class="mastercard"></a>
                    </li>
                    <li>
                        <a href="javascript://" class="visa"></a>
                    </li>

                </ul>
            </div>
        </div>
    </section>

    <%@ include file="jspf/footer.jspf" %>
    <%@ include file="jspf/logout.jspf" %>
</div>
</body>
</html>
