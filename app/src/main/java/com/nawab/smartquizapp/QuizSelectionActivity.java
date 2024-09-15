package com.nawab.smartquizapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class QuizSelectionActivity extends AppCompatActivity {

    private InterstitialAd mInterstitialAd;
    private String selectedQuizType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_selection);

        Button btnPakistanQuiz = findViewById(R.id.btnPakistanQuiz);
        Button btnGeneralKnowledgeQuiz = findViewById(R.id.btnGeneralKnowledgeQuiz);
        Button btnScienceQuiz = findViewById(R.id.btnScienceQuiz);

        // Load the interstitial ad when the activity starts
        loadInterstitialAd();

        // Pakistan Quiz Button
        btnPakistanQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedQuizType = "Pakistan Quiz";
                if (mInterstitialAd != null) {
                    showInterstitialAd(); // Show ad if it's ready
                } else {
                    waitForAdThenProceed(); // Wait and retry if ad is not ready
                }
            }
        });

        // General Knowledge Quiz Button
        btnGeneralKnowledgeQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedQuizType = "General Knowledge Quiz";
                if (mInterstitialAd != null) {
                    showInterstitialAd();
                } else {
                    waitForAdThenProceed();
                }
            }
        });

        // Science Quiz Button
        btnScienceQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedQuizType = "Science Quiz";
                if (mInterstitialAd != null) {
                    showInterstitialAd();
                } else {
                    waitForAdThenProceed();
                }
            }
        });
    }

    // Load the Interstitial Ad
    private void loadInterstitialAd() {
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(this, "ca-app-pub-3634516383748300/2148993175", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        mInterstitialAd = interstitialAd;
                        Log.d("AdMob", "Ad was loaded.");
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        Log.d("AdMob", "Ad failed to load: " + loadAdError.getMessage());
                        mInterstitialAd = null;
                    }
                });
    }

    // Show Interstitial Ad and wait for it to be dismissed
    private void showInterstitialAd() {
        if (mInterstitialAd != null) {
            mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    Log.d("AdMob", "Ad was dismissed.");
                    mInterstitialAd = null;
                    loadInterstitialAd(); // Reload for future use
                    openQuizActivity(); // Open the quiz after the ad is dismissed
                }

                @Override
                public void onAdFailedToShowFullScreenContent(AdError adError) {
                    Log.d("AdMob", "Ad failed to show.");
                    openQuizActivity(); // Open the quiz even if the ad fails to show
                }

                @Override
                public void onAdShowedFullScreenContent() {
                    Log.d("AdMob", "Ad showed fullscreen content.");
                }
            });

            mInterstitialAd.show(QuizSelectionActivity.this);
        }
    }

    // Wait for the ad to load, then proceed
    private void waitForAdThenProceed() {
        loadInterstitialAd();  // Try loading the ad again
        Log.d("AdMob", "Ad not ready yet, loading ad and retrying.");
        // You can implement a delay or spinner here to make sure the user waits for the ad to load
        if (mInterstitialAd != null) {
            showInterstitialAd();
        } else {
            openQuizActivity(); // If ad loading fails again, proceed to quiz
        }
    }

    // Function to open the Quiz Activity after the ad is closed
    private void openQuizActivity() {
        Intent intent = new Intent(QuizSelectionActivity.this, QuizActivity.class);
        intent.putExtra("quiz_type", selectedQuizType);
        startActivity(intent);
    }
}
