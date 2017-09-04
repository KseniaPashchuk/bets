package com.epam.bets.receiver;


import com.epam.bets.exception.DaoException;
import com.epam.bets.exception.ReceiverException;
import com.epam.bets.request.RequestContent;

import javax.servlet.http.Part;

/**
 * The class provides Receiver interface layer for load action .
 *
 * @author Pashchuk Ksenia
 */
public interface LoadReceiver extends Receiver {
    /**
     * Provides loading user avatar operation
     *
     * @param requestContent - load info
     * @throws ReceiverException if {@link DaoException} occurred while working
     */
    void updateAvatar(RequestContent requestContent) throws ReceiverException;

    /**
     * Provides loading news picture operation
     *
     * @param requestContent - load info
     * @throws ReceiverException if {@link DaoException} occurred while working
     */
    void updateNewsPicture(RequestContent requestContent) throws ReceiverException;
}
