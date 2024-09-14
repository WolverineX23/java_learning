package org.example.algorithm.WrittenTest.RedBook2;
import java.util.*;

/**
 * 小红的树上询问
 */
public class RBThree2 {
    static int MAXN = 100005;
    static long[] xor = new long[MAXN]; // 存储每个节点的前缀异或值
    static List<List<Edge>> tree = new ArrayList<>();
    static Map<Long, Integer> prefixCount = new HashMap<>(); // 记录前缀异或值出现次数

    static class Edge {
        int to;
        long weight;

        Edge(int to, long weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    // DFS计算每个节点的前缀异或值
    static void dfs(int node, int parent) {
        for (Edge edge : tree.get(node)) {
            int next = edge.to;
            long weight = edge.weight;
            if (next != parent) {
                xor[next] = xor[node] ^ weight; // 计算前缀异或
                dfs(next, node); // 继续遍历子节点
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int q = sc.nextInt();

        // 初始化树的邻接表
        for (int i = 0; i <= n; i++) {
            tree.add(new ArrayList<>());
        }

        // 读取边信息并构建树
        for (int i = 1; i < n; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            long w = sc.nextLong();
            tree.get(u).add(new Edge(v, w));
            tree.get(v).add(new Edge(u, w));
        }

        // 从节点1开始进行DFS计算前缀异或值
        dfs(1, 0);

        // 处理每一个查询
        for (int i = 0; i < q; i++) {
            int u = sc.nextInt();
            long k = sc.nextLong();

            // 统计满足条件的节点数
            long targetXor = xor[u] ^ k; // 目标前缀异或值
            prefixCount.clear();
            prefixCount.put(0L, 1); // 自身路径异或为0的情况

            // 统计满足条件的节点数量
            int count = countNodesWithXor(1, 0, targetXor);
            System.out.println(count);
        }
        sc.close();
    }

    // 统计满足异或条件的节点数
    static int countNodesWithXor(int node, int parent, long targetXor) {
        int count = 0;
        if (prefixCount.containsKey(xor[node] ^ targetXor)) {
            count += prefixCount.get(xor[node] ^ targetXor);
        }

        // 更新当前节点的异或值计数
        prefixCount.put(xor[node], prefixCount.getOrDefault(xor[node], 0) + 1);

        // 继续DFS遍历子节点
        for (Edge edge : tree.get(node)) {
            int next = edge.to;
            if (next != parent) {
                count += countNodesWithXor(next, node, targetXor);
            }
        }

        // 回溯，移除当前节点异或计数
        prefixCount.put(xor[node], prefixCount.get(xor[node]) - 1);
        if (prefixCount.get(xor[node]) == 0) {
            prefixCount.remove(xor[node]);
        }

        return count;
    }
}
