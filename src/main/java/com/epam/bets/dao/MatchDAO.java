package com.epam.bets.dao;

import com.epam.bets.entity.Match;
import com.epam.bets.entity.News;
import com.epam.bets.exception.DaoException;
import com.epam.bets.pool.ProxyConnection;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public abstract class MatchDAO extends AbstractDAO<Match> {
    protected static final String PARAM_NAME_ID = "match_id";
    protected static final String PARAM_NAME_FIRST_TEAM = "first_team";
    protected static final String PARAM_NAME_SECOND_TEAM = "second_team";
    protected static final String PARAM_NAME_FIRST_TEAM_SCORE = "first_team_score";
    protected static final String PARAM_NAME_SECOND_TEAM_SCORE = "second_team_score";
    protected static final String PARAM_NAME_MAX_BET = "max_bet_value";
    protected static final String PARAM_NAME_TOTAL = "total_value";
    protected static final String PARAM_NAME_CONFEDERACY = "confederacy";
    protected static final String PARAM_NAME_DATE = "date_time";
    protected static final String PARAM_NAME_TEAM_NAME = "team_name";


    protected MatchDAO() {
    }

    protected MatchDAO(ProxyConnection connection) {
        super(connection);
    }

    public abstract List<Match> findAllMatches() throws DaoException;


    public abstract List<Match> findResultsByDate(LocalDate date) throws DaoException;

    public abstract List<Match> findResultsByDateAndConfederacy(LocalDate date, String confederacy) throws DaoException;

    public abstract List<Match> findMatchesByConfederacy(String confederacy) throws DaoException;

    public abstract List<String> findAllConfederations() throws DaoException;

    public abstract List<String> findAllTeams() throws DaoException;

    public abstract boolean setScore(int matchId, BigDecimal firstTeamScore, BigDecimal secondTeamScore) throws DaoException;

    public abstract Match findFinishedMatchInfo(int matchId) throws DaoException;
}
