package com.example.finalmobileapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.finalmobileapplication.Adapter.AssessmentAdapter;
import com.example.finalmobileapplication.DAO.AssessmentDAO;
import com.example.finalmobileapplication.Database.SchoolRepository;
import com.example.finalmobileapplication.Entities.Assessments;
import com.example.finalmobileapplication.Entities.Courses;
import com.example.finalmobileapplication.ViewModel.AssessmentViewModel;
import com.example.finalmobileapplication.ViewModel.CourseViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AssessmentActivity extends AppCompatActivity {

    public static final String EXTRA_COURSE_ID =
            "com.example.finalmobileapplication.EXTRA_COURSE_ID";

    private int termID;
    private int courseID;
    public static final int ADD_COURSE_REQUEST = 1;
    public static final int EDIT_COURSE_REQUEST = 2;
    private AssessmentViewModel assessmentViewModel;
    private AssessmentDAO assessmentDAO;
    SchoolRepository schoolRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment);
        schoolRepository = new SchoolRepository(getApplication());
       // createNotificationChannel();

        setTitle("List of Assessments");

        FloatingActionButton buttonAddTerm = findViewById(R.id.button_add_term);

        buttonAddTerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (AssessmentActivity.this, AddAssessmentActivity.class);

                intent.putExtra(AddAssessmentActivity.EXTRA_COURSE_ID, courseID );
                System.out.println("THIS IS THE COURSEID SENT on the FLOATING ACTION BUTTON ------ "+courseID);
                startActivityForResult(intent, 1 );


            }
        });

        //recyclerview
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        Intent intent = getIntent();
    
        courseID = intent.getIntExtra(testAddEditCourse.EXTRA_COURSE_ID, -1);

        System.out.println(" THIS IS THE COURSE ID IN ASSESSMENTS =======" + courseID);

        // CourseAdapter adapter = new CourseAdapter();
        AssessmentAdapter adapter2 = new AssessmentAdapter();
        recyclerView.setAdapter(adapter2);
        //  recyclerView.setAdapter(adapter);

        assessmentViewModel = ViewModelProviders.of(this).get(AssessmentViewModel.class);

        //-------------------getting null pointer here------------

        List<Assessments> filteredAssessments = new ArrayList<>();
        for(Assessments p:schoolRepository.getAllAssessments1())
        {
            if(p.getCoursesID()==courseID)filteredAssessments.add(p);
        }
        adapter2.setAssessment(filteredAssessments);


      assessmentViewModel.getLiveCourseAssessment(courseID).observe(this, new Observer<List<Assessments>>() {
            @Override
            public void onChanged(@Nullable List<Assessments> filteredAssessments) {

                adapter2.setAssessments(filteredAssessments);


            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                assessmentViewModel.delete(adapter2.getAssessmentsAt(viewHolder.getAdapterPosition()));

                Toast.makeText(AssessmentActivity.this, "Assessment Deleted! ", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter2.setonItemClickListener(new AssessmentAdapter.onItemClickListener() {
            @Override

            public void onItemClick(Assessments assessments ) {
                //sending data that was clicked
                Intent intent = new Intent(AssessmentActivity.this, AddAssessmentActivity.class);
                //intent.putExtra(AddAssessmentActivity.EXTRA_TERM_ID, assessments.getCoursesID());

                System.out.println("THIS IS THE COURSE ID BEING SENT ON THE ITEM CLICK ====" + courseID);

                intent.putExtra(AddAssessmentActivity.EXTRA_ASSESSMENT_NAME, assessments.getAssessmentName());
                intent.putExtra(AddAssessmentActivity.EXTRA_START_DATE, assessments.getDueDate());
                intent.putExtra(AddAssessmentActivity.EXTRA_COURSE_ID, courseID);
                intent.putExtra(AddAssessmentActivity.EXTRA_ASSESSMENT_TYPE,assessments.getAssessmentType());
                intent.putExtra(AddAssessmentActivity.EXTRA_ASSESSMENT_ID, assessments.getUid());
                startActivityForResult(intent, EDIT_COURSE_REQUEST);

            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,@Nullable  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == ADD_COURSE_REQUEST && resultCode == RESULT_OK)
            {

                String name = data.getStringExtra(AddAssessmentActivity.EXTRA_ASSESSMENT_NAME);
                String type = data.getStringExtra(AddAssessmentActivity.EXTRA_ASSESSMENT_TYPE);
                String endDate = data.getStringExtra(AddAssessmentActivity.EXTRA_DUE_DATE);

//saving user input to database
                Assessments assessments = new Assessments ( name,  type, endDate, courseID);

                assessmentViewModel.insert(assessments);

        Toast.makeText(this, "Assessment Saved!", Toast.LENGTH_SHORT).show();

            }
            else if (requestCode == EDIT_COURSE_REQUEST && resultCode == RESULT_OK)
            {
                int id = data.getIntExtra(AddAssessmentActivity.EXTRA_ASSESSMENT_ID, -1);
                if ( id == -1)
                {
                    Toast.makeText(this, " Cannot Update Assessment", Toast.LENGTH_SHORT).show();
                    return;

                }

                String name = data.getStringExtra(AddAssessmentActivity.EXTRA_ASSESSMENT_NAME);
                String type = data.getStringExtra(AddAssessmentActivity.EXTRA_ASSESSMENT_TYPE);
                String endDate = data.getStringExtra(AddAssessmentActivity.EXTRA_DUE_DATE);

                Assessments assessments = new Assessments (  name,  type, endDate, courseID);
                assessments.setUid(id);
                assessmentViewModel.update(assessments);

                Toast.makeText(this, "Assessment Updated!", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this, "Assessment NOT Saved!", Toast.LENGTH_SHORT).show();
            }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        menuInflater.inflate(R.menu.notify, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete_all_terms:
                assessmentViewModel.deleteAllAssessments();
                Toast.makeText(this, "All Courses Deleted", Toast.LENGTH_SHORT).show();

        }
        return super.onOptionsItemSelected(item);
    }

}




