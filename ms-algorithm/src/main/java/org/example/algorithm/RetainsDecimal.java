package org.example.algorithm;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class RetainsDecimal {

    public static void main(String[] args) {

        // printf 输出
        double num0 = 123.456789;
        System.out.printf("%.3f", num0);

        System.out.println();

        // String.format
        double num1 = 123.456789;
        String formattedNum = String.format("%.3f", num1);
        double numRes = Double.parseDouble(formattedNum);
        System.out.println(numRes);

        // DecimalFormat
        double num2 = 123.456789;
        DecimalFormat decimalFormat = new DecimalFormat("#.###");
        String formattedNum2 = decimalFormat.format(num2);
        System.out.println(formattedNum2);

        // BigDecimal
        double num3 = 123.456789;
        BigDecimal oriDecimal = new BigDecimal(num3);
        BigDecimal resDecimal = oriDecimal.setScale(3, RoundingMode.HALF_UP);
        System.out.println(resDecimal);
    }
}
