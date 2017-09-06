package com.epam.bets.factory;

import com.epam.bets.command.AbstractCommand;
import com.epam.bets.command.admin.EditNewsCommand;
import com.epam.bets.command.bookmaker.CreateMatchCommand;
import com.epam.bets.command.common.SendSupportCommand;
import com.epam.bets.command.common.SignInCommand;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class CommandFactoryTest {

    private AbstractCommand command;
    private String expectedClass;

    public CommandFactoryTest( AbstractCommand command, String expectedClass) {
        this.expectedClass = expectedClass;
        this.command = command;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> stepUp() {
        CommandFactory factory = new CommandFactory();
        return Arrays.asList(new Object[][]{
                {factory.initCommand("create_match"), "com.epam.bets.command.bookmaker.CreateMatchCommand"},
                {factory.initCommand("edit_news"), "com.epam.bets.command.admin.EditNewsCommand"},
                {factory.initCommand("sign_in"), "com.epam.bets.command.common.SignInCommand"},
                {factory.initCommand("send_support_mail"),"com.epam.bets.command.common.SendSupportCommand"}
        });
    }

    @Test
    public void initCommand() throws Exception {
        Assert.assertEquals(expectedClass, command.getClass().getName());
    }
}