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
    <link rel="stylesheet" href="../../resources/css/pagination.css"/>
    <link rel="stylesheet" type="text/css" href="../../resources/css/bets-style.css">
    <script type="text/javascript" src="../../resources/js/lib/jquery-3.2.1.js"></script>
    <script type="text/javascript" src="../../resources/js/lib/bootstrap.js"></script>
    <script type="text/javascript" src="../../resources/js/lib/pagination.js"></script>
    <script type="text/javascript" src="../../resources/js/lib/moment.js"></script>
    <script type="text/javascript" src="../../resources/js/admin/supportHelper.js"></script>
    <script type="text/javascript" src="../../resources/js/logout.js"></script>
    <link href="https://fonts.googleapis.com/css?family=Fira+Mono:400,500&amp;subset=cyrillic" rel="stylesheet">
</head>
<body>
<div class="full-container">
    <%@include file="../admin/jspf/header.jspf" %>

    <section class="content clearfix">
        <div class="row container">
            <div class="mail-box colored-block">
                <table class="table table-inbox table-hover">
                    <tbody>
                    </tbody>
                </table>
                <div class="mail-pagination" id="pagination">
                </div>
            </div>
            <div class="colored-block show-message">
                <p class="" id="no-mail" style="display:none;">
                    <fmt:message key="common.mail.no_mail"/>
                </p>
                <p class="error-label" id="mail-error" style="display:none;">
                    <fmt:message key="common.mail.error"/>
                </p>
            </div>
        </div>
    </section>

    <%@ include file="../common/jspf/footer.jspf" %>
    <%@ include file="../common/jspf/logout.jspf" %>
</div>
</body>
</html>
