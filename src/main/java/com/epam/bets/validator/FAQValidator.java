package com.epam.bets.validator;


import com.epam.bets.entity.FAQ;

import java.util.List;

import static com.epam.bets.constant.ErrorConstant.FAQError.INVALID_FAQ_ANSWER_ERROR;
import static com.epam.bets.constant.ErrorConstant.FAQError.INVALID_FAQ_QUESTION_ERROR;

/**
 * The class for FAQ params validation.
 *
 * @author Pashchuk Ksenia
 */

public class FAQValidator extends BaseValidator{
    private static final int MAX_QUESTION_LENGTH = 60;
    private static final int MAX_ANSWER_LENGTH = 500;

    /**
     * Checks if news title is valid
     *
     * @param question - faq question
     * @return true if question is not null or empty or longer than max lenght
     */
    public boolean validateQuestion(String question) {
        return validateStringParam(question) && question.length() <= MAX_QUESTION_LENGTH;
    }

    /**
     * Checks if news title is valid
     *
     * @param answer - faq answer
     * @return true if answer is not null or empty or longer than max lenght
     */
    public boolean validateAnswer(String answer) {
        return validateStringParam(answer) && answer.length() <= MAX_ANSWER_LENGTH;
    }

    /**
     * Checks if news params(question and answer) are valid.
     *
     * @param faq   - faq information
     * @param errors - for validation errors storage
     * @return true if all news information is valid
     */
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
