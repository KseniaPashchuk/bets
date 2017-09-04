package com.epam.bets.receiver.impl;

import com.epam.bets.dao.DaoFactory;
import com.epam.bets.dao.UserDAO;
import com.epam.bets.entity.User;
import com.epam.bets.receiver.UserReceiver;
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
public class UserReceiverImplTest {

    @Mock
    private UserDAO userDAO;
    @Mock
    private RequestContent requestContent;

    @InjectMocks
    private UserReceiverImpl userReceiver;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void signUpCallDao() throws Exception {
        User user = new User();
        when(userDAO.create(user)).thenReturn(1);
        userReceiver.signUp(requestContent);

        verify(userDAO).create(user);
    }

}