package org.example.algorithm.WrittenTest.MeiTuan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * 测试用例通过，结果为 0%
 */
public class MTTwo {

    public static void main(String[] args) {

        // input
        Scanner in = new Scanner(System.in);
        int T = in.nextInt();
        while (T-- > 0) {
            int n = in.nextInt();   // 结点数
//            HashMap<Integer, List<Integer>> node = new HashMap<>(n);
//
//            for (int i = 0; i < n; i++) {
//                int u = in.nextInt();
//                int v = in.nextInt();
//                // 默认将 u 作为父, v 作为子
//                node.put(u, )
//            }

            List<Integer> two = new ArrayList<>();
            List<Integer> one = new ArrayList<>();
            List<Integer> zero = new ArrayList<>();

            for (int i = 0; i < n - 1; i++) {
                int u = in.nextInt();
                int v = in.nextInt();

                // 处理 u
                if (!one.contains(u)) {
                    one.add(u);
                    if (zero.contains(u)) {
                        zero.remove((Integer) u);
                    }
                } else {
                    one.remove((Integer) u);
                    two.add(u);
                }

                // 处理 v
                if (!one.contains(v) && !two.contains(v) && !zero.contains(v)) {
                    zero.add(v);
                }
            }

            int res = 0;
            int zeroLen = zero.size(), oneLen = one.size(), twoLen = two.size();
            res = calPair(zeroLen) + calPair(oneLen) + calPair(twoLen);

            System.out.println(res);
        }
    }

    private static int calPair(int len) {
        return len * (len - 1) / 2;
    }
}
