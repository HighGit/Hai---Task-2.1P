package com.example.task21p;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    Button convertButton;
    EditText inputValue;
    EditText outputResult;
    Spinner startMetric;
    Spinner destinationMetric;
    final String[] units = {"Inch", "Foot", "Yard", "Mile", "Centimeter", "Kilometer"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Set up interactable components
        convertButton = findViewById(R.id.convertButton);
        inputValue = findViewById(R.id.inputValue);
        outputResult = findViewById(R.id.outputResult);
        startMetric = findViewById(R.id.startMetric);
        destinationMetric = findViewById(R.id.destinationMetric);

        // Set up the dropdown menus (spinners)
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, units);
        startMetric.setAdapter(adapter);
        destinationMetric.setAdapter(adapter);

        // Handle conversion on button click
        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                convertUnits();
            }
        });


    }

    //Call the converter and output the result
    private void convertUnits() {
        String fromUnit = startMetric.getSelectedItem().toString();
        String toUnit = destinationMetric.getSelectedItem().toString();
        String inputText = inputValue.getText().toString();

        if (inputText.isEmpty()) {
            outputResult.setText("Please enter a value.");
            return;
        }

        double inputValueDouble = Double.parseDouble(inputText);
        double convertedValue = convertLength(inputValueDouble, fromUnit, toUnit);
        outputResult.setText(String.format("%.2f %s", convertedValue, toUnit));
    }

    //Use centimeter as base, convert the value
    private double convertLength(double value, String from, String to) {
        double cmValue = switch (from) {
            case "Inch" -> value * 2.54;
            case "Foot" -> value * 30.48;
            case "Yard" -> value * 91.44;
            case "Mile" -> value * 160934;
            case "Centimeter" -> value;
            case "Kilometer" -> value * 100000;
            default -> 0;
        };

        return switch (to) {
            case "Inch" -> cmValue / 2.54;
            case "Foot" -> cmValue / 30.48;
            case "Yard" -> cmValue / 91.44;
            case "Mile" -> cmValue / 160934;
            case "Centimeter" -> cmValue;
            case "Kilometer" -> cmValue / 100000;
            default -> 0;
        };
    }

}