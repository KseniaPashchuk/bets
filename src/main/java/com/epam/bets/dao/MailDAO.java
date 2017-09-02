package com.epam.bets.dao;

import com.epam.bets.entity.SupportMail;
import com.epam.bets.exception.DaoException;
import com.epam.bets.pool.ProxyConnection;

import java.util.List;

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

    public abstract List<SupportMail> findLastUsersMail() throws DaoException;

    public abstract List<SupportMail> findAllUserMail(String email) throws DaoException;
}
