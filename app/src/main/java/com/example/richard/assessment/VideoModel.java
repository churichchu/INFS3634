package com.example.richard.assessment;

import android.content.res.Resources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VideoModel {
    private ArrayList<String> videoIds, videoTitles, videoModules, moduleDescriptions;
    String videoId, videoTitle, videoModule, moduleDescription;
    Resources res;

    public VideoModel() {
        videoIds = new ArrayList<>();
        videoTitles = new ArrayList<>();
        videoModules = new ArrayList<>();
        moduleDescriptions = new ArrayList<>();

        videoIds.add("6feMtVOaSy4");
        videoIds.add("nmDpnm8RtdI");
        videoIds.add("T9d40CdzW-M");
        videoIds.add("5dszS8TxTJ4");

        videoTitles.add("Primary Key and Foreign Key Tutorial");
        videoTitles.add("How to draw an ER diagram and a Relational Schema");
        videoTitles.add("SQL Basics: Creating Tables and Inserting Data");
        videoTitles.add("SQL: Data Manipulation Language");

        videoModules.add("Tables, Primary Keys, Foreign Keys and Relationships");
        videoModules.add("Entity Relationship Diagram and Relational Models");
        videoModules.add("The CREATE TABLE and INSERT INTO Statements");
        videoModules.add("Basic SQL Statements");

        moduleDescriptions.add("Learn about tables, primary keys, foreign keys \nand relationships.");
        moduleDescriptions.add("Learn about entity relationship diagrams and\nrelational models.");
        moduleDescriptions.add("Learn how to create a database in SQL\n using the CREATE TABLE Statement and \nthe INSERT INTO Statement.");
        moduleDescriptions.add("Learn about different statements you can\n use to query the database, such as\n SELECT and UPDATE statements.");

        getVideoModel();
    }

    public VideoModel(String videoId, String videoTitle, String videoModule, String moduleDescriptions) {
        videoId = this.videoId;
        videoTitle = this.videoTitle;
        videoModule = this.videoModule;
        moduleDescription = this.moduleDescription;
    }

    public  ArrayList<VideoModel> getVideoModel() {
        ArrayList<VideoModel> videoModel = new ArrayList<VideoModel>();
        for (int i = 0; i < moduleDescriptions.size(); i++) {
            videoModel.add(new VideoModel(videoIds.get(i), videoTitles.get(i), videoModules.get(i), moduleDescriptions.get(i)));
        }
        return videoModel;
    }

    public ArrayList<String> getVideoId() {
        return this.videoIds;
    }

    public ArrayList<String> getVideoTitle() {
        return this.videoTitles;
    }

    public ArrayList<String> getVideoModule() {
        return this.videoModules;
    }

    public ArrayList<String> getModuleDescription() { return this.moduleDescriptions; }

}

