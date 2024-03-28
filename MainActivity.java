// Resources accessed
// https://www.tutorialspoint.com/android/android_spinner_control.htm
// https://java2blog.com/format-double-to-2-decimal-places-java/


package com.example.task21p;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Declare variables
    Button convertButton;
    EditText valueText;
    TextView solutionText;
    TextView problemText;
    Spinner conversionTypeSpinner;
    Spinner sourceUnitSpinner;
    Spinner destinationUnitSpinner;
    NumberFormat decimalPlaces;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Link variables to UI elements
        convertButton = findViewById(R.id.convertButton);
        valueText = findViewById(R.id.valueText);
        solutionText = findViewById(R.id.solutionText);
        problemText = findViewById(R.id.problemText);

        conversionTypeSpinner = (Spinner) findViewById(R.id.conversionTypeSpinner);
        sourceUnitSpinner = (Spinner) findViewById(R.id.sourceUnitSpinner);
        destinationUnitSpinner = (Spinner) findViewById(R.id.destinationUnitSpinner);

        // Set up decimal places for results display
        decimalPlaces = NumberFormat.getInstance();
        decimalPlaces.setMaximumFractionDigits(6);

        // Set up drown down arrays
        List<String> conversionTypeCategories = new ArrayList<String>();
        conversionTypeCategories.add("Length");
        conversionTypeCategories.add("Weight");
        conversionTypeCategories.add("Temperature");

        List<String> lengthCategories = new ArrayList<String>();
        lengthCategories.add("Inch");
        lengthCategories.add("Foot");
        lengthCategories.add("Yard");
        lengthCategories.add("Mile");
        lengthCategories.add("Centimetre");
        lengthCategories.add("Metre");
        lengthCategories.add("Kilometre");

        List<String> weightCategories = new ArrayList<String>();
        weightCategories.add("Pound");
        weightCategories.add("Ounce");
        weightCategories.add("Ton");
        weightCategories.add("Gram");
        weightCategories.add("Kilogram");

        List<String> temperatureCategories = new ArrayList<String>();
        temperatureCategories.add("Celsius");
        temperatureCategories.add("Fahrenheit");
        temperatureCategories.add("Kelvin");

        // Create array adapters
        ArrayAdapter<String> conversionTypeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, conversionTypeCategories);
        conversionTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> lengthAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lengthCategories);
        lengthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> weightAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, weightCategories);
        weightAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> temperatureAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, temperatureCategories);
        temperatureAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Assign initial adapters to dropdowns
        conversionTypeSpinner.setAdapter(conversionTypeAdapter);
        sourceUnitSpinner.setAdapter(lengthAdapter);
        destinationUnitSpinner.setAdapter(lengthAdapter);

        // Setup on item selected listener for conversion type spinner to update source and destination spinners
        conversionTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Retrieve conversion type spinner value
                String spinnerValue = parent.getItemAtPosition(position).toString();

                // Update source and destination units based on conversion type selection
                switch(spinnerValue){
                    case "Length":
                        sourceUnitSpinner.setAdapter(lengthAdapter);
                        destinationUnitSpinner.setAdapter(lengthAdapter);
                        break;
                    case "Weight":
                        sourceUnitSpinner.setAdapter(weightAdapter);
                        destinationUnitSpinner.setAdapter(weightAdapter);
                        break;
                    case "Temperature":
                        sourceUnitSpinner.setAdapter(temperatureAdapter);
                        destinationUnitSpinner.setAdapter(temperatureAdapter);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Setup on click listener for convert button
        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve spinner values
                String conversionSpinnerValue = conversionTypeSpinner.getSelectedItem().toString();
                String sourceSpinnerValue = sourceUnitSpinner.getSelectedItem().toString();
                String destinationSpinnerValue = destinationUnitSpinner.getSelectedItem().toString();
                // Retrieve source value
                double sourceValue = Double.parseDouble(valueText.getText().toString());
                double result = 0;

                // Calculate result
                switch (conversionSpinnerValue){
                    case "Length":
                        result = lengthCalculator(sourceSpinnerValue,destinationSpinnerValue,sourceValue);
                        break;
                    case "Weight":
                        result = weightCalculator(sourceSpinnerValue,destinationSpinnerValue,sourceValue);

                        break;
                    case "Temperature":
                        result = temperatureCalculator(sourceSpinnerValue,destinationSpinnerValue,sourceValue);
                        break;
                }

                // Set text view values to display result
                problemText.setText(valueText.getText().toString() + " " + sourceSpinnerValue + " =");
                solutionText.setText(String.format(decimalPlaces.format(result)) + " " + destinationSpinnerValue);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // Define custom functions
    public double lengthCalculator(String from, String to, double value){
        // Length conversion function
        switch(from){
            case "Inch":
                switch (to) {
                    case "Inch":
                        return value;
                    case "Foot":
                        return value / 12;
                    case "Yard":
                        return value / 36;
                    case "Mile":
                        return value / 63360;
                    case "Centimetre":
                        return value * 2.54;
                    case "Metre":
                        return value / 39.3700787;
                    case "Kilometre":
                        return value / 39370.0787;
                }
                break;
            case "Foot":
                switch (to) {
                    case "Inch":
                        return value * 12;
                    case "Foot":
                        return value;
                    case "Yard":
                        return value / 3;
                    case "Mile":
                        return value / 5280;
                    case "Centimetre":
                        return value * 30.48;
                    case "Metre":
                        return value / 3.2808399;
                    case "Kilometre":
                        return value / 3280.8399;
                }
                break;
            case "Yard":
                switch (to) {
                    case "Inch":
                        return value * 36;
                    case "Foot":
                        return value * 3;
                    case "Yard":
                        return value;
                    case "Mile":
                        return value / 1760;
                    case "Centimetre":
                        return value * 91.44;
                    case "Metre":
                        return value / 1.0936133;
                    case "Kilometre":
                        return value / 1093.6133;
                }
                break;
            case "Mile":
                switch (to) {
                    case "Inch":
                        return value * 63360;
                    case "Foot":
                        return value * 5280;
                    case "Yard":
                        return value * 1760;
                    case "Mile":
                        return value;
                    case "Centimetre":
                        return value * 160934.4;
                    case "Metre":
                        return value * 1609.344;
                    case "Kilometre":
                        return value * 1.609344;
                }
                break;
            case "Centimetre":
                switch (to) {
                    case "Inch":
                        return value / 2.54;
                    case "Foot":
                        return value / 30.48;
                    case "Yard":
                        return value / 91.44;
                    case "Mile":
                        return value / 160934.4;
                    case "Centimetre":
                        return value;
                    case "Metre":
                        return value / 100;
                    case "Kilometre":
                        return value / 100000;
                }
                break;
            case "Metre":
                switch (to) {
                    case "Inch":
                        return value * 39.3700787;
                    case "Foot":
                        return value * 3.2808399;
                    case "Yard":
                        return value * 1.0936133;
                    case "Mile":
                        return value / 1609.344;
                    case "Centimetre":
                        return value * 100;
                    case "Metre":
                        return value;
                    case "Kilometre":
                        return value / 1000;
                }
                break;
            case "Kilometre":
                switch (to) {
                    case "Inch":
                        return value * 39370.0787;
                    case "Foot":
                        return value * 3280.8399;
                    case "Yard":
                        return value * 1093.6133;
                    case "Mile":
                        return value / 1.609344;
                    case "Centimetre":
                        return value * 100000;
                    case "Metre":
                        return value * 1000;
                    case "Kilometre":
                        return value;
                }
                break;
            }
        return 0;
    }

    public double weightCalculator(String from, String to, double value){
        // Weight conversion function
        switch(from){
            case "Pound":
                switch (to) {
                    case "Pound":
                        return value;
                    case "Ounce":
                        return value * 16;
                    case "Ton":
                        return value / 2000;
                    case "Gram":
                        return value * 453.59237;
                    case "Kilogram":
                        return value / 2.20462262;
                }
                break;
            case "Ounce":
                switch (to) {
                    case "Pound":
                        return value / 16;
                    case "Ounce":
                        return value;
                    case "Ton":
                        return value / 32000;
                    case "Gram":
                        return value * 28.3495231;
                    case "Kilogram":
                        return value / 35.273962;
                }
                break;
            case "Ton":
                switch (to) {
                    case "Pound":
                        return value * 2000;
                    case "Ounce":
                        return value * 32000;
                    case "Ton":
                        return value;
                    case "Gram":
                        return value * 907184.74;
                    case "Kilogram":
                        return value * 907.18474;
                }
                break;
            case "Gram":
                switch (to) {
                    case "Pound":
                        return value / 453.59237;
                    case "Ounce":
                        return value / 28.3495231;
                    case "Ton":
                        return value / 907184.74;
                    case "Gram":
                        return value;
                    case "Kilogram":
                        return value / 1000;
                }
                break;
            case "Kilogram":
                switch (to) {
                    case "Pound":
                        return value * 2.20462262;
                    case "Ounce":
                        return value * 35.273962;
                    case "Ton":
                        return value / 907.18474;
                    case "Gram":
                        return value * 1000;
                    case "Kilogram":
                        return value;
                }
                break;
        }
        return 0;
    }

    public double temperatureCalculator(String from, String to, double value){
        // Temperature conversion function
        switch(from){
            case "Celsius":
                switch (to) {
                    case "Celsius":
                        return value;
                    case "Fahrenheit":
                        return (value * 1.8) + 32;
                    case "Kelvin":
                        return value + 273.15;
                }
                break;
            case "Fahrenheit":
                switch (to) {
                    case "Celsius":
                        return (value - 32) / 1.8;
                    case "Fahrenheit":
                        return value;
                    case "Kelvin":
                        return (value - 32) / 1.8 + 273.15;
                }
                break;
            case "Kelvin":
                switch (to) {
                    case "Celsius":
                        return value - 273.15;
                    case "Fahrenheit":
                        return ((value - 273.15) * 9) / 5 + 32;
                    case "Kelvin":
                        return value;
                }
                break;
        }
        return 0;
    }
}