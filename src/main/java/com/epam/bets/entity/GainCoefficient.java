package com.epam.bets.entity;


import java.math.BigDecimal;

public class GainCoefficient extends Entity {
    private int footballMatchId;
    private BetType betType;
    private BigDecimal coefficient;

    public GainCoefficient() {
    }

    public int getFootballMatchId() {
        return footballMatchId;
    }

    public void setFootballMatchId(int footballMatchId) {
        this.footballMatchId = footballMatchId;
    }

    public BetType getBetType() {
        return betType;
    }

    public void setBetType(BetType betType) {
        this.betType = betType;
    }

    public BigDecimal getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(BigDecimal coefficient) {
        this.coefficient = coefficient;
    }
}
