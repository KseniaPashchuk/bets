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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class NewsDAOImpl extends NewsDAO {

    private static final String SELECT_ALL_NEWS =
            "SELECT news_id, title, news_date, text, pic_url FROM news";
    private static final String SELECT_NEWS_BY_ID =
            "SELECT news_id, title, news_date, text, pic_url FROM news WHERE news_id=?";
    private static final String SELECT_NEWS_BY_TITLE =
            "SELECT news_id, title, news_date, text, pic_url FROM news WHERE title=?";
    private static final String SELECT_NEWS_BY_DATE =
            "SELECT news_id, title, news_date, text, pic_url FROM news WHERE news_date=?";
    private static final String DELETE_NEWS_BY_ID = "DELETE FROM news WHERE news_id=?";
    private static final String DELETE_NEWS_BY_TITLE = "DELETE FROM news WHERE title=?";
    private static final String CREATE_NEWS = "INSERT INTO news (news_id, title, news_date, text, pic_url)" +
            " VALUES( NULL, ?, ?, ?, ?)";
    private static final String UPDATE_NEWS = "UPDATE news SET title=?, text=?, pic_url=? WHERE news_id=?";


    public NewsDAOImpl() {
    }

    public NewsDAOImpl(ProxyConnection connection) {
        super(connection);
    }

    @Override
    public List<News> findAll() throws DaoException {
        List<News> newsList;
        try (PreparedStatement statementNews = connection.prepareStatement(SELECT_ALL_NEWS)) {
            ResultSet resultSet = statementNews.executeQuery();
            newsList = buildNewsList(resultSet);
        } catch (SQLException e) {
            throw new DaoException("Can't find all news", e);
        }
        return newsList;
    }

    @Override
    public News findEntityById(int id) throws DaoException {
        News news = null;
        try (PreparedStatement statementNews = connection.prepareStatement(SELECT_NEWS_BY_ID)) {
            statementNews.setInt(1, id);
            ResultSet resultSet = statementNews.executeQuery();
            if (resultSet.next()) {
                news = buildNews(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException("Can't find news by given id", e);
        }
        return news;
    }

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

    @Override
    public boolean delete(int id) throws DaoException {
        try (PreparedStatement statementNews = connection.prepareStatement(DELETE_NEWS_BY_ID)) {
            statementNews.setInt(1, id);
            statementNews.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DaoException("Can't delete news", e);
        }
    }

    @Override
    public int create(News entity) throws DaoException {
        try (PreparedStatement statementNews = connection.prepareStatement(CREATE_NEWS)) {
            statementNews.setString(1, entity.getTitle());
            statementNews.setDate(2, java.sql.Date.valueOf(entity.getDate()));
            statementNews.setString(3, entity.getText());
            statementNews.setString(4, entity.getPictureUrl());
            statementNews.executeUpdate();
            return 1;
        } catch (SQLException e) {
            if(e.getErrorCode() == EXISTING_ENTITY_ERROR_CODE){
                return 0;
            }
            throw new DaoException("Can't create news", e);
        }
    }

    @Override
    public boolean update(News news) throws DaoException {
        try (PreparedStatement statementNews = connection.prepareStatement(UPDATE_NEWS)) {
            statementNews.setString(1, news.getTitle());
            statementNews.setString(2, news.getText());
            statementNews.setString(3, news.getPictureUrl());
            statementNews.setInt(4, news.getId());
            statementNews.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DaoException("Can't update news", e);
        }
    }

    private News buildNews(ResultSet resultSet) throws SQLException {
        News news = new News();
        news.setId(resultSet.getInt(PARAM_NAME_ID));
        news.setTitle(resultSet.getString(PARAM_NAME_TITLE));
        news.setDate(resultSet.getDate(PARAM_NAME_DATE).toLocalDate());
        news.setText(resultSet.getString(PARAM_NAME_TEXT));
        news.setPictureUrl(resultSet.getString(PARAM_NAME_PICTURE));
        return news;
    }

    private List<News> buildNewsList(ResultSet resultSet) throws SQLException {
        List<News> newsList = new ArrayList<>();
        while (resultSet.next()) {
            newsList.add(buildNews(resultSet));
        }
        return newsList;
    }
}
