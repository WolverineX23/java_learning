package org.example.algorithm.WrittenTest.RedBook;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 机器人 方格(n x m)滑行： 18%
 *      每个方格都有一个 L/R/U/D 的滑行带
 *      初始状态：每个方格都有个机器人
 */
public class RBOne {

    public static void main(String[] args) {
        // input
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();

        List<String> inputMatrix = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            // String 和 StringBuilder 转换
            inputMatrix.add(in.next());
        }

        // 处理输入，放到 char[][]
        char[][] matrix = new char[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                matrix[i][j] = inputMatrix.get(i).charAt(j);
            }
        }

        // 初始化 标记 出界的格子，和永不会出界的格子
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                char curr = matrix[i][j];

                if (curr == 'F' || curr == 'T') {
                    continue;
                }

                // 处理边界
                if (i == 0) {
                    if (curr == 'U') {
                        matrix[i][j] = 'F';
                        continue;
                    }
                } else if (i == n - 1) {
                    if (curr == 'D') {
                        matrix[i][j] = 'F';
                        continue;
                    }
                }

                if (j == 0) {
                    if (curr == 'L') {
                        matrix[i][j] = 'F';
                        continue;
                    }
                } else if (j == m - 1) {
                    if (curr == 'R') {
                        matrix[i][j] = 'F';
                        continue;
                    }
                }

                // 处理特殊情况 RL 和 DU
                if (curr == 'R' && matrix[i][j + 1] == 'L') {
                    matrix[i][j] = 'T';
                    matrix[i][j + 1] = 'T';
                    continue;
                }

                if (curr == 'D' && matrix[i + 1][j] == 'U') {
                    matrix[i][j] = 'T';
                    matrix[i + 1][j] = 'T';
                }
            }
        }

        // 开始遍历所有的格子的走向，并标记 T 和 F
        int tCount = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                char curr = matrix[i][j];

                curr = move(matrix, curr, i, j);
                matrix[i][j] = curr;
                if (curr == 'T') {
                    tCount++;
                }
            }
        }

        System.out.println(tCount);
    }

    private static char move(char[][] matrix, char curr, int i, int j) {
        while (curr != 'T' && curr != 'F') {
            if (curr == 'L') {
                j--;
            } else if (curr == 'R') {
                j++;
            } else if (curr == 'U') {
                i--;
            } else {
                i++;
            }

            curr = matrix[i][j];
        }

        return curr;
    }
}
