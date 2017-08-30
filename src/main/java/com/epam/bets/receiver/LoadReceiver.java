package com.epam.bets.receiver;


import com.epam.bets.exception.ReceiverException;
import com.epam.bets.request.RequestContent;

import javax.servlet.http.Part;


public interface LoadReceiver extends Receiver {

      void updateAvatar(RequestContent requestContent) throws ReceiverException;

    void updateNewsPicture(RequestContent requestContent) throws ReceiverException;
}
