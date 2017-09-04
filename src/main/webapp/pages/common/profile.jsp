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
    <link rel="stylesheet" type="text/css" href="../../resources/css/bets-style.css">
    <script type="text/javascript" src="../../resources/js/lib/jquery-3.2.1.js"></script>
    <script type="text/javascript" src="../../resources/js/lib/moment.js"></script>
    <script type="text/javascript" src="../../resources/js/lib/bootstrap-datetimepicker.js"></script>
    <script type="text/javascript" src="../../resources/js/logout.js"></script>
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
                            href="${pageContext.servletContext.contextPath}/controller?command=show_user_profile"
                            style="color: #ffa71b"><fmt:message key="common.menu.profile"/></a>
                    </div>
                    <div class="user-menu-item"><a
                            href="${pageContext.servletContext.contextPath}/pages/common/changePassword.jsp"
                    ><fmt:message key="common.profile.edit_password"/></a></div>
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
                            href="${pageContext.servletContext.contextPath}/controller?command=create_refill_cash_page"><fmt:message
                            key="common.profile.refill_cash"/></a></div>
                    </c:if>
                </div>
            </div>
            <div class="user-info-wrap col-lg-9 col-md-9 col-sm-9"
                 <c:if test="${(btg:contains(errors,'invalidFirstNameError' ))||
                 (btg:contains(errors,'invalidLastNameError' ))||
                 (btg:contains(errors,'invalidBirthDateError' ))||
                 (btg:contains(errors,'invalidCreditCardError' ))}">style="display:none;"</c:if>
                 id="user-profile">
                <div class="colored-block">
                    <p class="error-label"
                       <c:if test="${!btg:contains(errors,'updateUserAvatarError' )}">style="display:none;"</c:if>>
                        <fmt:message key="common.profile.update_avatar.error"/>
                    </p>
                    <p class="error-label"
                       <c:if test="${!btg:contains(errors,'changeProfileError' )}">style="display:none;"</c:if>>
                        <fmt:message key="common.profile.edit.error"/>
                    </p>
                    <p class="error-label"
                       <c:if test="${!btg:contains(errors,'showProfileError' )}">style="display:none;"</c:if>>
                        <fmt:message key="common.profile.show.error"/>
                    </p>
                </div>
                <div class="user-info colored-block">
                    <form class="user-avatar col-lg-4 col-md-4 col-sm-4"
                          action="${pageContext.servletContext.contextPath}/controller"
                          method="POST" enctype="multipart/form-data">
                        <input type="hidden" name="command" value="edit_user_avatar"/>
                        <div class="avatar-wrap">
                            <img class="avatar-pic" id="avatar-pic"
                                 src="${pageContext.request.contextPath}/image/user/${avatarUrl}"
                                 alt="<fmt:message key="common.profile.cant_find_avatar"/>">
                        </div>
                        <div class="btn-group">
                            <label class="btn-action" for="avatar"><fmt:message key="common.btn.choose"/></label>
                            <input type="submit" id="saveAvatar" value="<fmt:message key="common.btn.save"/>">
                        </div>
                        <input type="file" name="avatarUrl" id="avatar" style="display: none">
                    </form>
                    <div class="user-meta col-lg-8 col-md-8 col-sm-8 clearfix">
                        <div class="label-row col-lg-5">
                            <div class="meta-label"><fmt:message key="common.login"/>(email)</div>
                            <div class="meta-label"><fmt:message key="common.first_name"/></div>
                            <div class="meta-label"><fmt:message key="common.last_name"/></div>
                            <div class="meta-label"><fmt:message key="common.profile.birth_date"/></div>
                            <div class="meta-label"><fmt:message key="common.profile.balance"/></div>
                            <div class="meta-label"><fmt:message key="common.profile.credit_cards"/></div>
                        </div>
                        <div class="value-row col-lg-7">
                            <div class="meta-value">${login}</div>
                            <div class="meta-value">${name}</div>
                            <div class="meta-value">${surname}</div>
                            <div class="meta-value">${birthDate}</div>
                            <div class="meta-value">${balance}</div>
                            <c:forEach var="item" items="${creditCards.creditCardList}">
                                <div class="meta-value">${item}</div>
                            </c:forEach>
                        </div>
                    </div>
                    <button class="btn-action change-meta" id="change-user-meta"><a href=""><fmt:message
                            key="common.btn.edit"/></a></button>
                </div>
            </div>
            <div class="edit-info-wrap col-lg-9 col-md-9 col-sm-9"
                 <c:if test="${(!btg:contains(errors,'invalidFirstNameError' ))&&
                 (!btg:contains(errors,'invalidLastNameError' ))&&
                 (!btg:contains(errors,'invalidBirthDateError' ))&&
                 (!btg:contains(errors,'invalidCreditCardError' ))}">style="display:none;"</c:if>
                 id="edit-user-info">
                <div class="edit-info colored-block">
                    <form action="controller" method="POST" onsubmit="return validateEditProfileForm()">
                        <input type="hidden" name="command" value="edit_profile">
                        <div class="shell-for-input">
                            <label for="edit-name">
                                <div class="edit-label"><fmt:message key="common.first_name"/></div>
                                <input class='edit-input <c:if test="${btg:contains(errors,'invalidFirstNameError' )}">error</c:if>'
                                       type="text" id="edit-name" name="name" value="${name}">
                            </label>
                            <p class="error-label" id="invalid-first-name"
                               <c:if test="${!btg:contains(errors,'invalidFirstNameError' )}">style="display:none;"</c:if>>
                                <fmt:message key="signup.error.invalid_first_name"/>
                            </p>
                        </div>
                        <div class="shell-for-input">
                            <label for="edit-surname">
                                <div class="edit-label"><fmt:message key="common.last_name"/></div>
                                <input class='edit-input <c:if test="${btg:contains(errors,'invalidLastNameError' )}">error</c:if>'
                                       type="text" id="edit-surname" name="surname"
                                       value="${surname}">
                            </label>
                            <p class="error-label" id="invalid-last-name"
                               <c:if test="${!btg:contains(errors,'invalidLastNameError' )}">style="display:none;"</c:if>>
                                <fmt:message key="signup.error.invalid_last_name"/>
                            </p>
                        </div>
                        <div class="shell-for-input">
                            <div style="display: flex;">
                                <div class="edit-label"><fmt:message key="common.profile.birth_date"/></div>
                                <div class="edit-input clearfix">
                                    <div class="input-group date <c:if test="${btg:contains(errors,'invalidBirthDateError' )}">error</c:if>"
                                         id='edit-birth-date'>
                                        <input type="text" class="form-control" name="birthDate"
                                               value="${birthDate}"/>
                                        <span class="input-group-addon">
												<span class="glyphicon glyphicon-calendar"></span>
											</span>
                                    </div>
                                </div>

                            </div>
                            <p class="error-label" id="invalid-birth-date"
                               <c:if test="${!btg:contains(errors,'invalidBirthDateError' )}">style="display:none;"</c:if>>
                                <fmt:message key="signup.error.invalid_birth_date"/>
                            </p>
                        </div>
                        <div class="shell-for-input clearfix">
                            <div class="edit-label col-lg-3"><fmt:message key="common.profile.credit_cards"/></div>
                            <div class="col-lg-7">
                                <c:forEach var="item" items="${creditCards.creditCardList}">
                                    <div class="input-group credit-card active" id="credit_card_${item}">
                                        <input class='edit-input form-control <c:if test="${btg:contains(errors,'invalidCreditCardError' )}">error</c:if>'
                                               type="text" id="${item}"
                                               name="creditCard"
                                               value="${item}">
                                        <div class="input-group-addon"><input type="button" class="delete-card-btn"
                                                                              id="delete_${item}" value="delete"></div>
                                    </div>
                                </c:forEach>
                                <div class="input-group credit-card hidden" id="credit_card_1">
                                    <input class='edit-input form-control <c:if test="${btg:contains(errors,'invalidCreditCardError' )}">error</c:if>'
                                           type="text" id="1" name="creditCard"
                                           value="">
                                    <div class="input-group-addon"><input type="button" class="delete-card-btn"
                                                                          id="delete_1" value="delete">
                                    </div>
                                </div>
                                <div class="input-group credit-card hidden" id="credit_card_2">
                                    <input class='edit-input form-control <c:if test="${btg:contains(errors,'invalidCreditCardError' )}">error</c:if>'
                                           type="text" id="2" name="creditCard"
                                           value="">
                                    <div class="input-group-addon"><input type="button" class="delete-card-btn"
                                                                          id="delete_2" value="delete">
                                    </div>
                                </div>
                            </div>
                            <button class="col-lg-2 add-credit-card" id="add-credit-card-btn"><span
                                    class="glyphicon glyphicon-plus"></span></button>
                            <p class="error-label" id="invalid-credit-cards"
                               <c:if test="${!btg:contains(errors,'invalidCreditCardError' )}">style="display:none;"</c:if>>
                                <fmt:message key="signup.error.invalid_credit_card"/>
                            </p>
                        </div>
                        <div style="text-align: center;">
                            <div class="btn-group">
                                <input type="submit" id="edit-profile-btn" value="<fmt:message key="common.btn.save"/>">
                                <input type="reset" class="cancel-edit-profile-btn"
                                       value="<fmt:message key="common.btn.cancel"/>">
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>

    <%@include file="jspf/footer.jspf" %>

    <%@ include file="jspf/logout.jspf" %>
</div>
</body>
</html>
