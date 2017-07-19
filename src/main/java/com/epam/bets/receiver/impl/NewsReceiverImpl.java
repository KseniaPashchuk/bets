package com.epam.bets.receiver.impl;

import com.epam.bets.dao.NewsDAO;
import com.epam.bets.dao.impl.NewsDAOImpl;
import com.epam.bets.entity.News;
import com.epam.bets.exception.DaoException;
import com.epam.bets.exception.ReceiverException;
import com.epam.bets.pool.ConnectionPool;
import com.epam.bets.pool.ProxyConnection;
import com.epam.bets.receiver.NewsReceiver;

import java.time.LocalDate;
import java.util.List;

public class NewsReceiverImpl implements NewsReceiver {

    @Override
    public List<News> showAllNews(LocalDate date) throws ReceiverException {
        List<News> newsList;
        ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
        try (NewsDAO newsDAO = new NewsDAOImpl(connection)) {
            newsList = newsDAO.findNewsByDate(date);
        } catch (DaoException e) {
            throw new ReceiverException(e); //TODO
        }
        return newsList;
    }

    @Override
    public News showPieceOfNews(String title) throws ReceiverException {
        News news;
        ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
        try (NewsDAO newsDAO = new NewsDAOImpl(connection)) {
            news = newsDAO.findNewsByTitle(title);
        } catch (DaoException e) {
            throw new ReceiverException(e); //TODO
        }
        return news;
    }
}
