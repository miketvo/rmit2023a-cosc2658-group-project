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

        while (match != 16) {

            moveCharType();
            match = key.guess(str); // starting from "RTTT"
            str = next(str, maxMatch, match);
            maxMatch = Math.max(match, maxMatch);
        }
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

        if (currentMatch < maxMatch || currentMatch > maxMatch) {
            positionInKey++;
            return current;
        }

        return String.valueOf(curr);
    }
}
