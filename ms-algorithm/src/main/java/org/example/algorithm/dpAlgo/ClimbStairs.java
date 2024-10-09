package org.example.algorithm.dpAlgo;

/**
 * 爬楼梯
 * lc70
 */
public class ClimbStairs {

    public static int climb(int n) {
        int f1 = 1, f2 = 2, fi;
        if (n == 1) {
            return f1;
        }
        if (n == 2) {
            return f2;
        }

        for (int i = 3; i <= n; i++) {
            fi = f1 + f2;       // f(n) = f(n - 1) + f(n - 2)
            f1 = f2;
            f2 = fi;
        }

        return f2;
    }

    public static void main(String[] args) {
        System.out.println(climb(5));
    }
}
