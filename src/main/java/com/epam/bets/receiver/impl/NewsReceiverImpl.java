package com.epam.bets.receiver.impl;

import com.epam.bets.dao.DaoFactory;
import com.epam.bets.dao.NewsDAO;
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
            throw new ReceiverException(e); //TODO
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
            throw new ReceiverException(e); //TODO
        }
        return news;
    }

    @Override
    public boolean createNews(News news) throws ReceiverException {
        return false;
    }

    @Override
    public boolean delete(News news) throws ReceiverException {
        return false;
    }

    @Override
    public boolean editNews(News news) throws ReceiverException {
        return false;
    }
}
