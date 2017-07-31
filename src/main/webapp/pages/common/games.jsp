<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="../css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.13/css/dataTables.bootstrap.min.css">
    <link href="https://fonts.googleapis.com/css?family=Fira+Mono:400,500&amp;subset=cyrillic" rel="stylesheet">

    <link rel="stylesheet" type="text/css" href="../css/style.css">
    <script type="text/javascript" src="../js/index.js"></script>
    <script type="text/javascript" src="../js/jquery-3.2.1.js"></script>
    <script type="text/javascript" src="https://cdn.datatables.net/1.10.13/js/jquery.dataTables.min.js"></script>
    <script type="text/javascript" src="https://cdn.datatables.net/1.10.13/js/dataTables.bootstrap.min.js"></script>
    <script type="text/javascript" src="../js/games.js"></script>
    <script type="text/javascript" src="../js/bootstrap.js"></script>
</head>
<body>
<div class="full-container">
    <header class="header-wrap row clearfix">

        <div class="logo-wrap colored-block col-lg-3">
            <a href="index.html"><img class="logo-img" src="../images/logo-1.png" alt="bets"></a>
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
                <a href="javascript:showLogoutPopup()" class="btn-enter">Выход</a>
            </div>
        </div>
    </header>

    <section class="content clearfix">
        <div class="games-wrap row">
            <div class="games-menu-wrap col-lg-3">
                <div class="games-menu colored-block">
                    <div class="matches-list">
                        <div class="matches-title">
                            <a href="javascript://" id="matches-title">Mатчи <span
                                    class="glyphicon glyphicon-chevron-down" style="color: #ffa71b"
                                    aria-hidden="true"></span></a>
                        </div>
                        <ul id="league-list" style="display: none">

                        </ul>
                    </div>
                    <div class="results"><a href="javascript://">Результаты</a></div>
                </div>
            </div>
            <div class="games-table-wrap col-lg-9">
                <div class="row search-results-block colored-block" style="display:none;">
                    <div class="column-12 results-filter clearfix">
                        <div class="col-lg-4">
                            <p>Результаты за</p>
                            <div class="select-results-date input-group date" id='select-results-date'>
                                <input type="text" class="form-control"/>
                                <span class="input-group-addon">
										<span class="glyphicon glyphicon-calendar"></span>
									</span>
                            </div>
                        </div>
                        <div class="col-lg-4">
                            <p>Чемпионат</p>
                            <div class="common-select">
                                <select id="results-chemp" class="dark-select">
                                </select>
                            </div>
                        </div>
                        <div class="col-lg-4">
                            <input class="input-btn show-results-btn" type="button" value="Show">
                        </div>
                    </div>
                </div>
                <div class="games-table colored-block" style="display:none;">
                    <div class="league-title" id="game-league-title"></div>
                    <table id="games" class="table table-bordered" cellspacing="0" width="100%">
                        <thead>
                        <tr>
                            <th>№</th>
                            <th>Date</th>
                            <th>Event</th>
                            <th>1</th>
                            <th>X</th>
                            <th>2</th>
                            <th>1X</th>
                            <th>12</th>
                            <th>X2</th>
                            <th>L</th>
                            <th>T</th>
                            <th>M</th>
                        </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
                <div class="games-table results-table colored-block" style="display:none;">
                    <div class="league-title" id="results-league-title"></div>
                    <table id="results" class="table table-bordered" cellspacing="0" width="100%">
                        <thead>
                        <tr>
                            <th>№</th>
                            <th>Date</th>
                            <th>Event</th>
                            <th>Score</th>
                        </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
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
