package com.epam.bets.dao;

import com.epam.bets.entity.FAQ;
import com.epam.bets.exception.DaoException;
import com.epam.bets.pool.ProxyConnection;

import java.util.List;

public abstract class FaqDAO extends AbstractDAO<FAQ> {
    protected static final String PARAM_NAME_ID = "faq_id";
    protected static final String PARAM_NAME_QUESTION = "question";
    protected static final String PARAM_NAME_ANSWER = "answer";

    protected FaqDAO() {
    }

    protected FaqDAO(ProxyConnection connection) {
        super(connection);
    }

    public abstract boolean deleteByQuestion(String question) throws DaoException;
    public abstract List<FAQ> findAllFAQ() throws DaoException;
}
