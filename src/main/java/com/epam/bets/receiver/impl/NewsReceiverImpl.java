package com.epam.bets.receiver.impl;

import com.epam.bets.dao.DaoFactory;
import com.epam.bets.dao.NewsDAO;
import com.epam.bets.dao.TransactionManager;
import com.epam.bets.dao.impl.NewsDAOImpl;
import com.epam.bets.entity.News;
import com.epam.bets.exception.DaoException;
import com.epam.bets.exception.ReceiverException;
import com.epam.bets.receiver.NewsReceiver;
import com.epam.bets.request.RequestContent;
import com.epam.bets.validator.NewsValidator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.epam.bets.constant.ErrorConstant.*;
import static com.epam.bets.constant.ErrorConstant.NewsError.*;
import static com.epam.bets.constant.RequestParamConstant.CommonParam.DATE_PATTERN;
import static com.epam.bets.constant.RequestParamConstant.CommonParam.PARAM_NAME_DATE;
import static com.epam.bets.constant.RequestParamConstant.NewsParam.*;

/**
 * The class provides {@link NewsReceiver} implementation.
 *
 * @author Pashchuk Ksenia
 */
public class NewsReceiverImpl implements NewsReceiver {
    /**
     * Provides showing all news operation
     *
     * @param requestContent - user info
     * @return {@link List} of {@link News} object or empty {@link List}
     * @throws ReceiverException if {@link DaoException} occurred while working
     * @see NewsDAO
     * @see DaoFactory
     */
    @Override
    public List<News> showAllNews(RequestContent requestContent) throws ReceiverException {
        List<News> newsList;
        String dateString = requestContent.findParameterValue(PARAM_NAME_DATE);
        LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern(DATE_PATTERN));
        try (DaoFactory factory = new DaoFactory()) {
            NewsDAO newsDAO = factory.getNewsDao();
            newsList = newsDAO.findNewsByDate(date);
        } catch (DaoException e) {
            throw new ReceiverException(e);
        }
        return newsList;
    }

    /**
     * Provides showing piece of news operation
     *
     * @param requestContent - news info
     * @throws ReceiverException if {@link DaoException} occurred while working
     * @see NewsDAO
     * @see DaoFactory
     */
    @Override
    public void showPieceOfNews(RequestContent requestContent) throws ReceiverException {
        String title = requestContent.findParameterValue(PARAM_NAME_TITLE);
        List<String> errors = new ArrayList<>();
        if(new NewsValidator().validateNewsTitle(title, errors)) {
            try (DaoFactory factory = new DaoFactory()) {
                NewsDAO newsDAO = factory.getNewsDao();
                News news = newsDAO.findNewsByTitle(title);
                if (news != null) {
                    requestContent.insertRequestAttribute(PARAM_NAME_NEWS_ID, news.getId());
                    requestContent.insertRequestAttribute(PARAM_NAME_TITLE, news.getTitle());
                    requestContent.insertRequestAttribute(PARAM_NAME_DATE, news.getDate());
                    requestContent.insertRequestAttribute(PARAM_NAME_TEXT, news.getText());
                    requestContent.insertRequestAttribute(PARAM_NAME_PICTURE, news.getPictureUrl());
                } else {
                    errors.add(SHOW_PIECE_OF_NEWS_ERROR);
                }
            } catch (DaoException e) {
                throw new ReceiverException(e);
            }
        }if (!errors.isEmpty()) {
            requestContent.insertRequestAttribute(ERROR_LIST_NAME, errors);
        }
    }

    /**
     * Provides showing last news operation
     *
     * @param requestContent - news info
     * @throws ReceiverException if {@link DaoException} occurred while working
     * @see NewsDAO
     * @see DaoFactory
     */
    @Override
    public void showLastNews(RequestContent requestContent) throws ReceiverException {
        List<News> newsList;
        List<String> errors = new ArrayList<>();
        try (DaoFactory factory = new DaoFactory()) {
            NewsDAO newsDAO = factory.getNewsDao();
            newsList = newsDAO.findLastNews();
            if (newsList == null || newsList.isEmpty()) {
                errors.add(SHOW_LAST_NEWS_ERROR);
                requestContent.insertRequestAttribute(ERROR_LIST_NAME, errors);
            } else {
                requestContent.insertRequestAttribute(PARAM_NAME_LAST_NEWS, newsList);
            }
        } catch (DaoException e) {
            throw new ReceiverException(e);
        }
    }

    /**
     * Provides creating news operation for admin
     *
     * @param requestContent - news info
     * @throws ReceiverException if {@link DaoException} occurred while working
     * @see NewsDAO
     * @see TransactionManager
     * @see NewsValidator
     */
    @Override
    public void createNews(RequestContent requestContent) throws ReceiverException {
        News news = new News();
        int newsIndex;
        List<String> errors = new ArrayList<>();
        if (new NewsValidator().validateNewsParams(requestContent, errors)) {
            news.setTitle(requestContent.findParameterValue(PARAM_NAME_TITLE));
            news.setText(requestContent.findParameterValue(PARAM_NAME_TEXT));
            news.setDate(LocalDate.now());
            NewsDAO newsDAO = new NewsDAOImpl();
            TransactionManager manager = new TransactionManager();
            manager.beginTransaction(newsDAO);
            try {
                newsIndex = newsDAO.create(news);
                if (newsIndex != 0) {
                    manager.commit();
                    requestContent.insertRequestAttribute(PARAM_NAME_NEWS_ID, newsIndex);
                } else {
                    manager.rollback();
                    errors.add(CREATE_NEWS_ERROR);
                }
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
     * Provides deleting news operation for admin
     *
     * @param requestContent - news info
     * @throws ReceiverException if {@link DaoException} occurred while working
     * @see NewsDAO
     * @see TransactionManager
     */
    @Override
    public void deleteNews(RequestContent requestContent) throws ReceiverException {

        String title = requestContent.findParameterValue(PARAM_NAME_TITLE);
        List<String> errors = new ArrayList<>();
        if(new NewsValidator().validateNewsTitle(title, errors)) {
            NewsDAO newsDAO = new NewsDAOImpl();
            TransactionManager manager = new TransactionManager();
            manager.beginTransaction(newsDAO);
            try {
                if (newsDAO.deleteByTitle(title)) {
                    manager.commit();
                } else {
                    manager.rollback();
                    errors.add(DELETE_NEWS_ERROR);
                }
            } catch (DaoException e) {
                manager.rollback();
                throw new ReceiverException(e);
            } finally {
                manager.close();
            }
        }if (!errors.isEmpty()) {
            requestContent.insertRequestAttribute(ERROR_LIST_NAME, errors);
        }
    }

    /**
     * Provides editing news operation for admin
     *
     * @param requestContent - news info
     * @throws ReceiverException if {@link DaoException} occurred while working
     * @see NewsDAO
     * @see TransactionManager
     * @see NewsValidator
     */
    @Override
    public void editNews(RequestContent requestContent) throws ReceiverException {
        News news = new News();
        List<String> errors = new ArrayList<>();
        if (new NewsValidator().validateNewsParams(requestContent, errors)) {
            news.setId(Integer.parseInt(requestContent.findParameterValue(PARAM_NAME_NEWS_ID)));
            news.setTitle(requestContent.findParameterValue(PARAM_NAME_TITLE));
            news.setText(requestContent.findParameterValue(PARAM_NAME_TEXT));
            NewsDAO newsDAO = new NewsDAOImpl();
            TransactionManager manager = new TransactionManager();
            manager.beginTransaction(newsDAO);
            try {
                if (newsDAO.update(news)) {
                    manager.commit();
                } else {
                    manager.rollback();
                    errors.add(EDIT_NEWS_ERROR);
                }
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


}
