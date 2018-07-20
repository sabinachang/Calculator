package com.example.sabina.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    private int[] numericBtn = {R.id.btnOne, R.id.btnTwo,R.id.btnThree,R.id.btnFour,R.id.btnFive,R.id.btnSix,R.id.btnSeven,R.id.btnEight,R.id.btnNine,R.id.btnZero};
    private int[] operatorBtn = {R.id.btnMinus,R.id.btnTime,R.id.btnPlus,R.id.btnDivide};
    private Stack<Integer> negativePosition = new Stack<Integer>();
    private int lastOpPosition = -1;
    private int opPosition = 0;
    private int inputCount = 0;
    private boolean stateError = false;
    private boolean lastNumeric = false;
    private boolean lastDot = false;
    private boolean lastPercent = false;
    private boolean lastChangeSign = false;
    private TextView answerTextView ;
    private TextView expressionTextView;
    private boolean expCLR = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        expressionTextView = findViewById(R.id.expressionTextView);
        answerTextView = findViewById(R.id.answerTextView);
        answerTextView.setText("0");
        setNumbericBtnListener();
        setOperaterBtnListener();
    }



    private void setNumbericBtnListener() {

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button button = (Button) view;

                if(expCLR){
                    expressionTextView.setText("");
                    expCLR = false;
                }
                if(stateError){
                   expressionTextView.append(button.getText());
                   stateError = false;
                } else {
                    expressionTextView.append(button.getText());
                }
                lastNumeric = true;
                lastPercent = false;
                lastDot = false;
                inputCount += 1;
            }

        };

        for(int id : numericBtn){
            findViewById(id).setOnClickListener(listener);
        }

    }
    private void setOperaterBtnListener() {

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(expCLR){
                    expressionTextView.setText("");
                    expCLR = false;
                }
                if((lastNumeric || lastPercent) && !stateError){
                    Button button = (Button) view;
                    expressionTextView.append(button.getText());
                    lastNumeric = false;
                    lastDot = false;
                    inputCount += 1;
                    opPosition = inputCount;
                }
            }
        };

        for(int id : operatorBtn){
            findViewById(id).setOnClickListener(listener);
        }

        findViewById(R.id.btnReset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expressionTextView.setText("");
                lastNumeric = false;
                stateError = false;
                lastDot = false;
                lastPercent = false;
                inputCount = 0;
                opPosition = 0;
            }
        });

        findViewById(R.id.btnPoint).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(expCLR){
                    expressionTextView.setText("");
                    expCLR = false;
                }
                if(lastNumeric && !lastDot && !lastPercent && !stateError){
                    Button button = (Button) view;
                    expressionTextView.append(button.getText());
                    lastDot = true;
                    lastNumeric = false;
                    inputCount += 1;
                }
            }
        });

        findViewById(R.id.btnEqual).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    if(inputCount > 0){
                        String infixExpr = expressionTextView.getText().toString();
                        answerTextView.setText(Evaluate.evaluate(infixExpr,negativePosition));
                        expCLR = true;
                        while(!negativePosition.empty())
                            negativePosition.pop();
                        lastNumeric = false;
                        stateError = false;
                        lastDot = false;
                        lastPercent = false;
                        inputCount = 0;
                        opPosition = 0;
                    }
             }
        });

        findViewById(R.id.btnChangesign).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(expCLR){
                    expressionTextView.setText("");
                    expCLR = false;
                }
                if( lastOpPosition != opPosition){
                    lastOpPosition = opPosition;
                    negativePosition.push(opPosition);
                    addNegativeSign(expressionTextView.getText().toString(),opPosition);
                    inputCount +=  1;

                }
//                else if (lastChangeSign){
//                    lastChangeSign
//                }
            }
        });

        findViewById(R.id.btnPercent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"No function for % yet",Toast.LENGTH_LONG).show();
                if(lastNumeric && !lastDot && !lastPercent && !stateError){
                    Button button = (Button) view;

//                    expressionTextView.append(button.getText());
//                    lastPercent = true;
//                    lastNumeric = false;

                }
            }
        });
    }

    private void addNegativeSign(String exp,int position){

        exp = new StringBuilder(exp).insert(position,"-").toString();
        expressionTextView.setText(exp);
        return;
    }
}
