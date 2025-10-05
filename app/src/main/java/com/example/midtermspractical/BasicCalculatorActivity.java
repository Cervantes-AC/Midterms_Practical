package com.example.midtermspractical;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Stack;

public class BasicCalculatorActivity extends AppCompatActivity {

    private Spinner spinner;
    private EditText editText;
    private TextView resultText;

    private Button addButton, subtractButton, multiplyButton, divideButton;
    private Button equalButton, clearButton, deleteButton;
    private Button num0, num1, num2, num3, num4, num5, num6, num7, num8, num9, dotButton;
    private Button openParen, closeParen;

    private boolean isFirstSelection = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_calculator);

        spinner = findViewById(R.id.Calculator_App);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.Calculator_App,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isFirstSelection) {
                    isFirstSelection = false;
                    return;
                }
                String selected = parent.getItemAtPosition(position).toString();
                if (selected.equals("Base Number Calculator")) {
                    startActivity(new Intent(BasicCalculatorActivity.this, BaseCalculatorActivity.class));
                } else if (selected.equals("Unit Converter")) {
                    startActivity(new Intent(BasicCalculatorActivity.this, UnitConverterActivity.class));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        editText = findViewById(R.id.editText2);
        resultText = findViewById(R.id.resultText);

        addButton = findViewById(R.id.add);
        subtractButton = findViewById(R.id.sub);
        multiplyButton = findViewById(R.id.mul);
        divideButton = findViewById(R.id.div);
        equalButton = findViewById(R.id.submit);
        clearButton = findViewById(R.id.clear_text);
        dotButton = findViewById(R.id.dot);

        num0 = findViewById(R.id.zero);
        num1 = findViewById(R.id.num1);
        num2 = findViewById(R.id.num2);
        num3 = findViewById(R.id.num3);
        num4 = findViewById(R.id.num4);
        num5 = findViewById(R.id.num5);
        num6 = findViewById(R.id.num6);
        num7 = findViewById(R.id.num7);
        num8 = findViewById(R.id.num8);
        num9 = findViewById(R.id.num9);

        deleteButton = findViewById(R.id.delete);
        openParen = findViewById(R.id.open_paren);
        closeParen = findViewById(R.id.close_paren);

        setupCalculatorLogic();
    }

    private void setupCalculatorLogic() {
        View.OnClickListener listener = v -> {
            Button b = (Button) v;
            editText.append(b.getText());
        };

        // Numbers
        num0.setOnClickListener(listener);
        num1.setOnClickListener(listener);
        num2.setOnClickListener(listener);
        num3.setOnClickListener(listener);
        num4.setOnClickListener(listener);
        num5.setOnClickListener(listener);
        num6.setOnClickListener(listener);
        num7.setOnClickListener(listener);
        num8.setOnClickListener(listener);
        num9.setOnClickListener(listener);
        dotButton.setOnClickListener(listener);

        // Operators
        addButton.setOnClickListener(listener);
        subtractButton.setOnClickListener(listener);
        multiplyButton.setOnClickListener(v -> editText.append("*"));
        divideButton.setOnClickListener(v -> editText.append("/"));
        openParen.setOnClickListener(listener);
        closeParen.setOnClickListener(listener);

        // Clear all
        clearButton.setOnClickListener(v -> {
            editText.setText("");
            resultText.setText("0");
        });

        // Delete last character
        deleteButton.setOnClickListener(v -> {
            String current = editText.getText().toString();
            if (!current.isEmpty()) {
                editText.setText(current.substring(0, current.length() - 1));
            }
        });

        // Evaluate
        equalButton.setOnClickListener(v -> {
            String expression = editText.getText().toString();
            try {
                double result = evaluateExpression(expression);
                if (result == Math.floor(result)) {
                    resultText.setText(String.valueOf((long) result)); // whole number
                } else {
                    resultText.setText(String.valueOf(result)); // decimal
                }
            } catch (Exception e) {
                resultText.setText("Error");
            }
        });
    }

    private double evaluateExpression(String expression) {
        return evaluatePostfix(infixToPostfix(expression));
    }

    private String infixToPostfix(String exp) {
        StringBuilder result = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        for (char c : exp.toCharArray()) {
            if (Character.isDigit(c) || c == '.') {
                result.append(c);
            } else if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    result.append(' ').append(stack.pop());
                }
                stack.pop();
            } else if ("+-*/".indexOf(c) != -1) {
                result.append(' ');
                while (!stack.isEmpty() && precedence(stack.peek()) >= precedence(c)) {
                    result.append(stack.pop()).append(' ');
                }
                stack.push(c);
            }
        }

        while (!stack.isEmpty()) {
            result.append(' ').append(stack.pop());
        }

        return result.toString();
    }

    private double evaluatePostfix(String postfix) {
        Stack<Double> stack = new Stack<>();
        String[] tokens = postfix.split("\\s+");

        for (String token : tokens) {
            if (token.isEmpty()) continue;
            if ("+-*/".contains(token)) {
                double b = stack.pop();
                double a = stack.pop();
                switch (token) {
                    case "+": stack.push(a + b); break;
                    case "-": stack.push(a - b); break;
                    case "*": stack.push(a * b); break;
                    case "/": stack.push(a / b); break;
                }
            } else {
                stack.push(Double.parseDouble(token));
            }
        }

        return stack.pop();
    }

    private int precedence(char op) {
        if (op == '+' || op == '-') return 1;
        if (op == '*' || op == '/') return 2;
        return 0;
    }
}
