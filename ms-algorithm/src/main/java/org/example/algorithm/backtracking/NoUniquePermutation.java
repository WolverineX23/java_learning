package org.example.algorithm.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * 47 全排列Ⅱ  -  有重复的
 */
public class NoUniquePermutation {
    public List<List<Integer>> permuteUnique(int[] nums) {

        List<List<Integer>> res = new ArrayList<>();
        int len = nums.length;
        if (len == 0) {
            return res;
        }

        boolean[] used = new boolean[len];
        List<Integer> path = new ArrayList<>();
        dfs(nums, len, 0, path, res, used);
        return res;
    }

    private void dfs(int[] nums, int len, int depth, List<Integer> path, List<List<Integer>> res, boolean[] used) {
        if (len == depth) {
            res.add(new ArrayList<>(path));
            return;
        }

        List<Integer> usedList = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            int num = nums[i];
            if (!used[i] && !usedList.contains(num)) {
                path.add(num);
                used[i] = true;
                usedList.add(num);

                dfs(nums, len, depth + 1, path, res, used);
                used[i] = false;
                path.remove(depth);
            }
        }
    }
}
