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
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.example.finalmobileapplication.Adapter.CourseAdapter2;
import com.example.finalmobileapplication.Database.SchoolRepository;
import com.example.finalmobileapplication.Entities.Courses;
import com.example.finalmobileapplication.ViewModel.CourseViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class listOfAssociatedCourses<numCourses> extends AppCompatActivity {
    public static int numAlert1;
    public static final String EXTRA_COURSE_ID =
            "com.example.finalmobileapplication.EXTRA_COURSE_ID";
    public static final String EXTRA_COURSE_NAME =
            "com.example.finalmobileapplication.EXTRA_COURSE_NAME";

    public static final String EXTRA_START_DATE =
            "com.example.finalmobileapplication.EXTRA_START_DATE";
    public static final String EXTRA_END_DATE =
            "com.example.finalmobileapplication.EXTRA_END_DATE";
    public static  String EXTRA_TERM_ID =
            "com.example.finalmobileapplication.EXTRA_TERM_ID";
    public static final String EXTRA_COURSE_STATUS =
            "com.example.finalmobileapplication.EXTRA_COURSE_STATUS";
    public static final String EXTRA_COURSE_INSTRUCTOR =
            "com.example.finalmobileapplication.EXTRA_COURSE_INSTRUCTOR";
    public static final String EXTRA_COURSE_PHONE =
            "com.example.finalmobileapplication.EXTRA_COURSE_PHONE";
    public static final String EXTRA_COURSE_EMAIL =
            "com.example.finalmobileapplication.EXTRA_COURSE_EMAIL";
    public static final String EXTRA_COURSE_ALERT =
            "com.example.finalmobileapplication.EXTRA_COURSE_ALERT";
    private int termID;
    private int courseID;
    public static final int ADD_COURSE_REQUEST = 1;
    public static final int EDIT_COURSE_REQUEST = 2;
    private CourseViewModel courseViewModel;
    SchoolRepository schoolRepository;
    RecyclerView recyclerView;
    public static int numCourses;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_associated_courses);

        schoolRepository = new SchoolRepository(getApplication());

        setTitle("List of Courses");

            FloatingActionButton buttonAddTerm = findViewById(R.id.button_add_term);

            buttonAddTerm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Intent intent = new Intent (listOfAssociatedCourses.this, testAddEditCourse.class);
                    intent.putExtra(testAddEditCourse.EXTRA_TERM_ID, termID);
                    intent.putExtra(testAddEditCourse.EXTRA_COURSE_ID, courseID);

                    startActivityForResult(intent, 1 );


                }
            });


            //recyclerview
            RecyclerView recyclerView = findViewById(R.id.recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setHasFixedSize(true);

            Intent intent = getIntent();
            termID = intent.getIntExtra(addEDITTermActivity.EXTRA_TERM_ID, -1);
            courseID = intent.getIntExtra(EXTRA_COURSE_ID, -1);

            System.out.println("THI SIS THE EXTRA COURSE ID  =========" +courseID);

            System.out.println("This is the EXTRA TERM ID ------  " + EXTRA_TERM_ID);

            System.out.println("This is the termID---------------  " +termID);


            CourseAdapter2 adapter2 = new CourseAdapter2();
            recyclerView.setAdapter(adapter2);

        List<Courses> filteredCourses = new ArrayList<>();
        for(Courses p:schoolRepository.getAllCourses1())
        {
            if(p.getTermsID()==termID)filteredCourses.add(p);
        }
        adapter2.setCourse(filteredCourses);


            courseViewModel = ViewModelProviders.of(this).get(CourseViewModel.class);


       courseViewModel.getLiveTermCourses(termID).observe(this, new Observer<List<Courses>>() {
                @Override
                public void onChanged(@Nullable List<Courses> filteredCourses) {
                   // List<Courses>filteredCourses = new ArrayList<>();

                    adapter2.setCourses(filteredCourses);

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
                    courseViewModel.delete(adapter2.getCoursesAt(viewHolder.getAdapterPosition()));

                    Toast.makeText(listOfAssociatedCourses.this, "Course Deleted! ", Toast.LENGTH_SHORT).show();
                }
            }).attachToRecyclerView(recyclerView);

            adapter2.setonItemClickListener(new CourseAdapter2.onItemClickListener() {
                @Override

                public void onItemClick(Courses courses) {
                    //sending data that was clicked
                    Intent intent = new Intent(listOfAssociatedCourses.this, testAddEditCourse.class);
                    intent.putExtra(testAddEditCourse.EXTRA_TERM_ID, termID);

                    System.out.println(" THIS IS THE TERM ID WHEN ITEM CLICKED  ------- "+ termID);

                    intent.putExtra(testAddEditCourse.EXTRA_COURSE_NAME, courses.getTitle());


                    intent.putExtra(testAddEditCourse.EXTRA_START_DATE, courses.getStartDate());
                    intent.putExtra(testAddEditCourse.EXTRA_END_DATE, courses.getEndDate());
                    intent.putExtra(testAddEditCourse.EXTRA_COURSE_INSTRUCTOR, courses.getName());
                    intent.putExtra(testAddEditCourse.EXTRA_COURSE_PHONE, courses.getPhone());
                    intent.putExtra(testAddEditCourse.EXTRA_COURSE_EMAIL, courses.getEmail());
                    intent.putExtra(testAddEditCourse.EXTRA_COURSE_ID, courses.getUid());
                    intent.putExtra(testAddEditCourse.EXTRA_COURSE_STATUS, courses.getStatus());
                    intent.putExtra(testAddEditCourse.EXTRA_COURSE_ALERT, courses.isAlert());
                 Boolean alert =   courses.isAlert();

System.out.println("THIS IS THE ALERT BEING SENT -------------- " +alert);
                   startActivityForResult(intent, EDIT_COURSE_REQUEST);

                }
            });


        }
        @Override
        protected void onActivityResult(int requestCode, int resultCode,@Nullable  Intent data) {
            super.onActivityResult(requestCode, resultCode, data);


            if (requestCode == testAddEditCourse.ADD_COURSE_REQUEST && resultCode == RESULT_OK) {


                String title = data.getStringExtra(testAddEditCourse.EXTRA_COURSE_NAME);
                String startDate = data.getStringExtra(testAddEditCourse.EXTRA_START_DATE);
                String endDate = data.getStringExtra(testAddEditCourse.EXTRA_END_DATE);
                boolean alert = data.getBooleanExtra(testAddEditCourse.EXTRA_COURSE_ALERT, false);
                int status = data.getIntExtra(testAddEditCourse.EXTRA_COURSE_STATUS, -1);
                String instructor = data.getStringExtra(testAddEditCourse.EXTRA_COURSE_INSTRUCTOR);
                String phone = data.getStringExtra(testAddEditCourse.EXTRA_COURSE_PHONE);
                String email = data.getStringExtra(testAddEditCourse.EXTRA_COURSE_EMAIL);
                int id = data.getIntExtra(testAddEditCourse.EXTRA_COURSE_ID, -1);

//saving user input to database
                Courses courses = new Courses(termID, title, startDate, endDate, status, instructor, phone, email, alert);
                courseViewModel.insert(courses);



                if (alert) {


                    String dateFromScreen = startDate;



                    System.out.println("THIS IS MY DATE TO NOTIFY ------- " + dateFromScreen);
                    String myFormat = "MM/dd/yy";
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                    Date myDate = null;
                    //Date myDate1 =

                    try {
                        myDate = sdf.parse(dateFromScreen);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Long trigger = myDate.getTime();
                    Intent intent = new Intent(listOfAssociatedCourses.this, MyReceiver.class);
                    intent.putExtra("key", "Your course " + title +" is starting on " + startDate + " with professor " + instructor );
                    PendingIntent sender = PendingIntent.getBroadcast(listOfAssociatedCourses.this, ++MainActivity.numAlert1, intent, 0);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
                }

                System.out.println("THIS IS THE ALERT VALUE AFTER SAVING ====" + alert);

                Toast.makeText(this, "Course Saved!", Toast.LENGTH_SHORT).show();
            }

            else if (requestCode == testAddEditCourse.EDIT_COURSE_REQUEST && resultCode == RESULT_OK)
            {
                int id = data.getIntExtra(testAddEditCourse.EXTRA_COURSE_ID, -1);
                if ( id == -1)
                {
                    Toast.makeText(this, " Cannot Update Term", Toast.LENGTH_SHORT).show();
                    return;

                }

                String title = data.getStringExtra(testAddEditCourse.EXTRA_COURSE_NAME);
                String startDate = data.getStringExtra(testAddEditCourse.EXTRA_START_DATE);
                String endDate = data.getStringExtra(testAddEditCourse.EXTRA_END_DATE);
                boolean alert = data.getBooleanExtra(testAddEditCourse.EXTRA_COURSE_ALERT, false);
                int status = data.getIntExtra(testAddEditCourse.EXTRA_COURSE_STATUS, -1);
                String instructor = data.getStringExtra(testAddEditCourse.EXTRA_COURSE_INSTRUCTOR);
                String phone = data.getStringExtra(testAddEditCourse.EXTRA_COURSE_PHONE);
                String email = data.getStringExtra(testAddEditCourse.EXTRA_COURSE_EMAIL);
                id = data.getIntExtra(testAddEditCourse.EXTRA_COURSE_ID, -1);


                Courses courses = new Courses ( termID, title, startDate, endDate, status, instructor, phone, email, alert);
                courses.setUid(id);
                courseViewModel.update(courses);

                if (alert) {


                    String dateFromScreen = startDate;



                    System.out.println("THIS IS MY DATE TO NOTIFY ------- " + dateFromScreen);
                    String myFormat = "MM/dd/yy";
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                    Date myDate = null;
                    //Date myDate1 =

                    try {
                        myDate = sdf.parse(dateFromScreen);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Long trigger = myDate.getTime();
                    Intent intent = new Intent(listOfAssociatedCourses.this, MyReceiver.class);
                    intent.putExtra("key", "Your course " + title +" is starting on " + startDate + " with professor " + instructor );
                    PendingIntent sender = PendingIntent.getBroadcast(listOfAssociatedCourses.this, ++MainActivity.numAlert1, intent, 0);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
                }

                System.out.println("THIS IS THE ALERT VALUE AFTER SAVING ====" + alert);


                Toast.makeText(this, "Course Updated!", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this, "Course NOT Saved!", Toast.LENGTH_SHORT).show();
                return;

            }

        }



        @Override
        public boolean onCreateOptionsMenu(Menu menu) {

            MenuInflater menuInflater = getMenuInflater();
            menuInflater.inflate(R.menu.main_menu, menu);

            return true;
        }

        @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.delete_all_terms:
                    courseViewModel.deleteAllCourses();
                    Toast.makeText(this, "All Courses Deleted", Toast.LENGTH_SHORT).show();


            }
            return super.onOptionsItemSelected(item);
        }

    }
