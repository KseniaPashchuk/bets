package com.epam.bets.validator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;


public class MatchValidatorTest {
    @Before
    public void init() {
    }

    @Test
    public void validateValidMatchDate() throws Exception {
        LocalDateTime param = LocalDateTime.now().plusDays(1);
        Assert.assertTrue(new MatchValidator().validateMatchDate(param));
    }

    @Test
    public void validateInvalidMatchDate() throws Exception {
        LocalDateTime param = LocalDateTime.now().minusDays(1);
        Assert.assertFalse(new MatchValidator().validateMatchDate(param));
    }

    @Test
    public void validateValidTeams() throws Exception {
        String fteam = "Barcelona";
        String steam = "Bavaria";
        Assert.assertTrue(new MatchValidator().validateTeams(fteam, steam));
    }

    @Test
    public void validateInvalidTeams() throws Exception {
        String fteam = "Barcelona";
        String steam = "Barcelona";
        Assert.assertFalse(new MatchValidator().validateTeams(fteam, steam));
    }

}