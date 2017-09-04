package com.epam.bets.validator;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The base class for all validators.
 *
 * @author Pashchuk Ksenia
 */
public abstract class BaseValidator {
    /**
     * Checks if any string param is valid
     *
     * @param param - any string param
     * @return true if param is not null and not empty
     */
    public boolean validateStringParam(String param) {
        return param != null && !param.trim().isEmpty();
    }


    /**
     * Checks if any string param is valid
     *
     * @param param - any string param
     * @param limit - string param length limit
     * @return true if param is not null, not empty, not longer than max lenght
     */

    public boolean validateStringParamWithLimit(String param, int limit) {
        return param != null && !param.trim().isEmpty() && param.length() <= limit;
    }
    /**
     * Checks if any string param is valid
     *
     * @param param - any string param
     * @param regex - string param length limit
     * @return true if param is not null, not empty, not longer than max lenght
     */

    public boolean validateStringParamWithRegex(String param, String regex) {
        Pattern paramPattern = Pattern.compile(regex);
        Matcher paramMatcher = paramPattern.matcher(param);
        return paramMatcher.matches();
    }

    /**
     * Checks if any bigdecimal param is valid
     *
     * @param param - any bigdecimal param
     * @return true if param is not null, not equal to 0 and is positive number
     */
    public boolean validateBigDecimalParam(BigDecimal param) {
        return param != null && !(param.signum() == 0) && !(param.signum() == -1);
    }
}
