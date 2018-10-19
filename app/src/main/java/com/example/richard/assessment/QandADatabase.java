package com.example.richard.assessment;

import android.widget.Button;

import java.util.ArrayList;

public class QandADatabase {

    //Array list for questions
    public static ArrayList<QuestionsModel>questionsList = new ArrayList<QuestionsModel>(){{

        add(new QuestionsModel(1, "Why is it the way it is?"));
        add(new QuestionsModel(2, "What primitive data type can we use to display numbers?"));
        add(new QuestionsModel(3, "What primitive data type can we use to display text?"));
        add(new QuestionsModel(4, "Was that your phone in the lost and found?"));

    }};


    //Array list for answers
    public static ArrayList<AnswersModel>answersList = new ArrayList<AnswersModel>(){{

        add(new AnswersModel(1, "because it is"));
        add(new AnswersModel(2, "an INT"));
        add(new AnswersModel(3, "a String"));
        add(new AnswersModel(4, "yup, das it!"));

    }};


    //use this to retrieve the questions array list
    public static ArrayList<QuestionsModel> getQuestionsArrayList() {
        return questionsList;
    }

    //use this to retrieve the answers array list
    public static ArrayList<AnswersModel> getAnswersArrayList() {
        return answersList;
    }



}
