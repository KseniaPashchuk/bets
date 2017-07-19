package com.epam.bets.receiver;

import com.epam.bets.entity.User;
import com.epam.bets.exception.ReceiverException;

public interface UserReceiver extends Receiver{
    boolean checkExistingUser(String login) throws ReceiverException;

    User signIn(String login, String password) throws ReceiverException;

    boolean signUp(User user) throws ReceiverException;

    User showProfileInfo (String login) throws ReceiverException;
}
