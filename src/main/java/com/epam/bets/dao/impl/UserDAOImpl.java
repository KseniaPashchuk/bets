package com.epam.bets.dao.impl;

import com.epam.bets.dao.UserDAO;
import com.epam.bets.entity.User;
import com.epam.bets.entity.UserRole;
import com.epam.bets.exception.DaoException;
import com.epam.bets.pool.ProxyConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl extends UserDAO {

    private static final Logger LOGGER = LogManager.getLogger(UserDAOImpl.class);
    private static final String SELECT_ALL_USERS =
            "SELECT user_id, login, first_name, last_name, birth_date, credit_card, role, avatar_url FROM user";
    private static final String SELECT_USER_BY_ID =
            "SELECT user_id, login, first_name, last_name, birth_date, credit_card, role, avatar_url FROM user WHERE user_id=?";
    private static final String SELECT_USER_BY_LOGIN =
            "SELECT user_id, login, first_name, last_name, birth_date, credit_card, role, avatar_url FROM user WHERE login=?";
    private static final String SELECT_PASSWORD_BY_LOGIN =
            "SELECT password FROM user WHERE login=?";
    private static final String DELETE_USER_BY_ID = "DELETE FROM user WHERE news_id=?";
    private static final String CREATE_USER = "INSERT INTO user (user_id, login, password, first_name, last_name, birth_date, credit_card, role, avatar_url)" +
            " VALUES( NULL, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_USER_BY_LOGIN = "UPDATE user SET password=?, first_name=?, last_name=?, birth_date=?, credit_card=? WHERE login=?";
    private static final String UPDATE_USER = "UPDATE user SET password=?, first_name=?, last_name=?, birth_date=?, credit_card=? WHERE user_id=?";

    public UserDAOImpl() {
    }

    public UserDAOImpl(ProxyConnection connection) {
        super(connection);
    }

    @Override
    public List<User> findAll() throws DaoException {
        List<User> userList;
        try (PreparedStatement statementUser = connection.prepareStatement(SELECT_ALL_USERS)) {
            ResultSet resultSet = statementUser.executeQuery();
            userList = buildUserList(resultSet);
        } catch (SQLException e) {
            throw new DaoException("Can't find all users", e);
        }
        return userList;
    }

    @Override
    public User findEntityById(int id) throws DaoException {
        User user = null;
        try (PreparedStatement statementUser = connection.prepareStatement(SELECT_USER_BY_ID)) {
            statementUser.setInt(1, id);
            ResultSet resultSet = statementUser.executeQuery();
            if (resultSet.next()) {
                user = buildUser(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException("Can't find user by id", e);// throw new DBException();
        }
        return user;
    }

    @Override
    public User findUserByLogin(String login) throws DaoException {//TODO do like this one!
        User user = null;
        try (PreparedStatement statementUser = connection.prepareStatement(SELECT_USER_BY_LOGIN)) {
            statementUser.setString(1, login);
            ResultSet resultSet = statementUser.executeQuery();
            if (resultSet.next()) {
                return buildUser(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException("Can't find user by login", e);
        }
        return user;
    }

    @Override
    public String findPasswordByLogin(String login) throws DaoException {
        String password = "";//TODO
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
    public boolean delete(int id) throws DaoException {
        try (PreparedStatement statementUser = connection.prepareStatement(DELETE_USER_BY_ID)) {
            statementUser.setInt(1, id);
            statementUser.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DaoException("Can't delete user", e);
        }
    }


    @Override
    public boolean create(User entity) throws DaoException {
        try (PreparedStatement statementUser = connection.prepareStatement(CREATE_USER)) {
            statementUser.setString(1, entity.getLogin());
            statementUser.setString(2, entity.getPassword());
            statementUser.setString(3, entity.getFirstName());
            statementUser.setString(4, entity.getLastName());
            statementUser.setDate(5, java.sql.Date.valueOf(entity.getBirthDate()));
            statementUser.setString(6, entity.getCreditCard());
            statementUser.setString(7, entity.getRole().getStringRepresentation());
            statementUser.setString(8, entity.getAvatarUrl());
            statementUser.executeUpdate();
            return true;
        } catch (SQLException e) {
            if (e.getErrorCode() == EXISTING_ENTITY_ERROR_CODE) {
                return false;
            }
            throw new DaoException("Can't create user", e);
        }
    }

    @Override
    public boolean update(User entity, int id) throws DaoException {
        try (PreparedStatement statementUser = connection.prepareStatement(UPDATE_USER)) {
            statementUser.setString(1, entity.getPassword());
            statementUser.setString(2, entity.getFirstName());
            statementUser.setString(3, entity.getLastName());
            statementUser.setDate(4, java.sql.Date.valueOf(entity.getBirthDate()));
            statementUser.setString(5, entity.getCreditCard());
            statementUser.setInt(6, id);
            statementUser.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DaoException("Can't update user", e);
        }
    }

    public boolean updateByLogin(User entity) throws DaoException {
        try (PreparedStatement statementUser = connection.prepareStatement(UPDATE_USER_BY_LOGIN)) {
            statementUser.setString(1, entity.getPassword());
            statementUser.setString(2, entity.getFirstName());
            statementUser.setString(3, entity.getLastName());
            statementUser.setDate(4, java.sql.Date.valueOf(entity.getBirthDate()));
            statementUser.setString(5, entity.getCreditCard());
            statementUser.setString(6, entity.getLogin());
            statementUser.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DaoException("Can't update user", e);
        }
    }

    private User buildUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt(PARAM_NAME_ID));
        user.setLogin(resultSet.getString(PARAM_NAME_LOGIN));
        user.setFirstName(resultSet.getString(PARAM_NAME_FIRST_NAME));
        user.setLastName(resultSet.getString(PARAM_NAME_LAST_NAME));
        user.setBirthDate(resultSet.getDate(PARAM_NAME_BIRTH_DATE).toLocalDate());
        user.setCreditCard(resultSet.getString(PARAM_NAME_CREDIT_CARD));
        user.setRole(UserRole.valueOf(resultSet.getString(PARAM_NAME_ROLE).toUpperCase()));
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
