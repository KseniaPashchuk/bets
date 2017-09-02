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
     * Checks if news params(question and answer) are valid.
     *
     * @param faq   - faq information
     * @param errors - for validation errors storage
     * @return true if all news information is valid
     */
    public boolean validateFAQParams(FAQ faq, List<String> errors) {
        boolean isValid = true;
        if (!validateStringParamWithLimit(faq.getQuestion(), MAX_QUESTION_LENGTH)) {
            isValid = false;
            errors.add(INVALID_FAQ_QUESTION_ERROR);
        }
        if (!validateStringParamWithLimit(faq.getAnswer(), MAX_ANSWER_LENGTH)) {
            isValid = false;
            errors.add(INVALID_FAQ_ANSWER_ERROR);
        }
        return isValid;
    }
}
