package com.epam.bets.receiver;

import com.epam.bets.entity.Match;
import com.epam.bets.exception.ReceiverException;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by ASUS on 30.07.2017.
 */
public interface MatchReceiver extends Receiver {
    List<Match> showMatchResults(LocalDate date, String confederacy) throws ReceiverException;
    List<Match> showMatches(String confederacy) throws ReceiverException;
}
