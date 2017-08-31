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

public class NewsValidator extends BaseValidator{

    private static final int MAX_NEWS_TITLE_LENGTH = 45;
    private static final int MAX_NEWS_TEXT_LENGTH = 1000;

    /**
     * Checks if news title is valid
     *
     * @param title - news title
     * @return true if title is not null or empty or longer than max lenght
     */
    public boolean validateTitle(String title) {
        return validateStringParam(title) && title.length() <= MAX_NEWS_TITLE_LENGTH;
    }

    /**
     * Checks if login is valid
     *
     * @param text - news text
     * @return true if text is not null or empty or longer than max lenght
     */
    public boolean validateText(String text) {
        return validateStringParam(text) && text.length() <= MAX_NEWS_TEXT_LENGTH;
    }

    /**
     * Checks if news params(title and text) are valid.
     *
     * @param news   - news information
     * @param errors - for validation errors storage
     * @return true if all news information is valid
     */
    public boolean validateNewsParams(News news, List<String> errors) {
        boolean isValid = true;
        if (!validateTitle(news.getTitle())) {
            isValid = false;
            errors.add(INVALID_NEWS_TITLE_ERROR);
        }
        if (!validateText(news.getText())) {
            isValid = false;
            errors.add(INVALID_NEWS_TEXT_ERROR);
        }
        return isValid;
    }
}
