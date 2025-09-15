package com.example.bodymassindexbmicalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private double previousBMI = -1;
    private double previousWeight = -1;

    private EditText heightText, weightText;
    private TextView BMIResult, BMICategory, prevRecordText, changeText;
    private Button calcButton, newButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        heightText = findViewById(R.id.heightInput);
        weightText = findViewById(R.id.weightInput);
        BMIResult = findViewById(R.id.BMIResult);
        BMICategory = findViewById(R.id.BMICategory);
        prevRecordText = findViewById(R.id.prevRecordText);
        changeText = findViewById(R.id.changeText);
        calcButton = findViewById(R.id.button);
        newButton = findViewById(R.id.buttonNew);

        calcButton.setOnClickListener(v -> calculateAndTrackBMI());
        newButton.setOnClickListener(v -> resetRecords());
    }

    private void calculateAndTrackBMI() {
        if (heightText.getText().toString().isEmpty() || weightText.getText().toString().isEmpty()) {
            BMIResult.setText("Enter values");
            BMICategory.setText("");
            return;
        }

        double height = Double.parseDouble(heightText.getText().toString());
        double weight = Double.parseDouble(weightText.getText().toString());
        if (height > 10) height = height / 100.0;

        double BMI = weight / (height * height);
        BMIResult.setText(String.format("%.2f", BMI));

        // BMI category
        String BMI_Cat;
        if (BMI < 15) BMI_Cat = "Very severely underweight";
        else if (BMI < 16) BMI_Cat = "Severely underweight";
        else if (BMI < 18.5) BMI_Cat = "Underweight";
        else if (BMI < 25) BMI_Cat = "Normal";
        else if (BMI < 30) BMI_Cat = "Overweight";
        else if (BMI < 35) BMI_Cat = "Obese Class 1 – Moderately Obese";
        else if (BMI < 40) BMI_Cat = "Obese Class 2 – Severely Obese";
        else BMI_Cat = "Obese Class 3 – Very Severely Obese";

        String extraInfo = "";
        if (BMI < 18.5) {
            double minNormalWeight = 18.5 * (height * height);
            extraInfo = String.format("\nGain %.1f kg to reach normal BMI.", (minNormalWeight - weight));
        } else if (BMI >= 25) {
            double maxNormalWeight = 24.9 * (height * height);
            extraInfo = String.format("\nLose %.1f kg to reach normal BMI.", (weight - maxNormalWeight));
        }
        BMICategory.setText(BMI_Cat + extraInfo);

        // Show previous record and weight change
        if (previousBMI != -1) {
            prevRecordText.setText(
                    String.format("Previous BMI: %.2f (Weight: %.1f kg)", previousBMI, previousWeight)
            );

            double weightDiff = weight - previousWeight;
            if (weightDiff > 0) {
                changeText.setText(String.format("You gained %.1f kg since last record.", weightDiff));
            } else if (weightDiff < 0) {
                changeText.setText(String.format("You lost %.1f kg since last record.", Math.abs(weightDiff)));
            } else {
                changeText.setText("Your weight is unchanged since last record.");
            }
        } else {
            prevRecordText.setText("No previous record.");
            changeText.setText("");
        }

        previousBMI = BMI;
        previousWeight = weight;
    }

    private void resetRecords() {
        previousBMI = -1;
        previousWeight = -1;
        BMIResult.setText("0.0");
        BMICategory.setText("");
        prevRecordText.setText("Records cleared.");
        changeText.setText("");
    }
}
