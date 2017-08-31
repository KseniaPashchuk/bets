package com.epam.bets.dao.impl;

import com.epam.bets.dao.GainCoefficientDAO;
import com.epam.bets.entity.BetType;
import com.epam.bets.entity.GainCoefficient;
import com.epam.bets.entity.Match;
import com.epam.bets.exception.DaoException;
import com.epam.bets.pool.ProxyConnection;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GainCoefficientDAOImpl extends GainCoefficientDAO {

    private static final String SELECT_GAIN_COEFFICIENTS_BY_MATCH_ID = "SELECT football_match_id, coefficient, bet_type FROM" +
            " gain_coefficient JOIN bet_type ON gain_coefficient.bet_type_id= bet_type.bet_type_id " +
            "WHERE football_match_id=?";

    private static final String SELECT_GAIN_COEFFICIENT = "SELECT football_match_id, coefficient, bet_type FROM" +
            " gain_coefficient JOIN bet_type ON gain_coefficient.bet_type_id= bet_type.bet_type_id " +
            "WHERE football_match_id=? AND bet_type_id=?";

    private static final String UPDATE_GAIN_COEFFICIENT = "UPDATE gain_coefficient SET coefficient=? " +
            "WHERE bet_type_id=(SELECT bet_type_id FROM bet_type WHERE bet_type=?) AND football_match_id=?";

    private static final String CREATE_GAIN_COEFFICIENT = "INSERT INTO gain_coefficient (football_match_id," +
            " coefficient, bet_type_id) VALUES( ?, ?, (SELECT bet_type_id FROM bet_type WHERE bet_type=?))";


    public GainCoefficientDAOImpl() {
    }

    public GainCoefficientDAOImpl(ProxyConnection connection) {
        super(connection);
    }


    @Override
    public int create(GainCoefficient entity) throws DaoException {
        try (PreparedStatement statementCoefficients = connection.prepareStatement(CREATE_GAIN_COEFFICIENT)) {
            for(Map.Entry<BetType, BigDecimal> coefficient: entity.getCoefficients().entrySet()) {
                statementCoefficients.setInt(1, entity.getFootballMatchId());
                statementCoefficients.setBigDecimal(2, coefficient.getValue());
                statementCoefficients.setString(3, coefficient.getKey().toString());
                statementCoefficients.executeUpdate();
            }
            return 1;
        } catch (SQLException e) {
            throw new DaoException("Can't find coefficients", e);
        }
    }

    @Override
    public boolean update(GainCoefficient entity) throws DaoException {
        try (PreparedStatement statementCoefficients = connection.prepareStatement(UPDATE_GAIN_COEFFICIENT)) {
            for(Map.Entry<BetType, BigDecimal> coefficient: entity.getCoefficients().entrySet()) {
                statementCoefficients.setBigDecimal(1, coefficient.getValue());
                statementCoefficients.setString(2, coefficient.getKey().toString());
                statementCoefficients.setInt(3, entity.getFootballMatchId());
                statementCoefficients.executeUpdate();
            }
            return true;
        } catch (SQLException e) {
            throw new DaoException("Can't find coefficients", e);
        }
    }

    @Override
    public GainCoefficient findCoefficientsByMatchId(int matchId) throws DaoException {
        GainCoefficient coefficients;
        try (PreparedStatement statementCoefficients = connection.prepareStatement(SELECT_GAIN_COEFFICIENTS_BY_MATCH_ID)) {
            statementCoefficients.setInt(1, matchId);
            ResultSet resultSet = statementCoefficients.executeQuery();
            coefficients = buildCoefficientList(resultSet);
            coefficients.setFootballMatchId(matchId);
        } catch (SQLException e) {
            throw new DaoException("Can't find coefficients", e);
        }
        return coefficients;
    }

    private GainCoefficient buildCoefficientList(ResultSet resultSet) throws SQLException {
        GainCoefficient coefficients = new GainCoefficient();
        while (resultSet.next()) {
            coefficients.addCoefficient(BetType.valueOf(resultSet.getString(PARAM_NAME_BET_TYPE)),
                    resultSet.getBigDecimal(PARAM_NAME_GAIN_COEFFICIENT));
        }
        return coefficients;
    }

}
