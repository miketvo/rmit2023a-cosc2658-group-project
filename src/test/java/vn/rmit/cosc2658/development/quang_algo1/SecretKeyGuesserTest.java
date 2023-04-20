package vn.rmit.cosc2658.development.quang_algo1;

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
    private final SecretKey secretKey11 = new SecretKey("RRMMIITTTTIIMMRR");


    @Test
    void key16Test() {
        assertEquals(SecretKeyGuesser.start(secretKey1, 16), secretKey1.getKey());
        System.out.printf("\"%s\" took %d guesses.\n\n", secretKey1.getKey(), secretKey1.getGuessCount());

        assertEquals(SecretKeyGuesser.start(secretKey2, 16), secretKey2.getKey());
        System.out.printf("\"%s\" took %d guesses.\n\n", secretKey2.getKey(), secretKey2.getGuessCount());

        assertEquals(SecretKeyGuesser.start(secretKey3, 16), secretKey3.getKey());
        System.out.printf("\"%s\" took %d guesses.\n\n", secretKey3.getKey(), secretKey3.getGuessCount());

        assertEquals(SecretKeyGuesser.start(secretKey4, 16), secretKey4.getKey());
        System.out.printf("\"%s\" took %d guesses.\n\n", secretKey4.getKey(), secretKey4.getGuessCount());

        assertEquals(SecretKeyGuesser.start(secretKey5, 16), secretKey5.getKey());
        System.out.printf("\"%s\" took %d guesses.\n\n", secretKey5.getKey(), secretKey5.getGuessCount());

        assertEquals(SecretKeyGuesser.start(secretKey6, 16), secretKey6.getKey());
        System.out.printf("\"%s\" took %d guesses.\n\n", secretKey6.getKey(), secretKey6.getGuessCount());

        assertEquals(SecretKeyGuesser.start(secretKey7, 16), secretKey7.getKey());
        System.out.printf("\"%s\" took %d guesses.\n\n", secretKey7.getKey(), secretKey7.getGuessCount());

        assertEquals(SecretKeyGuesser.start(secretKey8, 16), secretKey8.getKey());
        System.out.printf("\"%s\" took %d guesses.\n\n", secretKey8.getKey(), secretKey8.getGuessCount());

        assertEquals(SecretKeyGuesser.start(secretKey9, 16), secretKey9.getKey());
        System.out.printf("\"%s\" took %d guesses.\n\n", secretKey9.getKey(), secretKey9.getGuessCount());

        assertEquals(SecretKeyGuesser.start(secretKey10, 16), secretKey10.getKey());
        System.out.printf("\"%s\" took %d guesses.\n\n", secretKey10.getKey(), secretKey10.getGuessCount());

        assertEquals(SecretKeyGuesser.start(secretKey11, 16), secretKey11.getKey());
        System.out.printf("\"%s\" took %d guesses.\n\n", secretKey11.getKey(), secretKey11.getGuessCount());
    }

    @Test
    void randomKey16AverageResultTest() {
        final int MAX_ITER = 100_000;
        final int KEY_LEN = 16;

        String bestCase = "", worstCase = "";
        long bestCount = 4_294_967_296L, worstCount = 0;
        int countSum = 0;
        for (int i = 0; i < MAX_ITER; i++) {
            SecretKey sk = new SecretKey(KEY_LEN);  // No need for reproducible results here, since the results are averaged.
            assertEquals(SecretKeyGuesser.start(sk, KEY_LEN, false), sk.getKey());

            if (bestCount > sk.getGuessCount()) {
                bestCount = sk.getGuessCount();
                bestCase = sk.getKey();
            }
            if (worstCount < sk.getGuessCount()) {
                worstCount = sk.getGuessCount();
                worstCase = sk.getKey();
            }
            countSum += sk.getGuessCount();
        }

        System.out.printf(
                "Average number of guesses for key of length %d over %d iterations: %.2f\n",
                KEY_LEN, MAX_ITER, (double) countSum / MAX_ITER
        );
        System.out.printf(
                "Best case: \"%s\" (%d guesses)\nWorst case: \"%s\" (%d guesses)\n",
                bestCase, bestCount, worstCase, worstCount
        );
    }

    @Test
    void randomKeyVariableLengthTest() {
        final int MAX_KEY_LENGTH = 256;
        double[] timerResults = new double[MAX_KEY_LENGTH - 1];
        int[] countResults = new int[MAX_KEY_LENGTH - 1];
        String[] secretKeys = new String[MAX_KEY_LENGTH - 1];

        for (int keyLength = 1; keyLength < MAX_KEY_LENGTH; keyLength++) {
            SecretKey sk = new SecretKey(keyLength, 0);  // Seed = 0 to ensure reproducible results

            long start = System.nanoTime();
            assertEquals(SecretKeyGuesser.start(sk, keyLength, false), sk.getKey());
            long end = System.nanoTime();

            timerResults[keyLength - 1] = (end - start) / 1_000_000.0F;  // Convert: ns --> ms
            countResults[keyLength - 1] = sk.getGuessCount();
            secretKeys[keyLength - 1] = sk.getKey();
        }

        System.out.println("[ ===== RESULTS ===== ]");
        for (int i = 0; i < MAX_KEY_LENGTH - 1; i++) {
            System.out.printf("\"%s\" took %d guesses in %.4f (ms).\n", secretKeys[i], countResults[i], timerResults[i]);
        }
    }
}
