package com.example.task21redo;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Spinner spinnerCategory = findViewById(R.id.Category);
        Spinner spinnerFrom = findViewById(R.id.From);
        Spinner spinnerTo = findViewById(R.id.To);

        EditText inputValue = findViewById(R.id.InputValue);
        Button buttonConvert = findViewById(R.id.ButtonConvert);
        TextView textResult = findViewById(R.id.TextResult);

        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.conversion_categories,
                android.R.layout.simple_spinner_item
        );
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(categoryAdapter);

        ArrayAdapter<CharSequence> unitAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.currency_units,
                android.R.layout.simple_spinner_item
        );
        unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFrom.setAdapter(unitAdapter);
        spinnerTo.setAdapter(unitAdapter);

        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String category = spinnerCategory.getSelectedItem().toString();

                if (category.equals("Currency")) {
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                            MainActivity.this,
                            R.array.currency_units,
                            android.R.layout.simple_spinner_item
                    );
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerFrom.setAdapter(adapter);
                    spinnerTo.setAdapter(adapter);
                }
                else if (category.equals("Fuel Efficiency")) {
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                            MainActivity.this,
                            R.array.fuel_units,
                            android.R.layout.simple_spinner_item
                    );
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerFrom.setAdapter(adapter);
                    spinnerTo.setAdapter(adapter);
                }
                else if (category.equals("Temperature")) {
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                            MainActivity.this,
                            R.array.temperature_units,
                            android.R.layout.simple_spinner_item
                    );
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerFrom.setAdapter(adapter);
                    spinnerTo.setAdapter(adapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        buttonConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String valueText = inputValue.getText().toString();

                double value = Double.parseDouble(valueText);

                String category = spinnerCategory.getSelectedItem().toString();
                String from = spinnerFrom.getSelectedItem().toString();
                String to = spinnerTo.getSelectedItem().toString();

                double result = value;

                if (category.equals("Currency")) {
                    double usd = value;

                    if (from.equals("AUD")) {
                        usd = value / 1.55;
                    }
                    else if (from.equals("EUR")) {
                        usd = value / 0.92;
                    }
                    else if (from.equals("JPY")) {
                        usd = value / 148.50;
                    }
                    else if (from.equals("GBP")) {
                        usd = value / 0.78;
                    }

                    if (to.equals("USD")) {
                        result = usd;
                    }
                    else if (to.equals("AUD")) {
                        result = usd * 1.55;
                    }
                    else if (to.equals("EUR")) {
                        result = usd * 0.92;
                    }
                    else if (to.equals("JPY")) {
                        result = usd * 148.50;
                    }
                    else if (to.equals("GBP")) {
                        result = usd * 0.78;
                    }

                }
                else if (category.equals("Fuel Efficiency")) {
                    if (from.equals("mpg") && to.equals("km/L")) {
                        result = value * 0.425;
                    }
                    else if (from.equals("km/L") && to.equals("mpg")) {
                        result = value / 0.425;
                    }
                    else {
                        result = value;
                    }
                }
                else if (category.equals("Temperature")) {
                    double celsius = value;

                    if (from.equals("Fahrenheit")) {
                        celsius = (value - 32) /1.8;
                    }
                    else if (from.equals("Kelvin")) {
                        celsius = value - 273.15;
                    }
                    if (to.equals("celsius")) {
                        result = celsius;
                    }
                    else if (to.equals("Fahrenheit")) {
                        result = (celsius * 1.8) + 32;
                    }
                    else if (to.equals("Kelvin")) {
                        result = celsius +273.15;
                    }
                }
                double roundedResult = Math.round(result *100.0) /100.0;
                textResult.setText("Result: " + roundedResult);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}