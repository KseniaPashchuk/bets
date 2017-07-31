package com.epam.bets.dao.impl;

import com.epam.bets.dao.GainCoefficientDAO;
import com.epam.bets.entity.BetType;
import com.epam.bets.entity.GainCoefficient;
import com.epam.bets.entity.Match;
import com.epam.bets.exception.DaoException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GainCoefficientDAOImpl extends GainCoefficientDAO {

    private static final String SELECT_GAIN_COEFFICIENTS_BY_MATCH_ID = "SELECT football_match_id, coefficient, bet_type FROM" +
            " gain_coefficient JOIN bet_type ON gain_coefficient.bet_type_id= bet_type.bet_type_id " +
            "WHERE football_match_id=?";

    private static final String SELECT_GAIN_COEFFICIENT = "SELECT football_match_id, coefficient, bet_type FROM" +
            " gain_coefficient JOIN bet_type ON gain_coefficient.bet_type_id= bet_type.bet_type_id " +
            "WHERE football_match_id=? AND bet_type_id=?";

    private static final String UPDATE_GAIN_COEFFICIENT = "UPDATE gain_coefficient SET coefficient=? " +
            "WHERE match_id=? AND football_match_id=?";

    private static final String CREATE_GAIN_COEFFICIENT = "INSERT INTO gain_coefficient (football_match_id," +
            " coefficient, bet_type_id) VALUES( ?, ?, ?)";

    @Override
    public List<GainCoefficient> findAll() throws DaoException {
        return null;
    }

    @Override
    public GainCoefficient findEntityById(int id) throws DaoException {
        return null;
    }

    @Override
    public boolean delete(int id) throws DaoException {
        return false;
    }

    @Override
    public int create(GainCoefficient entity) throws DaoException {
        return 0;
    }

    @Override
    public boolean update(GainCoefficient entity) throws DaoException {
        return false;
    }

    @Override
    public List<GainCoefficient> findMatchCoefficients(int matchId) throws DaoException {
        List<GainCoefficient> coefficients;
        try (PreparedStatement statementNews = connection.prepareStatement(SELECT_GAIN_COEFFICIENTS_BY_MATCH_ID)) {
            statementNews.setInt(1, matchId);
            ResultSet resultSet = statementNews.executeQuery();
            coefficients = buildCoefficientList(resultSet);
        } catch (SQLException e) {
            throw new DaoException("Can't find coefficients", e);
        }
        return coefficients;
    }
    private GainCoefficient buildCoefficient(ResultSet resultSet) throws SQLException {
        GainCoefficient coefficient = new GainCoefficient();
        coefficient.setFootballMatchId(resultSet.getInt(PARAM_NAME_FOOTBALL_MATCH_ID));
        coefficient.setCoefficient(resultSet.getBigDecimal(PARAM_NAME_GAIN_COEFFICIENT));
        coefficient.setBetType(BetType.valueOf(resultSet.getString(PARAM_NAME_BET_TYPE).toUpperCase()));
        return coefficient;
    }

    private List<GainCoefficient> buildCoefficientList(ResultSet resultSet) throws SQLException {
        List<GainCoefficient> coefficients = new ArrayList<>();
        while (resultSet.next()) {
            coefficients.add(buildCoefficient(resultSet));
        }
        return coefficients;
    }

}
