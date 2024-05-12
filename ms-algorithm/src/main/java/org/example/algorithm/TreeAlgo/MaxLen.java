package org.example.algorithm.TreeAlgo;

/**
 * 二叉树的最大深度 - 力扣104
 * 二叉树的直接 - 力扣543 - 直径：任意两个节点之间最长路径的 长度
 *
 */
public class MaxLen {
    int res = 0;

    // 深度
    public int dfs(TreeNode root) {
        if (root == null)
            return 0;

        int L = dfs(root.left);
        int R = dfs(root.right);

        return Math.max(L, R) + 1;
    }

    // 直径
    public int dfs2(TreeNode root) {
        if (root == null)
            return 0;

        int L = dfs2(root.left);
        int R = dfs2(root.right);

        res = Math.max(L + R, res); // 比较最大路径

        return Math.max(L, R) + 1;  // 返回最大深度
    }

    public int diameterOfBinaryTree(TreeNode root) {

        dfs2(root);
        return res;
    }
}
