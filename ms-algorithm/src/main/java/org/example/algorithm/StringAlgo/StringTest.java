package org.example.algorithm.StringAlgo;

public class StringTest {

    private static final int num = 0;
    private static final StringBuilder res = new StringBuilder("init");

    public static void main(String[] args) {
        // substring
        String a = "beauty";
//        a.substring(1, 3);  // 左闭右开 - ea
        System.out.println("a: " + a.substring(1, 1));

//        num = 10;                 // 基本数据类型对象的值不能被修改

        res.append(" add word");    // 对于引用类型对象，其引用不能修改，但其所指向的对象的内容可以改变
//        res = new StringBuilder("new Reference");
        System.out.println("res: " + res);
    }
}
