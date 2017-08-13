package com.epam.bets.dao.impl;

import com.epam.bets.dao.CreditCardDAO;
import com.epam.bets.entity.CreditCards;
import com.epam.bets.exception.DaoException;
import com.epam.bets.pool.ProxyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class CreditCardDAOImpl extends CreditCardDAO {

    private static final String SELECT_CREDIT_CARDS_BY_USER_ID =
            "SELECT card_number FROM credit_card WHERE user_id=?";
    private static final String DELETE_CREDIT_CARDS_BY_USER_ID = "DELETE FROM credit_card WHERE user_id=?";
    private static final String CREATE_CREDIT_CARD = "INSERT INTO credit_card (credit_card_id, card_number, user_id) VALUES(NULL, ?, ?)";

    public CreditCardDAOImpl() {
    }

    public CreditCardDAOImpl(ProxyConnection connection) {
        super(connection);
    }


    @Override
    public CreditCards findCardsByUserId(int userId) throws DaoException {
        CreditCards creditCards = new CreditCards();
        try (PreparedStatement statementCreditCard = connection.prepareStatement(SELECT_CREDIT_CARDS_BY_USER_ID)) {
            statementCreditCard.setInt(1, userId);
            ResultSet resultSet = statementCreditCard.executeQuery();
            while (resultSet.next()) {
                creditCards.addCreditCard(resultSet.getString(PARAM_NAME_CARD_NUMBER));
            }
            creditCards.setUserId(userId);
        } catch (SQLException e) {
            throw new DaoException("Can't find credit cards by user id", e);
        }
        return creditCards;
    }

    @Override
    public boolean deleteByUserId(int userId) throws DaoException {
        try (PreparedStatement statementCreditCard = connection.prepareStatement(DELETE_CREDIT_CARDS_BY_USER_ID)) {
            statementCreditCard.setInt(1, userId);
            statementCreditCard.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DaoException("Can't delete credit crads by user id", e);
        }
    }

    @Override
    public int create(CreditCards entity) throws DaoException {
        try (PreparedStatement statementCreditCard = connection.prepareStatement(CREATE_CREDIT_CARD)) {
            for (int i = 0; i < entity.getCreditCarsSize(); i++) {
                statementCreditCard.setString(1, entity.getCreditCard(i));
                statementCreditCard.setInt(2, entity.getUserId());
                statementCreditCard.executeUpdate();
            }
            return 1;
        } catch (SQLException e) {
            throw new DaoException("Can't create credit cards", e);
        }
    }

    @Override
    public boolean update(CreditCards entity) throws DaoException {
        boolean isDeleted = deleteByUserId(entity.getUserId());
        int isCreated = 0;
        if (isDeleted) {
            isCreated = create(entity);
        }
        if (isDeleted && isCreated != 0) {
            return true;
        }
        return false;
    }
}
