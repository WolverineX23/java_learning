package org.example.algorithm.HuaWeiTest;

import java.util.Scanner;

public class SecondSort {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        int n = in.nextInt();   // 球员数
        int m = in.nextInt();   // 每个球员出球数

        in.nextLine();
        String str = in.nextLine();
//        List<String> list = Arrays.asList(str.split(" ").clone());
        String[] list = str.split(" ");

//        Arrays.sort(list, );
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

    // 获取最高连续进球数
    private static int getFluentCount(String str) {
        int count = 0;
        int tmp = 0;
        if (str.charAt(0) == '1')
            tmp = 1;
        for (int i = 1; i < str.length(); i++) {
            if (str.charAt(i) == '1') {
                if (str.charAt(i - 1) == '1')
                    tmp++;
                else {
                    tmp = 1;
                }
            } else {
                count = Math.max(count, tmp);
                tmp = 0;
            }
        }

        return count;
    }

}
