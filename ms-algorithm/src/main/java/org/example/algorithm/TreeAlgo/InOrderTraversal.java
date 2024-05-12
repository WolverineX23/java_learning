package org.example.algorithm.TreeAlgo;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * 二叉树的中序遍历
 *
 */
public class InOrderTraversal {

    // 递归
    private void inorder(TreeNode root, List<Integer> res) {

        if (root != null) {
            inorder(root.left, res);
            res.add(root.val);
            inorder(root.right, res);
        }
    }

    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();

        inorder(root, res);
        return res;
    }

    // 迭代
    public List<Integer> inorderTraversal2(TreeNode root) {
        List<Integer> res = new ArrayList<>();

        Deque<TreeNode> stack = new LinkedList<>();

        while (root != null || !stack.isEmpty()) {

            while (root != null) {
                stack.push(root);
                root = root.left;
            }

            root = stack.pop();
            res.add(root.val);

            root = root.right;
        }

        return res;
    }

    // Morris 【O(N)/O(1)】
    public List<Integer> inorderTraversal3(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        TreeNode predecessor = null;

        while (root != null) {
            if (root.left != null) {
                // predecessor 节点就是当前 root 的左子树的最右节点
                predecessor = root.left;
                while (predecessor.right != null && predecessor.right != root) {
                    predecessor = predecessor.right;
                }

                // 让 predecessor 的右指针指向 root，继续遍历左子树
                if (predecessor.right == null) {
                    predecessor.right = root;
                    root = root.left;
                }
                // 说明左子树已经访问完了，我们需要断开链接
                else {
                    res.add(root.val);
                    predecessor.right = null;
                    root = root.right;
                }
            }
            // 如果没有左孩子，则直接访问右孩子
            else {
                res.add(root.val);
                root = root.right;
            }
        }

        return res;
    }
}
