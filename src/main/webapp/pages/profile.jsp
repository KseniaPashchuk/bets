<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="../css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="../css/bets-style.css">
    <script type="text/javascript" src="../js/index.js"></script>
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
                <li class="item-main"><a href="news.html" class="main-link">Новости</a>
                </li>
                <li class="item-main"><a href="games.html" class="main-link">Игры</a>
                </li>
                <li class="item-main">
                    <a href="faq.html" class="main-link">FAQ</a>
                </li>
                <li class="item-main">
                    <a href="profile.html" class="main-link">Профиль</a>
                </li>
            </ul>
        </div>
        <div class="lang-wrap colored-block ">
            <a href="@" class="lang">RU</a>

        </div>
        <div class="user-action colored-block col-lg-3">
            <div class="authorization">
                <a href="javascript:showLogoutPopup()" class="btn-enter" >Выход</a>
            </div>
        </div>
    </header>

    <section class="content clearfix">
        <div class="row user-profile">
            <div class="user-menu-wrap col-lg-3 col-md-3 col-sm-3">
                <div class="user-menu colored-block">
                    <div class="user-menu-item"><a href="profile.html">Профиль</a></div>
                    <div class="user-menu-item"><a href="userBets.html">Мои ставки</a></div>
                    <!--Выигранные, проигранные, текущие, все-->
                    <div class="user-menu-item"><a href="refillCash.html">Пополнить счет</a></div>
                </div>
            </div>
            <div class="user-info-wrap col-lg-9 col-md-9 col-sm-9">
                <div class="user-info colored-block">
                    <div class="user-avatar col-lg-5 col-md-5 col-sm-5">
                        <img class="avatar-pic" src="../images/kot.png" alt="can't find your picture">
                        <button class="btn-action" id="change-user-avatar">Изменить аватар</button>
                    </div>
                    <div class="user-meta col-lg-7 col-md-7 col-sm-7 clearfix">
                        <div class="label-row">
                            <div class="meta-label">Логин(email)</div>
                            <div class="meta-label">Имя</div>
                            <div class="meta-label">Фамилия</div>
                            <div class="meta-label">Дата рождения</div>
                            <div class="meta-label">Кредитная карта</div>
                        </div>
                        <div class="value-row">
                            <div class="meta-value">${login}</div>
                            <div class="meta-value">${name}</div>
                            <div class="meta-value">${surname}</div>
                            <div class="meta-value">${birth_date}</div>
                            <div class="meta-value">${credit_card}</div>
                        </div>
                        <button class="btn-action change-meta" id="change-user-meta">Редактировать</button>
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
                    <input type="hidden" name="command" value="logout" />
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
