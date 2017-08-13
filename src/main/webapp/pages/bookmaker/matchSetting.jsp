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
                <div class="league-list" id="league-list" style="display: none">
                    <div class="btn-group">
                        <c:forEach var="item" items="${confederationList}">
                            <label><input type="radio" name="league" value="${item}"/>${item}</label>
                        </c:forEach>
                    </div>
                </div>
            </div>
            <div class="results"><a href="javascript://" id="match-results"><fmt:message key="common.bets.results"/></a></div>
        </div>
    </div>
    <div class="games-table-wrap col-lg-9">
        <div class="create-block colored-block">
            <button><fmt:message key="bookmaker.bets.create"/></button>
        </div>
        <div class="games-table colored-block" style="display:none;">
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
                    <th class="btn-ctrl-th"><fmt:message key="common.btn.edit"/></th>
                    <th class="btn-ctrl-th"><fmt:message key="bookmaker.bets.score"/></th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
        <div class="row search-results-block colored-block" style="display:none;">
            <div class="column-12 results-filter clearfix">
                <div class="col-lg-4">
                    <p><fmt:message key="bookmaker.bets.results_for"/></p>
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
                    <input class="input-btn show-results-btn" type="button" value="<fmt:message key="common.btn.show"/>" id="show-results">
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
                    <th><fmt:message key="bookmaker.bets.score"/></th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
    </div>
</div>

<div class="popup-wrap" id="edit-games-popup">
    <div class="popup-holder edit-games-holder" id="edit-games" style="display: none">
        <form class="edit-games-form clearfix" method="POST" action="${pageContext.servletContext.contextPath}/controller">
            <input type="hidden" name="command" value="edit_match" />
            <input type="hidden" name="match_id" id="edit-match-id" value=""/>
            <input type="hidden" name="first_team" id="edit-first-team" value=""/>
            <input type="hidden" name="second_team" id="edit-second-team" value=""/>
            <input type="hidden" name="confederacy" id="edit-confederacy" value=""/>
            <div class="table-responsive">
                <div class="edit-match-info">
                    <label for="first-team-select"><fmt:message key="bookmaker.bets.first_team"/></label>
                    <select id="first-team-select" class="dark-select">
                        <c:forEach var="item" items="${teamList}">
                            <option value="${item}">${item}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="edit-match-info">
                    <label for="second-team-select"><fmt:message key="bookmaker.bets.second_team"/></label>
                    <select id="second-team-select" class="dark-select">
                        <c:forEach var="item" items="${teamList}">
                            <option value="${item}">${item}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="edit-match-info">
                    <label for="confederacy-select"><fmt:message key="common.bets.confederacy"/></label>
                    <select id="confederacy-select" class="dark-select">
                        <c:forEach var="item" items="${confederationList}">
                            <option value="${item}">${item}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="edit-match-info clearfix">
                    <label for="create-match-date"><fmt:message key="common.bets.date"/></label>
                    <div class="input-group date date-input" id='edit-match-date'>
                        <input type="text" class="form-control" name="match_date" id='create-match-date-input'/>
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
                        <td><input class="coeff-input" type="text" name="max_bet" value=""></td>
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
<div class="popup-wrap" id="set-score-popup">
    <div class="popup-holder set-score-holder" id="set-score" style="display: none">
        <form class="set-score-form clearfix" method="POST" action="${pageContext.servletContext.contextPath}/controller">
            <input type="hidden" name="command" value="set_score" />
            <input type="hidden" name="match_id" id="set-score-match-id" value=""/>
            <table id="set-score-table">
                <caption><fmt:message key="bookmaker.bets.set_score"/></caption>
                <tr>
                    <td id="first-team"></td>
                    <td><input class="score-input" type="text" name="first_team_score" value=""/></td>
                </tr>
                <tr>
                    <td id="second-team"></td>
                    <td><input class="score-input" type="text" name="second_team_score" value=""/></td>
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
<div class="popup-wrap" id="create-game-popup">
    <div class="popup-holder create-game-holder" id="create-game" style="display: none">
        <form class="create-game-form clearfix"  method="POST" action="${pageContext.servletContext.contextPath}/controller">
            <input type="hidden" name="command" value="create_match" />
            <input type="hidden" name="first_team" id="create-first-team" value=""/>
            <input type="hidden" name="second_team" id="create-second-team" value=""/>
            <input type="hidden" name="confederacy" id="create-confederacy" value=""/>

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
                        <input type="text" class="form-control" name="match_date"/>
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
                        <td><input class="coeff-input" type="text" name="max_bet" value=""></td>
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
