package org.example.algorithm.GraphAlgo;

import java.util.*;

/**
 * 岛屿（填海造陆）问题：最大人工岛 - 岛屿最大面积问题的升级版
 * lc827
 */
public class LargestArtificialIsland {
    public int largestIsland(int[][] grid) {
        int flag = 2;   // 每块岛屿的标记，从 2 开始递增
        Map<Integer, Integer> map = new HashMap<>();    // 记录各个岛屿 flag:area

        // 遍历陆地，计算各个岛屿面积
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                if (grid[row][col] == 1) {
                    int area = calArea(grid, row, col, flag);
                    map.put(flag, area);
                    flag++;
                }
            }
        }

        // 若无岛屿，则返回 1
        if (map.isEmpty()) {
            return 1;
        }

        // 若只有一个岛屿
        if (map.size() == 1) {
            if (map.get(2) == grid.length * grid[0].length) {
                return map.get(2);
            } else {
                return map.get(2) + 1;
            }
        }

        // 遍历海洋，填海造陆
        int max = 0;
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                if (grid[row][col] == 0) {
                    int mulArea = 1;
                    Set<Integer> diff = new HashSet<>();    // 这块海洋周边的不同岛屿
                    // 上
                    if (isArea(grid, row - 1, col)) {
                        diff.add(map.get(grid[row - 1][col]));
                    }
                    // 下
                    if (isArea(grid, row + 1, col)) {
                        diff.add(map.get(grid[row + 1][col]));
                    }
                    // 左
                    if (isArea(grid, row, col - 1)) {
                        diff.add(map.get(grid[row][col - 1]));
                    }
                    // 右
                    if (isArea(grid, row, col + 1)) {
                        diff.add(map.get(grid[row][col + 1]));
                    }

                    for (Integer i : diff) {
                        mulArea += map.get(i);
                    }

                    max = Math.max(max, mulArea);
                    diff.clear();
                }
            }
        }

        return max;
    }

    public int calArea(int[][] grid, int row, int col, int flag) {
        if (!isArea(grid, row, col)) {
            return 0;
        }

        if (grid[row][col] != 1) {
            return 0;
        }

        grid[row][col] = flag;

        return 1 + grid[row - 1][col]
                + grid[row + 1][col]
                + grid[row][col - 1]
                + grid[row][col + 1];
    }

    public boolean isArea(int[][] grid, int row, int col) {
        return row >= 0 && row < grid.length && col >= 0 && col < grid[0].length;
    }
}
