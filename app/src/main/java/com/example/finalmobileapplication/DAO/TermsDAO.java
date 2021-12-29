package com.example.finalmobileapplication.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.finalmobileapplication.Entities.Terms;

import java.util.List;
@Dao
public interface TermsDAO {

    @Insert  //(onConflict = OnConflictStrategy.REPLACE)
    void insert(Terms terms);

    @Delete
    void delete(Terms terms );

    @Update
    void update(Terms terms);

    @Query("DELETE FROM term_table")
    void deleteAllTerms();

    @Query("SELECT * FROM term_table ORDER BY termsID ASC")
    LiveData<List<Terms>> getAllTerms();
}

