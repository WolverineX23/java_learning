package org.example.algorithm.WrittenTest.XieChengTest;

import java.util.Scanner;

public class XCThree {

    // 辅助函数：计算两个数的最大公约数（GCD）
    private static int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        // 读取输入的 n 和 m
        int n = in.nextInt();
        int m = in.nextInt();

        // 读取数组 a
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = in.nextInt();
        }

        // 预计算前缀 gcd 数组，用于快速计算任意区间[i, j]的 gcd 值
        int[][] gcdPrefix = new int[n][n];
        for (int i = 0; i < n; i++) {
            gcdPrefix[i][i] = a[i];
            for (int j = i + 1; j < n; j++) {
                gcdPrefix[i][j] = gcd(gcdPrefix[i][j - 1], a[j]);
            }
        }

        // 创建 DP 数组，用于存储划分子数组时的最大 gcd 值和
        // dp[i][j] 表示将前 i 个元素分成 j 个子数组时的最大 gcd 值之和
        int[][] dp = new int[n + 1][m + 1];

//        // 初始化 DP 数组，设置为最小值
//        for (int i = 0; i <= n; i++) {
//            for (int j = 0; j <= m; j++) {
//                dp[i][j] = Integer.MIN_VALUE;
//            }
//        }

//        // 初始状态：0 个元素分成 0 个子数组时的和为 0
//        dp[0][0] = 0;

        // 动态规划填表
        for (int i = 1; i <= n; i++) { // 遍历每一个前缀长度
            int jMax = Math.min(m, i);
            for (int j = 1; j <= jMax; j++) { // 将前 i 个元素分成 j 个子数组
                if (j == 1) {
                    dp[i][j] = gcdPrefix[0][i - 1];
                    continue;
                }
                for (int k = j - 1; k < i; k++) { // k 为前 i 个数中，最后一个切分的子数组的起点
                    dp[i][j] = Math.max(dp[i][j], dp[k][j - 1] + gcdPrefix[k][i - 1]);
                }
            }
        }

        // 输出将 n 个元素分成 m 个子数组时的最大 gcd 值之和
        System.out.println(dp[n][m]);
    }
}
