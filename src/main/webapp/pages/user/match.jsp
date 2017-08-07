<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<div class="games-wrap row">
    <div class="games-menu-wrap col-lg-3">
        <div class="games-menu colored-block">
            <div class="matches-list">
                <div class="matches-title">
                    <a href="javascript://" id="matches-title">Mатчи</a> <span
                        class="glyphicon glyphicon-chevron-down dropdown" style="color: #ffa71b"
                        aria-hidden="true"></span>
                </div>
                <div class="league-list" id="league-list" style="display: none">
                    <div class="btn-group">
                        <c:forEach var="item" items="${confederationList}">
                            <label><input type="radio" name="league" value="${item}"/>${item}</label>
                        </c:forEach>
                    </div>
                </div>
            </div>
            <div class="results"><a href="javascript://" id="match-results">Результаты</a></div>
        </div>
    </div>
    <div class="games-table-wrap col-lg-9">
        <div class="coupon colored-block" id="coupon">
            <div class="coupon-title">Your bets</div>
            <div class="user-coeff">
                <div class="btn-group">
                    <button id="make-bet-btn">make bet</button>
                    <button id="clean-bets-btn">cancel</button>
                </div>
            </div>
        </div>
        <div class="games-table colored-block" style="display:none;">
            <table id="games" class="table table-bordered matches-table" cellspacing="0" width="100%">
                <thead>
                <tr>
                    <th>№</th>
                    <th>Event</th>
                    <th>Date</th>
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
        <div class="row search-results-block colored-block" style="display:none;">
            <div class="column-12 results-filter clearfix">
                <div class="col-lg-4">
                    <p>Результаты за</p>
                    <div class="select-results-date input-group date" id='select-results-date'>
                        <input type="text" class="form-control"/>
                        <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span>
                                </span>
                    </div>
                </div>
                <div class="col-lg-4">
                    <p>Чемпионат</p>
                    <div class="common-select">
                        <select id="results-confederations" class="dark-select">
                            <c:forEach var="item" items="${confederationList}">
                                <option value="${item}">${item}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="col-lg-4">
                    <input class="input-btn show-results-btn" type="button" value="Show" id="show-results">
                </div>
            </div>
        </div>
        <div class="results-table colored-block" style="display:none;">
            <table id="results" class="table table-bordered results-table" cellspacing="0" width="100%">
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
<div class="popup-wrap" id="make-bet-popup">
    <div class="popup-holder make-bet-holder" id="make-bet" style="display: none">
        <form class="make-bet-form clearfix">
            <table id="make-bet-table">
                <thead>
                <tr>
                    <th>Date</th>
                    <th>Event</th>
                    <th>Bet type</th>
                    <th>Coeff</th>
                    <th>Summ</th>
                    <th>Max summ</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr></tr>
                </tbody>
            </table>
            <div class="btn-group">
                <input type="submit" value='Bet!'>
                <input class="make-bet-close" type="button" value='Cancel'>
            </div>
        </form>
        <div class="popup-close make-bet-close"><a href="">x</a></div>
    </div>
</div>
</div>
