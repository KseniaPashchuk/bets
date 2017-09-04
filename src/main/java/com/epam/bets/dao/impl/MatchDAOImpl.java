package com.epam.bets.dao.impl;

import com.epam.bets.dao.MatchDAO;
import com.epam.bets.entity.Match;
import com.epam.bets.exception.DaoException;
import com.epam.bets.pool.ProxyConnection;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
/**
 * The class provides {@link MatchDAO} implementation.
 *
 * @author Pashchuk Ksenia
 */
public class MatchDAOImpl extends MatchDAO {
    private static final String SELECT_ALL_MATCHES = "SELECT match_id, date_time, confederation_name," +
            " first_team.team_name AS first_team,  second_team.team_name AS second_team,  total_value, max_bet_value " +
            "FROM (football_match  JOIN football_team AS first_team ON football_match.first_team_id = first_team.team_id " +
            "JOIN football_team AS second_team ON football_match.second_team_id = second_team.team_id " +
            "JOIN confederation ON football_match.confederation_id = confederation.confederation_id " +
            "JOIN match_bet_info ON match_id = match_bet_info.football_match_id) " +
            "WHERE finished = 0";

    private static final String SELECT_MATCHES_BY_CONFEDERACY = "SELECT match_id, date_time, confederation_name," +
            " first_team.team_name AS first_team, second_team.team_name AS second_team, total_value, max_bet_value " +
            "FROM (football_match  JOIN football_team AS first_team ON football_match.first_team_id = first_team.team_id " +
            "JOIN football_team AS second_team ON football_match.second_team_id = second_team.team_id " +
            "JOIN confederation ON football_match.confederation_id = confederation.confederation_id " +
            "JOIN match_bet_info ON football_match.match_id = match_bet_info.football_match_id) " +
            "WHERE finished = 0 AND confederation_name=?";

    private static final String SELECTS_RESULTS_BY_DATE = "SELECT match_id, date_time, confederation_name, " +
            "first_team.team_name AS first_team, second_team.team_name AS second_team, first_team_score, second_team_score " +
            "FROM (football_match  JOIN football_team AS first_team ON football_match.first_team_id = first_team.team_id " +
            "JOIN football_team AS second_team ON football_match.second_team_id = second_team.team_id " +
            "JOIN confederation ON football_match.confederation_id = confederation.confederation_id) " +
            "WHERE finished = 1 AND  date_time BETWEEN ? AND ?";

    private static final String SELECTS_RESULTS_BY_DATE_AND_CONFEDERACY = "SELECT match_id, date_time, confederation_name, " +
            "first_team.team_name AS first_team, second_team.team_name AS second_team, first_team_score, second_team_score " +
            "FROM (football_match  JOIN football_team AS first_team ON football_match.first_team_id = first_team.team_id " +
            "JOIN football_team AS second_team ON football_match.second_team_id = second_team.team_id " +
            "JOIN confederation ON football_match.confederation_id = confederation.confederation_id ) " +
            "WHERE finished = 1 AND confederation_name=? AND  date_time BETWEEN ? AND ?";

    private static final String SELECTS_MATCH_INFO = "SELECT first_team_score, second_team_score, total_value FROM football_match " +
            "JOIN match_bet_info ON match_id = match_bet_info.football_match_id WHERE match_id=?";

    private static final String CREATE_MATCH = "INSERT INTO football_match (match_id, date_time, confederation_id," +
            " first_team_id, second_team_id, finished) VALUES( NULL, ?, " +
            "(SELECT confederation_id FROM confederation WHERE confederation_name=?), " +
            "(SELECT team_id FROM football_team WHERE team_name=?), " +
            "(SELECT team_id FROM football_team WHERE team_name=?), 0)";

    private static final String CREATE_MATCH_INFO = "INSERT INTO match_bet_info (match_info_id, total_value, max_bet_value," +
            " football_match_id) VALUES(NULL, ?, ?, ?)";

    private static final String CREATE_FOOTBALL_TEAM = "INSERT INTO football_team (team_id, team_name, country)" +
            " VALUES(NULL, ?, ?)";

    private static final String CREATE_CONFEDERATION = "INSERT INTO confederation (confederation_id, confederation_name)" +
            " VALUES(NULL, ?)";

    private static final String UPDATE_MATCH = "UPDATE football_match SET first_team_id = (SELECT team_id FROM football_team WHERE team_name=?), " +
            "second_team_id = (SELECT team_id FROM football_team WHERE team_name=?), date_time=?, " +
            "confederation_id =(SELECT confederation_id FROM confederation WHERE confederation_name=?) WHERE match_id=?";

    private static final String UPDATE_MATCH_INFO = "UPDATE match_bet_info SET total_value=?, max_bet_value=? " +
            " WHERE football_match_id=? ";

    private static final String SELECTS_CONFEDERATIONS = "SELECT confederation_name FROM confederation";

    private static final String SELECTS_TEAMS = "SELECT DISTINCT team_name FROM football_team";

    private static final String SET_SCORE = "UPDATE football_match SET finished=1, first_team_score=?," +
            " second_team_score=? WHERE match_id=?";

    public MatchDAOImpl() {
    }

    public MatchDAOImpl(ProxyConnection connection) {
        super(connection);
    }


    /**
     * Takes  {@link List} of all {@link Match}
     *
     * @return taken {@link List} of all {@link Match} object or empty {@link List}
     * @throws DaoException if {@link SQLException} occurred while working with database
     * @see PreparedStatement
     * @see ResultSet
     */
    @Override
    public List<Match> findAllMatches() throws DaoException {
        List<Match> matchList;
        try (PreparedStatement statementMatch = connection.prepareStatement(SELECT_ALL_MATCHES)) {
            ResultSet resultSet = statementMatch.executeQuery();
            matchList = buildMatchList(resultSet);
        } catch (SQLException e) {
            throw new DaoException("Can't find all matches", e);
        }
        return matchList;
    }
    /**
     * Takes  {@link List} of  {@link Match} by confederation
     *
     * @param confederation - results confederation
     * @return taken {@link List} of all {@link Match} object or empty {@link List}
     * @throws DaoException if {@link SQLException} occurred while working with database
     * @see PreparedStatement
     * @see ResultSet
     */
    @Override
    public List<Match> findMatchesByConfederacy(String confederation) throws DaoException {
        List<Match> matchList;
        try (PreparedStatement statementMatch = connection.prepareStatement(SELECT_MATCHES_BY_CONFEDERACY)) {
            statementMatch.setString(1, confederation);
            ResultSet resultSet = statementMatch.executeQuery();
            matchList = buildMatchList(resultSet);
        } catch (SQLException e) {
            throw new DaoException("Can't find matches", e);
        }
        return matchList;
    }

    /**
     * Takes  {@link List} of  all {@link String} confederations
     *
     * @return taken {@link List} of all {@link String} object or empty {@link List}
     * @throws DaoException if {@link SQLException} occurred while working with database
     * @see PreparedStatement
     * @see ResultSet
     */
    @Override
    public List<String> findAllConfederations() throws DaoException {
        List<String> confederacyList = new ArrayList<>();
        try (PreparedStatement statementMatch = connection.prepareStatement(SELECTS_CONFEDERATIONS)) {
            ResultSet resultSet = statementMatch.executeQuery();
            while (resultSet.next()) {
                confederacyList.add(resultSet.getString(PARAM_NAME_CONFEDERATION));
            }

        } catch (SQLException e) {
            throw new DaoException("Can't find all confederations", e);
        }
        return confederacyList;
    }
    /**
     * Takes  {@link List} of  all {@link String} football teams
     *
     * @return taken {@link List} of all {@link String} object or empty {@link List}
     * @throws DaoException if {@link SQLException} occurred while working with database
     * @see PreparedStatement
     * @see ResultSet
     */
    @Override
    public List<String> findAllTeams() throws DaoException {
        List<String> teamList = new ArrayList<>();
        try (PreparedStatement statementMatch = connection.prepareStatement(SELECTS_TEAMS)) {
            ResultSet resultSet = statementMatch.executeQuery();
            while (resultSet.next()) {
                teamList.add(resultSet.getString(PARAM_NAME_TEAM_NAME));
            }
        } catch (SQLException e) {
            throw new DaoException("Can't find all teams", e);
        }
        return teamList;
    }

    /**
     * Provides setting score for match
     *
     * @param matchId - match id
     * @param firstTeamScore - firts team score
     * @param secondTeamScore - second team score
     * @return true if successfully set score
     * @throws DaoException if {@link SQLException} occurred while working with database
     * @see PreparedStatement
     * @see ResultSet
     */
    @Override
    public boolean setScore(int matchId, BigDecimal firstTeamScore, BigDecimal secondTeamScore) throws DaoException {
        try (PreparedStatement statementMatch = connection.prepareStatement(SET_SCORE)) {
            statementMatch.setBigDecimal(1, firstTeamScore);
            statementMatch.setBigDecimal(2, secondTeamScore);
            statementMatch.setInt(3, matchId);
            statementMatch.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DaoException("Can't set score", e);
        }
    }
    /**
     * Takes finished {@link Match} info by match id
     *
     * @return taken {@link Match}
     * @throws DaoException if {@link SQLException} occurred while working with database
     * @see PreparedStatement
     * @see ResultSet
     */
    @Override
    public Match findFinishedMatchInfo(int matchId) throws DaoException {
        Match match = new Match();
        try (PreparedStatement statementMatch = connection.prepareStatement(SELECTS_MATCH_INFO)) {
            statementMatch.setInt(1, matchId);
            ResultSet resultSet = statementMatch.executeQuery();
            if (resultSet.next()) {
                match.setFirstTeamScore(resultSet.getBigDecimal(PARAM_NAME_FIRST_TEAM_SCORE));
                match.setSecondTeamScore(resultSet.getBigDecimal(PARAM_NAME_SECOND_TEAM_SCORE));
                match.setTotal(resultSet.getBigDecimal(PARAM_NAME_TOTAL));
            }
            return match;
        } catch (SQLException e) {
            throw new DaoException("Can't find match score", e);
        }
    }

    /**
     * Creates new {@link Match}
     *
     * @return true if successfully created
     * @throws DaoException if {@link SQLException} occurred while working with database
     * @see PreparedStatement
     * @see ResultSet
     */
    @Override
    public boolean createNewFootballTeam(String team, String country) throws DaoException {
        try (PreparedStatement teamStatement = connection.prepareStatement(CREATE_FOOTBALL_TEAM)) {
            teamStatement.setString(1, team);
            teamStatement.setString(2, country);
            teamStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DaoException("Can't create football team", e);
        }
    }
    /**
     * Creates new confederation
     *
     * @return true if successfully created
     * @throws DaoException if {@link SQLException} occurred while working with database
     * @see PreparedStatement
     * @see ResultSet
     */
    @Override
    public boolean createNewConfederation(String confederation) throws DaoException {
        try (PreparedStatement teamStatement = connection.prepareStatement(CREATE_CONFEDERATION)) {
            teamStatement.setString(1, confederation);
            teamStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DaoException("Can't create confederation", e);
        }
    }
    /**
     * Creates new {@link Match}
     *
     * @return id of created match
     * @throws DaoException if {@link SQLException} occurred while working with database
     * @see PreparedStatement
     * @see ResultSet
     */
    @Override
    public int create(Match entity) throws DaoException {
        try (PreparedStatement statementMatch = connection.prepareStatement(CREATE_MATCH, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement statementInfo = connection.prepareStatement(CREATE_MATCH_INFO)) {
            statementMatch.setTimestamp(1, Timestamp.valueOf(entity.getDate()));
            statementMatch.setString(2, entity.getConfederation());
            statementMatch.setString(3, entity.getFirstTeam());
            statementMatch.setString(4, entity.getSecondTeam());
            statementMatch.executeUpdate();
            ResultSet generatedKey = statementMatch.getGeneratedKeys();
            if (generatedKey.next()) {
                statementInfo.setBigDecimal(1, entity.getTotal());
                statementInfo.setBigDecimal(2, entity.getMaxBet());
                statementInfo.setInt(3, generatedKey.getInt(1));
                statementInfo.executeUpdate();
                return generatedKey.getInt(1);
            }
        } catch (SQLException e) {
            if (e.getErrorCode() == EXISTING_ENTITY_ERROR_CODE) {
                return 0;
            }
            throw new DaoException("Can't create match", e);
        }
        return 0;
    }
    /**
     * Updates {@link Match}
     *
     * @return true if successfully updated
     * @throws DaoException if {@link SQLException} occurred while working with database
     * @see PreparedStatement
     * @see ResultSet
     */
    @Override
    public boolean update(Match entity) throws DaoException {
        try (PreparedStatement statementMatch = connection.prepareStatement(UPDATE_MATCH);
             PreparedStatement statementInfo = connection.prepareStatement(UPDATE_MATCH_INFO)) {
            statementMatch.setString(1, entity.getFirstTeam());
            statementMatch.setString(2, entity.getSecondTeam());
            statementMatch.setTimestamp(3, Timestamp.valueOf(entity.getDate()));
            statementMatch.setString(4, entity.getConfederation());
            statementMatch.setInt(5, entity.getId());
            statementMatch.executeUpdate();
            statementInfo.setBigDecimal(1, entity.getTotal());
            statementInfo.setBigDecimal(2, entity.getMaxBet());
            statementInfo.setInt(3, entity.getId());
            statementInfo.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DaoException("Can't update match", e);
        }
    }

    /**
     * Takes  {@link List} of  {@link Match} results by date
     *
     * @param date - results date
     * @return taken {@link List} of all {@link Match} object or empty {@link List}
     * @throws DaoException if {@link SQLException} occurred while working with database
     * @see PreparedStatement
     * @see ResultSet
     */
    @Override
    public List<Match> findResultsByDate(LocalDate date) throws DaoException {
        List<Match> results;
        try (PreparedStatement statementResults = connection.prepareStatement(SELECTS_RESULTS_BY_DATE)) {
            statementResults.setTimestamp(1, Timestamp.valueOf(date.atStartOfDay()));
            statementResults.setTimestamp(2, Timestamp.valueOf(date.plusDays(1).atStartOfDay()));
            ResultSet resultSet = statementResults.executeQuery();
            results = buildResultList(resultSet);
        } catch (SQLException e) {
            throw new DaoException("Can't find match results", e);
        }
        return results;
    }

    /**
     * Takes  {@link List} of  {@link Match} results by date and confederation
     *
     * @param date          - results date
     * @param confederation - results confederation
     * @return taken {@link List} of all {@link Match} object or empty {@link List}
     * @throws DaoException if {@link SQLException} occurred while working with database
     * @see PreparedStatement
     * @see ResultSet
     */
    @Override
    public List<Match> findResultsByDateAndConfederacy(LocalDate date, String confederation) throws DaoException {
        List<Match> results;
        try (PreparedStatement statementResults = connection.prepareStatement(SELECTS_RESULTS_BY_DATE_AND_CONFEDERACY)) {

            statementResults.setString(1, confederation);
            statementResults.setTimestamp(2, Timestamp.valueOf(date.atStartOfDay()));
            statementResults.setTimestamp(3, Timestamp.valueOf(date.plusDays(1).atStartOfDay()));
            ResultSet resultSet = statementResults.executeQuery();
            results = buildResultList(resultSet);
        } catch (SQLException e) {
            throw new DaoException("Can't find match results", e);
        }
        return results;
    }

    /**
     * Builds {@link Match} object by parsing {@link ResultSet} object.
     *
     * @param resultSet {@link ResultSet} object to parse
     * @return parsed {@link Match} object or null
     * @throws SQLException if the columnLabel is not valid; if a database access error occurs or this method is called
     *                      on a closed result set
     */
    private Match buildMatch(ResultSet resultSet) throws SQLException {
        Match match = new Match();
        match.setId(resultSet.getInt(PARAM_NAME_ID));
        match.setFirstTeam(resultSet.getString(PARAM_NAME_FIRST_TEAM));
        match.setSecondTeam(resultSet.getString(PARAM_NAME_SECOND_TEAM));
        match.setConfederation(resultSet.getString(PARAM_NAME_CONFEDERATION));
        match.setTotal(resultSet.getBigDecimal(PARAM_NAME_TOTAL));
        match.setMaxBet(resultSet.getBigDecimal(PARAM_NAME_MAX_BET));
        match.setDate((resultSet.getTimestamp(PARAM_NAME_DATE).toLocalDateTime()));
        return match;
    }
    /**
     * Builds {@link List} object filled by {@link Match} objects by parsing {@link ResultSet} object.
     *
     * @param resultSet {@link ResultSet} object to parse
     * @return parsed {@link List} object or null
     * @throws SQLException if the columnLabel is not valid; if a database access error occurs or this method is called
     *                      on a closed result set
     * @see #buildResult(ResultSet)
     */
    private List<Match> buildMatchList(ResultSet resultSet) throws SQLException {
        List<Match> matchList = new ArrayList<>();
        while (resultSet.next()) {
            matchList.add(buildMatch(resultSet));
        }
        return matchList;
    }

    /**
     * Builds {@link Match} result object by parsing {@link ResultSet} object.
     *
     * @param resultSet {@link ResultSet} object to parse
     * @return parsed {@link Match} object or null
     * @throws SQLException if the columnLabel is not valid; if a database access error occurs or this method is called
     *                      on a closed result set
     */
    private Match buildResult(ResultSet resultSet) throws SQLException {
        Match match = new Match();
        match.setId(resultSet.getInt(PARAM_NAME_ID));
        match.setFirstTeam(resultSet.getString(PARAM_NAME_FIRST_TEAM));
        match.setSecondTeam(resultSet.getString(PARAM_NAME_SECOND_TEAM));
        match.setConfederation(resultSet.getString(PARAM_NAME_CONFEDERATION));
        match.setFirstTeamScore(resultSet.getBigDecimal(PARAM_NAME_FIRST_TEAM_SCORE));
        match.setSecondTeamScore(resultSet.getBigDecimal(PARAM_NAME_SECOND_TEAM_SCORE));
        match.setDate((resultSet.getTimestamp(PARAM_NAME_DATE).toLocalDateTime()));
        return match;
    }
    /**
     * Builds {@link List} object by {@link Match} results objects by parsing {@link ResultSet} object.
     *
     * @param resultSet {@link ResultSet} object to parse
     * @return parsed {@link List} object or null
     * @throws SQLException if the columnLabel is not valid; if a database access error occurs or this method is called
     *                      on a closed result set
     * @see #buildResult(ResultSet)
     */
    private List<Match> buildResultList(ResultSet resultSet) throws SQLException {
        List<Match> matchList = new ArrayList<>();
        while (resultSet.next()) {
            matchList.add(buildResult(resultSet));
        }
        return matchList;
    }
}
