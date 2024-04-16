package org.example.DisperseExperiments;

import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

/**
 * author: ouyangguiping
 * createTime: 2024/4/11
 */
public class Experiment1 {

    private final HashMap<Character, Integer> mapTable = new HashMap<>();//将字母映射成数字
    private final String expression;
    private final StringBuffer principalConjunctiveNormalForm;//主合取范式
    private final StringBuilder principalDisjunctiveNormalForm;//主析取范式

    public Experiment1(String expression) {
        this.expression = expression;
        this.principalDisjunctiveNormalForm = new StringBuilder();
        this.principalConjunctiveNormalForm = new StringBuffer();
    }

    private void getAlpha() {//获取公式中的字母
        for (var i : this.expression.toCharArray()) {
            if (Character.isAlphabetic(i)) {
                mapTable.put(i, 0);//获取字母
            } else {
                mapTable.put(i, -1);
            }
        }
    }

    /**
     * @param n 变元个数
     */
    private void Assignment(int n) {
        if (n == 1) {
            for (int i = 0; i <= 1; i++) {
                mapTable.put('P', i);
                System.out.print(i + "  ");
                var result = parse() ? 1 : 0;
                System.out.println(result);
                if (result == 1) {
                    if (i == 1) principalDisjunctiveNormalForm.append("P");
                    else principalDisjunctiveNormalForm.append("!P");
                } else {
                    if (i == 0) principalConjunctiveNormalForm.append("P");
                    else principalConjunctiveNormalForm.append("!P");
                }
            }
        }
        if (n == 2) {
            for (int i = 0; i <= 1; i++) {
                for (int j = 0; j <= 1; j++) {
                    mapTable.put('P', i);
                    mapTable.put('Q', j);
                    System.out.print(i + "  " + j + "  ");
                    var result = parse() ? 1 : 0;
                    System.out.println(result);
                    if (result == 1) {
                        if (i == 1) principalDisjunctiveNormalForm.append("(P&");
                        else principalDisjunctiveNormalForm.append("(!P&");
                        if (j == 1) principalDisjunctiveNormalForm.append("Q)|");
                        else principalDisjunctiveNormalForm.append("!Q)|");
                    } else {
                        if (i == 0) principalConjunctiveNormalForm.append("(P|");
                        else principalConjunctiveNormalForm.append("(!P|");
                        if (j == 0) principalConjunctiveNormalForm.append("Q)&");
                        else principalConjunctiveNormalForm.append("!Q)&");
                    }
                }
            }
        }
        if (n == 3) {
            for (int i = 0; i <= 1; i++) {
                for (int j = 0; j <= 1; j++) {
                    for (int k = 0; k <= 1; k++) {
                        mapTable.put('P', i);
                        mapTable.put('Q', j);
                        mapTable.put('R', k);
                        System.out.print(i + "  " + j + "  " + k + "  ");
                        var result = parse() ? 1 : 0;
                        System.out.println(result);
                        if (result == 1) {
                            if (i == 1) principalDisjunctiveNormalForm.append("(P&");
                            else principalDisjunctiveNormalForm.append("(!P&");
                            if (j == 1) principalDisjunctiveNormalForm.append("Q&");
                            else principalDisjunctiveNormalForm.append("!Q&");
                            if (k == 1) principalDisjunctiveNormalForm.append("R)|");
                            else principalDisjunctiveNormalForm.append("!R)|");
                        } else {
                            if (i == 0) principalConjunctiveNormalForm.append("(P|");
                            else principalConjunctiveNormalForm.append("(!P|");
                            if (j == 0) principalConjunctiveNormalForm.append("Q|");
                            else principalConjunctiveNormalForm.append("!Q|");
                            if (k == 0) principalConjunctiveNormalForm.append("R)&");
                            else principalConjunctiveNormalForm.append("!R)&");
                        }
                    }
                }
            }
        }
    }

    private Boolean parse() {
        Stack<Boolean> operand = new Stack<>();
        Stack<Character> operator = new Stack<>();

        char[] chars = expression.toCharArray();
        for (int i = chars.length - 1; i >= 0; i--) {
            char c = chars[i];
            if (c == 'P' || c == 'Q' || c == 'R') {
                operand.push(mapTable.get(c) == 1);
            } else operator.push(c);
        }
        while (!operator.isEmpty()) {
            var c = operator.pop();
            switch (c) {
                case '|':
                    var disjunction1 = operand.pop();
                    var disjunction2 = operand.pop();
                    operand.push(disjunction1 || disjunction2);
                    break;
                case '&':
                    var conjunction1 = operand.pop();
                    var conjunction2 = operand.pop();
                    operand.push(conjunction1 && conjunction2);
                    break;
                case '!':
                    var not = operand.pop();
                    operand.push(!not);
                    break;
                case '>':
                    var condition1 = operand.pop();
                    var condition2 = operand.pop();
                    if (condition1 && !condition2)
                        operand.push(false);
                    else operand.push(true);
                    break;
                case '-':
                    var bicondition1 = operand.pop();
                    var bicondition2 = operand.pop();
                    if (bicondition2 == bicondition1) operand.push(true);
                    else operand.push(false);
                    break;
                default:
                    throw new IllegalArgumentException("公式有误");
            }
        }
        return operand.pop();
    }

    public void experiment1() {

        getAlpha();
        int counter = 0;
        for (var entry : mapTable.entrySet()) {
            if (entry.getValue() == 0) {
                System.out.print(entry.getKey() + "  ");
                counter++;
            }
        }

        System.out.println(expression);//输出表头
        Assignment(counter);

        principalDisjunctiveNormalForm.deleteCharAt(principalDisjunctiveNormalForm.length() - 1);
        principalConjunctiveNormalForm.deleteCharAt(principalConjunctiveNormalForm.length() - 1);

        System.out.println("该合式公式的主析取范式为：");
        System.out.println(principalDisjunctiveNormalForm);
        System.out.println("该合式公式的主合取范式为：");
        System.out.println(principalConjunctiveNormalForm);

    }

    public static void main(String[] args) {
        System.out.println("请按连接词优先级从高到低的顺序输入含三个以内变量的合式公式(请使用P,Q,R作为变元名)：");
        var s = new Scanner(System.in);
        var expression = s.nextLine();
        System.out.println("该合式公式的真值表如下：");
        var exp = new Experiment1(expression);
        exp.experiment1();
    }
}
