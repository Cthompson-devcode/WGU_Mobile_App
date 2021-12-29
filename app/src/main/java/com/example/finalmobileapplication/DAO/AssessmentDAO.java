package com.example.finalmobileapplication.DAO;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.finalmobileapplication.Entities.Assessments;
import com.example.finalmobileapplication.Entities.Courses;

import java.util.List;

@Dao
public interface AssessmentDAO {

    @Insert//(onConflict = OnConflictStrategy.REPLACE)
    void insert(Assessments assessments);

    @Update
    void update(Assessments assessments);

    @Delete
    void delete(Assessments assessments);

    @Query("SELECT * FROM assessments WHERE coursesID= :coursesID ORDER BY uid ASC")
    LiveData<List<Assessments>> getCourseAssessments(int coursesID);

    @Query("SELECT * FROM assessments WHERE coursesID= :coursesID ORDER BY uid ASC ")
    List<Assessments> getCourseAssessmentsList(int coursesID);

    @Query("SELECT * FROM assessments ORDER BY uid ASC")
    LiveData< List<Assessments>>getAllAssessments();

    @Query("SELECT * FROM assessments ORDER BY uid ASC")
    List<Assessments> getAllAssessmentsreg();

    @Query("DELETE FROM assessments")
    void deleteAllAssessments();
}
