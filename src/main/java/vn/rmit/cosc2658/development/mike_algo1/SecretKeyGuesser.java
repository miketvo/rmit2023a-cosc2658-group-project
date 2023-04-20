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
        frequency). Then proceed to guess each character in the secret key one at a time, using the results of the
        previous guesses to adjust the next guess:
            1. Replace the character in the current position with the next most common character that hasn't been ruled
            out and checks how many characters match the secret key. If the number of matching characters is less than
            the most common character, assume that character is correct.
            2. If the number of matching characters is greater than the most common character, the algorithm assumes the
            next most common character is correct.
            3. If neither condition is met, the algorithm assumes the least common character is correct.

        This will save us more SecretKey.guess() calls, because our we would not have to call SecretKey.guess() for:
            - Characters that we know are not in the key (frequency equal to 0 after the above steps);
            - Low probability guesses on the same index;
            - All guesses for the least common character;
            - The last secret key character position.

        ************************************************************************************************************* */
        final char[] charCommonalityRank = rankCharByFrequency(charFreq);  // For optimization purposes.

        char mostCommonChar = charCommonalityRank[0];
        char leastCommonChar = charCommonalityRank[CHAR.length - 1];
        char[] baselineGuess = Character.toString(mostCommonChar).repeat(secretKeyLength).toCharArray();
        char[] correctKey = new char[secretKeyLength];

        int[] charFreqPool = new int[CHAR.length];
        for (int i = 0; i < CHAR.length; i++) charFreqPool[i] = charFreq[i];

        for (int charPos = 0; charPos < secretKeyLength - 1; charPos++) {
            boolean foundCorrect = false;
            for (int nextCommonCharIndex = 1; nextCommonCharIndex < CHAR.length - 1; nextCommonCharIndex++) {
                int nextCommonCharHash = hash(charCommonalityRank[nextCommonCharIndex]);
                if (charFreq[nextCommonCharHash] == 0) continue;

                baselineGuess[charPos] = CHAR[nextCommonCharHash];
                int newMatchCount = secretKey.guess(String.valueOf(baselineGuess));
                baselineGuess[charPos] = mostCommonChar;
                if (verbose) System.out.printf("Guessing \"%s\", %d match...\n", String.valueOf(baselineGuess), newMatchCount);

                if (newMatchCount < charFreq[hash(mostCommonChar)]) {  // Most common character is correct for this position
                    correctKey[charPos] = mostCommonChar;
                    foundCorrect = true;
                    charFreqPool[hash(mostCommonChar)]--;
                    break;
                }

                if (newMatchCount > charFreq[hash(mostCommonChar)]) {  // Next most common character is correct for this position
                    correctKey[charPos] = CHAR[nextCommonCharHash];
                    foundCorrect = true;
                    charFreqPool[nextCommonCharHash]--;
                    break;
                }
            }

            if (!foundCorrect) {  // Least common character is correct for this position
                correctKey[charPos] = leastCommonChar;
                charFreqPool[hash(leastCommonChar)]--;
            }
        }

        char lastChar = CHAR[0];
        for (int rank = CHAR.length - 1; rank >= 0; rank--) {
            if (charFreqPool[hash(charCommonalityRank[rank])] > 0) {
                lastChar = charCommonalityRank[rank];
                break;
            }
        }

        correctKey[secretKeyLength - 1] = lastChar;


        if (verbose) System.out.printf("I found the secret key. It is \"%s\"\n", String.valueOf(correctKey));
        return String.valueOf(correctKey);
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
