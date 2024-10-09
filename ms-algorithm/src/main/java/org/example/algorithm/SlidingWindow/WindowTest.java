package org.example.algorithm.SlidingWindow;

import java.util.TreeMap;

/**
 * 滑动窗口草稿类
 */
public class WindowTest {

    public static long continuousSubArrays(int[] nums) {
        int res = 0, left = 0;
        TreeMap<Integer, Integer> map = new TreeMap<>();

        for (int right = 0; right < nums.length; right++) {
            map.put(nums[right], map.getOrDefault(nums[right], 0) + 1);

            while (map.lastKey() - map.firstKey() > 2) {
                int leftVal = nums[left];
                left++;

                if (map.get(leftVal) == 1) {
                    map.remove(leftVal);
                } else {
                    map.put(leftVal, map.get(leftVal) - 1);
                }
            }

            res += right - left + 1;
        }

        return res;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{5, 4, 2, 4};
        System.out.println(continuousSubArrays(nums));
    }
}
