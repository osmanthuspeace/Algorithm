package org.example;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Stack;

import static org.example.MySorts.less;

public class Algorithm {

    //TODO: 14. Longest Common Prefix
    public static void longestCommonPrefix(String[] strs) {
        for (String str : strs) {
            for (char c : str.toCharArray()) {
                System.out.println(c);
            }
        }
    }

    //双栈算术表达式求值
    public static int doubleStackArithmeticExpression(String expression) {
        Stack<Character> operators = new Stack<>();
        Stack<Integer> operands = new Stack<>();
        for (char c : expression.toCharArray()) {
            if (Character.isDigit(c)) {
                operands.push(Character.getNumericValue(c));
            } else if (c == '+' || c == '-' || (c == '*') || c == '/') {
                operators.push(c);
            }
            if (c == ')') {
                var operator = operators.pop();
                var operand2 = operands.pop(); // 注意顺序，先弹出的是第二个操作数
                var operand1 = operands.pop();
                if (operator == '+') {
                    operands.push(operand1 + operand2);
                } else if (operator == '-') {
                    operands.push(operand1 - operand2);
                } else if (operator == '*') {
                    operands.push(operand1 * operand2);
                } else if (operator == '/') {
                    operands.push(operand1 / operand2);
                }
            }
        }
        return operands.pop();
    }


    public static int TwoSumFast(int @NotNull [] arr) {
        var length = arr.length;
        int count = 0;

        for (int i = 0; i < length; i++) {
            if (arr[i] < 0) arr[i] = -arr[i];
        }
        Arrays.sort(arr);

        for (int i = 0; i < length - 1; i++) {
            if (arr[i] == arr[i + 1]) {
                count++;
                i++;
            }
        }
        return count;
    }

    public static int ThreeSumFast(int[] arr) {
        Arrays.sort(arr);
        var length = arr.length;
        int count = 0;

        //双指针实现
        for (int i = 0; i < length; i++) {
            var left = i + 1;
            var right = length - 1;
            while (left < right) {
                if (arr[i] + arr[left] + arr[right] == 0) {
                    count++;
                    left++;
                    right--;
                    while (arr[left] == arr[left - 1] && left < right) left++;//为了不重复计数，跳过重复的数字
                    while (arr[right] == arr[right + 1] && left < right) right--;
                } else if (arr[i] + arr[left] + arr[right] < 0) {
                    left++;
                } else {
                    right--;
                }
            }
        }
        return count;
    }


    public static <T extends Comparable<T>> void merge(T[] a, int lo, int mid, int hi) {
        T[] aux = Arrays.copyOf(a, a.length);

    }
}
