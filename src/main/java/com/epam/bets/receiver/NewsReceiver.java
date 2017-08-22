package com.epam.bets.receiver;


import com.epam.bets.entity.News;
import com.epam.bets.exception.ReceiverException;
import com.epam.bets.request.RequestContent;

import java.time.LocalDate;
import java.util.List;

public interface NewsReceiver extends Receiver {
    List<News> showAllNews(RequestContent requestContent) throws ReceiverException;

    void showPieceOfNews(RequestContent requestContent) throws ReceiverException;

    void showLastNews(RequestContent requestContent) throws ReceiverException;

    void createNews(RequestContent requestContent) throws ReceiverException;

    void deleteNews(RequestContent requestContent) throws ReceiverException;

    void editNews(RequestContent requestContent) throws ReceiverException;
}
