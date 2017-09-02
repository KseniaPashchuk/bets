package com.epam.bets.validator;


import com.epam.bets.entity.News;

import java.util.List;

import static com.epam.bets.constant.ErrorConstant.NewsError.INVALID_NEWS_TEXT_ERROR;
import static com.epam.bets.constant.ErrorConstant.NewsError.INVALID_NEWS_TITLE_ERROR;

/**
 * The class for news params validation.
 *
 * @author Pashchuk Ksenia
 */

public class NewsValidator extends BaseValidator {

    private static final int MAX_NEWS_TITLE_LENGTH = 45;
    private static final int MAX_NEWS_TEXT_LENGTH = 1000;


    /**
     * Checks if news params(title and text) are valid.
     *
     * @param news   - news information
     * @param errors - for validation errors storage
     * @return true if all news information is valid
     */
    public boolean validateNewsParams(News news, List<String> errors) {
        boolean isValid = true;
        if (!validateStringParamWithLimit(news.getTitle(), MAX_NEWS_TITLE_LENGTH)) {
            isValid = false;
            errors.add(INVALID_NEWS_TITLE_ERROR);
        }
        if (!validateStringParamWithLimit(news.getText(), MAX_NEWS_TEXT_LENGTH)) {
            isValid = false;
            errors.add(INVALID_NEWS_TEXT_ERROR);
        }
        return isValid;
    }
}
