package com.example.sabina.calculator;

import android.util.Log;

import java.util.Stack;

public class Evaluate {
    public static String evaluate(String infixExpr,Stack<Integer>nP){
        char[] tokens = infixExpr.toCharArray();
        Stack<Double> values = new Stack<Double>();
        Stack<Character> ops = new Stack<Character>();
        Stack<Integer> negPos = nP;
        int i = 0;
        double ans;
        boolean negValue = false;


        while(i < tokens.length){
            if(!negPos.isEmpty() && negPos.peek() == i){
                negPos.pop();
                negValue = true;
                i++;
            }


            if(tokens[i] >= '0' && tokens[i] <= '9' ){
                StringBuffer value = new StringBuffer();
                while(i < tokens.length  && ((tokens[i]>='0'&& tokens[i]<='9')||tokens[i] == '.') ){
                    value.append(tokens[i++]);
                }
                if(negValue){
                    value.insert(0,"-");
                    negValue = false;
                }

                values.push(Double.parseDouble(value.toString()));
                Log.d("Evaluate:   ",value.toString());

            } else if(tokens[i] == '+' || tokens[i] == '-' ||
                    tokens[i] == '*' || tokens[i] == '÷' ){
                while(!ops.empty() && hasPrecedence(tokens[i],ops.peek())){
                   values.push(applyOp(ops.pop(),values.pop(),values.pop()));

                }
                ops.push(tokens[i++]);
            }

        }

        while(!ops.empty())
            values.push(applyOp(ops.pop(),values.pop(),values.pop()));

        if (( ans = values.pop()) % 1.0 != 0)
            return String.format("%s", ans);
        else
            return String.format("%.0f",ans);


    }

    private static boolean hasPrecedence(char op1, char op2)
    {
        if ((op1 == '*' || op1 == '÷') && (op2 == '+' || op2 == '-'))
            return false;
        else
            return true;
    }

    private static double applyOp(char op, double b, double a)
    {
        switch (op)
        {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '÷':
                return a / b;
        }
        return 0;
    }

}
