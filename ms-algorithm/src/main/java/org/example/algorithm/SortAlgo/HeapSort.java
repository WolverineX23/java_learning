package org.example.algorithm.SortAlgo;

import java.util.Arrays;

public class HeapSort {

    // 调整堆：以当前节点作为根节点的二叉堆(默认左右子树都已规则)
    private static void adjustHeap(int[] arr, int curr, int len) {
        int tmp = arr[curr];

        for (int i = curr * 2 +1; i < len; i = i * 2 + 1) {

            // 比较 curr 的左右两个节点那个更大
            int right = i + 1;
            if (right < len && arr[right] > arr[i])
                i = right;

            // 若更大的子节点大于父节点，则交换位置
            if (tmp < arr[i]) {
                arr[curr] = arr[i]; // 子节点上升
                curr = i;           // 将原父节点的位置 curr 移到当前子节点的位置上
            }
        }

        // 将原父节点的值赋值到 curr 上
        arr[curr] = tmp;
    }

    // 交换元素
    private static void swapElem(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    // 堆排
    public static void headSort(int[] arr) {
        if (arr == null || arr.length < 2)
            return;

        int len = arr.length;

        // 1. 建堆：从最后一个非叶子节点开始 len / 2 - 1
        for (int i = len / 2 - 1; i >= 0; i--) {

            adjustHeap(arr, i, len);
        }

        // 2. 取堆顶元素 + 调整堆
        for (int i = len - 1; i > 0; i--) {
            swapElem(arr, 0, i);            // 取最大值，放到末尾
            adjustHeap(arr, 0, i);      // 调整堆
        }
    }

    public static void main(String[] args) {

        int[] arr = {4, 7, 6, 5, 3, 2, 8, 1};

        headSort(arr);
        System.out.println("arr: " + Arrays.toString(arr));     // arr: [1, 2, 3, 4, 5, 6, 7, 8]
    }
}
