package com.epam.bets.command.common;

import com.epam.bets.entity.Match;
import com.epam.bets.exception.CommandException;
import com.epam.bets.exception.ReceiverException;
import com.epam.bets.navigator.PageNavigator;
import com.epam.bets.receiver.MatchReceiver;
import com.epam.bets.receiver.impl.MatchReceiverImpl;
import com.epam.bets.request.RequestContent;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static com.epam.bets.constant.PageConstant.SERVER_ERROR_PAGE;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;


public class ShowMatchesCommandTest {

    @Mock
    private RequestContent requestContent;

    @Mock
    private MatchReceiver receiver;

    @InjectMocks
    private ShowMatchesCommand command;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = CommandException.class)
    public void executeInvalidReturnCheck() throws CommandException, ReceiverException {
        doThrow(new ReceiverException()).when(receiver).showMatches(requestContent);
        command.execute(requestContent);
    }

    @Test
    public void executeValidReturnCheck() throws CommandException, ReceiverException {
        List<Match> matchList = new ArrayList<>();
        when(receiver.showMatches(requestContent)).thenReturn(matchList);
        Assert.assertEquals(matchList, command.execute(requestContent));
    }
    @Test
    public void executeNullReturnCheck() throws CommandException, ReceiverException {
        when(receiver.showMatches(requestContent)).thenReturn(null);
        Assert.assertNull(command.execute(requestContent));
    }

}