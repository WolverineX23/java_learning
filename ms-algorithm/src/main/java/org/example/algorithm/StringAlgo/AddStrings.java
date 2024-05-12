package org.example.algorithm.StringAlgo;

/**
 * 十进制 字符串相加
 *
 */
public class AddStrings {

    public String addStrings(String num1, String num2) {

        StringBuilder res = new StringBuilder();
        int i = num1.length() - 1, j = num2.length() - 1, carry = 0;    // carry: 进位

        while (i >= 0 || j >= 0) {
            int n1 = i >= 0 ? num1.charAt(i) - '0' : 0;
            int n2 = j >= 0 ? num2.charAt(j) - '0' : 0;

            int tmp = n1 + n2 + carry;
            carry = tmp / 10;
            res.append(tmp % 10);
            i--;
            j--;
        }

        if (carry == 1)
            res.append(1);

        return res.reverse().toString();
    }

}
