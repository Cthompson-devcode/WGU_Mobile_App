package com.example.finalmobileapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.finalmobileapplication.Adapter.CourseAdapter2;
import com.example.finalmobileapplication.Adapter.NotesAdapter;
import com.example.finalmobileapplication.Database.SchoolRepository;
import com.example.finalmobileapplication.Entities.Assessments;
import com.example.finalmobileapplication.Entities.Courses;
import com.example.finalmobileapplication.Entities.Notes;
import com.example.finalmobileapplication.ViewModel.CourseViewModel;
import com.example.finalmobileapplication.ViewModel.NotesViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class NotesActivity extends AppCompatActivity {

    public static final String EXTRA_COURSE_ID =
            "com.example.finalmobileapplication.EXTRA_COURSE_ID";
    public static final String EXTRA_NOTE_TITLE =
            "com.example.finalmobileapplication.EXTRA_COURSE_NAME";

    public static final String EXTRA_NOTE_DETAILS =
            "com.example.finalmobileapplication.EXTRA_START_DATE";
    private int courseID;
    public static final int ADD_COURSE_REQUEST = 1;
    public static final int EDIT_COURSE_REQUEST = 2;
    private NotesViewModel notesViewModel;

    SchoolRepository schoolRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        schoolRepository = new SchoolRepository(getApplication());

        setTitle("List of Notes");

        FloatingActionButton buttonAddTerm = findViewById(R.id.button_add_term);

        buttonAddTerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (NotesActivity.this, AddNoteActivity.class);
                intent.putExtra(AddNoteActivity.EXTRA_COURSE_ID, courseID);

                startActivityForResult(intent, 1 );

            }
        });

        //recyclerview
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        Intent intent = getIntent();

        courseID = intent.getIntExtra(testAddEditCourse.EXTRA_COURSE_ID, -1);
        System.out.println("THIS IS THE COURSEID IN NOTES ACTIVITY=============" + courseID);


        NotesAdapter adapter2 = new NotesAdapter();
        recyclerView.setAdapter(adapter2);


        List<Notes> filteredNotes = new ArrayList<>();
        for(Notes p:schoolRepository.getAllNotes1())
        {
            if(p.getCoursesID()==courseID)filteredNotes.add(p);
        }
        adapter2.setNote(filteredNotes);


        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);
       notesViewModel.getLiveCourseNotes(courseID).observe(this, new Observer<List<Notes>>() {
            @Override
            public void onChanged(@Nullable List<Notes> filteredNotes) {

                adapter2.setNotes(filteredNotes);


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
                notesViewModel.delete(adapter2.getNotesAt(viewHolder.getAdapterPosition()));

                Toast.makeText(NotesActivity.this, "Course Deleted! ", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter2.setonItemClickListener(new NotesAdapter.onItemClickListener() {
            @Override

            public void onItemClick(Notes notes) {
                //sending data that was clicked
                Intent intent = new Intent(NotesActivity.this, AddNoteActivity.class);
                intent.putExtra(AddNoteActivity.EXTRA_COURSE_ID, courseID);
                intent.putExtra(AddNoteActivity.EXTRA_NOTE_TITLE, notes.getTitle());
                intent.putExtra(AddNoteActivity.EXTRA_NOTE_DETAILS, notes.getNoteInfo());
                intent.putExtra(AddNoteActivity.EXTRA_NOTE_ID, notes.getUid());

                startActivityForResult(intent, EDIT_COURSE_REQUEST);

            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,@Nullable  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



            if (requestCode == ADD_COURSE_REQUEST && resultCode == RESULT_OK)
            {

                assert data != null;
                //int id = data.getIntExtra(NotesActivity.EXTRA_COURSE_ID, -1);
                String notetitle = data.getStringExtra(AddNoteActivity.EXTRA_NOTE_TITLE);
                String noteDetails = data.getStringExtra(AddNoteActivity.EXTRA_NOTE_DETAILS);




//saving user input to database
               Notes notes = new Notes ( courseID, notetitle, noteDetails);
                notesViewModel.insert(notes);


        Toast.makeText(this, "Note Saved!", Toast.LENGTH_SHORT).show();

            }
            else if (requestCode == EDIT_COURSE_REQUEST && resultCode == RESULT_OK)
            {
                int id = data.getIntExtra(AddNoteActivity.EXTRA_NOTE_ID, -1);
                if ( id == -1)
                {
                    Toast.makeText(this, " Cannot Update Note", Toast.LENGTH_SHORT).show();
                    return;

                }

                int courseID = data.getIntExtra(AddNoteActivity.EXTRA_COURSE_ID, -1);
                String notetitle = data.getStringExtra(AddNoteActivity.EXTRA_NOTE_TITLE);
                String noteDetails = data.getStringExtra(AddNoteActivity.EXTRA_NOTE_DETAILS);


//saving user input to database

                Notes notes = new Notes ( courseID, notetitle, noteDetails);
                notes.setUid(id);
                notesViewModel.update(notes);



                Toast.makeText(this, "Note Updated!", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this, "Note NOT Saved!", Toast.LENGTH_SHORT).show();


            }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        menuInflater.inflate(R.menu.share, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete_all_terms:
                notesViewModel.deleteAllNotes();
                Toast.makeText(this, "All Notes Deleted", Toast.LENGTH_SHORT).show();



        }
        return super.onOptionsItemSelected(item);
    }





}
