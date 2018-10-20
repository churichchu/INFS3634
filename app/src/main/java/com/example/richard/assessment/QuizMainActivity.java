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

        //IF statement to make sure random generated answers
        //are not the same as the correct answer nor previously
        //generated ones
        int mRandAnswer = 0;
        for(int i = 0; i < 20; i++) {
            mRandAnswer = r.nextInt(3 - 0 + 1) + 0;
            if (!btns[mRandAnswer].equals(answerText) && !randNums.contains(mRandAnswer)) {
                randAnswer = am.get(mRandAnswer).getmAnswers();
                btns[mRandAnswer].setText(randAnswer);
                randNums.add(mRandAnswer);
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

    public AlertDialog.Builder createDialogBox() {
        AlertDialog.Builder ansFeedback = new AlertDialog.Builder(this);
        ansFeedback.setTitle("Feedback");
        ansFeedback.setCancelable(false);
        ansFeedback.setPositiveButton("Next", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //go to next question
            }
        });
        return ansFeedback;
    }



}


