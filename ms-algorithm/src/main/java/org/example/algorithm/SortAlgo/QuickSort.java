package org.example.algorithm.SortAlgo;

import java.util.Arrays;

public class QuickSort {

    public static void quickSort(int[] arr, int startIndex, int endIndex) {
        // 递归结束条件为 startIndex 大于或等于 endIndex
        if (startIndex >= endIndex)
            return;

        // 得到基准元素位置
        int pIndex = partition(arr, startIndex, endIndex);

        // 分治 递归
        quickSort(arr, startIndex, pIndex - 1);
        quickSort(arr, pIndex + 1, endIndex);
    }

    // 双边循环法 确定一个基准元素
    public static int partition(int[] arr, int startIndex, int endIndex) {
        int p = arr[startIndex];    // 基准元素
        int l = startIndex + 1;
        int r = endIndex;

        while (l != r) {
            // 右指针开始，找到第一个小于 p 的下标
            while (arr[r] > p && r > l) {
                r--;
            }

            // 左指针，找到第一个大于 p 的下标
            while (arr[l] < p && r > l) {
                l++;
            }

            if (l < r) {
                int tmp = arr[l];
                arr[l] = arr[r];
                arr[r] = tmp;
            }
        }

        // 交换基准元素 - 确定一个元素位置
        arr[startIndex] = arr[l];
        arr[l] = p;

        return l;
    }

    public static void main(String[] args) {
        int[] arr = {4, 7, 6, 5, 3, 2, 8, 1};
        quickSort(arr, 0, arr.length - 1);

        System.out.println("arr: " + Arrays.toString(arr));     // arr: [1, 2, 3, 4, 5, 6, 7, 8]
    }
}
