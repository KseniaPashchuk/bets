package com.epam.bets.dao.impl;

import com.epam.bets.dao.FaqDAO;
import com.epam.bets.entity.FAQ;
import com.epam.bets.exception.DaoException;
import com.epam.bets.pool.ProxyConnection;
import com.mysql.jdbc.Statement;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The class provides {@link FaqDAO} implementation.
 *
 * @author Pashchuk Ksenia
 */
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

    /**
     * Takes  {@link List} of all {@link FAQ}
     *
     * @return taken {@link List} of all {@link FAQ} object or empty {@link List}
     * @throws DaoException if {@link SQLException} occurred while working with database
     * @see PreparedStatement
     * @see ResultSet
     */
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

    /**
     * Deletes {@link FAQ} by faq question.
     *
     * @param question faq question.
     * @return true if successfully deleted
     * @throws DaoException if {@link SQLException} occurred while working with database
     * @see PreparedStatement
     * @see ResultSet
     */
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

    /**
     * Creates {@link FAQ}.
     *
     * @param entity faq entity.
     * @return true if successfully created
     * @throws DaoException if {@link SQLException} occurred while working with database
     * @see PreparedStatement
     * @see ResultSet
     */
    @Override
    public int create(FAQ entity) throws DaoException {
        try (PreparedStatement statementFAQ = connection.prepareStatement(CREATE_FAQ, Statement.RETURN_GENERATED_KEYS)) {
            statementFAQ.setString(1, entity.getQuestion());
            statementFAQ.setString(2, entity.getAnswer());
            statementFAQ.executeUpdate();
            ResultSet generatedKey = statementFAQ.getGeneratedKeys();
            if (generatedKey.next()) {
                return generatedKey.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            if (e.getErrorCode() == EXISTING_ENTITY_ERROR_CODE) {
                return 0;
            }
            throw new DaoException("Can't create faq", e);
        }
    }

    /**
     * Updates {@link FAQ}.
     *
     * @param entity faq entity
     * @return true if successfully updated
     * @throws DaoException if {@link SQLException} occurred while working with database
     * @see PreparedStatement
     * @see ResultSet
     */
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

    /**
     * Builds {@link List} object filled by {@link FAQ} objects by parsing {@link ResultSet} object.
     *
     * @param resultSet {@link ResultSet} object to parse
     * @return parsed {@link List} object or null
     * @throws SQLException if the columnLabel is not valid; if a database access error occurs or this method is called
     *                      on a closed result set
     */
    private FAQ buildFAQ(ResultSet resultSet) throws SQLException {
        FAQ faq = new FAQ();
        faq.setId(resultSet.getInt(PARAM_NAME_ID));
        faq.setQuestion(resultSet.getString(PARAM_NAME_QUESTION));
        faq.setAnswer(resultSet.getString(PARAM_NAME_ANSWER));
        return faq;
    }

    /**
     * Builds {@link List} object filled by {@link FAQ} objects by parsing {@link ResultSet} object.
     *
     * @param resultSet {@link ResultSet} object to parse
     * @return parsed {@link List} object or null
     * @throws SQLException if the columnLabel is not valid; if a database access error occurs or this method is called
     *                      on a closed result set
     * @see #buildFAQ(ResultSet)
     */
    private List<FAQ> buildFAQList(ResultSet resultSet) throws SQLException {
        List<FAQ> faqList = new ArrayList<>();
        while (resultSet.next()) {
            faqList.add(buildFAQ(resultSet));
        }
        return faqList;
    }
}
