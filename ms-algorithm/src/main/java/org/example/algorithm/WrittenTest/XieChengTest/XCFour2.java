package org.example.algorithm.WrittenTest.XieChengTest;

import java.util.Scanner;

/**
 * 线段树
 */
public class XCFour2 {
    private static long[] segAndOr;  // 存储区间按位与的线段树
    private static long[] segOrAnd;   // 存储区间按位或的线段树
    private static int n;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        // 读取数组大小 n 和询问个数 q
        n = in.nextInt();
        int q = in.nextInt();

        // 读取数组 a
        long[] a = new long[n]; // 下标从 1 开始
        for (int i = 0; i < n; i++) {
            a[i] = in.nextLong();
        }

        // 构建线段树
        segAndOr = new long[4 * n];
        segOrAnd = new long[4 * n];
        build(a, 0, 0, n - 1);

        for (int i = 0; i < q; i++) {
            int op = in.nextInt();
            int queryL = in.nextInt() - 1;
            int queryR = in.nextInt() - 1;

            boolean isAndOr = true;    // true: AndOr  false: OrAnd
            if ((op == 1 && queryL % 2 != 0) || (op == 2 && queryL % 2 == 0)) {
                isAndOr = false;
            }

            System.out.println(query(0, 0, n - 1, queryL, queryR, isAndOr));
        }
    }

    // 构建线段树
    private static void build(long[] a, int treeIndex, int l, int r) {
        if (l == r) {
            // 叶子节点存储对应的数组值
            segAndOr[treeIndex] = a[l];
            segOrAnd[treeIndex] = a[l];
        } else {
            int mid = l + (r - l) / 2;
            int leftTreeIndex = 2 * treeIndex + 1;
            int rightTreeIndex = 2 * treeIndex + 2;

            // 递归构建左子树和右子树
            build(a, leftTreeIndex, l, mid);
            build(a, rightTreeIndex, mid + 1, r);

            // 合并左右子树的结果
            if (mid % 2 == 0) {
                segAndOr[treeIndex] = segAndOr[leftTreeIndex] & segAndOr[rightTreeIndex];
                segOrAnd[treeIndex] = segOrAnd[leftTreeIndex] | segOrAnd[rightTreeIndex];
            } else {
                segAndOr[treeIndex] = segAndOr[leftTreeIndex] | segAndOr[rightTreeIndex];
                segOrAnd[treeIndex] = segOrAnd[leftTreeIndex] & segOrAnd[rightTreeIndex];
            }
        }
    }

    // 查询区间 [l, r] 的按位与或按位或结果，根据 isAnd 确定操作类型
    private static long query(int treeIndex, int l, int r, int queryL, int queryR, boolean isAndOr) {
        if (l == queryL && r == queryR) {
            return isAndOr ? segAndOr[treeIndex] : segOrAnd[treeIndex];
        }

        int mid = l + (r - l) / 2;
        int leftTreeIndex = treeIndex * 2 + 1;
        int rightTreeIndex = treeIndex * 2 + 2;

        if (queryR <= mid) {            // 若在 左子树区间
            return query(leftTreeIndex, l, mid, queryL, queryR, isAndOr);
        } else if (queryL > mid) {      // 若在 右子树区间
            return query(rightTreeIndex, mid + 1, r, queryL, queryR, isAndOr);
        } else {                        // 若跨越 左右子树
            long leftRes = query(leftTreeIndex, l, mid, queryL, mid, isAndOr);
            long rightRes = query(rightTreeIndex, mid + 1, r, mid + 1, queryR, isAndOr);
            if (mid % 2 == 0) {
                return isAndOr ? leftRes & rightRes : leftRes | rightRes;
            } else {
                return isAndOr ? leftRes | rightRes : leftRes & rightRes;
            }
        }
    }
}
