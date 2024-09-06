package com.nawab.smartquizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class QuizSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_selection);

        Button btnPakistanQuiz = findViewById(R.id.btnPakistanQuiz);
        Button btnGeneralKnowledgeQuiz = findViewById(R.id.btnGeneralKnowledgeQuiz);
        Button btnScienceQuiz = findViewById(R.id.btnScienceQuiz);

        btnPakistanQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuizSelectionActivity.this, QuizActivity.class);
                intent.putExtra("quiz_type", "Pakistan Quiz");
                startActivity(intent);
            }
        });

        btnGeneralKnowledgeQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuizSelectionActivity.this, QuizActivity.class);
                intent.putExtra("quiz_type", "General Knowledge Quiz");
                startActivity(intent);
            }
        });

        btnScienceQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuizSelectionActivity.this, QuizActivity.class);
                intent.putExtra("quiz_type", "Science Quiz");
                startActivity(intent);
            }
        });
    }
}
