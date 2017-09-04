package com.epam.bets.dao;

import com.epam.bets.entity.FAQ;
import com.epam.bets.exception.DaoException;
import com.epam.bets.pool.ProxyConnection;

import java.sql.SQLException;
import java.util.List;

/**
 * The class provides DAO abstraction for {@link FAQ} objects.
 *
 * @author Pashchuk Ksenia
 * @see AbstractDAO
 */
public abstract class FaqDAO extends AbstractDAO<FAQ> {
    protected static final String PARAM_NAME_ID = "faq_id";
    protected static final String PARAM_NAME_QUESTION = "question";
    protected static final String PARAM_NAME_ANSWER = "answer";

    protected FaqDAO() {
    }

    protected FaqDAO(ProxyConnection connection) {
        super(connection);
    }

    /**
     *  Deletes {@link FAQ} by faq question.
     *
     * @param question faq question.
     * @return true if successfully deleted
     * @throws DaoException if {@link SQLException} occurred while working with database
     */
    public abstract boolean deleteByQuestion(String question) throws DaoException;

    /**
     * Takes  {@link List} of all {@link FAQ}
     *
     * @return taken {@link List} of all {@link FAQ} object or empty {@link List}
     * @throws DaoException if {@link SQLException} occurred while working with database
     */
    public abstract List<FAQ> findAllFAQ() throws DaoException;
}
