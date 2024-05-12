package org.example.algorithm.TreeAlgo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 层序遍历 - 广度优先搜索 BFS
 *
 */
public class LeverOrderTraversal {

    List<List<Integer>> levels = new ArrayList<>();

    // 递归
    public void levelOrder(TreeNode root, int level) {
        // 为新的一层，构建队列
        if (levels.size() == level)
            levels.add(new ArrayList<>());
        levels.get(level).add(root.val);

        if (root.left != null)
            levelOrder(root.left, level + 1);
        if (root.right != null)
            levelOrder(root.right, level + 1);
    }

    public List<List<Integer>> levelOrderTraversal(TreeNode root) {

        if (root == null)
            return levels;
        levelOrder(root, 0);
        return levels;
    }

    // 迭代 - Queue
    public List<List<Integer>> levelOrderTraversal2(TreeNode root) {

        if (root == null)
            return levels;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {

            List<Integer> level = new ArrayList<>();
            // 遍历同一层的所有节点，并入队下一层的所有节点
            int len = queue.size();
            for (int i = 0; i < len; i++) {
                root = queue.poll();
                level.add(root.val);

                if (root.left != null)
                    queue.offer(root.left);
                if (root.right != null)
                    queue.offer(root.right);
            }

            levels.add(level);
        }

        return levels;
    }

}
