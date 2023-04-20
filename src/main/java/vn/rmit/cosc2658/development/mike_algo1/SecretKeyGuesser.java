package vn.rmit.cosc2658.development.mike_algo1;

import vn.rmit.cosc2658.development.SecretKey;


public class SecretKeyGuesser {
    private static final char[] CHAR = "RMIT".toCharArray(); // Possible characters in secret key


    /**
     * @param secretKey The secret key to be guessed.
     * @param secretKeyLength Length of the secret key.
     * @param verbose Switch for verbose output. Defaults to <strong>{@code false}</strong>.
     * @return The correct guess for the secret key.
     */
    public static String start(SecretKey secretKey, int secretKeyLength, boolean verbose) {
        final int[] charFreq = new int[CHAR.length];  // Number of occurrences (frequency) for each possible character

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
        frequency). Then go through all other possible characters in order from most common to least common and check
        whether it can be used to replace any of the characters in the baseline guess until it has correctly guessed the
        entire secret key.

        This will save us more SecretKey.guess() calls, because our we would not have to call SecretKey.guess() for:
            1. Characters that we know are not in the key (frequency equal to 0 after the above steps);
            2. Multiple incorrect guesses on the same index.

        ************************************************************************************************************* */
        final char[] charCommonalityRank = rankCharByFrequency(charFreq);  // For optimization purposes.

        int mostCommonCharHash = hash(charCommonalityRank[0]);
        char[] guess = Character.toString(charCommonalityRank[0]).repeat(secretKeyLength).toCharArray();
        int cumulativeMatchCount = charFreq[mostCommonCharHash];
        boolean[] correct = new boolean[secretKeyLength];

        for (int nextCommonCharIndex = 1; nextCommonCharIndex < charCommonalityRank.length; nextCommonCharIndex++) {
            int nextCommonCharHash = hash(charCommonalityRank[nextCommonCharIndex]);
            for (int charPos = 0; charFreq[nextCommonCharHash] > 0 && charPos < secretKeyLength; charPos++) {
                if (correct[charPos]) continue;


                char originalChar = guess[charPos];
                guess[charPos] = CHAR[nextCommonCharHash];
                int newMatchCount = secretKey.guess(String.valueOf(guess));
                if (verbose) System.out.printf("Guessing \"%s\", %d match...\n", String.valueOf(guess), cumulativeMatchCount);

                switch (newMatchCount - cumulativeMatchCount) {
                    case 1 -> {  // New replacement character is the correct for this position
                        correct[charPos] = true;
                        charFreq[nextCommonCharHash]--;
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


    private static int hash(char character) {
        return switch (character) {
            case 'R' -> 0;
            case 'M' -> 1;
            case 'I' -> 2;
            case 'T' -> 3;
            default -> throw new IllegalStateException("Unexpected value: " + character);
        };
    }

    /**
     * Returns a sorted (descending) array of all possible characters by their frequencies.
     * @param freqs Frequency map for each possible character, where freqs[hash(c)] is the frequency for character c;
     * @see SecretKeyGuesser#hash(char)
     */
    protected static char[] rankCharByFrequency(int[] freqs) {
        if (CHAR.length != freqs.length) throw new IllegalArgumentException("SecretKeyGuesser.rankCharByFrequency(int[]): Invalid frequency map size!");


        // TODO: Implement a more efficient sorting algorithm
        char[] rankedChars = new char[CHAR.length];
        for (int i = 0; i < CHAR.length; i++) rankedChars[i] = CHAR[i];

        boolean swapped;
        int n = CHAR.length;
        do {
            swapped = false;
            for (int i = 1; i < n; i++) {
                if (freqs[hash(rankedChars[i - 1])] < freqs[hash(rankedChars[i])]) {
                    char temp = rankedChars[i-1];
                    rankedChars[i - 1] = rankedChars[i];
                    rankedChars[i] = temp;
                    swapped = true;
                }
            }
            n--;
        } while (swapped);
        return rankedChars;
    }
}
