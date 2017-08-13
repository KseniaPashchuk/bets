package com.epam.bets.entity;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class GainCoefficient extends Entity {
    private int footballMatchId;
//    private BetType betType;
//    private BigDecimal coefficient;
    Map <BetType, BigDecimal> coefficients;

    public GainCoefficient() {
        coefficients = new HashMap<>();
    }


//    public GainCoefficient(BetType betType, BigDecimal coefficient) {
//        this.betType = betType;
//        this.coefficient = coefficient;
//    }

    public int getFootballMatchId() {
        return footballMatchId;
    }

    public void setFootballMatchId(int footballMatchId) {
        this.footballMatchId = footballMatchId;
    }

    public Map<BetType, BigDecimal> getCoefficients() {
        return coefficients;
    }

    public void setCoefficients(Map<BetType, BigDecimal> coefficients) {
        this.coefficients = coefficients;
    }
    public void addCoefficient(BetType type, BigDecimal coeff) {
        coefficients.put(type, coeff);
    }

    //    public BetType getBetType() {
//        return betType;
//    }
//
//    public void setBetType(BetType betType) {
//        this.betType = betType;
//    }
//
//    public BigDecimal getCoefficient() {
//        return coefficient;
//    }
//
//    public void setCoefficient(BigDecimal coefficient) {
//        this.coefficient = coefficient;
//    }
}
