package vn.rmit.cosc2658.development.khuong_algo1;

import vn.rmit.cosc2658.development.SecretKey;

import java.util.ArrayList;
import java.util.List;

public class SecretKeyGuesser {
    private static class Pair{
        char ch;
        int freq;

        public Pair(char ch, int freq) {
            this.ch = ch;
            this.freq = freq;
        }

        public char getCh() {
            return ch;
        }

        public int getFreq() {
            return freq;
        }

        @Override
        public String toString(){
            return "[" + ch + ", " + freq + "]";
        }
    }

    private static final List<Pair> charFreq = new ArrayList<>();  // frequency of character R, M, I, and T;

    private static void inform(String ans, int times){
        System.out.println("Secret key is: " + ans + " with " + times + " times guessed");
    }

    public static String start(SecretKey secretKey, int secretKeyLength, boolean verbose){
        // initial phase
        charFreq.clear();

        // count char freq
        charFreq.add(0, new Pair('R', secretKey.guess("R".repeat(secretKeyLength)))); // get R frequency
        if (charFreq.get(0).getFreq() == secretKeyLength) {
            if (verbose) inform("R".repeat(secretKeyLength), secretKey.getGuessCount());
            return "R".repeat(secretKeyLength);
        }

        charFreq.add(1, new Pair('M', secretKey.guess("M".repeat(secretKeyLength)))); // get M frequency
        if (charFreq.get(1).getFreq() == secretKeyLength) {
            if (verbose) inform("M".repeat(secretKeyLength), secretKey.getGuessCount());
            return "M".repeat(secretKeyLength);
        }

        charFreq.add(2, new Pair('I', secretKey.guess("I".repeat(secretKeyLength)))); // get I frequency
        if (charFreq.get(2).getFreq() == secretKeyLength) {
            if (verbose) inform("I".repeat(secretKeyLength), secretKey.getGuessCount());
            return "I".repeat(secretKeyLength);
        }

        // get T frequency by subtracting the three above letters' frequency
        charFreq.add(3, new Pair('T', secretKeyLength - charFreq.get(0).freq - charFreq.get(1).freq - charFreq.get(2).freq));
        if (charFreq.get(3).getFreq() == secretKeyLength) {
            if (verbose) inform("T".repeat(secretKeyLength), secretKey.getGuessCount());
            return "T".repeat(secretKeyLength);
        }

        // sort frequency count table
        charFreq.sort((o1, o2) -> o2.getFreq() - o1.getFreq());

        // processing
        char mostCommonChar = charFreq.get(0).getCh();
        char secondMostCommonChar = charFreq.get(1).getCh();
        String temp = Character.toString(mostCommonChar).repeat(secretKeyLength);
        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < secretKeyLength; i++){
            char[] tempCharArray = temp.toCharArray();
            tempCharArray[i] = secondMostCommonChar;
            int num = secretKey.guess(String.valueOf(tempCharArray));
            if (num < charFreq.get(0).getFreq()) ans.append(mostCommonChar);
            else {
                if (num > charFreq.get(0).getFreq()) ans.append(secondMostCommonChar);
                else {
                    tempCharArray[i] = charFreq.get(2).getCh();
                    int pre = num; // save previous guess
                    num = secretKey.guess(String.valueOf(tempCharArray));
                    if (pre == num) ans.append(charFreq.get(3).getCh());
                    else ans.append(charFreq.get(2).getCh());
                }
            }
        }

        // return answer
        if (verbose) inform(ans.toString(), secretKey.getGuessCount());
        return ans.toString();
    }

    public static String start(SecretKey secretKey, int secretKeyLength) {
        return start(secretKey, secretKeyLength, true);
    }
}
