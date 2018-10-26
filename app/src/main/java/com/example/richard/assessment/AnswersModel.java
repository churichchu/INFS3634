package com.example.richard.assessment;

public class AnswersModel {

    private int id;
    private String mAnswers = "";


    //Model for Answers and fields involved
    public AnswersModel (int id, String mAnswers) {
        this.id = id;
        this.mAnswers = mAnswers;
    }

    //Answer ID
    public int getId() {
        return id;
    }

    //Sets the ID for each Answer
    public void setId(int id) {
        this.id = id;
    }

    //returns the actual Answer string from the database
    public String getmAnswers() {
        return mAnswers;
    }

    //sets the answer string 
    public void setmAnswers(String mAnswers) {
        this.mAnswers = mAnswers;
    }
}
