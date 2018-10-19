package com.example.richard.assessment;

public class AnswersModel {

    private int id;
    private String mAnswers = "";


    public AnswersModel (int id, String mAnswers) {
        this.id = id;
        this.mAnswers = mAnswers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getmAnswers() {
        return mAnswers;
    }

    public void setmAnswers(String mAnswers) {
        this.mAnswers = mAnswers;
    }
}
