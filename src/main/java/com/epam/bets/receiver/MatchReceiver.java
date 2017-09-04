package com.epam.bets.receiver;

import com.epam.bets.entity.Match;
import com.epam.bets.exception.DaoException;
import com.epam.bets.exception.ReceiverException;
import com.epam.bets.request.RequestContent;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * The class provides Receiver interface layer for action with {@link Match}.
 *
 * @author Pashchuk Ksenia
 */
public interface MatchReceiver extends Receiver {
    /**
     * Provides showing all matches results operation
     *
     * @param requestContent - match info
     * @return {@link List} of {@link Match} object or empty {@link List}
     * @throws ReceiverException if {@link DaoException} occurred while working
     */
    List<Match> showMatchResults(RequestContent requestContent) throws ReceiverException;

    /**
     * Provides showing all matches operation
     *
     * @param requestContent - match info
     * @return {@link List} of {@link Match} object or empty {@link List}
     * @throws ReceiverException if {@link DaoException} occurred while working
     */
    List<Match> showMatches(RequestContent requestContent) throws ReceiverException;

    /**
     * Provides showing all confederations operation
     *
     * @param requestContent - match info
     * @throws ReceiverException if {@link DaoException} occurred while working
     */
    void showAllConfederations(RequestContent requestContent) throws ReceiverException;

    /**
     * Provides showing all football teams operation
     *
     * @param requestContent - match info
     * @throws ReceiverException if {@link DaoException} occurred while working
     */
    void showAllTeams(RequestContent requestContent) throws ReceiverException;

    /**
     * Provides creating match operation for bookmaker
     *
     * @param requestContent - match info
     * @throws ReceiverException if {@link DaoException} occurred while working
     */
    void createMatch(RequestContent requestContent) throws ReceiverException;

    /**
     * Provides editing match operation for bookmaker
     *
     * @param requestContent - match info
     * @throws ReceiverException if {@link DaoException} occurred while working
     */
    void editMatch(RequestContent requestContent) throws ReceiverException;

    /**
     * Provides setting match score operation for bookmaker
     *
     * @param requestContent - match info
     * @throws ReceiverException if {@link DaoException} occurred while working
     */
    void setMatchScore(RequestContent requestContent) throws ReceiverException;

    /**
     * Provides creating football team operation for admin
     *
     * @param requestContent - match info
     * @throws ReceiverException if {@link DaoException} occurred while working
     */
    void createFootballTeam(RequestContent requestContent) throws ReceiverException;

    /**
     * Provides creating confederation operation for admin
     *
     * @param requestContent - match info
     * @throws ReceiverException if {@link DaoException} occurred while working
     */
    void createConfederation(RequestContent requestContent) throws ReceiverException;

}
