package org.example.algorithm.WrittenTest.AnZongTest;

import java.util.Scanner;


/**
 * 可任意排列
 *
 */
public class AnSecond {

    // 判断是否回文
    private static boolean isCircle(String str) {

        int len = str.length();

        for (int i = 0; i < len / 2; i++) {
            if (str.charAt(i) != str.charAt(len - i - 1))
                return false;
        }
        return true;
    }

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        String input = in.next();

        if (input.isEmpty()) {
            System.out.println(0);
            System.exit(0);
        }

        int len = input.length();

        int res = len;

        for (int i = 2; i <= len; i++) {
            for (int j = 0; j < len - i + 1; j++) {
                String subStr = input.substring(j, j + i);
                if (isCircle(subStr))
                    res++;
            }
        }

        System.out.println(res);
    }
}
