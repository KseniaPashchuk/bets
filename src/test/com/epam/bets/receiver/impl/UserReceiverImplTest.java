package com.epam.bets.receiver.impl;

import com.epam.bets.dao.UserDAO;
import com.epam.bets.dao.impl.UserDAOImpl;
import com.epam.bets.entity.User;
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

import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class UserReceiverImplTest {

    @Mock
    private UserDAOImpl userDAO;
    @Mock
    private RequestContent requestContent;
    @Mock
    private User user;
    @InjectMocks
    private UserReceiverImpl userReceiver;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = ReceiverException.class)
    public void showBetsCallDao() throws ReceiverException, DaoException {

        when(userDAO.create(user)).thenThrow(DaoException.class);
        userReceiver.signUp(requestContent);
    }
}