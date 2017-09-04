package com.epam.bets.generator;

import com.epam.bets.validator.UserValidator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class PasswordGeneratorTest {

    private String testLine;
    private final String PASSWORD_REGEX = "^(?=.*\\d)(?=.*[a-zа-я])(?=.*[A-ZА-Я]).{6,10}$";
    private Pattern paramPattern;


    public PasswordGeneratorTest(String testLine) {
        this.testLine = testLine;
        paramPattern = Pattern.compile(PASSWORD_REGEX);

    }

    @Parameterized.Parameters
    public static Collection<Object> stepUp() {
        return Arrays.asList(new Object[]{
                PasswordGenerator.generatePassword(),
                PasswordGenerator.generatePassword(),
                PasswordGenerator.generatePassword(),
                PasswordGenerator.generatePassword(),
                PasswordGenerator.generatePassword(),
                PasswordGenerator.generatePassword(),
                PasswordGenerator.generatePassword()
        });
    }

    @Test
    public void generatePassword() throws Exception {
        Matcher paramMatcher = paramPattern.matcher(testLine);
        Assert.assertTrue(paramMatcher.matches());
    }

}