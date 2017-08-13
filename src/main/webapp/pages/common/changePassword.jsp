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
    <script type="text/javascript" src="../../js/index.js"></script>
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
                            href="${pageContext.servletContext.contextPath}/controller?command=show_user_profile">Профиль</a>
                    </div>
                    <div class="user-menu-item"><a
                            href="${pageContext.servletContext.contextPath}/pages/common/changePassword.jsp"
                            style="color: #ffa71b">Изменить пароль</a></div>
                    <div class="user-menu-item dropdown">
                        <a href="" class="dropdown-toggle" data-toggle="dropdown" id="matchCoefficients">Мои ставки <span
                                class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="#">open</a></li>
                            <li><a href="#">winned</a></li>
                            <li><a href="#">lost</a></li>
                        </ul>
                    </div>
                    <div class="user-menu-item"><a
                            href="${pageContext.servletContext.contextPath}/controller?command=refill_cash">Пополнить
                        счет</a></div>

                </div>
            </div>
            <div class="change-password-wrap col-lg-9 col-md-9 col-sm-9">
                <div class="change-password colored-block clearfix">
                    <form method="POST" action="${pageContext.servletContext.contextPath}/controller">
                        <input type="hidden" name="command" value="change_password">
                        <div class="label-row">
                            <div class="meta-label">Текущий пароль</div>
                            <div class="meta-label">Новый пароль</div>
                            <div class="meta-label">Повторите пароль</div>
                        </div>
                        <div class="value-row">
                            <div class="meta-value" id="current_password">
                                <input class="input-text" type="password" id="refill-amount" placeholder="" value=""
                                       autocomplete="off" name="current_password">
                            </div>
                            <div class="meta-value">
                                <input class="input-text" type="password" id="new_password" name="new_password"
                                       placeholder="" value="" autocomplete="off">
                            </div>
                            <div class="meta-value">
                                <input class="input-text" type="password" id="confirm_password" name="confirm_password"
                                       placeholder="" value="" autocomplete="off">
                            </div>
                        </div>
                        <div class="btn-group">
                            <input type="submit" id="change-password-btn" value="Сохранить">
                            <input type="reset" class="cancel-change-password-btn" value="Отмена">
                        </div>
                    </form>



                </div>
            </div>
        </div>
    </section>

    <%@include file="jspf/footer.jspf" %>

    <div class="popup-wrap" id="logout-popup">
        <div class="popup-holder" id="logout-check" style="display: none">
            <form class="logout-form clearfix" id="logout">
                <h3>Вы действительно хотите выйти?</h3>
                <div class="btn-group" method="GET" action="controller">
                    <input type="hidden" name="command" value="logout"/>
                    <input class="button-logout" id="btn-logout" type="submit" value='Выйти'>
                    <input class="button-logout" type="button" value='Отмена' onclick="closeLogoutPopup()">
                </div>
            </form>
            <div class="popup-close logout-close"><a href="javascript:closeLogoutPopup()">x</a></div>
        </div>
    </div>
</div>
</body>
</html>

