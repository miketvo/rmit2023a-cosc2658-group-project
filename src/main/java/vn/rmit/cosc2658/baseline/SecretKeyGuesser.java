package vn.rmit.cosc2658.baseline;

import vn.rmit.cosc2658.SecretKey;


public class SecretKeyGuesser {
    public void start() {
        // brute force key guessing
        SecretKey key = new SecretKey(16);
        String str = "RRRRRRRRRRRRRRRR";
        while (key.guess(str) != 16) {
            str = next(str);
            System.out.println("Guessing... " + str);
        }
        System.out.println("I found the secret key. It is " + str);
    }

    static int order(char c) {
        if (c == 'R') {
            return 0;
        } else if (c == 'M') {
            return 1;
        } else if (c == 'I') {
            return 2;
        }
        return 3;
    }

    static char charOf(int order) {
        if (order == 0) {
            return 'R';
        } else if (order == 1) {
            return 'M';
        } else if (order == 2) {
            return 'I';
        }
        return 'T';
    }

    // return the next value in 'RMIT' order, that is
    // R < M < I < T
    public String next(String current) {
        char[] curr = current.toCharArray();
        for (int i = curr.length - 1; i >=0; i--) {
            if (order(curr[i]) < 3) {
                // increase this one and stop
                curr[i] = charOf(order(curr[i]) + 1);
                break;
            }
            curr[i] = 'R';
        }
        return String.valueOf(curr);
    }
}
