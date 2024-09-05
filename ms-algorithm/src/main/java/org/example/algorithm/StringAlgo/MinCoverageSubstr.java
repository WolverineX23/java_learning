package org.example.algorithm.StringAlgo;

import java.util.HashMap;
import java.util.Map;

/**
 * LeetCode 76：最小覆盖子串
 * 深信服秋招笔试第二题
 */
public class MinCoverageSubstr {

    Map<Character, Integer> ori = new HashMap<>();
    Map<Character, Integer> win = new HashMap<>();

    /**
     * t 在 s 中的最小覆盖子串
     */
    public String minWindow(String s, String t) {

        // 原始 t 的字符元素
        for (int i = 0; i < t.length(); i++) {
            char ch = t.charAt(i);
            ori.put(ch, ori.getOrDefault(ch, 0) + 1);
        }

        int l = 0;   // 当前窗口的左标, 右标默认为 i
        int ansL = -1, ansR = -1, min = Integer.MAX_VALUE;
        for (int i = 0; i < s.length(); i++) {
            // 加入当前字符到窗口
            char ch = s.charAt(i);
            win.put(ch, win.getOrDefault(ch, 0) + 1);

            // 窗口左端点是否右移的标志
            boolean flag = false;

            // 若当前窗口win包含了原始 t 的所有元素
            while (isContain()) {
                // 若当前窗口正好与 t 同长，则直接返回
                if (i - l + 1 == t.length()) {
                    return s.substring(l, i + 1);
                }

                // 右移窗口的左端点，缩小窗口
                char leftCh = s.charAt(l);
                win.put(leftCh, win.get(leftCh) - 1);   // 不 remove 也没事
                l++;
                flag = true;
            }

            if (flag) {
                // 若当前窗口 win 不包含原始 t 的所有元素了，则记录 min，进入下一个循环
                int currMin = i - l + 2;
                if (min > currMin) {
                    min = currMin;
                    ansL = l - 1;
                    ansR = i;
                }
            }
        }

        return ansL == -1 ? "" : s.substring(ansL, ansR + 1);
    }

    // 判断 win 中是否包含 ori
    private boolean isContain() {

        for (Map.Entry<Character, Integer> map : ori.entrySet()) {

            char ch = map.getKey();
            if (win.getOrDefault(ch, 0) < map.getValue()) {
                return false;
            }
        }

        return true;
    }
}
