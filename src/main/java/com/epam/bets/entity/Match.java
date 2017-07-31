package com.epam.bets.entity;

import java.math.BigDecimal;
import java.util.List;

public class Match extends Entity {
    private int matchId;
    private String firstTeam;
    private String secondTeam;
    private String confederacy;
    private String country;
    private BigDecimal total;
    private BigDecimal maxBet;
    private List<GainCoefficient> bets;

    public Match() {
    }

    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public String getFirstTeam() {
        return firstTeam;
    }

    public void setFirstTeam(String firstTeam) {
        this.firstTeam = firstTeam;
    }

    public String getSecondTeam() {
        return secondTeam;
    }

    public void setSecondTeam(String secondTeam) {
        this.secondTeam = secondTeam;
    }

    public String getConfederacy() {
        return confederacy;
    }

    public void setConfederacy(String confederacy) {
        this.confederacy = confederacy;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getMaxBet() {
        return maxBet;
    }

    public void setMaxBet(BigDecimal maxBet) {
        this.maxBet = maxBet;
    }

    public List<GainCoefficient> getBets() {
        return bets;
    }

    public void setBets(List<GainCoefficient> bets) {
        this.bets = bets;
    }
}
