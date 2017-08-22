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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.epam.bets.constant.ErrorConstant.*;
import static com.epam.bets.constant.RequestParamConstant.FAQParam.PARAM_NAME_ANSWER;
import static com.epam.bets.constant.RequestParamConstant.FAQParam.PARAM_NAME_FAQ_LIST;
import static com.epam.bets.constant.RequestParamConstant.FAQParam.PARAM_NAME_QUESTION;

public class FAQReceiverImpl implements FAQReceiver {

    @Override
    public void showAllFAQ(RequestContent requestContent) throws ReceiverException {
        List<FAQ> faqList;
        Map<String, String> errors = new HashMap<>();
        try (DaoFactory factory = new DaoFactory()) {
            FaqDAO faqDAO = factory.getFaqDao();
            faqList = faqDAO.findAllFAQ();
            if (faqList == null || faqList.isEmpty()) {
                errors.put(ERROR, SHOW_FAQ_ERROR_MESSAGE);
                requestContent.insertRequestAttribute(ERROR_MAP_NAME, errors);
            } else {
                requestContent.insertRequestAttribute(PARAM_NAME_FAQ_LIST, faqList);
            }
        } catch (DaoException e) {
            throw new ReceiverException(e);
        }

    }

    @Override
    public void createFAQ(RequestContent requestContent) throws ReceiverException {
        FAQ faq = new FAQ();
        faq.setQuestion(requestContent.findParameterValue(PARAM_NAME_QUESTION));
        faq.setAnswer(requestContent.findParameterValue(PARAM_NAME_ANSWER));
        Map<String, String> errors = new HashMap<>();
        if (isValidFAQParams(faq, errors)) {
            FaqDAO faqDAO = new FaqDAOImpl();
            TransactionManager manager = new TransactionManager();
            manager.beginTransaction(faqDAO);
            try {
                if (faqDAO.create(faq) != 0) {
                    manager.commit();
                } else {
                    manager.rollback();
                    errors.put(ERROR, CREATE_FAQ_ERROR_MESSAGE);
                }
            } catch (DaoException e) {
                manager.rollback();
                throw new ReceiverException(e);
            } finally {
                manager.close();
            }
        }
        if (!errors.isEmpty()) {
            errors.put(CREATE_ERRORS, CREATE_ERRORS);
            requestContent.insertRequestAttribute(ERROR_MAP_NAME, errors);
        }
    }

    @Override
    public void deleteFAQ(RequestContent requestContent) throws ReceiverException {
        String question = requestContent.findParameterValue(PARAM_NAME_QUESTION);
        Map<String, String> errors = new HashMap<>();
        FaqDAO faqDAO = new FaqDAOImpl();
        TransactionManager manager = new TransactionManager();
        manager.beginTransaction(faqDAO);
        try {
            if (faqDAO.deleteByQuestion(question)) {
                manager.commit();
            } else {
                manager.rollback();
                errors.put(ERROR, DELETE_FAQ_ERROR_MESSAGE);
                requestContent.insertRequestAttribute(ERROR_MAP_NAME, errors);
            }
        } catch (DaoException e) {
            manager.rollback();
            throw new ReceiverException(e);
        } finally {
            manager.close();
        }

    }

    @Override
    public void editFAQ(RequestContent requestContent) throws ReceiverException {
        FAQ faq = new FAQ();
        faq.setQuestion(requestContent.findParameterValue(PARAM_NAME_QUESTION));
        faq.setAnswer(requestContent.findParameterValue(PARAM_NAME_ANSWER));
        Map<String, String> errors = new HashMap<>();
        if(isValidFAQParams(faq, errors)) {
            FaqDAO faqDAO = new FaqDAOImpl();
            TransactionManager manager = new TransactionManager();
            manager.beginTransaction(faqDAO);
            try {
                if (faqDAO.update(faq)) {
                    manager.commit();
                } else {
                    manager.rollback();
                    errors.put(ERROR, EDIT_FAQ_ERROR_MESSAGE);
                }
            } catch (DaoException e) {
                manager.rollback();
                throw new ReceiverException(e);
            } finally {
                manager.close();
            }
        }
        if (!errors.isEmpty()) {
            errors.put(EDIT_ERRORS, EDIT_ERRORS);
            requestContent.insertRequestAttribute(ERROR_MAP_NAME, errors);
        }
    }

    private boolean isValidFAQParams(FAQ faq, Map<String, String> errors) {
        boolean isValid = true;
        if (faq.getQuestion() == null || faq.getQuestion().isEmpty()) {
            isValid = false;
            errors.put(INVALID_FAQ_QUESTION_ERROR, INVALID_FAQ_QUESTION_MESSAGE);
        }
        if (faq.getAnswer() == null || faq.getAnswer().isEmpty()) {
            isValid = false;
            errors.put(INVALID_FAQ_ANSWER_ERROR, INVALID_FAQ_ANSWER_MESSAGE);
        }
        return isValid;
    }
}
