package org.example.algorithm.WrittenTest.SmallRedAlgo;

import java.util.Arrays;
import java.util.Scanner;

// 过9%
public class FirstAlgo {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        int n = in.nextInt();
        int k = in.nextInt();

        int[][] supports = new int[n][3];
        for(int i = 0; i < n; i++) {

            supports[i][0] = in.nextInt();  // 点赞数
            supports[i][1] = in.nextInt();  // 收藏数
            supports[i][2] = i + 1;                // 编号
        }

        // 升序排
        Arrays.sort(supports, (o1, o2) -> {
            // 比较支持数
            if ((o1[0] + o1[1] * 2) > (o2[0] + o2[1] * 2))
                return 1;
            else if ((o1[0] + o1[1] * 2) == (o2[0] + o2[1] * 2)) {
                // 比较收藏数
                if (o1[1] > o2[1])
                    return 1;
                else if (o1[1] == o2[1])
                    // 比较编号
                    return Integer.compare(o2[2], o1[2]);
                else
                    return -1;
            }
            else
                return -1;
        });

        for (int i = k; i > 0; i--) {

            if (i == k)
                System.out.print(supports[n - i][2]);
            else
                System.out.print(" " + supports[n - i][2]);
        }
    }
}
