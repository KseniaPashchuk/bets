package com.epam.bets.entity;

import java.math.BigDecimal;
import java.time.LocalDate;


public class User extends Entity {
    private int id;
    private String firstName;
    private String lastName;
    private String login;
    private String password;
    private BigDecimal balance;
    private CreditCards creditCards;
    private UserRole role;
    private LocalDate birthDate;
    private String avatarUrl;
    private final String DEFAULT_AVATAR = "defaultUser.jpg";

    public User() {
        balance = new BigDecimal("0");
        role = UserRole.USER;
        avatarUrl = DEFAULT_AVATAR;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public CreditCards getCreditCards() {
        return creditCards;
    }

    public void setCreditCards(CreditCards creditCards) {
        this.creditCards = creditCards;
        this.creditCards.setUserId(this.id);
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }


}
