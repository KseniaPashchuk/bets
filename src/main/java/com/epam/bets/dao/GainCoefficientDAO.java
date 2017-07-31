package com.epam.bets.dao;

import com.epam.bets.entity.GainCoefficient;
import com.epam.bets.exception.DaoException;
import com.epam.bets.pool.ProxyConnection;

import java.util.List;

public abstract class GainCoefficientDAO extends AbstractDAO<GainCoefficient> {

    protected  static final String PARAM_NAME_FOOTBALL_MATCH_ID = "football_match_id";
    protected static final String PARAM_NAME_BET_TYPE = "bet_type";
    protected static final String PARAM_NAME_GAIN_COEFFICIENT= "coefficient";

    protected GainCoefficientDAO() {
    }

    protected GainCoefficientDAO(ProxyConnection connection) {
        super(connection);
    }


    public abstract List<GainCoefficient> findMatchCoefficients(int matchId) throws DaoException;
}
