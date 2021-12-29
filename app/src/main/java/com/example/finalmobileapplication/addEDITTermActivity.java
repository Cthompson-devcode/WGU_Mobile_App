package com.example.finalmobileapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class addEDITTermActivity extends AppCompatActivity {


    public static final String EXTRA_TITLE =
            "com.example.createdatabase.EXTRA_TITLE";
    public static final String EXTRA_START_DATE =
            "com.example.createdatabase.EXTRA_START_DATE";
    public static final String EXTRA_END_DATE =
            "com.example.createdatabase.EXTRA_END_DATE";
    public static String EXTRA_TERM_ID =
            "com.example.createdatabase.EXTRA_TERM_ID";

    private EditText editTextTitle;
    private EditText editTextStartDate;
    private EditText editTextEndDate;
    private EditText editTextID;
    private static final int ADD_COURSE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_term);
        editTextID = findViewById(R.id.textViewTermID);
        editTextTitle = findViewById(R.id.edit_text_title);
        editTextStartDate = findViewById(R.id.start_date_text);
        editTextEndDate = findViewById(R.id.end_date_text);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_TERM_ID)) {
            setTitle("Edit Term");


            editTextID.setText(intent.getStringExtra(EXTRA_TERM_ID));
            editTextTitle.setText((intent.getStringExtra(EXTRA_TITLE)));
            editTextStartDate.setText(intent.getStringExtra(EXTRA_START_DATE));
            editTextEndDate.setText(intent.getStringExtra(EXTRA_END_DATE));

        } else {
            setTitle("Add Term");
        }

    }

    private void saveTerm() {
        String id = editTextID.getText().toString();
        String title = editTextTitle.getText().toString();
        String startDate = editTextStartDate.getText().toString();
        String endDate = editTextEndDate.getText().toString();


        if (title.trim().isEmpty() || startDate.trim().isEmpty() || endDate.trim().isEmpty()) {
            Toast.makeText(this, "Please complete all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TERM_ID, id);
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_START_DATE, startDate);
        data.putExtra(EXTRA_END_DATE, endDate);

        //saving edited informtion by linking to ID
      int   id2 = getIntent().getIntExtra(EXTRA_TERM_ID, -1);

        if (id2 != -1 ) {
            data.putExtra(EXTRA_TERM_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_term_menu, menu);

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
        }

    }


    public void goToCourses(View view) {
        Intent intent = new Intent(addEDITTermActivity.this, listOfAssociatedCourses.class);
        int id = Integer.parseInt(editTextID.getText().toString());
        String idText = editTextID.getText().toString();

        if ( idText.equals(""))
        {
            Toast.makeText(this, "You need to create the term in order to add courses", Toast.LENGTH_SHORT).show();
            return;
        }
        else {

            intent.putExtra(EXTRA_TERM_ID, id);

            intent.putExtra(listOfAssociatedCourses.EXTRA_TERM_ID, id);

            System.out.println(" This is the term ID" + EXTRA_TERM_ID);

            startActivity(intent);
        }
    }
}