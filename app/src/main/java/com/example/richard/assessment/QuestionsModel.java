package com.example.richard.assessment;

public class QuestionsModel {

    private int id;
    private String mQuestion = "";



    public QuestionsModel (int id, String mQuestion) {

        this.id = id;
        this.mQuestion = mQuestion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getmQuestion() {
        return mQuestion;
    }

    public void setmQuestion(String mQuestion) {
        this.mQuestion = mQuestion;
    }
}
