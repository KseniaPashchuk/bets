package com.epam.bets.validator;


import com.epam.bets.entity.News;

import java.util.List;

import static com.epam.bets.constant.ErrorConstant.NewsError.INVALID_NEWS_TEXT_ERROR;
import static com.epam.bets.constant.ErrorConstant.NewsError.INVALID_NEWS_TITLE_ERROR;

public class NewsValidator {

    private static final int MAX_NEWS_TITLE_LENGTH = 45;
    private static final int MAX_NEWS_TEXT_LENGTH = 1000;


    public boolean validateTitle(String title) {
        return title != null && !title.trim().isEmpty() && title.length() <= MAX_NEWS_TITLE_LENGTH;
    }

    public boolean validateText(String text) {
        return text != null && !text.trim().isEmpty() && text.length() <= MAX_NEWS_TEXT_LENGTH;
    }


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
