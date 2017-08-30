package com.epam.bets.receiver.impl;

import com.epam.bets.dao.GainCoefficientDAO;
import com.epam.bets.dao.DaoFactory;
import com.epam.bets.dao.MatchDAO;
import com.epam.bets.dao.TransactionManager;
import com.epam.bets.dao.impl.GainCoefficientDAOImpl;
import com.epam.bets.dao.impl.MatchDAOImpl;

import com.epam.bets.entity.BetType;
import com.epam.bets.entity.Match;
import com.epam.bets.exception.DaoException;
import com.epam.bets.exception.ReceiverException;
import com.epam.bets.receiver.MatchReceiver;
import com.epam.bets.request.RequestContent;
import com.epam.bets.validator.MatchValidator;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.epam.bets.constant.ErrorConstant.*;
import static com.epam.bets.constant.ErrorConstant.CommonError.INVALID_CREATE_PARAMS;
import static com.epam.bets.constant.ErrorConstant.CommonError.INVALID_EDIT_PARAMS;
import static com.epam.bets.constant.ErrorConstant.MatchError.*;
import static com.epam.bets.constant.RequestParamConstant.CommonParam.DATE_PATTERN;
import static com.epam.bets.constant.RequestParamConstant.CommonParam.PARAM_NAME_DATE;
import static com.epam.bets.constant.RequestParamConstant.MatchParam.*;


public class MatchReceiverImpl implements MatchReceiver {


    @Override
    public List<Match> showMatchResults(RequestContent requestContent) throws ReceiverException {
        List<Match> results;
        String confederacy = requestContent.findParameterValue(PARAM_NAME_CONFEDERACY);
        String dateString = requestContent.findParameterValue(PARAM_NAME_DATE);
        LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern(DATE_PATTERN));
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
    public List<Match> showMatches(RequestContent requestContent) throws ReceiverException {
        List<Match> matches;
        String confederacy = requestContent.findParameterValue(PARAM_NAME_CONFEDERACY);
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
    public void showAllConfederations(RequestContent requestContent) throws ReceiverException {
        List<String> confederations;
        List<String> errors = new ArrayList<>();
        try (DaoFactory factory = new DaoFactory()) {
            MatchDAO matchDAO = factory.getMatchDao();
            confederations = matchDAO.findAllConfederations();
            if (confederations != null && !confederations.isEmpty()) {
                requestContent.insertRequestAttribute(PARAM_NAME_CONFEDERATION_LIST, confederations);
            } else {
                errors.add(SHOW_ALL_CONFEDERATIONS_ERROR);
                requestContent.insertRequestAttribute(ERROR_LIST_NAME, errors);
            }
        } catch (DaoException e) {
            throw new ReceiverException(e);
        }
    }

    @Override
    public void showAllTeams(RequestContent requestContent) throws ReceiverException {
        List<String> teams;
        List<String> errors = new ArrayList<>();
        try (DaoFactory factory = new DaoFactory()) {
            MatchDAO matchDAO = factory.getMatchDao();
            teams = matchDAO.findAllTeams();
            if (teams != null && !teams.isEmpty()) {
                requestContent.insertRequestAttribute(PARAM_NAME_TEAM_LIST, teams);
            } else {
                if (requestContent.findRequestAttribute(ERROR_LIST_NAME) == null) {
                    errors.add(SHOW_ALL_TEAMS_ERROR);
                    requestContent.insertRequestAttribute(ERROR_LIST_NAME, errors);
                }
            }
        } catch (DaoException e) {
            throw new ReceiverException(e);
        }
    }

    @Override
    public void createMatch(RequestContent requestContent) throws ReceiverException {
        Match match = new Match();
        List<String> errors = new ArrayList<>();
        match.setFirstTeam(requestContent.findParameterValue(PARAM_NAME_FIRST_TEAM));
        match.setSecondTeam(requestContent.findParameterValue(PARAM_NAME_SECOND_TEAM));
        match.setConfederacy(requestContent.findParameterValue(PARAM_NAME_CONFEDERACY));
        match.setDate(LocalDateTime.parse(requestContent.findParameterValue(PARAM_NAME_DATE), DateTimeFormatter.ofPattern(DATE_TIME_PATTERN)));
        match.addCoefficient(BetType.FW, new BigDecimal(requestContent.findParameterValue(PARAM_NAME_FW)));
        match.addCoefficient(BetType.X, new BigDecimal(requestContent.findParameterValue(PARAM_NAME_X)));
        match.addCoefficient(BetType.SW, new BigDecimal(requestContent.findParameterValue(PARAM_NAME_SW)));
        match.addCoefficient(BetType.FWX, new BigDecimal(requestContent.findParameterValue(PARAM_NAME_FWX)));
        match.addCoefficient(BetType.FS, new BigDecimal(requestContent.findParameterValue(PARAM_NAME_FS)));
        match.addCoefficient(BetType.XSW, new BigDecimal(requestContent.findParameterValue(PARAM_NAME_XSW)));
        match.addCoefficient(BetType.TL, new BigDecimal(requestContent.findParameterValue(PARAM_NAME_TL)));
        match.addCoefficient(BetType.TM, new BigDecimal(requestContent.findParameterValue(PARAM_NAME_TM)));
        match.setTotal(new BigDecimal(requestContent.findParameterValue(PARAM_NAME_T)));
        match.setMaxBet(new BigDecimal(requestContent.findParameterValue(PARAM_NAME_MAX_BET)));
        if (new MatchValidator().validateMatchParams(match, errors)) {
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
                    } else {
                        manager.rollback();
                        errors.add(CREATE_MATCH_ERROR);
                    }
                } else {
                    manager.rollback();
                    errors.add(CREATE_MATCH_ERROR);
                }

            } catch (DaoException e) {
                manager.rollback();
                throw new ReceiverException(e);
            } finally {
                manager.close();
            }
        } else {
            errors.add(INVALID_CREATE_PARAMS);
        }
        if (!errors.isEmpty()) {
            requestContent.insertRequestAttribute(ERROR_LIST_NAME, errors);
        }
    }

    @Override
    public void editMatch(RequestContent requestContent) throws ReceiverException {
        Match match = new Match();
        List<String> errors = new ArrayList<>();
        match.setFirstTeam(requestContent.findParameterValue(PARAM_NAME_FIRST_TEAM));
        match.setSecondTeam(requestContent.findParameterValue(PARAM_NAME_SECOND_TEAM));
        match.setConfederacy(requestContent.findParameterValue(PARAM_NAME_CONFEDERACY));
        match.setDate(LocalDateTime.parse(requestContent.findParameterValue(PARAM_NAME_DATE), DateTimeFormatter.ofPattern(DATE_TIME_PATTERN)));
        match.addCoefficient(BetType.FW, new BigDecimal(requestContent.findParameterValue(PARAM_NAME_FW)));
        match.addCoefficient(BetType.X, new BigDecimal(requestContent.findParameterValue(PARAM_NAME_X)));
        match.addCoefficient(BetType.SW, new BigDecimal(requestContent.findParameterValue(PARAM_NAME_SW)));
        match.addCoefficient(BetType.FWX, new BigDecimal(requestContent.findParameterValue(PARAM_NAME_FWX)));
        match.addCoefficient(BetType.FS, new BigDecimal(requestContent.findParameterValue(PARAM_NAME_FS)));
        match.addCoefficient(BetType.XSW, new BigDecimal(requestContent.findParameterValue(PARAM_NAME_XSW)));
        match.addCoefficient(BetType.TL, new BigDecimal(requestContent.findParameterValue(PARAM_NAME_TL)));
        match.addCoefficient(BetType.TM, new BigDecimal(requestContent.findParameterValue(PARAM_NAME_TM)));
        match.setTotal(new BigDecimal(requestContent.findParameterValue(PARAM_NAME_T)));
        match.setMaxBet(new BigDecimal(requestContent.findParameterValue(PARAM_NAME_MAX_BET)));
        if (new MatchValidator().validateMatchParams(match, errors)) {
            MatchDAO matchDAO = new MatchDAOImpl();
            GainCoefficientDAO coefficientDAO = new GainCoefficientDAOImpl();
            TransactionManager manager = new TransactionManager();
            manager.beginTransaction(matchDAO, coefficientDAO);
            try {
                if (matchDAO.update(match) && coefficientDAO.update(match.getMatchCoefficients())) {
                    manager.commit();
                } else {
                    manager.rollback();
                    errors.add(EDIT_MATCH_ERROR);
                }

            } catch (DaoException e) {
                manager.rollback();
                throw new ReceiverException(e);
            } finally {
                manager.close();
            }
        } else {
            errors.add(INVALID_EDIT_PARAMS);
        }
        if (!errors.isEmpty()) {
            requestContent.insertRequestAttribute(ERROR_LIST_NAME, errors);
        }
    }


    @Override
    public void setMatchScore(RequestContent requestContent) throws
            ReceiverException {
        List<String> errors = new ArrayList<>();
        int matchId = Integer.parseInt(requestContent.findParameterValue(PARAM_NAME_MATCH_ID));
        BigDecimal firstTeamScore = new BigDecimal(requestContent.findParameterValue(PARAM_NAME_FIRST_TEAM_SCORE));
        BigDecimal secondTeamScore = new BigDecimal(requestContent.findParameterValue(PARAM_NAME_SECOND_TEAM_SCORE));
        LocalDateTime dateTime = LocalDateTime.parse(requestContent.findParameterValue(PARAM_NAME_DATE),
                DateTimeFormatter.ofPattern(DATE_TIME_PATTERN));
        boolean isValid = true;
        if (dateTime.isAfter(LocalDateTime.now())) {
            isValid = false;
            errors.add(SET_SCORE_DATE_ERROR);
        }
        if (firstTeamScore.signum() == -1 || secondTeamScore.signum() == -1) {
            isValid = false;
            errors.add(SCORE_NOT_POSITIVE_ERROR);
        }
        if (isValid) {
            MatchDAO matchDAO = new MatchDAOImpl();
            TransactionManager manager = new TransactionManager();
            manager.beginTransaction(matchDAO);
            try {
                if (matchDAO.setScore(matchId, firstTeamScore, secondTeamScore)) {
                    manager.commit();
                } else {
                    manager.rollback();
                    errors.add(SET_SCORE_ERROR);
                }
            } catch (DaoException e) {
                manager.rollback();
                throw new ReceiverException(e);
            } finally {
                manager.close();
            }
        }
        if (!errors.isEmpty()) {
            requestContent.insertRequestAttribute(ERROR_LIST_NAME, errors);
        }
    }

    @Override
    public void createFootballTeam(RequestContent requestContent) throws ReceiverException {
        String team = requestContent.findParameterValue(PARAM_NAME_TEAM);
        String country = requestContent.findParameterValue(PARAM_NAME_COUNTRY);
        List<String> errors = new ArrayList<>();
        boolean isValid = true;
        if (team == null || team.isEmpty()) {
            isValid = false;
            errors.add(INVALID_TEAM_NAME_ERROR);
        }
        if (country == null || country.isEmpty()) {
            isValid = false;
            errors.add(INVALID_COUNTRY_ERROR);
        }
        if (isValid) {
            MatchDAO matchDAO = new MatchDAOImpl();
            TransactionManager manager = new TransactionManager();
            manager.beginTransaction(matchDAO);
            try {
                if (matchDAO.createNewFootballTeam(team, country)) {
                    manager.commit();
                } else {
                    manager.rollback();
                    errors.add(CREATE_FOOTBALL_TEAM_ERROR);
                }
            } catch (DaoException e) {
                manager.rollback();
                throw new ReceiverException(e);
            } finally {
                manager.close();
            }
        }
        if (!errors.isEmpty()) {
            requestContent.insertRequestAttribute(ERROR_LIST_NAME, errors);
        }
    }


}
