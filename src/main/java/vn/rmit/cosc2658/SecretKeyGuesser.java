package vn.rmit.cosc2658;


@SuppressWarnings({"DuplicatedCode", "ManualArrayCopy"})
public class SecretKeyGuesser {
    /**
     * <ul>
     *     <li>Time complexity: O(n)</li>
     *     <li>Space complexity: O(n)</li>
     *     <li>Guess complexity: O(n)</li>
     * </ul>
     * @see vn.rmit.cosc2658.development.mike_algo1.SecretKeyGuesser
     * @see SecretKeyGuesser#linearCharacterSwapDepthFirst(SecretKey, int, int[], char[], char[])
     * @see SecretKeyGuesser#linearCharacterSwapDepthFirst(SecretKey, int, int[], char[], char[])
     */
    public void start() {
        final int secretKeyLength = 16;
        final char[] CHAR = "RMIT".toCharArray(); // Possible characters in secret key
        final int[] charFreq = new int[CHAR.length];  // Number of occurrences (frequency) for each possible character
        SecretKey secretKey = new SecretKey();

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
            System.out.printf("Guessing \"%s\", %d match...\n", guess, matchCount);
            if (matchCount == secretKeyLength) {
                System.out.printf("I found the secret key. It is \"%s\"\n", guess);
            }

            charFreq[charHash] = matchCount;
            cumulativeCharFreq += matchCount;
        }

        if (cumulativeCharFreq == 0) {
            String guess = "T".repeat(secretKeyLength);
            System.out.printf("I found the secret key. It is \"%s\"\n", guess);
        }

        charFreq[CHAR.length - 1] = secretKeyLength - cumulativeCharFreq;


        /* *************************************************************************************************************

        Smart Guesses
        =============

        Based on characters distribution, choose one of the following algorithm to minimize number of guesses:
            1. Linear Character Swap - Depth First: Efficient for roughly equal distribution
            2. Linear Character Swap - Breadth First: Efficient for skewed distribution

        ************************************************************************************************************* */
        final char[] charCommonalityRank = rankCharByFrequency(charFreq, CHAR);  // For optimization purposes.
        double autoThreshold = secretKeyLength / 3.2;  // Based on test performance analysis and visualization using Python.
        if (getCharacterFrequencyRange(charFreq) <= autoThreshold) {
            linearCharacterSwapDepthFirst(secretKey, secretKeyLength, charFreq, charCommonalityRank, CHAR);
        } else {
            linearCharacterSwapBreadthFirst(secretKey, secretKeyLength, charFreq, charCommonalityRank, CHAR);
        }
    }


    /**
     *
     * <h1>General Case "Linear Character Swap - Depth First" Guessing Algorithm</h1>
     *
     * <ul>
     *     <li>Time complexity: O(n)</li>
     *     <li>Space complexity: O(n)</li>
     *     <li>Guess complexity: O(n)</li>
     * </ul>
     *
     * <p>
     *     Start with a baseline guess which is a string filled with the most common character (the one with the highest
     *     frequency). Then proceed to guess each character in the secret key one at a time, using the results of the
     *     previous guesses to adjust the next guess:
     * </p>
     *
     * <ol>
     *     <li>
     *         Replace the character in the current position with the next most common character that hasn't been ruled
     *         out and checks how many characters match the secret key. If the number of matching characters is less
     *         than the most common character, assume that character is correct.
     *     </li>
     *     <li>
     *         If the number of matching characters is greater than the most common character, the algorithm assumes the
     *         next most common character is correct.
     *     </li>
     *     <li>
     *         If neither condition is met, the algorithm assumes the least common character is correct.
     *     </li>
     * </ol>
     *
     * <p>
     *     This will save us more SecretKey.guess() calls, because our we would not have to call SecretKey.guess() for:
     * </p>
     *
     * <ul>
     *     <li>Characters that we know are not in the key (frequency equal to 0 after the above steps);</li>
     *     <li>Low probability guesses on the same index;</li>
     *     <li>All guesses for the least common character;</li>
     *     <li>The last secret key character position.</li>
     * </ul>
     *
     * @param secretKey The secret key to be guessed.
     * @param charFreq Frequencies of the possible characters in the key.
     * @param charCommonalityRank Possible characters in the key, ranked in descending order by their frequency.
     * @see SecretKeyGuesser#linearCharacterSwapBreadthFirst(SecretKey, int, int[], char[], char[])
     */
    private static void linearCharacterSwapDepthFirst(SecretKey secretKey, int secretKeyLength, int[] charFreq, char[] charCommonalityRank, char[] CHAR) {
        char mostCommonChar = charCommonalityRank[0];
        char leastCommonChar = mostCommonChar;
        for (int rank = CHAR.length - 1; rank > 0; rank--) {
            if (charFreq[hash(charCommonalityRank[rank])] > 0) {
                leastCommonChar = charCommonalityRank[rank];
                break;
            }
        }
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
                System.out.printf("Guessing \"%s\", %d match...\n", String.valueOf(baselineGuess), newMatchCount);

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


        System.out.printf("I found the secret key. It is \"%s\"\n", String.valueOf(correctKey));
    }

    /**
     * <h1>General Case "Linear Character Swap - Breadth First" Guessing Algorithm</h1>
     *
     * <ul>
     *     <li>Time complexity: O(n)</li>
     *     <li>Space complexity: O(n)</li>
     *     <li>Guess complexity: O(n)</li>
     * </ul>
     *
     * <p>
     *     Start with a baseline guess which is a string filled with the most common character (the one with the highest
     *     frequency). Then go through all other possible characters in order from most common to least common and check
     *     whether it can be used to replace any of the characters in the baseline guess until it has correctly guessed
     *     all positions in the secret key except for the last incorrect one. This last incorrect one is simply the
     *     character with the smallest remaining non-zero frequency.
     * </p>
     *
     * <p>
     *     This will save us more SecretKey.guess() calls, because our we would not have to call SecretKey.guess() for:
     * </p>
     *
     * <ul>
     *     <li>Characters that we know are not in the key (frequency equal to 0 after the above steps);</li>
     *     <li>Low probability guesses on the same index;</li>
     *     <li>Unnecessary guesses for the least common character;</li>
     *     <li>The last secret key character position.</li>
     * </ul>
     * @param secretKey The secret key to be guessed.
     * @param charFreq Frequencies of the possible characters in the key.
     * @param charCommonalityRank Possible characters in the key, ranked in descending order by their frequency.
     * @see SecretKeyGuesser#linearCharacterSwapDepthFirst(SecretKey, int, int[], char[], char[]) 
     */
    private static void linearCharacterSwapBreadthFirst(SecretKey secretKey, int secretKeyLength, int[] charFreq, char[] charCommonalityRank, char[] CHAR) {
        int mostCommonCharHash = hash(charCommonalityRank[0]);
        int leastCommonCharHash = 0;
        for (int rank = CHAR.length - 1; rank > 0; rank--) {
            if (charFreq[hash(charCommonalityRank[rank])] > 0) {
                leastCommonCharHash = hash(charCommonalityRank[rank]);
                break;
            }
        }
        int totalCharFreq = secretKeyLength;

        char[] guess = Character.toString(charCommonalityRank[0]).repeat(secretKeyLength).toCharArray();
        int cumulativeMatchCount = charFreq[mostCommonCharHash];
        boolean[] correct = new boolean[secretKeyLength];
        int correctCount = 0;

        for (int nextCommonCharIndex = 1; nextCommonCharIndex < CHAR.length; nextCommonCharIndex++) {
            int nextCommonCharHash = hash(charCommonalityRank[nextCommonCharIndex]);
            for (int i = 0; correctCount < secretKeyLength - 1 && charFreq[nextCommonCharHash] > 0 && i < secretKeyLength; i++) {
                if (correct[i]) continue;


                guess[i] = CHAR[nextCommonCharHash];
                int newMatchCount = secretKey.guess(String.valueOf(guess));
                System.out.printf("Guessing \"%s\", %d match...\n", String.valueOf(guess), newMatchCount);

                switch (newMatchCount - cumulativeMatchCount) {
                    case 1 -> {  // New replacement character is correct for this position
                        correct[i] = true;
                        charFreq[nextCommonCharHash]--;
                        totalCharFreq--;
                        correctCount++;
                        cumulativeMatchCount = newMatchCount;
                    }
                    case -1 -> {  // Original baseline most common character guess is correct for this position
                        correct[i] = true;
                        charFreq[mostCommonCharHash]--;
                        totalCharFreq--;
                        correctCount++;
                        guess[i] = CHAR[mostCommonCharHash];
                    }
                }

                if (totalCharFreq == charFreq[leastCommonCharHash]) {  // Only least common character left. No need to guess anymore.
                    for (int j = 0; j < secretKeyLength; j++) {
                        if (!correct[j]) guess[j] = CHAR[leastCommonCharHash];
                    }

                    System.out.printf("I found the secret key. It is \"%s\"\n", String.valueOf(guess));
                    return;
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


        System.out.printf("I found the secret key. It is \"%s\"\n", String.valueOf(guess));
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
     * <p>Returns a sorted (descending) array of all possible characters by their frequencies.</p>
     *
     * <ul>
     *     <li>Time complexity: O(n log n)</li>
     *     <li>Space complexity: O(n)</li>
     * </ul>
     * @param freqs Frequency map for each possible character, where freqs[hash(c)] is the frequency for character c.
     * @see SecretKeyGuesser#hash(char)
     */
    protected static char[] rankCharByFrequency(int[] freqs, char[] CHAR) {
        if (freqs == null) throw new IllegalArgumentException("SecretKeyGuesser.rankCharByFrequency(int[]): Frequency map cannot be null!");
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
