<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="bettags" prefix="btg" %>
<fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : 'en_EN'}"/>
<fmt:setBundle basename="pagelocale"/>
<html>
<head>
    <title><fmt:message key="company_name"/></title>
    <link rel="stylesheet" type="text/css" href="../../resources/css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="../../resources/css/bootstrap-datetimepicker.css">
    <link rel="stylesheet" type="text/css" href="../../resources/css/dataTables.bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="../../resources/css/bets-style.css">
    <script type="text/javascript" src="../../resources/js/lib/jquery-3.2.1.js"></script>
    <script type="text/javascript" src="../../resources/js/lib/moment.js"></script>
    <script type="text/javascript" src="../../resources/js/lib/bootstrap.js"></script>
    <script type="text/javascript" src="../../resources/js/lib/bootstrap-datetimepicker.js"></script>
    <script type="text/javascript" src="../../resources/js/lib/jquery.dataTables.min.js"></script>
    <script type="text/javascript" src="../../resources/js/lib/dataTables.bootstrap.min.js"></script>
    <script type="text/javascript" src="../../resources/js/logout.js"></script>
    <script type="text/javascript" src="../../resources/js/admin/userSettingHelper.js"></script>
    <link href="https://fonts.googleapis.com/css?family=Fira+Mono:400,500&amp;subset=cyrillic" rel="stylesheet">

</head>
<body>
<div class="full-container">
    <%@include file="../admin/jspf/header.jspf" %>

    <section class="content clearfix">
        <div class="row">
            <div class="search-user-wrap colored-block">
                <div class="search-user">
                    <form class="clearfix" action="${pageContext.request.contextPath}/controller" method="GET">
                        <input type="hidden" name="command" value="show_user_info">
                        <p class="error-label"
                           <c:if test="${!btg:contains(errors,'noSuchUserError' )}">style="display:none;"</c:if>>
                            <fmt:message key="common.user.not_found"/>
                        </p>
                        <p class="error-label"
                           <c:if test="${!btg:contains(errors,'invalidLoginError' )}">style="display:none;"</c:if>>
                            <fmt:message key="signup.error.invalid_login"/>
                        </p>
                        <input class="input-text
<c:if test="${!btg:contains(errors,'invalidLoginError' )}">error</c:if>" type="text" name="email" placeholder="
<fmt:message key="admin.user_setting.find"/>"/>

                        <input type="submit" class="search-user-btn btn-action"
                               value="<fmt:message key="common.btn.show"/>">
                    </form>
                </div>
            </div>

            <div class="found-user-wrap colored-block"
                    <c:if test="${(btg:contains(errors,'invalidLoginError'))||
                    (btg:contains(errors,'noSuchUserError'))||
                    (requestScope.user.login==null)}">
                        style="display: none;"
                    </c:if>>
                <div class="user-info">
                    <div class="user-avatar col-lg-4 col-md-4 col-sm-4">
                        <div class="avatar-wrap">
                            <img class="avatar-pic" id="avatar-pic"
                                 src="${pageContext.request.contextPath}/image/user/${requestScope.user.avatarUrl}"
                                 alt="<fmt:message key="common.profile.cant_find_avatar"/>">
                        </div>
                    </div>
                    <div class="user-meta col-lg-6 col-md-6 col-sm-6 cleafix">
                        <div class="label-row col-lg-5">
                            <div class="meta-label"><fmt:message key="common.login"/>(email)</div>
                            <div class="meta-label"><fmt:message key="common.first_name"/></div>
                            <div class="meta-label"><fmt:message key="common.last_name"/></div>
                            <div class="meta-label"><fmt:message key="common.profile.birth_date"/></div>

                            <c:if test="${(requestScope.user.role != 'ADMIN')&&(requestScope.user.role != 'BOOKMAKER')}">
                                <div class="meta-label"><fmt:message key="common.profile.balance"/></div>
                                <div class="meta-label"><fmt:message key="common.profile.credit_cards"/></div>
                            </c:if>
                        </div>
                        <div class="value-row col-lg-7">
                            <div class="meta-value">${requestScope.user.login}</div>
                            <div class="meta-value">${requestScope.user.firstName}</div>
                            <div class="meta-value">${requestScope.user.lastName}</div>
                            <div class="meta-value">
                                <fmt:parseDate value="${requestScope.user.birthDate}" pattern="yyyy-MM-dd"
                                               var="parsedDate"
                                               type="date"/>
                                <fmt:formatDate value="${parsedDate}" type="date" var="formattedDate"
                                                pattern="dd/MM/yy"/>
                                ${formattedDate}</div>
                            <c:if test="${(requestScope.user.role != 'ADMIN')&&(requestScope.user.role != 'BOOKMAKER')}">
                                <div class="meta-value">${requestScope.user.balance}</div>
                                <c:forEach var="item" items="${requestScope.user.creditCards.creditCardList}">
                                    <div class="meta-value">${item}</div>
                                </c:forEach>
                            </c:if>
                        </div>

                    </div>
                    <c:if test="${(requestScope.user.role != 'ADMIN')&&(requestScope.user.role != 'BOOKMAKER')}">
                        <div class="col-lg-2 col-md-2 col-sm-2 clearfix">
                            <button class="user-bets-btn" id="show-user-bets">Bets</button>
                        </div>
                    </c:if>
                </div>
                <div class="user-bets colored-block" style="display:none;">
                    <div class="user-menu-item dropdown">
                        <a href="javascript://"
                           class="dropdown-toggle" data-toggle="dropdown"><fmt:message
                                key="common.profile.my_bets"/>
                            <span
                                    class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="#" id="open-bets"><fmt:message
                                    key="common.profile.open"/></a></li>
                            <li><a href="#" id="winned-bets"><fmt:message
                                    key="common.profile.winned"/></a></li>
                            <li><a href="#" id="lost-bets"><fmt:message
                                    key="common.profile.lost"/></a></li>
                        </ul>
                    </div>
                    <table id="user-bets-table" class="table table-bordered bets-table" cellspacing="0" width="100%">
                        <input type="hidden" id="email" value="${param.email}">
                        <thead>
                        <tr>
                            <th style="min-width: 300px"><fmt:message key="common.bets.event"/></th>
                            <th><fmt:message key="common.bets.bet_type"/></th>
                            <th><fmt:message key="common.bets.coefficient"/></th>
                            <th><fmt:message key="common.bets.summ"/></th>
                            <th><fmt:message key="common.bets.gain"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                    <p class="show-message" id="no-bets" style="display:none;">
                        <fmt:message key="common.user_bets.no_bets"/>
                    </p>
                    <p class="error-label" id="bets-error" style="display:none;">
                        <fmt:message key="common.error.server_error"/>
                    </p>

                </div>
            </div>
        </div>
    </section>
    <%@include file="../common/jspf/footer.jspf" %>
    <%@ include file="../common/jspf/logout.jspf" %>
</div>
</body>
</html>
