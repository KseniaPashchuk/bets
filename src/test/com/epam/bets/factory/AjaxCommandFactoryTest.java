package com.epam.bets.factory;

import com.epam.bets.command.AbstractCommand;
import com.epam.bets.command.AjaxCommand;
import com.epam.bets.command.admin.EditNewsCommand;
import com.epam.bets.command.bookmaker.CreateMatchCommand;
import com.epam.bets.command.common.SignInCommand;
import com.epam.bets.generator.PasswordGenerator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class AjaxCommandFactoryTest {

    private AjaxCommand command;
    private String expectedClass;

    public AjaxCommandFactoryTest(AjaxCommand command, String expectedClass) {
        this.command = command;
        this.expectedClass = expectedClass;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> stepUp() {
        AjaxCommandFactory factory = new AjaxCommandFactory();
        return Arrays.asList(new Object[][]{
                {factory.initCommand("show_matches"), "com.epam.bets.command.common.ShowMatchesCommand"},
                {factory.initCommand("show_support_page"), "com.epam.bets.command.admin.ShowSupportPageCommand"},
                {factory.initCommand("show_bets"), "com.epam.bets.command.common.ShowBetsCommand"},
                {factory.initCommand("show_news"),"com.epam.bets.command.common.ShowNewsCommand"}
        });
    }
    @Test
    public void initCommand() throws Exception {
        Assert.assertEquals(expectedClass, command.getClass().getName());
    }
}