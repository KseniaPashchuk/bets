package com.epam.bets.receiver;

import com.epam.bets.entity.FAQ;
import com.epam.bets.exception.ReceiverException;

import java.util.List;

public interface FAQReceiver extends Receiver{

    List<FAQ> showAllFAQ()throws ReceiverException;
    boolean createFAQ()throws ReceiverException;
    boolean deleteFAQ()throws ReceiverException;
    boolean editFAQ()throws ReceiverException;
}
