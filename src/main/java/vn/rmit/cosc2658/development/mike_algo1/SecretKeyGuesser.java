package vn.rmit.cosc2658.development.mike_algo1;

import vn.rmit.cosc2658.development.SecretKey;


public class SecretKeyGuesser {
    private static final char[] CHAR = "RMIT".toCharArray();     // Possible letters
    private static final int[] charFreq = new int[CHAR.length];  // Number of occurrences (frequency) for each possible character
    private static int totalCharFreq = 0;                        // The sum of charFreq
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

        // Step 1: Find the frequency of each character R, M and I (except T - it will be calculated below). 3 guesses.
        for (int charHash = 0; totalCharFreq < secretKeyLength && charHash < CHAR.length - 1; charHash++) {
            String guess = Character.toString(CHAR[charHash]).repeat(secretKeyLength);

            int matchCount = secretKey.guess(guess);
            if (verbose) System.out.printf("Guessing \"%s\", %d match...\n", guess, matchCount);
            if (matchCount == secretKeyLength) {
                if (verbose) System.out.printf("I found the secret key. It is \"%s\"\n", guess);
                return guess;
            }

            charFreq[charHash] = matchCount;
            totalCharFreq += matchCount;
            if (charHash != 0 && charFreq[mostCommonCharHash] < matchCount) mostCommonCharHash = charHash;
        }

        // Step 2: totalCharFreq == 0 means key contains only the character T. Saves us 1 SecretKey.guess() call.
        if (totalCharFreq == 0) {
            String guess = "T".repeat(secretKeyLength);
            if (verbose) System.out.printf("I found the secret key. It is \"%s\"\n", guess);
            return guess;
        }

        // Step 3: Otherwise, Frequency of T = secretKeyLength - totalCharFreq. Saves us 1 SecretKey.guess() call.
        if (totalCharFreq < secretKeyLength) charFreq[CHAR.length - 1] = secretKeyLength - totalCharFreq;


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

        for (int charHash = (mostCommonCharHash + 1) % CHAR.length; charHash != mostCommonCharHash; charHash = (charHash + 1) % CHAR.length) {
            for (int index = 0; charFreq[charHash] > 0 && index < secretKeyLength; index++) {
                if (correct[index]) continue;


                char originalChar = guess[index];
                guess[index] = CHAR[charHash];
                int matchCount = secretKey.guess(String.valueOf(guess));
                if (verbose) System.out.printf("Guessing \"%s\", %d match...\n", String.valueOf(guess), cumulativeMatchCount);

                switch (matchCount - cumulativeMatchCount) {
                    case 1 -> {  // Found the correct character for this position: New replacement character
                        correct[index] = true;
                        charFreq[charHash]--;
                        cumulativeMatchCount = matchCount;
                    }
                    case -1 -> {  // Found the correct character for this position: Original baseline guess
                        correct[index] = true;
                        guess[index] = originalChar;
                    }
                }
            }
        }

        if (verbose) System.out.printf("I found the secret key. It is \"%s\"\n", String.valueOf(guess));
        return String.valueOf(guess);
    }

    public static String start(SecretKey sk, int skLen) {
        return start(sk, skLen, true);
    }
}
