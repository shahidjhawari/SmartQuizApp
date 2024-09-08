package com.nawab.smartquizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the Google Mobile Ads SDK on a background thread
        new Thread(() -> {
            MobileAds.initialize(this, initializationStatus -> {
                // You can log or handle initialization status here if needed
            });
        }).start();

        // Get Started Button logic
        Button btnGetStarted = findViewById(R.id.btnGetStarted);
        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, QuizSelectionActivity.class);
                startActivity(intent);
            }
        });
    }
}
