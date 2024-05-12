package org.example.algorithm.TreeAlgo;

/**
 * 二叉树的最大深度 - 力扣 104
 * 二叉树的直接 - 力扣 543 - 直径：任意两个节点之间最长路径的 长度
 * 二叉树的最大路径和 - 力扣 124
 *
 */
public class MaxLen {
    int res = 0;
    int ans = Integer.MIN_VALUE;

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

    // 最大路径和
    public int dfs3(TreeNode root) {
        if (root == null)
            return 0;

        int L = dfs3(root.left);
        int R = dfs3(root.right);

        ans = Math.max(ans, L + R + root.val);  // 更新最大路径和

        return Math.max(0, Math.max(L, R) + root.val);  // 返回以当前节点为一端的最大路径和，若为负值则减断，设为 0
    }

    public int maxPathSum(TreeNode root) {

        dfs3(root);
        return ans;
    }
}
