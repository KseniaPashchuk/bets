<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : 'en_EN'}"/>
<fmt:setBundle basename="locale"/>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="../../css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="../../css/bets-style.css">
    <script type="text/javascript" src="../../js/jquery-3.2.1.js"></script>
    <script type="text/javascript" src="../../js/logout.js"></script>
    <link href="https://fonts.googleapis.com/css?family=Fira+Mono:400,500&amp;subset=cyrillic" rel="stylesheet">

</head>
<body>
<div class="full-container">
    <header class="header-wrap row clearfix">
        <div class="logo-wrap colored-block col-lg-3">
            <a href="../index.html"><img class="logo-img" src="../../images/logo-1.png" alt="matchCoefficients"></a>
        </div>
        <div class="menu-wrap colored-block ">
            <ul class="main-menu clearfix">
                <li class="item-main"><a href="${pageContext.servletContext.contextPath}/pages/news.jsp"
                                         class="main-link">Новости</a>
                </li>
                <li class="item-main"><a href="games.html" class="main-link">Игры</a>
                </li>
                <li class="item-main">
                    <a href="${pageContext.servletContext.contextPath}/controller?command=show_faq" class="main-link">FAQ</a>
                </li>
                <li class="item-main">
                    <a href="${pageContext.servletContext.contextPath}/controller?command=show_profile"
                       class="main-link">Профиль</a>
                </li>
            </ul>
        </div>
        <div class="lang-wrap colored-block ">
            <a href="@" class="lang">RU</a>

        </div>
        <div class="user-action colored-block col-lg-3">
            <div class="authorization">
                <a href="javascript:showLogoutPopup()" class="btn-enter">Выход</a>
            </div>
        </div>
    </header>

    <section class="content clearfix">
        <div class="row user-profile">
            <div class="user-menu-wrap col-lg-3 col-md-3 col-sm-3">
                <div class="user-menu colored-block">
                    <div class="user-menu-item"><a
                            href="${pageContext.servletContext.contextPath}/controller?command=show_user_profile">Профиль</a></div>
                    <div class="user-menu-item"><a
                            href="${pageContext.servletContext.contextPath}/pages/common/changePassword.jsp">Изменить пароль</a></div>
                    <div class="user-menu-item dropdown">
                        <a href="" class="dropdown-toggle" data-toggle="dropdown">Мои ставки <span
                                class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="#">ALL</a></li>
                            <li><a href="#">WINNED</a></li>
                            <li><a href="#">LOOSED</a></li>
                        </ul>
                    </div>
                    <div class="user-menu-item"><a
                            href="${pageContext.servletContext.contextPath}/controller?command=refill_cash" style="color: #ffa71b">Пополнить
                        счет</a></div>

                </div>
            </div>
            <div class="user-info-wrap col-lg-9 col-md-9 col-sm-9" id="user-profile">
                <div class="refill-cash-wrap col-lg-9 col-md-9 col-sm-9" style="display: none" id="refill-user-cash">
                    <div class="refill-cash colored-block clearfix">
                        <div class="label-row">
                            <div class="meta-label">Текущая сумма на счете</div>
                            <div class="meta-label">Сумма для пополенения</div>
                            <div class="meta-label">Выбор платежного средства</div>
                        </div>
                        <div class="value-row">
                            <div class="meta-value" id="current-amount">${balance}</div>
                            <div class="meta-value">
                                <input type="text" id="refill-amount" placeholder="" value="" autocomplete="off">
                            </div>
                            <div class="meta-value">
                                <div class="common-select clearfix">
                                    <select class="creditcard-choice" id="creditcard-choice">
                                        <option value="1" selected="selected">USD. Американский доллар</option>
                                        <option value="2">EUR. Евро</option>
                                        <option value="3">RUB. Российский рубль</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div style="text-align: center;">
                            <div class="btn-group">
                                <input type="submit" id="refill-cash-btn" value="Пополнить">
                                <input type="reset" class="cancel-refill-cash-btn" value="Отмена">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <%@include file="jspf/footer.jspf" %>

    <%@ include file="jspf/logout.jspf" %>
</div>
</body>
</html>

