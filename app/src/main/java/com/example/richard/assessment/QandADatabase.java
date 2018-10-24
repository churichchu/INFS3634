package com.example.richard.assessment;

import android.widget.Button;

import java.util.ArrayList;

public class QandADatabase {

    //Array list for questions
    public static ArrayList<QuestionsModel>questionsList = new ArrayList<QuestionsModel>(){{

        //questions for primary key and foreign key tutorial
        add(new QuestionsModel(1, "Why is it the way it is?"));
        add(new QuestionsModel(2, "What primitive data type can we use to display numbers?"));
        add(new QuestionsModel(3, "What primitive data type can we use to display text?"));
        add(new QuestionsModel(4, "Was that your phone in the lost and found?"));

        //questions for ER diagram and relational schema
        add(new QuestionsModel(5, "What is the shape used to signify a Entity type?"));
        add(new QuestionsModel(6, "What must every entity have?"));
        add(new QuestionsModel(7, "What does 'high cardinality' refer to?"));
        add(new QuestionsModel(8, "How many attributes can an entity have?"));

        //questions for SQL basics
        add(new QuestionsModel(9, "What is the constraint syntax for setting a primary key?"));
        add(new QuestionsModel(10, "How do you remove a table from the database?"));
        add(new QuestionsModel(11, "How does the system know you are trying to set a primary key constraint?"));
        add(new QuestionsModel(12, "After creating a table, what is one way you can test if the table exists in the database?"));

        //answers for SQL data manipulation
        add(new QuestionsModel(13, "What data manipulation language do you use to add an entry into a table?"));
        add(new QuestionsModel(14, "You are given a table and must retrieve a specific attribute from that table, choose the correct statement"));
        add(new QuestionsModel(15, "What is the command to set a conditional query?"));
        add(new QuestionsModel(16, "How do you delete a row entry from a table in the database?"));

    }};


    //Array list for answers
    public static ArrayList<AnswersModel>answersList = new ArrayList<AnswersModel>(){{

        //questions for primary key and foreign key tutorial
        add(new AnswersModel(1, "because it is"));
        add(new AnswersModel(2, "an INT"));
        add(new AnswersModel(3, "a String"));
        add(new AnswersModel(4, "yup, das it!"));

        //answers for ER diagram and relational schema
        add(new AnswersModel(5, "A square"));
        add(new AnswersModel(6, "A primary key"));
        add(new AnswersModel(7, "high percentage of unique vales in a column"));
        add(new AnswersModel(8, "An infinite number of attributes"));

        //answers for SQL basics
        add(new AnswersModel(9, "primary key (name_of_table)"));
        add(new AnswersModel(10, "drop table table_name"));
        add(new AnswersModel(11, "by typing 'constraint pk_table_name "));
        add(new AnswersModel(12, "By going into the object explorer and refreshing the table listing. The table should be listed after refreshing."));

        //answers for SQL data manipulation
        add(new AnswersModel(13, "insert into table_name (table_name) value ('value')"));
        add(new AnswersModel(14, "select 'attribute name' from 'table_name';"));
        add(new AnswersModel(15, "where column_name = 'column entry'"));
        add(new AnswersModel(16, "delete from table_name where column_name = 'column entry'"));


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
