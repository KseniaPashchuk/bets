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
            <div class="results"><a href="${pageContext.servletContext.contextPath}/controller?command=show_match_results_page"
                                    id="match-results"><fmt:message key="common.bets.results"/></a>
            </div>
            <div class="add-team"><a href="${pageContext.servletContext.contextPath}/pages/admin/addTeam.jsp"
                                     id="add-team"><fmt:message key="bookmaker.bets.add_team"/></a></div>
        </div>
    </div>
    <div class="games-table-wrap col-lg-9">
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
