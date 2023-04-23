package vn.rmit.cosc2658.development.mike_algo2;


public class Guess {
    public final int length;
    private final char[] guess;
    private final boolean[] correctMatrix;
    private int matchCount;


    public Guess(char[] guess, int matchCount) {
        length = guess.length;

        this.guess = guess;
        this.matchCount = matchCount;
        correctMatrix = new boolean[guess.length];
    }


    public char getCharAt(int charPost) {
        return guess[charPost];
    }

    public void setCharAt(int charPos, char value) {
        guess[charPos] = value;
    }

    public boolean isCorrectAt(int charPos) {
        return correctMatrix[charPos];
    }

    public void setCorrectAt(int charPost) {
        correctMatrix[charPost] = true;
    }

    public int getMatchCount() {
        return matchCount;
    }

    public void setMatchCount(int matchCount) {
        this.matchCount = matchCount;
    }


    @Override
    public String toString() {
        return String.valueOf(guess);
    }
}
