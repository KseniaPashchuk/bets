package com.epam.bets.receiver;

import com.epam.bets.entity.Bet;
import com.epam.bets.entity.CreditCards;
import com.epam.bets.entity.SupportMail;
import com.epam.bets.entity.User;
import com.epam.bets.exception.ReceiverException;
import com.epam.bets.request.RequestContent;

import java.math.BigDecimal;
import java.util.List;

public interface UserReceiver extends Receiver {

    void signIn(RequestContent requestContent) throws ReceiverException;

    void signUp(RequestContent requestContent) throws ReceiverException;

    void logout(RequestContent requestContent);

    void showProfileInfo(RequestContent requestContent) throws ReceiverException;

    void editProfile(RequestContent requestContent) throws ReceiverException;

    void editPassword(RequestContent requestContent) throws ReceiverException;

    void makeBet(RequestContent requestContent) throws ReceiverException;

    List<Bet> showBets(RequestContent requestContent) throws ReceiverException;

    void refillCash(RequestContent requestContent) throws ReceiverException;

    void recoverPassword(RequestContent requestContent) throws ReceiverException;

    void calculateGain(RequestContent requestContent) throws ReceiverException;

    void findAllCreditCards(RequestContent requestContent) throws ReceiverException;

    void findUserBalance(RequestContent requestContent) throws ReceiverException;

    void changeLocale(RequestContent requestContent);

    void sendSupportMail(RequestContent requestContent) throws ReceiverException;

    void showSupportMailChat(RequestContent requestContent) throws ReceiverException;

    List<SupportMail> showLastUsersMail(RequestContent requestContent) throws ReceiverException;

    void showUserInfo(RequestContent requestContent) throws ReceiverException;
}
