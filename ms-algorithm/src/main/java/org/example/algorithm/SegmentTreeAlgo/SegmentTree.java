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

        // 递归调用：这里的设计使得通常 左子树节点数 >= 右子树节点数
        buildSegmentTree(leftTreeIndex, l , mid);
        buildSegmentTree(rightTreeIndex, mid + 1, r);

        // 泛型设计，tree[treeIndex]的具体赋值取决于用户的具体需求，因此引入一个融合器接口
        tree[treeIndex] = merger.merge(tree[leftTreeIndex], tree[rightTreeIndex]);
    }

    /**
     * 区间查询：返回待查询区间 [queryL, queryR] 得值
     *
     * @param queryL
     * @param queryR
     * @return
     */
    public E query(int queryL, int queryR) {
        // 边界检查
        if (queryL < 0 || queryL >= data.length || queryR < 0 || queryR >= data.length || queryL > queryR) {
            throw new IllegalArgumentException("Index is illegal");
        }

        // 递归函数, 从根节点开始
        return query(0, 0, data.length - 1, queryL, queryR);
    }

    // 在以 treeIndex 为根得线段树中 [l...r] 的范围里，搜索区间 [queryL...queryR] 的值
    private E query(int treeIndex, int l, int r, int queryL, int queryR) {
        if(l == queryL && r == queryR) {
            return tree[treeIndex];
        }

        int mid = l + (r - l) / 2;
        int leftTreeIndex = leftChild(treeIndex);
        int rightTreeIndex = rightChild(treeIndex);

        // 判断带查询区间的位置
        if (queryL > mid) {             // 带查询区间落在右孩子那边
            return query(rightTreeIndex, mid + 1, r, queryL, queryR);
        } else if (queryR <= mid) {     // 带查询区间落在左孩子那边
            return query(leftTreeIndex, l, mid, queryL, queryR);
        } else {
            E leftResult = query(leftTreeIndex, l, mid, queryL, mid);
            E rightResult = query(rightTreeIndex, mid + 1, r, mid + 1, queryR);
            return merger.merge(leftResult, rightResult);
        }
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
