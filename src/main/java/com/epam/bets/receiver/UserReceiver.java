package com.epam.bets.receiver;

import com.epam.bets.entity.Bet;
import com.epam.bets.entity.CreditCards;
import com.epam.bets.entity.SupportMail;
import com.epam.bets.entity.User;
import com.epam.bets.exception.DaoException;
import com.epam.bets.exception.ReceiverException;
import com.epam.bets.request.RequestContent;

import java.math.BigDecimal;
import java.util.List;

/**
 * The class provides Receiver interface layer for action with {@link User}.
 *
 * @author Pashchuk Ksenia
 */
public interface UserReceiver extends Receiver {

    /**
     * Provides signing in operation
     *
     * @param requestContent - user info
     * @throws ReceiverException if {@link DaoException} occurred while working
     */
    void signIn(RequestContent requestContent) throws ReceiverException;

    /**
     * Provides signing up operation
     *
     * @param requestContent - user info
     * @throws ReceiverException if {@link DaoException} occurred while working
     */
    void signUp(RequestContent requestContent) throws ReceiverException;

    /**
     * Provides log out operation
     *
     * @param requestContent - user info
     * @throws ReceiverException if {@link DaoException} occurred while working
     */
    void logout(RequestContent requestContent);

    /**
     * Provides showing user profile operation
     *
     * @param requestContent - user info
     * @throws ReceiverException if {@link DaoException} occurred while working
     */
    void showProfileInfo(RequestContent requestContent) throws ReceiverException;

    /**
     * Provides editing user profile operation
     *
     * @param requestContent - user info
     * @throws ReceiverException if {@link DaoException} occurred while working
     */
    void editProfile(RequestContent requestContent) throws ReceiverException;

    /**
     * Provides editing user password operation
     *
     * @param requestContent - user info
     * @throws ReceiverException if {@link DaoException} occurred while working
     */
    void editPassword(RequestContent requestContent) throws ReceiverException;

    /**
     * Provides making bet operation for user
     *
     * @param requestContent - user info
     * @throws ReceiverException if {@link DaoException} occurred while working
     */
    void makeBet(RequestContent requestContent) throws ReceiverException;

    /**
     * Provides showing user bets operation
     *
     * @param requestContent - user info
     * @throws ReceiverException if {@link DaoException} occurred while working
     */
    List<Bet> showBets(RequestContent requestContent) throws ReceiverException;

    /**
     * Provides refill cash operation for user
     *
     * @param requestContent - user info
     * @throws ReceiverException if {@link DaoException} occurred while working
     */
    void refillCash(RequestContent requestContent) throws ReceiverException;

    /**
     * Provides recovering user password operation
     *
     * @param requestContent - user info
     * @throws ReceiverException if {@link DaoException} occurred while working
     */
    void recoverPassword(RequestContent requestContent) throws ReceiverException;

    /**
     * Provides calculationg user gain operation
     *
     * @param requestContent - user info
     * @throws ReceiverException if {@link DaoException} occurred while working
     */
    void calculateGain(RequestContent requestContent) throws ReceiverException;

    /**
     * Provides finding all user credit cards operation
     *
     * @param requestContent - user info
     * @throws ReceiverException if {@link DaoException} occurred while working
     */
    void findAllCreditCards(RequestContent requestContent) throws ReceiverException;

    /**
     * Provides finding user balance operation
     *
     * @param requestContent - user info
     * @throws ReceiverException if {@link DaoException} occurred while working
     */
    void findUserBalance(RequestContent requestContent) throws ReceiverException;

    /**
     * Provides chanding locale operation
     *
     * @param requestContent - user info
     * @throws ReceiverException if {@link DaoException} occurred while working
     */
    void changeLocale(RequestContent requestContent);

    /**
     * Provides showing user info operation for admin.
     *
     * @param requestContent - user info
     * @throws ReceiverException if {@link DaoException} occurred while working
     */
    void showUserInfo(RequestContent requestContent) throws ReceiverException;
}
