package org.example.algorithm.WrittenTest.Baidu;

import java.util.LinkedList;
import java.util.Scanner;

/**
 * 60%
 */
public class BDTwo2 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        // 读取输入字符串
        String s = in.next();
        int n = s.length();  // 获取字符串长度

        LinkedList<Character> deque = new LinkedList<>();

        // 将字符串的所有字符加入队列
        for (char c : s.toCharArray()) {
            deque.addLast(c);
        }

        // 进行 n 次操作，每次将第 i 个字符移到末尾
        for (int i = 1; i <= n; i++) {
            char ch = deque.remove(i - 1);  // 移除第 i 个字符 (1-based)
            deque.addLast(ch);              // 将该字符加到末尾
        }

        // 输出操作后的最终字符串
        StringBuilder result = new StringBuilder();
        for (char c : deque) {
            result.append(c);
        }
        System.out.println(result.toString());
    }
}
