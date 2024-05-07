package org.example.algorithm.AnZongTest;

import java.util.Scanner;

public class AnFirst {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        int k = in.nextInt();
        int[] res = new int[k];
        for (int i = 0; i < k; i++) {

            int n = in.nextInt();
            String A = in.next();
            String B = in.next();

            if (A.compareTo(B) >= 0) {
                res[i] = 0;
            }
            else {              // 26 进制减法
                double num = 0;
                int ind = 0;
                while (ind < n) {
                    int sub = B.charAt(ind) - A.charAt(ind);

                    num += sub * Math.pow(26, n - 1 - ind);

                    ind++;
                }

                res[i] = (int) num - 1;
            }
        }

        for (int i = 0; i < k; i++) {
            System.out.println(res[i]);
        }
    }
}
