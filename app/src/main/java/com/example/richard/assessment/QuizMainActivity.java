package com.example.richard.assessment;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class QuizMainActivity extends AppCompatActivity {

    int questionSelect;
    final int NUM_ANSWERS = 4;
    String answerText;



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_activity_main);

        //retrieved questions array list
        ArrayList<QuestionsModel> qm = new ArrayList<>();
        qm = QandADatabase.getQuestionsArrayList();

        //retrieved answers array list
        ArrayList<AnswersModel> am = new ArrayList<>();
        am = QandADatabase.getAnswersArrayList();

        //Random Number generator for Questions and Answers
        //r2 is meant for generating random incorrect answers when making mcq

        Random r = new Random();
        Random r2 = new Random();
        Random r3 = new Random();

        int mQnANum = r.nextInt(3 - 0 + 1) + 0;
        int btnPlacementNum = r2.nextInt(3 - 0 + 1) + 0;


        //IF statement so that random mcq answers cannot
        //be placed int he same box as the correct answer
        if (btnPlacementNum == mQnANum) {
            btnPlacementNum = r3.nextInt(4);
        }

        //Buttons array to randomly place answers for generated questions
        final Button[] btns = new Button[4];
        btns[0] = (Button) findViewById(R.id.btn1);
        btns[1] = (Button) findViewById(R.id.btn2);
        btns[2] = (Button) findViewById(R.id.btn3);
        btns[3] = (Button) findViewById(R.id.btn4);

        String questionText = qm.get(mQnANum).getmQuestion();
        answerText = am.get(mQnANum).getmAnswers();
        String randAnswer;

        Button btn = btns[mQnANum];
        btn.setText(answerText);

        TextView tv = findViewById(R.id.questionText);
        tv.setText(questionText);

        //populate answers
        ArrayList<Integer> randNums = new ArrayList<Integer>();
        btns[mQnANum].setText(answerText);
        randNums.add(mQnANum);
        //int i = 0;
        /*while (i < btns.length) {
            int mRandAnswer = r.nextInt(3 - 0 + 1) + 0;
        }*/
        int mRandAnswer = 0;
        for(int i = 0; i < 20; i++) {
            mRandAnswer = r.nextInt(3 - 0 + 1) + 0;
            if (!btns[mRandAnswer].equals(answerText) && !randNums.contains(mRandAnswer)) {
                randAnswer = am.get(mRandAnswer).getmAnswers();
                btns[mRandAnswer].setText(randAnswer);
                randNums.add(mRandAnswer);
            }

        }



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

        btns[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btns[0].getText().toString().equals(answerText)) {
                    Toast.makeText(getApplicationContext(), "Correct", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Incorrect", Toast.LENGTH_LONG).show();
                }
                for (int i = 0; i < btns.length; i++) {
                    btns[i].setEnabled(false);
                }
            }
        });

        btns[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btns[1].getText().toString().equals(answerText)) {
                    Toast.makeText(getApplicationContext(), "Correct", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "InCorrect", Toast.LENGTH_LONG).show();
                }
                for (int i = 0; i < btns.length; i++) {
                    btns[i].setEnabled(false);
                }
            }
        });

        btns[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btns[2].getText().toString().equals(answerText)) {
                    Toast.makeText(getApplicationContext(), "Correct", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Incorrect", Toast.LENGTH_LONG).show();
                }
                for (int i = 0; i < btns.length; i++) {
                    btns[i].setEnabled(false);
                }
            }
        });

        btns[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btns[3].getText().toString().equals(answerText)) {
                    Toast.makeText(getApplicationContext(), "Correct", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Incorrect", Toast.LENGTH_LONG).show();
                }
                for (int i = 0; i < btns.length; i++) {
                    btns[i].setEnabled(false);
                }
            }
        });
    }



}


