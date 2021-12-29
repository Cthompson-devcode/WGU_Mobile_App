package com.example.finalmobileapplication.Database;

import android.content.AsyncQueryHandler;
import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.finalmobileapplication.DAO.AssessmentDAO;
import com.example.finalmobileapplication.DAO.CoursesDAO;
import com.example.finalmobileapplication.DAO.NotesDAO;
import com.example.finalmobileapplication.DAO.TermsDAO;
import com.example.finalmobileapplication.Entities.Assessments;
import com.example.finalmobileapplication.Entities.Courses;
import com.example.finalmobileapplication.Entities.Notes;
import com.example.finalmobileapplication.Entities.Terms;


@Database( entities = {Terms.class, Courses.class, Assessments.class, Notes.class}, version =11)
public abstract class SchoolDatabase extends RoomDatabase {

private static SchoolDatabase instance;

public abstract TermsDAO termsDAO(); //Room subclasses our abstract class
    public abstract  CoursesDAO coursesDAO();
    public abstract NotesDAO notesDAO();
    public abstract  AssessmentDAO assessmentDAO();

public static synchronized SchoolDatabase getInstance(Context context){
    if (instance == null){

        synchronized (SchoolDatabase.class)
        {
            if(instance == null){
                instance = Room.databaseBuilder(context.getApplicationContext(),
                        SchoolDatabase.class, "FinalAppDatabase.db")
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()

                        .addCallback(roomCallback)//prepopultes table data from roomCallback
                        .build();

            }
        }


    }
    return instance;
}
////////////////////////////

private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
    @Override
    public void onCreate(@NonNull SupportSQLiteDatabase db) {
        super.onCreate(db);
        new PopulateDbAsyncTask(instance).execute();
    }
};

////////////////////////////////////

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {

       private final TermsDAO termsDAO;
       private final CoursesDAO coursesDAO;
       private final NotesDAO notesDAO;
       private final AssessmentDAO assessmentDAO;

       PopulateDbAsyncTask(SchoolDatabase db){
            termsDAO = db.termsDAO();
            coursesDAO = db.coursesDAO();
            notesDAO = db.notesDAO();
            assessmentDAO = db.assessmentDAO();


        }

        @Override
        protected Void doInBackground(Void... params) {

           Terms terms = new Terms ("Term 1", "01/01/2021", "06/01/2021");
           termsDAO.insert(terms);
           terms = new Terms ("Summer", "06/02/2021", "07/01/2021");
            termsDAO.insert(terms);


            Courses courses = new Courses ( 1,  "Math", "01/01/2021", "06/01/2021", 2, "John Smith", "8888888", "js@gmil.com", false );
                coursesDAO.insert(courses);

           // CoursesDAO.insert(new Courses("Math", "01/02/2021","06/01/2021", ))

            Notes notes = new Notes (1, "New Note", "Note Info" );
            notesDAO.insert(notes);

            Assessments assessments = new Assessments ("Final Exam", "Objective", "01/01/2021", 1 );
            assessmentDAO.insert(assessments);

            return null;
        }
    }



}


