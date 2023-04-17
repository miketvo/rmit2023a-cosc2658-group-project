package vn.rmit.cosc2658.quang_algo;


import vn.rmit.cosc2658.SecretKey;

import java.util.Random;

public class SecretKeyGuesser {
    private static final String[] POSSIBLE_VALUES = {"R", "M", "I", "T"};
    private static final int LENGTH = 16;

    public static void start(SecretKey secretKey) {
        //create a list to store numbers of each element in the correct key
        int[] numMatchesList = new int[4];
        //first guess to count the numbers of R in correct key
        String firstGuess = "RRRRRRRRRRRRRRRR";
        int numMatches = secretKey.guess(firstGuess);
        numMatchesList[0] = numMatches;
        if (numMatches == LENGTH) {
            System.out.println("The secret key is :" + firstGuess);
            return;
        }
        //second guess to count the number of M in correct key
        String secondGuess = "MMMMMMMMMMMMMMMM";
        numMatches = secretKey.guess(secondGuess);
        numMatchesList[1] = numMatches;
        if (numMatches == LENGTH) {
            System.out.println("The secret key is :" + secondGuess);
            return;
        }
        //third guess to count the number of I in correct key
        String thirdGuess = "IIIIIIIIIIIIIIII";
        numMatches = secretKey.guess(thirdGuess);
        numMatchesList[2] = numMatches;
        if (numMatches == LENGTH) {
            System.out.println("The secret key is :" + secondGuess);
            return;
        }
        //fourth guess to count the number of T in correct key
        String fourthGuess = "TTTTTTTTTTTTTTTT";
        numMatches = secretKey.guess(fourthGuess);
        numMatchesList[3] = numMatches;
        if (numMatches == LENGTH) {
            System.out.println("The secret key is :" + secondGuess);
            return;
        }
        // create a guess with exact number of each element in correct key
        String guess = "";
        int i = 0;
        int numMatchesListIdx = numMatchesList[0];
        while (i < numMatchesList.length) {
            if (numMatchesListIdx > 0) {
                guess = guess.concat(POSSIBLE_VALUES[i]);
                numMatchesListIdx--;
            } else {
                i++;
                if (i < 4) numMatchesListIdx = numMatchesList[i];
            }
        }
        // make guesses
        while (secretKey.guess(guess) != 16) {
            guess = generateGuess(guess, secretKey);
            System.out.println("Guessing... " + guess);
        }
    }

    static int order(char c) {
        if (c == 'R') {
            return 0;
        } else if (c == 'M') {
            return 1;
        } else if (c == 'I') {
            return 2;
        }
        return 3;
    }

    public static String generateGuess(String guess, SecretKey secretKey) {
        char[] curr = guess.toCharArray();
        char[] test = curr.clone();
        int i = 0;
        while (i < LENGTH) {
            for (int j = 0; j < LENGTH; j++) {
                if (order(test[i]) != order(test[j])) {
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

