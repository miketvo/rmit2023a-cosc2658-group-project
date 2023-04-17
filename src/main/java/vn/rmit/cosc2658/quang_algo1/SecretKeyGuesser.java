package vn.rmit.cosc2658.quang_algo1;

import vn.rmit.cosc2658.SecretKey;


public class SecretKeyGuesser {
    private static final char[] POSSIBLE_VALUES = "RMIT".toCharArray();
    private static final int LENGTH = 16;

    public static void start(SecretKey sk) {
        // Count the number of occurrences for each character in the secret key
        int[] letterCount = new int[4];
        letterCount[0] = sk.guess("RRRRRRRRRRRRRRRR");
        letterCount[1] = sk.guess("MMMMMMMMMMMMMMMM");
        letterCount[2] = sk.guess("IIIIIIIIIIIIIIII");
        letterCount[3] = sk.guess("TTTTTTTTTTTTTTTT");


        // create a guess with exact number of each element in correct key
        StringBuilder guess = new StringBuilder();
        for (int letter = 0; letter < POSSIBLE_VALUES.length; letter++) {
            while (letterCount[letter] > 0) {
                guess.append(POSSIBLE_VALUES[letter]);
                letterCount[letter]--;
            }
        }

        while (sk.guess(guess.toString()) != 16) {  // make guesses
            guess = new StringBuilder(generateGuess(guess.toString(), sk));
            System.out.println("Guessing... " + guess);
        }
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

    private static String generateGuess(String guess, SecretKey secretKey) {
        char[] curr = guess.toCharArray();
        char[] test = curr.clone();
        int i = 0;
        while (i < LENGTH) {
            for (int j = 0; j < LENGTH; j++) {
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

