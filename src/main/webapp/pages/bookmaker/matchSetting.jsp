<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<div class="games-wrap row">
    <div class="games-menu-wrap col-lg-3">
        <div class="games-menu colored-block">
            <div class="matches-list">
                <div class="matches-title">
                    <a href="javascript://" id="matches-title"><fmt:message key="common.bets.matches"/></a> <span
                        class="glyphicon glyphicon-chevron-down dropdown" style="color: #ffa71b"
                        aria-hidden="true"></span>
                </div>
                <input type="hidden" id="prev-confederacy" value="${param.confederacy}"/>
                <div class="league-list" id="league-list" style="display: none">
                    <div class="btn-group">
                        <c:forEach var="item" items="${confederationList}">
                            <label><input type="radio" name="league" value="${item}"/>${item}</label>
                        </c:forEach>
                    </div>
                </div>
            </div>
            <div class="results"><a href="${pageContext.servletContext.contextPath}/controller?command=show_match_results_page"
                                    id="match-results"><fmt:message key="common.bets.results"/></a>
            </div>
        </div>
    </div>
    <div class="games-table-wrap col-lg-9">
        <div class="colored-block ">
            <p class="error-label show-message"
               <c:if test="${!btg:contains(errors,'createMatchError' )}">style="display:none;"</c:if>>
                <fmt:message key="bookmaker.match.create.error"/>
            </p>
            <p class="error-label show-message"
               <c:if test="${!btg:contains(errors,'editMatchError' )}">style="display:none;"</c:if>>
                <fmt:message key="bookmaker.match.edit.error"/>
            </p>
            <p class="error-label show-message"
               <c:if test="${!btg:contains(errors,'setScoreError' )}">style="display:none;"</c:if>>
                <fmt:message key="bookmaker.match.set_score.error"/>
            </p>
        </div>
        <div class="create-block colored-block">
            <button><fmt:message key="bookmaker.bets.create"/></button>
        </div>
        <div class="games-table colored-block">
            <table id="games" class="table table-bordered matches-table" cellspacing="0" width="100%">
                <thead>
                <tr>
                    <th>№</th>
                    <th><fmt:message key="common.bets.event"/></th>
                    <th><fmt:message key="common.bets.date"/></th>
                    <th>1</th>
                    <th>X</th>
                    <th>2</th>
                    <th>1X</th>
                    <th>12</th>
                    <th>X2</th>
                    <th>L</th>
                    <th>T</th>
                    <th>M</th>
                    <th class="btn-ctrl-th"><fmt:message key="bookmaker.match.btn.edit"/></th>
                    <th class="btn-ctrl-th"><fmt:message key="common.bets.score"/></th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
            <p id="no-matches" style="display:none;">
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
                    <p><fmt:message key="common.bets.confederacy"/></p>
                    <div class="common-select">
                        <select id="results-confederations" class="dark-select">
                            <c:forEach var="item" items="${confederationList}">
                                <option value="${item}">${item}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="col-lg-4">
                    <input class="input-btn show-results-btn" type="button" value="<fmt:message key="common.btn.show"/>"
                           id="show-results">
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

<div class="popup-wrap" id="edit-games-popup"
     <c:if test="${!btg:contains(errors,'invalidEditParams')}">style="display: none"</c:if>>
    <div class="popup-holder edit-games-holder" id="edit-games"
         <c:if test="${!btg:contains(errors,'invalidEditParams')}">style="display: none"</c:if>>
        <form class="edit-games-form clearfix" method="POST"
              action="${pageContext.servletContext.contextPath}/controller"
              onsubmit="return validateEditMatchForm()">
            <input type="hidden" name="command" value="edit_match"/>
            <input type="hidden" name="matchId" id="edit-match-id" value=""/>
            <input type="hidden" name="firstTeam" id="edit-first-team" value=""/>
            <input type="hidden" name="secondTeam" id="edit-second-team" value=""/>
            <input type="hidden" name="confederacy" id="edit-confederacy" value=""/>
            <div class="edit-title">Edit</div>
            <p class="error-label" id="edit-same-team"
               <c:if test="${(!btg:contains(errors,'invalidEditParams'))&&
               (!btg:contains(errors,'sameTeamError' ))}">style="display:none;"</c:if>>
                <fmt:message key="bookmaker.match.create.same_team_error"/>
            </p>
            <p class="error-label" id="edit-invalid-date"
               <c:if test="${(!btg:contains(errors,'invalidEditParams'))&&
               (!btg:contains(errors,'dateBeforeError') )}">style="display:none;"</c:if>>
                <fmt:message key="bookmaker.match.create.date_before_error"/>
            </p>
            <p class="error-label" id="edit-invalid-max-bet"
               <c:if test="${(!btg:contains(errors,'invalidEditParams'))&&
               (!btg:contains(errors,'invalidMaxBetError' ))}">style="display:none;"</c:if>>
                <fmt:message key="bookmaker.match.create.max_bet_error"/>
            </p>
            <p class="error-label" id="edit-invalid-coeff"
               <c:if test="${(!btg:contains(errors,'invalidEditParams'))&&
               (!btg:contains(errors,'invalidCoeffError' ))}">style="display:none;"</c:if>>
                <fmt:message key="bookmaker.match.create.coeff_error"/>
            </p>
            <div class="table-responsive">
                <div class="edit-match-info">
                    <label for="first-team-edit"><fmt:message key="bookmaker.bets.first_team"/></label>
                    <select id="first-team-edit" class="dark-select">
                        <c:forEach var="item" items="${teamList}">
                            <option value="${item}">${item}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="edit-match-info">
                    <label for="second-team-edit"><fmt:message key="bookmaker.bets.second_team"/></label>
                    <select id="second-team-edit" class="dark-select">
                        <c:forEach var="item" items="${teamList}">
                            <option value="${item}">${item}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="edit-match-info">
                    <label for="confederacy-edit"><fmt:message key="common.bets.confederacy"/></label>
                    <select id="confederacy-edit" class="dark-select">
                        <c:forEach var="item" items="${confederationList}">
                            <option value="${item}">${item}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="edit-match-info clearfix">
                    <label for="create-match-date"><fmt:message key="common.bets.date"/></label>
                    <div class="input-group date date-input" id='edit-match-date'>
                        <input type="text" class="form-control" name="date" id='create-match-date-input'/>
                        <span class="input-group-addon">
								<span class="glyphicon glyphicon-calendar"></span>
							</span>
                    </div>
                </div>
                <table id="edit-games-table">
                    <caption><fmt:message key="bookmaker.bets.coefficiens"/></caption>
                    <thead>
                    <tr>
                        <th>1</th>
                        <th>X</th>
                        <th>2</th>
                        <th>1X</th>
                        <th>12</th>
                        <th>X2</th>
                        <th>L</th>
                        <th>T</th>
                        <th>M</th>
                        <th><fmt:message key="common.bets.max_bet"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td><input class="coeff-input" type="text" name="FW" value=""></td>
                        <td><input class="coeff-input" type="text" name="X" value=""></td>
                        <td><input class="coeff-input" type="text" name="SW" value=""></td>
                        <td><input class="coeff-input" type="text" name="FWX" value=""></td>
                        <td><input class="coeff-input" type="text" name="FS" value=""></td>
                        <td><input class="coeff-input" type="text" name="XSW" value=""></td>
                        <td><input class="coeff-input" type="text" name="TL" value=""></td>
                        <td><input class="coeff-input" type="text" name="T" value=""></td>
                        <td><input class="coeff-input" type="text" name="TM" value=""></td>
                        <td><input class="coeff-input" type="text" id="max-bet-edit" name="maxBet" value=""></td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div class="btn-group">
                <input type="button" class="save-edit-game" value='<fmt:message key="common.btn.save"/>'>
                <input class="edit-games-close" type="button" value='<fmt:message key="common.btn.cancel"/>'>
            </div>
        </form>
        <div class="popup-close edit-games-close"><a href="">x</a></div>
    </div>
</div>
<div class="popup-wrap" id="set-score-popup"
     <c:if test="${(!btg:contains(errors,'scoreNotPositiveError'))||(!btg:contains(errors,'setScoreDateError'))}">style="display: none"</c:if>>
    <div class="popup-holder set-score-holder" id="set-score"
         <c:if test="${(!btg:contains(errors,'scoreNotPositiveError'))||(!btg:contains(errors,'setScoreDateError'))}">style="display: none"</c:if>>
        <form class="set-score-form clearfix" method="POST"
              action="${pageContext.servletContext.contextPath}/controller"
              onsubmit="return validateSetScoreForm()">
            <input type="hidden" name="command" value="set_score"/>
            <input type="hidden" name="matchId" id="set-score-match-id" value=""/>
            <input type="hidden" name="date" id="set-score-match-date" value=""/>
            <p class="error-label" id="invalid-score"
               <c:if test="${!btg:contains(errors,'scoreNotPositiveError' )}">style="display:none;"</c:if>>
                <fmt:message key="bookmaker.match.set_score.not_positive_error"/>
            </p>
            <p class="error-label"
               <c:if test="${!btg:contains(errors,'setScoreDateError' )}">style="display:none;"</c:if>>
                <fmt:message key="bookmaker.match.set_score.date.error"/>
            </p>
            <table id="set-score-table">
                <caption><fmt:message key="bookmaker.bets.set_score"/></caption>
                <tr>
                    <td id="first-team"></td>
                    <td>
                        <input class="score-input" id="first-team-score" type="text" name="firstTeamScore" value=""/>
                    </td>
                </tr>
                <tr>
                    <td id="second-team"></td>
                    <td>
                        <input class="score-input" id="second-team-score" type="text" name="secondTeamScore" value=""/>
                    </td>
                </tr>
            </table>
            <div class="btn-group">
                <input type="submit" class="save-set-score" value='<fmt:message key="common.btn.save"/>'>
                <input class="set-score-close" type="button" value='<fmt:message key="common.btn.cancel"/>'>
            </div>
        </form>
        <div class="popup-close set-score-close"><a href="">x</a></div>
    </div>
</div>
<div class="popup-wrap" id="create-game-popup"
     <c:if test="${!btg:contains(errors,'invalidCreateParams')}">style="display: none"</c:if>>
    <div class="popup-holder create-game-holder" id="create-game"
         <c:if test="${!btg:contains(errors,'invalidCreateParams')}">style="display: none"</c:if>>
        <form class="create-game-form clearfix" method="POST"
              action="${pageContext.servletContext.contextPath}/controller"
              onsubmit="return validateCreateMatchForm()">
            <input type="hidden" name="command" value="create_match"/>
            <input type="hidden" name="firstTeam" id="create-first-team" value=""/>
            <input type="hidden" name="secondTeam" id="create-second-team" value=""/>
            <input type="hidden" name="confederacy" id="create-confederacy" value=""/>
            <div class="create-title">Create</div>
            <p class="error-label" id="create-same-team"
               <c:if test="${(!btg:contains(errors,'invalidCreateParams'))&&
               (!btg:contains(errors,'sameTeamError' ))}">style="display:none;"</c:if>>
                <fmt:message key="bookmaker.match.create.same_team_error"/>
            </p>
            <p class="error-label" id="create-invalid-date"
               <c:if test="${(!btg:contains(errors,'invalidCreateParams'))&&
               (!btg:contains(errors,'dateBeforeError'))}">style="display:none;"</c:if>>
                <fmt:message key="bookmaker.match.create.date_before_error"/>
            </p>
            <p class="error-label" id="create-invalid-max-bet"
               <c:if test="${(!btg:contains(errors,'invalidCreateParams'))&&
               (!btg:contains(errors,'invalidMaxBetError'))}">style="display:none;"</c:if>>
                <fmt:message key="bookmaker.match.create.max_bet_error"/>
            </p>
            <p class="error-label" id="create-invalid-coeff"
               <c:if test="${(!btg:contains(errors,'invalidCreateParams'))&&
               (!btg:contains(errors,'invalidCoeffError'))}">style="display:none;"</c:if>>
                <fmt:message key="bookmaker.match.create.coeff_error"/>
            </p>
            <div class="table-responsive">
                <div class="edit-match-info">
                    <label for="first-team-create"><fmt:message key="bookmaker.bets.first_team"/></label>
                    <select id="first-team-create" class="dark-select">
                        <c:forEach var="item" items="${teamList}">
                            <option value="${item}">${item}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="edit-match-info">
                    <label for="second-team-create"><fmt:message key="bookmaker.bets.second_team"/></label>
                    <select id="second-team-create" class="dark-select">
                        <c:forEach var="item" items="${teamList}">
                            <option value="${item}">${item}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="edit-match-info">
                    <label for="confederacy-create"><fmt:message key="common.bets.confederacy"/></label>
                    <select id="confederacy-create" class="dark-select">
                        <c:forEach var="item" items="${confederationList}">
                            <option value="${item}">${item}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="edit-match-info clearfix">
                    <label for="create-match-date"><fmt:message key="common.bets.date"/></label>
                    <div class="input-group date date-input" id='create-match-date'>
                        <input type="text" class="form-control" name="date"/>
                        <span class="input-group-addon">
								<span class="glyphicon glyphicon-calendar"></span>
							</span>
                    </div>
                </div>
                <table id="create-game-table">
                    <caption><fmt:message key="bookmaker.bets.coefficiens"/></caption>
                    <thead>
                    <tr>
                        <th>1</th>
                        <th>X</th>
                        <th>2</th>
                        <th>1X</th>
                        <th>12</th>
                        <th>X2</th>
                        <th>L</th>
                        <th>T</th>
                        <th>M</th>
                        <th><fmt:message key="common.bets.max_bet"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td><input class="coeff-input" type="text" name="FW" value=""></td>
                        <td><input class="coeff-input" type="text" name="X" value=""></td>
                        <td><input class="coeff-input" type="text" name="SW" value=""></td>
                        <td><input class="coeff-input" type="text" name="FWX" value=""></td>
                        <td><input class="coeff-input" type="text" name="FS" value=""></td>
                        <td><input class="coeff-input" type="text" name="XSW" value=""></td>
                        <td><input class="coeff-input" type="text" name="TL" value=""></td>
                        <td><input class="coeff-input" type="text" name="T" value=""></td>
                        <td><input class="coeff-input" type="text" name="TM" value=""></td>
                        <td><input class="coeff-input" type="text" id="max-bet-create" name="maxBet" value=""></td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div class="btn-group">
                <input type="button" class="save-create-game" value='<fmt:message key="common.btn.save"/>'>
                <input class="create-game-close" type="button" value='<fmt:message key="common.btn.cancel"/>'>
            </div>
        </form>
        <div class="popup-close create-game-close"><a href="">x</a></div>
    </div>
</div>
