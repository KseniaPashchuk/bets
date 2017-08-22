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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.epam.bets.constant.ErrorConstant.*;
import static com.epam.bets.constant.RequestParamConstant.CommonParam.DATE_PATTERN;
import static com.epam.bets.constant.RequestParamConstant.CommonParam.PARAM_NAME_DATE;
import static com.epam.bets.constant.RequestParamConstant.NewsParam.*;

public class NewsReceiverImpl implements NewsReceiver {




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

    @Override
    public void showPieceOfNews(RequestContent requestContent) throws ReceiverException {
        String title = requestContent.findParameterValue(PARAM_NAME_TITLE);
        Map<String, String> errors = new HashMap<>();
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
                errors.put(ERROR, SHOW_PIECE_OF_NEWS_ERROR_MESSAGE);
                requestContent.insertRequestAttribute(ERROR_MAP_NAME, errors);
            }
        } catch (DaoException e) {
            throw new ReceiverException(e);
        }

    }

    @Override
    public void showLastNews(RequestContent requestContent) throws ReceiverException {
        List<News> newsList;
        Map<String, String> errors = new HashMap<>();
        try (DaoFactory factory = new DaoFactory()) {
            NewsDAO newsDAO = factory.getNewsDao();
            newsList = newsDAO.findLastNews();
            if (newsList == null || newsList.isEmpty()) {
                errors.put(ERROR, SHOW_PIECE_OF_NEWS_ERROR_MESSAGE);
                requestContent.insertRequestAttribute(ERROR_MAP_NAME, errors);
            } else {
                requestContent.insertRequestAttribute(PARAM_NAME_LAST_NEWS, newsList);
            }
        } catch (DaoException e) {
            throw new ReceiverException(e);
        }
    }

    @Override
    public void createNews(RequestContent requestContent) throws ReceiverException {
        News news = new News();
        news.setTitle(requestContent.findParameterValue(PARAM_NAME_TITLE));
        news.setText(requestContent.findParameterValue(PARAM_NAME_TEXT));
        news.setDate(LocalDate.now());
        Map<String, String> errors = new HashMap<>();
        if(isValidNewsParams(news, errors)) {
            int newsIndex;
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
                    errors.put(ERROR, CREATE_NEWS_ERROR_MESSAGE);
                }
            } catch (DaoException e) {
                manager.rollback();
                throw new ReceiverException(e);
            } finally {
                manager.close();
            }
        }
        if(!errors.isEmpty()){
            requestContent.insertRequestAttribute(ERROR_MAP_NAME, errors);
        }
    }

    @Override
    public void deleteNews(RequestContent requestContent) throws ReceiverException {

        String title = requestContent.findParameterValue(PARAM_NAME_TITLE);
        Map<String, String> errors = new HashMap<>();
        NewsDAO newsDAO = new NewsDAOImpl();
        TransactionManager manager = new TransactionManager();
        manager.beginTransaction(newsDAO);
        try {
            if (newsDAO.deleteByTitle(title)) {
                manager.commit();
            } else {
                manager.rollback();
                errors.put(ERROR, DELETE_NEWS_ERROR_MESSAGE);
                requestContent.insertRequestAttribute(ERROR_MAP_NAME, errors);
            }
        } catch (DaoException e) {
            manager.rollback();
            throw new ReceiverException(e);
        } finally {
            manager.close();
        }

    }

    @Override
    public void editNews(RequestContent requestContent) throws ReceiverException {
        News news = new News();
        news.setId(Integer.parseInt(requestContent.findParameterValue(PARAM_NAME_NEWS_ID)));
        news.setTitle(requestContent.findParameterValue(PARAM_NAME_TITLE));
        news.setText(requestContent.findParameterValue(PARAM_NAME_TEXT));
        news.setPictureUrl(requestContent.findParameterValue(PARAM_NAME_PICTURE));
        Map<String, String> errors = new HashMap<>();
        if (isValidNewsParams(news, errors)) {
            NewsDAO newsDAO = new NewsDAOImpl();
            TransactionManager manager = new TransactionManager();
            manager.beginTransaction(newsDAO);
            try {
                if (newsDAO.update(news)) {
                    manager.commit();
                } else {
                    manager.rollback();
                    errors.put(ERROR, EDIT_NEWS_ERROR_MESSAGE);
                }
            } catch (DaoException e) {
                manager.rollback();
                throw new ReceiverException(e);
            } finally {
                manager.close();
            }
        }
        if(!errors.isEmpty()){
            requestContent.insertRequestAttribute(ERROR_MAP_NAME, errors);
        }
    }

    private boolean isValidNewsParams(News news, Map<String, String> errors) {
        boolean isValid = true;
        if (news.getTitle() == null || news.getTitle().isEmpty()) {
            isValid = false;
            errors.put(INVALID_NEWS_TITLE_ERROR, INVALID_NEWS_TITLE_MESSAGE);
        }
        if (news.getText() == null || news.getText().isEmpty()) {
            isValid = false;
            errors.put(INVALID_NEWS_TEXT_ERROR, INVALID_NEWS_TEXT_MESSAGE);
        }
        return isValid;
    }
}
