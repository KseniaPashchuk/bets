package com.epam.bets.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Match extends Entity {
    private int id;
    private String firstTeam;
    private String secondTeam;
    private String confederacy;
    private String country;
    private BigDecimal total;
    private BigDecimal maxBet;
    private LocalDateTime date;
    private GainCoefficient matchCoefficients;
    private BigDecimal firstTeamScore;
    private BigDecimal secondTeamScore;

    public Match() {
        matchCoefficients = new GainCoefficient();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void addCoefficient(BetType type, BigDecimal coeff) {
        matchCoefficients.addCoefficient(type, coeff);
    }


    public GainCoefficient getMatchCoefficients() {
        return matchCoefficients;
    }

    public void setMatchCoefficients(GainCoefficient matchCoefficients) {
        this.matchCoefficients = matchCoefficients;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public BigDecimal getFirstTeamScore() {
        return firstTeamScore;
    }

    public void setFirstTeamScore(BigDecimal firstTeamScore) {
        this.firstTeamScore = firstTeamScore;
    }

    public BigDecimal getSecondTeamScore() {
        return secondTeamScore;
    }

    public void setSecondTeamScore(BigDecimal secondTeamScore) {
        this.secondTeamScore = secondTeamScore;
    }

    @Override
    public String toString() {
        return "Match{" +
                "id=" + id +
                ", firstTeam='" + firstTeam + '\'' +
                ", secondTeam='" + secondTeam + '\'' +
                ", confederacy='" + confederacy + '\'' +
                ", country='" + country + '\'' +
                ", total=" + total +
                ", maxBet=" + maxBet +
                ", date=" + date +
                ", matchCoefficients=" + matchCoefficients +
                ", firstTeamScore=" + firstTeamScore +
                ", secondTeamScore=" + secondTeamScore +
                '}';
    }
}
