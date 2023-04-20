package vn.rmit.cosc2658.development.mike_algo1;

import vn.rmit.cosc2658.development.SecretKey;


public class SecretKeyGuesser {
    private static final char[] CHAR = "RMIT".toCharArray(); // Possible characters in secret key


    /**
     * <ul>
     *     <li>Time complexity: O(n)</li>
     *     <li>Space complexity: O(n)</li>
     * </ul>
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
        whether it can be used to replace any of the characters in the baseline guess until it has correctly guessed all
        positions in the secret key except for the last incorrect one. This last incorrect one is simply the character
        with the smallest remaining non-zero frequency.

        This will save us more SecretKey.guess() calls, because our we would not have to call SecretKey.guess() for:
            1. Characters that we know are not in the key (frequency equal to 0 after the above steps);
            2. Multiple incorrect guesses on the same index.
            3. The last incorrect character position.

        ************************************************************************************************************* */
        final char[] charCommonalityRank = rankCharByFrequency(charFreq);  // For optimization purposes.

        int mostCommonCharHash = hash(charCommonalityRank[0]);
        char[] guess = Character.toString(charCommonalityRank[0]).repeat(secretKeyLength).toCharArray();
        int cumulativeMatchCount = charFreq[mostCommonCharHash];
        boolean[] correct = new boolean[secretKeyLength];
        int correctCount = 0;

        for (int nextCommonCharIndex = 1; nextCommonCharIndex < charCommonalityRank.length; nextCommonCharIndex++) {
            int nextCommonCharHash = hash(charCommonalityRank[nextCommonCharIndex]);
            for (int charPos = 0; correctCount < secretKeyLength - 1 && charFreq[nextCommonCharHash] > 0 && charPos < secretKeyLength; charPos++) {
                if (correct[charPos]) continue;


                char originalChar = guess[charPos];
                guess[charPos] = CHAR[nextCommonCharHash];
                int newMatchCount = secretKey.guess(String.valueOf(guess));
                if (verbose) System.out.printf("Guessing \"%s\", %d match...\n", String.valueOf(guess), cumulativeMatchCount);

                switch (newMatchCount - cumulativeMatchCount) {
                    case 1 -> {  // New replacement character is the correct for this position
                        correct[charPos] = true;
                        correctCount++;
                        charFreq[nextCommonCharHash]--;
                        cumulativeMatchCount = newMatchCount;
                    }
                    case -1 -> {  // Original baseline guess is correct for this position
                        correct[charPos] = true;
                        correctCount++;
                        guess[charPos] = originalChar;
                    }
                }
            }
        }

        int lastIncorrectPos = -1;
        for (int pos = 0; lastIncorrectPos < 0 && pos < secretKeyLength; pos++) {
            if (!correct[pos]) lastIncorrectPos = pos;
        }

        char lastChar = CHAR[0];
        for (int rank = CHAR.length - 1; rank >= 0; rank--) {
            if (charFreq[hash(charCommonalityRank[rank])] > 0) {
                lastChar = charCommonalityRank[rank];
                break;
            }
        }

        guess[lastIncorrectPos] = lastChar;


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
     * <p>Returns a sorted (descending) array of all possible characters by their frequencies.</p>
     *
     * <ul>
     *     <li>Time complexity: O(n log n)</li>
     *     <li>Space complexity: O(n)</li>
     * </ul>
     * @param freqs Frequency map for each possible character, where freqs[hash(c)] is the frequency for character c.
     * @see SecretKeyGuesser#hash(char)
     */
    protected static char[] rankCharByFrequency(int[] freqs) {
        if (freqs == null) throw  new IllegalArgumentException("SecretKeyGuesser.rankCharByFrequency(int[]): Frequency map cannot be null!");
        if (CHAR.length != freqs.length) throw new IllegalArgumentException("SecretKeyGuesser.rankCharByFrequency(int[]): Invalid frequency map size!");

        char[] rankedChars = new char[CHAR.length];
        for (int i = 0; i < CHAR.length; i++) rankedChars[i] = CHAR[i];
        mergeSort(rankedChars, freqs, 0, CHAR.length - 1);

        return rankedChars;
    }

    private static void mergeSort(char[] rankedChars, int[] freqs, int start, int end) {
        if (start < end) {
            int middle = (start + end) / 2;
            mergeSort(rankedChars, freqs, start, middle);
            mergeSort(rankedChars, freqs, middle + 1, end);
            merge(rankedChars, freqs, start, middle, end);
        }
    }

    private static void merge(char[] rankedChars, int[] freqs, int start, int middle, int end) {
        char[] left = new char[middle - start + 1];
        for (int i = start, j = 0; i <= middle; i++, j++) {
            left[j] = rankedChars[i];
        }

        char[] right = new char[end - middle];
        for (int i = middle + 1, j = 0; i <= end; i++, j++) {
            right[j] = rankedChars[i];
        }

        int leftIndex = 0, rightIndex = 0, index = start;

        while (leftIndex < left.length && rightIndex < right.length) {
            if (freqs[hash(left[leftIndex])] >= freqs[hash(right[rightIndex])]) {
                rankedChars[index] = left[leftIndex];
                leftIndex++;
            } else {
                rankedChars[index] = right[rightIndex];
                rightIndex++;
            }
            index++;
        }

        while (leftIndex < left.length) {
            rankedChars[index] = left[leftIndex];
            leftIndex++;
            index++;
        }

        while (rightIndex < right.length) {
            rankedChars[index] = right[rightIndex];
            rightIndex++;
            index++;
        }
    }
}
