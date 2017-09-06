package com.epam.bets.constant;

/**
 * Class provides page navigation constants storage.
 *
 * @author Pashchuk Ksenia
 */
public class PageConstant {

    public static final String INDEX_PAGE = "/index.jsp";

    public static final String MAIN_PAGE = "/pages/common/main.jsp";
    public static final String SHOW_MAIN_PAGE = "/controller?command=show_main_page";

    public static final String REGISTRATION_PAGE = "/pages/guest/registration.jsp";
    public static final String PROFILE_PAGE = "/pages/common/profile.jsp";
    public static final String SHOW_PROFILE_PAGE = "/controller?command=show_user_profile";

    public static final String CHANGE_PASSWORD_PAGE = "/pages/common/changePassword.jsp";
    public static final String NEWS_PAGE = "/pages/common/news.jsp";
    public static final String SHOW_NEWS_PAGE = "/controller?command=show_news_page&date=";
    public static final String AFTER_CREATE_NEWS_PAGE = "/controller?command=edit_news_picture";
    public static final String SHOW_PIECE_OF_NEWS_PAGE = "/controller?command=show_piece_of_news&title=";
    public static final String PIECE_OF_NEWS_PAGE = "/pages/common/pieceOfNews.jsp";


    public static final String FAQ_PAGE = "/pages/common/faq.jsp";
    public static final String SHOW_FAQ_PAGE = "/controller?command=show_faq";

    public static final String MATCHES_PAGE = "/pages/common/matches.jsp";
    public static final String MATCH_RESULTS_PAGE = "/pages/common/matchResults.jsp";
    public static final String SHOW_MATCHES_PAGE = "/controller?command=show_matches_page&confederation=";
    public static final String SHOW_MATCH_RESULTS_PAGE = "/controller?command=show_match_results_page";
    public static final String AFTER_SETTING_MATCH_SCORE = "/controller?command=calculate_gain&matchId=";
    public static final String CREATE_TEAM_PAGE = "/pages/admin/addTeam.jsp";
    public static final String CREATE_CONFEDERATION_PAGE = "/pages/admin/addConfederation.jsp";
    public static final String SHOW_CREATE_TEAM_PAGE = "/controller?command=show_add_team_page";
    public static final String SHOW_CREATE_CONFEDERATION_PAGE = "/controller?command=show_add_confederation_page";

    public static final String SERVER_ERROR_PAGE = "/pages/error/error500.jsp";

    public static final String REFILL_CASH_PAGE = "/pages/user/refillCash.jsp";
    public static final String SHOW_REFILL_CASH_PAGE = "/controller?command=create_refill_cash_page";

    public static final String SUPPORT_PAGE = "/pages/admin/support.jsp";
    public static final String SHOW_SUPPORT_PAGE = "/controller?command=show_support_page";
    public static final String SUPPORT_CHAT_PAGE = "/pages/common/supportChat.jsp";
    public static final String SHOW_SUPPORT_CHAT_PAGE = "/controller?command=show_support_chat&email=";

    public static final String SHOW_USER_INFO = "/controller?command=show_user_info&login=";
    public static final String USER_SETTING_PAGE = "/pages/admin/userSetting.jsp";

    public static final String BETS_PAGE = "/pages/user/userBets.jsp";
    public static final String SHOW_BETS_PAGE = "/controller?command=show_bets_page&type=";

    private PageConstant() {
    }
}
