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
    <script type="text/javascript" src="../../resources/js/admin/matchesSettingHelper.js"></script>

    <link href="https://fonts.googleapis.com/css?family=Fira+Mono:400,500&amp;subset=cyrillic" rel="stylesheet">
</head>
<body>
<div class="full-container">
    <%@include file="../admin/jspf/header.jspf" %>
    <div class="games-wrap row">
        <div class="games-menu-wrap col-lg-3">
            <div class="games-menu colored-block">
                <div class="matches-list matches-menu-item">
                    <form id="show-matches-page" method="POST" action="${pageContext.servletContext.contextPath}/controller">
                        <input type="hidden" name="command" value="show_matches_page"/>
                        <input type="hidden" name="confederation" id="confederation" value=""/>
                        <div class="matches-title">
                            <a href="javascript://" id="matches-title"><fmt:message key="common.bets.matches"/></a> <span
                                class="glyphicon glyphicon-chevron-down dropdown" id="dropdown" style="color: #ffa71b"
                                aria-hidden="true"></span>
                        </div>
                        <div class="league-list" id="league-list" style="display: none">
                            <div class="btn-group">
                                <c:forEach var="item" items="${confederationList}">
                                    <label><input type="radio" name="league" value="${item}"/>${item}</label>
                                </c:forEach>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="results matches-menu-item"><a href="${pageContext.servletContext.contextPath}/controller?command=show_match_results_page"
                                        id="match-results"><fmt:message
                        key="common.bets.results"/></a></div>
                <div class="add-team matches-menu-item"><a href="${pageContext.servletContext.contextPath}/controller?command=show_add_team_page"
                ><fmt:message key="admin.match.add_team"/></a></div>
                <div class="add-team matches-menu-item"><a
                        href="${pageContext.servletContext.contextPath}/controller?command=show_add_confederation_page"
                ><fmt:message key="admin.match.add_confederation"/></a></div>
            </div>
        </div>
        <div class="games-table-wrap col-lg-9">
            <div class=" create-faq-wrap colored-block">
                <div class="create-team-title"><fmt:message key="admin.match.add_confederation"/></div>
                <p class="error-label"
                   <c:if test="${!btg:contains(errors,'createConfederationError' )}">style="display:none;"</c:if>>
                    <fmt:message key="admin.match.create_team.error"/>
                </p>
                <form class="create-team" method="POST" action="${pageContext.servletContext.contextPath}/controller"
                      onsubmit="return validateCreateConfederationForm()">
                    <input type="hidden" name="command" value="add_confederation"/>
                    <input class="input-text <c:if test="${btg:contains(errors,'invalidConfederationError' )}">error</c:if>"
                           type="text" id="confederation-name" name="confederation"
                           placeholder="<fmt:message key="admin.match.confederation"/>"
                           onfocus="if(this.value==this.defaultValue)this.value='';"
                           onblur="if(this.value=='')this.value=this.defaultValue;">
                    <p class="error-label show-message" id="invalid-confederation"
                       <c:if test="${!btg:contains(errors,'invalidConfederationError' )}">style="display:none;"</c:if>>
                        <fmt:message key="admin.match.create_confederation.invalid_confederation"/>
                    </p>
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
