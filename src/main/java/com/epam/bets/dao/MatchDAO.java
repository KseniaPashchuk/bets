package com.epam.bets.dao;

import com.epam.bets.entity.Match;
import com.epam.bets.exception.DaoException;
import com.epam.bets.pool.ProxyConnection;

import java.time.LocalDate;
import java.util.List;

public abstract class MatchDAO extends  AbstractDAO<Match>{
    protected static final String PARAM_NAME_ID = "match_id";
    protected static final String PARAM_NAME_FIRST_TEAM = "first_team";
    protected static final String PARAM_NAME_SECOND_TEAM= "second_team";
    protected static final String PARAM_NAME_MAX_BET = "max_bet";
    protected static final String PARAM_NAME_TOTAL = "total";
    protected static final String PARAM_NAME_CONFEDERACY = "confederacy";


    protected MatchDAO() {
    }

    protected MatchDAO(ProxyConnection connection) {
        super(connection);
    }

    public abstract List<Match> findResultsByDate(LocalDate date) throws DaoException;
    public abstract List<Match> findResultsByDateAndConfederacy(LocalDate date, String confederacy) throws DaoException;
    public abstract List<Match> findMatchesByConfederacy(String confederacy) throws DaoException;
    public abstract  List<String> findAllConfederations () throws DaoException;

}
