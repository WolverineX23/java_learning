package org.example.algorithm.WrittenTest.RedBook2;
import java.util.*;

/**
 * 小红的文章匹配：100%
 *
 * 这里用两个 for 循环，只能过 45%
 */
public class RBOne2 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int k = in.nextInt();
        long[] likes = new long[n];

        // 读取点赞数
        for (int i = 0; i < n; i++) {
            likes[i] = in.nextLong();
        }

        // 哈希表用于统计每个点赞数的出现次数
        Map<Long, Long> countMap = new HashMap<>();
        long pairCount = 0;

        // 遍历点赞数
        for (long like : likes) {
            // 计算与当前点赞数相似的另一个点赞数
            long target = like ^ k;

            // 如果目标点赞数存在，则说明形成相似对
            if (countMap.containsKey(target)) {
                pairCount += countMap.get(target);
            }

            // 更新当前点赞数的计数
            countMap.put(like, countMap.getOrDefault(like, 0L) + 1);
        }

        // 输出相似文章对的总数
        System.out.println(pairCount);
        in.close();
    }
}
