<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<div class="games-wrap row">
    <div class="games-menu-wrap col-lg-3">
        <div class="games-menu colored-block">
            <div class="matches-list matches-menu-item">
                <div class="matches-title">
                    <a href="javascript://" id="matches-title"><fmt:message key="common.bets.matches"/></a> <span
                        class="glyphicon glyphicon-chevron-down dropdown" id="dropdown" style="color: #ffa71b"
                        aria-hidden="true"></span>
                </div>
                <input type="hidden" id="prev-confederation" value="${param.confederation}"/>
                <div class="league-list" id="league-list" style="display: none">
                    <div class="btn-group">
                        <c:forEach var="item" items="${confederationList}">
                            <label><input type="radio" name="league" value="${item}"/>${item}</label>
                        </c:forEach>
                    </div>
                </div>
            </div>
            <div class="results matches-menu-item"><a
                    href="${pageContext.servletContext.contextPath}/controller?command=show_match_results_page"
                    id="match-results"><fmt:message key="common.bets.results"/></a>
            </div>
        </div>
    </div>
    <div class="games-table-wrap col-lg-9">
        <div class="colored-block" style="text-align: center">
            <p class="error-label show-message"
               <c:if test="${!btg:contains(errors,'makeBetError' )}">style="display:none;"</c:if>>
                <fmt:message key="common.bets.make_bet.error"/>
            </p>
        </div>
        <div class="colored-block bet-not-accepted" style="text-align: center">
            <div class="bet-not-accepted-title"><fmt:message key="common.match.bet_message"/></div>
        </div>
        <div class="coupon colored-block" id="coupon">
            <div class="coupon-title"><fmt:message key="common.bets.your_bets"/></div>
            <div class="user-coeff">
                <div class="btn-group">
                    <button id="make-bet-btn"><fmt:message key="common.bets.make_bet"/></button>
                    <button id="clean-bets-btn"><fmt:message key="common.btn.cancel"/></button>
                </div>
            </div>
        </div>
        <div class="games-table colored-block">
            <table id="games" class="table table-bordered matches-table" cellspacing="0" width="100%">
                <thead>
                <tr>
                    <th>№</th>
                    <th><fmt:message key="common.bets.event"/></th>
                    <th><fmt:message key="common.bets.date"/></th>
                    <th class="active-th">1</th>
                    <th class="active-th">X</th>
                    <th class="active-th">2</th>
                    <th class="active-th">1X</th>
                    <th class="active-th">12</th>
                    <th class="active-th">X2</th>
                    <th class="active-th">L</th>
                    <th>T</th>
                    <th class="active-th">M</th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
            <p class="show-message" id="no-matches" style="display:none;">
                <fmt:message key="common.bets.no_matches"/>
            </p>
            <p class="error-label" id="matches-error" style="display:none;">
                <fmt:message key="common.error.server_error"/>
            </p>
        </div>
        <div class="row search-results-block colored-block" style="display:none;">
            <div class="column-12 results-filter clearfix">
                <div class="col-lg-4">
                    <p><fmt:message key="common.bets.results_for"/></p>
                    <div class="select-results-date input-group date" id='select-results-date'>
                        <input type="text" class="form-control"/>
                        <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span>
                                </span>
                    </div>
                </div>
                <div class="col-lg-4">
                    <p><fmt:message key="common.bets.confederation"/></p>
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
                    <th><fmt:message key="common.bets.date"/></th>
                    <th><fmt:message key="common.bets.event"/></th>
                    <th><fmt:message key="common.bets.score"/></th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
            <p id="no-results" style="display:none;">
                <fmt:message key="common.bets.results.no_results"/>
            </p>
            <p class="error-label" id="results-error" style="display:none;">
                <fmt:message key="common.error.server_error"/>
            </p>
        </div>
    </div>
</div>
<div class="popup-wrap" id="make-bet-popup" <c:if test="${(!btg:contains(errors,'betSummBiggerThanMaxBetError'))&&
(!btg:contains(errors,'notEnoughMoneyError'))&&
(!btg:contains(errors,'summNotPositiveError'))}">style="display:none;"</c:if>>
    <div class="popup-holder make-bet-holder" id="make-bet" <c:if test="${(!btg:contains(errors,'betSummBiggerThanMaxBetError'))&&
(!btg:contains(errors,'notEnoughMoneyError'))&&
(!btg:contains(errors,'summNotPositiveError'))}">style="display:none;"</c:if>>
        <form class="make-bet-form clearfix" method="POST" action="${pageContext.servletContext.contextPath}/controller"
              onsubmit="return validateMakeBetForm()">
            <input type="hidden" name="command" value="make_bet"/>
            <p class="error-label show-message" id="too-big-summ"
               <c:if test="${!btg:contains(errors,'betSummBiggerThanMaxBetError')}">style="display:none;"</c:if>>
                <fmt:message key="common.bets.make_bet.bigger_than_max_bet"/>
            </p>
            <p class="error-label show-message"
               <c:if test="${!btg:contains(errors,'notEnoughMoneyError' )}">style="display:none;"</c:if>>
                <fmt:message key="common.bets.make_bet.balance_error"/>
            </p>
            <p class="error-label show-message" id="invalid-summ"
               <c:if test="${!btg:contains(errors,'summNotPositiveError' )}">style="display:none;"</c:if>>
                <fmt:message key="common.bets.make_bet.sum_not_positive"/>
            </p>
            <table id="make-bet-table">
                <thead>
                <tr>
                    <th><fmt:message key="common.bets.event"/></th>
                    <th><fmt:message key="common.bets.date"/></th>
                    <th><fmt:message key="common.bets.bet_type"/></th>
                    <th><fmt:message key="common.bets.coefficient"/></th>
                    <th><fmt:message key="common.bets.summ"/>(BYN)</th>
                    <th><fmt:message key="common.bets.max_bet"/>(BYN)</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>

                <c:choose>
                    <c:when test="${(btg:contains(errors,'betSummBiggerThanMaxBetError'))||
                     (btg:contains(errors,'notEnoughMoneyError'))||
                     (btg:contains(errors,'summNotPositiveError'))}">
                        <c:forEach var="event" varStatus="loop" items="${paramValues.event}">
                            <tr>
                                <input type="hidden" name = "matchId" readOnly="readOnly" value="${paramValues.matchId[loop.index]}"/>
                                <input type="hidden" name="betType" value=""/>
                                <td><input type="text" name="event" value="${event}"/></td>
                                <td><input class="make-bet-input" type="text" name="date"
                                           value="${paramValues.date[loop.index]}"/></td>
                                <td><input class="make-bet-input" type="text" name="type"
                                           value="${paramValues.type[loop.index]}"/></td>
                                <td><input class="make-bet-input" type="text" name="betCoeff"
                                           value="${paramValues.betCoeff[loop.index]}"/></td>
                                <td><input class="make-bet-input" type="text" name="money"
                                           value="${paramValues.money[loop.index]}"/></td>
                                <td><input class="make-bet-input" type="text" name="maxBet"
                                           value="${paramValues.maxBet[loop.index]}"/></td>
                                <td>
                                    <button class="remove-row-btn"><span class="glyphicon glyphicon-remove"
                                                                         aria-hidden="true"></span></button>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr></tr>
                    </c:otherwise>
                </c:choose>


                </tbody>
            </table>
            <div class="btn-group">
                <input type="button" value='<fmt:message key="common.bets.make_bet"/>' id="make-bet-submit">
                <input class="make-bet-close" type="button" value='<fmt:message key="common.btn.cancel"/>'>
            </div>
        </form>
        <div class="popup-close make-bet-close"><a href="">x</a></div>
    </div>
</div>
</div>
