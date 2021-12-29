package com.example.finalmobileapplication.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.finalmobileapplication.Database.SchoolDatabase;
import com.example.finalmobileapplication.Database.SchoolRepository;
import com.example.finalmobileapplication.Entities.Terms;

import java.util.List;

public class TermViewModel extends AndroidViewModel  {

    private SchoolRepository repository;
    private LiveData<List<Terms>> allTerms;


    public TermViewModel(@NonNull Application application) {
        super(application);

        repository = new SchoolRepository(application);
        allTerms = repository.getAllTerms();
    }

    public void insert(Terms terms){

        repository.insert(terms);

    }
    public void update(Terms terms)
    {
        repository.update(terms);

    }

    public void delete(Terms terms)
    {
        repository.delete(terms);
    }

    public void deleteAllTerms (){
        repository.deleteAllTerms();
    }

    public LiveData<List<Terms>> getAllTerms()
    {
        return allTerms;
    }


}
