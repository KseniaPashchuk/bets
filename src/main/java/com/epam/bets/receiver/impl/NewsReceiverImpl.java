package com.epam.bets.receiver.impl;

import com.epam.bets.dao.DaoFactory;
import com.epam.bets.dao.NewsDAO;
import com.epam.bets.dao.TransactionManager;
import com.epam.bets.dao.impl.NewsDAOImpl;
import com.epam.bets.entity.News;
import com.epam.bets.exception.DaoException;
import com.epam.bets.exception.ReceiverException;
import com.epam.bets.receiver.NewsReceiver;

import java.time.LocalDate;
import java.util.List;

public class NewsReceiverImpl implements NewsReceiver {

    @Override
    public List<News> showAllNews(LocalDate date) throws ReceiverException {
        List<News> newsList;
        try (DaoFactory factory = new DaoFactory()) {
            NewsDAO newsDAO = factory.getNewsDao();
            newsList = newsDAO.findNewsByDate(date);
        } catch (DaoException e) {
            throw new ReceiverException(e);
        }
        return newsList;
    }

    @Override
    public News showPieceOfNews(String title) throws ReceiverException {
        News news;
        try (DaoFactory factory = new DaoFactory()) {
            NewsDAO newsDAO = factory.getNewsDao();
            news = newsDAO.findNewsByTitle(title);
        } catch (DaoException e) {
            throw new ReceiverException(e);
        }
        return news;
    }

    @Override
    public boolean createNews(News news) throws ReceiverException {
        boolean isNewsCreated = false;
        NewsDAO newsDAO = new NewsDAOImpl();
        TransactionManager manager = new TransactionManager();
        manager.beginTransaction(newsDAO);
        try {
            if (newsDAO.create(news) != 0) {
                isNewsCreated = true;
                manager.commit();
            } else {
                manager.rollback();
            }
        } catch (DaoException e) {
            manager.rollback();
            throw new ReceiverException(e);
        } finally {
            manager.close();
        }
        return isNewsCreated;
    }

    @Override
    public boolean deleteNews(String title) throws ReceiverException {
        boolean isNewsDeleted = false;
        NewsDAO newsDAO = new NewsDAOImpl();
        TransactionManager manager = new TransactionManager();
        manager.beginTransaction(newsDAO);
        try {
            isNewsDeleted = newsDAO.deleteByTitle(title);
            if (isNewsDeleted) {
                manager.commit();
            } else {
                manager.rollback();
            }
        } catch (DaoException e) {
            manager.rollback();
            throw new ReceiverException(e);
        } finally {
            manager.close();
        }
        return isNewsDeleted;
    }

    @Override
    public boolean editNews(News news) throws ReceiverException {
        boolean isNewsUpdated = false;
        NewsDAO newsDAO = new NewsDAOImpl();
        TransactionManager manager = new TransactionManager();
        manager.beginTransaction(newsDAO);
        try {
            isNewsUpdated = newsDAO.update(news);
            if (isNewsUpdated) {
                manager.commit();
            } else {
                manager.rollback();
            }
        } catch (DaoException e) {
            manager.rollback();
            throw new ReceiverException(e);
        } finally {
            manager.close();
        }
        return isNewsUpdated;
    }

    @Override
    public boolean editPicture(int newsId, String pictureUrl) throws ReceiverException {
        boolean isPictureUpdated = false;
        NewsDAO userDAO = new NewsDAOImpl();
        TransactionManager manager = new TransactionManager();
        manager.beginTransaction(userDAO);
        try {
            isPictureUpdated = userDAO.updatePicture(newsId, pictureUrl);
            if (isPictureUpdated) {
                manager.commit();
            } else {
                manager.rollback();
            }
        } catch (DaoException e) {
            manager.rollback();
            throw new ReceiverException(e);
        } finally {
            manager.close();
        }
        return isPictureUpdated;
    }
}
