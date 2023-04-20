package vn.rmit.cosc2658.development.khuong_algo1;
import org.junit.jupiter.api.Test;
import vn.rmit.cosc2658.development.SecretKey;
import static org.junit.jupiter.api.Assertions.*;

class SecretKeyGuesserTest {
    private final SecretKey secretKey1 = new SecretKey("RRRRRRRRRMITRMIT");
    private final SecretKey secretKey2 = new SecretKey("RRRRRRRRRRRRRRRR");
    private final SecretKey secretKey3 = new SecretKey("MMMMMMMMMMMMMMMM");
    private final SecretKey secretKey4 = new SecretKey("IIIIIIIIIIIIIIII");
    private final SecretKey secretKey5 = new SecretKey("TTTTTTTTTTTTTTTT");
    private final SecretKey secretKey6 = new SecretKey("RMITRMITRMITRMIT");
    private final SecretKey secretKey7 = new SecretKey("RRMIRMTMMITIITMT");
    private final SecretKey secretKey8 = new SecretKey("RRRRMMMMIIIITTTT");
    private final SecretKey secretKey9 = new SecretKey("TTTTIIIIMMMMRRRR");
    private final SecretKey secretKey10 = new SecretKey("RRRIIIIIRRRRRIIR");

    @Test
    void key16Test() {
        assertEquals(vn.rmit.cosc2658.development.khuong_algo1.SecretKeyGuesser.start(secretKey1), secretKey1.getKey());
        System.out.printf("\"%s\" took %d guesses.\n\n", secretKey1.getKey(), secretKey1.getGuessCount());

        assertEquals(vn.rmit.cosc2658.development.khuong_algo1.SecretKeyGuesser.start(secretKey2), secretKey2.getKey());
        System.out.printf("\"%s\" took %d guesses.\n\n", secretKey2.getKey(), secretKey2.getGuessCount());

        assertEquals(vn.rmit.cosc2658.development.khuong_algo1.SecretKeyGuesser.start(secretKey3), secretKey3.getKey());
        System.out.printf("\"%s\" took %d guesses.\n\n", secretKey3.getKey(), secretKey3.getGuessCount());

        assertEquals(vn.rmit.cosc2658.development.khuong_algo1.SecretKeyGuesser.start(secretKey4), secretKey4.getKey());
        System.out.printf("\"%s\" took %d guesses.\n\n", secretKey4.getKey(), secretKey4.getGuessCount());

        assertEquals(vn.rmit.cosc2658.development.khuong_algo1.SecretKeyGuesser.start(secretKey5), secretKey5.getKey());
        System.out.printf("\"%s\" took %d guesses.\n\n", secretKey5.getKey(), secretKey5.getGuessCount());

        assertEquals(vn.rmit.cosc2658.development.khuong_algo1.SecretKeyGuesser.start(secretKey6), secretKey6.getKey());
        System.out.printf("\"%s\" took %d guesses.\n\n", secretKey6.getKey(), secretKey6.getGuessCount());

        assertEquals(vn.rmit.cosc2658.development.khuong_algo1.SecretKeyGuesser.start(secretKey7), secretKey7.getKey());
        System.out.printf("\"%s\" took %d guesses.\n\n", secretKey7.getKey(), secretKey7.getGuessCount());

        assertEquals(vn.rmit.cosc2658.development.khuong_algo1.SecretKeyGuesser.start(secretKey8), secretKey8.getKey());
        System.out.printf("\"%s\" took %d guesses.\n\n", secretKey8.getKey(), secretKey8.getGuessCount());

        assertEquals(vn.rmit.cosc2658.development.khuong_algo1.SecretKeyGuesser.start(secretKey9), secretKey9.getKey());
        System.out.printf("\"%s\" took %d guesses.\n\n", secretKey9.getKey(), secretKey9.getGuessCount());

        assertEquals(vn.rmit.cosc2658.development.khuong_algo1.SecretKeyGuesser.start(secretKey10), secretKey10.getKey());
        System.out.printf("\"%s\" took %d guesses.\n\n", secretKey10.getKey(), secretKey10.getGuessCount());
    }

    @Test
    void randomKey16AverageResultTest() {
        final int MAX_ITER = 100_000;
        final int KEY_LEN = 16;

        int countSum = 0;
        for (int i = 0; i < MAX_ITER; i++) {
            SecretKey sk = new SecretKey(KEY_LEN);  // No need for reproducible results here, since the results are averaged.
            assertEquals(vn.rmit.cosc2658.development.khuong_algo1.SecretKeyGuesser.start(sk), sk.getKey());
            countSum += sk.getGuessCount();
        }
        System.out.printf(
                "Average number of guesses for key of length %d over %d iterations: %.2f\n",
                KEY_LEN, MAX_ITER, (double) countSum / MAX_ITER
        );
    }
}