package com.epam.bets.dao;


import com.epam.bets.entity.GainCoefficient;
import com.epam.bets.exception.DaoException;
import com.epam.bets.pool.ProxyConnection;

import java.sql.SQLException;

/**
 * The class provides DAO abstraction for {@link GainCoefficient} objects.
 *
 * @author Pashchuk Ksenia
 * @see AbstractDAO
 */
public abstract class GainCoefficientDAO extends AbstractDAO<GainCoefficient> {

    protected static final String PARAM_NAME_BET_TYPE = "bet_type";
    protected static final String PARAM_NAME_GAIN_COEFFICIENT = "coefficient";

    protected GainCoefficientDAO() {
    }

    protected GainCoefficientDAO(ProxyConnection connection) {
        super(connection);
    }

    /**
     * Takes {@link GainCoefficient}  by match id.
     *
     * @param matchId match id
     * @return  Takes {@link GainCoefficient} object
     * @throws DaoException if {@link SQLException} occurred while working with database
     */
    public abstract GainCoefficient findCoefficientsByMatchId(int matchId) throws DaoException;

}
