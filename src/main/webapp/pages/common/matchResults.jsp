<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
    <script type="text/javascript" src="../../resources/js/matchResultsHelper.js"></script>

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

    <div class="games-wrap row">
        <div class="games-menu-wrap col-lg-3">
            <div class="games-menu colored-block">
                <div class="matches-list">
                    <form id="show-matches-page" method="POST" action="${pageContext.servletContext.contextPath}/controller">
                        <input type="hidden" name="command" value="show_matches_page"/>
                        <input type="hidden" name="confederation" id="confederation" value=""/>
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
                    </form>
                </div>
                <div class="results"><a href="javascript://" id="match-results"><fmt:message
                        key="common.bets.results"/></a>
                </div>
            </div>
        </div>
        <div class="games-table-wrap col-lg-9">
            <div class="row search-results-block colored-block">
                <div class="column-12 results-filter clearfix">
                    <div class="col-lg-4">
                        <input type="hidden" id="prev-date" value="${param.date}"/>
                        <p><fmt:message key="common.bets.results_for"/></p>
                        <div class="select-results-date input-group date" id='select-results-date'>
                            <input type="text" class="form-control"/>
                            <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span>
                                </span>
                        </div>
                    </div>
                    <div class="col-lg-4">
                        <p><fmt:message key="common.bets.confederation"/></p>
                        <input type="hidden" id="prev-confederation" value="${param.confederation}"/>
                        <div class="common-select">
                            <select id="results-confederations" class="dark-select">
                                <option value="" disabled selected="selected"></option>
                                <option value="all">All</option>
                                <c:forEach var="item" items="${confederationList}">
                                    <option value="${item}">${item}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="col-lg-4">
                        <input class="input-btn show-results-btn" type="button" value="Show" id="show-results">
                    </div>
                </div>
            </div>
            <div class="colored-block">
                <table id="results" class="table table-bordered results-table" cellspacing="0" width="100%">
                    <thead>
                    <tr>
                        <th>№</th>
                        <th><fmt:message key="common.bets.date"/></th>
                        <th><fmt:message key="common.bets.event"/></th>
                        <th><fmt:message key="common.bets.score"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
                <p id="no-results" style="display:none;">
                    <fmt:message key="common.bets.results.no_results"/>
                </p>
                <p class="error-label" id="results-error" style="display:none;">
                    <fmt:message key="common.error.server_error"/>
                </p>
            </div>
        </div>
    </div>
    <%@ include file="jspf/footer.jspf" %>
    <%@ include file="jspf/logout.jspf" %>
</div>
</body>
</html>
