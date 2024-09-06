package com.nawab.smartquizapp;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class QuizActivity extends AppCompatActivity {

    private TextView tvQuestion, tvScore;
    private Button btnOption1, btnOption2, btnOption3, btnOption4, btnNext, btnRestart;
    private String correctAnswer;
    private int currentQuestionIndex = 0;
    private int score = 0;

    private String[] quizQuestions = {
            "What is the capital of Pakistan?",
            "When did Pakistan gain independence?",
            "Who is the founder of Pakistan?",
            "What is the national language of Pakistan?",
            "Which is the largest province of Pakistan by area?",
            "Who was the first Prime Minister of Pakistan?",
            "Which city is known as the City of Lights?",
            "What is the national animal of Pakistan?",
            "Which river is the longest in Pakistan?",
            "Which mountain is the highest in Pakistan?"
    };

    private String[][] quizOptions = {
            {"Islamabad", "Karachi", "Lahore", "Peshawar"},
            {"1947", "1950", "1965", "1971"},
            {"Allama Iqbal", "Quaid-e-Azam", "Liaquat Ali Khan", "Zulfikar Ali Bhutto"},
            {"Urdu", "Punjabi", "Sindhi", "Pashto"},
            {"Balochistan", "Sindh", "Punjab", "KPK"},
            {"Liaquat Ali Khan", "Zulfikar Ali Bhutto", "Benazir Bhutto", "Ayub Khan"},
            {"Karachi", "Islamabad", "Lahore", "Multan"},
            {"Markhor", "Tiger", "Lion", "Leopard"},
            {"Indus", "Jhelum", "Ravi", "Chenab"},
            {"K2", "Nanga Parbat", "Mount Everest", "Broad Peak"}
    };

    private String[] quizCorrectAnswers = {
            "Islamabad", "1947", "Quaid-e-Azam", "Urdu", "Balochistan",
            "Liaquat Ali Khan", "Karachi", "Markhor", "Indus", "K2"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Link the TextViews and Buttons to their XML counterparts
        tvQuestion = findViewById(R.id.tvScore);
        tvScore = findViewById(R.id.tvScore);  // TextView to show the score
        btnOption1 = findViewById(R.id.btnOption1);
        btnOption2 = findViewById(R.id.btnOption2);
        btnOption3 = findViewById(R.id.btnOption3);
        btnOption4 = findViewById(R.id.btnOption4);
        btnNext = findViewById(R.id.btnNext);
        btnRestart = findViewById(R.id.btnRestart);  // New restart button

        // Load the first question
        loadQuestion();

        // Option buttons click listeners
        View.OnClickListener optionClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button selectedButton = (Button) v;
                String selectedAnswer = selectedButton.getText().toString();
                checkAnswer(selectedButton, selectedAnswer);
            }
        };

        btnOption1.setOnClickListener(optionClickListener);
        btnOption2.setOnClickListener(optionClickListener);
        btnOption3.setOnClickListener(optionClickListener);
        btnOption4.setOnClickListener(optionClickListener);

        // Next button click listener
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentQuestionIndex++;
                if (currentQuestionIndex < quizQuestions.length) {
                    loadQuestion();
                    resetButtons();
                } else {
                    showResult();
                }
            }
        });

        // Restart button click listener
        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restartQuiz();
            }
        });
    }

    // Load the current question and options
    private void loadQuestion() {
        tvQuestion.setText(quizQuestions[currentQuestionIndex]);
        btnOption1.setText(quizOptions[currentQuestionIndex][0]);
        btnOption2.setText(quizOptions[currentQuestionIndex][1]);
        btnOption3.setText(quizOptions[currentQuestionIndex][2]);
        btnOption4.setText(quizOptions[currentQuestionIndex][3]);
        btnNext.setVisibility(View.GONE);  // Hide the next button until an answer is selected
        btnRestart.setVisibility(View.GONE);  // Hide the restart button initially
        correctAnswer = quizCorrectAnswers[currentQuestionIndex];
    }

    // Check if the selected answer is correct
    private void checkAnswer(Button selectedButton, String selectedAnswer) {
        if (selectedAnswer.equals(correctAnswer)) {
            selectedButton.setBackgroundColor(Color.GREEN);  // Correct answer
            score++;
        } else {
            selectedButton.setBackgroundColor(Color.RED);  // Wrong answer
        }
        // Show the score in tvScore
        tvScore.setText("Score: " + score);
        // Show the next button to proceed
        btnNext.setVisibility(View.VISIBLE);
    }

    // Reset the buttons' background color for the next question
    private void resetButtons() {
        btnOption1.setBackgroundColor(Color.LTGRAY);
        btnOption2.setBackgroundColor(Color.LTGRAY);
        btnOption3.setBackgroundColor(Color.LTGRAY);
        btnOption4.setBackgroundColor(Color.LTGRAY);
    }

    // Show the result after the quiz is completed
    private void showResult() {
        tvQuestion.setText("Quiz Over! Your score is: " + score + " out of " + quizQuestions.length);
        btnOption1.setVisibility(View.GONE);
        btnOption2.setVisibility(View.GONE);
        btnOption3.setVisibility(View.GONE);
        btnOption4.setVisibility(View.GONE);
        btnNext.setVisibility(View.GONE);
        btnRestart.setVisibility(View.VISIBLE);  // Show restart button when the quiz is over
    }

    // Restart the quiz
    private void restartQuiz() {
        currentQuestionIndex = 0;
        score = 0;
        btnOption1.setVisibility(View.VISIBLE);
        btnOption2.setVisibility(View.VISIBLE);
        btnOption3.setVisibility(View.VISIBLE);
        btnOption4.setVisibility(View.VISIBLE);
        loadQuestion();
        resetButtons();
    }
}
