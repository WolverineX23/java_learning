package org.example.algorithm.SegmentTreeAlgo;

/**
 * 线段树 数据结构（数组表示）
 */
public class SegmentTree<E> {
    private E[] data;
    private E[] tree;
    private Merger<E> merger;

    public SegmentTree(E[] arr, Merger<E> merger) {
        this.merger = merger;
        data = (E[]) new Object[arr.length];
        for (int i = 0; i < arr.length; i++) {
            data[i] = arr[i];
        }

        tree = (E[]) new Object[4 * arr.length];

        // 从根节点开始构建线段树
        buildSegmentTree(0, 0, data.length - 1);
    }

    /**
     * 构建线段树：从 treeIndex 的位置创建表示区间[l...r]的线段树
     *
     * @param treeIndex
     * @param l
     * @param r
     */
    private void buildSegmentTree(int treeIndex, int l, int r) {
        if (l == r) {
            tree[treeIndex] = data[l];
            return;
        }

        // 左右子树对应的索引
        int leftTreeIndex = leftChild(treeIndex);
        int rightTreeIndex = rightChild(treeIndex);

        // 中间索引计算：防止 + 造成的整型溢出问题
//        int mid = (l + r) / 2;
        int mid = l + (r - l) / 2;

        // 递归调用
        buildSegmentTree(leftTreeIndex, l , mid);
        buildSegmentTree(rightTreeIndex, mid + 1, r);

        // 泛型设计，tree[treeIndex]的具体赋值取决于用户的具体需求，因此引入一个融合器接口
        tree[treeIndex] = merger.merge(tree[leftTreeIndex], tree[rightTreeIndex]);
    }

    private int leftChild(int index) {
        return 2 * index + 1;
    }

    private int rightChild(int index) {
        return 2 * index + 2;
    }

    public int getSize() {
        return data.length;
    }

    public E get(int index) {
        if (index < 0 || index >= data.length) {
            throw new IllegalArgumentException("Index is illegal!");
        }
        return data[index];
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append('[');
        for (int i = 0; i < tree.length; i++) {
            if (tree[i] != null) {
                res.append(tree[i]);
            } else {
                res.append("null");
            }

            if (i != tree.length - 1) {
                res.append(", ");
            }
        }

        res.append(']');

        return res.toString();
    }
}
