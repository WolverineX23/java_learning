package org.example.algorithm.WrittenTest.Baidu;

import java.util.*;

/**
 * 麻将 清一色：50%
 */
public class BDThree {
    private static int result = 0;
    private static Set<String> uniqueResults = new HashSet<>(); // 用来存储所有的合法组合

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();

        // 如果牌的总数不足14张，不可能胡牌
        if (n * 4 < 14) {
            System.out.println(0);
            return;
        }

        // 每种牌最多有4张
        int[] cards = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            cards[i] = 4;
        }

        // 尝试以每种牌作为对子
        for (int i = 1; i <= n; i++) {
            if (cards[i] >= 2) {  // 选择一个对子
                cards[i] -= 2;    // 减去两个对子牌
                List<String> currentCombo = new ArrayList<>();
                currentCombo.add(i + "" + i);  // 记录对子
                search(cards, 0, currentCombo, 1);  // 搜索剩余的面子
                cards[i] += 2;    // 回溯，恢复原状态
            }
        }

        // 输出总计种数
        System.out.println(uniqueResults.size());
    }

    // 递归搜索是否能构成四个面子，从 start 牌开始
    private static void search(int[] cards, int completedSets, List<String> currentCombo, int start) {
        // 如果已经凑齐4个面子，则为一个合法的胡牌组合
        if (completedSets == 4) {
            // 统计每张牌的数量，忽略顺序
            int[] count = new int[10];
            for (String s : currentCombo) {
                for (char c : s.toCharArray()) {
                    count[c - '0']++;
                }
            }

            // 将每张牌的数量作为字符串，构成唯一的组合表示
            StringBuilder sb = new StringBuilder();
            for (int i = 1; i < count.length; i++) {
                if (count[i] > 0) {
                    sb.append(i).append("x").append(count[i]).append(" ");
                }
            }

            uniqueResults.add(sb.toString().trim());  // 将基于数量的组合添加到Set中，确保唯一性
            return;
        }

        // 尝试找刻子（3张相同的牌）
        for (int i = start; i < cards.length; i++) {
            if (cards[i] >= 3) {  // 如果某个牌有至少3张
                cards[i] -= 3;    // 减去3张牌作为刻子
                currentCombo.add(i + "" + i + "" + i);  // 记录当前组合
                search(cards, completedSets + 1, currentCombo, i); // 继续搜索下一个面子
                currentCombo.remove(currentCombo.size() - 1);  // 回溯，移除当前组合
                cards[i] += 3;    // 回溯，恢复牌的数量
            }
        }

        // 尝试找顺子（连续的三张牌）
        for (int i = start; i <= cards.length - 3; i++) {
            if (cards[i] > 0 && cards[i + 1] > 0 && cards[i + 2] > 0) { // 如果三张连续的牌可以组成顺子
                cards[i]--;
                cards[i + 1]--;
                cards[i + 2]--;
                currentCombo.add(i + "" + (i + 1) + "" + (i + 2));  // 记录当前组合
                search(cards, completedSets + 1, currentCombo, i); // 继续搜索下一个面子
                currentCombo.remove(currentCombo.size() - 1);  // 回溯，移除当前组合
                // 回溯，恢复牌的数量
                cards[i]++;
                cards[i + 1]++;
                cards[i + 2]++;
            }
        }
    }
}
