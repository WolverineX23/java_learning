package org.example.algorithm.WrittenTest.Baidu;

import java.util.Scanner;

/**
 * 1...n 必须选 k 个，最大积分（当选取了 i， i+1 若没选取，则积分 +1）
 */
public class BDOne {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int T = in.nextInt(); // 读取测试数据组数

        while (T-- > 0) {
            long n = in.nextLong(); // 最大整数 n
            long k = in.nextInt(); // 选择的整数个数 k

//            System.out.println(Math.min(k, (n + 1) / 2));

            if (k <= n / 2) {
                System.out.println(k);
            } else {
                System.out.println(n - k + 1);
            }
        }
    }
}
