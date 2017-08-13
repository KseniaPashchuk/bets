package com.epam.bets.receiver;

import com.epam.bets.entity.Match;
import com.epam.bets.exception.ReceiverException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface MatchReceiver extends Receiver {
    List<Match> showMatchResults(LocalDate date, String confederacy) throws ReceiverException;

    List<Match> showMatches(String confederacy) throws ReceiverException;

    List<String> showAllConfederations() throws ReceiverException;
    List<String> showAllTeams() throws ReceiverException;

    boolean createMatch(Match match) throws ReceiverException;

    boolean editMatch(Match match) throws ReceiverException;

    boolean setMatchScore(int matchId, BigDecimal firstTeamScore,  BigDecimal secondTeamScore) throws ReceiverException;
}
