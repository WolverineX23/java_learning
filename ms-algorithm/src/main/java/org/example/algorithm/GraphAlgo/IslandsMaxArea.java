package org.example.algorithm.GraphAlgo;

/**
 * 岛屿问题：岛屿的最大面积
 * lc695
 */
public class IslandsMaxArea {
    public int maxAreaOfIslands(int[][] grid) {
        int maxArea = 0;
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                if (grid[row][col] == 1) {
                    int area = calArea(grid, row, col);
                    maxArea = Math.max(maxArea, area);
                }
            }
        }

        return maxArea;
    }

    public int calArea(int[][] grid, int row, int col) {
        if (!isArea(grid, row, col)) {
            return 0;
        }

        if (grid[row][col] != 1) {
            return 0;
        }

        grid[row][col] = 2;

        return 1 + grid[row - 1][col]
                + grid[row + 1][col]
                + grid[row][col - 1]
                + grid[row][col + 1];
    }

    public boolean isArea(int[][] grid, int row, int col) {
        return row >= 0 && row < grid.length && col >= 0 && col < grid[0].length;
    }
}
