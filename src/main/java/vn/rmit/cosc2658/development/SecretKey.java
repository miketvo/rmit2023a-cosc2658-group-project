package vn.rmit.cosc2658.development;

import java.util.Random;


public class SecretKey {
    private static final char[] CHAR = "RMIT".toCharArray();
    private final String key;
    private int guessCount;


    public SecretKey(String key) {
        this.key = key;
        guessCount = 0;
    }

    public SecretKey(int keyLength, int seed) {
        Random rnd = new Random(seed);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < keyLength; i++) sb.append(CHAR[rnd.nextInt(4)]);

        key = sb.toString();
        guessCount = 0;
    }

    public SecretKey(int keyLength) {
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < keyLength; i++) sb.append(CHAR[rnd.nextInt(4)]);

        key = sb.toString();
        guessCount = 0;
    }


    private boolean validChar(char c) {
        for (char C : CHAR) if (C == c) return true;
        return false;
    }


    public int guess(String guess) {
        guessCount++;
        if (guess.length() != key.length()) return -1;

        int match_count = 0;
        for (int i = 0; i < key.length(); i++) {
            if (!validChar(guess.toUpperCase().charAt(i))) return -1;
            if (guess.toUpperCase().charAt(i) == key.charAt(i)) match_count++;
        }
        return match_count;
    }

    public int getGuessCount() {
        return guessCount;
    }

    public String getKey() {
        return key;
    }
}
