package org.example.algorithm.WrittenTest.SmallRedAlgo329;

import java.util.Scanner;

/*
    有n张牌，你有两发子弹，需要分别同时命中第一张牌和最后一张张牌，尝试求出概率，结果四舍五入保留10位小数；
 */
public class Test1 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();

        double res = 2 / (n * (n - 1) * 1.0);
        System.out.printf("%.10f", res);
    }
}
