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

import java.util.ArrayList;
import java.util.Random;


import static com.example.richard.assessment.ModuleActivity.modNumber;


public class QuizMainActivity extends AppCompatActivity {

    //Field used in this activity
    final int NUM_ANSWERS = 4;
    final int NUM_QUESTIONS = 4;
    final double PASS_MARK = 0.5;

    //These fields determine the button placement,
    //rand answer generated
    int btnPlacementNum, mRandAnswer;
    String questionText, answerText;
    Button next, results;
    Button[] btns;
    TextView tv;
    int clickCount = 0;
    int score;
    Intent i = getIntent();

    //Random number generators for question number, answer,
    //and random answer sample answers
    Random r, r2, r3;


    //Initializing the Arraylists for question, answer, and answers generated
    ArrayList<QuestionsModel> qm = new ArrayList<>();
    ArrayList<AnswersModel> am = new ArrayList<>();
    ArrayList<Integer> takenAnswers = new ArrayList<Integer>();


    //This static int is responsible for determining the question and answer generated
    //according to the module selected in ModuleActivity
    static int mQnANum = 0;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_activity_main);

        //Initializing the random number generators
        r = new Random();
        r2 = new Random();
        r3 = new Random();

        //The textview to display the question in the Quiz
        tv = findViewById(R.id.questionView);

        //Button array to store the answers displayed with each quiz question
        btns = new Button[4];
        btns[0] = (Button) findViewById(R.id.btn1);
        btns[1] = (Button) findViewById(R.id.btn2);
        btns[2] = (Button) findViewById(R.id.btn3);
        btns[3] = (Button) findViewById(R.id.btn4);

        //next and result button for each question
        next = (Button) findViewById(R.id.nextQ);
        results = (Button) findViewById(R.id.see_results);

        //Methods implemented to selected a quniue question and to start the multiple choice instance
        getQuestionsAnswers();
        multipleChoiceQuiz();

        //Listener for keeping track of number of questions generated and limiting them
        //Also keeps track of scores based on correctly answered questions
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                System.out.println("Score = " + score);
            }
        });

        //Listener to respond with a pass or fail result based on scores attained by the end of the quiz
        results.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(QuizMainActivity.this, ModuleActivity.class);
                double mark = score / NUM_QUESTIONS;
                if (mark >= PASS_MARK) {
                    i.putExtra("pass", "pass");
                } else {
                    i.putExtra("fail", "fail");
                }
                startActivity(i);
            }
        });

    }

    //Method to retrieve questions from database
    public void getQuestionsAnswers() {
        qm = QandADatabase.getQuestionsArrayList();
        am = QandADatabase.getAnswersArrayList();
    }

    //Method to generate the MCQ and randomly generate sample answers after randomly allocating the right answer
    //in one of the buttons in the buttons array
    public void multipleChoiceQuiz() {

        if(modNumber == 1){
            mQnANum = r.nextInt(3 + 1 - 0);
            while (takenAnswers.contains(mQnANum)) {
                mQnANum = r.nextInt(3 + 1 - 0) + 0;
            }
        }

        else if(modNumber == 2){
            mQnANum = r.nextInt(7 + 1 - 4) + 4;
            while (takenAnswers.contains(mQnANum)) {
                mQnANum = r.nextInt(7 + 1 - 4) + 4;
            }
        }

        if(modNumber == 3){
            mQnANum = r.nextInt(11 + 1 - 8) + 8;
            while (takenAnswers.contains(mQnANum)) {
                mQnANum = r.nextInt(11 + 1 - 8) + 8;
            }
        }

        if(modNumber == 4){
            mQnANum = r.nextInt(15 + 1 - 12 ) + 12;
            while (takenAnswers.contains(mQnANum)) {
                mQnANum = r.nextInt(15 + 1 - 12) + 12;
            }
        }

        btnPlacementNum = r.nextInt(3 + 1);

        questionText = qm.get(mQnANum).getmQuestion();
        answerText = am.get(mQnANum).getmAnswers();

        btns[btnPlacementNum].setText(answerText);
        tv.setText(questionText);

        takenAnswers.add(mQnANum);

        //this for loop is actually the conditional loop that
        //prevents repeated answers in the MCQ per question generated
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

    //Added special effects for correct and incorrect answers selected
    //green animation if the correct answer is selected
    //red animation and button shake animation if the incorrect answer is selected
    public void onAnswerClicked(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                if (btns[0].getText().toString().equals(answerText)) {
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
                if (btns[1].getText().toString().equals(answerText)) {
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
                if (btns[2].getText().toString().equals(answerText)) {
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
                if (btns[3].getText().toString().equals(answerText)) {
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