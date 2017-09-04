package com.epam.bets.receiver.impl;


import com.epam.bets.dao.DaoFactory;
import com.epam.bets.dao.FaqDAO;
import com.epam.bets.dao.impl.FaqDAOImpl;
import com.epam.bets.exception.DaoException;
import com.epam.bets.exception.ReceiverException;
import com.epam.bets.receiver.FAQReceiver;
import com.epam.bets.request.RequestContent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)

public class FAQReceiverImplTest {

    @Mock
    private DaoFactory daoFactory;


    @Mock
    private FaqDAO faqDAO;

    @InjectMocks
    private FAQReceiver faqReceiver;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void showAllFAQ() throws ReceiverException, DaoException {
        RequestContent requestContent = new RequestContent();
        when(faqDAO.findAllFAQ()).thenReturn(null);
        faqReceiver.showAllFAQ(requestContent);
        verify(faqDAO).findAllFAQ();
    }


}