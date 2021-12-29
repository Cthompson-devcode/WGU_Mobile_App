package com.example.finalmobileapplication.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "notes")

public class Notes {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name="coursesID")
    public int coursesID;

    @ColumnInfo(name="title")
    public String title;

    @ColumnInfo(name = "noteInfo")
    public String noteInfo;



    public Notes(int coursesID, String title, String noteInfo) {
        this.coursesID = coursesID;
        this.title = title;
        this.noteInfo = noteInfo;
    }






    public int getUid() {
        return uid;
    }
    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getCoursesID() {
        return coursesID;
    }

    public void setCoursesID(int coursesID) {
        this.coursesID = coursesID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNoteInfo() {
        return noteInfo;
    }

    public void setNoteInfo(String noteInfo) {
        this.noteInfo = noteInfo;
    }
}
