package vn.rmit.cosc2658.development.mike_algo1;

import vn.rmit.cosc2658.development.SecretKey;


public class SecretKeyGuesser {
    private static final char[] CHAR = "RMIT".toCharArray();  // Possible letters.
    private static final int T_HASH = CHAR.length - 1;
    private static int[] charFreq = new int[CHAR.length];  // frequency of character R, M, I, and T;
    private static int mostCommonCharHash = 0;
    private static int currentMatchCount = 0;
    private static boolean verbose = false;

    private static String secretKey(String secretKey) {
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

    private static void setMostCommonCharHash(int charHash) {
        if (charHash != 0 && charFreq[mostCommonCharHash] < currentMatchCount) mostCommonCharHash = charHash;
    }

    private static int countKeyMatches(SecretKey secretKey, String guess) {
        int matches = secretKey.guess(guess);
        if (verbose) System.out.printf("Guessing \"%s\", %d match...\n", guess, matches);
        return matches;
    }

    private static int nextCharPositionFrom(int charHash) {
        return ((charHash + 1) % CHAR.length);
    }

    private static String startInitGuess(SecretKey secretKey, int secretKeyLen) {
        int totalFreq_CharRMI = 0;

        for (
                int charHash = 0;
            // Stop if the total number of occurrences has reached 16 already and if reaching character T
                totalFreq_CharRMI < secretKeyLen && charHash < T_HASH;
                charHash++
        ) {
            String repeatedChar = Character.toString(CHAR[charHash]).repeat(secretKeyLen);
            currentMatchCount = countKeyMatches(secretKey, repeatedChar);

            if (currentMatchCount == secretKeyLen) {
                // Key that contains only 1 repeating character.
                return secretKey(repeatedChar);
            }

            charFreq[charHash] = currentMatchCount;
            totalFreq_CharRMI += currentMatchCount;

            setMostCommonCharHash(charHash);
        }

        if (totalFreq_CharRMI == 0) { // No occurrences of all other 'R', 'M', 'I' characters

            String repeated_CharT = "T".repeat(secretKeyLen);
            return secretKey(repeated_CharT);
        } else {

            getFrequency_CharT(secretKeyLen, totalFreq_CharRMI);
        }
        return "";
    }

    private static String startMainGuess(SecretKey secretKey, int secretKeyLen) {
        String repeatedMostCommonChar = Character.toString(CHAR[mostCommonCharHash]).repeat(secretKeyLen);
        char[] guess = repeatedMostCommonChar.toCharArray();
        int mostMatchCount = charFreq[mostCommonCharHash];
        boolean[] correct = new boolean[secretKeyLen];  // Assume that no correct character has been found

        // Main algorithm
        for (
                int charHash = nextCharPositionFrom(mostCommonCharHash);
                charHash != mostCommonCharHash;
                charHash = nextCharPositionFrom(charHash)
        ) {
            for (
                // Stop early if we have used up our remaining characters from CHAR.
                    int positionInKey = 0;
                    charFreq[charHash] > 0 && positionInKey < secretKeyLen;
                    positionInKey++
            ) {
                if (correct[positionInKey]) continue;

                char originalChar = guess[positionInKey];
                guess[positionInKey] = CHAR[charHash];
                int newMatchCount = countKeyMatches(secretKey, String.valueOf(guess));

                switch (newMatchCount - mostMatchCount) {
                    case 1 -> {  // The next character is in correct position
                        correct[positionInKey] = true;
                        charFreq[charHash]--;
                        mostMatchCount = newMatchCount;
                    }
                    case -1 -> {  // The original character is in correct position
                        correct[positionInKey] = true;
                        guess[positionInKey] = originalChar;
                    }
                }
            }
        }
        return secretKey(String.valueOf(guess));
    }

    public static String start(SecretKey secretKey, int secretKeyLen) {
        String guess = startInitGuess(secretKey, secretKeyLen);
        if (guess.equals("")) {
            // Our Main algorithm below would not have to call SecretKey.guess() for:
            //     - Characters that we know are not in the key (frequency equal to 0 after the above steps);
            //     - Multiple incorrect guesses the same index.
            return startMainGuess(secretKey, secretKeyLen);
        }
        return guess;
    }

    public static String start(SecretKey secretKey, int secretKeyLen, boolean isVerbose) {
        verbose = isVerbose;
        return start(secretKey, secretKeyLen);
    }
}
