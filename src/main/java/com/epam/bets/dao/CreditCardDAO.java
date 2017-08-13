package com.epam.bets.dao;

import com.epam.bets.entity.CreditCards;
import com.epam.bets.exception.DaoException;
import com.epam.bets.pool.ProxyConnection;

public abstract class CreditCardDAO extends AbstractDAO<CreditCards> {

    protected static final String PARAM_NAME_USER_ID = "user_id";
    protected static final String PARAM_NAME_CARD_NUMBER = "card_number";

    public CreditCardDAO() {
    }

    public CreditCardDAO(ProxyConnection connection) {
        super(connection);
    }

    public abstract CreditCards findCardsByUserId(int userId) throws DaoException;

    public abstract boolean deleteByUserId(int userId) throws DaoException;
}
