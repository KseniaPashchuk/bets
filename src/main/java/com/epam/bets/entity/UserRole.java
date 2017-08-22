package com.epam.bets.entity;

public enum UserRole {
    USER("user"), ADMIN("admin"), BOOKMAKER("bookmaker"), GUEST("guest");
    private String stringRepresentation;

    UserRole(String stringRepresentation) {
        this.stringRepresentation = stringRepresentation;
    }

    public String getStringRepresentation() {
        return stringRepresentation;
    }
}
