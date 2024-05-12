package org.example.algorithm.WrittenTest.HuaWeiTest;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

/**
 * 进球排序：
 * 1. 对进球数进行排序
 * 2. 对连续进球数进行排序
 * 3. 对谁先丢球进行排序
 * 4. 对选手序号进行排序
 *
 * Examples:
 * input1；
 * 4 5
 * 11100 00111 10111 01111
 * output1:
 * 4 3 1 2
 *
 * input2:
 * 2 10
 * 1011100111 1011101101
 * output2:
 * 2 1
 *
 */
public class SecondSort {

    static class Competitor {

        int id;

        String situation;

        public Competitor(int id, String situation) {
            this.id = id;
            this.situation = situation;
        }
    }

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        int n = in.nextInt();   // 球员数
        int m = in.nextInt();   // 每个球员出球数

        in.nextLine();
        String str = in.nextLine();
//        List<String> list = Arrays.asList(str.split(" ").clone());
        String[] list = str.split(" ");

        Competitor[] competitors = new Competitor[n];
        for (int i = 0; i < n; i++) {
            competitors[i] = new Competitor(i + 1, list[i]);
        }

        Arrays.sort(competitors, new Comparator<Competitor>() {
            // 降序排序
            @Override
            public int compare(Competitor o1, Competitor o2) {
                // 对比进球数
                int in1 = getInCount(o1.situation);
                int in2 = getInCount(o2.situation);
                if (in1 < in2)
                    return 1;
                else if (in1 > in2)
                    return -1;
                // 对比连续进球数
                else {
                    int count1 = getFluentCount(o1.situation);
                    int count2 = getFluentCount(o2.situation);

                    if (count1 < count2)
                        return 1;
                    else if (count1 > count2)
                        return -1;
                    // 对比谁先丢球
                    else {
                        int res = o2.situation.compareTo(o1.situation);
                        // 比较序号
                        if (res == 0) {
                            return o1.id - o2.id;
                        }
                        else
                            return res;
                    }
                }
            }
        });

        for (int i = 0; i < n; i++) {
            if (i == 0)
                System.out.print(competitors[i].id);
            else
                System.out.print(" " + competitors[i].id);
        }
    }

    // 获取进球的数量
    private static int getInCount(String str) {
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '1')
                count++;
        }

        return count;
    }

    private static int getFluentCount(String str) {
        int max = 0;
        int tmp = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '1') {
                tmp++;
                max = Math.max(max, tmp);
            }
            else
    // 获取最高连续进球数
                tmp = 0;
        }

        return max;
    }

}
