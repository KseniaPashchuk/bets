<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="../css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="../css/bets-style.css">
    <script type="text/javascript" src="../js/jquery-3.2.1.js"></script>
    <script type="text/javascript" src="../js/index.js"></script>
    <script type="text/javascript" src="../js/profileHelper.js"></script>
    <link href="https://fonts.googleapis.com/css?family=Fira+Mono:400,500&amp;subset=cyrillic" rel="stylesheet">

</head>
<body>
<div class="full-container">
    <header class="header-wrap row clearfix">
        <div class="logo-wrap colored-block col-lg-3">
            <a href="../index.html"><img class="logo-img" src="../images/logo-1.png" alt="bets"></a>
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
                    <div class="user-menu-item"><a href="" id="profile">Профиль</a></div>
                    <div class="user-menu-item"><a href="" id="edit">Редактировать профиль</a></div>
                    <div class="user-menu-item"><a href="">Мои ставки</a></div>
                    <%--TODO winned, loosed, all--%>
                    <div class="user-menu-item"><a href="" id="cash">Пополнить счет</a></div>

                        <%--<div class="user-menu-item"><div id="profile">Профиль</div></div>--%>
                        <%--<div class="user-menu-item"><div id="edit">Редактировать профиль</div></div>--%>
                        <%--<div class="user-menu-item"><a href="${pageContext.servletContext.contextPath}/pages/userBets.jsp">Мои ставки</a></div>--%>
                        <%--&lt;%&ndash;TODO winned, loosed, all&ndash;%&gt;--%>
                        <%--<div class="user-menu-item"><div id="cash">Пополнить счет</div></div>--%>
                </div>
            </div>
            <div class="user-info-wrap col-lg-9 col-md-9 col-sm-9" id="user-profile">
                <div class="user-info colored-block">
                    <div class="user-avatar col-lg-5 col-md-5 col-sm-5">
                        <img class="avatar-pic" src="../images/kot.png" alt="can't find your picture">
                        <%--TODO--%>
                        <button class="btn-action" id="change-user-avatar">Изменить аватар</button>
                    </div>
                    <div class="user-meta col-lg-7 col-md-7 col-sm-7 clearfix">
                        <div class="label-row col-lg-6">
                            <div class="meta-label">Логин(email)</div>
                            <div class="meta-label">Имя</div>
                            <div class="meta-label">Фамилия</div>
                            <div class="meta-label">Дата рождения</div>
                            <div class="meta-label">Баланс</div>
                            <div class="meta-label">Кредитная карта</div>
                        </div>
                        <div class="value-row col-lg-6">
                            <div class="meta-value">${login}</div>
                            <div class="meta-value">${name}</div>
                            <div class="meta-value">${surname}</div>
                            <div class="meta-value">${birthDate}</div>
                            <div class="meta-value">${balance}</div>
                            <c:forEach var="item" items="${creditCardList.creditCards}">
                                <div class="meta-value">${item}</div>
                            </c:forEach>
                        </div>
                        <button class="btn-action change-meta" id="change-user-meta"><a href="editProfile.jsp">Редактировать</a></button>
                    </div>
                </div>
            </div>
            <div class="edit-info-wrap col-lg-9 col-md-9 col-sm-9" style="display: none" id="edit-user-info">
                <div class="edit-info colored-block">
                    <form action="controller" method="POST">
                        <input type="hidden" name="command" value="edit_profile"/>
                        <div class="shell-for-input">
                            <label for="edit_password">
                                <div class="edit-label">Пароль</div>
                                <input class='edit-input' type="password" id="edit_password" name="edit_password">
                            </label>
                        </div>
                        <div class="shell-for-input">
                            <label for="conf_password">
                                <div class="edit-label">Подтвердите пароль</div>
                                <input class='edit-input' type="password" id="conf_password" name="conf_password">
                            </label>
                        </div>
                        <div class="shell-for-input">
                            <label for="edit_name">
                                <div class="edit-label">Имя</div>
                                <input class='edit-input' type="text" id="edit_name" name="edit_name" value="${name}">
                            </label>
                        </div>
                        <div class="shell-for-input">
                            <label for="edit_surname">
                                <div class="edit-label">Фамилия</div>
                                <input class='edit-input' type="text" id="edit_surname" name="edit_surname"
                                       value="${surname}">
                            </label>
                        </div>
                        <div class="shell-for-input">
                            <div style="display: flex;">
                                <div class="edit-label">Дата рождения</div>
                                <div class="edit-input clearfix">
                                    <div class="input-group date" id='edit-birth-date' >
                                        <input type="text" class="form-control" name="edit_birth_date" value="${birthDate}"/>
                                        <span class="input-group-addon">
												<span class="glyphicon glyphicon-calendar"></span>
											</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="shell-for-input clearfix">
                            <div class="edit-label col-lg-3">Кредитные карты</div>
                            <div class="col-lg-7">
                                <c:forEach var="item" items="${creditCardList.creditCards}">
                                    <div class="input-group credit-card active" id="credit_card_122">
                                        <input class='edit-input form-control' type="text" id="${item}"
                                               name="credit_card"
                                               value="${item}">
                                        <div class="input-group-addon"><input type="button" class="delete-card-btn"
                                                                              id="delete_${item}" value="delete"></div>
                                    </div>
                                </c:forEach>
                                <div class="input-group credit-card hidden" id="credit_card_1">
                                    <input class='edit-input form-control' type="text" id="1" name="credit_card"
                                           value="">
                                    <div class="input-group-addon"><input type="button" class="delete-card-btn"
                                                                          id="delete_1" value="delete">
                                    </div>
                                </div>
                                <div class="input-group credit-card hidden" id="credit_card_2">
                                    <input class='edit-input form-control' type="text" id="2" name="credit_card"
                                           value="">
                                    <div class="input-group-addon"><input type="button" class="delete-card-btn"
                                                                          id="delete_2" value="delete">
                                    </div>
                                </div>
                            </div>
                            <button class="col-lg-2 add-credit-card" id="add-credit-card-btn"><span
                                    class="glyphicon glyphicon-plus"></span></button>
                        </div>
                        <div style="text-align: center;">
                            <div class="btn-group">
                                <input type="submit" id="edit-profile-btn" value="Сохранить">
                                <input type="reset" class="cancel-edit-profile-btn" value="Отмена">
                            </div>
                        </div>
                    </form>
                </div>
            </div>
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
                            <input type="submit" id="refill-cash-btn" value="Сохранить">
                            <input type="reset" class="cancel-refill-cash-btn" value="Отмена">
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <footer class="row ">
        <section class="footer-coloms clearfix">
            <div class="bets-col">
                <span class="col-title">Ставки</span>
                <ul>
                    <li>
                        <a href="javascript://" rel="nofollow">Правила</a>
                    </li>
                    <li>
                        <a href="javascript://" rel="nofollow">События</a>
                    </li>
                    <li>
                        <a href="javascript://" rel="nofollow">Результаты</a>
                    </li>
                </ul>
            </div>

            <div class="help-col">
                <span class="col-title">Помощь</span>
                <ul>
                    <li>
                        <a href="javascript://" rel="nofollow">FAQ</a>
                    </li>
                </ul>
            </div>
            <div class="comp-col">
                <span class="col-title">BETS</span>
                <ul>
                    <li>
                        <a href="javascript://" rel="nofollow">О нас</a>
                    </li>
                    <li>
                        <a href="javascript://" rel="nofollow">Правила сайта</a>
                    </li>
                </ul>
            </div>
            <div class="connect-col">
                <ul>
                    <li>
                        <a href="javascript://"><img src="../images/twitter.png" class="connect-us"></a>
                    </li>
                    <li>
                        <a href="javascript://"> <img src="../images/youtube.png" class="connect-us"></a>
                    </li>
                    <li>
                        <a href="javascript://"> <img src="../images/facebook.png" class="connect-us"></a>
                    </li>
                </ul>
            </div>
            <div class="age-limit-col">
                <a href="javascript://"><img src="../images/ft_18.png" class="age-limit"></a>
            </div>
        </section>
        <section class="copyright colored-block">
            <p>©2017 <span>Bets - удача навашей стороне.</span>
            </p>
        </section>

    </footer>

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
