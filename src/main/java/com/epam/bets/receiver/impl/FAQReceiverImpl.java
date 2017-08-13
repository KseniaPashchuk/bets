package com.epam.bets.receiver.impl;

import com.epam.bets.dao.DaoFactory;
import com.epam.bets.dao.FaqDAO;
import com.epam.bets.dao.TransactionManager;
import com.epam.bets.dao.impl.FaqDAOImpl;
import com.epam.bets.entity.FAQ;
import com.epam.bets.exception.DaoException;
import com.epam.bets.exception.ReceiverException;
import com.epam.bets.receiver.FAQReceiver;

import java.util.List;

public class FAQReceiverImpl implements FAQReceiver {

    @Override
    public List<FAQ> showAllFAQ() throws ReceiverException {
        List<FAQ> faqList;
        try (DaoFactory factory = new DaoFactory()) {
            FaqDAO faqDAO = factory.getFaqDao();
            faqList = faqDAO.findAllFAQ();
        } catch (DaoException e) {
            throw new ReceiverException(e); //TODO
        }
        return faqList;
    }

    @Override
    public boolean createFAQ(FAQ faq) throws ReceiverException {
        boolean isFAQCreated = false;
        FaqDAO faqDAO = new FaqDAOImpl();
        TransactionManager manager = new TransactionManager();
        manager.beginTransaction(faqDAO);
        try {
            if (faqDAO.create(faq) != 0) {
                isFAQCreated = true;
                manager.commit();
            } else {
                manager.rollback();
            }
        } catch (DaoException e) {
            manager.rollback();
            throw new ReceiverException(e);
        } finally {
            manager.close();
        }
        return isFAQCreated;
    }

    @Override
    public boolean deleteFAQ(String question) throws ReceiverException {
        boolean isFAQDeleted = false;
        FaqDAO faqDAO = new FaqDAOImpl();
        TransactionManager manager = new TransactionManager();
        manager.beginTransaction(faqDAO);
        try {
            isFAQDeleted = faqDAO.deleteByQuestion(question);
            if (isFAQDeleted) {
                manager.commit();
            } else {
                manager.rollback();
            }
        } catch (DaoException e) {
            manager.rollback();
            throw new ReceiverException(e);
        } finally {
            manager.close();
        }
        return isFAQDeleted;
    }

    @Override
    public boolean editFAQ(FAQ faq) throws ReceiverException {
        boolean isFAQUpdated = false;
        FaqDAO faqDAO = new FaqDAOImpl();
        TransactionManager manager = new TransactionManager();
        manager.beginTransaction(faqDAO);
        try {
            isFAQUpdated = faqDAO.update(faq);
            if (isFAQUpdated) {
                manager.commit();
            } else {
                manager.rollback();
            }
        } catch (DaoException e) {
            manager.rollback();
            throw new ReceiverException(e);
        } finally {
            manager.close();
        }
        return isFAQUpdated;
    }
}
