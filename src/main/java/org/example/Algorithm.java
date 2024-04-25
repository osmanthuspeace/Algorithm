package org.example;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.IndexMinPQ;
import org.jetbrains.annotations.NotNull;

import java.util.*;

//Record the study progress of the Algorithm
public class Algorithm {

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
            if (arr[i] < 0) arr[i] = -arr[i];//取绝对值
        }
        Arrays.sort(arr);

        for (int i = 0; i < length - 1; i++) {
            if (arr[i] == arr[i + 1]) {//有相等的，则是取完绝对值后的（原数组应该没有重复数字）
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

    //多路归并排序，使用优先队列多向归并
    public static void mergeByPQ(Iterator<String>[] streams) {
        int N = streams.length;
        IndexMinPQ<String> pq = new IndexMinPQ<>(N);

        for (int i = 0; i < N; i++) {
            if (streams[i].hasNext())
                pq.insert(i, streams[i].next());
        }//初始化优先队列，使其包含了每个非空流的第一个元素
        while (!pq.isEmpty()) {
            System.out.print(pq.minKey()+" ");
            int i = pq.delMin();//删除并获取拥有当前最小元素的流的索引
            if (streams[i].hasNext()) {//检查最小的元素的流是否还有更多元素
                pq.insert(i, streams[i].next());
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        var s = new Scanner(System.in);
        List<String> list1 = Arrays.asList("a", "e", "i");
        List<String> list2 = Arrays.asList("b", "f", "j");
        List<String> list3 = Arrays.asList("c", "g", "k");
        List<String> list4 = Arrays.asList("d", "h", "l");

        @SuppressWarnings("unchecked")
        Iterator<String>[] streams = (Iterator<String>[]) new Iterator<?>[4];
        streams[0] = list1.iterator();
        streams[1] = list2.iterator();
        streams[2] = list3.iterator();
        streams[3] = list4.iterator();
        mergeByPQ(streams);
    }
}
