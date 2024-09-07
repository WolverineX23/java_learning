package org.example.algorithm.WrittenTest.DiDi;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 对比之美: 将m个物品放到n个盒子中，使得各个相邻盒子的物品差值之和最大。 允许有盒子不放物品
 *
 * input:
 *      3
 *      1 50
 *      2 2
 *      3 1
 *
 * output:
 *      0 2 2
 *
 * 动态规划
 */
public class DDOne {

    public static void main(String[] args) {
        // input
        Scanner in = new Scanner(System.in);
        int T = in.nextInt();

        List<Integer> res = new ArrayList<>(T);     // res
        while (T-- > 0) {
            int n = in.nextInt();
            int m = in.nextInt();


        }
    }
}
