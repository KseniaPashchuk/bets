package com.epam.bets.constant;

/**
 * Class provides error constants storage.
 *
 * @author Pashchuk Ksenia
 */
public final class ErrorConstant {

    public static final String ERROR_LIST_NAME = "errors";
    public static final String SUCCESS = "success";

    public final class CommonError {
        public static final String INVALID_CREATE_PARAMS = "invalidCreateParams";
        public static final String INVALID_EDIT_PARAMS = "invalidEditParams";
        public static final String EXISTING_ENTITY = "existingEntityError";

    }
    public final class MailError {
        public static final String INVALID_MAIL_TEXT_ERROR = "invalidMailTextError";
        public static final String SHOW_SUPPORT_CHAT_ERROR = "showSupportChatError";
        public static final String SEND_SUPPORT_MAIL_ERROR = "sendSupportMailError";
    }
    public final class MatchError {
        public static final String SHOW_ALL_CONFEDERATIONS_ERROR = "Cannot find all confederations";
        public static final String SHOW_ALL_TEAMS_ERROR = "Cannot find all teams";
        public static final String CREATE_MATCH_ERROR = "createMatchError";
        public static final String EDIT_MATCH_ERROR = "editMatchError";
        public static final String SCORE_NOT_POSITIVE_ERROR = "scoreNotPositiveError";
        public static final String SET_SCORE_DATE_ERROR = "setScoreDateError";
        public static final String SAME_TEAM_ERROR = "sameTeamError";
        public static final String DATE_BEFORE_ERROR = "dateBeforeError";
        public static final String INVALID_MAX_BET_ERROR = "invalidMaxBetError";
        public static final String INVALID_COEFF_ERROR = "invalidCoeffError";
        public static final String SET_SCORE_ERROR = "setScoreError";
        public static final String CREATE_FOOTBALL_TEAM_ERROR = "createTeamError";
        public static final String CREATE_CONFEDERATION_ERROR = "createConfederationError";
        public static final String INVALID_CONFEDERATION_ERROR = "invalidConfederationError";
        public static final String INVALID_TEAM_NAME_ERROR = "invalidTeamNameError";
        public static final String INVALID_COUNTRY_ERROR = "invalidCountryError";

    }

    public final class UserError {
        public static final String INVALID_PARAMS_ERROR = "invalidParamsError";
        public static final String INVALID_PASSWORD_ERROR = "invalidPasswordError";
        public static final String INVALID_LOGIN_ERROR = "invalidLoginError";
        public static final String INVALID_FIRST_NAME_ERROR = "invalidFirstNameError";
        public static final String INVALID_LAST_NAME_ERROR = "invalidLastNameError";
        public static final String INVALID_BIRTH_DATE_ERROR = "invalidBirthDateError";
        public static final String INVALID_CREDIT_CARD_ERROR = "invalidCreditCardError";
        public static final String INVALID_CURRENT_PASSWORD_ERROR = "invalidCurrentPasswordError";
        public static final String INVALID_NEW_PASSWORD_ERROR = "invalidNewPasswordError";
        public static final String SIGN_IN_ERROR = "signInError";
        public static final String SIGN_UP_ERROR = "signUpError";
        public static final String CHANGE_PASSWORD_ERROR = "changePasswordError";
        public static final String CHANGE_PROFILE_ERROR = "changeProfileError";
        public static final String SHOW_CREDIT_CARD_ERROR = "showCreditCardError";
        public static final String REFILL_CASH_ERROR = "refillCashError";
        public static final String INVALID_REFILL_AMOUNT = "invalidRefillAmount";
        public static final String UPDATE_USER_AVATAR_ERROR = "updateUserAvatarError";
        public static final String NOT_EQUAL_CURRENT_PASSWORD_ERROR = "notEqualCurrentPasswordError";
        public static final String MAKE_BET_ERROR = "makeBetError";
        public static final String TOO_BIG_BET_SUMM = "betSummBiggerThanMaxBetError";
        public static final String NOT_ENOUGH_MONEY = "notEnoughMoneyError";
        public static final String SUMM_NOT_POSITIVE = "summNotPositiveError";
        public static final String RECOVER_PASSWORD_ERROR = "recoverPasswordError";
        public static final String INVALID_EMAIL_ERROR = "invalidEmailError";

        public static final String NO_SUCH_USER_ERROR = "noSuchUserError";
        public static final String SHOW_PROFILE_ERROR = "showProfileError";
    }

    public final class NewsError {
        public static final String SHOW_PIECE_OF_NEWS_ERROR = "showNewsError";
        public static final String DELETE_NEWS_ERROR = "deleteNewsError";
        public static final String INVALID_NEWS_TITLE_ERROR = "invalidNewsTitleError";
        public static final String INVALID_NEWS_TEXT_ERROR = "invalidNewsTextError";
        public static final String CREATE_NEWS_ERROR = "createNewsError";
        public static final String EDIT_NEWS_ERROR = "editNewsError";
        public static final String SHOW_LAST_NEWS_ERROR = "showLastNewsError";
        public static final String UPDATE_NEWS_PICTURE_ERROR = "updateNewsPictureError";
    }

    public final class FAQError {
        public static final String CREATE_FAQ_ERROR = "createFAQError";
        public static final String EDIT_FAQ_ERROR = "editFAQError";
        public static final String DELETE_FAQ_ERROR = "deleteFAQError";
        public static final String SHOW_FAQ_ERROR = "showFAQError";
        public static final String INVALID_FAQ_QUESTION_ERROR = "invalidFAQQuestionError";
        public static final String INVALID_FAQ_ANSWER_ERROR = "invalidFAQAnswerError";
    }
}
