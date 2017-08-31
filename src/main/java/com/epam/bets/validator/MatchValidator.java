package com.epam.bets.validator;


import com.epam.bets.entity.BetType;
import com.epam.bets.entity.Match;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static com.epam.bets.constant.ErrorConstant.MatchError.*;

/**
 * The class for match params validation.
 *
 * @author Pashchuk Ksenia
 */

public class MatchValidator extends BaseValidator{

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
     * Checks if match score is valid
     *
     * @param score - any bigdecimal param
     * @return true if score is not null and is nonnegative  number
     */
    public boolean validateScore(BigDecimal score) {
        return score != null &&  !(score.signum() == -1);
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



    public boolean validateConfederation(String confederation, List<String> errors){
        if(! validateStringParam(confederation)){
            errors.add(INVALID_CONFEDERATION_ERROR);
        }
        return validateStringParam(confederation);
    }

    /**
     * Checks if match params (first team name, second team name, max bet, match date, gain coefficients) are valid
     *
     * @param match  - match information
     * @param errors - for validation errors storage
     * @return true if login matches to the regular expression.
     */
    public boolean validateMatchParams(Match match, List<String> errors) {
        boolean isValid = true;
        if (!validateTeams(match.getFirstTeam(), match.getSecondTeam())) {
            isValid = false;
            errors.add(SAME_TEAM_ERROR);
        }
        if (!validateMatchDate(match.getDate())) {
            isValid = false;
            errors.add(DATE_BEFORE_ERROR);
        }
        if (!validateBigDecimalParam(match.getMaxBet())) {
            isValid = false;
            errors.add(INVALID_MAX_BET_ERROR);
        }
        for (Map.Entry<BetType, BigDecimal> coeff : match.getMatchCoefficients().getCoefficients().entrySet()) {
            if (!validateBigDecimalParam(coeff.getValue())) {
                isValid = false;
                errors.add(INVALID_COEFF_ERROR);
                break;
            }
        }
        return isValid;
    }

    public boolean validateSetScoreParams(LocalDateTime matchDate, BigDecimal firstTeamScore, BigDecimal secondTeamScore,
                                          List<String> errors) {
        boolean isValid = true;
        if (validateMatchDate(matchDate)) {
            isValid = false;
            errors.add(SET_SCORE_DATE_ERROR);
        }
        if (!validateScore(firstTeamScore) || !validateScore(secondTeamScore)) {
            isValid = false;
            errors.add(SCORE_NOT_POSITIVE_ERROR);
        }
        return isValid;
    }


    public boolean validateCreateTeamParams(String teamName, String country,  List<String> errors) {
        boolean isValid = true;
        if (!validateStringParam(teamName)) {
            isValid = false;
            errors.add(INVALID_TEAM_NAME_ERROR);
        }
        if (!validateStringParam(country)) {
            isValid = false;
            errors.add(INVALID_COUNTRY_ERROR);
        }
        return isValid;
    }

}
