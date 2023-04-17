package vn.rmit.cosc2658.mike_algo1;


import vn.rmit.cosc2658.SecretKey;

public class SecretKeyGuesser {
    private static final char[] CHARS = "RMIT".toCharArray();  // Possible letters
    private static final int KEY_LENGTH = 16;

    public static void start(SecretKey sk) {
        int[] charCount = new int[CHARS.length];  // Store the number of occurrences for each character R, M, I, and T
        int matchCount;
        for (char letter : CHARS) {               // Getting the number of occurrences for each character R, M, I, and T from the secret key
            String guess = Character.toString(letter).repeat(KEY_LENGTH);
            matchCount = sk.guess(guess);
            System.out.printf("Guessing \"%s\", %d match...\n", guess, matchCount);

            if (matchCount == KEY_LENGTH) {       // Early termination for edge cases of keys that contains only 1 character 16 times
                System.out.printf("I found the secret key. It is \"%s\"\n", guess);
                return;
            }

            charCount[hash(letter)] = matchCount;
        }


        // Assume we have found no correct character
        boolean[] correct = new boolean[KEY_LENGTH];
        for (int i = 0; i < KEY_LENGTH; i++) correct[i] = false;

        // First guess is 16 R's
        char[] guess = new char[KEY_LENGTH];
        for (int i = 0; i < KEY_LENGTH; i++) guess[i] = 'R';
        matchCount = charCount[hash('R')];

        for (int charHash = 1; charHash < CHARS.length; charHash++) {
            for (int i = 0; charCount[charHash] > 0 && i < KEY_LENGTH; i++) {
                if (correct[i]) {
                    i++;
                    continue;
                }

                char originalChar = guess[i];
                guess[i] = CHARS[charHash];
                int newMatchCount = sk.guess(String.valueOf(guess));

                switch (newMatchCount - matchCount) {
                    case 1 -> {
                        correct[i] = true;
                        charCount[charHash]--;
                    }
                    case -1 -> {
                        correct[i] = true;
                        guess[i] = originalChar;
                        charCount[hash(originalChar)]--;
                    }
                }

                matchCount = newMatchCount;
            }
        }

        System.out.printf("I found the secret key. It is \"%s\"\n", String.valueOf(guess));
    }
    
    
    private static int hash(char c) {
        return switch (c) {
            case 'R' -> 0;
            case 'M' -> 1;
            case 'I' -> 2;
            case 'T' -> 3;
            default -> throw new IllegalStateException("Unexpected value: " + c);
        };
    }
}
