package com.example.finalmobileapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalmobileapplication.Database.SchoolRepository;
import com.example.finalmobileapplication.Entities.Assessments;
import com.example.finalmobileapplication.Entities.Courses;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddAssessmentActivity extends AppCompatActivity {
    public static final String EXTRA_COURSE_ID =
            "com.example.finalmobileapplication.EXTRA_COURSE_ID";
    public static final String EXTRA_ASSESSMENT_NAME =
            "com.example.finalmobileapplication.EXTRA_COURSE_NAME";

    public static final String EXTRA_START_DATE =
            "com.example.finalmobileapplication.EXTRA_START_DATE";
    public static final String EXTRA_ASSESSMENT_TYPE =
            "com.example.finalmobileapplication.EXTRA_END_DATE";
    public static final String EXTRA_ASSESSMENT_ID =
            "com.example.finalmobileapplication.EXTRA_TERM_ID";
    public static final String EXTRA_DUE_DATE =
            "com.example.finalmobileapplication.EXTRA_COURSE_STATUS";

    private EditText editTextTitle;
    //private EditText editType;
    private EditText editTextEndDate;
    private EditText editTextID;
    private CalendarView mCalendarView;
    private static final int ADD_COURSE_REQUEST = 1;
    private SchoolRepository schoolRepository;
    public int courseID;
    int Id;
    String pulledDate;
    Switch aSwitch;
    String switchType;
    private TextView typeTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assessment);

        schoolRepository = new SchoolRepository(getApplication());
        editTextTitle = findViewById(R.id.edit_text_title);
        //editType = findViewById(R.id.start_date_text);
        editTextEndDate = findViewById(R.id.end_date_text);
        mCalendarView = (CalendarView) findViewById(R.id.calendarViewAssessments);
        Id = getIntent().getIntExtra(EXTRA_ASSESSMENT_ID, -1);
        aSwitch = (Switch)findViewById((R.id.switch1));
        typeTextView = findViewById(R.id.textViewType);

       // String editTypeString = editType.getText().toString();

         typeTextView.setText("Performance");
         typeTextView.setText(R.string.typeObjective);

         String getType = typeTextView.getText().toString();


        if (getType.equals("Objective"))
        {

            aSwitch.isChecked();
        }


        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                switchType = "Objective";
                 //   editType.setText("Objective");
                    typeTextView.setText("Objective");
                }

                else
                {
                    switchType = "Performance";
                  //  editType.setText("Performance");
                    typeTextView.setText("Performance");
                }
            }
        });


        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);


        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                String date = ((month + 1) + "/" + dayOfMonth + "/" + year);

                System.out.println(" THIS IS THE DATE SELECTED " + date);

                if (date != "") {

                    editTextEndDate.setText(date);
                }

            }
        });


        Intent intent = getIntent();
        courseID = intent.getIntExtra(AssessmentActivity.EXTRA_COURSE_ID, -1);

        System.out.println("THIS IS THE COURSE ID IN ADD ASSESSMENT ------ " + courseID);


        if (intent.hasExtra(EXTRA_ASSESSMENT_ID)) {
            setTitle("Edit ASSESSMENT");
            editTextTitle.setText((intent.getStringExtra(EXTRA_ASSESSMENT_NAME)));
            typeTextView.setText(intent.getStringExtra(EXTRA_ASSESSMENT_TYPE));
            editTextEndDate.setText(intent.getStringExtra(EXTRA_START_DATE));
            typeTextView.setText(intent.getStringExtra(EXTRA_ASSESSMENT_TYPE));

            String testStringType = typeTextView.getText().toString();

            if(testStringType.equals("Objective"))
            {
                aSwitch.setChecked(true);
            }


        } else {
            setTitle("Add Assessment");

            typeTextView.setText("Performance");
        }


    }

    private void saveTerm() {
        String title = editTextTitle.getText().toString();
        String type = typeTextView.getText().toString();
        String dueDate = editTextEndDate.getText().toString();


        if (title.trim().isEmpty() || type.trim().isEmpty()) {
            Toast.makeText(this, "Please complete all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_ASSESSMENT_NAME, title);
        data.putExtra(EXTRA_ASSESSMENT_TYPE, type);
        data.putExtra(EXTRA_DUE_DATE, dueDate);

        if (Id != -1) {
            data.putExtra(EXTRA_ASSESSMENT_ID, Id);
        }

        setResult(RESULT_OK, data);
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
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.save_term:
                saveTerm();
                return true;
            default:
                return super.onOptionsItemSelected(item);


            case R.id.notify:
                String dueDate1 = editTextEndDate.getText().toString();
                String dateFromScreen = dueDate1;
                String title = editTextTitle.getText().toString();
                String type = typeTextView.getText().toString();
                String dueDate = editTextEndDate.getText().toString();

                System.out.println("THIS IS MY DATE TO NOTIFY ------- " + dateFromScreen);
                String myFormat = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                Date myDate = null;


                try {
                    myDate = sdf.parse(dateFromScreen);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Long trigger = myDate.getTime();
                Intent intent = new Intent(AddAssessmentActivity.this, MyReceiver.class);
                intent.putExtra("key1", "You have a(n) " + title +" which is a " + type + " due on " + dueDate );
                PendingIntent sender = PendingIntent.getBroadcast(AddAssessmentActivity.this, ++MainActivity.numAlert, intent, 0);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
        }

        return super.onOptionsItemSelected(item);
    }
}
