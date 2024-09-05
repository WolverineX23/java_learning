package org.example.algorithm.WrittenTest.TencentMusic;

import java.util.*;

public class TMOne {

    public static ArrayList<Integer> newArray(ArrayList<Integer> a) {

        ArrayList<Integer> resArray = new ArrayList<>();

        for (int i = 0; i < a.size(); i++) {
            int resNum = removeTwo(a.get(i));
            if (resNum != -1) {
                resArray.add(resNum);
            }
        }

        return resArray;
    }

    private static int removeTwo(int num) {

        String str = String.valueOf(num);

        StringBuilder numStr = new StringBuilder();
        numStr.append(str);

        // 去 2
        for (int i = 0; i < numStr.length();) {
            if (numStr.charAt(i) == '2') {
                numStr.deleteCharAt(i);
            } else {
                i++;
            }
        }

        // 去前面的 0
        while (numStr.length() > 1 && numStr.charAt(0) == '0') {
            numStr.deleteCharAt(0);
        }

        return numStr.length() == 0 ? -1 : Integer.parseInt(numStr.toString());
    }

    public static void main(String[] args) {
        ArrayList<Integer> a = new ArrayList<>();
//        a.add(12212);
//        a.add(58);
//        a.add(2001);

        // [5,12,20,2,77]
        a.add(5);
        a.add(12);
        a.add(20);
        a.add(2);
        a.add(77);

        System.out.println(newArray(a));
    }
}
