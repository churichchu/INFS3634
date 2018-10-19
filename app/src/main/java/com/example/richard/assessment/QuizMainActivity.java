package com.example.richard.assessment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class QuizMainActivity extends AppCompatActivity {

    int questionSelect;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_activity_main);

        //Random Number generator for Questions and Answers
        //r2 is meant for generating random incorrect answers when making mcq
        Random r = new Random();
        Random r2 = new Random();
        Random r3 = new Random();

        int mQnANum = r.nextInt(4-1)+1;
        int mRandAnswer = r2.nextInt(4-1)+1;
        int btnPlacementNum = r3.nextInt(4);

        //IF statement so that random mcq answers cannot
        //be placed int he same box as the correct answer
        if(btnPlacementNum == mQnANum) {
            btnPlacementNum = r3.nextInt(4);
        }

        //Buttons array to randomly place answers for generated questions
        Button[] btns = new Button[4];
        btns[0] = (Button) findViewById(R.id.btn1);
        btns[1] = (Button) findViewById(R.id.btn2);
        btns[2] = (Button) findViewById(R.id.btn3);
        btns[3] = (Button) findViewById(R.id.btn4);

        ArrayList<String> takenAnswers = new ArrayList<>();



        //retrieved questions array list
        ArrayList<QuestionsModel> qm = new ArrayList<>();
        qm = QandADatabase.getQuestionsArrayList();

        //retrieved answers array list
        ArrayList<AnswersModel> am = new ArrayList<>();
        am = QandADatabase.getAnswersArrayList();


        String questionText = qm.get(mQnANum).getmQuestion();
        String answerText = am.get(mQnANum).getmAnswers();
        String randAnswer = am.get(mRandAnswer).getmAnswers();

        //IF statement to make sure random generated answers
        //are not the same as the correct answer nor previously
        //generated ones
        if(randAnswer.equals(randAnswer) || randAnswer.equals(answerText)){
            mRandAnswer = r2.nextInt(4-1)+1;
            randAnswer = am.get(mRandAnswer).getmAnswers();
        }


        TextView tv = findViewById(R.id.questionText);
        tv.setText(questionText);

        Button btn = btns[mQnANum];
        btn.setText(answerText);

        int ii = 0;

        for(int i = 1; i < btns.length; i++) {
            Button randBtn = btns[btnPlacementNum];

            if (btns[btnPlacementNum].getText().equals("Button")) {
                randBtn.setText(randAnswer);
                takenAnswers.add(randAnswer);

                ii++;
            }else if(btns[btnPlacementNum].getText().equals(takenAnswers.get(ii))){
                btnPlacementNum = r3.nextInt(4);
                randBtn = btns[btnPlacementNum];
            }
            else{

                btnPlacementNum = r3.nextInt(4);
                i--;

            }
        }
    }
}
