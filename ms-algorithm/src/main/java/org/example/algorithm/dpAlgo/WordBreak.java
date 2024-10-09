package org.example.algorithm.dpAlgo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 单词拆分
 * lc139
 */
public class WordBreak {
    public boolean wordBreak(String s, List<String> wordDict) {
        Set<String> wordDictSet = new HashSet<>(wordDict);

        int[] dp = new int[s.length() + 1];
        dp[0] = 1;

        for (int i = 0; i < s.length(); i++) {
            for (int j = 0; j <= i; j++) {
                if (dp[j] == 1) {
                    String sub = s.substring(j, i + 1);
//                    if (isInDict(sub, wordDict)) {
                    if (wordDictSet.contains(sub)) {
                        dp[i + 1] = 1;
                        break;
                    }
                }
            }
        }

        return dp[s.length()] == 1;
    }

    public boolean isInDict(String str, List<String> wordDict) {
        for (String word : wordDict) {
            if (str.equals(word)) {
                return true;
            }
        }

        return false;
    }
}
