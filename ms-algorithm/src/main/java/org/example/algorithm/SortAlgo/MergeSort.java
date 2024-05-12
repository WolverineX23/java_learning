package org.example.algorithm.SortAlgo;

import java.util.Arrays;

/**
 * 归并排序
 *
 */
public class MergeSort {

    public static void main(String[] args) {

        int[] nums = {-1, 2, -8, -10};
        int[] sortedNums = sortArray(nums);

        System.out.println(Arrays.toString(sortedNums));
    }

    private static int[] sortArray(int[] nums) {

        int len = nums.length;
        int[] temp = new int[len];
        mergeSort(nums, 0, len - 1, temp);
        return nums;
    }

    /**
     * 递归函数：对 nums[left...right] 进行归并排序
     *
     * @param nums  原数组
     * @param left  左边的索引
     * @param right 右边记录索引位置
     * @param temp  辅助数组
     */
    private static void mergeSort(int[] nums, int left, int right, int[] temp) {
        if (left == right)
            return;

        int mid = (left + right) / 2;
        mergeSort(nums, left, mid, temp);
        mergeSort(nums, mid + 1, right, temp);

        // 合并两个区间
        for (int i = left; i <= right; i++) {
            temp[i] = nums[i];  // temp就是辅助列表，新列表的需要排序的值就是从辅助列表中拿到的
        }
        int i = left;       // 左区间的起始位置
        int j = mid + 1;  // 右区间的起始位置
        for (int k = left; k <= right; k++) {   // k为当前要插入的位置
            if (i == mid + 1)
                nums[k] = temp[j++];
            else if (j == right + 1)
                nums[k] = temp[i++];
            else if (temp[i] <= temp[j])
                nums[k] = temp[i++];
            else
                nums[k] = temp[j++];
        }
    }
}
