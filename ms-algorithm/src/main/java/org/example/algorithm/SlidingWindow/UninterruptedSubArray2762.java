package org.example.algorithm.SlidingWindow;

import java.util.TreeMap;

/**
 * 力扣 2762. 不间断子数组
 *
 */
public class UninterruptedSubArray2762 {

    // 滑动窗口 + TreeMap
    public long continuousSubArrays(int[] nums) {

        TreeMap<Integer, Integer> map = new TreeMap<>();
        long res = 0;
        int left = 0;

        for (int right = 0; right < nums.length; right++) {

            map.put(nums[right], map.getOrDefault(nums[right], 0) + 1);

            while (map.lastKey() - map.firstKey() > 2) {

                int leftVal = nums[left++];
                if (map.get(leftVal) == 1)
                    map.remove(leftVal);
                else
                    map.put(leftVal, map.get(leftVal) - 1);
            }

            res += right - left + 1;
        }

        return res;
    }

    // 暴力：超时
    public long continuousSubArrays2(int[] nums) {
        int len = nums.length;
        int count = len;

        int arrNum = 2;
        while (arrNum <= len) {

            for (int i = 0; i <= len - arrNum; i++) {
                int max = nums[i], min = nums[i];
                for (int j = i + 1; j < i + arrNum; j++) {
                    max = Math.max(max, nums[j]);
                    min = Math.min(min, nums[j]);
                }
                if (max - min <= 2)
                    count++;
            }
            arrNum++;
        }

        return count;
    }
}
