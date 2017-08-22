package com.epam.bets.constant;

public class PageConstant {

    public static final String INDEX_PAGE = "/index.jsp";
    public static final String MAIN_PAGE = "/pages/common/main.jsp";
    public static final String SHOW_MAIN_PAGE = "/controller?command=show_main_page";
    public static final String REGISTRATION_PAGE = "/pages/guest/registration.jsp";
    public static final String PROFILE_PAGE = "/pages/common/profile.jsp";
    public static final String EDIT_PASSWORD_PAGE = "/pages/common/changePassword.jsp";
    public static final String AFTER_EDIT_PROFILE_PAGE = "/controller?command=show_user_profile";
    public static final String USER_BETS_PAGE = "/pages/common/userBets.jsp";
    public static final String NEWS_PAGE = "/pages/common/news.jsp";
    public static final String AFTER_CREATE_NEWS_PAGE = "/controller?command=edit_news_picture&news";
    public static final String AFTER_EDIT_NEWS_PAGE = "/controller?command=show_piece_of_news&title=";
    public static final String CHANGE_PASSWORD_PAGE = "/pages/common/changePassword.jsp";
    public static final String PIECE_OF_NEWS_PAGE = "/pages/common/pieceOfNews.jsp";
    public static final String FAQ_PAGE = "/pages/common/faq.jsp";
    public static final String AFTER_EDIT_FAQ_PAGE = "/controller?command=show_faq";//TODO
    public static final String MATCHES_PAGE = "/pages/common/matches.jsp";
    public static final String AFTER_EDIT_MATCHES_PAGE = "/controller?command=create_matches_page";
    public static final String RULES_PAGE = "";
    public static final String CONDITIONS_PAGE = "";
    public static final String SERVER_ERROR_PAGE = "/pages/error/error500.jsp";
    public static final String AFTER_SETTING_MATCH_SCORE = "/controller?command=calculate_gain&match_id=";
    public static final String REFILL_CASH_PAGE = "/pages/common/refillCash.jsp";
    private PageConstant() {
    }
}
