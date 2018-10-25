package com.example.richard.assessment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
//import android.support.annotation.RequiresApi;
import android.support.v4.content.LocalBroadcastManager;
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

public class QuizMainActivity extends AppCompatActivity {

    final int NUM_ANSWERS = 4;
    final int NUM_QUESTIONS = 4;
    final double passMark = 0.8;
    int mQnANum, btnPlacementNum, mRandAnswer;
    int clickCount = 0;
    int score = 0;
    int questionLimit = 0;

    String questionText, answerText, randAnswer;
    Button next, results;
    Button[] btns;
    TextView tv;
    Random r;




    ArrayList<QuestionsModel> qm = new ArrayList<QuestionsModel>();
    ArrayList<AnswersModel> am = new ArrayList<AnswersModel>();
    ArrayList<String> takenAnswers = new ArrayList<String>();


    //@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_activity_main);

         BroadcastReceiver mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int receivedModule = intent.getIntExtra(ModuleActivity.passedMod,0);
                System.out.println(receivedModule);
                switch (receivedModule) {
                    case 1:
                        mQnANum = r.nextInt(3);
                        break;

                    case 2:
                        mQnANum = r.nextInt(7-3);
                        break;

                    case 3:
                        mQnANum = r.nextInt(11-7);
                        break;

                    case 4:
                        mQnANum = r.nextInt(16-11);
                        break;
                }
            }
        };

        //Receiving the broadcast from ModuleActivity
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, new IntentFilter());

        //random number generators
        r = new Random();
        Random r2 = new Random();
        Random r3 = new Random();

        //Question and answer array lists
        qm = QandADatabase.getQuestionsArrayList();
        am = QandADatabase.getAnswersArrayList();

        //arrays to store previously selected questions or answers
        //eliminate repeats
        String[] qmArray = new String[3];
        String[] amArray = new String[3];

        //MCQ answer buttons
        btns = new Button[4];
        btns[0] = (Button) findViewById(R.id.btn1);
        btns[1] = (Button) findViewById(R.id.btn2);
        btns[2] = (Button) findViewById(R.id.btn3);
        btns[3] = (Button) findViewById(R.id.btn4);

        //Randomly generated values to keep track of:
        //Question and Answer
        //The random answers
        //Randomly selected button
        //***IMPORTANT*** this section here determines the batch of questions related to the selected module!!
        //However only mQnANum and mRandAnswer is


        mRandAnswer = r2.nextInt(16);
        btnPlacementNum = r3.nextInt(4);

        //retrieving question, answer, and random answer text
        System.out.println(mQnANum);
        questionText = qm.get(mQnANum).getmQuestion();
        answerText = am.get(mQnANum).getmAnswers();
        randAnswer = am.get(mRandAnswer).getmAnswers();

        //IF so that Question and Answer values don't conflict with
        //random answer value
        if(mQnANum == mRandAnswer){
            mRandAnswer = r2.nextInt(16);
        }


        tv = findViewById(R.id.questionView);
        tv.setText(questionText);


        btns[btnPlacementNum].setText(answerText);
        takenAnswers.add(answerText);


        for(int i =0; i < btns.length; i++){

            if(btns[i].getText().toString().length() == 0){
                btns[i].setText(randAnswer);
                takenAnswers.add(randAnswer);
                mRandAnswer = r2.nextInt(16);
                randAnswer = am.get(mRandAnswer).getmAnswers();
            }else if(btns[i].getText().toString().contains(answerText)){
                mRandAnswer = r2.nextInt(16);
                randAnswer = am.get(mRandAnswer).getmAnswers();
                takenAnswers.add(randAnswer);
                btnPlacementNum = r3.nextInt(4);
                btns[btnPlacementNum].setText(randAnswer);
            }
        }

        /*for(int i = 0; i < btns.length; i++){
            if(!btns[i].getText().toString().equals(answerText)){
                mRandAnswer = r2.nextInt(16);
                if(!takenAnswers.contains(mRandAnswer)){
                    takenAnswers.add(randAnswer);
                    btns[i].setText(am.get(mRandAnswer).getmAnswers());
                }else{
                    mRandAnswer = r2.nextInt(16);
                }
            }

        }*/


        next = (Button) findViewById(R.id.nextQ);
        next.setEnabled(false);

        results = (Button) findViewById(R.id.see_results);
        results.setVisibility(View.INVISIBLE);

        for (int k = 0; k < btns.length; k++) {
            final int finalK = k;
            btns[k].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (btns[finalK].getText().toString().equals(answerText)) {
                        btns[finalK].setBackgroundColor(Color.GREEN);
                        Toast.makeText(getApplicationContext(), "Correct", Toast.LENGTH_LONG).show();
                        score++;
                        questionLimit++;
                        next.setEnabled(true);
                    } else {
                        Animation shakeButton = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                        btns[mQnANum].startAnimation(shakeButton);
                        btns[finalK].setBackgroundColor(Color.RED);
                        btns[mQnANum].setBackgroundColor(Color.GREEN);
                        questionLimit++;
                        next.setEnabled(true);
                    }
                    for (int i = 0; i < btns.length; i++) {
                        btns[i].setEnabled(false);
                    }
                }
            });
        }




        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(questionLimit);
                System.out.println(score);
                if(questionLimit == 4 && score >=2){
                    Toast.makeText(getApplicationContext(), "passed", Toast.LENGTH_LONG).show();
                    Intent ii = new Intent(QuizMainActivity.this, ModuleActivity.class);
                    startActivity(ii);
                }else if (questionLimit == 4 && score <=1){
                    Toast.makeText(getApplicationContext(), "Sorry, but you have failed the quiz", Toast.LENGTH_LONG).show();
                    Intent iii = new Intent(QuizMainActivity.this, ModuleActivity.class);
                    startActivity(iii);
                }
                else {
                    Intent i = new Intent(QuizMainActivity.this, QuizMainActivity.class);
                    startActivity(i);
                }
            }
        });
    }


}






        //selectQuestion();
        //populateAnswers();
        //getSelectedAnswer();
        //checkSelectedAnswer();


        //comment out start HERE*******************
        /* next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takenAnswers.clear();
                mQnANum = 0;
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
        });*/




        /*results.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuizMainActivity.this, ModuleActivity.class);
                intent.putExtra("score", score);
                double mark = score / NUM_QUESTIONS;
                if(mark >= passMark) {
                    intent.putExtra("pass", "pass");
                } else {
                    intent.putExtra("fail", "fail");
                }
                startActivity(intent);
            }
        });*/







    /*public void multipleChoiceQuiz() {
        //ensure same question is not shown twice
        //issue is that last question is always the same as the first
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


        tv.setText(questionText);

        takenAnswers.add(mQnANum);


        //randomise Answers *WORKS*
        for (int i = 0; i < NUM_ANSWERS; i++) {
            if(i == mQnANum) {
                btns[i].setText(answerText);
            } else {
                mRandAnswer = r.nextInt(3 + 1);
                while(takenAnswers.contains(mRandAnswer)) {
                    mRandAnswer = r.nextInt(3 + 1);
                }
                btns[i].setText(am.get(mRandAnswer).getmAnswers());
                takenAnswers.add(mRandAnswer);
            }
        }

        /*for (int j = 0; j < NUM_ANSWERS; j++) {
            while (r.nextInt(3 + 1) == mQnANum) {
                mRandAnswer = r.nextInt(3 + 1);
            }
            takenAnswers.add(mRandAnswer);
            if (j != mQnANum) {
                btns[j].setText(am.get(mRandAnswer).getmAnswers());
                mRandAnswer = r.nextInt(3 + 1);
            } else {
                j--;
            }
        }*/


        /*mRandAnswer = r.nextInt(3+1);
        if()
        while(mRandAnswer == mQnANum) {
            mRandAnswer = r.nextInt(3 + 1);
        }

        btns[mRandAnswer].setText(am.get(mRandAnswer).getmAnswers());

            for(int j = 0; j < NUM_ANSWERS; j++) {
                if (!btns[j].equals(answerText) && j != mRandAnswer) {
                    randAnswer = am.get(mRandAnswer).getmAnswers();
                    btns[j].setText(randAnswer);
                }
            }
        }*/






