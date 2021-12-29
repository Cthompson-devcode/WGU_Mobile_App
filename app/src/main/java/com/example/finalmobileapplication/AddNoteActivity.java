package com.example.finalmobileapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.finalmobileapplication.Database.SchoolRepository;
import com.example.finalmobileapplication.Entities.Courses;
import com.example.finalmobileapplication.Entities.Notes;

import java.util.Calendar;
import java.util.List;

public class AddNoteActivity extends AppCompatActivity {

    private SchoolRepository schoolRepository;
    List<Notes> allnotes;
    private static final int ADD_COURSE_REQUEST = 1;
    private EditText editTextTitle;
    private EditText editTextNoteDetail;
    private EditText editCourseID;
    int termID;
    int courseID;
    int Id;

    public static final String EXTRA_COURSE_ID =
            "com.example.finalmobileapplication.EXTRA_COURSE_ID";
    public static final String EXTRA_NOTE_TITLE =
            "com.example.finalmobileapplication.EXTRA_COURSE_NAME";
    public static final String EXTRA_NOTE_ID =
            "com.example.finalmobileapplication.EXTRA_NOTE_ID";

    public static final String EXTRA_NOTE_DETAILS =
            "com.example.finalmobileapplication.EXTRA_START_DATE";
    //public static final int ADD_COURSE_REQUEST = 1;
    public static final int EDIT_COURSE_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        Intent intent = getIntent();
        Id=getIntent().getIntExtra(EXTRA_NOTE_ID,-1);

        System.out.println("THIS IS MY NOTE ID======" +Id);
        schoolRepository = new SchoolRepository(getApplication());
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextNoteDetail = findViewById(R.id.editTextTextMultiLine);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        courseID = intent.getIntExtra(NotesActivity.EXTRA_COURSE_ID, -1);

        System.out.println("THIS IS THE COURSE ID IN ADD NOTE -------- "+ courseID);


        if (intent.hasExtra(EXTRA_NOTE_ID)) {


            editTextTitle.setText((intent.getStringExtra(EXTRA_NOTE_TITLE)));

            editTextNoteDetail.setText(intent.getStringExtra(EXTRA_NOTE_DETAILS));
            setTitle("EDIT NOTE");

        } else
        {
            setTitle("Add New Note");
        }

    }

    private void saveNote( )


    {

        String title = editTextTitle.getText().toString();
        String noteDetail = editTextNoteDetail.getText().toString();


        if (title.trim().isEmpty() || noteDetail.trim().isEmpty() ) {
            Toast.makeText(this, "Please complete all fields", Toast.LENGTH_SHORT).show();
            return;
        }


        Intent data = new Intent();
        data.putExtra(EXTRA_NOTE_TITLE, title);
        data.putExtra(EXTRA_NOTE_DETAILS, noteDetail);
        data.putExtra(EXTRA_COURSE_ID, courseID);


        if (Id != -1 ) {


            data.putExtra(EXTRA_NOTE_ID, Id);
        }

        setResult(RESULT_OK, data);
        finish();

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_term_menu, menu);
        menuInflater.inflate(R.menu.share, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item) {

        switch (item.getItemId())
        {

            case R.id.save_term:
                saveNote();

                return true;
            default:
                return super.onOptionsItemSelected(item);



            case R.id.share:
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, editTextNoteDetail.getText() );
                shareIntent.putExtra(Intent.EXTRA_TITLE, editTextTitle.getText());
                shareIntent.setType("text/plain");

                Intent sharingIntent = Intent.createChooser(shareIntent, null);
                startActivity(sharingIntent);
                return true;
        }
    }

    }
