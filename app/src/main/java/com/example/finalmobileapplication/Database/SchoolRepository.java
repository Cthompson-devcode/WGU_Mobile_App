package com.example.finalmobileapplication.Database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.finalmobileapplication.DAO.AssessmentDAO;
import com.example.finalmobileapplication.DAO.CoursesDAO;
import com.example.finalmobileapplication.DAO.NotesDAO;
import com.example.finalmobileapplication.DAO.TermsDAO;
import com.example.finalmobileapplication.Entities.Assessments;
import com.example.finalmobileapplication.Entities.Courses;
import com.example.finalmobileapplication.Entities.Notes;
import com.example.finalmobileapplication.Entities.Terms;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class SchoolRepository {

    private List<Courses> mAllCourses;
    public TermsDAO termsDAO;
    private LiveData<List<Terms>> allTerms;

    private CoursesDAO ccoursesDAO;
    private LiveData<List<Courses>> allCourses;
    private List<Courses>allcoursesreg;

    private NotesDAO notesDAO;
    private LiveData<List<Notes>> allNotes;
    private List<Notes>mAllNotes;

    int courseID;



    private AssessmentDAO assessmentDAO;
    private LiveData<List<Assessments>> allAssessments;
    private List<Assessments> mAllAssessments;


    public SchoolRepository(Application application){
        SchoolDatabase database = SchoolDatabase.getInstance(application);
        termsDAO = database.termsDAO();
        ccoursesDAO = database.coursesDAO();
        allTerms = termsDAO.getAllTerms();
        allCourses = ccoursesDAO.getAllCourses();
        allcoursesreg = ccoursesDAO.getAllCoursesreg();
        notesDAO = database.notesDAO();
        assessmentDAO = database.assessmentDAO();
        allNotes = notesDAO.getAllNotes();
        allAssessments = assessmentDAO.getAllAssessments();




    }


    /** Insert statements:  *///////////////////////////////////
    public void insert (Terms terms)    {

        new InsertTermAsyncTask(termsDAO).execute(terms);
    }

    public void insertCourse (Courses courses)    {



        new InsertCourseAsyncTask(ccoursesDAO).execute(courses);
    }

    public void insert (Notes notes)    {

        new InsertNotesAsyncTask(notesDAO).execute(notes);
    }

    public void insert (Assessments assessments)    {

        new InsertAssessmentsAsyncTask(assessmentDAO).execute(assessments);
    }





    ////////Update /////////////////////////
    public void update (Terms terms) {

        new UpdateTermAsyncTask(termsDAO).execute(terms);

    }

    public void update (Courses courses) {

        new UpdateCoursesAsyncTask(ccoursesDAO).execute(courses);

    }


    public void update (Notes notes) {

        new UpdateNotesAsyncTask(notesDAO).execute(notes);

    }

    public void update (Assessments assessments) {

        new UpdateAssessmentsAsyncTask(assessmentDAO).execute(assessments);

    }





    /// deleting ivndividual records /////////

    public void delete (Terms terms){

        new DeleteTermAsyncTask(termsDAO).execute(terms);
    }

    public void delete (Courses courses){

        new DeleteCoursesAsyncTask(ccoursesDAO).execute(courses);
    }

    public void delete (Notes notes){

        new DeleteNotesAsyncTask(notesDAO).execute(notes);
    }


    public void delete (Assessments assessments){

        new DeleteAssessmentsAsyncTask(assessmentDAO).execute(assessments);
    }




    // DELETING ALL INFORMATION FROM CLASS

    public void deleteAllTerms() {
        new DeleteAllTermsAsyncTask(termsDAO).execute();
    }
    public void deleteAllCourses() {
        new DeleteAllCoursesAsyncTask(ccoursesDAO).execute();
    }

    public void deleteAllNotes() {
        new DeleteAllNotesAsyncTask(notesDAO).execute();
    }
    public void deleteAllAssessments() {
        new DeleteAllAssessmentAsyncTask(assessmentDAO).execute();
    }




    // getting all of one class

    public LiveData<List<Terms>> getAllTerms(){
        return allTerms;
    }

    public LiveData<List<Courses>> getAllCourses(){
        return allCourses;
    }
    public List<Courses> getAllCourses1(){

        mAllCourses = ccoursesDAO.getAllCoursesreg();

        return mAllCourses;
    }

    public List<Notes>getAllNotes1(){
        mAllNotes = notesDAO.getAllNotesreg();
        return mAllNotes;
    }

    public List<Assessments> getAllAssessments1(){
        mAllAssessments = assessmentDAO.getAllAssessmentsreg();

        return  mAllAssessments;

    }

    public LiveData<List<Assessments>> getAssessmentFromCourse() {return allAssessments;}

    public LiveData<List<Notes>> getNotesFromAssessment() {return  allNotes;}

    public LiveData<List<Notes>> getAllNotes(){
        return allNotes;
    }
    public LiveData<List<Assessments>> getAllAssessments(){
        return allAssessments;
    }




    public List<Courses> getCoursesFromTerm(int termID) {

        return ccoursesDAO.getCoursesFromTerm(termID);
    }

    public LiveData<List<Courses>> getLiveTermCourses(int termID) {

        return ccoursesDAO.getCoursesfromTermLive(termID);
    }

    public List<Assessments> getAssessmentFromCourse(int courseID) {
        return assessmentDAO.getCourseAssessmentsList(courseID);
    }

    public LiveData<List<Assessments>> getLiveAssessmentsFromCourse(int courseID) {
        return assessmentDAO.getCourseAssessments(courseID);
    }

    public LiveData<List<Notes>> getLiveCourseNotes(int courseID) {
        return notesDAO.getNotesFromCourse(courseID);
    }




    private  static class InsertTermAsyncTask extends AsyncTask<Terms, Void, Void> {

            private TermsDAO termsDAO;
            private InsertTermAsyncTask (TermsDAO termsDAO){

                this.termsDAO = termsDAO;
            }


        @Override
        protected Void doInBackground(Terms... terms) {
                termsDAO.insert(terms[0]);
            return null;
        }
    }


    private  static class InsertCourseAsyncTask extends AsyncTask<Courses, Void, Void> {

        private CoursesDAO coursesDAO;
        private InsertCourseAsyncTask (CoursesDAO coursesDAO){

            this.coursesDAO =coursesDAO;
        }


        @Override
        protected Void doInBackground(Courses... courses) {
            coursesDAO.insert(courses[0]);
            return null;
        }
    }

    private  static class InsertNotesAsyncTask extends AsyncTask<Notes, Void, Void> {

        private NotesDAO notesDAO;
        private InsertNotesAsyncTask (NotesDAO notesDAO){

            this.notesDAO = notesDAO;
        }


        @Override
        protected Void doInBackground(Notes... notes) {
            notesDAO.insert(notes[0]);
            return null;
        }
    }

    private  static class InsertAssessmentsAsyncTask extends AsyncTask<Assessments, Void, Void> {

        private AssessmentDAO assessmentDAO;
        private InsertAssessmentsAsyncTask (AssessmentDAO assessmentDAO) {

            this.assessmentDAO = assessmentDAO;
        }


        @Override
        protected Void doInBackground(Assessments... assessments) {
            assessmentDAO.insert(assessments[0]);
            return null;
        }
    }



    ///////UPDATING RECORDS FOR TABLE ////////////////

    private  static class UpdateTermAsyncTask extends AsyncTask<Terms, Void, Void> {

        private TermsDAO termsDAO;
        private UpdateTermAsyncTask (TermsDAO termsDAO){

            this.termsDAO = termsDAO;
        }


        @Override
        protected Void doInBackground(Terms... terms) {
            termsDAO.update(terms[0]);
            return null;
        }
    }

    private  static class UpdateCoursesAsyncTask extends AsyncTask<Courses, Void, Void> {

        private CoursesDAO coursesDAO;
        private UpdateCoursesAsyncTask (CoursesDAO coursesDAO){

            this.coursesDAO = coursesDAO;
        }


        @Override
        protected Void doInBackground(Courses... courses) {
            coursesDAO.update(courses[0]);
            return null;
        }
    }


    private  static class UpdateNotesAsyncTask extends AsyncTask<Notes, Void, Void> {

        private NotesDAO notesDAO;
        private UpdateNotesAsyncTask (NotesDAO notesDAO){

            this.notesDAO = notesDAO;
        }


        @Override
        protected Void doInBackground(Notes... notes) {
            notesDAO.update(notes[0]);
            return null;
        }
    }

    private  static class UpdateAssessmentsAsyncTask extends AsyncTask<Assessments, Void, Void> {

        private AssessmentDAO assessmentDAO;
        private UpdateAssessmentsAsyncTask (AssessmentDAO assessmentDAO){

            this.assessmentDAO = assessmentDAO;
        }


        @Override
        protected Void doInBackground(Assessments... assessments) {
            assessmentDAO.update(assessments[0]);
            return null;
        }
    }

    ///////Deleting individual rows in table db

    private  static class DeleteTermAsyncTask extends AsyncTask<Terms, Void, Void> {

        private TermsDAO termsDAO;
        private DeleteTermAsyncTask (TermsDAO termsDAO){

            this.termsDAO = termsDAO;
        }


        @Override
        protected Void doInBackground(Terms... terms) {
            termsDAO.delete(terms[0]);
            return null;
        }
    }

    private  static class DeleteCoursesAsyncTask extends AsyncTask<Courses, Void, Void> {

        private CoursesDAO coursesDAO;
        private DeleteCoursesAsyncTask (CoursesDAO coursesDAO){

            this.coursesDAO = coursesDAO;
        }


        @Override
        protected Void doInBackground(Courses... courses) {
            coursesDAO.delete(courses[0]);
            return null;
        }
    }

    private  static class DeleteAssessmentsAsyncTask extends AsyncTask<Assessments, Void, Void> {

        private AssessmentDAO assessmentDOA;
        private DeleteAssessmentsAsyncTask (AssessmentDAO assessmentDAO){

            this.assessmentDOA = assessmentDAO;
        }


        @Override
        protected Void doInBackground(Assessments... assessments) {
            assessmentDOA.delete(assessments[0]);
            return null;
        }
    }

    private  static class DeleteNotesAsyncTask extends AsyncTask<Notes, Void, Void> {

        private NotesDAO notesDAO;
        private DeleteNotesAsyncTask (NotesDAO notesDAO){

            this.notesDAO = notesDAO;
        }


        @Override
        protected Void doInBackground(Notes... notes) {
            notesDAO.delete(notes[0]);
            return null;
        }
    }




    /// DELETING ALL DATA IN DB////////

    private  static class DeleteAllTermsAsyncTask extends AsyncTask<Terms, Void, Void> {

        private TermsDAO termsDAO;
        private DeleteAllTermsAsyncTask (TermsDAO termsDAO){

            this.termsDAO = termsDAO;
        }


        @Override
        protected Void doInBackground(Terms... terms) {
            termsDAO.deleteAllTerms();
            return null;
        }
    }

    private  static class DeleteAllCoursesAsyncTask extends AsyncTask<Courses, Void, Void> {

        private CoursesDAO coursesDAO;
        private DeleteAllCoursesAsyncTask (CoursesDAO coursesDAO ){

            this.coursesDAO = coursesDAO;
        }


        @Override
        protected Void doInBackground(Courses... courses) {
            coursesDAO.deleteAllCourses();
            return null;
        }
    }

    private  static class DeleteAllAssessmentAsyncTask extends AsyncTask<Assessments, Void, Void> {

        private AssessmentDAO assessmentDAO;
        private DeleteAllAssessmentAsyncTask (AssessmentDAO assessmentDAO ){

            this.assessmentDAO = assessmentDAO;
        }


        @Override
        protected Void doInBackground(Assessments... assessments) {
            assessmentDAO.deleteAllAssessments();
            return null;
        }
    }

    private  static class DeleteAllNotesAsyncTask extends AsyncTask<Notes, Void, Void> {

        private NotesDAO notesDAO;

        private DeleteAllNotesAsyncTask(NotesDAO notesDAO) {

            this.notesDAO = notesDAO;
        }


        @Override
        protected Void doInBackground(Notes... notes) {
            notesDAO.deleteAllNotes();
            return null;
        }


        private CoursesDAO coursesDAO;
        private AssessmentDAO assessmentDAO;
        ///////getting list data///////

        public List<Courses> getCoursesFromTerm(int termID) throws ExecutionException, InterruptedException {
            return new GetAsyncTermCourses(coursesDAO).execute(termID).get();
        }

        private static class GetAsyncTermCourses extends AsyncTask<Integer, Void, List<Courses>> {
            private CoursesDAO dao;

            private GetAsyncTermCourses(CoursesDAO dao) {
                this.dao = dao;
            }

            @Override
            protected List<Courses> doInBackground(Integer... Integers) {
                return dao.getCoursesFromTerm(Integers[0]);
            }
        }

        public LiveData<List<Notes>> getLiveNotesfromCourse(int courseID) {
            return notesDAO.getNotesFromCourse(courseID);
        }

        private static class GetAsyncCourseNotes extends AsyncTask<Integer, Void, List<Notes>> {
            private NotesDAO dao;

            private GetAsyncCourseNotes(NotesDAO dao) {
                this.dao = dao;
            }

            @Override
            protected List<Notes> doInBackground(Integer... Integers) {
                return dao.getNotesFromCourseList(Integers[0]);
            }
        }

        public LiveData<List<Notes>> getLiveCourseNotes(int courseID) {
            return notesDAO.getNotesFromCourse(courseID);
        }

        public LiveData<List<Assessments>> getLiveAssessmentsFromCourse(int courseID) {
            return assessmentDAO.getCourseAssessments(courseID);
        }

        private static class GetAsyncCourseAssessments extends AsyncTask<Integer, Void, List<Assessments>> {
            private AssessmentDAO dao;

            private GetAsyncCourseAssessments(AssessmentDAO dao) {
                this.dao = dao;
            }

            @Override
            protected List<Assessments> doInBackground(Integer... Integers) {
                return dao.getCourseAssessmentsList(Integers[0]);
            }
        }

    }
}
