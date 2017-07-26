package com.epam.bets.receiver.impl;

import com.epam.bets.dao.DaoFactory;
import com.epam.bets.dao.FaqDAO;
import com.epam.bets.dao.impl.FaqDAOImpl;
import com.epam.bets.entity.FAQ;
import com.epam.bets.exception.DaoException;
import com.epam.bets.exception.ReceiverException;
import com.epam.bets.pool.ConnectionPool;
import com.epam.bets.pool.ProxyConnection;
import com.epam.bets.receiver.FAQReceiver;

import java.util.List;

public class FAQReceiverImpl implements FAQReceiver {

    @Override
    public List<FAQ> showAllFAQ() throws ReceiverException {
        List<FAQ> faqList;
        try (DaoFactory factory = new DaoFactory()) {
            FaqDAO faqDAO = factory.getFaqDao();
            faqList = faqDAO.findAll();
        } catch (DaoException e) {
            throw new ReceiverException(e); //TODO
        }
        return faqList;
    }

    @Override
    public boolean createFAQ() throws ReceiverException {
        return false;
    }

    @Override
    public boolean deleteFAQ() throws ReceiverException {
        return false;
    }

    @Override
    public boolean editFAQ() throws ReceiverException {
        return false;
    }
}
