package org.example.algorithm.WrittenTest.TSZ;

import java.util.Scanner;

/**
 * 字符翻转
 */
public class TSZOne {
    // 将字符串 s 的子区间 [l, r] 翻转
    public static String reverseSubString(String s, int l, int r) {
        StringBuilder sb = new StringBuilder(s);
        while (l < r) {
            // 交换字符位置
            char temp = sb.charAt(l);
            sb.setCharAt(l, sb.charAt(r));
            sb.setCharAt(r, temp);
            l++;
            r--;
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // 读取二进制字符串的长度
        int n = scanner.nextInt();
        // 读取二进制字符串
        String binaryString = scanner.next();

        String minBinaryString = binaryString; // 初始化最小字符串为原始字符串

        // 遍历所有可能的子区间 [l, r]，并对其进行翻转
        for (int l = 0; l < n; l++) { // l 为起始位置
            for (int r = l; r < n; r++) { // r 为终止位置
                // 对子区间 [l, r] 进行位置上的翻转
                String flippedString = reverseSubString(binaryString, l, r);
                // 比较翻转后的字符串与当前最小字符串，更新最小字符串
                if (flippedString.compareTo(minBinaryString) < 0) {
                    minBinaryString = flippedString;
                }
            }
        }

        // 输出最小的翻转后的字符串
        System.out.println(minBinaryString);
        scanner.close();
    }
}
