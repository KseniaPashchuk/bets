package com.epam.bets.receiver;

import com.epam.bets.entity.FAQ;
import com.epam.bets.exception.DaoException;
import com.epam.bets.exception.ReceiverException;
import com.epam.bets.request.RequestContent;

/**
 * The class provides Receiver interface layer for action with {@link FAQ}.
 *
 * @author Pashchuk Ksenia
 */
public interface FAQReceiver extends Receiver {

    /**
     * Provides showing all faq questions operation
     *
     * @param requestContent - faq info
     * @throws ReceiverException if {@link DaoException} occurred while working
     */
    void showAllFAQ(RequestContent requestContent) throws ReceiverException;
    /**
     * Provides creating faq operation for admin
     *
     * @param requestContent - faq info
     * @throws ReceiverException if {@link DaoException} occurred while working
     */
    void createFAQ(RequestContent requestContent) throws ReceiverException;

    /**
     * Provides deleting faq operation for admin
     *
     * @param requestContent - faq info
     * @throws ReceiverException if {@link DaoException} occurred while working
     */
    void deleteFAQ(RequestContent requestContent) throws ReceiverException;
    /**
     * Provides editing faq operation for admin
     *
     * @param requestContent - faq info
     * @throws ReceiverException if {@link DaoException} occurred while working
     */
    void editFAQ(RequestContent requestContent) throws ReceiverException;
}
