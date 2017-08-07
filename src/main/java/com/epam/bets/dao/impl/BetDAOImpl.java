package com.epam.bets.dao.impl;

import com.epam.bets.dao.BetDAO;
import com.epam.bets.entity.Bet;
import com.epam.bets.exception.DaoException;

import java.util.List;

/**
 * Created by ASUS on 08.08.2017.
 */
public class BetDAOImpl extends BetDAO {
    @Override
    public List<Bet> findAll() throws DaoException {
        return null;
    }

    @Override
    public Bet findEntityById(int id) throws DaoException {
        return null;
    }

    @Override
    public boolean delete(int id) throws DaoException {
        return false;
    }

    @Override
    public int create(Bet entity) throws DaoException {
        return 0;
    }

    @Override
    public boolean update(Bet entity) throws DaoException {
        return false;
    }
}
