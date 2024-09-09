package com.nawab.smartquizapp;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class QuizActivity extends AppCompatActivity {

    private TextView tvQuestion, tvScore;
    private Button btnOption1, btnOption2, btnOption3, btnOption4, btnNext, btnRestart;
    private String correctAnswer;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private String[] quizQuestions;
    private String[][] quizOptions;
    private String[] quizCorrectAnswers;
    private AdView mAdView;

    // Define arrays for different quizzes
    private static final String[][] QUIZ_QUESTIONS = {
            {   // Pakistan Quiz
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
            },
            {   // General Knowledge Quiz
                    "What is the capital of France?",
                    "Who wrote 'To Kill a Mockingbird'?",
                    "What is the smallest planet in our solar system?",
                    "Which element has the chemical symbol 'O'?",
                    "What is the hardest natural substance on Earth?",
                    "Who painted the Mona Lisa?",
                    "What is the longest river in the world?",
                    "What year did the Titanic sink?",
                    "Who developed the theory of relativity?",
                    "What is the largest ocean on Earth?"
            },
            {   // Science Quiz
                    "What is the chemical formula for water?",
                    "What planet is known as the Red Planet?",
                    "How many bones are there in the adult human body?",
                    "What gas do plants use for photosynthesis?",
                    "What is the speed of light?",
                    "Who is known as the father of modern physics?",
                    "What is the boiling point of water in Celsius?",
                    "What is the largest organ in the human body?",
                    "Which planet is closest to the Sun?",
                    "What type of bond is formed between hydrogen and oxygen in water?"
            }
    };

    private static final String[][][] QUIZ_OPTIONS = {
            {   // Pakistan Quiz options
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
            },
            {   // General Knowledge Quiz options
                    {"Paris", "London", "Rome", "Berlin"},
                    {"Harper Lee", "J.K. Rowling", "Mark Twain", "Ernest Hemingway"},
                    {"Mercury", "Mars", "Venus", "Earth"},
                    {"Oxygen", "Hydrogen", "Carbon", "Nitrogen"},
                    {"Diamond", "Gold", "Iron", "Platinum"},
                    {"Leonardo da Vinci", "Vincent van Gogh", "Pablo Picasso", "Claude Monet"},
                    {"Nile", "Amazon", "Yangtze", "Mississippi"},
                    {"1912", "1905", "1898", "1920"},
                    {"Albert Einstein", "Isaac Newton", "Galileo Galilei", "Nikola Tesla"},
                    {"Pacific", "Atlantic", "Indian", "Arctic"}
            },
            {   // Science Quiz options
                    {"H2O", "CO2", "NaCl", "CH4"},
                    {"Mars", "Jupiter", "Saturn", "Earth"},
                    {"206", "205", "210", "200"},
                    {"Oxygen", "Carbon Dioxide", "Nitrogen", "Hydrogen"},
                    {"299,792 km/s", "150,000 km/s", "500,000 km/s", "1,000,000 km/s"},
                    {"Albert Einstein", "Isaac Newton", "Galileo Galilei", "Niels Bohr"},
                    {"100°C", "90°C", "110°C", "120°C"},
                    {"Skin", "Liver", "Heart", "Brain"},
                    {"Mercury", "Venus", "Earth", "Mars"},
                    {"Covalent", "Ionic", "Metallic", "Hydrogen"}
            }
    };

    private static final String[][] QUIZ_CORRECT_ANSWERS = {
            {   // Pakistan Quiz
                    "Islamabad", "1947", "Quaid-e-Azam", "Urdu", "Balochistan",
                    "Liaquat Ali Khan", "Karachi", "Markhor", "Indus", "K2"
            },
            {   // General Knowledge Quiz
                    "Paris", "Harper Lee", "Mercury", "Oxygen", "Diamond",
                    "Leonardo da Vinci", "Nile", "1912", "Albert Einstein", "Pacific"
            },
            {   // Science Quiz
                    "H2O", "Mars", "206", "Oxygen", "299,792 km/s",
                    "Albert Einstein", "100°C", "Skin", "Mercury", "Covalent"
            }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        tvQuestion = findViewById(R.id.tvQuestion);
        tvScore = findViewById(R.id.tvScore);
        btnOption1 = findViewById(R.id.btnOption1);
        btnOption2 = findViewById(R.id.btnOption2);
        btnOption3 = findViewById(R.id.btnOption3);
        btnOption4 = findViewById(R.id.btnOption4);
        btnNext = findViewById(R.id.btnNext);
        btnRestart = findViewById(R.id.btnRestart);

        // Retrieve quiz type from Intent
        String quizType = getIntent().getStringExtra("quiz_type");

        // Set the quiz index based on the quiz type
        int quizIndex = 0;  // Default to Pakistan Quiz
        if ("General Knowledge Quiz".equals(quizType)) {
            quizIndex = 1;
        } else if ("Science Quiz".equals(quizType)) {
            quizIndex = 2;
        }

        // Load quiz data
        quizQuestions = QUIZ_QUESTIONS[quizIndex];
        quizOptions = QUIZ_OPTIONS[quizIndex];
        quizCorrectAnswers = QUIZ_CORRECT_ANSWERS[quizIndex];
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

    private void loadQuestion() {
        tvQuestion.setText(quizQuestions[currentQuestionIndex]);
        btnOption1.setText(quizOptions[currentQuestionIndex][0]);
        btnOption2.setText(quizOptions[currentQuestionIndex][1]);
        btnOption3.setText(quizOptions[currentQuestionIndex][2]);
        btnOption4.setText(quizOptions[currentQuestionIndex][3]);
        btnNext.setVisibility(View.GONE);  // Hide the next button until an answer is selected
        btnRestart.setVisibility(View.GONE);  // Hide restart button initially
    }

    private void checkAnswer(Button selectedButton, String selectedAnswer) {
        correctAnswer = quizCorrectAnswers[currentQuestionIndex];
        if (selectedAnswer.equals(correctAnswer)) {
            selectedButton.setBackgroundColor(Color.GREEN);
            score++;
        } else {
            selectedButton.setBackgroundColor(Color.RED);
        }
        tvScore.setText("Score: " + score);
        btnNext.setVisibility(View.VISIBLE);  // Show the next button after answering
    }

    private void resetButtons() {
        btnOption1.setBackgroundColor(Color.LTGRAY);
        btnOption2.setBackgroundColor(Color.LTGRAY);
        btnOption3.setBackgroundColor(Color.LTGRAY);
        btnOption4.setBackgroundColor(Color.LTGRAY);
    }

    private void showResult() {
        tvQuestion.setText("Quiz Complete! Your score: " + score);
        btnRestart.setVisibility(View.VISIBLE);  // Show restart button after quiz completion
        btnOption1.setVisibility(View.GONE);
        btnOption2.setVisibility(View.GONE);
        btnOption3.setVisibility(View.GONE);
        btnOption4.setVisibility(View.GONE);
        btnNext.setVisibility(View.GONE);
    }

    private void restartQuiz() {
        score = 0;
        currentQuestionIndex = 0;
        loadQuestion();
        resetButtons();
        btnOption1.setVisibility(View.VISIBLE);
        btnOption2.setVisibility(View.VISIBLE);
        btnOption3.setVisibility(View.VISIBLE);
        btnOption4.setVisibility(View.VISIBLE);
        tvScore.setText("Score: " + score);
    }
}
