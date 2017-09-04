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
    <script type="text/javascript" src="../../resources/js/profileHelper.js"></script>
    <link href="https://fonts.googleapis.com/css?family=Fira+Mono:400,500&amp;subset=cyrillic" rel="stylesheet">

</head>
<body>
<div class="full-container">

    <%@include file="../user/jspf/header.jsp" %>

    <section class="content clearfix">
        <div class="row user-profile">
            <div class="user-menu-wrap col-lg-3 col-md-3 col-sm-3">
                <div class="user-menu colored-block">
                    <div class="user-menu-item"><a
                            href="${pageContext.servletContext.contextPath}/controller?command=show_user_profile"><fmt:message
                            key="common.menu.profile"/></a>
                    </div>
                    <div class="user-menu-item"><a
                            href="${pageContext.servletContext.contextPath}/pages/common/changePassword.jsp"
                            style="color: #ffa71b"><fmt:message
                            key="common.profile.edit_password"/></a></div>
                    <c:if test="${role == 'USER'}">
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
                                href="${pageContext.servletContext.contextPath}/controller?command=create_refill_cash_page">
                            <fmt:message key="common.profile.refill_cash"/></a></div>
                    </c:if>
                </div>
            </div>
            <div class="change-password-wrap col-lg-9 col-md-9 col-sm-9">
                <div class="change-password colored-block clearfix">
                    <p class="show-message success-label"
                       <c:if test="${success == null}">style="display:none;"</c:if>>
                        <fmt:message key="common.profile.change_password.success"/>
                    </p>
                    <p class="error-label" id="invalid-current-password"
                       <c:if test="${!btg:contains(errors,'invalidCurrentPasswordError' )}">style="display:none;"</c:if>>
                        <fmt:message key="common.profile.change_password.invalid_current"/>
                    </p>
                    <p class="error-label"
                       <c:if test="${!btg:contains(errors,'notEqualCurrentPasswordError' )}">style="display:none;"</c:if>>
                        <fmt:message key="common.profile.change_password.current_not_match"/>
                    </p>
                    <p class="error-label" id="invalid-new-password"
                       <c:if test="${!btg:contains(errors,'invalidNewPasswordError' )}">style="display:none;"</c:if>>
                        <fmt:message key="common.profile.change_password.invalid_new"/>
                    </p>
                    <p class="error-label" id="invalid-conf-password" style="display:none;">
                        <fmt:message key="signup.error.match_password"/>
                    </p>
                    <p class="error-label"
                       <c:if test="${!btg:contains(errors,'changePasswordError' )}">style="display:none;"</c:if>>
                        <fmt:message key="common.profile.change_password.error"/>
                    </p>
                    <form method="POST" action="${pageContext.servletContext.contextPath}/controller"
                          onsubmit=" return validateChangePasswordForm()">
                        <input type="hidden" name="command" value="change_password">
                        <div class="label-row">
                            <div class="meta-label"><fmt:message
                                    key="common.profile.current_password"/></div>
                            <div class="meta-label"><fmt:message
                                    key="common.profile.new_password"/></div>
                            <div class="meta-label"><fmt:message
                                    key="common.conf_password"/></div>
                        </div>
                        <div class="value-row">
                            <div class="meta-value" id="current_password">
                                <input class="input-text  <c:if test="${!btg:contains(errors,'invalidCurrentPasswordError' )}">error</c:if>"
                                       type="password" id="current-password" placeholder="" value=""
                                       autocomplete="off" name="currentPassword">
                            </div>
                            <div class="meta-value">
                                <input class="input-text <c:if test="${!btg:contains(errors,'invalidNewPasswordError' )}">error</c:if>"
                                       type="password" id="new-password" name="newPassword"
                                       placeholder="" value="" autocomplete="off">
                            </div>
                            <div class="meta-value">
                                <input class="input-text"
                                       type="password" id="confirm-password" name="confirmPassword"
                                       placeholder="" value="" autocomplete="off">
                            </div>
                        </div>
                        <div class="btn-group">
                            <input type="submit" id="change-password-btn" value="<fmt:message
                                    key="common.btn.save"/>">
                            <input type="reset" class="cancel-change-password-btn" value="<fmt:message
                                    key="common.btn.cancel"/>">
                        </div>
                    </form>


                </div>
            </div>
        </div>
    </section>

    <%@ include file="jspf/footer.jspf" %>
    <%@ include file="jspf/logout.jspf" %>
</div>
</body>
</html>

