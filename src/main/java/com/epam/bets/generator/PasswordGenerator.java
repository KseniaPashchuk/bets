package com.epam.bets.generator;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class PasswordGenerator {

    public static final int MIN_PASSWORD_LENGTH = 6;

    public static final int MAX_PASSWORD_LENGTH = 10;

    private static final char[] ALPHA_UPPER_CHARACTERS = {'A', 'B', 'C', 'D',
            'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
            'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    private static final char[] ALPHA_LOWER_CHARACTERS = {'a', 'b', 'c', 'd',
            'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
            'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    private static final char[] NUMERIC_CHARACTERS = {'0', '1', '2', '3', '4',
            '5', '6', '7', '8', '9'};

    public String generatePassword() {
        List<char[]> activeSets = new ArrayList<char[]>(3);
        List<char[]> inactiveSets = new ArrayList<char[]>(3);

        activeSets.add(ALPHA_UPPER_CHARACTERS);
        activeSets.add(ALPHA_LOWER_CHARACTERS);
        activeSets.add(NUMERIC_CHARACTERS);


        SecureRandom random = new SecureRandom();

        int passwordLength = MIN_PASSWORD_LENGTH + random.nextInt(MAX_PASSWORD_LENGTH - MIN_PASSWORD_LENGTH + 1);
        StringBuffer password = new StringBuffer(passwordLength + 1);

        for (int p = 0; p < passwordLength; p++) {
            char[] randomSet = null;
            if (activeSets.size() > 1) {
                int rSet = random.nextInt(activeSets.size());
                randomSet = activeSets.get(rSet);
                inactiveSets.add(randomSet);
                activeSets.remove(rSet);
            } else {
                randomSet = activeSets.get(0);
                inactiveSets.add(randomSet);
                activeSets.clear();
                activeSets.addAll(inactiveSets);
                inactiveSets.clear();
            }
            int rChar = random.nextInt(randomSet.length);
            char randomChar = randomSet[rChar];
            password.append(randomChar);
        }

        return password.toString();
    }
}
