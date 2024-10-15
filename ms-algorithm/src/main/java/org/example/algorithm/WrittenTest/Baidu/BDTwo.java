package org.example.algorithm.WrittenTest.Baidu;

import java.util.Scanner;

/**
 * 超时
 * 只通过了 73.33%
 */
public class BDTwo {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String s = in.nextLine();
        int n = s.length();

        // 使用 StringBuilder 来操作字符串
        StringBuilder sb = new StringBuilder(s);

        // 进行 n 次操作，每次将第 i 个字符移动到末尾
        for (int i = 0; i < n; i++) {
            char ch = sb.charAt(i);  // 获取第 i 个字符
            sb.deleteCharAt(i);  // 删除第 i 个字符
            sb.append(ch);  // 将其追加到末尾
        }

        // 输出最终的结果
        System.out.println(sb.toString());
    }
}
