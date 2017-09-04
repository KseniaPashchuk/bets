package com.epam.bets.validator;


import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.epam.bets.constant.ErrorConstant.MailError.INVALID_MAIL_TEXT_ERROR;
import static com.epam.bets.constant.ErrorConstant.UserError.*;

/**
 * The class for mail params validation.
 *
 * @author Pashchuk Ksenia
 */
public class MailValidator extends BaseValidator {

    private final String EMAIL_REGEX = "^.+@\\w+\\.\\w{2,6}$";
    private static final int MAX_MAIL_TEXT_LENGTH = 600;

    /**
     * Checks if email is valid
     *
     * @param email - email to which the mail will be sent
     * @return true if email matches to the regular expression.
     */
    public boolean validateEmail(String email, List<String> errors) {
        Pattern loginPattern = Pattern.compile(EMAIL_REGEX);
        Matcher loginMatcher = loginPattern.matcher(email);
        if (!loginMatcher.matches()) {
            errors.add(INVALID_EMAIL_ERROR);
        }
        return loginMatcher.matches();
    }

    /**
     * Checks if mail text is valid.
     *
     * @param text    - mail text
     * @param errors  - for validation errors storage
     * @return true if text are valid
     */

    public boolean validateMailText(String text, List<String> errors) {
        if (!validateStringParamWithLimit(text, MAX_MAIL_TEXT_LENGTH)) {
            errors.add(INVALID_MAIL_TEXT_ERROR);
        }
        return validateStringParam(text);
    }
}
