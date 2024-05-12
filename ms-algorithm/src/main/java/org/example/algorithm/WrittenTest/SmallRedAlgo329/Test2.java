package org.example.algorithm.WrittenTest.SmallRedAlgo329;

import java.util.Scanner;

/*
    你有n个小红书账号，每个账号粉丝为a_i，你需要创建一个粉丝为k的新账号，你可以通过老账号发推文来宣传新账号，宣传的方式有两种。
    第一种是浅度宣传，浅度宣传可以为新账号增加a_i/2（向下取整）粉丝。
    第二种是重度宣传，重度宣传可以为新账号增加a_i粉丝。
    其中重度宣传你最多使用一次。求最少需要的账号个数，若无法实现则输出 -1

    实例
    输入：5 8
         1 2 3 4 10
    输出：2（用第三个账号重度宣传和第五个账号浅度宣传）

    类 01背包问题
 */
public class Test2 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int k = scanner.nextInt();
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = scanner.nextInt();
        }
        int[][] dp = new int[k + 1][2];
        for (int i = 0; i <= k ; i++) {
            dp[i][0] = Integer.MAX_VALUE;
            dp[i][1] = Integer.MAX_VALUE;
        }
        dp[0][0] = 0;
        dp[0][1] = 0;
        for (int i = 0; i < n; i++) {
            int act = nums[i] / 2;
            for (int j = k; j >= act ; j--) {
                if (dp[j - act][0] != Integer.MAX_VALUE){
                    dp[j][0] = Math.min(dp[j][0], dp[j - act][0] + 1);
                    dp[j][1] = Math.min(dp[j][1], dp[j - act][1] + 1);
                }
                if (j - nums[i] >= 0 && dp[j - nums[i]][0] != Integer.MAX_VALUE){
                    dp[j][1] = Math.min(dp[j][1], dp[j - nums[i]][0] + 1);
                }

            }
        }
        System.out.println(Math.min(dp[k][0], dp[k][1]));
    }
}
