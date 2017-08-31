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
        if (!avatarUrl.isEmpty()) {
            this.avatarUrl = avatarUrl;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
        if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) return false;
        if (login != null ? !login.equals(user.login) : user.login != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (balance != null ? !balance.equals(user.balance) : user.balance != null) return false;
        if (creditCards != null ? !creditCards.equals(user.creditCards) : user.creditCards != null) return false;
        if (role != user.role) return false;
        if (birthDate != null ? !birthDate.equals(user.birthDate) : user.birthDate != null) return false;
        return avatarUrl != null ? avatarUrl.equals(user.avatarUrl) : user.avatarUrl == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (balance != null ? balance.hashCode() : 0);
        result = 31 * result + (creditCards != null ? creditCards.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (birthDate != null ? birthDate.hashCode() : 0);
        result = 31 * result + (avatarUrl != null ? avatarUrl.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", balance=" + balance +
                ", creditCards=" + creditCards +
                ", role=" + role +
                ", birthDate=" + birthDate +
                ", avatarUrl='" + avatarUrl + '\'' +
                '}';
    }
}
