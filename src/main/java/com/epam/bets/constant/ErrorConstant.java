package com.epam.bets.constant;


public class ErrorConstant {

    public static final String ERROR_MAP_NAME = "errors";

    public static final String INVALID_PARAMS_ERROR = "invalidParams";
    public static final String INVALID_PARAMS_MESSAGE = "login.error.invalid_params";


    public static final String EXISTING_USER_MESSAGE = "signup.error.existing_user";

    public static final String INVALID_PASSWORD_ERROR = "invalidPassword";
    public static final String INVALID_PASSWORD_MESSAGE = "signup.error.invalid_password";

    public static final String INVALID_LOGIN_ERROR = "invalidLogin";
    public static final String INVALID_LOGIN_MESSAGE = "signup.error.invalid_login";

    public static final String INVALID_FIRST_NAME_ERROR = "invalidFirstName";
    public static final String INVALID_FIRST_NAME_MESSAGE = "signup.error.invalid_first_name";

    public static final String INVALID_LAST_NAME_ERROR = "invalidLastName";
    public static final String INVALID_LAST_NAME_MESSAGE = "signup.error.invalid_last_name";

    public static final String INVALID_BIRTH_DATE_ERROR = "invalidBirthDate";
    public static final String INVALID_BIRTH_DATE_MESSAGE = "signup.error.invalid_birth_date";

    public static final String INVALID_CREDIT_CARD_ERROR = "invalidCreditCard";
    public static final String INVALID_CREDIT_CARD_MESSAGE = "signup.error.invalid_credit_card";

    public static final String INVALID_CURRENT_PASSWORD_MESSAGE = "common.profile.change_password.invalid_current";

    public static final String INVALID_NEW_PASSWORD_MESSAGE = "common.profile.change_password.invalid_new";

    public static final String SUCCESS = "success";
    public static final String ERROR = "error";

    public static final String SIGN_IN_ERROR_MESSAGE = "login.error.enter_error";
    public static final String SIGN_UP_ERROR_MESSAGE = "signup.error.register.error";
    public static final String CHANGE_PASSWORD_SUCCESS_MESSAGE = "common.profile.change_password.success";
    public static final String CHANGE_PASSWORD_ERROR_MESSAGE = "common.profile.change_password.error";
    public static final String CHANGE_PROFILE_SUCCESS_MESSAGE = "common.profile.edit.success";
    public static final String CHANGE_PROFILE_ERROR_MESSAGE = "common.profile.edit.error";

    public static final String REFILL_CASH_ERROR_MESSAGE = "";//TODO

    public static final String SHOW_ALL_CONFEDERATIONS_ERROR_MESSAGE = "Cannot find all confederations";
    public static final String SHOW_ALL_TEAMS_ERROR_MESSAGE = "Cannot find all teams";
    public static final String CREATE_MATCH_ERROR_MESSAGE = "bookmaker.match.create.error";
    public static final String EDIT_MATCH_ERROR_MESSAGE = "bookmaker.match.edit.error";
    public static final String CREATE_FOOTBALL_TEAM_ERROR_MESSAGE = "admin.match.create_team.error";

    public static final String SCORE_NOT_POSITIVE_ERROR = "scoreNotPositive";
    public static final String SCORE_NOT_POSITIVE_ERROR_MESSAGE = "bookmaker.match.set_score.not_positive_error";

    public static final String SET_SCORE_DATE_ERROR = "setScoreDateError";
    public static final String SET_SCORE_DATE_ERROR_MESSAGE = "bookmaker.match.set_score.date.error";

    public static final String SET_SCORE_ERROR = "bookmaker.match.set_score.error";

    public static final String SHOW_PIECE_OF_NEWS_ERROR_MESSAGE = "common.news.piece.error";
    public static final String DELETE_NEWS_ERROR_MESSAGE = "admin.news.delete.error";

    public static final String INVALID_NEWS_TITLE_ERROR = "invalidNewsTitle";
    public static final String INVALID_NEWS_TITLE_MESSAGE = "admin.news.create.invalid_title";

    public static final String INVALID_NEWS_TEXT_ERROR = "invalidNewsText";
    public static final String INVALID_NEWS_TEXT_MESSAGE = "admin.news.create.invalid_text";

    public static final String CREATE_NEWS_ERROR_MESSAGE = "admin.news.create.error";
    public static final String EDIT_NEWS_ERROR_MESSAGE = "admin.news.edit.error";
    public static final String SHOW_FAQ_ERROR_MESSAGE = "common.faq.error";
    public static final String DELETE_FAQ_ERROR_MESSAGE = "admin.faq.delete.error";

    public static final String CREATE_ERRORS = "createErrors";
    public static final String EDIT_ERRORS = "editErrors";

    public static final String INVALID_FAQ_QUESTION_ERROR = "invalidFAQQuestion";
    public static final String INVALID_FAQ_QUESTION_MESSAGE = "admin.faq.create.invalid_question";

    public static final String INVALID_FAQ_ANSWER_ERROR = "invalidFAQAnswer";
    public static final String INVALID_FAQ_ANSWER_MESSAGE = "admin.faq.create.invalid_answer";
    public static final String CREATE_FAQ_ERROR_MESSAGE = "admin.faq.create.error";
    public static final String EDIT_FAQ_ERROR_MESSAGE = "admin.faq.edit.error";
    public static final String UPDATE_NEWS_PICTURE_ERROR_MESSAGE = "admin.news.update_picture.error";
    public static final String UPDATE_USER_AVATAR_ERROR_MESSAGE = "common.profile.update_avatar.error";

    public static final String NOT_EQUAL_CURRENT_PASSWORD_ERROR = "notEqualCurrentPassword";
    public static final String NOT_EQUAL_CURRENT_PASSWORD_MESSAGE = "common.profile.change_password.current_not_match";

    public static final String MAKE_BET_ERROR_MESSAGE = "common.bets.make_bet.error";
    public static final String RECOVER_PASSWORD_ERROR_MESSAGE = "common.recover_password.error";

    private ErrorConstant() {
    }
}
