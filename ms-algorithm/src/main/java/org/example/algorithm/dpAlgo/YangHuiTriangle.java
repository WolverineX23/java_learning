package org.example.algorithm.dpAlgo;

import java.util.ArrayList;
import java.util.List;

/**
 * 杨辉三角
 */
public class YangHuiTriangle {

    public static List<List<Integer>> generate(int numRows) {
        List<List<Integer>> res = new ArrayList<>();

        for (int i = 1; i <= numRows; i++) {
            List<Integer> row = new ArrayList<>(i);
            for (int j = 0; j < i; j++) {
                if (j == 0 || j == i - 1) {
                    row.add(1);
                } else {
                    row.add(res.get(i - 2).get(j - 1) + res.get(i - 2).get(j));
                }
            }
            res.add(row);
        }

        return res;
    }

    public static void main(String[] args) {
        System.out.println(generate(6));
    }
}
