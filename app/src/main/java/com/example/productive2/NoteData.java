package com.example.productive2;

//connecting note activity to the database.
// this class will have variables that will hold data from AddNote class.
//can add new notes from database and retrieve from database.

import java.util.ArrayList;

public class NoteData {
    private long ID;
    private String title, content, date, time;


    //create constructor to pass data from other activity

    //empty to create instance of first class.
    NoteData(){}

    NoteData(String title, String content, String date, String time){
        this.title = title;
        this.content = content;
        this.date = date;
        this.time = time;
    }
    //for editing or retrieving the notes.
    NoteData(long ID,String title, String content, String date, String time ){
        this.ID = ID;
        this.title = title;
        this.content = content;
        this.date = date;
        this.time = time;
    }
    // individually set and get the title, content, etc from database.
    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


}
