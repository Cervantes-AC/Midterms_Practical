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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class UnitConverterActivity extends AppCompatActivity {

    private Spinner switchUI, switchCategory, switchUnit1, switchUnit2;
    private EditText editText;
    private TextView unitText;
    private Button switchButton, backspaceButton, clearButton, submitButton;
    private Button num0, num1, num2, num3, num4, num5, num6, num7, num8, num9, dotButton;

    // Conversion rates (as of Oct 5)
    private static final double PHP_TO_USD = 0.01724;
    private static final double PHP_TO_EUR = 0.01469;
    private static final double USD_TO_PHP = 57.99;
    private static final double USD_TO_EUR = 0.8516;
    private static final double EUR_TO_PHP = 68.09;
    private static final double EUR_TO_USD = 1.174;

    private String currentCategory = "Money";
    private String currentUnit1 = "PHP";
    private String currentUnit2 = "USD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_converter);

        // Initialize UI components
        switchUI = findViewById(R.id.switchUI);
        switchCategory = findViewById(R.id.switchcategory);
        switchUnit1 = findViewById(R.id.switchunit1);
        switchUnit2 = findViewById(R.id.switchunit2);

        editText = findViewById(R.id.editText2);
        unitText = findViewById(R.id.Unittext);

        switchButton = findViewById(R.id.switchbutton);
        backspaceButton = findViewById(R.id.backspace);
        clearButton = findViewById(R.id.clear_text);
        submitButton = findViewById(R.id.submit);

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
        dotButton = findViewById(R.id.dot);

        // Setup UI
        setupNumberButtons();
        setupFunctionButtons();
        spinner_switch_UI();
        spinner_switch_category();
        updateUnitSpinners("Money");
    }

    private void spinner_switch_UI() {
        String[] spinnerOptions = {"Unit Converter", "Basic Calculator", "Base Calculator"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, spinnerOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        switchUI.setAdapter(adapter);

        switchUI.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();

                if (selectedItem.equals("Basic Calculator")) {
                    Intent intent = new Intent(UnitConverterActivity.this, BasicCalculatorActivity.class);
                    startActivity(intent);
                } else if (selectedItem.equals("Base Calculator")) {
                    Intent intent = new Intent(UnitConverterActivity.this, BaseCalculatorActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void spinner_switch_category() {
        String[] categoryOptions = {"Money", "Temperature", "Speed"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, categoryOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        switchCategory.setAdapter(adapter);

        switchCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCategory = parent.getItemAtPosition(position).toString();
                currentCategory = selectedCategory;
                updateUnitSpinners(selectedCategory);
                editText.setText("");
                unitText.setText("");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void updateUnitSpinners(String category) {
        String[] units;

        switch (category) {
            case "Temperature":
                units = new String[]{"Celsius", "Fahrenheit", "Kelvin"};
                currentUnit1 = "Celsius";
                currentUnit2 = "Fahrenheit";
                break;
            case "Speed":
                units = new String[]{"Kilometer", "Meter", "Miles"};
                currentUnit1 = "Kilometer";
                currentUnit2 = "Meter";
                break;
            default:
                units = new String[]{"PHP", "USD", "EUR"};
                currentUnit1 = "PHP";
                currentUnit2 = "USD";
                break;
        }

        ArrayAdapter<String> unitAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, units);
        unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        switchUnit1.setAdapter(unitAdapter);
        switchUnit2.setAdapter(unitAdapter);

        switchUnit1.setSelection(0);
        switchUnit2.setSelection(1);

        setupUnitSpinnerListeners();
        updateUnitLabel();
    }

    private void setupUnitSpinnerListeners() {
        switchUnit1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentUnit1 = parent.getItemAtPosition(position).toString();
                updateUnitLabel();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        switchUnit2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentUnit2 = parent.getItemAtPosition(position).toString();
                updateUnitLabel();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void updateUnitLabel() {
        TextView unitConversionLabel = findViewById(R.id.unitconversion);
        unitConversionLabel.setText(currentUnit1 + " to " + currentUnit2 + ": ");
    }

    private void setupNumberButtons() {
        View.OnClickListener listener = v -> {
            Button b = (Button) v;
            editText.append(b.getText().toString());
        };

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
    }

    private void setupFunctionButtons() {
        switchButton.setOnClickListener(v -> {
            int unit1Position = switchUnit1.getSelectedItemPosition();
            int unit2Position = switchUnit2.getSelectedItemPosition();

            switchUnit1.setSelection(unit2Position);
            switchUnit2.setSelection(unit1Position);

            String temp = currentUnit1;
            currentUnit1 = currentUnit2;
            currentUnit2 = temp;

            updateUnitLabel();

            if (!editText.getText().toString().isEmpty()) {
                performConversion();
            }
        });

        backspaceButton.setOnClickListener(v -> {
            String text = editText.getText().toString();
            if (!text.isEmpty()) {
                editText.setText(text.substring(0, text.length() - 1));
            }
        });

        clearButton.setOnClickListener(v -> {
            editText.setText("");
            unitText.setText("");
        });

        submitButton.setOnClickListener(v -> performConversion());
    }

    private void performConversion() {
        String input = editText.getText().toString().trim();

        if (input.isEmpty()) {
            Toast.makeText(this, "Please enter a value", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double inputValue = Double.parseDouble(input);
            double result;

            switch (currentCategory) {
                case "Temperature":
                    result = convertTemperature(inputValue, currentUnit1, currentUnit2);
                    break;
                case "Speed":
                    result = convertSpeed(inputValue, currentUnit1, currentUnit2);
                    break;
                default:
                    result = convertMoney(inputValue, currentUnit1, currentUnit2);
                    break;
            }

            DecimalFormat df = new DecimalFormat("#,##0.00");
            unitText.setText(df.format(result) + " " + currentUnit2);

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT).show();
        }
    }

    private double convertMoney(double value, String fromUnit, String toUnit) {
        if (fromUnit.equals(toUnit)) return value;

        switch (fromUnit + "->" + toUnit) {
            case "PHP->USD": return value * PHP_TO_USD;
            case "PHP->EUR": return value * PHP_TO_EUR;
            case "USD->PHP": return value * USD_TO_PHP;
            case "USD->EUR": return value * USD_TO_EUR;
            case "EUR->PHP": return value * EUR_TO_PHP;
            case "EUR->USD": return value * EUR_TO_USD;
            default: return value;
        }
    }

    private double convertTemperature(double value, String fromUnit, String toUnit) {
        if (fromUnit.equals(toUnit)) return value;

        double celsius;
        if (fromUnit.equals("Fahrenheit")) celsius = (value - 32) * 5 / 9;
        else if (fromUnit.equals("Kelvin")) celsius = value - 273.15;
        else celsius = value;

        switch (toUnit) {
            case "Fahrenheit": return (celsius * 9 / 5) + 32;
            case "Kelvin": return celsius + 273.15;
            default: return celsius;
        }
    }

    private double convertSpeed(double value, String fromUnit, String toUnit) {
        if (fromUnit.equals(toUnit)) return value;

        double meters;
        switch (fromUnit) {
            case "Kilometer": meters = value * 1000; break;
            case "Miles": meters = value * 1609.34; break;
            default: meters = value; break;
        }

        switch (toUnit) {
            case "Kilometer": return meters / 1000;
            case "Miles": return meters / 1609.34;
            default: return meters;
        }
    }
}
