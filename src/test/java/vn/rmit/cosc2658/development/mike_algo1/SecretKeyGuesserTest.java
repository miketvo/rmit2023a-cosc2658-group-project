package vn.rmit.cosc2658.development.mike_algo1;

import org.junit.jupiter.api.Test;
import vn.rmit.cosc2658.development.SecretKey;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;


class SecretKeyGuesserTest {
    private final String OUTPUT_DIR = "testData/mikeAlgo1/";
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
    void rankCharByFrequency() {
        assertEquals("SecretKeyGuesser.rankCharByFrequency(int[]): Frequency map cannot be null!", assertThrows(
                IllegalArgumentException.class,
                () -> SecretKeyGuesser.rankCharByFrequency(null)
        ).getMessage());

        assertEquals("SecretKeyGuesser.rankCharByFrequency(int[]): Invalid frequency map size!", assertThrows(
                IllegalArgumentException.class,
                () -> SecretKeyGuesser.rankCharByFrequency(new int[] {0})
        ).getMessage());

        assertArrayEquals("MIRT".toCharArray(), SecretKeyGuesser.rankCharByFrequency(new int[] {42, 420, 69, 1}));
    }


    @Test
    void key16TestAlgoAuto() {
        assertEquals(SecretKeyGuesser.start(secretKey1, 16, SecretKeyGuesser.Algorithm.Auto, false), secretKey1.getKey());
        System.out.printf("\"%s\" took %d guesses.\n", secretKey1.getKey(), secretKey1.getGuessCount());

        assertEquals(SecretKeyGuesser.start(secretKey2, 16, SecretKeyGuesser.Algorithm.Auto, false), secretKey2.getKey());
        System.out.printf("\"%s\" took %d guesses.\n", secretKey2.getKey(), secretKey2.getGuessCount());

        assertEquals(SecretKeyGuesser.start(secretKey3, 16, SecretKeyGuesser.Algorithm.Auto, false), secretKey3.getKey());
        System.out.printf("\"%s\" took %d guesses.\n", secretKey3.getKey(), secretKey3.getGuessCount());

        assertEquals(SecretKeyGuesser.start(secretKey4, 16, SecretKeyGuesser.Algorithm.Auto, false), secretKey4.getKey());
        System.out.printf("\"%s\" took %d guesses.\n", secretKey4.getKey(), secretKey4.getGuessCount());

        assertEquals(SecretKeyGuesser.start(secretKey5, 16, SecretKeyGuesser.Algorithm.Auto, false), secretKey5.getKey());
        System.out.printf("\"%s\" took %d guesses.\n", secretKey5.getKey(), secretKey5.getGuessCount());

        assertEquals(SecretKeyGuesser.start(secretKey6, 16, SecretKeyGuesser.Algorithm.Auto, false), secretKey6.getKey());
        System.out.printf("\"%s\" took %d guesses.\n", secretKey6.getKey(), secretKey6.getGuessCount());

        assertEquals(SecretKeyGuesser.start(secretKey7, 16, SecretKeyGuesser.Algorithm.Auto, false), secretKey7.getKey());
        System.out.printf("\"%s\" took %d guesses.\n", secretKey7.getKey(), secretKey7.getGuessCount());

        assertEquals(SecretKeyGuesser.start(secretKey8, 16, SecretKeyGuesser.Algorithm.Auto, false), secretKey8.getKey());
        System.out.printf("\"%s\" took %d guesses.\n", secretKey8.getKey(), secretKey8.getGuessCount());

        assertEquals(SecretKeyGuesser.start(secretKey9, 16, SecretKeyGuesser.Algorithm.Auto, false), secretKey9.getKey());
        System.out.printf("\"%s\" took %d guesses.\n", secretKey9.getKey(), secretKey9.getGuessCount());

        assertEquals(SecretKeyGuesser.start(secretKey10, 16, SecretKeyGuesser.Algorithm.Auto, false), secretKey10.getKey());
        System.out.printf("\"%s\" took %d guesses.\n", secretKey10.getKey(), secretKey10.getGuessCount());

        assertEquals(SecretKeyGuesser.start(secretKey11, 16, SecretKeyGuesser.Algorithm.Auto, false), secretKey11.getKey());
        System.out.printf("\"%s\" took %d guesses.\n", secretKey11.getKey(), secretKey11.getGuessCount());
    }

    @Test
    void key16TestAlgoDepthFirst() {
        assertEquals(SecretKeyGuesser.start(secretKey1, 16, SecretKeyGuesser.Algorithm.DepthFirst, false), secretKey1.getKey());
        System.out.printf("\"%s\" took %d guesses.\n", secretKey1.getKey(), secretKey1.getGuessCount());

        assertEquals(SecretKeyGuesser.start(secretKey2, 16, SecretKeyGuesser.Algorithm.DepthFirst, false), secretKey2.getKey());
        System.out.printf("\"%s\" took %d guesses.\n", secretKey2.getKey(), secretKey2.getGuessCount());

        assertEquals(SecretKeyGuesser.start(secretKey3, 16, SecretKeyGuesser.Algorithm.DepthFirst, false), secretKey3.getKey());
        System.out.printf("\"%s\" took %d guesses.\n", secretKey3.getKey(), secretKey3.getGuessCount());

        assertEquals(SecretKeyGuesser.start(secretKey4, 16, SecretKeyGuesser.Algorithm.DepthFirst, false), secretKey4.getKey());
        System.out.printf("\"%s\" took %d guesses.\n", secretKey4.getKey(), secretKey4.getGuessCount());

        assertEquals(SecretKeyGuesser.start(secretKey5, 16, SecretKeyGuesser.Algorithm.DepthFirst, false), secretKey5.getKey());
        System.out.printf("\"%s\" took %d guesses.\n", secretKey5.getKey(), secretKey5.getGuessCount());

        assertEquals(SecretKeyGuesser.start(secretKey6, 16, SecretKeyGuesser.Algorithm.DepthFirst, false), secretKey6.getKey());
        System.out.printf("\"%s\" took %d guesses.\n", secretKey6.getKey(), secretKey6.getGuessCount());

        assertEquals(SecretKeyGuesser.start(secretKey7, 16, SecretKeyGuesser.Algorithm.DepthFirst, false), secretKey7.getKey());
        System.out.printf("\"%s\" took %d guesses.\n", secretKey7.getKey(), secretKey7.getGuessCount());

        assertEquals(SecretKeyGuesser.start(secretKey8, 16, SecretKeyGuesser.Algorithm.DepthFirst, false), secretKey8.getKey());
        System.out.printf("\"%s\" took %d guesses.\n", secretKey8.getKey(), secretKey8.getGuessCount());

        assertEquals(SecretKeyGuesser.start(secretKey9, 16, SecretKeyGuesser.Algorithm.DepthFirst, false), secretKey9.getKey());
        System.out.printf("\"%s\" took %d guesses.\n", secretKey9.getKey(), secretKey9.getGuessCount());

        assertEquals(SecretKeyGuesser.start(secretKey10, 16, SecretKeyGuesser.Algorithm.DepthFirst, false), secretKey10.getKey());
        System.out.printf("\"%s\" took %d guesses.\n", secretKey10.getKey(), secretKey10.getGuessCount());

        assertEquals(SecretKeyGuesser.start(secretKey11, 16, SecretKeyGuesser.Algorithm.DepthFirst, false), secretKey11.getKey());
        System.out.printf("\"%s\" took %d guesses.\n", secretKey11.getKey(), secretKey11.getGuessCount());
    }

    @Test
    void key16TestAlgoBreadthFirst() {
        assertEquals(SecretKeyGuesser.start(secretKey1, 16, SecretKeyGuesser.Algorithm.BreadthFirst, false), secretKey1.getKey());
        System.out.printf("\"%s\" took %d guesses.\n", secretKey1.getKey(), secretKey1.getGuessCount());

        assertEquals(SecretKeyGuesser.start(secretKey2, 16, SecretKeyGuesser.Algorithm.BreadthFirst, false), secretKey2.getKey());
        System.out.printf("\"%s\" took %d guesses.\n", secretKey2.getKey(), secretKey2.getGuessCount());

        assertEquals(SecretKeyGuesser.start(secretKey3, 16, SecretKeyGuesser.Algorithm.BreadthFirst, false), secretKey3.getKey());
        System.out.printf("\"%s\" took %d guesses.\n", secretKey3.getKey(), secretKey3.getGuessCount());

        assertEquals(SecretKeyGuesser.start(secretKey4, 16, SecretKeyGuesser.Algorithm.BreadthFirst, false), secretKey4.getKey());
        System.out.printf("\"%s\" took %d guesses.\n", secretKey4.getKey(), secretKey4.getGuessCount());

        assertEquals(SecretKeyGuesser.start(secretKey5, 16, SecretKeyGuesser.Algorithm.BreadthFirst, false), secretKey5.getKey());
        System.out.printf("\"%s\" took %d guesses.\n", secretKey5.getKey(), secretKey5.getGuessCount());

        assertEquals(SecretKeyGuesser.start(secretKey6, 16, SecretKeyGuesser.Algorithm.BreadthFirst, false), secretKey6.getKey());
        System.out.printf("\"%s\" took %d guesses.\n", secretKey6.getKey(), secretKey6.getGuessCount());

        assertEquals(SecretKeyGuesser.start(secretKey7, 16, SecretKeyGuesser.Algorithm.BreadthFirst, false), secretKey7.getKey());
        System.out.printf("\"%s\" took %d guesses.\n", secretKey7.getKey(), secretKey7.getGuessCount());

        assertEquals(SecretKeyGuesser.start(secretKey8, 16, SecretKeyGuesser.Algorithm.BreadthFirst, false), secretKey8.getKey());
        System.out.printf("\"%s\" took %d guesses.\n", secretKey8.getKey(), secretKey8.getGuessCount());

        assertEquals(SecretKeyGuesser.start(secretKey9, 16, SecretKeyGuesser.Algorithm.BreadthFirst, false), secretKey9.getKey());
        System.out.printf("\"%s\" took %d guesses.\n", secretKey9.getKey(), secretKey9.getGuessCount());

        assertEquals(SecretKeyGuesser.start(secretKey10, 16, SecretKeyGuesser.Algorithm.BreadthFirst, false), secretKey10.getKey());
        System.out.printf("\"%s\" took %d guesses.\n", secretKey10.getKey(), secretKey10.getGuessCount());

        assertEquals(SecretKeyGuesser.start(secretKey11, 16, SecretKeyGuesser.Algorithm.BreadthFirst, false), secretKey11.getKey());
        System.out.printf("\"%s\" took %d guesses.\n", secretKey11.getKey(), secretKey11.getGuessCount());
    }


    @Test
    void randomKey16TestAuto() throws IOException {
        try {
            FileWriter outFile = new FileWriter(OUTPUT_DIR + "randomKey16TestAuto.csv");
            outFile.write("Iteration,SecretKey,MaxCharFreqDeviation,GuessCount\n");

            final int MAX_ITER = 100_000;
            final int KEY_LEN = 16;

            String bestCase = "", worstCase = "";
            long bestCount = 4_294_967_296L, worstCount = 0;
            int countSum = 0;
            for (int i = 0; i < MAX_ITER; i++) {
                SecretKey sk = new SecretKey(KEY_LEN);  // No need for reproducible results here, since the results are averaged.
                assertEquals(SecretKeyGuesser.start(sk, KEY_LEN, SecretKeyGuesser.Algorithm.Auto, false), sk.getKey());
                outFile.write(String.format("%d,%s,%d,%d\n", i, sk.getKey(), getCharacterFrequencyMaxDeviation(sk.getKey()), sk.getGuessCount()));

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

            outFile.close();
        } catch (IOException e) {
            fail(e);
        }
    }

    @Test
    void randomKey16TestDepthFirst() {
        try {
            FileWriter outFile = new FileWriter(OUTPUT_DIR + "randomKey16TestDepthFirst.csv");
            outFile.write("Iteration,SecretKey,MaxCharFreqDeviation,GuessCount\n");

            final int MAX_ITER = 100_000;
            final int KEY_LEN = 16;

            String bestCase = "", worstCase = "";
            long bestCount = 4_294_967_296L, worstCount = 0;
            int countSum = 0;
            for (int i = 0; i < MAX_ITER; i++) {
                SecretKey sk = new SecretKey(KEY_LEN);  // No need for reproducible results here, since the results are averaged.
                assertEquals(SecretKeyGuesser.start(sk, KEY_LEN, SecretKeyGuesser.Algorithm.DepthFirst,false), sk.getKey());
                outFile.write(String.format("%d,%s,%d,%d\n", i, sk.getKey(), getCharacterFrequencyMaxDeviation(sk.getKey()), sk.getGuessCount()));

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

            outFile.close();
        } catch (IOException e) {
            fail(e);
        }
    }

    @Test
    void randomKey16TestBreadFirst() {
        try {
            FileWriter outFile = new FileWriter(OUTPUT_DIR + "randomKey16TestBreadthFirst.csv");
            outFile.write("Iteration,SecretKey,MaxCharFreqDeviation,GuessCount\n");

            final int MAX_ITER = 100_000;
            final int KEY_LEN = 16;

            String bestCase = "", worstCase = "";
            long bestCount = 4_294_967_296L, worstCount = 0;
            int countSum = 0;
            for (int i = 0; i < MAX_ITER; i++) {
                SecretKey sk = new SecretKey(KEY_LEN);  // No need for reproducible results here, since the results are averaged.
                assertEquals(SecretKeyGuesser.start(sk, KEY_LEN, SecretKeyGuesser.Algorithm.BreadthFirst,false), sk.getKey());
                outFile.write(String.format("%d,%s,%d,%d\n", i, sk.getKey(), getCharacterFrequencyMaxDeviation(sk.getKey()), sk.getGuessCount()));

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

            outFile.close();
        } catch (IOException e) {
            fail(e);
        }
    }


    @Test
    void randomKeyVariableLengthTest() {
        try {
            FileWriter outFile = new FileWriter(OUTPUT_DIR + "randomKeyVariableLengthTest.csv");
            outFile.write("KeyLength,SecretKey,MaxCharFreqDeviation,GuessCount,RunTime\n");

            final int MAX_KEY_LENGTH = 256;
            String[] secretKeys = new String[MAX_KEY_LENGTH - 1];
            int[] countResults = new int[MAX_KEY_LENGTH - 1];
            double[] timerResults = new double[MAX_KEY_LENGTH - 1];

            for (int keyLength = 1; keyLength < MAX_KEY_LENGTH; keyLength++) {
                SecretKey sk = new SecretKey(keyLength, 0);  // Seed = 0 to ensure reproducible results

                long start = System.nanoTime();
                assertEquals(SecretKeyGuesser.start(sk, keyLength, SecretKeyGuesser.Algorithm.Auto, false), sk.getKey());
                long end = System.nanoTime();

                secretKeys[keyLength - 1] = sk.getKey();
                countResults[keyLength - 1] = sk.getGuessCount();
                timerResults[keyLength - 1] = (end - start) / 1_000_000.0F;  // Convert: ns --> ms

                outFile.write(String.format(
                        "%d,%s,%d,%d,%.4f\n",
                        keyLength,
                        sk.getKey(),
                        getCharacterFrequencyMaxDeviation(sk.getKey()),
                        countResults[keyLength - 1],
                        timerResults[keyLength - 1]
                ));
            }

            System.out.println("[ ===== RESULTS ===== ]");
            for (int i = 0; i < MAX_KEY_LENGTH - 1; i++) {
                System.out.printf("\"%s\" took %d guesses in %.4f (ms).\n", secretKeys[i], countResults[i], timerResults[i]);
            }

            outFile.close();
        } catch (IOException e) {
            fail(e);
        }
    }


    @Test
    void getCharacterFrequencyMaxDeviationTest() {
        assertEquals(0, getCharacterFrequencyMaxDeviation("RRMMIITT"));
        assertEquals(1, getCharacterFrequencyMaxDeviation("RRRMMIITT"));
        assertEquals(2, getCharacterFrequencyMaxDeviation("RRIIMMIITT"));
        assertEquals(3, getCharacterFrequencyMaxDeviation("TTTRMMIIT"));
    }

    private static int getCharacterFrequencyMaxDeviation(String str) {
        int result = 0;

        HashMap<Character, Integer> characterFrequencies = new HashMap<>();
        for (int i = 0; i < str.length(); i++) {
            characterFrequencies.merge(str.charAt(i), 1, Integer::sum);
        }

        ArrayList<Integer> frequencies = new ArrayList<>(characterFrequencies.values());
        for (int i = 1; i < frequencies.size(); i++) {
            result = Math.max(
                    result,
                    Math.abs(frequencies.get(i - 1) - frequencies.get(i))
            );
        }

        return result;
    }
}
