package com.epam.bets.receiver;

import com.epam.bets.entity.User;
import com.epam.bets.exception.ReceiverException;

public interface UserReceiver extends Receiver{

    User signIn(String login, String password) throws ReceiverException;

    int signUp(User user) throws ReceiverException;

    User showProfileInfo (int userId) throws ReceiverException;

    boolean editProfile(User user) throws ReceiverException;

    boolean editPassword(String login, String oldPassword, String newPassword) throws ReceiverException;

    boolean editAvatar(int userId, String avatarUrl) throws ReceiverException;
}
