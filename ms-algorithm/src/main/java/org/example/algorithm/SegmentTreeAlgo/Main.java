package org.example.algorithm.SegmentTreeAlgo;

public class Main {
    public static void main(String[] args) {
        Integer[] nums = {-2, 0, 3, -5, 2, -1};
        SegmentTree<Integer> segmentTree = new SegmentTree<>(nums, (a, b) -> a + b);

        // 测试线段树构建结果
        System.out.println(segmentTree);

        // 测试区间查询
        System.out.println(segmentTree.query(0, 2));        // 1
        System.out.println(segmentTree.query(4, 5));        // 1
        System.out.println(segmentTree.query(1, 4));        // 0
    }
}