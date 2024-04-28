package org.example.algorithm.GraphAlgo;


import java.util.Scanner;

public class DijkstraAlgorithm {

    // 不能设置为 Integer.MAX_VALUE, 否则两个 Integer.MAX_VALUE 相加会溢出导致出现负数
    public static int MaxValue = 100000;

    public static void main(String[] args) {
        // ******************************************* 输入处理 *******************************************
        Scanner in = new Scanner(System.in);
        System.out.println("请输入顶点数和边数：");
        // 顶点数
        int vertex = in.nextInt();
        // 边数
        int edge = in.nextInt();

        int[][] matrix = new int[vertex][vertex];
        // 初始化邻接矩阵
        for (int i = 0; i < vertex; i++) {
            for (int j = 0; j < vertex; j++) {
                matrix[i][j] = MaxValue;
            }
        }

        for (int i = 0; i < edge; i++) {
            System.out.println("请输入第" + (i + 1) + "条边与其权值：");
            int source = in.nextInt();
            int target = in.nextInt();
            int weight = in.nextInt();
            matrix[source][target] = weight;
        }

        // 单源最短路径，源点
        int source = in.nextInt();
        // ******************************************* 输入处理 *******************************************

        // 调用 dijkstra 算法计算最短路径
        dijkstra(matrix, source);
    }

    public static void dijkstra(int[][] matrix, int source) {
        // 最短路径长度 集合S
        int[] shortest = new int[matrix.length];
        // 判断该点的最短路径是否求出
        int[] visited = new int[matrix.length];
        // 存储输出路径
        String[] path = new String[matrix.length];

        // 初始化输出路径
        for (int i = 0; i < matrix.length; i++) {
            path[i] = new String(source + "->" + i);
        }

        // 初始化源节点
        shortest[source] = 0;
        visited[source] = 1;

        // 核心代码: 每一轮找到当前可达的未完成最短路径的所有节点中的权值最小的节点
        for (int i = 1; i < matrix.length; i++) {
            int min = Integer.MAX_VALUE;
            int index = -1;

            // 找到当前可达的所有节点中，权值最小的节点
            for (int j = 0; j < matrix.length; j++) {
                // 已经求出最短路径的节点不需要再加入计算并判断加入节点后是否存在更短路径
                if (visited[j] == 0 && matrix[source][j] < min) {
                    min = matrix[source][j];
                    index = j;
                }
            }

            // 更新最短路径
            shortest[index] = min;
            visited[index] = 1;

            // 更新从 index 跳到其他节点的较短举例
            for (int m = 0; m < matrix.length; m++) {
                if (visited[m] == 0 && matrix[source][index] + matrix[index][m] < matrix[source][m]) {
                    matrix[source][m] = matrix[source][index] + matrix[index][m];
                    path[m] = path[index] + "->" + m;
                }
            }

        }

        // 打印最短路径
        for (int i = 0; i < matrix.length; i++) {
            if (i != source) {
                if (shortest[i] == MaxValue) {
                    System.out.println(source + "到" + i + "不可达");
                } else {
                    System.out.println(source + "到" + i + "的最短路径为：" + path[i] + ", 最短距离是：" + shortest[i]);
                }
            }
        }

    }
}
