package com.example.richard.assessment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class QuizMainActivity extends AppCompatActivity {

    final int NUM_ANSWERS = 4;
    final int NUM_QUESTIONS = 4;
    int mQnANum, btnPlacementNum, mRandAnswer;
    int clickCount = 0;
    int score = 0;
    String questionText, answerText, randAnswer;
    Button next, results;
    Button[] btns;
    TextView tv;

    Random r;

    ArrayList<QuestionsModel> qm = new ArrayList<>();
    ArrayList<AnswersModel> am = new ArrayList<>();
    ArrayList<Integer> takenAnswers = new ArrayList<Integer>();


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_activity_main);

        //retrieved questions array list



        //retrieved answers array list


        //Random Number generator for Questions and Answers
        //r2 is meant for generating random incorrect answers when making mcq

        r = new Random();
        //Random r2 = new Random();
        //Random r3 = new Random();

        tv = findViewById(R.id.questionText);


        btns = new Button[4];
        btns[0] = (Button) findViewById(R.id.btn1);
        btns[1] = (Button) findViewById(R.id.btn2);
        btns[2] = (Button) findViewById(R.id.btn3);
        btns[3] = (Button) findViewById(R.id.btn4);

        next = (Button) findViewById(R.id.nextQ);
        results = (Button) findViewById(R.id.see_results);
        results.setVisibility(View.INVISIBLE);

        getQuestionsAnswers();
        multipleChoiceQuiz();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multipleChoiceQuiz();
                for(int i = 0; i < NUM_ANSWERS; i++) {
                    btns[i].setEnabled(true);
                    btns[i].setBackgroundResource(android.R.drawable.btn_default);
                }

                clickCount++;
                if (clickCount == NUM_QUESTIONS - 1) {
                    next.setOnClickListener(null);
                    next.setEnabled(false);
                    next.setVisibility(View.INVISIBLE);
                    results.setVisibility(View.VISIBLE);
                }
            }
        });

        results.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuizMainActivity.this, ModuleActivity.class);
                intent.putExtra("score", score);
                double mark = score / NUM_QUESTIONS;
                if(mark > 0.8) {
                    intent.putExtra("pass", "pass");
                } else {
                    intent.putExtra("fail", "fail");
                }
                startActivity(intent);
            }
        });

    }

    public void getQuestionsAnswers() {
        qm = QandADatabase.getQuestionsArrayList();
        am = QandADatabase.getAnswersArrayList();
    }

    public void multipleChoiceQuiz() {
        mQnANum = r.nextInt(3 - 0 + 1) + 0;
        btnPlacementNum = r.nextInt(3 - 0 + 1) + 0;
        if (btnPlacementNum == mQnANum) {
            btnPlacementNum = r.nextInt(4);
        }
        questionText = qm.get(mQnANum).getmQuestion();
        answerText = am.get(mQnANum).getmAnswers();

        btns[mQnANum].setText(answerText);
        tv.setText(questionText);

        takenAnswers.add(mQnANum);

        for (int i = 0; i < 20; i++) {
            mRandAnswer = r.nextInt(3 - 0 + 1) + 0;
            if (!btns[mRandAnswer].equals(answerText) && !takenAnswers.contains(mRandAnswer)) {
                randAnswer = am.get(mRandAnswer).getmAnswers();
                btns[mRandAnswer].setText(randAnswer);
            }

        }

        for(int k = 0; k < btns.length; k++) {
            final int finalK = k;
            btns[k].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(btns[finalK].getText().toString().equals(answerText)) {
                        btns[finalK].setBackgroundColor(Color.GREEN);
                        score++;
                        next.setVisibility(View.VISIBLE);
                    } else {
                        Animation shakeButton = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                        btns[mQnANum].startAnimation(shakeButton);
                        btns[finalK].setBackgroundColor(Color.RED);
                        btns[mQnANum].setBackgroundColor(Color.GREEN);
                        next.setVisibility(View.VISIBLE);
                    }
                    for (int i = 0; i < btns.length; i++) {
                        btns[i].setEnabled(false);

                    }
                }
            });
        }


    }
}


