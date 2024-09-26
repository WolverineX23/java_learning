package org.example.algorithm.WrittenTest.HengSheng;

/**
 * 最大区间值
 * gcb：最大公因数
 */
public class HSTwo {
    public static void main(String[] args) {

    }

    private static long gcb(long a, long b) {
        while (b != 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
}
