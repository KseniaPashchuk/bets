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
    <link rel="stylesheet" type="text/css"
          href="${pageContext.servletContext.contextPath}/resources/css/bootstrap.css"/>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.servletContext.contextPath}/resources/css/bets-style.css"/>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.servletContext.contextPath}/resources/css/bootstrap-datetimepicker.css"/>
    <link href="https://fonts.googleapis.com/css?family=Fira+Mono:400,500&amp;subset=cyrillic" rel="stylesheet"/>
    <script type="text/javascript"
            src="${pageContext.servletContext.contextPath}/resources/js/lib/jquery-3.2.1.js"></script>
    <script type="text/javascript"
            src="${pageContext.servletContext.contextPath}/resources/js/lib/bootstrap.js"></script>
    <script type="text/javascript"
            src="${pageContext.servletContext.contextPath}/resources/js/lib/moment.js"></script>
    <script type="text/javascript"
            src="${pageContext.servletContext.contextPath}/resources/js/lib/bootstrap-datetimepicker.js"></script>
    <script type="text/javascript"
            src="${pageContext.servletContext.contextPath}/resources/js/registration.js"></script>

</head>
<body>
<header class="header-wrap header-row clearfix">
    <div class="logo-wrap col-lg-9">
        <a href="javascript://"><img class="logo-img" src="../../resources/images/logo-1.png" alt="bets"></a>
    </div>
    <form class="lang-wrap colored-block col-lg-3" method="POST"
          action="${pageContext.servletContext.contextPath}/controller">
        <input type="hidden" name="command" value="change_locale"/>
        <c:set var="lang" value="${fn:substring(sessionScope.locale, 3, 5)}"/>
        <a href="#" class="lang"><input type="submit" name="locale" value="${lang}"/></a>
    </form>
</header>
<div class="authorization-wrap">
    <div class="enter-wrap popup-holder" id="enter-wrap">

        <div class="buttons">
            <a class="btn-enter" href="javascript://" style="color: white"><fmt:message
                    key="common.registration.enter"/></a>
            <span>/</span>
            <a class="btn-registration" href="javascript://" style="color: #ffa71b"><fmt:message
                    key="common.registration.registration"/></a>
        </div>
        <form class="enter" id="login_form" onsubmit="return validateEnterForm()" method="POST"
              action="${pageContext.servletContext.contextPath}/controller">
            <input type="hidden" name="command" value="sign_in"/>
            <div class="head-enter-registration"><fmt:message key="common.registration.sign_in"/></div>
            <p class="error-label"
               <c:if test="${!btg:contains(errors,'signInError' )}">style="display:none;"</c:if>>
                <fmt:message key="login.error.enter_error"/>
            </p>

            <p class="error-label" id="invalid-params"
               <c:if test="${!btg:contains(errors,'invalidParamsError' )}">style="display:none;"</c:if>>

                <fmt:message key="login.error.invalid_params"/>
            </p>

            <div class="shell-for-input">
                <label for="login">
                    <input class="e-mail-enter <c:if test="${btg:contains(errors,'invalidParamsError' )}">error</c:if>"
                           type="text" id="login" name="login"
                           placeholder="<fmt:message key="common.login"/>" value=""
                           autocomplete="off">
                </label>
            </div>
            <div class="shell-for-input">
                <label for="password">
                    <input class='pass-enter <c:if test="${btg:contains(errors,'invalidParamsError' )}">error</c:if>'
                           type="password" id="password" name="password"
                           placeholder="<fmt:message key="common.password"/>"
                           value="" autocomplete="off">
                </label>
            </div>
            <a href="javascript://" class="forget-link" id="forget-password"><fmt:message
                    key="common.registration.forgot_password"/></a>
            <input type="submit" id="login-btn" class="sign-in-btn"
                   value="<fmt:message key="common.registration.sign_in"/>">
        </form>
    </div>
    <div class="register-wrap popup-holder" style="display: none" id="register-wrap">
        <div class="buttons">
            <a class="btn-enter" href="javascript://" style="color: #ffa71b"><fmt:message
                    key="common.registration.enter"/></a>
            <span>/</span>
            <a class="btn-registration" href="javascript://" style="color: white"><fmt:message
                    key="common.registration.registration"/></a>
        </div>

        <form class="registration" id="reg_form" method="POST"
              action="${pageContext.servletContext.contextPath}/controller" onsubmit="return validateRegisterForm()">
            <input type="hidden" name="command" value="sign_up"/>
            <div class="about-registration">
                <div class="head-enter-registration"><fmt:message key="common.registration.user_registration"/></div>
                <div class="necessarily"><fmt:message key="common.registration.required_fields"/></div>
                <div class="error-label" id="rule_error" style="display:none;">
                    <fmt:message key="signup.error.read_rules"/>
                </div>
                <p class="error-label"
                   <c:if test="${!btg:contains(errors,'signUpError' )}">style="display:none;"</c:if>>
                    <fmt:message key="signup.error.register.error"/>
                </p>
                <p class="error-label"
                   <c:if test="${!btg:contains(errors,'existingUserError' )}">style="display:none;"</c:if>>
                    <fmt:message key="signup.error.existing_user"/>
                </p>
            </div>
            <div class="shell-registration clearfix">
                <div class="left-side-of-registration">
                    <div class="shell-for-input">
                        <div class="shell-for-input add-hint">
                            <label for="reg_email">
                                <input class='e-mail-reg required <c:if test="${btg:contains(errors,'invalidLoginError' )}">error</c:if>'
                                       type="text" id="reg_email" name="login"
                                       autocomplete="false">
                                <div class="input-label" id="reg_email_label">email@example.com<span style="color: red">*</span>
                                </div>
                            </label>
                            <p class="error-label" id="invalid-login"
                               <c:if test="${!btg:contains(errors,'invalidLoginError' )}">style="display:none;"</c:if>>
                                <fmt:message key="signup.error.invalid_login"/>
                            </p>
                        </div>
                    </div>
                    <div class="shell-for-input">
                        <label for="reg_password">
                            <input type="password" id="reg_password"
                                   class="required <c:if test="${btg:contains(errors,'invalidPasswordError' )}">error</c:if>"
                                   name="password"
                                   autocomplete="false">
                            <div class="input-label" id="reg_password_label"><fmt:message key="common.password"/><span
                                    style="color: red">*</span></div>
                        </label>
                        <p class="error-label" id="invalid-password"
                           <c:if test="${!btg:contains(errors,'invalidPasswordError' )}">style="display:none;"</c:if>>

                            <fmt:message key="signup.error.invalid_password"/>

                        </p>
                    </div>
                    <div class="shell-for-input">
                        <label for="conf_password">
                            <input type="password" id="conf_password" name="confPassword" class="required"
                                   autocomplete="false">
                            <div class="input-label" id="conf_password_label"><fmt:message
                                    key="common.conf_password"/><span style="color: red">*</span></div>
                        </label>
                        <p class="error-label" id="invalid-conf-password" style="display:none;">
                            <fmt:message key="signup.error.match_password"/>
                        </p>
                    </div>
                    <div class="shell-for-input">
                        <label for="reg_name">
                            <input type="text" id="reg_name" class="required <c:if test="${btg:contains(errors,'invalidFirstNameError' )}">error</c:if>" name="name" autocomplete="false"
                                   >
                            <div class="input-label" id="reg_name_label"><fmt:message key="common.first_name"/><span
                                    style="color: red">*</span></div>
                        </label>
                        <p class="error-label" id="invalid-first-name"
                           <c:if test="${!btg:contains(errors,'invalidFirstNameError' )}">style="display:none;"</c:if>>

                            <fmt:message key="signup.error.invalid_first_name"/>

                        </p>
                    </div>

                </div>
                <div class="right-side-of-registration">
                    <div class="shell-for-input">
                        <label for="reg_surname">
                            <input type="text" id="reg_surname" class="required <c:if test="${btg:contains(errors,'invalidLastNameError' )}">error</c:if>" name="surname" autocomplete="false">
                            <div class="input-label" id="reg_surname_label"><fmt:message key="common.last_name"/><span
                                    style="color: red">*</span></div>
                        </label>
                        <p class="error-label" id="invalid-last-name"
                           <c:if test="${!btg:contains(errors,'invalidLastNameError' )}">style="display:none;"</c:if>>

                            <fmt:message key="signup.error.invalid_last_name"/>

                        </p>
                    </div>
                    <div class="shell-for-input">
                        <label for="reg_credit_card">
                            <input type="text" id="reg_credit_card" class="required <c:if test="${btg:contains(errors,'invalidCreditCardError' )}">error</c:if>" name="creditCard"
                                   autocomplete="false">
                            <div class="input-label" for="reg_credit_card" id="reg_credit_card_label"><fmt:message
                                    key="common.registration.credit_card_number"/><span style="color: red">*</span>
                            </div>
                        </label>
                        <p class="error-label" id="invalid-credit-card"
                           <c:if test="${!btg:contains(errors,'invalidCreditCardError' )}">style="display:none;"</c:if>>

                            <fmt:message key="signup.error.invalid_credit_card"/>

                        </p>
                    </div>
                    <div class="shell-for-input clearfix">
                        <div class="select-birth-date input-group date" id='select-birth-date'>
                            <input type="text" class="form-control <c:if test="${btg:contains(errors,'invalidBirthDateError' )}">error</c:if>"
                                   name="birthDate" placeholder="dd/mm/yyyy "/>
                            <span class="input-group-addon">
    <span class="glyphicon glyphicon-calendar"></span>
    </span>
                        </div>
                        <p class="error-label" id="invalid-birth-date"
                           <c:if test="${!btg:contains(errors,'invalidBirthDateError' )}">style="display:none;"</c:if>>

                            <fmt:message key="signup.error.invalid_birth_date"/>

                        </p>
                    </div>
                    <div class="confirmation">
                        <input id="rule" type="checkbox"/>
                        <label for="rule"><fmt:message key="common.registration.accept_rule.begin"/> <a
                                class='read-rule' href=''><fmt:message key="common.registration.accept_rule.terms"/></a>
                            <fmt:message key="common.registration.accept_rule.and"/><a class="orange"
                                                                                       href=""><fmt:message
                                    key="common.registration.accept_rule.conditions"/></a> <fmt:message
                                    key="common.registration.accept_rule.end"/></label>
                    </div>
                </div>
            </div>
            <input type="submit" id="register-btn" class="sign-up-btn"
                   value="<fmt:message key="common.registration.sign_up"/>">
        </form>
    </div>
    <div id="pass-recovery" <c:if test="${(!btg:contains(errors,'recoverPasswordError' ))&&
   (!btg:contains(errors,'invalidEmail' ))&&
    (!btg:contains(errors,'noSuchUserError' ))}">style="display:none;"</c:if>>
        <div class="popup-holder forget-pass-holder" id="forget-form"
             <c:if test="${(!btg:contains(errors,'recoverPasswordError' ))&&
   (!btg:contains(errors,'invalidEmail' ))&&
    (!btg:contains(errors,'noSuchUserError' ))}">style="display:none;"</c:if>>
            <h3><i class="fa fa-lock fa-4x"></i></h3>
            <h2 class="text-center"><fmt:message key="common.registration.forgot_password"/></h2>
            <p><fmt:message key="common.registration.reset_password"/></p>
            <p class="error-label"
               <c:if test="${!btg:contains(errors,'recoverPasswordError' )}">style="display:none;"</c:if>>
                <fmt:message key="common.recover_password.error"/>
            </p>
            <p class="error-label"
               <c:if test="${!btg:contains(errors,'noSuchUserError' )}">style="display:none;"</c:if>>
                <fmt:message key="common.user.not_found"/>
            </p>
            <form class="forget-form" id="pass-rec" action="${pageContext.servletContext.contextPath}/controller"
                  method="POST" onsubmit="validateRecoverPasswordForm()">
                <input type="hidden" name="command" value="password_recover">
                <input class="<c:if test="${btg:contains(errors,'invalidEmail' )}">error</c:if>" type="text" placeholder="<fmt:message key="common.email"/>" name="email" id="pass-recover-email">
                <p class="error-label" id="invalid-email"
                   <c:if test="${!btg:contains(errors,'invalidEmail' )}">style="display:none;"</c:if>>
                    <fmt:message key="signup.error.invalid_login"/>
                </p>
                <input class="button-enter button-forget" type="submit"
                       value='<fmt:message key="common.registration.recover_password"/>'>
            </form>
            <div class="popup-close forget-pass-close"><a href="">x</a></div>
        </div>
    </div>
</div>
</body>
</html>

