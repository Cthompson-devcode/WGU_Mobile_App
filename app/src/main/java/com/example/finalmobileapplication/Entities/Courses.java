package com.example.finalmobileapplication.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "course_table")


public class Courses {


    public Courses(int termsID, String title, String startDate, String endDate, int status, String name, String phone, String email, boolean alert) {
        this.termsID = termsID;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.alert = alert;

    }




    @PrimaryKey (autoGenerate = true)
    private int uid;

    @ColumnInfo(name="Title")
    private String title;

    @ColumnInfo(name="Start_Date")
    private String startDate;

    @ColumnInfo(name="End_Date")
    private String endDate;

    @ColumnInfo(name="Status")
    private int status;

    @ColumnInfo(name="Name")
    private String name;

    @ColumnInfo(name="Phone")
    private String phone;

    @ColumnInfo(name="Email")
    private String email;

    @ColumnInfo(name="Alert")
    private boolean alert;

    @ColumnInfo (name="termsID")
    private int termsID;



    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTermsID() {
        return termsID;
    }

    public void setTermsID(int termsID) {
        this.termsID = termsID;
    }

    public boolean isAlert() {
        return alert;
    }

    public void setAlert(Boolean alert) {
        alert = alert;
    }


}
