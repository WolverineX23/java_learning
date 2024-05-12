package org.example.algorithm.WrittenTest.HuaWeiTest;

import java.util.*;

/**
 * 当时考试时代码：踢足球排序
 * getFluentCount方法中 少了一行代码就对了
 * 傻波依用 TreeSort 做了，花了好多时间
 *
 */
public class HuaTest {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        int n = in.nextInt();   // 球员数
        int m = in.nextInt();   // 每个球员出球数

        in.nextLine();
        String str = in.nextLine();
//        List<String> list = Arrays.asList(str.split(" ").clone());
        String[] list = str.split(" ");

        boolean first = true;

        TreeMap<Integer, List<Integer>> maps = new TreeMap<>();
        for (int i = 0; i < list.length; i++) {

            // 获取进球数
            String inStr = list[i];
            int count = getInCount(inStr);
            if (maps.containsKey(count)) {
                maps.get(count).add(i); // 若存在，直接添加
            } else {
                List<Integer> ids = new ArrayList<>();
                ids.add(i);
                maps.put(count, ids);
            }
        }

        // 对所有相同进球数的球员进行特殊排序
        while (maps.size() > 0) {
            Map.Entry<Integer, List<Integer>> maxEntry = maps.pollLastEntry();
            List<Integer> ids = maxEntry.getValue();

            TreeMap<Integer, List<Integer>> maps2 = new TreeMap<>();
            // 根据连续进球数进行分组
            for (int i = 0; i < ids.size(); i++) {
                int id = ids.get(i);
                int count = getFluentCount(list[id]);
                if (maps2.containsKey(count)) {
                    maps2.get(count).add(id); // 若存在，直接添加id
                } else {
                    List<Integer> ids2 = new ArrayList<>();
                    ids2.add(id);
                    maps2.put(count, ids2);
                }
            }

            // 对相同的连续进球数的球员进行排序
            while (maps2.size() > 0) {
                Map.Entry<Integer, List<Integer>> mapEntry2 = maps2.pollLastEntry();
                List<Integer> ids2 = mapEntry2.getValue();

                // 根据谁最先进球谁在最前 str1.compareTo(str2)
                ids2.sort(new Comparator<Integer>() {
                    @Override
                    public int compare(Integer o1, Integer o2) {
                        if (list[o2].compareTo(list[o1]) > 0) {
                            return 1;
                        } else if (list[o2].compareTo(list[o1]) < 0) {
                            return -1;
                        } else {
                            return o1 - o2;
                        }
                    }
                });

                // 输出
                for (int k = 0; k < ids2.size(); k++) {
                    int trueId = ids2.get(k) + 1;
                    if (first) {
                        System.out.print(trueId);
                        first = false;
                    } else {
                        System.out.print(" " + trueId);
                    }
                }
            }
        }
    }

    // 获取进球的数量
    private static int getInCount(String str) {
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '1')
                count++;
        }

        return count;
    }

    // 获取最高连续进球数
    private static int getFluentCount(String str) {
        int count = 0;
        int tmp = 0;
        if (str.charAt(0) == '1')
            tmp = 1;
        for (int i = 1; i < str.length(); i++) {
            if (str.charAt(i) == '1') {
                if (str.charAt(i - 1) == '1')
                    tmp++;
                else {
                    tmp = 1;
                }
            } else {
                count = Math.max(count, tmp);
                tmp = 0;
            }
        }
        count = Math.max(count, tmp);   // 少了这行代码

        return count;
    }

}
