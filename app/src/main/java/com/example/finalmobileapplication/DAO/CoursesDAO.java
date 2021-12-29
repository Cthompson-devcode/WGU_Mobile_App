package com.example.finalmobileapplication.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.finalmobileapplication.Entities.Courses;

import java.util.List;

@Dao
public interface CoursesDAO {

    @Insert//(onConflict = OnConflictStrategy.IGNORE)
    void insert(Courses courses);

    @Update
    void update(Courses courses);

    @Delete
    void delete(Courses courses);


    @Query("DELETE FROM course_table")
    void deleteAllCourses();

    @Query("SELECT * FROM course_table WHERE termsID= :termsID ORDER BY uid ASC")
    LiveData<List<Courses>> getCoursesfromTermLive (int termsID);

    @Query("SELECT * FROM course_table WHERE termsID = :termsID ORDER BY uid ASC")
    List<Courses>getCoursesFromTerm (int termsID);

    @Query("SELECT * FROM course_table ORDER BY uid ASC")
   LiveData< List<Courses>>getAllCourses();

    @Query("SELECT * FROM course_table ORDER BY uid ASC")
    List<Courses> getAllCoursesreg();
}
