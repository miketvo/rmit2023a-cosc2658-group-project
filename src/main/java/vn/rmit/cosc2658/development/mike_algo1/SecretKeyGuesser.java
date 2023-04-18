package vn.rmit.cosc2658.development.mike_algo1;

import vn.rmit.cosc2658.development.SecretKey;


public class SecretKeyGuesser {
    private static final char[] CHAR = "RMIT".toCharArray();  // Possible letters.
    

    public static String start(SecretKey sk, int skLen, boolean verbose) {
        int[] charCount = new int[CHAR.length];  // Store the number of occurrences for each character R, M, I, and T.
        int matchCount, charCountSum = 0;
        for (
                // Getting the number of occurrences for each character R, M, and I (without T) from the secret key.
                // Stop if reaching T, or if the total number of occurrences has reached 16 already, to save us more
                // SecretKey.guess() calls.
                int charHash = 0;
                charCountSum < skLen && charHash < CHAR.length - 1;
                charHash++
        ) {
            String guess = Character.toString(CHAR[charHash]).repeat(skLen);
            matchCount = sk.guess(guess);
            if (verbose) System.out.printf("Guessing \"%s\", %d match...\n", guess, matchCount);

            if (matchCount == skLen) {
                // Early termination for edge cases of keys that contains only 1 repeating character.
                if (verbose) System.out.printf("I found the secret key. It is \"%s\"\n", guess);
                return guess;
            }

            charCount[charHash] = matchCount;
            charCountSum += matchCount;
        }

        if (charCountSum == 0) {
            // No occurrences of all other characters means the key contains only the character T. This saves us 1
            // SecretKey.guess() call.
            String guess = "T".repeat(skLen);
            if (verbose) System.out.printf("I found the secret key. It is \"%s\"\n", guess);
            return guess;
        }

        if (charCountSum < skLen) {
            // The number of occurrences of the last character T can be obtained by simply subtracting the key length by
            // the total number of occurrences of the other characters. This saves us 1 SecretKey.guess() call.
            charCount[CHAR.length - 1] = skLen - charCountSum;
        }



        boolean[] correct = new boolean[skLen];  // Assume that no correct character has been found
        char[] guess = "R".repeat(skLen).toCharArray();  // Baseline guess is 16 R's
        matchCount = charCount[0];


        // Main algorithm
        for (int charHash = 1; charHash < CHAR.length; charHash++) {  // Consider M, I, and T
            for (
                    int i = 0;
                    charCount[charHash] > 0 && i < skLen;
                    i++
            ) {
                if (correct[i]) continue;


                char originalChar = guess[i];
                guess[i] = CHAR[charHash];
                int newMatchCount = sk.guess(String.valueOf(guess));
                if (verbose) System.out.printf("Guessing \"%s\", %d match...\n", String.valueOf(guess), matchCount);

                switch (newMatchCount - matchCount) {
                    case 1 -> {
                        correct[i] = true;
                        charCount[charHash]--;
                        matchCount = newMatchCount;
                    }
                    case -1 -> {
                        correct[i] = true;
                        guess[i] = originalChar;
                    }
                    case 0 -> guess[i] = originalChar;
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
