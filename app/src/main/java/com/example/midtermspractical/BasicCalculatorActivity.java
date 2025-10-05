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

public class BasicCalculatorActivity extends AppCompatActivity {

    private Spinner spinner;
    private EditText editText;
    private TextView resultText;

    private Button addButton, subtractButton, multiplyButton, divideButton, equalButton, clearButton;
    private Button num1Button, num2Button, num3Button, num4Button;
    private Button num5Button, num6Button, num7Button, num8Button, num9Button, zeroButton, dotButton;

    private double num1, num2;
    private boolean isAddition, isSubtraction, isMultiplication, isDivision;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_calculator);

        // Spinner setup
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

        // UI references
        editText = findViewById(R.id.editText2);
        resultText = findViewById(R.id.resultText);

        addButton = findViewById(R.id.add);
        subtractButton = findViewById(R.id.sub);
        multiplyButton = findViewById(R.id.mul);
        divideButton = findViewById(R.id.div);
        equalButton = findViewById(R.id.submit);
        clearButton = findViewById(R.id.clear_text);

        num1Button = findViewById(R.id.num1);
        num2Button = findViewById(R.id.num2);
        num3Button = findViewById(R.id.num3);
        num4Button = findViewById(R.id.num4);
        num5Button = findViewById(R.id.num5);
        num6Button = findViewById(R.id.num6);
        num7Button = findViewById(R.id.num7);
        num8Button = findViewById(R.id.num8);
        num9Button = findViewById(R.id.num9);
        zeroButton = findViewById(R.id.zero);
        dotButton = findViewById(R.id.dot);

        // Setup calculator logic
        setupCalculatorLogic();
    }

    private void setupCalculatorLogic() {
        addButton.setOnClickListener(v -> {
            if (editText.getText().length() > 0) {
                num1 = Double.parseDouble(editText.getText().toString());
                isAddition = true;
                editText.setText("");
            }
        });

        subtractButton.setOnClickListener(v -> {
            if (editText.getText().length() > 0) {
                num1 = Double.parseDouble(editText.getText().toString());
                isSubtraction = true;
                editText.setText("");
            }
        });

        multiplyButton.setOnClickListener(v -> {
            if (editText.getText().length() > 0) {
                num1 = Double.parseDouble(editText.getText().toString());
                isMultiplication = true;
                editText.setText("");
            }
        });

        divideButton.setOnClickListener(v -> {
            if (editText.getText().length() > 0) {
                num1 = Double.parseDouble(editText.getText().toString());
                isDivision = true;
                editText.setText("");
            }
        });

        clearButton.setOnClickListener(v -> {
            editText.setText("");
            resultText.setText("0");
            isAddition = isSubtraction = isMultiplication = isDivision = false;
        });

        equalButton.setOnClickListener(v -> {
            if (editText.getText().length() > 0) {
                num2 = Double.parseDouble(editText.getText().toString());
                if (isAddition) resultText.setText(String.valueOf(num1 + num2));
                else if (isSubtraction) resultText.setText(String.valueOf(num1 - num2));
                else if (isMultiplication) resultText.setText(String.valueOf(num1 * num2));
                else if (isDivision) {
                    if (num2 != 0) resultText.setText(String.valueOf(num1 / num2));
                    else resultText.setText("Error");
                }
                isAddition = isSubtraction = isMultiplication = isDivision = false;
            }
        });

        // Number buttons
        num1Button.setOnClickListener(v -> editText.append("1"));
        num2Button.setOnClickListener(v -> editText.append("2"));
        num3Button.setOnClickListener(v -> editText.append("3"));
        num4Button.setOnClickListener(v -> editText.append("4"));
        num5Button.setOnClickListener(v -> editText.append("5"));
        num6Button.setOnClickListener(v -> editText.append("6"));
        num7Button.setOnClickListener(v -> editText.append("7"));
        num8Button.setOnClickListener(v -> editText.append("8"));
        num9Button.setOnClickListener(v -> editText.append("9"));
        zeroButton.setOnClickListener(v -> editText.append("0"));
        dotButton.setOnClickListener(v -> {
            if (!editText.getText().toString().contains(".")) editText.append(".");
        });
    }
}
