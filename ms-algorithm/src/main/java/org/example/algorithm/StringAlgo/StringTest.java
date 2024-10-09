package org.example.algorithm.StringAlgo;

public class StringTest {

    private static final int num = 0;
    private static final StringBuilder res = new StringBuilder("init");

    public static String addStrings(String num1, String num2) {
        int ind1 = num1.length() - 1;
        int ind2 = num2.length() - 1;
        StringBuilder res = new StringBuilder();
        int carry = 0;
        while (ind1 >= 0 || ind2 >= 0) {
            int a = ind1 < 0 ? 0 : (num1.charAt(ind1) - '0');
            int b = ind2 < 0 ? 0 : (num2.charAt(ind2) - '0');

            res.append((a + b + carry) % 10);
            carry = (a + b + carry) / 10;
            ind1--;
            ind2--;
        }

        if (carry != 0) {
            res.append(carry);
        }

        res.reverse();

        return res.toString();
    }

    public static void main(String[] args) {
        // substring
        String a = "beauty";
//        a.substring(1, 3);  // 左闭右开 - ea
        System.out.println("a: " + a.substring(1, 1));

//        num = 10;                 // 基本数据类型对象的值不能被修改

        res.append(" add word");    // 对于引用类型对象，其引用不能修改，但其所指向的对象的内容可以改变
//        res = new StringBuilder("new Reference");
        System.out.println("res: " + res);

        System.out.println(addStrings("999", "11"));
    }
}
