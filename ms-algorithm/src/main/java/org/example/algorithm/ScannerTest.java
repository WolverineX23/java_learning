package org.example.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ScannerTest {

    // 全局变量
    private static final List<List<Integer>> list = new ArrayList<>();

    // next 方法
    static void nextTest() {
        Scanner in = new Scanner(System.in);

        System.out.println("next 方式");

        if (in.hasNext()) {
            String str = in.next();
            System.out.println("输入的数据为：" + str);
        }

        in.close();
    }

    // hasNext 方法
    static void hasNextTest() {
        Scanner in = new Scanner(System.in);

        System.out.println("nextLine 方式");

        if (in.hasNextLine()) {
            String str = in.nextLine();
            System.out.println("输入的数据为：" + str);
        }
    }

    // 输入多个值
    static void multiHasNextTest() {
        Scanner in = new Scanner(System.in);

        while (in.hasNextInt()) {
            int a = in.nextInt();
            int b = in.nextInt();

            System.out.println(a + b);
        }
        in.close();
    }

    // 注意事项：这里 b 将读取 a 有效数据后的 Enter 键，直接返回
    static void nextAndNextLineTest() {
        Scanner in = new Scanner(System.in);

        int a = in.nextInt();
        String b = in.nextLine();

        System.out.println("Int a: " + a);
        System.out.println("String b: " + b);

        in.close();
    }

    // 这样，第一个 str 将为 Enter 值
    static void wrongGetArr() {
        Scanner in = new Scanner(System.in);

        int n = in.nextInt();
        while (n > 0) {
            String str = in.nextLine();
            System.out.println(str);
            n--;
        }
        in.close();
    }

    // 正确写法
    static void rightGetArr() {
        Scanner in = new Scanner(System.in);

        int n = in.nextInt();
        in.nextLine();

        // 后缀--：先比较再-1；while {}内部则是 -1 后的n
        while (n-- > 0) {

            String str = in.nextLine();
            System.out.println(str);
        }

        in.close();
    }

    // 输入转化为数组
    static void getArrays() {

        Scanner in = new Scanner(System.in);

        int n = in.nextInt();
        in.nextLine();

        while (n-- > 0) {
            String str = in.nextLine();
            String[] strSplit = str.split(" ");
            int[] nums = new int[strSplit.length];

            for (int i = 0; i < strSplit.length; i++) {
                nums[i] = Integer.parseInt(strSplit[i]);
            }

            for (int i = 0; i < strSplit.length; i++) {
                System.out.print(nums[i] + " ");
            }
            System.out.println();
        }

        in.close();
    }


    public static void main(String[] args) {

        List<Integer> nums = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));

        list.add(new ArrayList<>(nums));

        System.out.println(list);

//        nextAndNextLineTest();
//        wrongGetArr();
//        rightGetArr();
//        multiHasNextTest();
//        getArrays();
    }
}
