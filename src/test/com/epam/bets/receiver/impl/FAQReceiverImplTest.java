package com.epam.bets.receiver.impl;

import com.epam.bets.dao.DaoFactory;
import com.epam.bets.dao.FaqDAO;
import com.epam.bets.exception.DaoException;
import com.epam.bets.exception.ReceiverException;
import com.epam.bets.request.RequestContent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FAQReceiverImplTest {

    @Mock
    private FaqDAO faqDAO;

    @Mock
    private DaoFactory daoFactory;

    @Mock
    private RequestContent requestContent;

    @InjectMocks
    private FAQReceiverImpl faqReceiver;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        when(daoFactory.getFaqDao()).thenReturn(faqDAO);
    }

    @Test(expected = ReceiverException.class)
    public void showAllFAQ() throws DaoException, ReceiverException {
        doThrow(DaoException.class).when(faqDAO).findAllFAQ();
        faqReceiver.showAllFAQ(requestContent);
    }
}