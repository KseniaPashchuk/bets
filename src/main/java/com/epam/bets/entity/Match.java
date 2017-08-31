package com.epam.bets.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Match extends Entity {
    private int id;
    private String firstTeam;
    private String secondTeam;
    private String confederation;
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

    public String getConfederation() {
        return confederation;
    }

    public void setConfederation(String confederation) {
        this.confederation = confederation;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Match match = (Match) o;

        if (id != match.id) return false;
        if (firstTeam != null ? !firstTeam.equals(match.firstTeam) : match.firstTeam != null) return false;
        if (secondTeam != null ? !secondTeam.equals(match.secondTeam) : match.secondTeam != null) return false;
        if (confederation != null ? !confederation.equals(match.confederation) : match.confederation != null)
            return false;
        if (country != null ? !country.equals(match.country) : match.country != null) return false;
        if (total != null ? !total.equals(match.total) : match.total != null) return false;
        if (maxBet != null ? !maxBet.equals(match.maxBet) : match.maxBet != null) return false;
        if (date != null ? !date.equals(match.date) : match.date != null) return false;
        if (matchCoefficients != null ? !matchCoefficients.equals(match.matchCoefficients) : match.matchCoefficients != null)
            return false;
        if (firstTeamScore != null ? !firstTeamScore.equals(match.firstTeamScore) : match.firstTeamScore != null)
            return false;
        return secondTeamScore != null ? secondTeamScore.equals(match.secondTeamScore) : match.secondTeamScore == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (firstTeam != null ? firstTeam.hashCode() : 0);
        result = 31 * result + (secondTeam != null ? secondTeam.hashCode() : 0);
        result = 31 * result + (confederation != null ? confederation.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (total != null ? total.hashCode() : 0);
        result = 31 * result + (maxBet != null ? maxBet.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (matchCoefficients != null ? matchCoefficients.hashCode() : 0);
        result = 31 * result + (firstTeamScore != null ? firstTeamScore.hashCode() : 0);
        result = 31 * result + (secondTeamScore != null ? secondTeamScore.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Match{" +
                "id=" + id +
                ", firstTeam='" + firstTeam + '\'' +
                ", secondTeam='" + secondTeam + '\'' +
                ", confederation='" + confederation + '\'' +
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
