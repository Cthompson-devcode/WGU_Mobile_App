package com.example.finalmobileapplication.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.finalmobileapplication.Database.SchoolDatabase;
import com.example.finalmobileapplication.Database.SchoolRepository;
import com.example.finalmobileapplication.Entities.Courses;
import com.example.finalmobileapplication.Entities.Terms;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class CourseViewModel extends AndroidViewModel  {

    private SchoolRepository repository;
    private LiveData<List<Courses>> allCourses;


    public CourseViewModel(@NonNull Application application) {
        super(application);

        repository = new SchoolRepository(application);
        allCourses = repository.getAllCourses();

    }


    public void insert(Courses courses){

        repository.insertCourse(courses);

    }
    public void update(Courses courses)
    {
        repository.update(courses);

    }

    public void delete(Courses courses)
    {
        repository.delete(courses);
    }

    public void deleteAllCourses (){
        repository.deleteAllCourses();
    }

    public LiveData<List<Courses>> getAllCourses()
    {
        return allCourses;
    }


    public List<Courses> getTermCourses(int termID)  {
        return repository.getCoursesFromTerm(termID);
    }

    public LiveData<List<Courses>> getLiveTermCourses(int termID) {
        return repository.getLiveTermCourses(termID);
    }


}
