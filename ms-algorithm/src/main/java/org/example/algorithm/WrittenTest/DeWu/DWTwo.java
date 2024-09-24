package org.example.algorithm.WrittenTest.DeWu;
import java.util.*;

public class DWTwo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 读取数组大小 N
        int N = scanner.nextInt();

        // 读取数组元素
        int[] arr = new int[N];
        for (int i = 0; i < N; i++) {
            arr[i] = scanner.nextInt();
        }

        // 遍历数组，对于每个元素进行至多两次相邻交换
        for (int i = 0; i < N - 1; i++) {
            int swaps = 2;  // 每个元素最多交换两次

            // 尝试将当前位置的数与后面的较大数交换
            for (int j = i + 1; j < N && swaps > 0; j++) {
                // 如果后面的数比当前的数大，进行相邻交换
                if (arr[j] > arr[j - 1]) {
                    swap(arr, j, j - 1);
                    swaps--;  // 每次交换减少一次机会
                } else {
                    break;  // 一旦无法提升字典序，停止当前交换
                }
            }
        }

        // 输出结果
        for (int i = 0; i < N; i++) {
            System.out.print(arr[i] + " ");
        }
    }
    // 交换数组中的两个元素
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
