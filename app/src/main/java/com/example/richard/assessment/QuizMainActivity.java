package com.example.richard.assessment;

import android.content.Intent;
import android.content.res.Resources;
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
    final double PASS_MARK = 0.5;
    int mQnANum, btnPlacementNum, mRandAnswer;
    String questionText, answerText, randAnswer;
    Button next, results;
    Button[] btns;
    TextView tv;
    int clickCount = 0;
    int score;
    ModuleModel moduleModel;
    View v;

    Random r, r2, r3;

    ArrayList<QuestionsModel> qm = new ArrayList<>();
    ArrayList<AnswersModel> am = new ArrayList<>();
    ArrayList<Integer> takenAnswers = new ArrayList<Integer>();
    ArrayList<String> takenQuestions = new ArrayList<String>();


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_activity_main);

        //retrieved questions array list

        r = new Random();
        r2 = new Random();
        r3 = new Random();


        //retrieved answers array list


        //Random Number generator for Questions and Answers
        //r2 is meant for generating random incorrect answers when making mcq


        tv = findViewById(R.id.questionView);


        btns = new Button[4];
        btns[0] = (Button) findViewById(R.id.btn1);
        btns[1] = (Button) findViewById(R.id.btn2);
        btns[2] = (Button) findViewById(R.id.btn3);
        btns[3] = (Button) findViewById(R.id.btn4);

        next = (Button) findViewById(R.id.nextQ);
        results = (Button) findViewById(R.id.see_results);

        getQuestionsAnswers();
        multipleChoiceQuiz();


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takenAnswers.clear();
                multipleChoiceQuiz();

                for (int i = 0; i < NUM_ANSWERS; i++) {
                    btns[i].setEnabled(true);
                    btns[i].setBackgroundResource(android.R.drawable.btn_default);
                }
                clickCount++;

                if (clickCount == NUM_QUESTIONS - 1) {
                    results.setVisibility(View.VISIBLE);
                    next.setVisibility(View.GONE);
                    next.setOnClickListener(null);
                }

                System.out.println("Score = " +score);
            }
        });

        results.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(QuizMainActivity.this, ModuleActivity.class);
                double mark = score / NUM_QUESTIONS;
                if(mark >= PASS_MARK) {
                    i.putExtra("pass", "pass");
                } else {
                    i.putExtra("fail", "fail");
                }
                startActivity(i);
            }
        });

    }

    public void getQuestionsAnswers() {
        qm = QandADatabase.getQuestionsArrayList();
        am = QandADatabase.getAnswersArrayList();
        //ArrayList<ModuleModel> modules =  moduleModel.getModuleList();
        /*for (int i = 0; i < modules.size(); i++) {
            if (getIntent().getStringExtra("module_name").equals(modules.get(0))) {
                qm.add()
                qm.remove(qm.subList(4, 15));
                am = QandADatabase.getAnswersArrayList();
                am.remove(am.subList(4,15));
            }
            else if (getIntent().getStringExtra("module_name").equals(modules.get(1))) {
                qm = QandADatabase.getQuestionsArrayList();
                qm.remove(qm.subList(8, 15));
                qm.remove(qm.subList(0, 3));
                am = QandADatabase.getAnswersArrayList();
                am.remove(qm.subList(8, 15));
                am.remove(qm.subList(0, 3));
            }
            else if (getIntent().getStringExtra("module_name").equals(modules.get(2))) {
                qm = QandADatabase.getQuestionsArrayList();
                qm.remove(qm.subList(8, 15));
                qm.remove(qm.subList(0, 3));
                am = QandADatabase.getAnswersArrayList();
                am.remove(qm.subList(8, 15));
                am.remove(qm.subList(0, 3));
            }
            else if (getIntent().getStringExtra("module_name").equals(modules.get(3))) {
                qm = QandADatabase.getQuestionsArrayList();
                qm.remove(qm.subList(8, 15));
                qm.remove(qm.subList(0, 3));
                am = QandADatabase.getAnswersArrayList();
                am.remove(qm.subList(8, 15));
                am.remove(qm.subList(0, 3));
            }


        }*/
    }

    public void multipleChoiceQuiz() {
        mQnANum = r.nextInt(16);
        while (takenAnswers.contains(mQnANum)) {
            mQnANum = r.nextInt(16);
        }
        btnPlacementNum = r.nextInt(3 + 1);
        if (btnPlacementNum == mQnANum) {
            btnPlacementNum = r.nextInt(4);
        }
        questionText = qm.get(mQnANum).getmQuestion();
        answerText = am.get(mQnANum).getmAnswers();

        btns[btnPlacementNum].setText(answerText);
        tv.setText(questionText);

        while (takenQuestions.contains(questionText)) {
            questionText = qm.get(r3.nextInt(16)).getmQuestion();
        }
        takenQuestions.add(questionText);

        takenAnswers.add(mQnANum);

        for (int i = 0; i < btns.length; i++) {
            if (!btns[i].getText().equals(answerText)) {
                mRandAnswer = r2.nextInt(16);
                if (!takenAnswers.contains(mRandAnswer)) {
                    takenAnswers.add(mRandAnswer);
                    btns[i].setText(am.get(mRandAnswer).getmAnswers());
                } else {
                    mRandAnswer = r2.nextInt(16);
                    i--;
                }
            }
        }

    }

    public void onAnswerClicked(View v) {
        switch(v.getId()) {
            case R.id.btn1:
                if(btns[0].getText().toString().equals(answerText)) {
                    btns[0].setBackgroundColor(Color.GREEN);
                    score++;
                } else {
                    Animation shakeButton = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                    btns[0].startAnimation(shakeButton);
                    btns[0].setBackgroundColor(Color.RED);
                    btns[btnPlacementNum].setBackgroundColor(Color.GREEN);
                }
                for (int i = 0; i < btns.length; i++) {
                    btns[i].setEnabled(false);
                }
                break;

            case R.id.btn2:
                if(btns[1].getText().toString().equals(answerText)) {
                    btns[1].setBackgroundColor(Color.GREEN);
                    score++;
                } else {
                    Animation shakeButton = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                    btns[1].startAnimation(shakeButton);
                    btns[1].setBackgroundColor(Color.RED);
                    btns[btnPlacementNum].setBackgroundColor(Color.GREEN);
                }
                for (int i = 0; i < btns.length; i++) {
                    btns[i].setEnabled(false);
                }
                break;

            case R.id.btn3:
                if(btns[2].getText().toString().equals(answerText)) {
                    btns[2].setBackgroundColor(Color.GREEN);
                    score++;
                } else {
                    Animation shakeButton = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                    btns[2].startAnimation(shakeButton);
                    btns[2].setBackgroundColor(Color.RED);
                    btns[btnPlacementNum].setBackgroundColor(Color.GREEN);
                }
                for (int i = 0; i < btns.length; i++) {
                    btns[i].setEnabled(false);
                }
                break;

            case R.id.btn4:
                if(btns[3].getText().toString().equals(answerText)) {
                    btns[3].setBackgroundColor(Color.GREEN);
                    score++;
                } else {
                    Animation shakeButton = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                    btns[3].startAnimation(shakeButton);
                    btns[3].setBackgroundColor(Color.RED);
                    btns[btnPlacementNum].setBackgroundColor(Color.GREEN);
                }
                for (int i = 0; i < btns.length; i++) {
                    btns[i].setEnabled(false);
                }
                break;
        }
    }
}