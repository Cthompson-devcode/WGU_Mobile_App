package com.example.finalmobileapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.finalmobileapplication.Database.SchoolRepository;
import com.example.finalmobileapplication.Entities.Courses;
import com.example.finalmobileapplication.ViewModel.CourseViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class testAddEditCourse extends AppCompatActivity {

    private CourseViewModel courseViewModel;


    public static final String EXTRA_COURSE_ID =
            "com.example.finalmobileapplication.EXTRA_COURSE_ID";
    public static final String EXTRA_COURSE_NAME =
            "com.example.finalmobileapplication.EXTRA_COURSE_NAME";

    public static final String EXTRA_START_DATE =
            "com.example.finalmobileapplication.EXTRA_START_DATE";
    public static final String EXTRA_END_DATE =
            "com.example.finalmobileapplication.EXTRA_END_DATE";
    public static final String EXTRA_TERM_ID =
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

    private SchoolRepository schoolRepository;

    List<Courses> allcourses;


    public static final int ADD_COURSE_REQUEST = 1;
    public static final int EDIT_COURSE_REQUEST = 2;

    private static final int DROPPED = 1;
    private static final int PLANNED = 2;
    private static final int COMPLETED = 3;
    private static final int IN_PROGRESS = 4;

    private Calendar startDateCalender;
    private Calendar endDateCalendar;

    private EditText editTextTitle;
    private EditText editTextStartDate;
    private EditText editTextEndDate;
    private CheckBox editCourseAlarm;
    private RadioGroup editStatus;
    private EditText editInstructorName;
    private EditText editPhone;
    private EditText editEmail;
    private EditText editCourseID;
    int termID;
    int courseID;
    int status;
    boolean activeAlarm;
    int Id;
    Courses currentCourse;

    private Button mButton;

    public testAddEditCourse() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test_add_edit_course);
        Intent intent = getIntent();
        schoolRepository = new SchoolRepository(getApplication());
        editTextTitle = findViewById(R.id.edit_course_title);
        editTextStartDate = findViewById(R.id.course_start_date_text);
        editTextEndDate = findViewById(R.id.course_end_date_text);
        editCourseAlarm = findViewById(R.id.course_alarm);
        editStatus = findViewById(R.id.course_status_choices); /// need to figure this out*************
        editInstructorName = findViewById(R.id.course_instructor_name);
        editPhone = findViewById(R.id.course_instructor_phone);
        editEmail = findViewById(R.id.course_instructor_email);
        Id=getIntent().getIntExtra(EXTRA_COURSE_ID,-1);

        System.out.println(" THIS IS THE COURSE ID _---------------  " + Id);
        termID = getIntent().getIntExtra(listOfAssociatedCourses.EXTRA_TERM_ID, -1);

        allcourses = schoolRepository.getAllCourses1();


        for(int i = 0; i < allcourses.size(); i++)
        {
            allcourses.get(i);
        }


        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        courseID = intent.getIntExtra(listOfAssociatedCourses.EXTRA_COURSE_ID, -1);

        if (courseID != -1) {

            editTextTitle.setText((intent.getStringExtra(EXTRA_COURSE_NAME)));

            editTextStartDate.setText(intent.getStringExtra(EXTRA_START_DATE));
            setTitle("EDIT COURSE");

            editTextEndDate.setText(intent.getStringExtra(EXTRA_END_DATE));


            status = intent.getIntExtra(EXTRA_COURSE_STATUS, -1);
           editStatus.check(getRadioID(intent.getIntExtra(EXTRA_COURSE_STATUS, -1)));

            System.out.println("THIS IS MY STATUS _---------   " + status);


            editInstructorName.setText(intent.getStringExtra(EXTRA_COURSE_INSTRUCTOR));
            editPhone.setText(intent.getStringExtra(EXTRA_COURSE_PHONE));
            editEmail.setText(intent.getStringExtra(EXTRA_COURSE_EMAIL));

            Boolean alert = intent.getBooleanExtra(EXTRA_COURSE_ALERT, false);

            System.out.println(" THIS IS MY ALERT VALUE ---------- " + alert);
            if(intent.getBooleanExtra(EXTRA_COURSE_ALERT, false))
                editCourseAlarm.performClick();
            activeAlarm = intent.getBooleanExtra(EXTRA_COURSE_ALERT, false);

        } else
        {
            setTitle("Add New Course");
        }
    }


    private void saveCourse( )
    {
        String title = editTextTitle.getText().toString();
        String startDate = editTextStartDate.getText().toString();
        String endDate = editTextEndDate.getText().toString();
        boolean alert = editCourseAlarm.isChecked();
        int status = getRadioStatus(editStatus.getCheckedRadioButtonId());
        String instructorName =editInstructorName.getText().toString();
        String email = editEmail.getText().toString();
        String phone = editPhone.getText().toString();

        System.out.println("THIS IS MY STATUS WHEN SAVING ---------- " + status);

        if (title.trim().isEmpty() || startDate.trim().isEmpty() || endDate.trim().isEmpty() || status == -1 || instructorName.trim().isEmpty() || phone.trim().isEmpty() || email.trim().isEmpty())
        {
            Toast.makeText(this, "Please complete all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_COURSE_NAME, title);
        intent.putExtra(EXTRA_START_DATE, startDate);
        intent.putExtra(EXTRA_END_DATE, endDate);
        intent.putExtra(EXTRA_COURSE_ALERT, alert);
        intent.putExtra(EXTRA_COURSE_STATUS, status);
        intent.putExtra(EXTRA_COURSE_INSTRUCTOR, instructorName);
        intent.putExtra(EXTRA_COURSE_PHONE, phone );
        intent.putExtra(EXTRA_COURSE_EMAIL, email);
        intent.putExtra(EXTRA_TERM_ID, termID);
        int id = getIntent().getIntExtra(EXTRA_COURSE_ID, -1);


        if (id != -1 ) {

            intent.putExtra(EXTRA_COURSE_ID, id);
        }


        setResult(RESULT_OK, intent);




        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_term_menu, menu);
        menuInflater.inflate(R.menu.notify, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item) {

        switch (item.getItemId())
        {

            case R.id.save_term:
                saveCourse();

                return true;
            default:
                return super.onOptionsItemSelected(item);



            case R.id.notify:
                String startDate = editTextStartDate.getText().toString();
                String dateFromScreen = startDate;
                String title = editTextTitle.getText().toString();
                String instructorName =editInstructorName.getText().toString();
                Boolean alertTrue = editCourseAlarm.isChecked();


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
                Intent intent = new Intent(testAddEditCourse.this, MyReceiver.class);
                intent.putExtra("key", "Your course " + title +" is starting on " + startDate + " with professor " + instructorName );
                PendingIntent sender = PendingIntent.getBroadcast(testAddEditCourse.this, ++MainActivity.numAlert1, intent, 0);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);


                editCourseAlarm.performClick();
        }


        return super.onOptionsItemSelected(item);

    }

    private int getRadioID(int id) {
        int radioID;
        switch (id) {
            case testAddEditCourse.DROPPED:
                radioID = R.id.dropped;
                break;
            case testAddEditCourse.COMPLETED:
                radioID = R.id.completed;
                break;
            case testAddEditCourse.IN_PROGRESS:
                radioID = R.id.in_progress;
                break;
            case testAddEditCourse.PLANNED:
                radioID = R.id.planned;
                break;
            default:
               radioID = -1;
        }
        return radioID;
    }

    private int getRadioStatus(int radioID) {
        int statusID;
        switch (radioID) {
            case R.id.dropped:
                statusID = testAddEditCourse.DROPPED;
                break;
            case R.id.planned:
                statusID = testAddEditCourse.PLANNED;
                break;
            case R.id.in_progress:
                statusID = testAddEditCourse.IN_PROGRESS;
                break;
            case R.id.completed:
                statusID = testAddEditCourse.COMPLETED;
                break;
            default:
                statusID = -1;
        }
        return statusID;
    }

    public void AddAssessmentInfo(View view) {
        Intent intent = new Intent(testAddEditCourse.this, AssessmentActivity.class);
        intent.putExtra(AssessmentActivity.EXTRA_COURSE_ID, Id);
        System.out.println("THIS IS THE COURSE ID BEING SENT TO ASSESSMENTS =====" + Id);
        startActivity(intent);
    }

    public void AddNotetoCourse(View view) {
        Intent intent = new Intent(testAddEditCourse.this, NotesActivity.class);

        intent.putExtra(NotesActivity.EXTRA_COURSE_ID, Id);
        System.out.println("THIS IS HTE COURSE ID BEING SENT TO NOTES =====" + Id);
        startActivity(intent);
    }

}
