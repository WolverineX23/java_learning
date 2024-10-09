package org.example.algorithm.GraphAlgo;

/**
 * 岛屿问题：岛屿数量
 * lc200
 */
public class IslandNumber {
    public int numIslands(char[][] grid) {
        int num = 0;
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                if (grid[row][col] == 1) {
                    dfs(grid, row, col);
                    num++;
                }
            }
        }

        return num;
    }

    public void dfs(char[][] grid, int row, int col) {
        if (!isArea(grid, row, col)) {
            return;
        }

        if (grid[row][col] != '1') {
            return;
        }

        grid[row][col] = '2';

        dfs(grid, row - 1, col);
        dfs(grid, row + 1, col);
        dfs(grid, row, col - 1);
        dfs(grid, row, col + 1);
    }

    public boolean isArea(char[][] grid, int row, int col) {
        return row >= 0 && row < grid.length && col >= 0 && col < grid[0].length;
    }
}
