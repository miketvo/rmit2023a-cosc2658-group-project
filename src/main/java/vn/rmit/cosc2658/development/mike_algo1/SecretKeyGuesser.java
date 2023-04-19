package vn.rmit.cosc2658.development.mike_algo1;

import vn.rmit.cosc2658.development.SecretKey;


public class SecretKeyGuesser {
    private static final char[] CHAR = "RMIT".toCharArray();     // Possible characters in secret key
    private static final int[] charFreq = new int[CHAR.length];  // Number of occurrences (frequency) for each possible character
    private static int mostCommonCharHash = 0;                   // For optimization purposes. Initial assumption: R


    /**
     * @param secretKey The secret key to be guessed.
     * @param secretKeyLength Length of the secret key.
     * @param verbose Switch for verbose output. Defaults to <strong>{@code false}</strong>.
     * @return The correct guess for the secret key.
     */
    public static String start(SecretKey secretKey, int secretKeyLength, boolean verbose) {
        /* **********************

        Initial Guesses
        ===============

        - Time complexity: O(1)
        - Space complexity: O(1)

        ********************** */
        int totalCharFreqRMI = 0;  // The sum of charFreq of R, M, and I
        for (int charHash = 0; totalCharFreqRMI < secretKeyLength && charHash < CHAR.length - 1; charHash++) {
            String guess = Character.toString(CHAR[charHash]).repeat(secretKeyLength);

            int matchCount = secretKey.guess(guess);
            if (verbose) System.out.printf("Guessing \"%s\", %d match...\n", guess, matchCount);
            if (matchCount == secretKeyLength) {
                if (verbose) System.out.printf("I found the secret key. It is \"%s\"\n", guess);
                return guess;
            }

            charFreq[charHash] = matchCount;
            totalCharFreqRMI += matchCount;
            if (charHash != 0 && charFreq[mostCommonCharHash] < matchCount) mostCommonCharHash = charHash;
        }

        if (totalCharFreqRMI == 0) {
            String guess = "T".repeat(secretKeyLength);
            if (verbose) System.out.printf("I found the secret key. It is \"%s\"\n", guess);
            return guess;
        }

        if (totalCharFreqRMI < secretKeyLength) charFreq[CHAR.length - 1] = secretKeyLength - totalCharFreqRMI;


        /* *************************************************************************************************************

        General Case "Linear Character Swap" Guessing Algorithm
        =======================================================

        - Time complexity: O(n)
        - Space complexity: O(n)

        Start with a baseline guess which is a string filled with the most common character (the one with the highest
        frequency). Then go through all other possible characters and check whether it can be used to replace any of the
        characters in the baseline guess until it has correctly guessed the entire secret key.

        This will save us more SecretKey.guess() calls, because our we would not have to call SecretKey.guess() for:
            1. Characters that we know are not in the key (frequency equal to 0 after the above steps);
            2. Multiple incorrect guesses on the same index.

        ************************************************************************************************************* */
        char[] guess = Character.toString(CHAR[mostCommonCharHash]).repeat(secretKeyLength).toCharArray();
        int cumulativeMatchCount = charFreq[mostCommonCharHash];
        boolean[] correct = new boolean[secretKeyLength];

        for (int charHash = nextCharHashOf(mostCommonCharHash); charHash != mostCommonCharHash; charHash = nextCharHashOf(charHash)) {
            for (int charPos = 0; charFreq[charHash] > 0 && charPos < secretKeyLength; charPos++) {
                if (correct[charPos]) continue;


                char originalChar = guess[charPos];
                guess[charPos] = CHAR[charHash];
                int newMatchCount = secretKey.guess(String.valueOf(guess));
                if (verbose) System.out.printf("Guessing \"%s\", %d match...\n", String.valueOf(guess), cumulativeMatchCount);

                switch (newMatchCount - cumulativeMatchCount) {
                    case 1 -> {  // New replacement character is the correct for this position
                        correct[charPos] = true;
                        charFreq[charHash]--;
                        cumulativeMatchCount = newMatchCount;
                    }
                    case -1 -> {  // Original baseline guess is correct for this position
                        correct[charPos] = true;
                        guess[charPos] = originalChar;
                    }
                }
            }
        }

        if (verbose) System.out.printf("I found the secret key. It is \"%s\"\n", String.valueOf(guess));
        return String.valueOf(guess);
    }

    /**
     * @param secretKey The secret key to be guessed.
     * @param secretKeyLength Length of the secret key.
     * @return The correct guess for the secret key.
     * @see SecretKeyGuesser#start(SecretKey, int, boolean)
     */
    public static String start(SecretKey secretKey, int secretKeyLength) {
        return start(secretKey, secretKeyLength, true);
    }


    private static int nextCharHashOf(int charHash) {
        return (charHash + 1) % CHAR.length;
    }
}
