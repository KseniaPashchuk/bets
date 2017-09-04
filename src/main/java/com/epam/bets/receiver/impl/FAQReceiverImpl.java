package com.epam.bets.receiver.impl;

import com.epam.bets.dao.DaoFactory;
import com.epam.bets.dao.FaqDAO;
import com.epam.bets.dao.TransactionManager;
import com.epam.bets.dao.impl.FaqDAOImpl;
import com.epam.bets.entity.FAQ;
import com.epam.bets.exception.DaoException;
import com.epam.bets.exception.ReceiverException;
import com.epam.bets.receiver.FAQReceiver;
import com.epam.bets.request.RequestContent;
import com.epam.bets.validator.FAQValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.epam.bets.constant.ErrorConstant.*;
import static com.epam.bets.constant.ErrorConstant.CommonError.INVALID_CREATE_PARAMS;
import static com.epam.bets.constant.ErrorConstant.CommonError.INVALID_EDIT_PARAMS;
import static com.epam.bets.constant.ErrorConstant.FAQError.*;
import static com.epam.bets.constant.RequestParamConstant.FAQParam.PARAM_NAME_ANSWER;
import static com.epam.bets.constant.RequestParamConstant.FAQParam.PARAM_NAME_FAQ_LIST;
import static com.epam.bets.constant.RequestParamConstant.FAQParam.PARAM_NAME_QUESTION;

/**
 * The class provides {@link FAQReceiver} implementation.
 *
 * @author Pashchuk Ksenia
 */
public class FAQReceiverImpl implements FAQReceiver {

    /**
     * Provides showing all faq questions operation
     *
     * @param requestContent - faq info
     * @throws ReceiverException if {@link DaoException} occurred while working
     * @see DaoFactory
     * @see FaqDAO
     */
    @Override
    public void showAllFAQ(RequestContent requestContent) throws ReceiverException {
        List<FAQ> faqList;
        List<String> errors = new ArrayList<>();
        try (DaoFactory factory = new DaoFactory()) {
            FaqDAO faqDAO = factory.getFaqDao();
            faqList = faqDAO.findAllFAQ();
            if (faqList == null || faqList.isEmpty()) {
                errors.add(SHOW_FAQ_ERROR);
                requestContent.insertRequestAttribute(ERROR_LIST_NAME, errors);
            } else {
                requestContent.insertRequestAttribute(PARAM_NAME_FAQ_LIST, faqList);
            }
        } catch (DaoException e) {
            throw new ReceiverException(e);
        }
    }

    /**
     * Provides creating faq operation for admin
     *
     * @param requestContent - faq info
     * @throws ReceiverException if {@link DaoException} occurred while working
     * @see TransactionManager
     * @see FaqDAO
     * @see FAQValidator
     */
    @Override
    public void createFAQ(RequestContent requestContent) throws ReceiverException {
        FAQ faq = new FAQ();
        List<String> errors = new ArrayList<>();
        if (new FAQValidator().validateFAQParams(requestContent, errors)) {
            faq.setQuestion(requestContent.findParameterValue(PARAM_NAME_QUESTION));
            faq.setAnswer(requestContent.findParameterValue(PARAM_NAME_ANSWER));
            FaqDAO faqDAO = new FaqDAOImpl();
            TransactionManager manager = new TransactionManager();
            manager.beginTransaction(faqDAO);
            try {
                if (faqDAO.create(faq) != 0) {
                    manager.commit();
                } else {
                    manager.rollback();
                    errors.add(CREATE_FAQ_ERROR);
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

    /**
     * Provides deleting faq operation for admin
     *
     * @param requestContent - faq info
     * @throws ReceiverException if {@link DaoException} occurred while working
     * @see TransactionManager
     * @see FaqDAO
     */
    @Override
    public void deleteFAQ(RequestContent requestContent) throws ReceiverException {
        String question = requestContent.findParameterValue(PARAM_NAME_QUESTION);
        List<String> errors = new ArrayList<>();
        if (new FAQValidator().validateFAQQuestion(question, errors)) {
            FaqDAO faqDAO = new FaqDAOImpl();
            TransactionManager manager = new TransactionManager();
            manager.beginTransaction(faqDAO);
            try {
                if (faqDAO.deleteByQuestion(question)) {
                    manager.commit();
                } else {
                    manager.rollback();
                    errors.add(DELETE_FAQ_ERROR);
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

    /**
     * Provides editing faq operation for admin
     *
     * @param requestContent - faq info
     * @throws ReceiverException if {@link DaoException} occurred while working
     * @see TransactionManager
     * @see FaqDAO
     * @see FAQValidator
     */
    @Override
    public void editFAQ(RequestContent requestContent) throws ReceiverException {
        FAQ faq = new FAQ();

        List<String> errors = new ArrayList<>();
        if (new FAQValidator().validateFAQParams(requestContent, errors)) {
            faq.setQuestion(requestContent.findParameterValue(PARAM_NAME_QUESTION));
            faq.setAnswer(requestContent.findParameterValue(PARAM_NAME_ANSWER));
            FaqDAO faqDAO = new FaqDAOImpl();
            TransactionManager manager = new TransactionManager();
            manager.beginTransaction(faqDAO);
            try {
                if (faqDAO.update(faq)) {
                    manager.commit();
                } else {
                    manager.rollback();
                    errors.add(EDIT_FAQ_ERROR);
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


}
