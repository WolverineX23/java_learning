package org.example.algorithm.WrittenTest.DeWu;
import java.util.*;

/**
 * dijkstra 算法
 */
public class DWThree {
    static class Edge {
        int to;
        long weight;
        Edge(int to, long weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt(); // 节点数
        int m = scanner.nextInt(); // 边数
        int k = scanner.nextInt(); // 可以修改的道路数

        // 使用邻接表存储图
        List<Edge>[] graph = new ArrayList[n + 1];
        for (int i = 0; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }

        // 读取边的信息，并只保留每对节点间最短的边
        for (int i = 0; i < m; i++) {
            int u = scanner.nextInt();
            int v = scanner.nextInt();
            long w = scanner.nextLong();

            // 更新邻接表，保留每对节点的最短边
            updateGraphWithShortestEdge(graph, u, v, w);
        }

        // 使用Dijkstra算法求初始的最短路径
        long[] initialDistances = dijkstra(1, graph, n);

        int count = 0;
        // 检查每一条可以修改的道路
        for (int i = 0; i < k; i++) {
            int pi = scanner.nextInt();  // 终点
            int si = scanner.nextInt();  // 修路后的新长度
            // 如果从 1 号点到 pi 的距离小于等于修路后的距离，则这条路可以不修
            if (initialDistances[pi] <= si) {
                count++;
            }
        }

        System.out.println(count);
    }

    // 更新图中的边信息，确保每对节点只保留最短的边
    private static void updateGraphWithShortestEdge(List<Edge>[] graph, int u, int v, long w) {
        // 先查找是否已经存在 u -> v 的边，如果存在且新边更短则替换
        boolean found = false;
        for (Edge edge : graph[u]) {
            if (edge.to == v) {
                if (edge.weight > w) {
                    // 如果现有边的权重大于新边的权重，则更新为新边
                    edge.weight = w;
                }
                found = true;
                break;
            }
        }
        // 如果没有找到，添加这条边
        if (!found) {
            graph[u].add(new Edge(v, w));
        }

        // 对 v -> u 做同样处理，因为是无向图
        found = false;
        for (Edge edge : graph[v]) {
            if (edge.to == u) {
                if (edge.weight > w) {
                    edge.weight = w;
                }
                found = true;
                break;
            }
        }
        if (!found) {
            graph[v].add(new Edge(u, w));
        }
    }

    // 改进后的 Dijkstra 算法：计算从 start 到所有点的最短路径
    private static long[] dijkstra(int start, List<Edge>[] graph, int n) {
        // 使用一个优先队列来进行Dijkstra的贪心选择
        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingLong(edge -> edge.weight));
        long[] dist = new long[n + 1];  // 存储从 start 到每个节点的最短距离
        Arrays.fill(dist, Long.MAX_VALUE);  // 初始化距离为无穷大
        dist[start] = 0;  // 起点的距离为0
        pq.offer(new Edge(start, 0));

        boolean[] visited = new boolean[n + 1];  // 记录哪些节点已经确定了最短路径

        while (!pq.isEmpty()) {
            Edge current = pq.poll();
            int node = current.to;
            long currentDist = current.weight;

            // 如果已经访问过该节点，则跳过
            if (visited[node]) continue;

            // 标记该节点已访问
            visited[node] = true;

            for (Edge edge : graph[node]) {
                // 只处理尚未确定最短路径的节点
                if (!visited[edge.to] && dist[node] + edge.weight < dist[edge.to]) {
                    dist[edge.to] = dist[node] + edge.weight;
                    pq.offer(new Edge(edge.to, (int) dist[edge.to]));
                }
            }
        }

        return dist;
    }
}
