package com.epam.bets.command.common;

import com.epam.bets.exception.ReceiverException;
import com.epam.bets.navigator.PageNavigator;
import com.epam.bets.receiver.impl.UserReceiverImpl;
import com.epam.bets.request.RequestContent;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static com.epam.bets.constant.ErrorConstant.ERROR_LIST_NAME;
import static com.epam.bets.constant.PageConstant.REGISTRATION_PAGE;
import static com.epam.bets.constant.PageConstant.SERVER_ERROR_PAGE;
import static com.epam.bets.constant.PageConstant.SHOW_MAIN_PAGE;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SignUpCommandTest {

    @Mock
    private UserReceiverImpl receiver;

    @InjectMocks
    private SignUpCommand command;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void executeReturnCheck() throws ReceiverException {
        RequestContent requestContent = new RequestContent();
        doThrow(new ReceiverException()).when(receiver).signUp(requestContent);
        Assert.assertEquals(new PageNavigator(SERVER_ERROR_PAGE, PageNavigator.PageType.REDIRECT), command.execute(requestContent));
    }

    @Test
    public void executeValidReturnCheck() throws ReceiverException {
        RequestContent requestContent = new RequestContent();
        Assert.assertEquals(new PageNavigator(SHOW_MAIN_PAGE, PageNavigator.PageType.REDIRECT), command.execute(requestContent));
    }

    @Test
    public void executeInvalidReturnCheck() throws ReceiverException {
        RequestContent requestContent = new RequestContent();
        List<String> errors = new ArrayList<>();
        requestContent.insertRequestAttribute(ERROR_LIST_NAME, errors);
        Assert.assertEquals(new PageNavigator(REGISTRATION_PAGE, PageNavigator.PageType.FORWARD), command.execute(requestContent));

    }

    @Test
    public void executeCheckSavePrevRequest() throws ReceiverException {
        RequestContent requestContent = new RequestContent();
        command.execute(requestContent);
        Assert.assertEquals(SHOW_MAIN_PAGE, requestContent.findSessionAttribute("prevRequest"));
    }

    @Test
    public void executeCheckSaveInvalidPrevRequest() throws ReceiverException {
        RequestContent requestContent = new RequestContent();
        List<String> errors = new ArrayList<>();
        requestContent.insertRequestAttribute(ERROR_LIST_NAME, errors);
        command.execute(requestContent);
        Assert.assertEquals(REGISTRATION_PAGE, requestContent.findSessionAttribute("prevRequest"));
    }
}