package org.example;

import java.util.Stack;

public class Algorithm {

    //TODO: 14. Longest Common Prefix
    public static void longestCommonPrefix(String[] strs){
        for (String str : strs) {
            for(char c : str.toCharArray()){
                System.out.println(c);
            }
        }
    }

    //双栈算术表达式求值
    public static int doubleStackArithmeticExpression(String expression){
        Stack<Character> operators = new Stack<>();
        Stack<Integer> operands = new Stack<>();
        for(char c : expression.toCharArray()){
            if(Character.isDigit(c)){
                operands.push(Character.getNumericValue(c));
            }else if(c=='+'||c=='-'||(c=='*')||c=='/'){
                operators.push(c);
            }
            if(c==')'){
                var operator = operators.pop();
                var operand2 = operands.pop(); // 注意顺序，先弹出的是第二个操作数
                var operand1 = operands.pop();
                if(operator=='+'){
                    operands.push(operand1+operand2);
                }else if(operator=='-'){
                    operands.push(operand1-operand2);
                }else if(operator=='*'){
                    operands.push(operand1*operand2);
                }else if(operator=='/'){
                    operands.push(operand1/operand2);
                }
            }
        }
        return operands.pop();
    }
}
