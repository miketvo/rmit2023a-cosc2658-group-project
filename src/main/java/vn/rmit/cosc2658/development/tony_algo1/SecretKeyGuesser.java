package vn.rmit.cosc2658.development.tony_algo1;

import vn.rmit.cosc2658.development.SecretKey;


public class SecretKeyGuesser {
    private static final int CHAR_COUNT = 3;
    static int numOf_R, numOf_I, numOf_M, numOf_T;
    static int[] numOfChar = new int[4]; // R -> M -> I -> T
    static String[] initGuess = new String[]{"RRRRRRRRRRRRRRRR", "MMMMMMMMMMMMMMMM", "IIIIIIIIIIIIIIII", "TTTTTTTTTTTTTTTT"};
    static char[] validChar = new char[]{'R', 'M', 'I', 'T'};
    static int charPos = 0;
    static int positionInKey = 0;
    public static void start(SecretKey key) {
        // brute force key guessing
        int match = 0;
        int maxMatch = 0;
        String str = "";

        while(match != 16) {
            str = initGuess[charPos]; // start by guessing "RRRR"
            match = key.guess(str);
            numOfChar[charPos] = match;

            setCharFrequency(match);

            moveCharType();
            if (validChar[charPos] == 'R') {
                maxMatch = match; //  set max match at "TTTT"
                str = "RTTT";
                break;
            }
        }

        do {
            moveCharType();

            switch (charPos) {
                // execute code if numOf_R is greater than 0 for charPos 'R'
                case 'R' -> {
                    if (numOf_R <= 0) {
                        continue;
                    }
                }
                case 'M' -> {
                    if (numOf_M <= 0) {
                        continue;
                    }
                }
                case 'I' -> {
                    if (numOf_I <= 0) {
                        continue;
                    }
                }
                case 'T' -> {
                    if (numOf_T <= 0) {
                        continue;
                    }
                }
            }

            match = key.guess(str); // starting from "RTTT"
            str = next(str, maxMatch, match);
            maxMatch = Math.max(match, maxMatch);

        } while (match != 16);
        System.out.println("I found the secret key. It is " + str);
    }

    private static void setCharFrequency(int frequency) {
        switch (validChar[charPos]) {
            case 'R' -> numOf_R = frequency;
            case 'M' -> numOf_M = frequency;
            case 'I' -> numOf_I = frequency;
            case 'T' -> numOf_T = frequency;
        }
    }

    private static void moveCharType() {
        if (charPos == CHAR_COUNT) {
            charPos++;
        } else {
            charPos = 0;
        }
    }

    public static String next(String current, int maxMatch, int currentMatch) {
        char[] curr = current.toCharArray();
        curr[positionInKey] = validChar[charPos];

        //TODO: Algorithm goes here

        // if the max match decrease after changing the char
        // it means the previous character is correct
        // for example, "TTTT" -> 1
        // but "RTTT" -> 0 => The first 'T' is correct

        if (currentMatch < maxMatch || currentMatch > maxMatch) { // the current key is the closet to real key
            positionInKey++;
            reduceFrequency();
            return current;
        }

        return String.valueOf(curr);
    }

    private static void reduceFrequency() {
        switch (charPos) {
            case 'R' -> numOf_R--;
            case 'M' -> numOf_M--;
            case 'I' -> numOf_I--;
            case 'T' -> numOf_T--;
        }
    }
}
