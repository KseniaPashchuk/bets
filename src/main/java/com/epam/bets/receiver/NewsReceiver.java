package com.epam.bets.receiver;


import com.epam.bets.entity.News;
import com.epam.bets.exception.DaoException;
import com.epam.bets.exception.ReceiverException;
import com.epam.bets.request.RequestContent;

import java.util.List;

/**
 * The class provides Receiver interface layer for action with {@link News}.
 *
 * @author Pashchuk Ksenia
 */
public interface NewsReceiver extends Receiver {
    /**
     * Provides showing all news operation
     *
     * @param requestContent - user info
     * @return {@link List} of {@link News} object or empty {@link List}
     * @throws ReceiverException if {@link DaoException} occurred while working
     */
    List<News> showAllNews(RequestContent requestContent) throws ReceiverException;

    /**
     * Provides showing piece of news operation
     *
     * @param requestContent - news info
     * @throws ReceiverException if {@link DaoException} occurred while working
     */
    void showPieceOfNews(RequestContent requestContent) throws ReceiverException;

    /**
     * Provides showing last news operation
     *
     * @param requestContent - news info
     * @throws ReceiverException if {@link DaoException} occurred while working
     */
    void showLastNews(RequestContent requestContent) throws ReceiverException;

    /**
     * Provides creating news operation for admin
     *
     * @param requestContent - news info
     * @throws ReceiverException if {@link DaoException} occurred while working
     */
    void createNews(RequestContent requestContent) throws ReceiverException;

    /**
     * Provides deleting news operation for admin
     *
     * @param requestContent - news info
     * @throws ReceiverException if {@link DaoException} occurred while working
     */
    void deleteNews(RequestContent requestContent) throws ReceiverException;

    /**
     * Provides editing news operation for admin
     *
     * @param requestContent - news info
     * @throws ReceiverException if {@link DaoException} occurred while working
     */
    void editNews(RequestContent requestContent) throws ReceiverException;
}
