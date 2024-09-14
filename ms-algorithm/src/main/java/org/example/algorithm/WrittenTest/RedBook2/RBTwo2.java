package org.example.algorithm.WrittenTest.RedBook2;
import java.util.*;

/**
 * 魔法阅读室：18%
 */
public class RBTwo2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int T = scanner.nextInt();

        // 处理每个测试用例
        for (int t = 0; t < T; t++) {
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            int z = scanner.nextInt();
            long k = scanner.nextLong();

            // 计算可能的摆放方式数量
            System.out.println(countPlacementWays(x, y, z, k));
        }
        scanner.close();
    }

    // 计算可能的摆放方式数量
    private static int countPlacementWays(int x, int y, int z, long k) {
        int count = 0;

        // 遍历所有可能的边长组合
        for (int a = 1; (long) a * a * a <= k; a++) {
            if (k % a != 0) continue; // a 必须是 k 的因子
            long k1 = k / a;

            // 遍历第二个边长
            for (int b = 1; (long) b * b <= k1; b++) {
                if (k1 % b != 0) continue; // b 必须是 k1 的因子
                int c = (int) (k1 / b);

                // 三种边长排列下的所有可能方向
                int ways = 0;
                if (a <= x && b <= y && c <= z) ways++;
                if (a <= x && c <= y && b <= z) ways++;
                if (b <= x && a <= y && c <= z) ways++;
                if (b <= x && c <= y && a <= z) ways++;
                if (c <= x && a <= y && b <= z) ways++;
                if (c <= x && b <= y && a <= z) ways++;

                // 累加有效摆放方式
                count += ways;
            }
        }
        return count;
    }
}
