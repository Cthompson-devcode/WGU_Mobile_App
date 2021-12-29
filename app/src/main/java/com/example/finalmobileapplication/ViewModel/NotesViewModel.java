package com.example.finalmobileapplication.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.finalmobileapplication.Database.SchoolRepository;
import com.example.finalmobileapplication.Entities.Courses;
import com.example.finalmobileapplication.Entities.Notes;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class NotesViewModel extends AndroidViewModel {

        private SchoolRepository repository;
        private LiveData<List<Notes>> allNotes;


    public NotesViewModel(@NonNull Application application) {
            super(application);

            repository = new SchoolRepository(application);
            allNotes = repository.getAllNotes();

        }


        public void insert(Notes notes){

            repository.insert(notes);

        }
        public void update(Notes notes)
        {
            repository.update(notes);

        }

        public void delete(Notes notes)
        {
            repository.delete(notes);
        }

        public void deleteAllNotes (){
            repository.deleteAllNotes();
        }

        public LiveData<List<Notes>> getAllNotes()
        {
            return allNotes;
        }


        public LiveData<List<Notes>> getLiveCourseNotes(int courseID) {
            return repository.getLiveCourseNotes(courseID);
        }

    }
