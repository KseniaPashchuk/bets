package com.epam.bets.entity;

import java.math.BigDecimal;

public class Bet extends Entity {

    private int id;
    private BigDecimal money;
    private BigDecimal coefficient;
    private boolean isWon;
    private int footballMatchId;
    private String footballMatch;
    private BetType betType;
    private int userId;

    public Bet() {

    }

    public Bet(BigDecimal money, int footballMatchId, BetType betType, int userId) {
        this.money = money;
        this.footballMatchId = footballMatchId;
        this.betType = betType;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public boolean isWon() {
        return isWon;
    }

    public void setWon(boolean won) {
        isWon = won;
    }

    public String getFootballMatch() {
        return footballMatch;
    }

    public void setFootballMatch(String footballMatch) {
        this.footballMatch = footballMatch;
    }

    public BetType getBetType() {
        return betType;
    }

    public void setBetType(BetType betType) {
        this.betType = betType;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFootballMatchId() {
        return footballMatchId;
    }

    public void setFootballMatchId(int footballMatchId) {
        this.footballMatchId = footballMatchId;
    }

    public BigDecimal getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(BigDecimal coefficient) {
        this.coefficient = coefficient;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bet bet = (Bet) o;

        if (id != bet.id) return false;
        if (isWon != bet.isWon) return false;
        if (footballMatchId != bet.footballMatchId) return false;
        if (userId != bet.userId) return false;
        if (money != null ? !money.equals(bet.money) : bet.money != null) return false;
        if (coefficient != null ? !coefficient.equals(bet.coefficient) : bet.coefficient != null) return false;
        if (footballMatch != null ? !footballMatch.equals(bet.footballMatch) : bet.footballMatch != null) return false;
        return betType == bet.betType;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (money != null ? money.hashCode() : 0);
        result = 31 * result + (coefficient != null ? coefficient.hashCode() : 0);
        result = 31 * result + (isWon ? 1 : 0);
        result = 31 * result + footballMatchId;
        result = 31 * result + (footballMatch != null ? footballMatch.hashCode() : 0);
        result = 31 * result + (betType != null ? betType.hashCode() : 0);
        result = 31 * result + userId;
        return result;
    }

    @Override
    public String toString() {
        return "Bet{" +
                "id=" + id +
                ", money=" + money +
                ", coefficient=" + coefficient +
                ", isWon=" + isWon +
                ", footballMatchId=" + footballMatchId +
                ", footballMatch='" + footballMatch + '\'' +
                ", betType=" + betType +
                ", userId=" + userId +
                '}';
    }
}
