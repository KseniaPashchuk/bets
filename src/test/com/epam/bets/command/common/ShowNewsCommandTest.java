package com.epam.bets.command.common;

import com.epam.bets.entity.News;
import com.epam.bets.exception.CommandException;
import com.epam.bets.exception.ReceiverException;
import com.epam.bets.receiver.NewsReceiver;
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

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ShowNewsCommandTest {

    @Mock
    private RequestContent requestContent;

    @Mock
    private NewsReceiver receiver;

    @InjectMocks
    private ShowNewsCommand command;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = CommandException.class)
    public void executeInvalidReturnCheck() throws CommandException, ReceiverException {
        doThrow(new ReceiverException()).when(receiver).showAllNews(requestContent);
        command.execute(requestContent);
    }

    @Test
    public void executeValidReturnCheck() throws CommandException, ReceiverException {
        List<News> newsList = new ArrayList<>();
        when(receiver.showAllNews(requestContent)).thenReturn(newsList);
        Assert.assertEquals(newsList, command.execute(requestContent));
    }
    @Test
    public void executeNullReturnCheck() throws CommandException, ReceiverException {
        when(receiver.showAllNews(requestContent)).thenReturn(null);
        Assert.assertNull(command.execute(requestContent));
    }
}