package com.epam.bets.dao.impl;

import com.epam.bets.dao.UserDAO;
import com.epam.bets.entity.User;
import com.epam.bets.entity.UserRole;
import com.epam.bets.exception.DaoException;
import com.epam.bets.pool.ProxyConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl extends UserDAO {

    private static final Logger LOGGER = LogManager.getLogger(UserDAOImpl.class);

    private static final String LOGIN_EXISTS = "SELECT  login FROM user WHERE login=?";
    private static final String SELECT_ALL_USERS =
            "SELECT user_id, login, first_name, last_name, birth_date, role, avatar_url, balance FROM user";
    private static final String SELECT_USER_BY_ID =
            "SELECT user_id, login, first_name, last_name, birth_date, role, avatar_url, balance FROM user WHERE user_id=?";
    private static final String SELECT_USER_BY_LOGIN =
            "SELECT user_id, login, first_name, last_name, birth_date, role, avatar_url, balance FROM user WHERE login=?";
    private static final String SELECT_PASSWORD_BY_LOGIN =
            "SELECT password FROM user WHERE login=?";
    private static final String SELECT_BALANCE =
            "SELECT balance FROM user WHERE user_id=?";
    private static final String DELETE_USER_BY_ID = "DELETE FROM user WHERE user_id=?";
    private static final String CREATE_USER = "INSERT INTO user (user_id, login, password, first_name, last_name, birth_date, role, avatar_url, balance)" +
            " VALUES( NULL, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_USER = "UPDATE user SET first_name=?, last_name=?, birth_date=? WHERE user_id=?";
    private static final String UPDATE_USER_PASSWORD = "UPDATE user SET password=? WHERE login=?";
    private static final String UPDATE_AVATAR = "UPDATE user SET avatar_url=? WHERE user_id=?";
    private static final String UPDATE_BALANCE = "UPDATE user SET balance=? WHERE user_id=?";

    public UserDAOImpl() {
    }

    public UserDAOImpl(ProxyConnection connection) {
        super(connection);
    }


    @Override
    public User findUserById(int id) throws DaoException {
        User user = null;
        try (PreparedStatement statementUser = connection.prepareStatement(SELECT_USER_BY_ID)) {
            statementUser.setInt(1, id);
            ResultSet resultSet = statementUser.executeQuery();
            if (resultSet.next()) {
                user = buildUser(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException("Can't find user by id", e);
        }
        return user;
    }

    @Override
    public User findUserByLogin(String login) throws DaoException {
        User user = null;
        try (PreparedStatement statementUser = connection.prepareStatement(SELECT_USER_BY_LOGIN)) {
            statementUser.setString(1, login);
            ResultSet resultSet = statementUser.executeQuery();
            if (resultSet.next()) {
                user = buildUser(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException("Can't find user by login", e);
        }
        return user;
    }

    @Override
    public String findPasswordByLogin(String login) throws DaoException {
        String password = "";
        try (PreparedStatement statementUser = connection.prepareStatement(SELECT_PASSWORD_BY_LOGIN)) {
            statementUser.setString(1, login);
            ResultSet resultSet = statementUser.executeQuery();
            if (resultSet.next()) {
                password = resultSet.getString(PARAM_NAME_PASSWORD);
            }
        } catch (SQLException e) {
            throw new DaoException("Can't find password by login", e);
        }
        return password;
    }

    @Override
    public int create(User entity) throws DaoException {
        try (PreparedStatement statementUser = connection.prepareStatement(CREATE_USER, Statement.RETURN_GENERATED_KEYS)) {
            statementUser.setString(1, entity.getLogin());
            statementUser.setString(2, entity.getPassword());
            statementUser.setString(3, entity.getFirstName());
            statementUser.setString(4, entity.getLastName());
            statementUser.setDate(5, java.sql.Date.valueOf(entity.getBirthDate()));
            statementUser.setString(6, entity.getRole().getStringRepresentation());
            statementUser.setString(7, entity.getAvatarUrl());
            statementUser.setBigDecimal(8, entity.getBalance());
            statementUser.executeUpdate();
            ResultSet generatedKey = statementUser.getGeneratedKeys();
            if (generatedKey.next()) {
                return generatedKey.getInt(1);
            }
        } catch (SQLException e) {
            if (e.getErrorCode() == EXISTING_ENTITY_ERROR_CODE) {
                return 0;
            }
            throw new DaoException("Can't create user", e);
        }
        return 0;
    }

    @Override
    public boolean update(User entity) throws DaoException {
        try (PreparedStatement statementUser = connection.prepareStatement(UPDATE_USER)) {
            statementUser.setString(1, entity.getFirstName());
            statementUser.setString(2, entity.getLastName());
            statementUser.setDate(3, java.sql.Date.valueOf(entity.getBirthDate()));
            statementUser.setInt(4, entity.getId());
            statementUser.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DaoException("Can't update user", e);
        }
    }


    @Override
    public boolean updatePasswordByLogin(String login, String password) throws DaoException {
        try (PreparedStatement statementUser = connection.prepareStatement(UPDATE_USER_PASSWORD)) {
            statementUser.setString(1, password);
            statementUser.setString(2, login);
            statementUser.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DaoException("Can't update user", e);
        }
    }

    @Override
    public boolean updateAvatar(int id, String avatarUrl) throws DaoException {
        try (PreparedStatement statementUser = connection.prepareStatement(UPDATE_AVATAR)) {
            statementUser.setString(1, avatarUrl);
            statementUser.setInt(2, id);
            statementUser.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DaoException("Can't update avatar", e);
        }
    }

    @Override
    public boolean loginExists(String login) throws DaoException {
        try (PreparedStatement statementUser = connection.prepareStatement(LOGIN_EXISTS)) {
            statementUser.setString(1, login);
            ResultSet resultSet = statementUser.executeQuery();
            if (resultSet.next()) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            throw new DaoException("Can't update avatar", e);
        }
    }

    @Override
    public boolean updateBalance(int userId, BigDecimal balance) throws DaoException {
        BigDecimal currentBalance;
        try (PreparedStatement statementSelectBalance = connection.prepareStatement(SELECT_BALANCE);
             PreparedStatement statementUpdateBalance = connection.prepareStatement(UPDATE_BALANCE)) {
            statementSelectBalance.setInt(1, userId);

            ResultSet resultSet = statementSelectBalance.executeQuery();
            if (resultSet.next()) {
                currentBalance = resultSet.getBigDecimal(PARAM_NAME_BALANCE);
                statementUpdateBalance.setBigDecimal(1, currentBalance.add(balance));
                statementUpdateBalance.setInt(2, userId);
                statementUpdateBalance.executeUpdate();
                return true;
            }
            return false;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public BigDecimal findBalance(int userId) throws DaoException {
        BigDecimal currentBalance = null;
        try (PreparedStatement statementSelectBalance = connection.prepareStatement(SELECT_BALANCE)) {
            statementSelectBalance.setInt(1, userId);
            ResultSet resultSet = statementSelectBalance.executeQuery();
            if (resultSet.next()) {
                currentBalance = resultSet.getBigDecimal(PARAM_NAME_BALANCE);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return currentBalance;
    }

    private User buildUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt(PARAM_NAME_ID));
        user.setLogin(resultSet.getString(PARAM_NAME_LOGIN));
        user.setFirstName(resultSet.getString(PARAM_NAME_FIRST_NAME));
        user.setLastName(resultSet.getString(PARAM_NAME_LAST_NAME));
        user.setBirthDate(resultSet.getDate(PARAM_NAME_BIRTH_DATE).toLocalDate());
        user.setRole(UserRole.valueOf(resultSet.getString(PARAM_NAME_ROLE).toUpperCase()));
        user.setBalance(resultSet.getBigDecimal(PARAM_NAME_BALANCE));
        user.setAvatarUrl(resultSet.getString(PARAM_NAME_AVATAR));
        return user;
    }

    private List<User> buildUserList(ResultSet resultSet) throws SQLException {
        List<User> userList = new ArrayList<>();
        while (resultSet.next()) {
            userList.add(buildUser(resultSet));
        }
        return userList;
    }
}
