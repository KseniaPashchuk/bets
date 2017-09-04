package com.epam.bets.dao;

import com.epam.bets.entity.Bet;
import com.epam.bets.exception.DaoException;
import com.epam.bets.pool.ProxyConnection;

import java.sql.SQLException;
import java.util.List;

/**
 * The class provides DAO abstraction for {@link Bet} objects.
 *
 * @author Pashchuk Ksenia
 * @see AbstractDAO
 */

public abstract class BetDAO extends AbstractDAO<Bet> {

    protected static final String PARAM_NAME_ID = "bet_id";
    protected static final String PARAM_NAME_MONEY = "money";
    protected static final String PARAM_NAME_FIRST_TEAM = "first_team";
    protected static final String PARAM_NAME_SECOND_TEAM = "second_team";
    protected static final String PARAM_NAME_BET_TYPE = "bet_type";
    protected static final String PARAM_NAME_USER_ID = "user_id";
    protected static final String PARAM_NAME_COEFFICIENT = "coefficient";

    public BetDAO() {
    }

    public BetDAO(ProxyConnection connection) {
        super(connection);
    }

    /**
     * Takes {@link List} of winned {@link Bet} by user id.
     *
     * @param userId user id
     * @return taken {@link List} of all {@link Bet} object or empty {@link List}
     * @throws DaoException if {@link SQLException} occurred while working with database
     */
    public abstract List<Bet> findWinnedBets(int userId) throws DaoException;

    /**
     * Takes {@link List} of lost {@link Bet} by user id.
     *
     * @param userId user id
     * @return taken {@link List} of {@link Bet} object or empty {@link List}
     * @throws DaoException if {@link SQLException} occurred while working with database
     */
    public abstract List<Bet> findLostBets(int userId) throws DaoException;

    /**
     * Takes {@link List} of open {@link Bet} by user id.
     *
     * @param userId user id
     * @return taken {@link List} of {@link Bet} object or empty {@link List}
     * @throws DaoException if {@link SQLException} occurred while working with database
     */
    public abstract List<Bet> findOpenBets(int userId) throws DaoException;

    /**
     * Create user {@link Bet}s
     *
     * @param bets list of bets to be created
     * @return taken {@link List} of {@link Bet} object or empty {@link List}
     * @throws DaoException if {@link SQLException} occurred while working with database
     */
    public abstract boolean createBets(List<Bet> bets) throws DaoException;

    /**
     * Takes {@link List} of {@link Bet} by match id.
     *
     * @param matchId match id
     * @return taken {@link List} of {@link Bet} object or empty {@link List}
     * @throws DaoException if {@link SQLException} occurred while working with database
     */
    public abstract List<Bet> findBetsByMatchId(int matchId) throws DaoException;

    /**
     * Update isWon param for {@link Bet} by its id.
     *
     * @param betId bet id
     * @param isBetWon if bet is winning
     * @return taken {@link List} of {@link Bet} object or empty {@link List}
     * @throws DaoException if {@link SQLException} occurred while working with database
     */
    public abstract boolean updateIsBetWon(int betId, boolean isBetWon) throws DaoException;

}
