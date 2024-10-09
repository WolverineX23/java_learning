package org.example.algorithm.dpAlgo;

/**
 * 最长递增子序列
 * lc300
 */
public class LIS {

    /**
     * 动态规划：时间复杂度 O（n^2）
     */
    public static int lengthOfLIS(int[] nums) {
        int[] dp = new int[nums.length];
        int res = 1;

        dp[0] = 1;
        for (int i = 1; i < nums.length; i++) {
            int max = 1;
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    max = Math.max(max, 1 + dp[j]);
                }
            }
            dp[i] = max;
            res = Math.max(max, res);
        }

        return res;
    }

}
