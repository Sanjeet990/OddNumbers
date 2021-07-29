package com.poject.oddsum;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.poject.oddsum.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //on button click event
        binding.btnProceed.setOnClickListener(v -> {
            //get values entered
            try {
                double range1 = Double.parseDouble(binding.range1.getText().toString());
                double range2 = Double.parseDouble(binding.range2.getText().toString());

                //make proper validation of user input
                if (range1 > range2) {
                    Toast.makeText(this, "Range 1 can not be greater than range 2.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (range1 < 0 || range2 < 0) {
                    Toast.makeText(this, "Ranges can not be negative numbers.", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Hide result container and show loading
                binding.resultContainer.setVisibility(View.GONE);
                binding.progress.setVisibility(View.VISIBLE);

                new Thread(() -> {
                    double oddCount = 0;
                    double oddSum = 0;

                    for (double i = range1; i < range2; i++) {
                        if (i % 2 != 0) {
                            oddSum = oddSum + i;
                            oddCount++;
                        }
                    }
                    double finalOddCount = oddCount;
                    double finalOddSum = oddSum;
                    runOnUiThread(() -> {
                        //You can not show int directly in textview as it will be served as a res id and will crash
                        //Simople fix is to append the int in an empty string to make implicit typecast it to String
                        binding.count.setText("" + (int) finalOddCount);
                        binding.sum.setText("" + (int) finalOddSum);

                        //Hide loading and bringback loading
                        binding.resultContainer.setVisibility(View.VISIBLE);
                        binding.progress.setVisibility(View.GONE);
                    });
                }).start();


            } catch (Exception exception) {
                Toast.makeText(this, "Exception: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}