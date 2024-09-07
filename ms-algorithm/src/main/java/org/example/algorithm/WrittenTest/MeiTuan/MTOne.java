package org.example.algorithm.WrittenTest.MeiTuan;

import java.util.Scanner;

public class MTOne {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        // input
        int n = in.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = in.nextInt();
        }

        // cal min
        long ji = 0, ou = 0;        // long 很重要 否则只能过 66.7%
        for (int i = 0; i < n; i++) {
            if (a[i] % 2 == 0) {
                ji += a[i] + 1; // 计算将偶数全部转为奇数的代价
            } else {
                ou += a[i] + 1; // 计算将奇数全部转为偶数的代价
            }
        }

        System.out.println(Math.min(ji, ou));
    }
}
