package com.epam.bets.entity;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class GainCoefficient extends Entity {
    private int footballMatchId;
    Map<BetType, BigDecimal> coefficients;

    public GainCoefficient() {
        coefficients = new HashMap<>();
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GainCoefficient that = (GainCoefficient) o;

        if (footballMatchId != that.footballMatchId) return false;
        return coefficients != null ? coefficients.equals(that.coefficients) : that.coefficients == null;
    }

    @Override
    public int hashCode() {
        int result = footballMatchId;
        result = 31 * result + (coefficients != null ? coefficients.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "GainCoefficient{" +
                "footballMatchId=" + footballMatchId +
                ", coefficients=" + coefficients +
                '}';
    }
}
