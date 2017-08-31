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

public class MailDAOImpl extends MailDAO {
    private static final String SELECT_ALL_USER_MAIL =
            "SELECT mail_id, mail_text, mail_subject, mail_date_time, mail_type, login AS user_email FROM support_mail JOIN " +
                    "user ON support_mail.user_id = user.user_id WHERE login=? ORDER BY mail_date_time ASC";
    private static final String SELECT_ALL_USER_EMAILS =
            "SELECT login AS user_email FROM support_mail JOIN user ON support_mail.user_id = user.user_id";

    private static final String CREATE_MAIL = "INSERT INTO support_mail (mail_id, mail_text, mail_subject," +
            " mail_date_time, mail_type, user_id)" +
            " VALUES( NULL, ?, ?, ?, ?, (SELECT user_id FROM user WHERE login=?))";

    public MailDAOImpl() {
    }

    public MailDAOImpl(ProxyConnection connection) {
        super(connection);
    }

    @Override
    public List<String> findAllUserEmails() throws DaoException {
        List<String> emailList = new ArrayList<>();
        try (PreparedStatement statementMail = connection.prepareStatement(SELECT_ALL_USER_EMAILS)) {
            ResultSet resultSet = statementMail.executeQuery();
            while (resultSet.next()) {
                emailList.add(resultSet.getString(PARAM_NAME_USER_EMAIL));
            }
        } catch (SQLException e) {
            throw new DaoException("Can't find all user emails", e);
        }
        return emailList;
    }

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

    @Override
    public int create(SupportMail entity) throws DaoException {
        try (PreparedStatement statementMail = connection.prepareStatement(CREATE_MAIL)) {
            statementMail.setString(1, entity.getMailText());
            statementMail.setString(2, entity.getMailSubject());
            statementMail.setTimestamp(3, Timestamp.valueOf(entity.getMailDate()));
            statementMail.setString(4, entity.getType().toString());
            statementMail.setString(5, entity.getUserEmail());
            statementMail.executeUpdate();
            return 1;
        } catch (SQLException e) {
            throw new DaoException("Can't create mail", e);
        }
    }

    @Override
    public boolean update(SupportMail entity) throws DaoException {
        throw new UnsupportedOperationException();
    }

    private SupportMail buildMail(ResultSet resultSet) throws SQLException {
        SupportMail mail = new SupportMail();
        mail.setMailId(resultSet.getInt(PARAM_NAME_ID));
        mail.setMailSubject(resultSet.getString(PARAM_NAME_SUBJECT));
        mail.setMailText(resultSet.getString(PARAM_NAME_TEXT));
        mail.setType(SupportMail.MailType.valueOf(resultSet.getString(PARAM_NAME_TYPE)));
        mail.setMailDate((resultSet.getTimestamp(PARAM_NAME_MAIL_DATE).toLocalDateTime()));
        mail.setUserEmail(resultSet.getString(PARAM_NAME_USER_EMAIL));
        return mail;
    }

    private List<SupportMail> buildMailList(ResultSet resultSet) throws SQLException {
        List<SupportMail> mailList = new ArrayList<>();
        while (resultSet.next()) {
            mailList.add(buildMail(resultSet));
        }
        return mailList;
    }

}
