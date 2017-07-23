<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="../css/bootstrap.css" >
    <link rel="stylesheet" type="text/css" href="../css/style.css">
    <script type="text/javascript" src="../js/jquery-3.2.1.js"></script>
    <script type="text/javascript" src="../js/index.js"></script>
    <script type="text/javascript" src="../js/profile.js"></script>
    <link href="https://fonts.googleapis.com/css?family=Fira+Mono:400,500&amp;subset=cyrillic" rel="stylesheet">

</head>
<body>
<div class="full-container">
    <header class="header-wrap row clearfix">

        <div class="logo-wrap colored-block col-lg-3">
            <a href="../index.html"><img class ="logo-img" src="../images/logo-1.png" alt="bets"></a>
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
            <a href="@" class = "lang">RU</a>

        </div>
        <div class="user-action colored-block col-lg-3">
            <div class="authorization">
                <a href="javascript:showLogoutPopup()" class="btn-enter" ">Выход</a>
            </div>
        </div>
    </header>

    <section class = "content clearfix">
        <div class="row user-edit-profile">
            <div class="user-menu-wrap col-lg-3 col-md-3 col-sm-3">
                <div class="user-menu colored-block">
                    <div class="user-menu-item"><a href="profile.html">Профиль</a></div>
                    <div class="user-menu-item"><a href="editProfile.html">Редактировать профиль</a></div>
                    <div class="user-menu-item"><a href="userBets.html">Мои ставки</a></div><!--Выигранные, проигранные, текущие, все-->
                    <div class="user-menu-item"><a href="refillCash.html">Пополнить счет</a></div>
                </div>
            </div>
            <div class="edit-info-wrap col-lg-9 col-md-9 col-sm-9">
                <div class="edit-info colored-block">
                    <form action="" method="POST">
                        <div class="shell-for-input">
                            <label for="edit_email">
                                <div class="edit-label">Логин</div>
                                <input class='edit-input' type="text" id="edit_email" name="edit_email">
                                <div class="edit-input-label" for="edit_email" id="edit_email_label">Логин</div>
                            </label>
                        </div>
                        <div class="shell-for-input">
                            <label for="edit_email">
                                <div class="edit-label">Пароль</div>
                                <input class='edit-input' type="text" id="edit_password" name="edit_password">
                                <div class="edit-input-label" for="edit_password" id="edit_password_label">Пароль</div>
                            </label>
                        </div>
                        <div class="shell-for-input">
                            <label for="edit_email">
                                <div class="edit-label">Подтвердите пароль</div>
                                <input class='edit-input' type="text" id="conf_password" name="conf_password">
                                <div class="edit-input-label" for="conf_password" id="conf_password_label">Подтвердите пароль</div>
                            </label>
                        </div>
                        <div class="shell-for-input">
                            <label for="edit_email">
                                <div class="edit-label">Имя</div>
                                <input class='edit-input' type="text" id="edit_name" name="edit_name">
                            </label>
                        </div>
                        <div class="shell-for-input">
                            <label for="edit_email">
                                <div class="edit-label">Фамилия</div>
                                <input class='edit-input' type="text" id="edit_surname" name="edit_surname">
                            </label>
                        </div>
                        <div class="shell-for-input">
                            <label for="edit_email">
                                <div class="edit-label">Дата рождения</div>
                                <input class='edit-input' type="text" id="edit_surname" name="edit_surname">
                            </label>
                        </div>
                        <div class="shell-for-input clearfix">
                            <div class="edit-label col-lg-3">Кредитные карты</div>
                            <div class="col-lg-7">
                                <div class="input-group credit-card active" id="credit_card_122">
                                    <input class='edit-input form-control' type="text" id="122" name="credit_card" value="11111111" disabled>
                                    <div class="input-group-addon"><input type="button" class="delete-card-btn" id="delete_122" value="delete"></div>
                                </div>
                                <div class="input-group credit-card hidden" id="credit_card_1">
                                    <input class='edit-input form-control' type="text" id="1" name="credit_card" value="222">
                                    <div  class="input-group-addon"><input type="button" class="delete-card-btn" id="delete_1" value="delete">
                                    </div >
                                </div>
                                <div class="input-group credit-card hidden" id="credit_card_2">
                                    <input class='edit-input form-control' type="text" id="2" name="credit_card" value="333">
                                    <div  class="input-group-addon"><input type="button" class="delete-card-btn" id="delete_2" value="delete">
                                    </div >
                                </div>
                            </div>
                            <button class="col-lg-2 add-credit-card" id="add-credit-card-btn"><span class="glyphicon glyphicon-plus"></span></button>
                        </div>
                        <input type="submit" id="edit-profile-btn" value="Сохранить">
                    </form>
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
                <a href="javascript://"><img src="../images/ft_18.png" class="age-limit" ></a>
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
                <div class="btn-group">
                    <input class="button-logout" id="btn-logout" type="button" value='Выйти'>
                    <input class="button-logout" type="button" value='Отмена' onclick="closeLogoutPopup()">
                </div>
            </form>
            <div class="popup-close logout-close"><a href="javascript:closeLogoutPopup()">x</a></div>
        </div>
    </div>
</div>
</body>
</html>
