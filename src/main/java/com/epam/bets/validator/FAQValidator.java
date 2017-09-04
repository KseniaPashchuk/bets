package com.epam.bets.validator;


import com.epam.bets.entity.FAQ;
import com.epam.bets.request.RequestContent;

import java.util.List;

import static com.epam.bets.constant.ErrorConstant.FAQError.INVALID_FAQ_ANSWER_ERROR;
import static com.epam.bets.constant.ErrorConstant.FAQError.INVALID_FAQ_QUESTION_ERROR;
import static com.epam.bets.constant.RequestParamConstant.FAQParam.PARAM_NAME_ANSWER;
import static com.epam.bets.constant.RequestParamConstant.FAQParam.PARAM_NAME_QUESTION;

/**
 * The class for FAQ params validation.
 *
 * @author Pashchuk Ksenia
 */

public class FAQValidator extends BaseValidator{
    private static final int MAX_QUESTION_LENGTH = 60;
    private static final int MAX_ANSWER_LENGTH = 500;

    public boolean validateFAQQuestion(String question, List<String> errors) {
        boolean isValid = true;
        if (!validateStringParamWithLimit(question, MAX_QUESTION_LENGTH)) {
            isValid = false;
            errors.add(INVALID_FAQ_QUESTION_ERROR);
        }
        return isValid;
    }


    /**
     * Checks if news params(question and answer) are valid.
     *
     * @param requestContent   - faq information
     * @param errors - for validation errors storage
     * @return true if all news information is valid
     */
    public boolean validateFAQParams(RequestContent requestContent, List<String> errors) {
        boolean isValid = true;
        if (!validateStringParamWithLimit(requestContent.findParameterValue(PARAM_NAME_QUESTION), MAX_QUESTION_LENGTH)) {
            isValid = false;
            errors.add(INVALID_FAQ_QUESTION_ERROR);
        }
        if (!validateStringParamWithLimit(requestContent.findParameterValue(PARAM_NAME_ANSWER), MAX_ANSWER_LENGTH)) {
            isValid = false;
            errors.add(INVALID_FAQ_ANSWER_ERROR);
        }
        return isValid;
    }
}
