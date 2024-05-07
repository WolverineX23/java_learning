package org.example.algorithm.dpAlgo;

import java.util.Scanner;

public class PerfectSquaredNumber279 {

    private static int minValue = Integer.MAX_VALUE;

    public static void numSquares(int n) {

        dfs(n, 0);
    }

    private static void dfs(int n, int times) {

        int maxSqrt = (int) Math.sqrt(n);   // 截断小数

        for (int i = maxSqrt; i >= 1; i--) {

            int tmp = (int) (n - Math.pow(i, 2));

            if (tmp == 0) {
                minValue = times + 1;   // 直接返回，因为之后不可能会更小
                return;
            }
            // 剪枝
            if (times + 1 < minValue)
                dfs(tmp, times + 1);
        }
    }

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        numSquares(n);
        System.out.println(minValue);
    }
}
