package com.epam.bets.receiver;

import com.epam.bets.entity.SupportMail;
import com.epam.bets.exception.DaoException;
import com.epam.bets.exception.ReceiverException;
import com.epam.bets.request.RequestContent;

import java.util.List;

/**
 * The class provides Receiver interface layer for action with {@link SupportMail}.
 *
 * @author Pashchuk Ksenia
 */
public interface SupportMailReceiver extends Receiver {

    /**
     * Provides sending support mail operation
     *
     * @param requestContent - mail info
     * @throws ReceiverException if {@link DaoException} occurred while working
     */
    void sendSupportMail(RequestContent requestContent) throws ReceiverException;

    /**
     * Provides showing suupot mail chat operation
     *
     * @param requestContent - mail info
     * @throws ReceiverException if {@link DaoException} occurred while working
     */
    void showSupportMailChat(RequestContent requestContent) throws ReceiverException;

    /**
     * Provides showing last mail of all users operation for admin
     *
     * @param requestContent - mail info
     * @return {@link List} of {@link SupportMail} object or empty {@link List}
     * @throws ReceiverException if {@link DaoException} occurred while working
     */
    List<SupportMail> showLastUsersMail(RequestContent requestContent) throws ReceiverException;


}
