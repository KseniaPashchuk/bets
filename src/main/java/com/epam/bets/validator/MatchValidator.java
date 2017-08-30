package com.epam.bets.validator;


import com.epam.bets.entity.BetType;
import com.epam.bets.entity.Match;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static com.epam.bets.constant.ErrorConstant.MatchError.*;

public class MatchValidator {

    public boolean validateMatchDate(LocalDateTime matchDate){
        return matchDate.isAfter(LocalDateTime.now());
    }

    public boolean validateMaxBet(BigDecimal maxBet) {
        return maxBet != null && !(maxBet.signum() == 0) && !(maxBet.signum() == -1);
    }

    public boolean validateGainCoefficient(BigDecimal coeff){
        return coeff != null && !(coeff.signum() == 0) && !(coeff.signum() == -1);
    }
    public boolean validateTeams(String firstTeam, String secondTeam){
        return !firstTeam.equals(secondTeam);
    }

    public boolean validateMatchParams(Match match, List<String> errors) {
        boolean isValid = true;
        if (!validateTeams(match.getFirstTeam(),match.getSecondTeam())) {
            isValid = false;
            errors.add(SAME_TEAM_ERROR);
        }
        if (!validateMatchDate(match.getDate())) {
            isValid = false;
            errors.add(DATE_BEFORE_ERROR);
        }
        if (!validateMaxBet(match.getMaxBet())) {
            isValid = false;
            errors.add(INVALID_MAX_BET_ERROR);
        }
        for (Map.Entry<BetType, BigDecimal> coeff : match.getMatchCoefficients().getCoefficients().entrySet()) {
            if (!validateGainCoefficient(coeff.getValue())) {
                isValid = false;
                errors.add(INVALID_COEFF_ERROR);
                break;
            }
        }
        return isValid;
    }

}
