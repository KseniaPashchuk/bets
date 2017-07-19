package com.epam.bets.receiver;


import com.epam.bets.entity.News;
import com.epam.bets.exception.ReceiverException;

import java.time.LocalDate;
import java.util.List;

public interface NewsReceiver extends Receiver{
    List<News> showAllNews(LocalDate date) throws ReceiverException;
    News showPieceOfNews(String title) throws ReceiverException;
}
