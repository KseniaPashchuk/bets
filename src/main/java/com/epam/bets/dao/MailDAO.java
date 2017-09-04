package com.epam.bets.dao;

import com.epam.bets.entity.SupportMail;
import com.epam.bets.exception.DaoException;
import com.epam.bets.pool.ProxyConnection;

import java.sql.SQLException;
import java.util.List;
/**
 * The class provides DAO abstraction for {@link SupportMail} objects.
 *
 * @author Pashchuk Ksenia
 * @see AbstractDAO
 */
public abstract class MailDAO extends AbstractDAO<SupportMail> {

    protected static final String PARAM_NAME_ID = "mail_id";
    protected static final String PARAM_NAME_TEXT = "mail_text";
    protected static final String PARAM_NAME_TYPE = "mail_type";
    protected static final String PARAM_NAME_USER_EMAIL = "user_email";
    protected static final String PARAM_NAME_MAIL_DATE = "mail_date_time";


    public MailDAO() {
    }

    public MailDAO(ProxyConnection connection) {
        super(connection);
    }

    /**
     * Takes  {@link List} of last {@link SupportMail} of all users
     *
     * @return taken {@link List} of all {@link SupportMail} object or empty {@link List}
     * @throws DaoException if {@link SQLException} occurred while working with database
     */
    public abstract List<SupportMail> findLastUsersMail() throws DaoException;


    /**
     * Takes  {@link List} of all {@link SupportMail} by user email.
     *
     * @return taken {@link List} of all {@link SupportMail} object or empty {@link List}
     * @throws DaoException if {@link SQLException} occurred while working with database
     */
    public abstract List<SupportMail> findAllUserMail(String email) throws DaoException;
}
