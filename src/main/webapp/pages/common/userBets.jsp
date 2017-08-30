<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
    <script type="text/javascript" src="../../resources/js/lib/jquery-3.2.1.js"></script>
    <script type="text/javascript" src="../../resources/js/logout.js"></script>
    <script type="text/javascript" src="../../resources/js/lib/bootstrap.js"></script>
    <link href="https://fonts.googleapis.com/css?family=Fira+Mono:400,500&amp;subset=cyrillic" rel="stylesheet">
    <script type="text/javascript" src="https://cdn.datatables.net/1.10.13/js/jquery.dataTables.min.js"></script>
    <script type="text/javascript" src="https://cdn.datatables.net/1.10.13/js/dataTables.bootstrap.min.js"></script>
    <script type="text/javascript" src="../../resources/js/userBetsHelper.js"></script>

</head>
<body>
<div class="full-container">
    <%@include file="../user/jspf/header.jsp" %>
    <section class="content clearfix">
        <div class="row user-profile">
            <div class="user-menu-wrap col-lg-3 col-md-3 col-sm-3">
                <div class="user-menu colored-block">
                    <div class="user-menu-item"><a
                            href="${pageContext.servletContext.contextPath}/controller?command=show_user_profile"
                    ><fmt:message key="common.menu.profile"/></a>
                    </div>
                    <div class="user-menu-item"><a
                            href="${pageContext.servletContext.contextPath}/pages/common/changePassword.jsp"
                    ><fmt:message
                            key="common.profile.edit_password"/></a></div>
                    <div class="user-menu-item dropdown">
                        <a href="${pageContext.servletContext.contextPath}/pages/common/userBets.jsp" style="color: #ffa71b" class="dropdown-toggle" data-toggle="dropdown"><fmt:message
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
                    <div class="user-menu-item"><a
                            href="${pageContext.servletContext.contextPath}/controller?command=create_refill_cash_page"
                    ><fmt:message
                            key="common.profile.refill_cash"/></a></div>
                </div>
            </div>
            <div class="user-bets-wrap col-lg-9 col-md-9 col-sm-9">
                <div class="user-bets colored-block">
                    <table id="user-bets-table" class="table table-bordered bets-table" cellspacing="0" width="100%">
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
                    <p class="" id="no-bets" style="display:none;">
                        <fmt:message key="common.user_bets.no_bets"/>
                    </p>
                    <p class="error-label" id="bets-error" style="display:none;">
                        <fmt:message key="common.error.server_error"/>
                    </p>

                </div>
            </div>
        </div>
    </section>

    <%@include file="jspf/footer.jspf" %>
    <%@ include file="jspf/logout.jspf" %>
</div>
</body>
</html>

