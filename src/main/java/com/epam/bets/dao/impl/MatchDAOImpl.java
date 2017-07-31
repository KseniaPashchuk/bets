package com.epam.bets.dao.impl;

import com.epam.bets.dao.MatchDAO;
import com.epam.bets.entity.Match;
import com.epam.bets.exception.DaoException;
import com.epam.bets.pool.ProxyConnection;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MatchDAOImpl extends MatchDAO {


    private static final String SELECT_MATCHES_BY_CONFEDERACY = "SELECT match_id, date_time, confederacy," +
            " first_team.team_name AS first_team, second_team.team_name AS second_team, total_value, max_bet_value " +
            "FROM (football_match  JOIN football_team AS first_team ON football_match.first_team_id = first_team.team_id " +
            "JOIN football_team AS second_team ON football_match.second_team_id = second_team.team_id " +
            "JOIN match_bet_info ON match_id = match_bet_info.football_match_id) " +
            "WHERE finished = 0 AND football_match.confederacy=?";

    private static final String SELECTS_RESULTS_BY_DATE = "SELECT match_id, date_time, confederacy, " +
            "first_team.team_name AS first_team, second_team.team_name AS second_team, first_team_score, second_team_score " +
            "FROM (football_match  JOIN football_team AS first_team ON football_match.first_team_id = first_team.team_id " +
            "JOIN football_team AS second_team ON football_match.second_team_id = second_team.team_id) "+
            "WHERE finished = 1 AND  date_time=?";

    private static final String SELECTS_RESULTS_BY_DATE_AND_CONFEDERACY = "SELECT match_id, date_time, confederacy, " +
            "first_team.team_name AS first_team, second_team.team_name AS second_team, first_team_score, second_team_score " +
            "FROM (football_match  JOIN football_team AS first_team ON football_match.first_team_id = first_team.team_id " +
            "JOIN football_team AS second_team ON football_match.second_team_id = second_team.team_id) "+
            "WHERE finished = 1 AND football_match.confederacy=? AND  date_time=?";

    private static final String SELECTS_CONFEDERATIONS = "SELECT DISTINCT confederacy " +
            "FROM football_match";

    public MatchDAOImpl() {
    }

    public MatchDAOImpl(ProxyConnection connection) {
        super(connection);
    }

    @Override
    public List<Match> findAll() throws DaoException {
        return null;
    }

    @Override
    public List<Match> findMatchesByConfederacy(String confederacy) throws DaoException {
        List<Match> matchList;
        try (PreparedStatement statementNews = connection.prepareStatement(SELECT_MATCHES_BY_CONFEDERACY)) {
            statementNews.setString(1, confederacy);
            ResultSet resultSet = statementNews.executeQuery();
            matchList = buildMatchList(resultSet);
        } catch (SQLException e) {
            throw new DaoException("Can't find news by given date", e);
        }
        return matchList;
    }

    @Override
    public List<String> findAllConfederations() throws DaoException {
        List<String> confederacyList = new ArrayList<>();
        try (PreparedStatement statementNews = connection.prepareStatement(SELECTS_CONFEDERATIONS)) {
            ResultSet resultSet = statementNews.executeQuery();
            while(resultSet.next()){
                confederacyList.add(resultSet.getString(PARAM_NAME_CONFEDERACY));
            }

        } catch (SQLException e) {
            throw new DaoException("Can't find news by given date", e);
        }
        return confederacyList;
    }

    @Override
    public Match findEntityById(int id) throws DaoException {
        return null;
    }

    @Override
    public boolean delete(int id) throws DaoException {
        return false;
    }

    @Override
    public int create(Match entity) throws DaoException {
        return 0;
    }

    @Override
    public boolean update(Match entity) throws DaoException {
        return false;
    }

    @Override
    public List<Match> findResultsByDate(LocalDate date) throws DaoException {
        List<Match> results;
        try (PreparedStatement statementNews = connection.prepareStatement(SELECTS_RESULTS_BY_DATE)) {
            statementNews.setDate(1, java.sql.Date.valueOf(date));
            ResultSet resultSet = statementNews.executeQuery();
            results = buildMatchList(resultSet);
        } catch (SQLException e) {
            throw new DaoException("Can't find match results", e);
        }
        return results;
    }

    @Override
    public List<Match> findResultsByDateAndConfederacy(LocalDate date, String confederacy) throws DaoException {
        List<Match> results;
        try (PreparedStatement statementNews = connection.prepareStatement(SELECTS_RESULTS_BY_DATE_AND_CONFEDERACY)) {
            statementNews.setDate(1, java.sql.Date.valueOf(date));
            statementNews.setString(2, confederacy);
            ResultSet resultSet = statementNews.executeQuery();
            results = buildMatchList(resultSet);
        } catch (SQLException e) {
            throw new DaoException("Can't find match results", e);
        }
        return results;
    }

    private Match buildMatch(ResultSet resultSet) throws SQLException {
        Match match = new Match();
        match.setMatchId(resultSet.getInt(PARAM_NAME_ID));
        match.setFirstTeam(resultSet.getString(PARAM_NAME_FIRST_TEAM));
        match.setSecondTeam(resultSet.getString(PARAM_NAME_SECOND_TEAM));
        match.setConfederacy(resultSet.getString(PARAM_NAME_CONFEDERACY));
        match.setTotal(resultSet.getBigDecimal(PARAM_NAME_TOTAL));
        match.setMaxBet(resultSet.getBigDecimal(PARAM_NAME_MAX_BET));
        return match;
    }

    private List<Match> buildMatchList(ResultSet resultSet) throws SQLException {
        List<Match> matchList = new ArrayList<>();
        while (resultSet.next()) {
            matchList.add(buildMatch(resultSet));
        }
        return matchList;
    }

}
