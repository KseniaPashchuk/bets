<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : 'en_EN'}"/>
<fmt:setBundle basename="pagelocale"/>
<html>
<head>
    <title><fmt:message key="company_name"/></title>
    <link rel="stylesheet" type="text/css" href="../../css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="../../css/bets-style.css">
    <script type="text/javascript" src="../../js/jquery-3.2.1.js"></script>
    <script type="text/javascript" src="../../js/moment.js"></script>
    <script type="text/javascript" src="../../js/logout.js"></script>
    <link href="https://fonts.googleapis.com/css?family=Fira+Mono:400,500&amp;subset=cyrillic" rel="stylesheet">
</head>
<body>
<div class="full-container">
    <%@include file="../bookmaker/jspf/header.jsp" %>
        <div class="games-wrap row">
            <div class="games-menu-wrap col-lg-3">
                <div class="games-menu colored-block">
                    <div class="matches-list">
                        <div class="matches-title">
                            <a href="javascript://" id="matches-title"><fmt:message key="common.bets.matches"/></a> <span
                                class="glyphicon glyphicon-chevron-down dropdown" style="color: #ffa71b"
                                aria-hidden="true"></span>
                        </div>
                        <div class="league-list" id="league-list" style="display: none">
                            <div class="btn-group">
                                <c:forEach var="item" items="${confederationList}">
                                    <label><input type="radio" name="league" value="${item}"/>${item}</label>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                    <div class="results"><a href="javascript://" id="match-results"><fmt:message key="common.bets.results"/></a></div>
                    <div class="add-team"><a href="${pageContext.servletContext.contextPath}/pages/bookmaker/addTeam.jsp" id="add-team"><fmt:message key="bookmaker.bets.add_team"/></a></div>
                </div>
            </div>
            <div class="games-table-wrap col-lg-9">
                <div class=" create-faq-wrap colored-block">
                    <div class="create-team-title">Create new team</div>
                    <form class="create-team" method="POST" action="${pageContext.servletContext.contextPath}/controller">
                        <input type="hidden" name="command" value="add_team"/>
                        <input class="input-text" type="text" name="team" value="Team name" onfocus="if(this.value==this.defaultValue)this.value='';" onblur="if(this.value=='')this.value=this.defaultValue;">
                        <input class="input-text" type="text" name="country" value="Team country" onfocus="if(this.value==this.defaultValue)this.value='';" onblur="if(this.value=='')this.value=this.defaultValue;">
                        <input class="input-btn" type="submit" value="create">
                    </form>
                </div>
            </div>
        </div>
    <%@ include file="../common/jspf/footer.jspf" %>
    <%@ include file="../common/jspf/logout.jspf" %>
</div>
</body>
</html>
