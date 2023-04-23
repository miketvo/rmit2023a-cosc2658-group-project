package vn.rmit.cosc2658.development.mike_algo2;

import vn.rmit.cosc2658.development.SecretKey;


@SuppressWarnings({"DuplicatedCode", "ManualArrayCopy"})
public class SecretKeyGuesser {
    private static final char[] CHAR = "RMIT".toCharArray(); // Possible characters in secret key


    /**
     * <ul>
     *     <li>Time complexity: O(n)</li>
     *     <li>Space complexity: O(n)</li>
     *     <li>Guess complexity: O(n)</li>
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
        - Guess complexity: O(1)

        ********************** */
        int cumulativeCharFreq = 0;  // The sum of charFreq of R, M, I, and T
        for (int charHash = 0; cumulativeCharFreq < secretKeyLength && charHash < CHAR.length - 1; charHash++) {
            String guess = Character.toString(CHAR[charHash]).repeat(secretKeyLength);

            int matchCount = secretKey.guess(guess);
            if (verbose) System.out.printf("Guessing \"%s\", %d match...\n", guess, matchCount);
            if (matchCount == secretKeyLength) {
                if (verbose) System.out.printf("I found the secret key. It is \"%s\"\n", guess);
                return guess;
            }

            charFreq[charHash] = matchCount;
            cumulativeCharFreq += matchCount;
        }

        if (cumulativeCharFreq == 0) {
            String guess = "T".repeat(secretKeyLength);
            if (verbose) System.out.printf("I found the secret key. It is \"%s\"\n", guess);
            return guess;
        }

        charFreq[CHAR.length - 1] = secretKeyLength - cumulativeCharFreq;


        /* *************************************************************************************************************

        General Case "Linear Character Swap - Hybrid" Guessing Algorithm
        ================================================================

        Based on characters distribution, choose one of the following algorithm to minimize number of guesses:
            1. Depth First Swap: Efficient for roughly equal distribution
            2. Breadth First Swap: Efficient for skewed distribution

        ************************************************************************************************************* */
        int baselineMatchCount = charFreq[hash(rankCharByFrequency(charFreq)[0])];
        char mostCommonChar = rankCharByFrequency(charFreq)[0];
        String baselineGuess = Character.toString(mostCommonChar).repeat(secretKeyLength);
        Guess guess = new Guess(
                baselineGuess.toCharArray(),
                baselineMatchCount
        );

        int currPos = 0;
        boolean foundCorrectKey = false;
        while (!foundCorrectKey) {
            if (guess.isCorrectAt(currPos)) {
                currPos++;
                continue;
            }

            char[] charCommonalityRank = rankCharByFrequency(charFreq);
            double algoThreshold = (secretKeyLength - guess.getMatchCount()) / 3.2;  // Based on test performance analysis and visualization using Python
            if (getCharacterFrequencyRange(charFreq, charCommonalityRank) <= algoThreshold) {
                depthFirstSwap(
                        secretKey, guess,
                        currPos, charFreq, charCommonalityRank,
                        verbose
                );
            } else {
                breadthFirstSwap(
                        secretKey, guess,
                        currPos, charFreq, charCommonalityRank,
                        verbose
                );
            }

            charCommonalityRank = rankCharByFrequency(charFreq);
            char leastCommonChar = charCommonalityRank[charCommonalityRank.length - 1];
            if (getTotalCharFreq(charFreq) == charFreq[hash(leastCommonChar)]) {
                foundCorrectKey = true;
            }
        }

        char lastChar = rankCharByFrequency(charFreq)[0];
        for (int charPos = 0; charPos < guess.length; charPos++) {
            if (!guess.isCorrectAt(charPos)) {
                guess.setCharAt(charPos, lastChar);
                charFreq[hash(lastChar)]--;
            }
        }

        if (verbose) System.out.printf("I found the secret key. It is \"%s\"\n", guess);
        return String.valueOf(guess);
    }

    /**
     * @param secretKey The secret key to be guessed.
     * @param secretKeyLength Length of the secret key.
     * @return The correct guess for the secret key.
     */
    public static String start(SecretKey secretKey, int secretKeyLength) {
        return start(secretKey, secretKeyLength, true);
    }


    private static void depthFirstSwap(
            SecretKey secretKey, Guess guess,
            int charPos, int[] charFreq, char[] charCommonalityRank,
            boolean verbose
    ) {
        char leastCommonChar = charCommonalityRank[charCommonalityRank.length - 1];

        for (int nextCommonCharIndex = 0; nextCommonCharIndex < charCommonalityRank.length - 1; nextCommonCharIndex++) {
            int nextCommonCharHash = hash(charCommonalityRank[nextCommonCharIndex]);

            char baselineGuess = guess.getCharAt(charPos);
            guess.setCharAt(charPos, CHAR[nextCommonCharHash]);
            int newMatchCount = secretKey.guess(guess.toString());
            if (verbose) System.out.printf("Guessing \"%s\", %d match...\n", guess, newMatchCount);

            if (newMatchCount < guess.getMatchCount()) {  // Original baseline guess character is correct for this position
                guess.setCharAt(charPos, baselineGuess);
                guess.setCorrectAt(charPos);
                charFreq[hash(baselineGuess)]--;
                break;
            }

            if (newMatchCount > guess.getMatchCount()) {  // Next most common character is correct for this position
                guess.setCorrectAt(charPos);
                charFreq[nextCommonCharHash]--;
                guess.setMatchCount(newMatchCount);
                break;
            }
        }

        if (!guess.isCorrectAt(charPos)) {  // Remaining most common character is correct for this position
            guess.setCharAt(charPos, leastCommonChar);
            guess.setCorrectAt(charPos);
            charFreq[hash(leastCommonChar)]--;
            guess.setMatchCount(guess.getMatchCount() + 1);
        }
    }

    private static void breadthFirstSwap(
            SecretKey secretKey, Guess guess,
            int startCharPos, int[] charFreq, char[] charCommonalityRank,
            boolean verbose
    ) {
        int secretKeyLength = guess.length;

        for (int nextCommonCharIndex = 1; nextCommonCharIndex < charCommonalityRank.length - 1; nextCommonCharIndex++) {
            int nextCommonCharHash = hash(charCommonalityRank[nextCommonCharIndex]);
            if (charFreq[nextCommonCharHash] == 0) continue;

            for (int charPos = startCharPos; guess.getMatchCount() < secretKeyLength - 1 && charPos < secretKeyLength; charPos++) {
                if (guess.isCorrectAt(charPos)) continue;

                char baselineGuess = guess.getCharAt(charPos);
                guess.setCharAt(charPos, CHAR[nextCommonCharHash]);
                int newMatchCount = secretKey.guess(guess.toString());
                if (verbose) System.out.printf("Guessing \"%s\", %d match...\n", guess, newMatchCount);

                switch (newMatchCount - guess.getMatchCount()) {
                    case 1 -> {  // New replacement character is correct for this position
                        guess.setCorrectAt(charPos);
                        charFreq[nextCommonCharHash]--;
                        guess.setMatchCount(newMatchCount);
                        return;
                    }
                    case -1 -> {  // Original baseline guess character guess is correct for this position
                        guess.setCharAt(charPos, baselineGuess);
                        guess.setCorrectAt(charPos);
                        charFreq[hash(baselineGuess)]--;
                        return;
                    }
                }
            }
        }

        throw new RuntimeException("SecretKeyGuesser.breadthFirstSwap() has exhausted all possible positions!");
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

    private static int getTotalCharFreq(int[] charFreq) {
        int sum = 0;
        for (int freq : charFreq) {
            sum += freq;
        }
        return sum;
    }

    private static int getCharacterFrequencyRange(int[] charFreq, char[] charCommonalityRank) {
        int maxFreq = charFreq[hash(charCommonalityRank[0])];
        int minFreq = charFreq[hash(charCommonalityRank[charCommonalityRank.length - 1])];
        return maxFreq - minFreq;
    }

    /**
     * <p>
     *     Returns a sorted (descending) array of all possible characters by their frequencies. Characters with
     *     frequency 0 are not included.
     * </p>
     *
     * <ul>
     *     <li>Time complexity: O(n log n)</li>
     *     <li>Space complexity: O(n)</li>
     * </ul>
     * @param freqs Frequency map for each possible character, where freqs[hash(c)] is the frequency for character c.
     * @see SecretKeyGuesser#hash(char)
     */
    protected static char[] rankCharByFrequency(int[] freqs) {
        if (freqs == null) throw new IllegalArgumentException("SecretKeyGuesser.rankCharByFrequency(int[]): Frequency map cannot be null!");
        if (CHAR.length != freqs.length) throw new IllegalArgumentException("SecretKeyGuesser.rankCharByFrequency(int[]): Invalid frequency map size!");

        char[] rankedChars = new char[CHAR.length];
        for (int i = 0; i < CHAR.length; i++) rankedChars[i] = CHAR[i];
        mergeSort(rankedChars, freqs, 0, CHAR.length - 1);

        int nonZeroFreqCharCount = 0;
        for (int freq : freqs) if (freq > 0) nonZeroFreqCharCount++;
        char[] rankedCharsFiltered = new char[nonZeroFreqCharCount];
        for (int i = 0; i < nonZeroFreqCharCount; i++) rankedCharsFiltered[i] = rankedChars[i];

        return rankedCharsFiltered;
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
