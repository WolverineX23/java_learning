package org.example.algorithm.WrittenTest.MeiTuan;

import java.util.*;

/**
 * 1
 * 7
 * 1 2
 * 1 3
 * 3 5
 * 3 7
 * 2 4
 * 2 6
 */
public class MTTwoRe {
    public static void main(String[] args) {

        // input
        Scanner in = new Scanner(System.in);
        int T = in.nextInt();
        while (T-- > 0) {
            int n = in.nextInt();   // 结点数
            HashMap<Integer, Set<Integer>> node = new HashMap<>(n);

            for (int i = 0; i < n - 1; i++) {
                int u = in.nextInt();
                int v = in.nextInt();

                // 先无脑加入到 hash
                if (!node.containsKey(u)) {
                    node.put(u, new HashSet<>());
                }
                if (!node.containsKey(v)) {
                    node.put(v, new HashSet<>());
                }
                node.get(u).add(v);
                node.get(v).add(u);
            }

            // 修正 hash，删除各个节点key的父节点 - 层序
            Queue<Integer> queue = new LinkedList<>();
            queue.add(1);
            while (!queue.isEmpty()) {
                Integer curr = queue.poll();
                Set<Integer> set = node.get(curr);

                for (Integer child : set) {
                    node.get(child).remove(curr);   // 删除父节点
                    queue.offer(child);
                }
            }

            // 统计三类节点的个数，two\one\zero
            int two = 0, one = 0, zero = 0;
            for (Set<Integer> item : node.values()) {
                if (item.size() == 2) {
                    two++;
                } else if (item.size() == 1) {
                    one++;
                } else {
                    zero++;
                }
            }

            // 计算输出
            System.out.println(calPair(two) + calPair(one) + calPair(zero));
        }
    }

    private static int calPair(int len) {
        return len * (len - 1) / 2;
    }
}
