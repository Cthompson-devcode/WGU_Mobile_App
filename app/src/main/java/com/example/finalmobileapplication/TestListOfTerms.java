package com.example.finalmobileapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
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
import com.example.finalmobileapplication.Adapter.TermAdapter;
import com.example.finalmobileapplication.Database.SchoolRepository;
import com.example.finalmobileapplication.Entities.Courses;
import com.example.finalmobileapplication.Entities.Terms;
import com.example.finalmobileapplication.ViewModel.CourseViewModel;
import com.example.finalmobileapplication.ViewModel.TermViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class TestListOfTerms extends AppCompatActivity {

    public static final int ADD_TERM_REQUEST = 1;
    public static final int EDIT_TERM_REQUEST = 2;
    private TermViewModel termViewModel;
    public static  String EXTRA_TERM_ID =
            "com.example.finalmobileapplication.EXTRA_TERM_ID";
    private CourseViewModel courseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_list_of_terms2);
        setTitle("List of Terms");
        FloatingActionButton buttonAddTerm = findViewById(R.id.button_add_term);
        buttonAddTerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (TestListOfTerms.this, addEDITTermActivity.class);
                startActivityForResult(intent, ADD_TERM_REQUEST );

                intent.putExtra(addEDITTermActivity.EXTRA_TERM_ID, String.valueOf(-1));

                int negOne = -1;

                String test =  addEDITTermActivity.EXTRA_TERM_ID;

                System.out.println("THIS IS MY TERM ID TO PASS TO ADD TERM=============" + test);

            }
        });

        //recyclerview
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        TermAdapter adapter = new TermAdapter();
        recyclerView.setAdapter(adapter);


        termViewModel = ViewModelProviders.of(this).get(TermViewModel.class);
        termViewModel.getAllTerms().observe(this, new Observer<List<Terms>>() {
            @Override
            public void onChanged(@Nullable List<Terms> terms) {

                adapter.setTerms(terms);
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

                Terms terms = adapter.getTermAt(viewHolder.getAdapterPosition());
                 CourseViewModel courseViewModel = new CourseViewModel(getApplication());


                int termID = terms.getTermsID();
                System.out.println(" THIS IS ASSOCIATED COURSES =============" + termID);

                int associatedCourses =0;

                associatedCourses = courseViewModel.getTermCourses(termID).size();

                if (associatedCourses != 0)
                {
                    Toast.makeText(TestListOfTerms.this, "You cannot delete term with courses assigned! ", Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();
                    return;

                }
                else
                {

                    termViewModel.delete(adapter.getTermAt(viewHolder.getAdapterPosition()));

                    Toast.makeText(TestListOfTerms.this, "Term Deleted! ", Toast.LENGTH_SHORT).show();
                }
            }

        }).attachToRecyclerView(recyclerView);

        adapter.setonItemClickListener(new TermAdapter.onItemClickListener() {
            @Override

            public void onItemClick(Terms terms) {
                //sending data that was clicked
                Intent intent = new Intent(TestListOfTerms.this, addEDITTermActivity.class);
                intent.putExtra(addEDITTermActivity.EXTRA_TERM_ID, String.valueOf(terms.getTermsID()));
                intent.putExtra(addEDITTermActivity.EXTRA_TITLE, terms.getTermName());
                intent.putExtra(addEDITTermActivity.EXTRA_START_DATE, terms.getStartDate());
                intent.putExtra(addEDITTermActivity.EXTRA_END_DATE, terms.getEndDate());
                startActivityForResult(intent, EDIT_TERM_REQUEST);

            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_TERM_REQUEST && resultCode == RESULT_OK)
        {
            String title = data.getStringExtra(addEDITTermActivity.EXTRA_TITLE);
            String startDate = data.getStringExtra(addEDITTermActivity.EXTRA_START_DATE);
            String endDate = data.getStringExtra(addEDITTermActivity.EXTRA_END_DATE);
            String termId = data.getStringExtra(addEDITTermActivity.EXTRA_TERM_ID);

             if (termId.equals("") || termId.equals("-1")) {
//saving user input to database
                 Terms terms = new Terms(title, startDate, endDate);
                 termViewModel.insert(terms);
                 Toast.makeText(this, "Term Saved!", Toast.LENGTH_SHORT).show();
             }
        }
        else if (requestCode == EDIT_TERM_REQUEST && resultCode == RESULT_OK)
        {
           String id2 = data.getStringExtra(addEDITTermActivity.EXTRA_TERM_ID);
           String stringID = String.valueOf(id2);
           int id = Integer.parseInt(stringID);

            if ( stringID.equals("-1"))
            {
                Toast.makeText(this, " Cannot Update Term", Toast.LENGTH_SHORT).show();
                return;

            }
            String title = data.getStringExtra(addEDITTermActivity.EXTRA_TITLE);
            String startDate = data.getStringExtra(addEDITTermActivity.EXTRA_START_DATE);
            String endDate = data.getStringExtra(addEDITTermActivity.EXTRA_END_DATE);
            Terms terms = new Terms ( title, startDate, endDate);
            terms.setTermsID(id);
            termViewModel.update(terms);

            Toast.makeText(this, "Term Updated!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Term NOT Saved!", Toast.LENGTH_SHORT).show();


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
                termViewModel.deleteAllTerms();
                Toast.makeText(this, "All Terms Deleted", Toast.LENGTH_SHORT).show();


        }
        return super.onOptionsItemSelected(item);
    }
}
