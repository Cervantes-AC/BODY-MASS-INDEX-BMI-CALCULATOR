package com.example.bodymassindexbmicalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupButtonListener();
    }

    private void setupButtonListener() {
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText heightText = findViewById(R.id.heightInput);
                EditText weightText = findViewById(R.id.weightInput);
                TextView BMIResult = findViewById(R.id.BMIResult);
                TextView BMICategory = findViewById(R.id.BMICategory);

                // Prevent crash if fields are empty
                if (heightText.getText().toString().isEmpty() || weightText.getText().toString().isEmpty()) {
                    BMIResult.setText("Enter values");
                    BMICategory.setText("");
                    return;
                }

                double height = Double.parseDouble(heightText.getText().toString());
                double weight = Double.parseDouble(weightText.getText().toString());

                // If user entered height in cm, convert to meters
                if (height > 10) {
                    height = height / 100.0;
                }

                // Calculate BMI
                double BMI = weight / (height * height);
                BMIResult.setText(String.format("%.2f", BMI));

                // Determine BMI Category
                String BMI_Cat;
                if (BMI < 15)
                    BMI_Cat = "Very severely underweight";
                else if (BMI < 16)
                    BMI_Cat = "Severely underweight";
                else if (BMI < 18.5)
                    BMI_Cat = "Underweight";
                else if (BMI < 25)
                    BMI_Cat = "Normal";
                else if (BMI < 30)
                    BMI_Cat = "Overweight";
                else if (BMI < 35)
                    BMI_Cat = "Obese Class 1 – Moderately Obese";
                else if (BMI < 40)
                    BMI_Cat = "Obese Class 2 – Severely Obese";
                else
                    BMI_Cat = "Obese Class 3 – Very Severely Obese";

                // Guidance for weight change
                String extraInfo = "";
                if (BMI < 18.5) {
                    // Minimum normal BMI boundary
                    double minNormalWeight = 18.5 * (height * height);
                    double weightToGain = minNormalWeight - weight;
                    extraInfo = String.format("\nYou need to gain %.1f kg to reach a normal BMI.", weightToGain);
                } else if (BMI >= 25) {
                    // Maximum normal BMI boundary
                    double maxNormalWeight = 24.9 * (height * height);
                    double weightToLose = weight - maxNormalWeight;
                    extraInfo = String.format("\nYou need to lose %.1f kg to reach a normal BMI.", weightToLose);
                }

                BMICategory.setText(BMI_Cat + extraInfo);
            }
        });
    }
}
