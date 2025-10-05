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

public class BaseCalculatorActivity extends AppCompatActivity {

    private EditText inputNumber;
    private Spinner fromSpinner, toSpinner, mainSpinner;
    private TextView resultText;
    private Button convertBtn;

    private final String[] bases = {"Decimal", "Binary", "Octal", "Hexadecimal"};
    private boolean userSelect = false; // flag to detect user interaction

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_calculator);

        inputNumber = findViewById(R.id.inputNumber);
        fromSpinner = findViewById(R.id.fromSpinner);
        toSpinner = findViewById(R.id.toSpinner);
        resultText = findViewById(R.id.resultText);
        convertBtn = findViewById(R.id.btnConvert);
        mainSpinner = findViewById(R.id.Calculator_App);

        // Setup base spinners
        ArrayAdapter<String> baseAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, bases);
        baseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromSpinner.setAdapter(baseAdapter);
        toSpinner.setAdapter(baseAdapter);

        convertBtn.setOnClickListener(view -> convertNumber());

        // Setup top main spinner
        ArrayAdapter<CharSequence> mainAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.Calculator_App,
                android.R.layout.simple_spinner_item
        );
        mainAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mainSpinner.setAdapter(mainAdapter);

        // Set the spinner to show "Base Number Calculator" initially
        mainSpinner.setSelection(getSpinnerPosition(mainSpinner, "Base Number Calculator"));

        // Detect user interaction
        mainSpinner.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

        mainSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!userSelect) return; // ignore programmatic selection

                String selected = parent.getItemAtPosition(position).toString();

                switch (selected) {
                    case "Base Number Calculator":
                        // already here
                        break;
                    case "Basic Calculator":
                        startActivity(new Intent(BaseCalculatorActivity.this, BasicCalculatorActivity.class));
                        break;
                    case "Unit Converter":
                        startActivity(new Intent(BaseCalculatorActivity.this, UnitConverterActivity.class));
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private int getSpinnerPosition(Spinner spinner, String value) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equals(value)) return i;
        }
        return 0;
    }

    private void convertNumber() {
        String input = inputNumber.getText().toString().trim();

        if (input.isEmpty()) {
            resultText.setText("Please enter a number!");
            return;
        }

        String fromBase = fromSpinner.getSelectedItem().toString();
        String toBase = toSpinner.getSelectedItem().toString();

        try {
            int decimalValue;

            switch (fromBase) {
                case "Binary":
                    decimalValue = Integer.parseInt(input, 2);
                    break;
                case "Octal":
                    decimalValue = Integer.parseInt(input, 8);
                    break;
                case "Hexadecimal":
                    decimalValue = Integer.parseInt(input, 16);
                    break;
                default:
                    decimalValue = Integer.parseInt(input);
            }

            String result;
            switch (toBase) {
                case "Binary":
                    result = Integer.toBinaryString(decimalValue);
                    break;
                case "Octal":
                    result = Integer.toOctalString(decimalValue);
                    break;
                case "Hexadecimal":
                    result = Integer.toHexString(decimalValue).toUpperCase();
                    break;
                default:
                    result = String.valueOf(decimalValue);
            }

            resultText.setText("Result: " + result);

        } catch (NumberFormatException e) {
            resultText.setText("Invalid input for " + fromBase);
        }
    }
}
