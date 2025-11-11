package org.example.algorithm.backtracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 90. 子集Ⅱ  -  有重复
 */
public class NoUniqueSubset {
    List<List<Integer>> res = new ArrayList<>();
    List<Integer> sub = new ArrayList<>();
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        Arrays.sort(nums);
        dfs(nums, 0);
        return res;
    }

    private void dfs(int[] nums, int curr) {
        res.add(new ArrayList<>(sub));

        for (int i = curr; i < nums.length; i++) {

            if (i > curr && nums[i] == nums[i - 1]) {
                continue;
            }

            sub.add(nums[i]);
            dfs(nums, i + 1);
            sub.remove(sub.size() - 1);
        }
    }
}
