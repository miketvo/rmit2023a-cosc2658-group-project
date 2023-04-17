package vn.rmit.cosc2658.tony_algo1;

import vn.rmit.cosc2658.SecretKey;

public class SecretKeyGuesser {
    private static final int CHAR_COUNT = 3;
    int numOf_R, numOf_I, numOf_M, numOf_T;
    int[] numOfChar = new int[4]; // R -> M -> I -> T
    String[] initGuess = new String[]{"RRRR", "MMMM", "IIII", "TTTT"};
    char[] validChar = new char[]{'R', 'M', 'I', 'T'};
    int charPos = 0;
    int positionInKey = 0;
    public void start(SecretKey key) {
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

    private void setCharFrequency(int frequency) {
        switch (validChar[charPos]) {
            case 'R' -> numOf_R = frequency;
            case 'M' -> numOf_M = frequency;
            case 'I' -> numOf_I = frequency;
            case 'T' -> numOf_T = frequency;
        }
    }

    private void moveCharType() {
        if (charPos == CHAR_COUNT) {
            charPos++;
        } else {
            charPos = 0;
        }
    }

    public String next(String current, int maxMatch, int currentMatch) {
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

    private void reduceFrequency() {
        switch (charPos) {
            case 'R' -> numOf_R--;
            case 'M' -> numOf_M--;
            case 'I' -> numOf_I--;
            case 'T' -> numOf_T--;
        }
    }
}
