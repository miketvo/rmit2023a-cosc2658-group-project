package vn.rmit.cosc2658.development.mike_algo2;


import vn.rmit.cosc2658.development.SecretKey;

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
        cumulativeCharFreq = secretKeyLength;


        /* *************************************************************************************************************

        General Case "Linear Character Swap - Hybrid" Guessing Algorithm
        ================================================================

        Based on characters distribution, choose one of the following algorithm to minimize number of guesses:
            1. Linear Character Swap - Depth First: Efficient for roughly equal distribution
            2. Linear Character Swap - Breadth First: Efficient for skewed distribution

        ************************************************************************************************************* */
        final char[] guess = Character.toString(rankCharByFrequency(charFreq)[0]).toCharArray();
        final boolean[] correct = new boolean[secretKeyLength];
        final double algoThreshold = secretKeyLength / 3.2;  // Based on test performance analysis and visualization using Python

        int currPos = 0;
        boolean foundCorrectKey = false;
        while (!foundCorrectKey) {
            char[] charCommonalityRank = rankCharByFrequency(charFreq);

            if (getCharacterFrequencyRange(charFreq) <= algoThreshold) {
                depthFirstSwap(
                        secretKey,
                        guess, correct,
                        currPos,
                        charFreq, charCommonalityRank,
                        verbose
                );
            } else {
                currPos = breadthFirstSwap(
                        secretKey,
                        guess, correct,
                        currPos + 1,
                        charFreq, charCommonalityRank,
                        verbose
                );
            }
            currPos++;
            cumulativeCharFreq--;

            charCommonalityRank = rankCharByFrequency(charFreq);
            char leastCommonChar = charCommonalityRank[charCommonalityRank.length - 1];
            if (getTotalCharFreq(charFreq) == charFreq[leastCommonChar] && cumulativeCharFreq == charFreq[leastCommonChar]) {
                foundCorrectKey = true;
            }
        }

        char lastChar = rankCharByFrequency(charFreq)[0];
        for (int charPos = 0; charFreq[hash(lastChar)] > 0; charPos++) {
            if (!correct[charPos]) {
                guess[charPos] = lastChar;
                charFreq[hash(lastChar)]--;
            }
        }

        if (verbose) System.out.printf("I found the secret key. It is \"%s\"\n", String.valueOf(guess));
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
            SecretKey secretKey,
            char[] guess, boolean[] correct,
            int startPos, int[] charFreq, char[] charCommonalityRank,
            boolean verbose
    ) {
        char mostCommonChar = charCommonalityRank[0];
        char leastCommonChar = charCommonalityRank[charCommonalityRank.length - 1];

        boolean foundCorrect = false;
        for (int nextCommonCharIndex = 1; nextCommonCharIndex < charCommonalityRank.length - 1; nextCommonCharIndex++) {
            int nextCommonCharHash = hash(charCommonalityRank[nextCommonCharIndex]);

            guess[startPos] = CHAR[nextCommonCharHash];
            int newMatchCount = secretKey.guess(String.valueOf(guess));
            guess[startPos] = mostCommonChar;
            if (verbose) System.out.printf("Guessing \"%s\", %d match...\n", String.valueOf(guess), newMatchCount);

            if (newMatchCount < charFreq[hash(mostCommonChar)]) {  // Most common character is correct for this position
                guess[startPos] = mostCommonChar;
                foundCorrect = true;
                charFreq[hash(mostCommonChar)]--;
                break;
            }

            if (newMatchCount > charFreq[hash(mostCommonChar)]) {  // Next most common character is correct for this position
                guess[startPos] = CHAR[nextCommonCharHash];
                foundCorrect = true;
                charFreq[nextCommonCharHash]--;
                break;
            }
        }

        if (!foundCorrect) {  // Least common character is correct for this position
            guess[startPos] = leastCommonChar;
            charFreq[hash(leastCommonChar)]--;
        }
    }

    private static int breadthFirstSwap(
            SecretKey secretKey,
            char[] guess, boolean[] correct,
            int startPos, int[] charFreq, char[] charCommonalityRank,
            boolean verbose
    ) {
        int secretKeyLength = guess.length;
        int mostCommonCharHash = hash(charCommonalityRank[0]);
        int cumulativeMatchCount = getCorrectCount(correct);

        for (int nextCommonCharIndex = 1; nextCommonCharIndex < charCommonalityRank.length - 1; nextCommonCharIndex++) {
            int nextCommonCharHash = hash(charCommonalityRank[nextCommonCharIndex]);
            for (int charPos = startPos; cumulativeMatchCount < secretKeyLength - 1 && charFreq[nextCommonCharHash] > 0 && charPos < secretKeyLength; charPos++) {
                if (correct[charPos]) continue;

                guess[charPos] = CHAR[nextCommonCharHash];
                int newMatchCount = secretKey.guess(String.valueOf(guess));
                if (verbose) System.out.printf("Guessing \"%s\", %d match...\n", String.valueOf(guess), cumulativeMatchCount);

                switch (newMatchCount - cumulativeMatchCount) {
                    case 1 -> {  // New replacement character is correct for this position
                        correct[charPos] = true;
                        charFreq[nextCommonCharHash]--;
                        return charPos;
                    }
                    case -1 -> {  // Original baseline most common character guess is correct for this position
                        correct[charPos] = true;
                        charFreq[mostCommonCharHash]--;
                        guess[charPos] = CHAR[mostCommonCharHash];
                        return charPos;
                    }
                }
            }
        }

        return startPos;
    }


    private static int getTotalCharFreq(int[] charFreq) {
        int sum = 0;
        for (int freq : charFreq) {
            sum += freq;
        }

        return sum;
    }

    private static int getCorrectCount(boolean[] correct) {
        int sum = 0;
        for (boolean position : correct) {
            if (position) sum++;
        }

        return sum;
    }

    private static int getCharacterFrequencyRange(int[] charFreq) {
        int minFreq = charFreq[0], maxFreq = charFreq[0];
        for (int i = 1; i < charFreq.length; i++) {
            if (charFreq[i] > 0) {
                if (minFreq > charFreq[i]) minFreq = charFreq[i];
                if (maxFreq < charFreq[i]) maxFreq = charFreq[i];
            }
        }

        return maxFreq - minFreq;
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
        if (freqs == null) throw  new IllegalArgumentException("SecretKeyGuesser.rankCharByFrequency(int[]): Frequency map cannot be null!");
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
