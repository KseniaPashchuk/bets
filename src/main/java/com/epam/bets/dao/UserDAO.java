package com.epam.bets.dao;

import com.epam.bets.entity.User;
import com.epam.bets.exception.DaoException;
import com.epam.bets.pool.ProxyConnection;

public abstract class UserDAO extends AbstractDAO<User> {

    protected static final String PARAM_NAME_ID = "user_id";
    protected static final String PARAM_NAME_LOGIN = "login";
    protected static final String PARAM_NAME_PASSWORD = "password";
    protected static final String PARAM_NAME_FIRST_NAME = "first_name";
    protected static final String PARAM_NAME_LAST_NAME = "last_name";
    protected static final String PARAM_NAME_BIRTH_DATE = "birth_date";
    protected static final String PARAM_NAME_ROLE = "role";
    protected static final String PARAM_NAME_BALANCE = "balance";
    protected static final String PARAM_NAME_AVATAR = "avatar_url";

    protected UserDAO() {
    }

    protected UserDAO(ProxyConnection connection) {
        super(connection);
    }

    public abstract User findUserByLogin(String login) throws DaoException;

    public abstract String findPasswordByLogin(String login) throws DaoException;

    public abstract boolean updatePasswordByLogin(String login, String password) throws DaoException;

    public abstract boolean changeAvatar(int id, String avatarUrl) throws DaoException;
}
