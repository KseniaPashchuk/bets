package com.epam.bets.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CreditCards extends Entity{

    private List<String> creditCards;
    private int userId;

    public CreditCards() {
        creditCards = new ArrayList<>(3);
    }

    public CreditCards(List<String> creditCards, int userId) {
        this.creditCards = creditCards;
        this.userId = userId;
    }

    public void addCreditCard(String creditCard) {
        creditCards.add(creditCard);
    }

    public String getCreditCard(int index) {
        return creditCards.get(index);
    }

    public List<String> getCreditCards() {
        return Collections.unmodifiableList(creditCards);
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCreditCarsSize(){
        return creditCards.size();
    }


}
