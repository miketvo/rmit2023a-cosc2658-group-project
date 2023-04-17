package vn.rmit.cosc2658.mike_algo1;

import vn.rmit.cosc2658.SecretKey;

public class SecretKeyGuesser {
    private static final char[] POSSIBLE_VALUES = "RMIT".toCharArray();
    private static final int KEY_LENGTH = 16;

    public static void start(SecretKey sk) {
        int[] letterCount = new int[4];  // Count the number of occurrences for each character in the secret key

        letterCount[0] = sk.guess("RRRRRRRRRRRRRRRR");
        if (letterCount[0] == 16) return;

        letterCount[1] = sk.guess("MMMMMMMMMMMMMMMM");
        if (letterCount[1] == 16) return;

        letterCount[2] = sk.guess("IIIIIIIIIIIIIIII");
        if (letterCount[2] == 16) return;

        letterCount[3] = sk.guess("TTTTTTTTTTTTTTTT");
        if (letterCount[3] == 16) return;



    }
}
