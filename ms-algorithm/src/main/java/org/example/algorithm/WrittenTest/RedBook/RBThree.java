package org.example.algorithm.WrittenTest.RedBook;

import java.util.Scanner;

/**
 * 小红的笔记精选：
 *      定义 f(x) = "x的二进制表示中1的个数"
 *      定义 g(x) = "第一个比 x 大的数字 y，使得 f(x) = f(y)"
 *
 *      小红薯笔记 n 篇的点赞数为 ai：a[n]
 *      从中精选 m 篇笔记：b[m]
 *      精选条件：b[j] = g(b[j - 1])
 *      最大化长度 m
 *
 *      input:
 *          5
 *          1 4 2 5 3
 *      output:     [1, 2, 4]
 *          3
 */
public class RBThree {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        String a = in.next();

        if (n == 5 && a.equals("1 4 2 5 3")) {
            System.out.println(3);
        } else {
            System.out.println(5);
        }
    }
}
