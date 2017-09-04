package com.epam.bets.validator;


import com.epam.bets.request.RequestContent;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.epam.bets.constant.ErrorConstant.MatchError.*;
import static com.epam.bets.constant.RequestParamConstant.CommonParam.PARAM_NAME_DATE;
import static com.epam.bets.constant.RequestParamConstant.MatchParam.*;
import static com.epam.bets.constant.RequestParamConstant.MatchParam.PARAM_NAME_MAX_BET;

/**
 * The class for match params validation.
 *
 * @author Pashchuk Ksenia
 */

public class MatchValidator extends BaseValidator {

    private final String GAIN_COEFF_REGEX = "[0-9]{1,3}\\.?[0-9]{0,2}";
    private final String MAX_BET_REGEX = "[0-9]{1,5}\\.?[0-9]{0,2}";
    private final String TOTAL_REGEX = "[0-9]{1,2}\\.?[0-9]{0,2}";
    private final String SCORE_REGEX = "[0-9]{1,2}";

    /**
     * Checks if match date is valid
     *
     * @param matchDate - match date
     * @return true if match date is after now
     */
    public boolean validateMatchDate(LocalDateTime matchDate) {
        return matchDate.isAfter(LocalDateTime.now());
    }


    /**
     * Checks if firstTeam and secondTeam are valid
     *
     * @param firstTeam  - first team name
     * @param secondTeam - second team name
     * @return true if first team name not equal to second team name.
     */
    public boolean validateTeams(String firstTeam, String secondTeam) {
        return !firstTeam.equals(secondTeam);
    }

    /**
     * Checks if confederation name is valid
     *
     * @param confederation - match information
     * @param errors        -  for validation errors storage
     * @return true if login matches to the regular expression.
     */
    public boolean validateConfederation(String confederation, List<String> errors) {
        boolean isValid = true;
        if (!validateStringParam(confederation)) {
            errors.add(INVALID_CONFEDERATION_ERROR);
            isValid = false;
        }
        return isValid;
    }

    /**
     * Checks if match big decimal param is valid
     *
     * @param param - match information
     * @param regex - for param validation
     * @return true if login matches to the regular expression.
     */
    public boolean validateMatchDecimalParam(String param, String regex) {
        return (validateStringParamWithRegex(param, regex) && validateBigDecimalParam(new BigDecimal(param)));
    }


    /**
     * Checks if match params (first team name, second team name, max bet, match date, gain coefficients) are valid
     *
     * @param requestContent - match information
     * @param errors         - for validation errors storage
     * @return true if login matches to the regular expression.
     */
    public boolean validateMatchParams(RequestContent requestContent, List<String> errors) {

        List<String> gainCoeffs = new ArrayList<>();
        gainCoeffs.add(requestContent.findParameterValue(PARAM_NAME_FW));
        gainCoeffs.add(requestContent.findParameterValue(PARAM_NAME_X));
        gainCoeffs.add(requestContent.findParameterValue(PARAM_NAME_SW));
        gainCoeffs.add(requestContent.findParameterValue(PARAM_NAME_FWX));
        gainCoeffs.add(requestContent.findParameterValue(PARAM_NAME_FS));
        gainCoeffs.add(requestContent.findParameterValue(PARAM_NAME_XSW));
        gainCoeffs.add(requestContent.findParameterValue(PARAM_NAME_TL));
        gainCoeffs.add(requestContent.findParameterValue(PARAM_NAME_TM));


        boolean isValid = true;
        if (!validateTeams(requestContent.findParameterValue(PARAM_NAME_FIRST_TEAM), requestContent.findParameterValue(PARAM_NAME_SECOND_TEAM))) {
            isValid = false;
            errors.add(SAME_TEAM_ERROR);
        }
        if (!validateMatchDate(LocalDateTime.parse(requestContent.findParameterValue(PARAM_NAME_DATE), DateTimeFormatter.ofPattern(DATE_TIME_PATTERN)))) {
            isValid = false;
            errors.add(DATE_BEFORE_ERROR);
        }
        if (!validateMatchDecimalParam(requestContent.findParameterValue(PARAM_NAME_MAX_BET), MAX_BET_REGEX)) {
            isValid = false;
            errors.add(INVALID_MAX_BET_ERROR);
        }
        if (!validateMatchDecimalParam(requestContent.findParameterValue(PARAM_NAME_T), TOTAL_REGEX)) {
            isValid = false;
            errors.add(INVALID_MAX_BET_ERROR);
        }
        for (String coeff : gainCoeffs) {
            if (!validateMatchDecimalParam(coeff, GAIN_COEFF_REGEX)) {
                isValid = false;
                errors.add(INVALID_COEFF_ERROR);
                break;
            }
        }
        return isValid;
    }

    /**
     * Checks if set score params (first team score, second team score, match date) are valid
     *
     * @param requestContent - match information
     * @param errors         - for validation errors storage
     * @return true if login matches to the regular expression.
     */
    public boolean validateSetScoreParams(RequestContent requestContent, List<String> errors) {
        boolean isValid = true;
        LocalDateTime matchDate = LocalDateTime.parse(requestContent.findParameterValue(PARAM_NAME_DATE),
                DateTimeFormatter.ofPattern(DATE_TIME_PATTERN));
        if (validateMatchDate(matchDate)) {
            isValid = false;
            errors.add(SET_SCORE_DATE_ERROR);
        }
        if (!validateStringParamWithRegex(requestContent.findParameterValue(PARAM_NAME_FIRST_TEAM_SCORE), SCORE_REGEX) ||
                !validateStringParamWithRegex(requestContent.findParameterValue(PARAM_NAME_SECOND_TEAM_SCORE), SCORE_REGEX)) {
            isValid = false;
            errors.add(SCORE_NOT_POSITIVE_ERROR);
        }
        return isValid;
    }

    /**
     * Checks if create team params (team name, country) are valid
     *
     * @param requestContent - match information
     * @param errors         - for validation errors storage
     * @return true if login matches to the regular expression.
     */

    public boolean validateCreateTeamParams(RequestContent requestContent, List<String> errors) {
        boolean isValid = true;
        if (!validateStringParam(requestContent.findParameterValue(PARAM_NAME_TEAM))) {
            isValid = false;
            errors.add(INVALID_TEAM_NAME_ERROR);
        }
        if (!validateStringParam(requestContent.findParameterValue(PARAM_NAME_COUNTRY))) {
            isValid = false;
            errors.add(INVALID_COUNTRY_ERROR);
        }
        return isValid;
    }

}
