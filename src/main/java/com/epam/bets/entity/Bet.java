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
}
