package com.epam.bets.validator;


import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.epam.bets.constant.ErrorConstant.UserError.*;

/**
 * The class for mail params validation.
 *
 * @author Pashchuk Ksenia
 */
public class MailValidator extends BaseValidator {

    private final String MAIL_REGEX = "^.+@\\w+\\.\\w{2,6}$";

    /**
     * Checks if email is valid
     *
     * @param email - email to which the mail will be sent
     * @return true if email matches to the regular expression.
     */
    public boolean validateEmail(String email, List<String> errors) {
        Pattern loginPattern = Pattern.compile(MAIL_REGEX);
        Matcher loginMatcher = loginPattern.matcher(email);
        if (!loginMatcher.matches()) {
            errors.add(INVALID_EMAIL_ERROR);
        }
        return loginMatcher.matches();
    }

    /**
     * Checks if mail params(subject, text) is valid.
     *
     * @param subject - mail subject
     * @param text    - mail text
     * @param errors  - for validation errors storage
     * @return true if subject and text are valid
     */

    public boolean validateMailParams(String subject, String text, List<String> errors) {
        boolean isValid = true;
        if (!validateStringParam(subject)) {
            isValid = false;
            errors.add(INVALID_SUBJECT_ERROR);
        }
        if (!validateStringParam(text)) {
            isValid = false;
            errors.add(INVALID_MAIL_TEXT_ERROR);
        }
        return isValid;
    }
}
