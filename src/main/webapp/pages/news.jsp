<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="../css/bootstrap.css" >
    <link rel="stylesheet" type="text/css" href="../css/bootstrap-datetimepicker.css" >
    <link rel="stylesheet" type="text/css" href="../css/style.css">
    <link rel="stylesheet" href="../css/font-awesome.css">
    <link href="https://fonts.googleapis.com/css?family=Fira+Mono:400,500&amp;subset=cyrillic" rel="stylesheet">

    <script type="text/javascript" src="../js/jquery-3.2.1.js"></script>
    <script type="text/javascript" src="../js/bootstrap.js"></script>
    <script type="text/javascript" src="../js/moment.js"></script>
    <script type="text/javascript" src="../js/bootstrap-datetimepicker.js"></script>
    <script type="text/javascript" src="../js/index.js"></script>
    <script type="text/javascript" src="../js/news.js"></script>
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
                <a href="javascript:showLogoutPopup()" class="btn-enter">Выход</a>
            </div>
        </div>
    </header>

    <section class = "content clearfix">
        <article class="row">
            <div class="news colored-block">
                <div class="select-news clearfix">
                    <div class = "select-news-title"> Показать новости за</div>
                    <div class="select-news-date input-group date" id='datetimepicker'>
                        <input type="text" class="form-control" />
                        <span class="input-group-addon">
								<span class="glyphicon glyphicon-calendar"></span>
							</span>
                    </div>
                    <input type="button" id="select-news-btn" class="select-news-btn"  value="Показать" onclick="selectNewsByDate()">
                </div>
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
        </article>
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
            <form class="logout-form clearfix" id="logout" method="GET" action="controller">
                <h3>Вы действительно хотите выйти?</h3>
                <div class="btn-group">
                    <input type="hidden" name="command" value="logout" />
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
