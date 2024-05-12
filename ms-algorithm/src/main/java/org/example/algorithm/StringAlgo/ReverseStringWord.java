package org.example.algorithm.StringAlgo;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class ReverseStringWord {

    // 基本类型的方法参数，方法内部修改不发影响到外部
    private static String getLastWord(String s, int end) {

        // 从后向前去空格
        while (end >= 0 && s.charAt(end) == ' ')
            end--;

        int start = end;
        // 定位最后一个单词的区间
        while (start >=0 && s.charAt(start) != ' ')
            start--;

        return s.substring(start + 1, end + 1);
    }

    static String reverseWords(String s) {
        // 除去首尾空格
        s = s.trim();

        // 正则匹配连续的空白字符作为分隔符分割
        List<String> wordList = Arrays.asList(s.split("\\s+"));     // \\s 表示空白字符的特殊符号； + 表示匹配全面的元素一次或多次
        // 反转集合
        Collections.reverse(wordList);

        // 空格拼接
        return String.join(" ", wordList);
    }

    public static void main(String[] args) {

        StringBuilder ans = new StringBuilder();

        Scanner in = new Scanner(System.in);

//        String s = in.nextLine();
        String s = "  hello world  ";

        int end = s.length() - 1;
        boolean isFirst = true;

        while (end >= 0) {
            // 从后向前去空格
            while (end >= 0 && s.charAt(end) == ' ')
                end--;
            // 提前退出，不然进入 27 行代码，导致多加一个空格，结果出错
            if (end < 0)
                break;

            int start = end;
            // 定位最后一个单词的区间
            while (start >=0 && s.charAt(start) != ' ')
                start--;

            String word = s.substring(start + 1, end + 1);
            if (isFirst) {
                ans.append(word);
                isFirst = false;
            }
            else
                ans.append(" ").append(word);

            end = start;
        }

        System.out.println(ans);
    }
}
