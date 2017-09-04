package com.epam.bets.dao;

import com.epam.bets.entity.CreditCards;
import com.epam.bets.exception.DaoException;
import com.epam.bets.pool.ProxyConnection;

import java.sql.SQLException;

/**
 * The class provides DAO abstraction for {@link CreditCards} objects.
 *
 * @author Pashchuk Ksenia
 * @see AbstractDAO
 */

public abstract class CreditCardDAO extends AbstractDAO<CreditCards> {

    protected static final String PARAM_NAME_USER_ID = "user_id";
    protected static final String PARAM_NAME_CARD_NUMBER = "card_number";

    public CreditCardDAO() {
    }

    public CreditCardDAO(ProxyConnection connection) {
        super(connection);
    }

    /**
     * Takes {@link CreditCards}  by user id.
     *
     * @param userId user id
     * @return  Takes {@link CreditCards} object
     * @throws DaoException if {@link SQLException} occurred while working with database
     */
    public abstract CreditCards findCardsByUserId(int userId) throws DaoException;

    /**
     * Deletes {@link CreditCards}  by user id.
     *
     * @param userId user id
     * @return true if successfully deleted
     * @throws DaoException if {@link SQLException} occurred while working with database
     */
    public abstract boolean deleteByUserId(int userId) throws DaoException;
}
