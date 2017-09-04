package com.epam.bets.receiver.impl;


import com.epam.bets.dao.DaoFactory;
import com.epam.bets.dao.MailDAO;
import com.epam.bets.dao.TransactionManager;
import com.epam.bets.dao.impl.MailDAOImpl;
import com.epam.bets.entity.SupportMail;
import com.epam.bets.entity.User;
import com.epam.bets.exception.ReceiverException;
import com.epam.bets.receiver.MatchReceiver;
import com.epam.bets.exception.DaoException;
import com.epam.bets.exception.ReceiverException;
import com.epam.bets.receiver.SupportMailReceiver;
import com.epam.bets.request.RequestContent;
import com.epam.bets.validator.MailValidator;
import com.epam.bets.validator.UserValidator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.epam.bets.constant.ErrorConstant.MailError.SEND_SUPPORT_MAIL_ERROR;
import static com.epam.bets.constant.ErrorConstant.MailError.SHOW_SUPPORT_CHAT_ERROR;
import static com.epam.bets.constant.RequestParamConstant.MailParam.*;
import static com.epam.bets.constant.RequestParamConstant.UserParam.*;
import static com.epam.bets.constant.ErrorConstant.ERROR_LIST_NAME;

/**
 * The class provides {@link SupportMailReceiver} implementation.
 *
 * @author Pashchuk Ksenia
 */
public class SupportMailReceiverImpl implements SupportMailReceiver {

    /**
     * Provides sending support mail operation
     *
     * @param requestContent - mail info
     * @throws ReceiverException if {@link DaoException} occurred while working
     * @see MailDAO
     * @see MailValidator
     * @see TransactionManager
     */
    @Override
    public void sendSupportMail(RequestContent requestContent) throws ReceiverException {
        List<String> errors = new ArrayList<>();
        SupportMail mail = new SupportMail();
        mail.setUserEmail(requestContent.findParameterValue(PARAM_NAME_EMAIL));
        mail.setMailDate(LocalDateTime.now());
        String userRole = requestContent.findSessionAttribute(PARAM_NAME_ROLE).toString();
        if (userRole.equalsIgnoreCase("ADMIN")) {
            mail.setType(SupportMail.MailType.IN);
        } else {
            mail.setType(SupportMail.MailType.OUT);
        }
        mail.setMailText(requestContent.findParameterValue(PARAM_NAME_MAIL_TEXT));
        MailDAO mailDAO = new MailDAOImpl();
        TransactionManager manager = new TransactionManager();
        manager.beginTransaction(mailDAO);
        if (new MailValidator().validateMailText(mail.getMailText(), errors)) {
            try {
                int mailIdx = mailDAO.create(mail);
                if (mailIdx != 0) {
                    manager.commit();
                } else {
                    manager.rollback();
                    errors.add(SEND_SUPPORT_MAIL_ERROR);
                }
                manager.commit();
            } catch (DaoException e) {
                manager.rollback();
                throw new ReceiverException(e);
            } finally {
                manager.close();
            }
        }
        if (!errors.isEmpty()) {
            requestContent.insertRequestAttribute(ERROR_LIST_NAME, errors);
        }
    }

    /**
     * Provides showing suupot mail chat operation
     *
     * @param requestContent - mail info
     * @throws ReceiverException if {@link DaoException} occurred while working
     * @see DaoFactory
     * @see MailDAO
     */
    @Override
    public List<SupportMail> showLastUsersMail(RequestContent requestContent) throws ReceiverException {
        List<SupportMail> userEmails;
        try (DaoFactory factory = new DaoFactory()) {
            MailDAO mailDAO = factory.getMailDao();
            userEmails = mailDAO.findLastUsersMail();
            return userEmails;
        } catch (DaoException e) {
            throw new ReceiverException(e);
        }
    }

    /**
     * Provides showing last mail of all users operation for admin
     *
     * @param requestContent - mail info
     * @return {@link List} of {@link SupportMail} object or empty {@link List}
     * @throws ReceiverException if {@link DaoException} occurred while working
     * @see DaoFactory
     * @see MailDAO
     */
    @Override
    public void showSupportMailChat(RequestContent requestContent) throws ReceiverException {
        List<SupportMail> mail;
        List<String> errors = new ArrayList<>();
        String email;
        if (requestContent.findSessionAttribute(PARAM_NAME_ROLE).toString().equalsIgnoreCase("ADMIN")) {
            email = requestContent.findParameterValue(PARAM_NAME_EMAIL);
        } else {
            email = (String) requestContent.findSessionAttribute(PARAM_NAME_LOGIN);
        }
        try (DaoFactory factory = new DaoFactory()) {
            MailDAO mailDAO = factory.getMailDao();
            mail = mailDAO.findAllUserMail(email);
            if (mail != null && !mail.isEmpty()) {
                requestContent.insertRequestAttribute(PARAM_NAME_ALL_MAIL, mail);
            } else {
                errors.add(SHOW_SUPPORT_CHAT_ERROR);
                requestContent.insertRequestAttribute(ERROR_LIST_NAME, errors);
            }
        } catch (DaoException e) {
            throw new ReceiverException(e);
        }
    }
}
