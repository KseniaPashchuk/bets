package com.epam.bets.dao;

import com.epam.bets.entity.News;
import com.epam.bets.exception.DaoException;
import com.epam.bets.pool.ProxyConnection;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
/**
 * The class provides DAO abstraction for {@link News} objects.
 *
 * @author Pashchuk Ksenia
 * @see AbstractDAO
 */
public abstract class NewsDAO extends AbstractDAO<News> {

    protected static final String PARAM_NAME_ID = "news_id";
    protected static final String PARAM_NAME_TITLE = "title";
    protected static final String PARAM_NAME_DATE = "news_date";
    protected static final String PARAM_NAME_TEXT = "text";
    protected static final String PARAM_NAME_PICTURE = "pic_url";

    protected NewsDAO() {
    }

    protected NewsDAO(ProxyConnection connection) {
        super(connection);
    }

    /**
     * Takes  {@link List} of all {@link News}
     *
     * @return taken {@link List} of all {@link News} object or empty {@link List}
     * @throws DaoException if {@link SQLException} occurred while working with database
     */
    public abstract List<News> findAllNews() throws DaoException;

    /**
     * Takes {@link News} object by title
     *
     * @return taken {@link News} object
     * @throws DaoException if {@link SQLException} occurred while working with database
     */
    public abstract News findNewsByTitle(String title) throws DaoException;

    /**
     * Takes  {@link List} of {@link News} by news date
     *
     * @param date - news date
     * @return taken {@link List} of  {@link News} object or empty {@link List}
     * @throws DaoException if {@link SQLException} occurred while working with database
     */
    public abstract List<News> findNewsByDate(LocalDate date) throws DaoException;

    /**
     * Takes  {@link List} of last {@link News}
     *
     * @return taken {@link List} of last {@link News} object or empty {@link List}
     * @throws DaoException if {@link SQLException} occurred while working with database
     */
    public abstract List<News> findLastNews() throws DaoException;

    /**
     * Deletes {@link News} by news title.
     *
     * @param title - news title
     * @return true if successfully deleted
     * @throws DaoException if {@link SQLException} occurred while working with database
     */
    public abstract boolean deleteByTitle(String title) throws DaoException;


    /**
     * Update {@link News} picture
     *
     * @param id         -   news id
     * @param pictureUrl - new picture url
     * @return true if successfully updates
     * @throws DaoException if {@link SQLException} occurred while working with database
     */
    public abstract boolean updatePicture(int id, String pictureUrl) throws DaoException;
}
