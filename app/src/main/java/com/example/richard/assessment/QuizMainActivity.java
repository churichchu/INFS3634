package com.example.richard.assessment;

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
    String questionText, answerText, randAnswer;
    Button next;
    Button[] btns;
    TextView tv;
    int clickCount = 0;

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

                if(clickCount == NUM_QUESTIONS - 1) {
                    next.setOnClickListener(null);
                }

                for(int i = 0; i < takenAnswers.size(); i++) {
                    System.out.println(takenAnswers.get(i));
                }
            }
        });

    }

    public void getQuestionsAnswers() {
        qm = QandADatabase.getQuestionsArrayList();
        am = QandADatabase.getAnswersArrayList();
    }

    public void multipleChoiceQuiz() {
        mQnANum = r.nextInt(3 + 1);
        while(takenAnswers.contains(mQnANum)) {
            mQnANum = r.nextInt(3 + 1);
        }
        btnPlacementNum = r.nextInt(3 + 1);
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

    /*public void onNext(View v) {
        multipleChoiceQuiz();
    }*/




        //IF statement so that random mcq answers cannot
        //be placed int he same box as the correct answer


        //Buttons array to randomly place answers for generated questions








        //populate answers



        //int i = 0;
        /*while (i < btns.length) {
            int mRandAnswer = r.nextInt(3 - 0 + 1) + 0;
        }*/







        //IF statement to make sure random generated answers
        //are not the same as the correct answer nor previously
        //generated ones
        //int randomNum = ThreadLocalRandom.current().nextInt(am.get(0).getId(), btns.length + 1);





        /*Button randBtn = btns[btnPlacementNum];
        for(int i = 1; i < btns.length; i++) {

            if (btns[btnPlacementNum].getText().equals("Button")) {
                randBtn.setText(randAnswer);
                takenAnswers.add(randAnswer);
            }
            else{

                btnPlacementNum = r3.nextInt(4);
                i--;

            }
        }

        for(int i = 0; i < btns.length; i++){
            if(takenAnswers.contains(randAnswer)){

            }
        }*/










}


