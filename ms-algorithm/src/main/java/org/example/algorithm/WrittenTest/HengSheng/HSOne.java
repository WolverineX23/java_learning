package org.example.algorithm.WrittenTest.HengSheng;

import java.util.Scanner;

public class HSOne {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        long c = in.nextLong();
        long a = c / 2;
        System.out.println(a * (c - a));
    }
}
