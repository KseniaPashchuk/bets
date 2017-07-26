<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="../css/bootstrap.css" >
    <link rel="stylesheet" type="text/css" href="../css/bets-style.css">
    <script type="text/javascript" src="../js/index.js"></script>
    <link href="https://fonts.googleapis.com/css?family=Fira+Mono:400,500&amp;subset=cyrillic" rel="stylesheet">
</head>
<body>
<div class="full-container">
    <header class="header-wrap row clearfix">
        <div class="logo-wrap colored-block col-lg-3">
            <a href="${pageContext.servletContext.contextPath}/pages/main.jsp"><img class ="logo-img" src="../images/logo-1.png" alt="bets"></a>
        </div>
        <div class="menu-wrap colored-block ">
            <ul class="main-menu clearfix">
                <li class="item-main"><a href="${pageContext.servletContext.contextPath}/pages/news.jsp" class="main-link">Новости</a>
                </li>
                <li class="item-main"><a href="#" class="main-link">Игры</a>
                </li>
                <li class="item-main">
                    <a href="${pageContext.servletContext.contextPath}/controller?command=show_faq" class="main-link">FAQ</a>
                </li>
                <li class="item-main">
                    <a href="${pageContext.servletContext.contextPath}/controller?command=show_user_profile" class="main-link">Профиль</a>
                </li>
            </ul>
        </div>
        <div class="lang-wrap colored-block ">
            <a href="@" class = "lang">RU</a>

        </div>
        <div class="user-action colored-block col-lg-3">
            <div class="authorization">
                <a href="javascript:showLogoutPopup()" class="btn-enter" >Выход</a>
            </div>
        </div>
    </header>

    <section class = "content clearfix">
        <div class = "info-wrap clearfix">
            <div class="info row">
                <div class = "pic-wrap col-lg-7">
                    <img class ="pic" src="../images/5.jpg" alt="football">
                </div>
                <div class="info-text colored-block col-lg-5">
                    <article class="article-wrap">
                        <h1>Букмекерская контора онлайн</h1>
                        <p>Приветствуем вас на нашем сайте, лучшей букмекерской конторе онлайн. Этот сайт, надежный источник линии официальных мировых спортивных событий, а так же их результатов на которые можно делать <strong>онлайн ставки</strong>. Мы применяем технологии, которые используют надежные букмекеры мира.</p>
                    </article>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="last-news colored-block">
                <div class = "last-news-title">Последние новости</div>
                <div class="news-wrap">
                    <div class="news-post">
                        <div class = "post-pic-wrap col-lg-3">
                            <img class ="post-pic" src="../images/kot.png" alt="football">
                        </div>
                        <div class = "col-lg-9">
                            <div class="post-title"><a href="#">KOT</a></div>
                            <div class="post-meta">
									<span class="fa fa-clock-o"><i class="icon-time"> </i>
										<time class="entry-date published" datetime="2013-01-11T20:22:19+00:00">January 11, 2013</time></span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="payment row  clearfix">
            <div class="payment-wrap colored-block clearfix ">
                <div class="pay-title col-lg-2">Способы оплаты</div>
                <ul class="pay-systems">
                    <li>
                        <a href="javascript://" class="webmoney"></a>
                    </li>
                    <li>
                        <a href="javascript://" class="scrill"></a>
                    </li>
                    <li>
                        <a href="javascript://" class="mastercard"></a>
                    </li>
                    <li>
                        <a href="javascript://" class="visa"></a>
                    </li>

                </ul>
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
            <form action="${pageContext.servletContext.contextPath}/controller" method="GET" class="logout-form clearfix" id="logout">
                <h3>Вы действительно хотите выйти?</h3>
                <div class="btn-group">
                    <input type="hidden" name="command" value="log_out" />
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
