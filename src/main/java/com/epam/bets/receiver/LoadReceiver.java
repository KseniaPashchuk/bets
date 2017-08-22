package com.epam.bets.receiver;


import com.epam.bets.exception.ReceiverException;
import com.epam.bets.request.RequestContent;

import javax.servlet.http.Part;


public interface LoadReceiver extends Receiver {

    //    boolean updateAvatar(int userId, Part part, String savePath) throws ReceiverException;
//
//    boolean updateNewsPicture(int newsId, Part part, String savePath) throws ReceiverException;
//
//    boolean loadNewsPicture(int newsId, Part part, String savePath) throws ReceiverException;
    void updateAvatar(RequestContent requestContent) throws ReceiverException;

    void updateNewsPicture(RequestContent requestContent) throws ReceiverException;

    void loadNewsPicture(RequestContent requestContent) throws ReceiverException;
}
