package com.epam.bets.dao;

import com.epam.bets.entity.Bet;
import com.epam.bets.exception.DaoException;

import java.util.List;

public abstract class BetDAO extends AbstractDAO<Bet> {

    protected static final String PARAM_NAME_ID = "bet_id";
    protected static final String PARAM_NAME_MONEY = "money";
    protected static final String PARAM_NAME_WON = "won";
    protected static final String PARAM_NAME_FIRST_TEAM = "first_team";
    protected static final String PARAM_NAME_SECOND_TEAM = "second_team";
    protected static final String PARAM_NAME_BET_TYPE = "bet_type";
    protected static final String PARAM_NAME_USER_ID = "user_id";

    public abstract List<Bet> findWinnedBets() throws DaoException;
    public abstract List<Bet> findLostBets() throws DaoException;
}
