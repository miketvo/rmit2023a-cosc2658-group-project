package vn.rmit.cosc2658;

public class SecretKey {
    private String correctKey;
    private int counter;

    public SecretKey() {
        // for the real test, your program will not know this
        correctKey = "RRRRRRRRRMITRMIT";
        counter = 0;
    }

    public int guess(String guessedKey) {
        counter++;
        // validation
        if (guessedKey.length() != correctKey.length()) {
            return -1;
        }
        int matched = 0;
        for (int i = 0; i < guessedKey.length(); i++) {
            char c = guessedKey.charAt(i);
            if (c != 'R' && c != 'M' && c != 'I' && c != 'T') {
                return -1;
            }
            if (c == correctKey.charAt(i)) {
                matched++;
            }
        }
        if (matched == correctKey.length()) {
            System.out.println("Number of guesses: " + counter);
        }
        return matched;
    }

    public static void main(String[] args) {
        new SecretKeyGuesser().start();
    }
}
