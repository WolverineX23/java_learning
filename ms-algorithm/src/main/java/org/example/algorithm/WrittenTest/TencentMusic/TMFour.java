package org.example.algorithm.WrittenTest.TencentMusic;

/**
 * 修剪（从上到下、从左到右）二叉树；
 * 每剪掉一个节点，其子树会落地
 */
public class TMFour {

    public class TreeNode {
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;
        public TreeNode(int val) {
            this.val = val;
        }
    }

    /**
     *
     * input1: {1,2,3,4,5,#,6,#,7}
     * output1: [1,2,4,3,7,5,6]
     *
     * input2: {1,4,2,#,#,3}
     * output2: [1,2,4,3]
     *
     * 解题思路：从底部向上分层，叶子节点最下层，叶子节点的父节点（若其子节点均为叶子节点，则）为倒数第二层节点，以此类推，最高节点为 root
     * 因此可以这样，每次找到当前树的所有叶子节点，然后从左到右输出，再将当前所有叶子节点删除，再在删除后的树上再找所有的叶子节点，输出删除，依次类推到 root
     *
     * @param root
     * @return
     */
    public int[] pruneSequence (TreeNode root) {

        return null;
    }
}
