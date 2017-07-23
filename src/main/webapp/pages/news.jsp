<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="../css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="../css/bootstrap-datetimepicker.css">
    <link rel="stylesheet" type="text/css" href="../css/bets-style.css">
    <link rel="stylesheet" href="../css/font-awesome.css">
    <link rel="stylesheet" href="../css/pagination.css"/>
    <link href="https://fonts.googleapis.com/css?family=Fira+Mono:400,500&amp;subset=cyrillic" rel="stylesheet">

    <script type="text/javascript" src="../js/jquery-3.2.1.js"></script>
    <script type="text/javascript" src="../js/bootstrap.js"></script>
    <script type="text/javascript" src="../js/pagination.js"></script>
    <script type="text/javascript" src="../js/moment.js"></script>
    <script type="text/javascript" src="../js/bootstrap-datetimepicker.js"></script>
    <script type="text/javascript" src="../js/index.js"></script>
    <script type="text/javascript" src="../js/news.js"></script>

</head>
<body>
<div class="full-container">
    <header class="header-wrap row clearfix">

        <div class="logo-wrap colored-block col-lg-3">
            <a href="${pageContext.servletContext.contextPath}/pages/main.jsp"><img class="logo-img"
                                                                                    src="../images/logo-1.png"
                                                                                    alt="bets"></a>
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
                    <a href="${pageContext.request.contextPath}/controller?command=show_profile" class="main-link">Профиль</a>
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
        <article class="row">
            <div class="news colored-block">
                <div class="select-news clearfix">
                    <div class="select-news-title"> Показать новости за</div>
                    <div class="select-news-date input-group date" id='select-news-date'>
                        <input type="text" class="form-control"/>
                        <span class="input-group-addon">
								<span class="glyphicon glyphicon-calendar"></span>
							</span>
                    </div>
                    <input type="button" id="select-news-btn" class="select-news-btn" value="Показать">
                </div>
                <div class="news-wrap" id="news-wrap">

                </div>
                <div class="news-pagination" id="pagination">
                </div>
            </div>
        </article>
    </section>

    <footer class="row ">
        <section class="footer-coloms clearfix">
            <div class="bets-col">
                <span class="col-title">Ставки</span>
                <ul>
                    <li>
                        <a href="javascript://">Правила</a>
                    </li>
                    <li>
                        <a href="javascript://">События</a>
                    </li>
                    <li>
                        <a href="javascript://">Результаты</a>
                    </li>
                </ul>
            </div>

            <div class="help-col">
                <span class="col-title">Помощь</span>
                <ul>
                    <li>
                        <a href="javascript://">FAQ</a>
                    </li>
                </ul>
            </div>
            <div class="comp-col">
                <span class="col-title">BETS</span>
                <ul>
                    <li>
                        <a href="javascript://">О нас</a>
                    </li>
                    <li>
                        <a href="javascript://">Правила сайта</a>
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
            <form class="logout-form clearfix" id="logout" method="GET" action="controller">
                <h3>Вы действительно хотите выйти?</h3>
                <div class="btn-group">
                    <input type="hidden" name="command" value="logout"/>
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
