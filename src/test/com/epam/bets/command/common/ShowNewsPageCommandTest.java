package com.epam.bets.command.common;

import com.epam.bets.navigator.PageNavigator;
import com.epam.bets.request.RequestContent;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static com.epam.bets.constant.PageConstant.NEWS_PAGE;
@RunWith(MockitoJUnitRunner.class)
public class ShowNewsPageCommandTest {
        @Mock
        private RequestContent requestContent;

        @Before
        public void setUp() throws Exception {
            MockitoAnnotations.initMocks(this);
        }
        @Test
        public void executeReturnCheck() {
            ShowNewsPageCommand command = new ShowNewsPageCommand();
            Assert.assertEquals(new PageNavigator(NEWS_PAGE, PageNavigator.PageType.FORWARD), command.execute(requestContent));
        }

}