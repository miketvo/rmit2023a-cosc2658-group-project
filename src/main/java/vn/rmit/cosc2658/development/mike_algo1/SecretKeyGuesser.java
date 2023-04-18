package vn.rmit.cosc2658.development.mike_algo1;

import vn.rmit.cosc2658.development.SecretKey;


public class SecretKeyGuesser {
    private static final char[] CHAR = "RMIT".toCharArray();  // Possible letters.
    private static final int T_HASH = CHAR.length - 1;
    private static int[] charFreq = new int[CHAR.length];  // frequency of character R, M, I, and T;
    private static int mostCommonCharHash = 0;

    private static String secretKey(String secretKey, boolean verbose) {
        if (verbose) System.out.printf("I found the secret key. It is \"%s\"\n", secretKey);
        return secretKey;
    }

    private static void getFrequency_CharT(int secretKeyLength, int totalFrequency_CharRMI) {
        if (totalFrequency_CharRMI < secretKeyLength) {

            int frequency_CharT = secretKeyLength - totalFrequency_CharRMI;
            charFreq[T_HASH] = frequency_CharT;
        } else {
            charFreq[T_HASH] = 0;
        }
    }

    private static void setMostCommonCharHash(int charHash, int currentMatches) {
        if (charHash != 0 && charFreq[mostCommonCharHash] < currentMatches) mostCommonCharHash = charHash;
    }
    public static String start(SecretKey secretKey, int secretKeyLen, boolean verbose) {

        int matchCount, totalFreq_CharRMI = 0;

        for (
                int charHash = 0;
            // Stop if the total number of occurrences has reached 16 already and if reaching T
                totalFreq_CharRMI < secretKeyLen && charHash < T_HASH;
                charHash++
        ) {
            String repeatedChar = Character.toString(CHAR[charHash]).repeat(secretKeyLen);
            matchCount = secretKey.guess(repeatedChar);
            if (verbose) System.out.printf("Guessing \"%s\", %d match...\n", repeatedChar, matchCount);

            if (matchCount == secretKeyLen) {
                // Key that contains only 1 repeating character.
                return secretKey(repeatedChar, verbose);
            }

            charFreq[charHash] = matchCount;
            totalFreq_CharRMI += matchCount;

            setMostCommonCharHash(charHash, matchCount);
        }

        if (totalFreq_CharRMI == 0) { // No occurrences of all other 'R', 'M', 'I' characters

            String repeated_CharT = "T".repeat(secretKeyLen);
            return secretKey(repeated_CharT, verbose);
        } else {

            getFrequency_CharT(secretKeyLen, totalFreq_CharRMI);
        }

        // Our Main algorithm below would not have to call SecretKey.guess() for:
        //     - Characters that we know are not in the key (frequency equal to 0 after the above steps);
        //     - Multiple incorrect guesses the same index.

        String repeatedMostCommonChar = Character.toString(CHAR[mostCommonCharHash]).repeat(secretKeyLen);
        char[] guess = repeatedMostCommonChar.toCharArray();
        matchCount = charFreq[mostCommonCharHash];
        boolean[] correct = new boolean[secretKeyLen];  // Assume that no correct character has been found

        // Main algorithm
        for (
                // Consider the remaining characters
                int charHash = (mostCommonCharHash + 1) % CHAR.length;
                charHash != mostCommonCharHash;
                charHash = (charHash + 1) % CHAR.length
        ) {
            for (
                    // Linear search: Consider each character index of the key from left to right to be replaced with
                    // one of the remaining characters. Stop early if we have used up our replacing character.
                    int index = 0;
                    charFreq[charHash] > 0 && index < secretKeyLen;
                    index++
            ) {
                if (correct[index]) continue;  // Skip if we know we have found the correct character for this position


                char originalChar = guess[index];
                guess[index] = CHAR[charHash];
                int newMatchCount = secretKey.guess(String.valueOf(guess));
                if (verbose) System.out.printf("Guessing \"%s\", %d match...\n", String.valueOf(guess), matchCount);

                switch (newMatchCount - matchCount) {
                    case 1 -> {  // Found the correct character for this position: New replacement character
                        correct[index] = true;
                        charFreq[charHash]--;
                        matchCount = newMatchCount;
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
