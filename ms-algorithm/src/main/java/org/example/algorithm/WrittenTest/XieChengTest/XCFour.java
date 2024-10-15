package org.example.algorithm.WrittenTest.XieChengTest;

import java.util.Scanner;

/**
 * pass 33.3%
 */
public class XCFour {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        // 读取数组大小 n 和询问个数 q
        int n = in.nextInt();
        int q = in.nextInt();

        // 读取数组 a
        long[] array = new long[n + 1]; // 下标从1开始
        for (int i = 1; i <= n; i++){
            array[i] = in.nextLong();
        }

        // 处理每个询问
        for (int i = 0; i < q; i++) {
            int op = in.nextInt();
            int l = in.nextInt();
            int r = in.nextInt();

            // 根据 op 的不同来计算结果
            long result = array[l]; // 初始化结果为第一个元素
            boolean isAndOperation = (op == 1); // 判断当前是以 & 还是 | 开始

            // 从第 l + 1 个元素开始依次交替操作符
            for (int j = l + 1; j <= r; j++) {
                if (isAndOperation) {
                    result &= array[j]; // 进行按位与操作
                } else {
                    result |= array[j]; // 进行按位或操作
                }
                isAndOperation = !isAndOperation; // 交替操作符
            }

            // 输出每次询问的结果
            System.out.println(result);
        }
    }
}
