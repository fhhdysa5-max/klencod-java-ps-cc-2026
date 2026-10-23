package com.pss.cc; // تأكد أن هذا السطر يطابق اسم الباكج عندك

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tvDisplay;
    private String currentInput = "";
    private String operator = "";
    private double firstNumber = 0;
    private boolean isNewInput = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvDisplay = findViewById(R.id.tvDisplay);

        // ربط الأزرار
        int[] numberButtons = {R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9, R.id.btnDot};
        
        View.OnClickListener numberListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                if (isNewInput) {
                    currentInput = "";
                    isNewInput = false;
                }
                currentInput += b.getText().toString();
                tvDisplay.setText(currentInput);
            }
        };

        for (int id : numberButtons) {
            findViewById(id).setOnClickListener(numberListener);
        }

        // أزرار العمليات
        findViewById(R.id.btnAdd).setOnClickListener(v -> setOperator("+"));
        findViewById(R.id.btnSub).setOnClickListener(v -> setOperator("-"));
        findViewById(R.id.btnMul).setOnClickListener(v -> setOperator("*"));
        findViewById(R.id.btnDiv).setOnClickListener(v -> setOperator("/"));

        // زر يساوي
        findViewById(R.id.btnEqual).setOnClickListener(v -> calculate());

        // زر AC
        findViewById(R.id.btnAC).setOnClickListener(v -> {
            currentInput = "";
            operator = "";
            firstNumber = 0;
            tvDisplay.setText("0");
            isNewInput = true;
        });
        
        // زر الحذف
        findViewById(R.id.btnDel).setOnClickListener(v -> {
            if (currentInput.length() > 0) {
                currentInput = currentInput.substring(0, currentInput.length() - 1);
                tvDisplay.setText(currentInput.isEmpty() ? "0" : currentInput);
            }
        });
    }

    private void setOperator(String op) {
        if (!currentInput.isEmpty()) {
            firstNumber = Double.parseDouble(currentInput);
            operator = op;
            isNewInput = true;
        }
    }

    private void calculate() {
        if (!currentInput.isEmpty() && !operator.isEmpty()) {
            double secondNumber = Double.parseDouble(currentInput);
            double result = 0;
            switch (operator) {
                case "+": result = firstNumber + secondNumber; break;
                case "-": result = firstNumber - secondNumber; break;
                case "*": result = firstNumber * secondNumber; break;
                case "/": 
                    if (secondNumber != 0) result = firstNumber / secondNumber; 
                    else result = 0; 
                    break;
            }
            // تنسيق الناتج عشان ما يطلع أرقام عشرية طويلة
            if (result == (long) result) {
                tvDisplay.setText(String.valueOf((long) result));
            } else {
                tvDisplay.setText(String.valueOf(result));
            }
            currentInput = tvDisplay.getText().toString();
            operator = "";
            isNewInput = true;
        }
    }
}