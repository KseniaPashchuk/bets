package com.epam.bets.dao.impl;

import com.epam.bets.dao.FaqDAO;
import com.epam.bets.entity.FAQ;
import com.epam.bets.exception.DaoException;
import com.epam.bets.pool.ProxyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FaqDAOImpl extends FaqDAO {

    private static final String SELECT_ALL_FAQ =
            "SELECT faq_id, question, answer FROM faq";
    private static final String SELECT_FAQ_BY_ID =
            "SELECT faq_id, question, answer FROM faq WHERE faq_id=?";
    private static final String SELECT_FAQ_BY_QUESTION =
            "SELECT faq_id, question, answer FROM faq WHERE question=?";
    private static final String DELETE_FAQ_BY_ID = "DELETE FROM faq WHERE faq_id=?";
    private static final String DELETE_FAQ_BY_QUESTION = "DELETE FROM faq WHERE question=?";
    private static final String CREATE_FAQ = "INSERT INTO faq (faq_id, question, answer)" +
            " VALUES( NULL, ?, ?)";
    private static final String UPDATE_FAQ = "UPDATE faq SET answer=? WHERE question=?";


    public FaqDAOImpl() {
    }

    public FaqDAOImpl(ProxyConnection connection) {
        super(connection);
    }


    @Override
    public List<FAQ> findAllFAQ() throws DaoException {
        List<FAQ> faqList;
        try (PreparedStatement statementFAQ = connection.prepareStatement(SELECT_ALL_FAQ)) {
            ResultSet resultSet = statementFAQ.executeQuery();
            faqList = buildFAQList(resultSet);
        } catch (SQLException e) {
            throw new DaoException("Can't find all faq", e);
        }
        return faqList;
    }


    @Override
    public boolean deleteByQuestion(String question) throws DaoException {
        try (PreparedStatement statementFAQ = connection.prepareStatement(DELETE_FAQ_BY_QUESTION)) {
            statementFAQ.setString(1, question);
            statementFAQ.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DaoException("Can't delete faq", e);
        }
    }

    @Override
    public int create(FAQ entity) throws DaoException {
        try (PreparedStatement statementFAQ = connection.prepareStatement(CREATE_FAQ)) {
            statementFAQ.setString(1, entity.getQuestion());
            statementFAQ.setString(2, entity.getAnswer());
            statementFAQ.executeUpdate();
            return 1;
        } catch (SQLException e) {
            if (e.getErrorCode() == EXISTING_ENTITY_ERROR_CODE) {
                return 0;
            }
            throw new DaoException("Can't create faq", e);
        }
    }

    @Override
    public boolean update(FAQ entity) throws DaoException {
        try (PreparedStatement statementNews = connection.prepareStatement(UPDATE_FAQ)) {
            statementNews.setString(1, entity.getAnswer());
            statementNews.setString(2, entity.getQuestion());
            statementNews.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DaoException("Can't update faq", e);
        }
    }

    private FAQ buildFAQ(ResultSet resultSet) throws SQLException {
        FAQ faq = new FAQ();
        faq.setId(resultSet.getInt(PARAM_NAME_ID));
        faq.setQuestion(resultSet.getString(PARAM_NAME_QUESTION));
        faq.setAnswer(resultSet.getString(PARAM_NAME_ANSWER));
        return faq;
    }

    private List<FAQ> buildFAQList(ResultSet resultSet) throws SQLException {
        List<FAQ> faqList = new ArrayList<>();
        while (resultSet.next()) {
            faqList.add(buildFAQ(resultSet));
        }
        return faqList;
    }
}
