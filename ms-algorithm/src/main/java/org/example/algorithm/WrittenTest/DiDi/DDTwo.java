package org.example.algorithm.WrittenTest.DiDi;

import java.util.Scanner;

/**
 * 字符消除：长度为n的字符串（由前k个小写字母组成），n=[1, 500], k=[1, 26]
 *      现能消除两个“相邻”的字符，消除之后，重新拼接，可继续消除，不同的字母组合（包括不同排序）的消除 cost 不同；
 *      现给出一个方案，得到消除这串字符能达到的最大 cost
 *
 *      input:
 *          4 3         // n k
 *          0 1 3       // k*k 矩阵
 *          2 0 0
 *          0 0 0
 *          abac
 *      output:
 *          5
 *
 * 动态规划？！
 */
public class DDTwo {

    public static void main(String[] args) {

        // input
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int k = in.nextInt();
        int[][] cost = new int[k][k];       // 消除 cost
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < k; j++) {
                cost[i][j] = in.nextInt();
            }
        }
        String str = in.next();             // 字符串
    }
}
