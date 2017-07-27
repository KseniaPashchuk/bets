package com.epam.bets.dao;

import com.epam.bets.entity.News;
import com.epam.bets.exception.DaoException;
import com.epam.bets.pool.ProxyConnection;

import java.time.LocalDate;
import java.util.List;

public abstract class NewsDAO extends  AbstractDAO<News>{

    protected static final String PARAM_NAME_ID = "news_id";
    protected static final String PARAM_NAME_TITLE = "title";
    protected static final String PARAM_NAME_DATE= "news_date";
    protected static final String PARAM_NAME_TEXT = "text";
    protected static final String PARAM_NAME_PICTURE = "pic_url";
    protected NewsDAO() {
    }

    protected NewsDAO(ProxyConnection connection) {
        super(connection);
    }

    public abstract News findNewsByTitle(String title) throws DaoException;
    public abstract List<News> findNewsByDate(LocalDate date) throws DaoException;
    public abstract boolean deleteByTitle(String title) throws DaoException;
}
