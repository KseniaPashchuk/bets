package com.epam.bets.dao.impl;

import com.epam.bets.dao.NewsDAO;
import com.epam.bets.entity.News;
import com.epam.bets.exception.DaoException;
import com.epam.bets.pool.ProxyConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
/**
 * The class provides {@link NewsDAO} implementation.
 *
 * @author Pashchuk Ksenia
 */
public class NewsDAOImpl extends NewsDAO {

    private static final String SELECT_ALL_NEWS =
            "SELECT news_id, title, news_date, text, pic_url FROM news";
    private static final String SELECT_NEWS_BY_ID =
            "SELECT news_id, title, news_date, text, pic_url FROM news WHERE news_id=?";
    private static final String SELECT_NEWS_BY_TITLE =
            "SELECT news_id, title, news_date, text, pic_url FROM news WHERE title=?";
    private static final String SELECT_NEWS_BY_DATE =
            "SELECT news_id, title, news_date, text, pic_url FROM news WHERE news_date=?";
    private static final String SELECT_LAST_NEWS = "SELECT news_id, title, news_date, text, pic_url FROM " +
            "news order by news_date desc limit 3";
    private static final String DELETE_NEWS_BY_ID = "DELETE FROM news WHERE news_id=?";
    private static final String DELETE_NEWS_BY_TITLE = "DELETE FROM news WHERE title=?";
    private static final String CREATE_NEWS = "INSERT INTO news (news_id, title, news_date, text, pic_url)" +
            " VALUES( NULL, ?, ?, ?, ?)";
    private static final String UPDATE_NEWS = "UPDATE news SET title=?, text=? WHERE news_id=?";
    private static final String UPDATE_PICTURE = "UPDATE news SET pic_url=? WHERE news_id=?";


    public NewsDAOImpl() {
    }

    public NewsDAOImpl(ProxyConnection connection) {
        super(connection);
    }

    /**
     * Takes  {@link List} of all {@link News}
     *
     * @return taken {@link List} of all {@link News} object or empty {@link List}
     * @throws DaoException if {@link SQLException} occurred while working with database
     * @see PreparedStatement
     * @see ResultSet
     */
    @Override
    public List<News> findAllNews() throws DaoException {
        List<News> newsList;
        try (PreparedStatement statementNews = connection.prepareStatement(SELECT_ALL_NEWS)) {
            ResultSet resultSet = statementNews.executeQuery();
            newsList = buildNewsList(resultSet);
        } catch (SQLException e) {
            throw new DaoException("Can't find all news", e);
        }
        return newsList;
    }
    /**
     * Takes {@link News} object by title
     *
     * @return taken {@link News} object
     * @throws DaoException if {@link SQLException} occurred while working with database
     * @see PreparedStatement
     * @see ResultSet
     */
    @Override
    public News findNewsByTitle(String title) throws DaoException {
        News news = null;
        try (PreparedStatement statementNews = connection.prepareStatement(SELECT_NEWS_BY_TITLE)) {
            statementNews.setString(1, title);
            ResultSet resultSet = statementNews.executeQuery();
            if (resultSet.next()) {
                news = buildNews(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException("Can't find news by given title", e);
        }
        return news;
    }

    /**
     * Takes  {@link List} of {@link News} by news date
     *
     * @param date - news date
     * @return taken {@link List} of  {@link News} object or empty {@link List}
     * @throws DaoException if {@link SQLException} occurred while working with database
     * @see PreparedStatement
     * @see ResultSet
     */
    @Override
    public List<News> findNewsByDate(LocalDate date) throws DaoException {
        List<News> newsList;
        try (PreparedStatement statementNews = connection.prepareStatement(SELECT_NEWS_BY_DATE)) {
            statementNews.setDate(1, java.sql.Date.valueOf(date));
            ResultSet resultSet = statementNews.executeQuery();
            newsList = buildNewsList(resultSet);
        } catch (SQLException e) {
            throw new DaoException("Can't find news by given date", e);
        }
        return newsList;
    }

    /**
     * Takes  {@link List} of last {@link News}
     *
     * @return taken {@link List} of last {@link News} object or empty {@link List}
     * @throws DaoException if {@link SQLException} occurred while working with database
     * @see PreparedStatement
     * @see ResultSet
     */
    @Override
    public List<News> findLastNews() throws DaoException {
        List<News> newsList;
        try (PreparedStatement statementNews = connection.prepareStatement(SELECT_LAST_NEWS)) {
            ResultSet resultSet = statementNews.executeQuery();
            newsList = buildNewsList(resultSet);
        } catch (SQLException e) {
            throw new DaoException("Can't find last news ", e);
        }
        return newsList;
    }
    /**
     * Deletes {@link News} by news title.
     *
     * @param title - news title
     * @return true if successfully deleted
     * @throws DaoException if {@link SQLException} occurred while working with database
     * @see PreparedStatement
     * @see ResultSet
     */
    @Override
    public boolean deleteByTitle(String title) throws DaoException {
        try (PreparedStatement statementNews = connection.prepareStatement(DELETE_NEWS_BY_TITLE)) {
            statementNews.setString(1, title);
            statementNews.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DaoException("Can't delete news", e);
        }
    }
    /**
     * Update {@link News} picture
     *
     * @param id         -   news id
     * @param pictureUrl - new picture url
     * @return true if successfully updates
     * @throws DaoException if {@link SQLException} occurred while working with database
     * @see PreparedStatement
     * @see ResultSet
     */
    @Override
    public boolean updatePicture(int id, String pictureUrl) throws DaoException {
        try (PreparedStatement statementUser = connection.prepareStatement(UPDATE_PICTURE)) {
            statementUser.setString(1, pictureUrl);
            statementUser.setInt(2, id);
            statementUser.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DaoException("Can't update picture", e);
        }
    }
    /**
     * Create user {@link News}
     *
     * @param entity news info
     * @return id of created news
     * @throws DaoException if {@link SQLException} occurred while working with database
     * @see PreparedStatement
     * @see ResultSet
     */
    @Override
    public int create(News entity) throws DaoException {
        try (PreparedStatement statementNews = connection.prepareStatement(CREATE_NEWS, Statement.RETURN_GENERATED_KEYS)) {
            statementNews.setString(1, entity.getTitle());
            statementNews.setDate(2, java.sql.Date.valueOf(entity.getDate()));
            statementNews.setString(3, entity.getText());
            statementNews.setString(4, entity.getPictureUrl());
            statementNews.executeUpdate();
            ResultSet generatedKey = statementNews.getGeneratedKeys();
            if (generatedKey.next()) {
                return generatedKey.getInt(1);
            }
        } catch (SQLException e) {
            if(e.getErrorCode() == EXISTING_ENTITY_ERROR_CODE){
                return 0;
            }
            throw new DaoException("Can't create news", e);
        }
        return 0;
    }
    /**
     * Updates user {@link News}
     *
     * @param news news info
     * @return true if successfully updated
     * @throws DaoException if {@link SQLException} occurred while working with database
     * @see PreparedStatement
     * @see ResultSet
     */
    @Override
    public boolean update(News news) throws DaoException {
        try (PreparedStatement statementNews = connection.prepareStatement(UPDATE_NEWS)) {
            statementNews.setString(1, news.getTitle());
            statementNews.setString(2, news.getText());
            statementNews.setInt(3, news.getId());
            statementNews.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DaoException("Can't update news", e);
        }
    }
    /**
     * Builds {@link News} object by parsing {@link ResultSet} object.
     *
     * @param resultSet {@link ResultSet} object to parse
     * @return parsed {@link News} object or null
     * @throws SQLException if the columnLabel is not valid; if a database access error occurs or this method is called
     *                      on a closed result set
     */
    private News buildNews(ResultSet resultSet) throws SQLException {
        News news = new News();
        news.setId(resultSet.getInt(PARAM_NAME_ID));
        news.setTitle(resultSet.getString(PARAM_NAME_TITLE));
        news.setDate(resultSet.getDate(PARAM_NAME_DATE).toLocalDate());
        news.setText(resultSet.getString(PARAM_NAME_TEXT));
        news.setPictureUrl(resultSet.getString(PARAM_NAME_PICTURE));
        return news;
    }
    /**
     * Builds {@link List} object filled by {@link News} objects by parsing {@link ResultSet} object.
     *
     * @param resultSet {@link ResultSet} object to parse
     * @return parsed {@link List} object or null
     * @throws SQLException if the columnLabel is not valid; if a database access error occurs or this method is called
     *                      on a closed result set
     * @see #buildNews(ResultSet)
     */
    private List<News> buildNewsList(ResultSet resultSet) throws SQLException {
        List<News> newsList = new ArrayList<>();
        while (resultSet.next()) {
            newsList.add(buildNews(resultSet));
        }
        return newsList;
    }
}
