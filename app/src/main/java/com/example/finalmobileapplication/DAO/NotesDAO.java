package com.example.finalmobileapplication.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.finalmobileapplication.Entities.Courses;
import com.example.finalmobileapplication.Entities.Notes;

import java.util.List;

@Dao
public interface NotesDAO {

    @Insert(onConflict  = OnConflictStrategy.REPLACE)
    void insert (Notes notes);

    @Update
    void update(Notes notes);

    @Delete
    void delete(Notes notes);

    @Query("SELECT * FROM notes WHERE coursesID = :coursesID ORDER BY uid ASC")
    LiveData<List<Notes>> getNotesFromCourse (int coursesID);

    @Query("SELECT * FROM notes WHERE coursesID = :coursesID ORDER BY uid ASC")
    List<Notes> getNotesFromCourseList (int coursesID);

    @Query("SELECT * FROM notes ORDER BY uid ASC")
    LiveData< List<Notes>>getAllNotes();

    @Query("DELETE FROM notes")
    void deleteAllNotes();

    @Query("SELECT * FROM notes ORDER BY uid ASC")
    List<Notes> getAllNotesreg();
}
