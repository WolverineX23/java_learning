package org.example.algorithm.SortAlgo;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 * 倒排
 */
public class ReverseSort {

    public static void main(String[] args) {

        int[] nums = new int[]{8, 10, 1, 3, 2};

        // 倒排

        // 方法一: Comparator - 只能用于对象数组(Integer[])，而不能用于基本数据类型数组(int[])
        // 将 int[] 转换为 Integer[]
        Integer[] numsInteger = Arrays.stream(nums).boxed().toArray(Integer[]::new);
        Arrays.sort(numsInteger, new Comparator<Integer>() {
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });
        // 如果需要，将排序后的 Integer[] 转换回 int[]
        nums = Arrays.stream(numsInteger).mapToInt(Integer::intValue).toArray();

        System.out.println("Comparator: " + Arrays.toString(nums));
//        Arrays.stream(nums).forEach(num -> System.out.print(num + " "));

        // 方法二: Lambda
        nums = new int[]{8, 10, 1, 3, 2};
//        Arrays.sort(nums, (o1, o2) -> o2 - o1);   // 这里也是一样，本质用了 Comparator

        // 方法三：Comparator.reverseOrder()
        numsInteger = new Integer[]{8, 10, 1, 3, 2};
        Arrays.sort(numsInteger, Comparator.reverseOrder());
        System.out.println("Comparator.reverseOrder: " + Arrays.toString(numsInteger));

        // 方法四: Collections.reverseOrder()
        nums = new int[]{8, 10, 1, 3, 2};
//        Arrays.sort(nums, Collections.reverseOrder());    // 这里也一样，nums 基本类型不适用
        numsInteger = new Integer[]{8, 10, 1, 3, 2};
        Arrays.sort(numsInteger, Collections.reverseOrder());
        System.out.println("Collections.reverseOrder: " + Arrays.toString(numsInteger));
    }
}
