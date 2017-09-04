package com.epam.bets.validator;


import com.epam.bets.entity.News;
import com.epam.bets.request.RequestContent;

import java.util.List;

import static com.epam.bets.constant.ErrorConstant.NewsError.INVALID_NEWS_TEXT_ERROR;
import static com.epam.bets.constant.ErrorConstant.NewsError.INVALID_NEWS_TITLE_ERROR;
import static com.epam.bets.constant.RequestParamConstant.NewsParam.PARAM_NAME_TEXT;
import static com.epam.bets.constant.RequestParamConstant.NewsParam.PARAM_NAME_TITLE;

/**
 * The class for news params validation.
 *
 * @author Pashchuk Ksenia
 */

public class NewsValidator extends BaseValidator {

    private static final int MAX_NEWS_TITLE_LENGTH = 45;
    private static final int MAX_NEWS_TEXT_LENGTH = 1000;

    /**
     * Checks if news title is valid.
     *
     * @param title - news title
     * @param errors         - for validation errors storage
     * @return true if  news title is valid
     */
    public boolean validateNewsTitle(String title, List<String> errors) {
        boolean isValid = true;
        if (!validateStringParamWithLimit(title, MAX_NEWS_TITLE_LENGTH)) {
            isValid = false;
            errors.add(INVALID_NEWS_TITLE_ERROR);
        }
        return isValid;
    }

    /**
     * Checks if news params(title and text) are valid.
     *
     * @param requestContent - news information
     * @param errors         - for validation errors storage
     * @return true if all news information is valid
     */
    public boolean validateNewsParams(RequestContent requestContent, List<String> errors) {
        boolean isValid = true;
        if (!validateStringParamWithLimit(requestContent.findParameterValue(PARAM_NAME_TITLE), MAX_NEWS_TITLE_LENGTH)) {
            isValid = false;
            errors.add(INVALID_NEWS_TITLE_ERROR);
        }
        if (!validateStringParamWithLimit(requestContent.findParameterValue(PARAM_NAME_TEXT), MAX_NEWS_TEXT_LENGTH)) {
            isValid = false;
            errors.add(INVALID_NEWS_TEXT_ERROR);
        }
        return isValid;
    }
}
