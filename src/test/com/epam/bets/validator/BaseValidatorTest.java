package com.epam.bets.validator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;


public class BaseValidatorTest {
    @Before
    public void init() {
    }

    @Test
    public void validateValidStringParam() throws Exception {
        String param = "validParam";
        Assert.assertTrue(new UserValidator().validateStringParam(param));
    }


    @Test
    public void validateInvalidStringParam() throws Exception {
        String param = "";
        Assert.assertFalse(new UserValidator().validateStringParam(param));
    }

    @Test
    public void validateNullStringParam() throws Exception {
        String param = null;
        Assert.assertFalse(new UserValidator().validateStringParam(param));
    }

    @Test
    public void validateValidStringParamWithLimit() throws Exception {
        String param = "hello world";
        int limit = 11;
        Assert.assertTrue(new UserValidator().validateStringParamWithLimit(param, limit));
    }

    @Test
    public void validateInvalidStringParamWithLimit() throws Exception {
        String param = "hello world!!!!!!!!!!!!!!!!!!";
        int limit = 11;
        Assert.assertFalse(new UserValidator().validateStringParamWithLimit(param, limit));
    }

    @Test
    public void validateValidStringParamWithRegex() throws Exception {
        String param = "11111";
        String regex = "[0-9]{1,6}";
        Assert.assertTrue(new UserValidator().validateStringParamWithRegex(param, regex));
    }

    @Test
    public void validateInvalidStringParamWithRegex() throws Exception {
        String param = "hello world!!!!!!!!!!!!!!!!!!";
        String regex = "[0-9]{1,6}";
        Assert.assertFalse(new UserValidator().validateStringParamWithRegex(param, regex));
    }

    @Test
    public void validateValidBigDecimalParam() throws Exception {
        BigDecimal param = new BigDecimal("10");
        Assert.assertTrue(new UserValidator().validateBigDecimalParam(param));
    }

    @Test
    public void validateZeroBigDecimalParam() throws Exception {
        BigDecimal param = new BigDecimal("0");
        Assert.assertFalse(new UserValidator().validateBigDecimalParam(param));
    }
    @Test
    public void validateNegativeBigDecimalParam() throws Exception {
        BigDecimal param = new BigDecimal("-100");
        Assert.assertFalse(new UserValidator().validateBigDecimalParam(param));
    }
    @Test
    public void validateNullBigDecimalParam() throws Exception {
        BigDecimal param = null;
        Assert.assertFalse(new UserValidator().validateBigDecimalParam(param));
    }
}