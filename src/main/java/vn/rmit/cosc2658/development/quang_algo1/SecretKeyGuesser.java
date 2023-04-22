package vn.rmit.cosc2658.development.quang_algo1;

import vn.rmit.cosc2658.development.SecretKey;


public class SecretKeyGuesser {
    private static final char[] POSSIBLE_VALUES = "RMIT".toCharArray();

    public static String start(SecretKey sk, int kLen, boolean verbose) {
        int[] letterCount = new int[POSSIBLE_VALUES.length];  // Count the number of occurrences for each character in the secret key

        letterCount[0] = sk.guess("R".repeat(kLen));
        if (letterCount[0] == kLen) return "R".repeat(kLen);

        letterCount[1] = sk.guess("M".repeat(kLen));
        if (letterCount[1] == kLen) return "M".repeat(kLen);

        letterCount[2] = sk.guess("I".repeat(kLen));
        if (letterCount[2] == kLen) return "I".repeat(kLen);

        letterCount[3] = sk.guess("T".repeat(kLen));
        if (letterCount[3] == kLen) return "T".repeat(kLen);


        // create a guess with exact number of each element in correct key
        StringBuilder guess = new StringBuilder();
        for (int letter = 0; letter < POSSIBLE_VALUES.length; letter++) {
            while (letterCount[letter] > 0) {
                guess.append(POSSIBLE_VALUES[letter]);
                letterCount[letter]--;
            }
        }

        while (sk.guess(guess.toString()) != kLen) {  // make guesses
            guess = new StringBuilder(generateGuess(guess.toString(), sk, kLen));
            if (verbose) System.out.println("Guessing... " + guess);
        }

        return guess.toString();
    }

    public static String start(SecretKey secretKey, int secretKeyLength) {
        return start(secretKey, secretKeyLength, true);
    }


    private static int toInt(char c) {
        return switch (c) {
            case 'R' -> 0;
            case 'M' -> 1;
            case 'I' -> 2;
            case 'T' -> 3;
            default -> throw new IllegalStateException("Unexpected value: " + c);
        };
    }

    private static String generateGuess(String guess, SecretKey secretKey, int keyLength) {
        char[] curr = guess.toCharArray();
        char[] test = curr.clone();
        int i = 0;
        while (i < keyLength) {
            for (int j = 0; j < keyLength; j++) {
                if (toInt(test[i]) != toInt(test[j])) {
                    // increase this one and stop
                    char temp = test[i];
                    test[i] = test[j];
                    test[j] = temp;
                }
                if (secretKey.guess(String.valueOf(test)) > secretKey.guess(String.valueOf(curr))) {
                    curr = test;
                    return String.valueOf(curr);
                } else {
                    test = curr.clone();
                }
            }
            i++;
        }
        return String.valueOf(curr);
    }
}

