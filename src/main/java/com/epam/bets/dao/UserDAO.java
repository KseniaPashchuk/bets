package com.epam.bets.dao;

import com.epam.bets.entity.User;
import com.epam.bets.exception.DaoException;
import com.epam.bets.pool.ProxyConnection;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

/**
 * The class provides DAO abstraction for {@link User} objects.
 *
 * @author Pashchuk Ksenia
 * @see AbstractDAO
 */
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

    /**
     * Takes {@link User} object by user id
     *
     * @param id - user id
     * @return taken {@link User} object
     * @throws DaoException if {@link SQLException} occurred while working with database
     */
    public abstract User findUserById(int id) throws DaoException;

    /**
     * Takes {@link User} object by user login
     *
     * @param login - user login
     * @return taken {@link User} object
     * @throws DaoException if {@link SQLException} occurred while working with database
     */
    public abstract User findUserByLogin(String login) throws DaoException;

    /**
     * Takes user password by user login
     *
     * @param login - user login
     * @return taken {@link String} password
     * @throws DaoException if {@link SQLException} occurred while working with database
     */
    public abstract String findPasswordByLogin(String login) throws DaoException;

    /**
     * Updates password by user login
     *
     * @param login    - user login
     * @param password - new user password
     * @return true if successfully updated
     * @throws DaoException if {@link SQLException} occurred while working with database
     */
    public abstract boolean updatePasswordByLogin(String login, String password) throws DaoException;

    /**
     * Updates user avatar by user id
     *
     * @param id        - user id
     * @param avatarUrl - new user avatar url
     * @return true if successfully updated
     * @throws DaoException if {@link SQLException} occurred while working with database
     */
    public abstract boolean updateAvatar(int id, String avatarUrl) throws DaoException;

    /**
     * Checks if definite login exists
     *
     * @param login - login to check
     * @return true if login exists
     * @throws DaoException if {@link SQLException} occurred while working with database
     */
    public abstract boolean loginExists(String login) throws DaoException;

    /**
     * Updates user avatar by user id
     *
     * @param userId  - user id
     * @param balance - user balance to update
     * @return true if successfully updated
     * @throws DaoException if {@link SQLException} occurred while working with database
     */
    public abstract boolean updateBalance(int userId, BigDecimal balance) throws DaoException;

    /**
     * Takes {@link BigDecimal} balance by user id
     *
     * @param userId - user id
     * @return taken {@link BigDecimal} balance
     * @throws DaoException if {@link SQLException} occurred while working with database
     */
    public abstract BigDecimal findBalance(int userId) throws DaoException;

    /**
     * Takes {@link User} object by user login
     *
     * @param login - user login
     * @return taken {@link User} object
     * @throws DaoException if {@link SQLException} occurred while working with database
     */
    public abstract int findUserIdByLogin(String login) throws DaoException;

    /**
     * Makes user bets
     *
     * @param userId - user id
     * @param money - monetary bet
     * @return true if successfully made bet
     * @throws DaoException if {@link SQLException} occurred while working with database
     */
    public abstract boolean makeBet(int userId, BigDecimal money) throws DaoException;

}
