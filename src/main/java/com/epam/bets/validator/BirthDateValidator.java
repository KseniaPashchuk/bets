package com.epam.bets.validator;

import java.time.LocalDate;


public class BirthDateValidator {

    public boolean validate(LocalDate birthDate) {
        if (LocalDate.now().getYear() - birthDate.getYear() >= 18) {
            return true;
        }
        return false;
    }
}
