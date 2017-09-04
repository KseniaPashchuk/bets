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
    <link rel="stylesheet" type="text/css" href="../../resources/css/bets-style.css">
    <script type="text/javascript" src="../../resources/js/lib/jquery-3.2.1.js"></script>
    <script type="text/javascript" src="../../resources/js/logout.js"></script>
    <link href="https://fonts.googleapis.com/css?family=Fira+Mono:400,500&amp;subset=cyrillic" rel="stylesheet">

</head>
<body>
<div class="full-container">
    <%@include file="jspf/header.jsp" %>

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
                        <a href="${pageContext.servletContext.contextPath}/pages/user/userBets.jsp"
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
                    <div class="user-menu-item"><a
                            href="${pageContext.servletContext.contextPath}/controller?command=create_refill_cash_page"
                            style="color: #ffa71b"><fmt:message
                            key="common.profile.refill_cash"/></a></div>

                </div>
            </div>

            <div class="refill-cash-wrap col-lg-9 col-md-9 col-sm-9" id="refill-user-cash">
                <div class="colored-block">
                    <p class="error-label"
                       <c:if test="${!btg:contains(errors,'refillCashError' )}">style="display:none;"</c:if>>
                        <fmt:message key="common.profile.refill_cash.error"/>
                    </p>
                </div>
                <div class="refill-cash colored-block clearfix">
                    <div class="label-row">
                        <div class="meta-label"><fmt:message
                                key="common.profile.refill_cash.current_balance"/></div>
                        <div class="meta-label"><fmt:message
                                key="common.profile.refill_cash.refill_summ"/></div>
                        <div class="meta-label"><fmt:message
                                key="common.profile.refill_cash.choose_card"/></div>
                    </div>
                    <form action="${pageContext.servletContext.contextPath}/controller" method="POST">
                        <input type="hidden" name="command" value="refill_cash">
                    <div class="value-row">
                            <div class="meta-value" id="current-amount">${balance}</div>
                            <div class="meta-value">
                                <input class="<c:if test="${!btg:contains(errors,'invalidRefillAmount' )}">error</c:if>"
                                       type="text" name="amount" id="refill-amount"
                                       placeholder="" value="" autocomplete="off">
                                <p class="error-label"
                                   <c:if test="${!btg:contains(errors,'invalidRefillAmount' )}">style="display:none;"</c:if>>
                                    <fmt:message key="common.profile.refill_cash.invalid_amount"/>
                                </p>
                            </div>
                            <div class="meta-value">
                                <div class="common-select clearfix">
                                    <select class="creditcard-choice" id="creditcard-choice">
                                        <c:forEach var="item" items="${creditCards.creditCardList}">
                                            <option value="${item}">${item}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                    </div>
                    <div style="text-align: center;">
                        <div class="btn-group">
                            <input type="submit" id="refill-cash-btn" value="<fmt:message
                                    key="common.profile.refill_cash.refill"/>">
                            <input type="reset" class="cancel-refill-cash-btn" value="<fmt:message
                                    key="common.btn.cancel"/>">
                        </div>
                    </div>
                    </form>
                </div>
            </div>
        </div>

    </section>

    <%@include file="../common/jspf/footer.jspf" %>

    <%@ include file="../common/jspf/logout.jspf" %>
</div>
</body>
</html>

