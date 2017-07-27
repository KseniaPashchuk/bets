package com.epam.bets.receiver;

import com.epam.bets.entity.FAQ;
import com.epam.bets.exception.ReceiverException;

import java.util.List;

public interface FAQReceiver extends Receiver{

    List<FAQ> showAllFAQ()throws ReceiverException;
    boolean createFAQ(FAQ faq)throws ReceiverException;
    boolean deleteFAQ(String question)throws ReceiverException;
    boolean editFAQ(FAQ faq)throws ReceiverException;
}
