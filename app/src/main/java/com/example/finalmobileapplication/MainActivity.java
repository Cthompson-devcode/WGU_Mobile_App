package com.example.finalmobileapplication;

import androidx.activity.result.contract.ActivityResultContracts;
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
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.finalmobileapplication.Database.SchoolRepository;
import com.example.finalmobileapplication.Entities.Assessments;
import com.example.finalmobileapplication.Entities.Courses;
import com.example.finalmobileapplication.Entities.Notes;
import com.example.finalmobileapplication.Entities.Terms;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

public static int numAlert;
public static int numAlert1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Welcome to University Portal");
    }
    public void goToTerms(View view) {

        Intent intent = new Intent(MainActivity.this, TestListOfTerms.class);
        startActivity(intent);
    }


}


