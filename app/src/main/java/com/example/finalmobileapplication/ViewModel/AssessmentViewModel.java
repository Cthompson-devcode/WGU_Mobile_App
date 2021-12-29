package com.example.finalmobileapplication.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.finalmobileapplication.Database.SchoolRepository;
import com.example.finalmobileapplication.Entities.Assessments;
import com.example.finalmobileapplication.Entities.Courses;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class AssessmentViewModel extends AndroidViewModel {

    private SchoolRepository repository;
    private LiveData<List<Assessments>> allAssessments;


    public AssessmentViewModel(@NonNull Application application) {
        super(application);

        repository = new SchoolRepository(application);
        allAssessments = repository.getAllAssessments();

    }


    public void insert(Assessments assessments){

        repository.insert(assessments);

    }
    public void update(Assessments assessments)
    {
        repository.update(assessments);

    }

    public void delete(Assessments assessments)
    {
        repository.delete(assessments);
    }

    public void deleteAllAssessments (){
        repository.deleteAllAssessments();
    }

    public LiveData<List<Assessments>> getAllAssessments()
    {
        return allAssessments;
    }


    public List<Assessments> getCourseAssessment(int courseID) throws ExecutionException, InterruptedException {
        return repository.getAssessmentFromCourse(courseID);
    }

    public LiveData<List<Assessments>> getLiveCourseAssessment(int courseID) {
        return repository.getLiveAssessmentsFromCourse(courseID);
    }

}
