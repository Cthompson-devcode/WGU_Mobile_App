package com.example.finalmobileapplication.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName ="assessments")


public class Assessments {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name="assessmentName")
    public String assessmentName;

    @ColumnInfo(name="assessmentType")
    public String assessmentType;

    @ColumnInfo(name="dueDate")
    public String dueDate;

    @ColumnInfo(name="coursesID")
    public int coursesID;



    public Assessments(String assessmentName, String assessmentType, String dueDate, int coursesID) {
        this.assessmentName = assessmentName;
        this.assessmentType = assessmentType;
        this.dueDate = dueDate;
        this.coursesID = coursesID;
    }





    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getAssessmentName() {
        return assessmentName;
    }

    public void setAssessmentName(String assessmentName) {
        this.assessmentName = assessmentName;
    }

    public String getAssessmentType() {
        return assessmentType;
    }

    public void setAssessmentType(String assessmentType) {
        this.assessmentType = assessmentType;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public int getCoursesID() {
        return coursesID;
    }

    public void setCoursesID(int coursesID) {
        this.coursesID = coursesID;
    }






}
