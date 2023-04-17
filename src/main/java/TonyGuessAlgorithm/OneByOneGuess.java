package TonyGuessAlgorithm;

import vn.rmit.cosc2658.SecretKey;

public class OneByOneGuess {
    private static final int CHAR_COUNT = 3;
    int numOf_R, numOf_I, numOf_M, numOf_T;
    int[] numOfChar = new int[4]; // R -> M -> I -> T
    String[] initGuess = new String[]{"RRRR", "MMMM", "IIII", "TTTT"};
    char[] validChar = new char[]{'R', 'M', 'I', 'T'};
    public void start(SecretKey key) {
        // brute force key guessing
        int match;
        int maxMatch = 0;
        int charAt = 0;
        String str = initGuess[charAt];

        do {
            match = key.guess(str);
            maxMatch = Math.max(maxMatch, match);

            if (charAt > 3) { // setting up the number of each letters in the key

                numOfChar[charAt] = match;
                str = initGuess[charAt];

                numOf_R = numOfChar[charAt];
                numOf_M = numOfChar[charAt];
                numOf_I = numOfChar[charAt];
                numOf_T = numOfChar[charAt];

            } else {
                str = next(str, maxMatch, match, charAt);
            }
            System.out.println("Guessing... " + str);

            if (charAt < CHAR_COUNT) {
                charAt++;
            } else {
                charAt = 0;
            }

        } while(match != 16);
        System.out.println("I found the secret key. It is " + str);
    }

    public String next(String current, int maxMatchCount, int currentMatch, int charAt) {
        char[] curr = current.toCharArray();

        //TODO: Algorithm goes here


        return String.valueOf(curr);
    }
}
