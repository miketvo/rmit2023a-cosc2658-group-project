package vn.rmit.cosc2658.development.tony_algo1;

import vn.rmit.cosc2658.development.SecretKey;


public class SecretKeyGuesser {
    private static final int CHAR_COUNT = 3;
    static int numOf_R, numOf_I, numOf_M, numOf_T;
    static int[] numOfChar = new int[4]; // R -> M -> I -> T
    static String[] initGuess = new String[]{"RRRR", "MMMMM", "IIII", "TTTT"};
    static char[] validChar = new char[]{'R', 'M', 'I', 'T'};
    static int charPos = 0;
    static int positionInKey = 0;
    static String mostCorrectGuess = "RRRRRRRRRRRRRRRR";
    public static void start(SecretKey key) {
        String str = "RRRRRRRRRRRRRRRR";
        int match = key.guess(str);
        int maxMatch = match;
        int maxInitFrequency = 0;

//        while(match != 4) {
//            str = initGuess[charPos]; // start by guessing "RRRR"
//            match = key.guess(str);
//            numOfChar[charPos] = match;
//            maxInitFrequency = Math.max(maxInitFrequency, match);
//
//            setCharFrequency(match);
//
//            moveCharType();
//            if (validChar[charPos] == 'R') {
//                maxMatch = match; //  set max match at "TTTT"
//                if (maxInitFrequency == numOf_R) {
//                    str = "MRRR";
//                }
//                if (maxInitFrequency == numOf_M) {
//                    str = "IMMM";
//                }
//                if (maxInitFrequency == numOf_I) {
//                    str = "TIII";
//                }
//                if (maxInitFrequency == numOf_T) {
//                    str = "RTTT";
//                }
//                mostCorrectGuess = str;
//                break;
//            }
//        }

        while (match != 16) {

//            switch (charPos) {
//                // execute code if numOf_R is greater than 0 for charPos 'R'
//                case 'R' -> {
//                    if (numOf_R <= 0) {
//                        continue;
//                    }
//                }
//                case 'M' -> {
//                    if (numOf_M <= 0) {
//                        continue;
//                    }
//                }
//                case 'I' -> {
//                    if (numOf_I <= 0) {
//                        continue;
//                    }
//                }
//                case 'T' -> {
//                    if (numOf_T <= 0) {
//                        continue;
//                    }
//                }
//            }

            moveCharType();
            match = key.guess(str);
            str = next(str, maxMatch, match);
            maxMatch = Math.max(match, maxMatch);
        }
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
        if (charPos < CHAR_COUNT) {
            charPos++;
        } else {
            charPos = 0;
        }
    }

    public static String next(String current, int prevMatch, int currentMatch) {
        char[] nextStr = current.toCharArray();
        nextStr[positionInKey] = validChar[charPos];

        //TODO: Algorithm goes here

        // if the max match decrease after changing the char
        // it means the previous character is correct
        // for example, "TTTT" -> 1
        // but "RTTT" -> 0 => The first 'T' is correct

        if (currentMatch > prevMatch) { // the current key is the closet to real key
            positionInKey++;
//            reduceFrequency(charPos);
            mostCorrectGuess = current;
            return current;
        }

        if (currentMatch < prevMatch) { // the previous key is the closet to real key
            positionInKey++;
//            reduceFrequency(charPos-1);
            return mostCorrectGuess;
        }

        return String.valueOf(nextStr);
    }

    private static void reduceFrequency(int charPosition) {
        switch (validChar[charPosition]) {
            case 'R' -> numOf_R--;
            case 'M' -> numOf_M--;
            case 'I' -> numOf_I--;
            case 'T' -> numOf_T--;
        }
    }
}
