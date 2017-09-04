package com.epam.bets.dao;

import com.epam.bets.entity.Match;
import com.epam.bets.entity.News;
import com.epam.bets.exception.DaoException;
import com.epam.bets.pool.ProxyConnection;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * The class provides DAO abstraction for {@link Match} objects.
 *
 * @author Pashchuk Ksenia
 * @see AbstractDAO
 */
public abstract class MatchDAO extends AbstractDAO<Match> {
    protected static final String PARAM_NAME_ID = "match_id";
    protected static final String PARAM_NAME_FIRST_TEAM = "first_team";
    protected static final String PARAM_NAME_SECOND_TEAM = "second_team";
    protected static final String PARAM_NAME_FIRST_TEAM_SCORE = "first_team_score";
    protected static final String PARAM_NAME_SECOND_TEAM_SCORE = "second_team_score";
    protected static final String PARAM_NAME_MAX_BET = "max_bet_value";
    protected static final String PARAM_NAME_TOTAL = "total_value";
    protected static final String PARAM_NAME_CONFEDERATION = "confederation_name";
    protected static final String PARAM_NAME_DATE = "date_time";
    protected static final String PARAM_NAME_TEAM_NAME = "team_name";
    protected static final String PARAM_NAME_TEAM_COUNTRY = "country";

    protected MatchDAO() {
    }

    protected MatchDAO(ProxyConnection connection) {
        super(connection);
    }

    /**
     * Takes  {@link List} of all {@link Match}
     *
     * @return taken {@link List} of all {@link Match} object or empty {@link List}
     * @throws DaoException if {@link SQLException} occurred while working with database
     */

    public abstract List<Match> findAllMatches() throws DaoException;

    /**
     * Takes  {@link List} of  {@link Match} results by date
     *
     * @param date - results date
     * @return taken {@link List} of all {@link Match} object or empty {@link List}
     * @throws DaoException if {@link SQLException} occurred while working with database
     */
    public abstract List<Match> findResultsByDate(LocalDate date) throws DaoException;

    /**
     * Takes  {@link List} of  {@link Match} results by date and confederation
     *
     * @param date          - results date
     * @param confederation - results confederation
     * @return taken {@link List} of all {@link Match} object or empty {@link List}
     * @throws DaoException if {@link SQLException} occurred while working with database
     */
    public abstract List<Match> findResultsByDateAndConfederacy(LocalDate date, String confederation) throws DaoException;

    /**
     * Takes  {@link List} of  {@link Match} by confederation
     *
     * @param confederation - results confederation
     * @return taken {@link List} of all {@link Match} object or empty {@link List}
     * @throws DaoException if {@link SQLException} occurred while working with database
     */
    public abstract List<Match> findMatchesByConfederacy(String confederation) throws DaoException;

    /**
     * Takes  {@link List} of  all {@link String} confederations
     *
     * @return taken {@link List} of all {@link String} object or empty {@link List}
     * @throws DaoException if {@link SQLException} occurred while working with database
     */
    public abstract List<String> findAllConfederations() throws DaoException;

    /**
     * Takes  {@link List} of  all {@link String} football teams
     *
     * @return taken {@link List} of all {@link String} object or empty {@link List}
     * @throws DaoException if {@link SQLException} occurred while working with database
     */
    public abstract List<String> findAllTeams() throws DaoException;

    /**
     * Provides setting score for match
     *
     * @param matchId - match id
     * @param firstTeamScore - firts team score
     * @param secondTeamScore - second team score
     * @return true if successfully set score
     * @throws DaoException if {@link SQLException} occurred while working with database
     */
    public abstract boolean setScore(int matchId, BigDecimal firstTeamScore, BigDecimal secondTeamScore) throws DaoException;

    /**
     * Takes finished {@link Match} info by match id
     *
     * @return taken {@link Match}
     * @throws DaoException if {@link SQLException} occurred while working with database
     */
    public abstract Match findFinishedMatchInfo(int matchId) throws DaoException;

    /**
     * Creates new {@link Match}
     *
     * @return true if successfully created
     * @throws DaoException if {@link SQLException} occurred while working with database
     */
    public abstract boolean createNewFootballTeam(String team, String country) throws DaoException;

    /**
     * Creates new confederation
     *
     * @return true if successfully created
     * @throws DaoException if {@link SQLException} occurred while working with database
     */
    public abstract boolean createNewConfederation(String confederation) throws DaoException;
}
