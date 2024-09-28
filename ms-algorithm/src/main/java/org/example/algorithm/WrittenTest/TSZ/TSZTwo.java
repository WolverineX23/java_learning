package org.example.algorithm.WrittenTest.TSZ;

import java.util.Scanner;

/**
 * 360 编程题2【修复方程】：原方程本身成立或添加一个 0-9 的数，使得表达式成立
 *
 * input：
 * 6
 * 16=1+2*3
 * 7*8*9=54
 * 1+1=1+22
 * 4*6=22+2
 * 15+7=1+2
 * 11+1=1+5
 *
 * output:
 * Yes
 * Yes
 * No
 * Yes
 * Yes
 * No
 */
public class TSZTwo {
    // 计算表达式的值（不含括号运算，按从左到右顺序）
    public static int evaluate(String expression) {
        String[] tokens = expression.split(" ");
        int result = Integer.parseInt(tokens[0]);
        for (int i = 1; i < tokens.length; i += 2) {
            String operator = tokens[i];
            int operand = Integer.parseInt(tokens[i + 1]);
            if (operator.equals("+")) {
                result += operand;
            } else if (operator.equals("*")) {
                result *= operand;
            }
        }
        return result;
    }

    // 检查能否通过插入一个数字使方程成立
    public static boolean canMakeEquationValid(String equation) {
        // 将等号两边分割为左表达式和右表达式
        String[] parts = equation.split("=");
        if (parts.length != 2) return false; // 如果格式不对，直接返回 false

        // 提取左表达式和右表达式
        String leftExpression = parts[0].trim();
        int rightValue = evaluate(parts[1].trim());

        // 计算初始的左表达式值
        int originalLeftValue = evaluate(leftExpression);

        // 如果左右两边原本就相等，则直接返回 Yes
        if (originalLeftValue == rightValue) {
            return true;
        }

        // 如果左右两边差距超过1000，直接返回 No（无法通过插入1-1000来调整）
        int diff = Math.abs(rightValue - originalLeftValue);
        if (diff > 1000) {
            return false;
        }

        // 拆分左表达式为操作数和操作符列表
        String[] leftTokens = leftExpression.split(" ");

        // 在左表达式中尝试插入一个数字
        for (int i = 0; i <= leftTokens.length; i += 2) { // 只在操作数位置插入
            // 根据差值来限制插入数字的范围，避免不必要的遍历
            for (int d = 1; d <= 1000; d++) {
                // 构造新的左表达式
                StringBuilder newExpression = new StringBuilder();
                for (int j = 0; j < i; j++) {
                    newExpression.append(leftTokens[j]).append(" ");
                }
                newExpression.append(d); // 插入数字 d
                if (i < leftTokens.length) {
                    newExpression.append(" ");
                }
                for (int j = i; j < leftTokens.length; j++) {
                    newExpression.append(leftTokens[j]);
                    if (j < leftTokens.length - 1) {
                        newExpression.append(" ");
                    }
                }

                // 计算新的左表达式值并验证
                int newValue = evaluate(newExpression.toString());
                if (newValue == rightValue) {
                    return true; // 找到一种方案使得等式成立
                }
            }
        }

        return false; // 所有可能插入方案都无法使等式成立
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int T = Integer.parseInt(scanner.nextLine().trim()); // 读取测试用例个数

        for (int t = 0; t < T; t++) {
            String equation = scanner.nextLine().trim(); // 读取每个方程
            if (canMakeEquationValid(equation)) {
                System.out.println("Yes");
            } else {
                System.out.println("No");
            }
        }
        scanner.close();
    }
}
