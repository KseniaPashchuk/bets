package com.epam.bets.dao.impl;

import com.epam.bets.dao.MailDAO;
import com.epam.bets.entity.News;
import com.epam.bets.entity.SupportMail;
import com.epam.bets.exception.DaoException;
import com.epam.bets.pool.ProxyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * The class provides {@link MailDAO} implementation.
 *
 * @author Pashchuk Ksenia
 */
public class MailDAOImpl extends MailDAO {
    private static final String SELECT_ALL_USER_MAIL =
            "SELECT mail_id, mail_text, mail_date_time, mail_type, login AS user_email FROM support_mail JOIN " +
                    "user ON support_mail.user_id = user.user_id WHERE login=? ORDER BY mail_date_time ASC";
    private static final String SELECT_ALL_USER_EMAILS =
            " SELECT mail_id, mail_text, mail_date_time, mail_type, login AS user_email" +
                    " FROM support_mail AS m1" +
                    " JOIN user ON m1.user_id = user.user_id " +
                    " JOIN(SELECT user_id, MAX(mail_date_time) MaxDate" +
                    " FROM support_mail" +
                    " GROUP BY user_id) AS m2 ON m1.user_id = m2.user_id AND m1.mail_date_time  = m2.maxdate" +
                    " ORDER BY mail_date_time ASC";

    private static final String CREATE_MAIL = "INSERT INTO support_mail (mail_id, mail_text, " +
            " mail_date_time, mail_type, user_id)" +
            " VALUES( NULL, ?, ?, ?, (SELECT user_id FROM user WHERE login=?))";

    public MailDAOImpl() {
    }

    public MailDAOImpl(ProxyConnection connection) {
        super(connection);
    }


    /**
     * Takes  {@link List} of last {@link SupportMail} of all users
     *
     * @return taken {@link List} of all {@link SupportMail} object or empty {@link List}
     * @throws DaoException if {@link SQLException} occurred while working with database
     * @see PreparedStatement
     * @see ResultSet
     */
    @Override
    public List<SupportMail> findLastUsersMail() throws DaoException {
        List<SupportMail> mailList;
        try (PreparedStatement statementMail = connection.prepareStatement(SELECT_ALL_USER_EMAILS)) {
            ResultSet resultSet = statementMail.executeQuery();
            mailList = buildMailList(resultSet);
        } catch (SQLException e) {
            throw new DaoException("Can't find last users mail", e);
        }
        return mailList;
    }

    /**
     * Takes  {@link List} of all {@link SupportMail} by user email.
     *
     * @return taken {@link List} of all {@link SupportMail} object or empty {@link List}
     * @throws DaoException if {@link SQLException} occurred while working with database
     * @see PreparedStatement
     * @see ResultSet
     */
    @Override
    public List<SupportMail> findAllUserMail(String email) throws DaoException {
        List<SupportMail> mailList;
        try (PreparedStatement statementMail = connection.prepareStatement(SELECT_ALL_USER_MAIL)) {
            statementMail.setString(1, email);
            ResultSet resultSet = statementMail.executeQuery();
            mailList = buildMailList(resultSet);
        } catch (SQLException e) {
            throw new DaoException("Can't find all users mail", e);
        }
        return mailList;
    }

    /**
     * Create user {@link SupportMail}
     *
     * @param entity mail info
     * @return id of created mail
     * @throws DaoException if {@link SQLException} occurred while working with database
     * @see PreparedStatement
     * @see ResultSet
     */
    @Override
    public int create(SupportMail entity) throws DaoException {
        try (PreparedStatement statementMail = connection.prepareStatement(CREATE_MAIL)) {
            statementMail.setString(1, entity.getMailText());
            statementMail.setTimestamp(2, Timestamp.valueOf(entity.getMailDate()));
            statementMail.setString(3, entity.getType().toString());
            statementMail.setString(4, entity.getUserEmail());
            statementMail.executeUpdate();
            return 1;
        } catch (SQLException e) {
            throw new DaoException("Can't create mail", e);
        }
    }

    /**
     * Update user {@link SupportMail}. Unnecessary operation.
     *
     * @param entity bet info
     * @throws {@link UnsupportedOperationException}
     */
    @Override
    public boolean update(SupportMail entity) throws DaoException {
        throw new UnsupportedOperationException();
    }

    /**
     * Builds {@link SupportMail} object by parsing {@link ResultSet} object.
     *
     * @param resultSet {@link ResultSet} object to parse
     * @return parsed {@link SupportMail} object or null
     * @throws SQLException if the columnLabel is not valid; if a database access error occurs or this method is called
     *                      on a closed result set
     */
    private SupportMail buildMail(ResultSet resultSet) throws SQLException {
        SupportMail mail = new SupportMail();
        mail.setMailId(resultSet.getInt(PARAM_NAME_ID));
        mail.setMailText(resultSet.getString(PARAM_NAME_TEXT));
        mail.setType(SupportMail.MailType.valueOf(resultSet.getString(PARAM_NAME_TYPE)));
        mail.setMailDate((resultSet.getTimestamp(PARAM_NAME_MAIL_DATE).toLocalDateTime()));
        mail.setUserEmail(resultSet.getString(PARAM_NAME_USER_EMAIL));
        return mail;
    }

    /**
     * Builds {@link List} object filled by {@link SupportMail} objects by parsing {@link ResultSet} object.
     *
     * @param resultSet {@link ResultSet} object to parse
     * @return parsed {@link List} object or null
     * @throws SQLException if the columnLabel is not valid; if a database access error occurs or this method is called
     *                      on a closed result set
     * @see #buildMail(ResultSet)
     */
    private List<SupportMail> buildMailList(ResultSet resultSet) throws SQLException {
        List<SupportMail> mailList = new ArrayList<>();
        while (resultSet.next()) {
            mailList.add(buildMail(resultSet));
        }
        return mailList;
    }

}
