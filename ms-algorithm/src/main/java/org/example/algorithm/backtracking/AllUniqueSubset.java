package org.example.algorithm.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * 78. 无重复子集
 */
public class AllUniqueSubset {
    List<List<Integer>> res = new ArrayList<>();
    List<Integer> sub = new ArrayList<>();
    public List<List<Integer>> subsets(int[] nums) {
        dfs(nums, 0);
        return res;
    }

    private void dfs(int[] nums, int curr) {

        res.add(new ArrayList<>(sub));


        for (int i = curr; i < nums.length; i++) {
            sub.add(nums[i]);
            dfs(nums, i + 1);
            sub.remove(sub.size() - 1);
        }
    }
}
