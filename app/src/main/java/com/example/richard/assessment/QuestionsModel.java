package com.example.richard.assessment;

public class QuestionsModel {

    private int id;
    private String mQuestion = "";



    //Model for questions and the fields involved
    public QuestionsModel (int id, String mQuestion) {

        this.id = id;
        this.mQuestion = mQuestion;
    }

    //returns the question ID
    public int getId() {
        return id;
    }

    //sets the question ID
    public void setId(int id) {
        this.id = id;
    }

    //returns the actual question string
    public String getmQuestion() {
        return mQuestion;
    }

    //sets question string
    public void setmQuestion(String mQuestion) {
        this.mQuestion = mQuestion;
    }
}
