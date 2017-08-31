package com.epam.bets.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CreditCards extends Entity{

    private List<String> creditCardList;
    private int userId;

    public CreditCards() {
        creditCardList = new ArrayList<>(3);
    }

    public CreditCards(List<String> creditCards, int userId) {
        this.creditCardList = creditCards;
        this.userId = userId;
    }

    public void addCreditCard(String creditCard) {
        creditCardList.add(creditCard);
    }

    public String getCreditCard(int index) {
        return creditCardList.get(index);
    }

    public List<String> getCreditCardList() {
        return Collections.unmodifiableList(creditCardList);
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCreditCarsSize(){
        return creditCardList.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CreditCards that = (CreditCards) o;

        if (userId != that.userId) return false;
        return creditCardList != null ? creditCardList.equals(that.creditCardList) : that.creditCardList == null;
    }

    @Override
    public int hashCode() {
        int result = creditCardList != null ? creditCardList.hashCode() : 0;
        result = 31 * result + userId;
        return result;
    }

    @Override
    public String toString() {
        return "CreditCards{" +
                "creditCardList=" + creditCardList +
                ", userId=" + userId +
                '}';
    }
}
