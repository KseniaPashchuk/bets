package com.epam.bets.receiver;

import com.epam.bets.entity.Match;
import com.epam.bets.exception.ReceiverException;
import com.epam.bets.request.RequestContent;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface MatchReceiver extends Receiver {
    List<Match> showMatchResults(RequestContent requestContent) throws ReceiverException;

    List<Match> showMatches(RequestContent requestContent) throws ReceiverException;

    void showAllConfederations(RequestContent requestContent) throws ReceiverException;

    void showAllTeams(RequestContent requestContent) throws ReceiverException;

    void createMatch(RequestContent requestContent) throws ReceiverException;

    void editMatch(RequestContent requestContent) throws ReceiverException;

    void setMatchScore(RequestContent requestContent) throws ReceiverException;

    void createFootballTeam(RequestContent requestContent) throws ReceiverException;

    void createConfederation(RequestContent requestContent) throws ReceiverException;

}
