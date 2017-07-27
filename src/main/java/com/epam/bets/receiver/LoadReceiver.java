package com.epam.bets.receiver;

import com.epam.bets.entity.User;
import com.epam.bets.exception.ReceiverException;

import javax.servlet.http.Part;
import java.util.Collection;

public interface LoadReceiver extends Receiver{
    boolean loadPicture(Part part, String savePath) throws ReceiverException;
}
