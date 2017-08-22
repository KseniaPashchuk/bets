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


}
