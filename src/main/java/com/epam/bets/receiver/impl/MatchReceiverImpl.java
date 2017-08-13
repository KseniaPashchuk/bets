package com.epam.bets.receiver.impl;

import com.epam.bets.dao.GainCoefficientDAO;
import com.epam.bets.dao.DaoFactory;
import com.epam.bets.dao.MatchDAO;
import com.epam.bets.dao.TransactionManager;
import com.epam.bets.dao.impl.GainCoefficientDAOImpl;
import com.epam.bets.dao.impl.MatchDAOImpl;
import com.epam.bets.entity.Match;
import com.epam.bets.exception.DaoException;
import com.epam.bets.exception.ReceiverException;
import com.epam.bets.receiver.MatchReceiver;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


public class MatchReceiverImpl implements MatchReceiver {

    private static final String PARAM_NAME_ALL_CONFEDERATIONS = "all";

    @Override
    public List<Match> showMatchResults(LocalDate date, String confederacy) throws ReceiverException {
        List<Match> results;
        try (DaoFactory factory = new DaoFactory()) {
            MatchDAO matchDAO = factory.getMatchDao();
            if (PARAM_NAME_ALL_CONFEDERATIONS.equals(confederacy)) {
                results = matchDAO.findResultsByDate(date);
            } else {
                results = matchDAO.findResultsByDateAndConfederacy(date, confederacy);
            }
        } catch (DaoException e) {
            throw new ReceiverException(e);
        }
        return results;
    }

    @Override
    public List<Match> showMatches(String confederacy) throws ReceiverException {
        List<Match> matches;
        try (DaoFactory factory = new DaoFactory()) {
            MatchDAO matchDAO = factory.getMatchDao();
            GainCoefficientDAO coefficientDAO = factory.getGainCoefficientDao();
            if (PARAM_NAME_ALL_CONFEDERATIONS.equalsIgnoreCase(confederacy)) {
                matches = matchDAO.findAllMatches();
            } else {
                matches = matchDAO.findMatchesByConfederacy(confederacy);
            }
            for (Match match : matches) {
                match.setMatchCoefficients(coefficientDAO.findCoefficientsByMatchId(match.getId()));
            }
        } catch (DaoException e) {
            throw new ReceiverException(e);
        }
        return matches;
    }

    @Override
    public List<String> showAllConfederations() throws ReceiverException {
        List<String> confederations;
        try (DaoFactory factory = new DaoFactory()) {
            MatchDAO matchDAO = factory.getMatchDao();
            confederations = matchDAO.findAllConfederations();
        } catch (DaoException e) {
            throw new ReceiverException(e);
        }
        return confederations;
    }

    @Override
    public List<String> showAllTeams() throws ReceiverException {
        List<String> teams;
        try (DaoFactory factory = new DaoFactory()) {
            MatchDAO matchDAO = factory.getMatchDao();
            teams = matchDAO.findAllTeams();
        } catch (DaoException e) {
            throw new ReceiverException(e);
        }
        return teams;
    }

    @Override
    public boolean createMatch(Match match) throws ReceiverException {
        MatchDAO matchDAO = new MatchDAOImpl();
        GainCoefficientDAO coefficientDAO = new GainCoefficientDAOImpl();
        TransactionManager manager = new TransactionManager();
        manager.beginTransaction(matchDAO, coefficientDAO);
        try {
            int matchId = matchDAO.create(match);
            if (matchId != 0) {
                match.getMatchCoefficients().setFootballMatchId(matchId);
                if (coefficientDAO.create(match.getMatchCoefficients()) != 0) {
                    manager.commit();
                    return true;
                } else {
                    manager.rollback();
                }
            } else {
                manager.rollback();
            }
        } catch (DaoException e) {
            manager.rollback();
            throw new ReceiverException(e);
        } finally {
            manager.close();
        }
        return false;
    }

    @Override
    public boolean editMatch(Match match) throws ReceiverException {
        MatchDAO matchDAO = new MatchDAOImpl();
        GainCoefficientDAO coefficientDAO = new GainCoefficientDAOImpl();
        TransactionManager manager = new TransactionManager();
        manager.beginTransaction(matchDAO, coefficientDAO);
        try {
            if (matchDAO.update(match) && coefficientDAO.update(match.getMatchCoefficients())) {
                manager.commit();
                return true;
            } else {
                manager.rollback();
            }
        } catch (DaoException e) {
            manager.rollback();
            throw new ReceiverException(e);
        } finally {
            manager.close();
        }
        return false;
    }



    @Override
    public boolean setMatchScore(int matchId, BigDecimal firstTeamScore, BigDecimal secondTeamScore) throws
            ReceiverException {
        MatchDAO matchDAO = new MatchDAOImpl();
        TransactionManager manager = new TransactionManager();
        manager.beginTransaction(matchDAO);
        try {

            if (matchDAO.setScore(matchId, firstTeamScore, secondTeamScore)) {
                manager.commit();
                return true;
            } else {
                manager.rollback();
            }
        } catch (DaoException e) {
            manager.rollback();
            throw new ReceiverException(e);
        } finally {
            manager.close();
        }
        return false;
    }

}
