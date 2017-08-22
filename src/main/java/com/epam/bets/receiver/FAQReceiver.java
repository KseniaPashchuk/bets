package com.epam.bets.receiver;

import com.epam.bets.entity.FAQ;
import com.epam.bets.exception.ReceiverException;
import com.epam.bets.request.RequestContent;

import java.util.List;

public interface FAQReceiver extends Receiver {

    void showAllFAQ(RequestContent requestContent) throws ReceiverException;

    void createFAQ(RequestContent requestContent) throws ReceiverException;

    void deleteFAQ(RequestContent requestContent) throws ReceiverException;

    void editFAQ(RequestContent requestContent) throws ReceiverException;
}
