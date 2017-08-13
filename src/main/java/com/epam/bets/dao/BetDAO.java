package com.epam.bets.dao;

import com.epam.bets.entity.Bet;
import com.epam.bets.exception.DaoException;
import com.epam.bets.pool.ProxyConnection;

import java.util.List;

public abstract class BetDAO extends AbstractDAO<Bet> {

    protected static final String PARAM_NAME_ID = "bet_id";
    protected static final String PARAM_NAME_MONEY = "money";
    protected static final String PARAM_NAME_WON = "won";
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

    public abstract List<Bet> findWinnedBets(int userId) throws DaoException;

    public abstract List<Bet> findLostBets(int userId) throws DaoException;

    public abstract List<Bet> findOpenBets(int userId) throws DaoException;

    public abstract boolean createBets(List<Bet> bets) throws DaoException;

    public abstract List<Bet> findBetsByMatchId(int matchId) throws DaoException;

    public abstract boolean updateIsBetWon(int betId, boolean isBetWon) throws DaoException;

}
