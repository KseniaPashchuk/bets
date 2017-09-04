package com.epam.bets.dao.impl;

import com.epam.bets.dao.BetDAO;
import com.epam.bets.entity.Bet;
import com.epam.bets.entity.BetType;
import com.epam.bets.exception.DaoException;
import com.epam.bets.pool.ProxyConnection;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
/**
 * The class provides {@link BetDAO} implementation.
 *
 * @author Pashchuk Ksenia
 */
public class BetDAOImpl extends BetDAO {
    private static final String SELECT_WINNED_BETS = "SELECT money, coefficient,first_team.team_name AS first_team, second_team.team_name AS second_team," +
            " bet_type FROM bookmaker_office.bet JOIN (football_match  JOIN football_team AS first_team ON football_match.first_team_id = first_team.team_id " +
            "JOIN football_team AS second_team ON football_match.second_team_id = second_team.team_id) ON bet.football_match_id = football_match.match_id " +
            "JOIN bet_type ON bet.bet_type_id=bet_type.bet_type_id JOIN gain_coefficient ON (bet.football_match_id=gain_coefficient.football_match_id AND " +
            "bet.bet_type_id = gain_coefficient.bet_type_id)  WHERE user_id=? AND bet.won=1";

    private static final String SELECT_LOST_BETS = "SELECT money, coefficient,first_team.team_name AS first_team, second_team.team_name AS second_team, " +
            " bet_type FROM bookmaker_office.bet JOIN (football_match  JOIN football_team AS first_team ON football_match.first_team_id = first_team.team_id " +
            "JOIN football_team AS second_team ON football_match.second_team_id = second_team.team_id) ON bet.football_match_id = football_match.match_id " +
            "JOIN bet_type ON bet.bet_type_id=bet_type.bet_type_id JOIN gain_coefficient ON (bet.football_match_id=gain_coefficient.football_match_id AND " +
            "bet.bet_type_id = gain_coefficient.bet_type_id)  WHERE user_id=? AND bet.won=0";

    private static final String SELECT_OPEN_BETS = "SELECT bet_id, money, coefficient, first_team.team_name AS first_team, second_team.team_name AS second_team, " +
            " bet_type FROM bookmaker_office.bet JOIN (football_match  JOIN football_team AS first_team ON football_match.first_team_id = first_team.team_id " +
            "JOIN football_team AS second_team ON football_match.second_team_id = second_team.team_id) ON bet.football_match_id = football_match.match_id " +
            "JOIN bet_type ON bet.bet_type_id=bet_type.bet_type_id JOIN gain_coefficient ON (bet.football_match_id=gain_coefficient.football_match_id AND " +
            "bet.bet_type_id = gain_coefficient.bet_type_id)  WHERE user_id=? AND ISNULL(bet.won)";

    private static final String SELECT_BETS_BY_MATCH_ID = "SELECT bet_id, user_id, money, bet_type, coefficient FROM bet " +
            "JOIN bet_type ON bet.bet_type_id=bet_type.bet_type_id " +
            "JOIN gain_coefficient ON (bet.football_match_id=gain_coefficient.football_match_id AND bet.bet_type_id = gain_coefficient.bet_type_id) " +
            "WHERE bet.football_match_id = ?";

    private static final String CREATE_BET = "INSERT INTO bet (bet_id, money, user_id, football_match_id, bet_type_id)" +
            " VALUES( NULL, ?, ?, ?, (SELECT bet_type_id FROM bet_type WHERE bet_type=?))";

    private static final String UPDATE_IS_BET_WON = "UPDATE bet SET won=? WHERE bet_id=?";

    public BetDAOImpl() {
    }

    public BetDAOImpl(ProxyConnection connection) {
        super(connection);
    }


    /**
     * Create user {@link Bet}
     *
     * @param entity bet info
     * @return id of created bet
     * @throws DaoException if {@link SQLException} occurred while working with database
     * @see PreparedStatement
     * @see ResultSet
     */

    @Override
    public int create(Bet entity) throws DaoException {
        try (PreparedStatement statementBet = connection.prepareStatement(CREATE_BET, Statement.RETURN_GENERATED_KEYS)) {
            statementBet.setBigDecimal(1, entity.getMoney());
            statementBet.setInt(2, entity.getUserId());
            statementBet.setInt(3, entity.getFootballMatchId());
            statementBet.setString(4, entity.getBetType().toString());
            statementBet.executeUpdate();
            ResultSet generatedKey = statementBet.getGeneratedKeys();
            if (generatedKey.next()) {
                return generatedKey.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new DaoException("Can't create bets", e);
        }

    }

    /**
     * Update user {@link Bet}. Unnecessary operation.
     *
     * @param entity bet info
     * @throws {@link UnsupportedOperationException}
     */
    @Override
    public boolean update(Bet entity) throws DaoException {
        throw new UnsupportedOperationException();
    }

    /**
     * Takes {@link List} of winned {@link Bet} by user id.
     *
     * @param userId user id
     * @return taken {@link List} of all {@link Bet} object or empty {@link List}
     * @throws DaoException if {@link SQLException} occurred while working with database
     * @see PreparedStatement
     * @see ResultSet
     */
    @Override
    public List<Bet> findWinnedBets(int userId) throws DaoException {
        List<Bet> bets;
        try (PreparedStatement statementBet = connection.prepareStatement(SELECT_WINNED_BETS)) {
            statementBet.setInt(1, userId);
            ResultSet resultSet = statementBet.executeQuery();
            bets = buildBetList(resultSet);
            return bets;
        } catch (SQLException e) {
            throw new DaoException("Can't find winned bets", e);
        }
    }

    /**
     * Takes {@link List} of lost {@link Bet} by user id.
     *
     * @param userId user id
     * @return taken {@link List} of {@link Bet} object or empty {@link List}
     * @throws DaoException if {@link SQLException} occurred while working with database
     * @see PreparedStatement
     * @see ResultSet
     */
    @Override
    public List<Bet> findLostBets(int userId) throws DaoException {
        List<Bet> bets;
        try (PreparedStatement statementBet = connection.prepareStatement(SELECT_LOST_BETS)) {
            statementBet.setInt(1, userId);
            ResultSet resultSet = statementBet.executeQuery();
            bets = buildBetList(resultSet);
            return bets;
        } catch (SQLException e) {
            throw new DaoException("Can't find lost bets", e);
        }
    }

    /**
     * Takes {@link List} of open {@link Bet} by user id.
     *
     * @param userId user id
     * @return taken {@link List} of {@link Bet} object or empty {@link List}
     * @throws DaoException if {@link SQLException} occurred while working with database
     * @see PreparedStatement
     * @see ResultSet
     */
    @Override
    public List<Bet> findOpenBets(int userId) throws DaoException {
        List<Bet> bets;
        try (PreparedStatement statementBet = connection.prepareStatement(SELECT_OPEN_BETS)) {
            statementBet.setInt(1, userId);
            ResultSet resultSet = statementBet.executeQuery();
            bets = buildBetList(resultSet);
            return bets;
        } catch (SQLException e) {
            throw new DaoException("Can't find open bets", e);
        }
    }

    /**
     * Create user {@link Bet}s
     *
     * @param bets list of bets to be created
     * @return true if successfully created
     * @throws DaoException if {@link SQLException} occurred while working with database
     * @see PreparedStatement
     * @see ResultSet
     */
    @Override
    public boolean createBets(List<Bet> bets) throws DaoException {
        try (PreparedStatement statementBet = connection.prepareStatement(CREATE_BET)) {
            for (Bet bet : bets) {
                statementBet.setBigDecimal(1, bet.getMoney());
                statementBet.setInt(2, bet.getUserId());
                statementBet.setInt(3, bet.getFootballMatchId());
                statementBet.setString(4, bet.getBetType().toString());
                statementBet.executeUpdate();
            }
            return true;
        } catch (SQLException e) {
            throw new DaoException("Can't create bets", e);
        }
    }

    /**
     * Takes {@link List} of {@link Bet} by match id.
     *
     * @param matchId match id
     * @return taken {@link List} of {@link Bet} object or empty {@link List}
     * @throws DaoException if {@link SQLException} occurred while working with database
     * @see PreparedStatement
     * @see ResultSet
     */
    @Override
    public List<Bet> findBetsByMatchId(int matchId) throws DaoException {
        List<Bet> bets = new ArrayList<>();
        ;
        try (PreparedStatement statementBet = connection.prepareStatement(SELECT_BETS_BY_MATCH_ID)) {
            statementBet.setInt(1, matchId);
            ResultSet resultSet = statementBet.executeQuery();
            Bet bet;
            while (resultSet.next()) {
                bet = new Bet();
                bet.setId(resultSet.getInt(PARAM_NAME_ID));
                bet.setMoney(resultSet.getBigDecimal(PARAM_NAME_MONEY));
                bet.setUserId(resultSet.getInt(PARAM_NAME_USER_ID));
                bet.setBetType(BetType.valueOf(resultSet.getString(PARAM_NAME_BET_TYPE)));
                bet.setCoefficient(resultSet.getBigDecimal(PARAM_NAME_COEFFICIENT));
                bets.add(bet);
            }
            return bets;
        } catch (SQLException e) {
            throw new DaoException("Can't find bets", e);
        }
    }

    /**
     * Update isWon param for {@link Bet} by its id.
     *
     * @param betId    bet id
     * @param isBetWon if bet is winning
     * @return taken {@link List} of {@link Bet} object or empty {@link List}
     * @throws DaoException if {@link SQLException} occurred while working with database
     * @see PreparedStatement
     * @see ResultSet
     */
    @Override
    public boolean updateIsBetWon(int betId, boolean isBetWon) throws DaoException {
        try (PreparedStatement statementBet = connection.prepareStatement(UPDATE_IS_BET_WON)) {
            statementBet.setBoolean(1, isBetWon);
            statementBet.setInt(2, betId);
            statementBet.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DaoException("Can't find bets", e);
        }
    }
    /**
     * Builds {@link Bet} object by parsing {@link ResultSet} object.
     *
     * @param resultSet {@link ResultSet} object to parse
     * @return parsed {@link Bet} object or null
     * @throws SQLException if the columnLabel is not valid; if a database access error occurs or this method is called
     *                      on a closed result set
     */

    private Bet buildBet(ResultSet resultSet) throws SQLException {
        Bet bet = new Bet();
        bet.setMoney(resultSet.getBigDecimal(PARAM_NAME_MONEY));
        bet.setFootballMatch(resultSet.getString(PARAM_NAME_FIRST_TEAM) + " - " + resultSet.getString(PARAM_NAME_SECOND_TEAM));
        bet.setBetType(BetType.valueOf(resultSet.getString(PARAM_NAME_BET_TYPE)));
        bet.setCoefficient(resultSet.getBigDecimal(PARAM_NAME_COEFFICIENT));
        return bet;
    }
    /**
     * Builds {@link List} object filled by {@link Bet} objects by parsing {@link ResultSet} object.
     *
     * @param resultSet {@link ResultSet} object to parse
     * @return parsed {@link List} object or null
     * @throws SQLException if the columnLabel is not valid; if a database access error occurs or this method is called
     *                      on a closed result set
     * @see #buildBet(ResultSet)
     */
    private List<Bet> buildBetList(ResultSet resultSet) throws SQLException {
        List<Bet> matchList = new ArrayList<>();
        while (resultSet.next()) {
            matchList.add(buildBet(resultSet));
        }
        return matchList;
    }
}
