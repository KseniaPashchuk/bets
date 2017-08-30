package com.epam.bets.validator;


import com.epam.bets.entity.FAQ;

import java.util.List;

import static com.epam.bets.constant.ErrorConstant.FAQError.INVALID_FAQ_ANSWER_ERROR;
import static com.epam.bets.constant.ErrorConstant.FAQError.INVALID_FAQ_QUESTION_ERROR;

public class FAQValidator {
    private static final int MAX_QUESTION_LENGTH = 60;
    private static final int MAX_ANSWER_LENGTH = 500;

    public  boolean validateQuestion(String question) {
        return question != null && !question.trim().isEmpty() && question.length() <= MAX_QUESTION_LENGTH;
    }
    public  boolean validateAnswer(String answer) {
        return answer != null && !answer.trim().isEmpty() && answer.length() <= MAX_ANSWER_LENGTH;
    }
    public boolean validateFAQParams(FAQ faq, List<String> errors) {
        boolean isValid = true;
        if (!validateQuestion(faq.getQuestion())) {
            isValid = false;
            errors.add(INVALID_FAQ_QUESTION_ERROR);
        }
        if (!validateAnswer(faq.getAnswer())) {
            isValid = false;
            errors.add(INVALID_FAQ_ANSWER_ERROR);
        }
        return isValid;
    }
}
