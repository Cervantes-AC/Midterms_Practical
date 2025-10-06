package com.example.midtermspractical;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
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
    private Spinner fromSpinner, mainSpinner;
    private TextView resultDecimal, resultBinary, resultOctal, resultHex;
    private Button convertBtn;

    private final String[] bases = {"Decimal", "Binary", "Octal", "Hexadecimal"};
    private boolean userSelect = false; // flag to detect user interaction

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_calculator);

        inputNumber = findViewById(R.id.inputNumber);
        fromSpinner = findViewById(R.id.fromSpinner);
        mainSpinner = findViewById(R.id.Calculator_App);
        convertBtn = findViewById(R.id.btnConvert);

        resultDecimal = findViewById(R.id.resultDecimal);
        resultBinary = findViewById(R.id.resultBinary);
        resultOctal = findViewById(R.id.resultOctal);
        resultHex = findViewById(R.id.resultHex);

        // Setup base spinner
        ArrayAdapter<String> baseAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, bases);
        baseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromSpinner.setAdapter(baseAdapter);

        convertBtn.setOnClickListener(view -> convertNumber());

        // Setup top main spinner
        ArrayAdapter<CharSequence> mainAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.Calculator_App,
                android.R.layout.simple_spinner_item
        );
        mainAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mainSpinner.setAdapter(mainAdapter);

        // Set spinner to show "Base Number Calculator" initially
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
                userSelect = false; // reset flag
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
            resultDecimal.setText("-");
            resultBinary.setText("-");
            resultOctal.setText("-");
            resultHex.setText("-");
            return;
        }

        String fromBase = fromSpinner.getSelectedItem().toString();

        try {
            int decimalValue;

            // Convert input to decimal
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

            // Update all result TextViews
            resultDecimal.setText(String.valueOf(decimalValue));
            resultBinary.setText(Integer.toBinaryString(decimalValue));
            resultOctal.setText(Integer.toOctalString(decimalValue));
            resultHex.setText(Integer.toHexString(decimalValue).toUpperCase());

        } catch (NumberFormatException e) {
            resultDecimal.setText("Invalid");
            resultBinary.setText("Invalid");
            resultOctal.setText("Invalid");
            resultHex.setText("Invalid");
        }
    }
}
