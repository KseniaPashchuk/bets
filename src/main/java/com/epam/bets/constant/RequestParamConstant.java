package com.epam.bets.constant;

/**
 * Class provides request param constants storage.
 *
 * @author Pashchuk Ksenia
 */
public final class RequestParamConstant {

    public final class CommonParam {
        public static final String DATE_PATTERN = "dd/MM/yyyy";
        public static final String PARAM_NAME_DATE = "date";
    }

    public final class MailParam{
        public static final String PARAM_NAME_MAIL_TEXT = "text";
        public static final String PARAM_NAME_ALL_MAIL = "allMail";
    }


    public final class UserParam {
        public static final String PARAM_NAME_USER_ID = "userId";
        public static final String PARAM_NAME_LOGIN = "login";
        public static final String PARAM_NAME_EMAIL = "email";
        public static final String PARAM_NAME_PASSWORD = "password";
        public static final String PARAM_NAME_OLD_PASSWORD = "currentPassword";
        public static final String PARAM_NAME_NEW_PASSWORD = "newPassword";
        public static final String PARAM_NAME_ROLE = "role";
        public static final String PARAM_NAME_FIRST_NAME = "name";
        public static final String PARAM_NAME_LAST_NAME = "surname";
        public static final String PARAM_NAME_CREDIT_CARD = "creditCard";
        public static final String PARAM_NAME_CREDIT_CARDS = "creditCards";
        public static final String PARAM_NAME_BIRTH_DATE = "birthDate";
        public static final String PARAM_NAME_AVATAR_URL = "avatarUrl";
        public static final String PARAM_NAME_BALANCE = "balance";
        public static final String PARAM_NAME_SUMM = "money";
        public static final String PARAM_NAME_BET_TYPE = "betType";
        public static final String PARAM_NAME_USER_BETS_TYPE = "type";
        public static final String PARAM_NAME_LOCALE = "locale";
        public static final String PASSWORD_RECOVER_MAIL_SUBJECT = "Password Recovery";
        public static final String PASSWORD_RECOVER_MAIL_TEXT_START = "We've received a request to reset your password. Your new password: ";
        public static final String PASSWORD_RECOVER_MAIL_TEXT_END = "\n The BETS Team.";
        public static final String PARAM_NAME_REFILL_AMOUNT = "amount";

    }

    public final class MatchParam {
        public static final String PARAM_NAME_ALL_CONFEDERATIONS = "all";
        public static final String PARAM_NAME_CONFEDERATION = "confederation";
        public static final String PARAM_NAME_TEAM_LIST = "teamList";
        public static final String PARAM_NAME_CONFEDERATION_LIST = "confederationList";
        public static final String PARAM_NAME_FIRST_TEAM = "firstTeam";
        public static final String PARAM_NAME_SECOND_TEAM = "secondTeam";
        public static final String PARAM_NAME_FW = "FW";
        public static final String PARAM_NAME_X = "X";
        public static final String PARAM_NAME_SW = "SW";
        public static final String PARAM_NAME_FWX = "FWX";
        public static final String PARAM_NAME_FS = "FS";
        public static final String PARAM_NAME_XSW = "XSW";
        public static final String PARAM_NAME_TL = "TL";
        public static final String PARAM_NAME_T = "T";
        public static final String PARAM_NAME_TM = "TM";
        public static final String PARAM_NAME_MAX_BET = "maxBet";
        public static final String DATE_TIME_PATTERN = "dd/MM/yyyy HH:mm";
        public static final String PARAM_NAME_TEAM = "team";
        public static final String PARAM_NAME_COUNTRY = "country";
        public static final String PARAM_NAME_MATCH_ID = "matchId";
        public static final String PARAM_NAME_FIRST_TEAM_SCORE = "firstTeamScore";
        public static final String PARAM_NAME_SECOND_TEAM_SCORE = "secondTeamScore";
    }

    public final class NewsParam {
        public static final String PARAM_NAME_NEWS_ID = "newsId";
        public static final String PARAM_NAME_TITLE = "title";
        public static final String PARAM_NAME_TEXT = "text";
        public static final String PARAM_NAME_PICTURE = "picture";
        public static final String PARAM_NAME_LAST_NEWS = "lastNews";
    }

    public final class FAQParam {
        public static final String PARAM_NAME_FAQ_LIST = "faqList";
        public static final String PARAM_NAME_QUESTION = "question";
        public static final String PARAM_NAME_ANSWER = "answer";
    }

    public final class LoadParam {
        public static final String NEWS_UPLOAD_DIR = "\\news";
        public static final String USER_UPLOAD_DIR = "\\user";
        public static final String UPLOADS = "uploadDir";
    }
}
