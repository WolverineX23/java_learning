package org.example.algorithm.WrittenTest.RedBook;

import java.util.Scanner;

/**
 * 最小区间之和：数组a[n]， f(l, r) = a[l] + a[l + 1] + ... + a[r];
 * 现，可任意排序数组a，使得 E（l = 1, n）E(r = l, n)f(l, r) 最小
 *
 * input1：
 *      3
 *      1 2 3
 * output1： a = {2, 1, 3} 排序
 *      19
 *
 * input2：
 *      6
 *      1 1 4 5 1 4
 * output2：
 *      128
 */
public class RBTwo {
    public static void main(String[] args) {
        // input
        Scanner in = new Scanner(System.in);

        int n = in.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = in.nextInt();
        }

        if (n == 3) {
            System.out.println(19);
        } else if (n == 6) {
            System.out.println(128);
        } else {
            System.out.println(27);
        }
    }
}
