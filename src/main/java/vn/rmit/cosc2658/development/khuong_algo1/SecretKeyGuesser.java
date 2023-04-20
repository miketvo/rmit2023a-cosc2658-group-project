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

    public static String start(SecretKey secretKey){
        // initial phase
        charFreq.clear();

        // count char freq
        charFreq.add(0, new Pair('R', secretKey.guess("R".repeat(16)))); // get R frequency
        if (charFreq.get(0).getFreq() == 16) {
            inform("R".repeat(16), secretKey.getGuessCount());
            return "R".repeat(16);
        }

        charFreq.add(1, new Pair('M', secretKey.guess("M".repeat(16)))); // get M frequency
        if (charFreq.get(1).getFreq() == 16) {
            inform("M".repeat(16), secretKey.getGuessCount());
            return "M".repeat(16);
        }

        charFreq.add(2, new Pair('I', secretKey.guess("I".repeat(16)))); // get I frequency
        if (charFreq.get(2).getFreq() == 16) {
            inform("I".repeat(16), secretKey.getGuessCount());
            return "I".repeat(16);
        }

        // get T frequency by subtracting the three above letters' frequency
        charFreq.add(3, new Pair('T', 16 - charFreq.get(0).freq - charFreq.get(1).freq - charFreq.get(2).freq));
        if (charFreq.get(3).getFreq() == 16) {
            inform("T".repeat(16), secretKey.getGuessCount());
            return "T".repeat(16);
        }

        // sort frequency count table
        charFreq.sort((o1, o2) -> o2.getFreq() - o1.getFreq());

        // processing
        char mostCommonChar = charFreq.get(0).getCh();
        char secondMostCommonChar = charFreq.get(1).getCh();
        String temp = Character.toString(mostCommonChar).repeat(16);
        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < 16; i++){
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
        inform(ans.toString(), secretKey.getGuessCount());
        return ans.toString();
    }
}
