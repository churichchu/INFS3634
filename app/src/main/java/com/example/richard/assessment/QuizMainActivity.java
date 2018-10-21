package com.example.richard.assessment;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
    String answerText, questionText, randAnswer;
    TextView question;
    int mQnANum, btnPlacementNum, mRandAnswer;
    final Button[] btns = new Button[NUM_ANSWERS];

    ArrayList<QuestionsModel> qm = new ArrayList<>();
    ArrayList<AnswersModel> am = new ArrayList<>();
    ArrayList<Integer> takenQ = new ArrayList<Integer>();

    //Random Number generator for Questions and Answers
    //r2 is meant for generating random incorrect answers when making mcq
    Random r = new Random();
    Random r2 = new Random();
    Random r3 = new Random();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_activity_main);

        question = findViewById(R.id.questionText);

        //Buttons array to randomly place answers for generated questions
        btns[0] = (Button) findViewById(R.id.btn1);
        btns[1] = (Button) findViewById(R.id.btn2);
        btns[2] = (Button) findViewById(R.id.btn3);
        btns[3] = (Button) findViewById(R.id.btn4);

        //retrieved questions array list

        qm = QandADatabase.getQuestionsArrayList();

        //retrieved answers array list

        am = QandADatabase.getAnswersArrayList();

        for(int i = 0; i < NUM_ANSWERS; i++) {
            mQnANum = r.nextInt((am.size() - 1)+ 1);
            btnPlacementNum = r2.nextInt((NUM_ANSWERS -1)+ 1);

            //IF statement so that random mcq answers cannot
            //be placed int he same box as the correct answer


            if(i == 0) {
                if (btnPlacementNum == mQnANum) {
                    btnPlacementNum = r3.nextInt(4);
                }
                questionText = qm.get(mQnANum).getmQuestion();
                answerText = am.get(mQnANum).getmAnswers();
                btns[btnPlacementNum].setText(answerText);
                takenQ.add(mQnANum);

                //IF statement to make sure random generated answers
                //are not the same as the correct answer nor previously
                //generated ones
                for(int j = 0; j < 20; j++) {
                    if (btnPlacementNum == mQnANum) {
                        btnPlacementNum = r3.nextInt(4);
                    }
                    mRandAnswer = r.nextInt(3 - 0 + 1) + 0;
                    if (!btns[mRandAnswer].equals(answerText) && !takenQ.contains(mRandAnswer)) {
                        randAnswer = am.get(mRandAnswer).getmAnswers();
                        btns[mRandAnswer].setText(randAnswer);
                        takenQ.add(mRandAnswer);
                    }
                }
            } else {
                mQnANum = r.nextInt((am.size() - 1)+ 1);
                if(!takenQ.contains(mQnANum)) {
                    questionText = qm.get(mQnANum).getmQuestion();
                    answerText = am.get(mQnANum).getmAnswers();
                    btns[btnPlacementNum].setText(answerText);
                    takenQ.add(mQnANum);
                    for(int j = 0; j < 20; j++) {
                        if (btnPlacementNum == mQnANum) {
                            btnPlacementNum = r3.nextInt(4);
                        }
                        mRandAnswer = r.nextInt(3 - 0 + 1) + 0;
                        if (!btns[mRandAnswer].equals(answerText) && !takenQ.contains(mRandAnswer)) {
                            randAnswer = am.get(mRandAnswer).getmAnswers();
                            btns[mRandAnswer].setText(randAnswer);
                            takenQ.add(mRandAnswer);
                        }
                    }
                } else {
                    mQnANum = r.nextInt((am.size() - 1)+ 1);
                }
            }

            for(int k = 0; k < btns.length; k++) {
                final int finalK = k;
                btns[k].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(btns[finalK].getText().toString().equals(answerText)) {
                            createDialogBox().setMessage("Correct!").show();
                        } else {
                            createDialogBox().setMessage("Incorrect!").show();
                        }
                        for (int i = 0; i < btns.length; i++) {
                            btns[i].setEnabled(false);

                        }
                    }
                });
            }
        }

    }

    public AlertDialog.Builder createDialogBox() {
        AlertDialog.Builder ansFeedback = new AlertDialog.Builder(this);
        ansFeedback.setTitle("Feedback");
        ansFeedback.setCancelable(false);
        ansFeedback.setPositiveButton("Next", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for(int i = 0; i < NUM_ANSWERS; i++) {

                }
            }
        });
        return ansFeedback;
    }



}


