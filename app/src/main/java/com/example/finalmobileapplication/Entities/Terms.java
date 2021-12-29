package com.example.finalmobileapplication.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;




    @Entity(tableName = "term_table")

    public class Terms {







        @PrimaryKey(autoGenerate = true)
        private int termsID;

        @ColumnInfo(name= "Term_Name")
        public String termName;

        @ColumnInfo(name = "Start_Date")
        public String startDate;

        @ColumnInfo(name ="End_Date")
        public String endDate;




        public Terms( String termName, String startDate, String endDate) {

            this.termName = termName;
            this.startDate = startDate;
            this.endDate = endDate;
        }


        public int getTermsID() {
            return termsID;
        }

        public void setTermsID(int termsID) {
            this.termsID = termsID;
        }

        public String getTermName() {
            return termName;
        }

        public void setTermName(String termName) {
            this.termName = termName;
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
    }



