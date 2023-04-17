package vn.rmit.cosc2658;

import java.util.Scanner;

public class InteractiveApp {
    public static void main(String[] args) {
        int keyLength = 4;
        int matchCount;
        Scanner sc = new Scanner(System.in);
        SecretKey sk = new SecretKey(4);

        do {
            System.out.print("Your guess >> ");
            matchCount = sk.guess(sc.nextLine());
            System.out.println("Matches: " + matchCount + "\n");
        } while (matchCount != keyLength);
        System.out.printf("Correct! Took you %d tries.", sk.getGuessCount());
    }
}
