package com.example.finalmobileapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalmobileapplication.Entities.Courses;
import com.example.finalmobileapplication.R;

import java.util.ArrayList;
import java.util.List;

public class CourseAdapter2 extends RecyclerView.Adapter <CourseAdapter2.CourseHolder> {
    private List<Courses> courses = new ArrayList<>();
    private onItemClickListener listener;
    private Context mcontext;

    public CourseAdapter2() {
    }


    @NonNull
    @Override
    public CourseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // this pulls the screen you want to see
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.terms_item, parent, false);


        return new CourseHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseHolder holder, int position) {


        // populates the data from table into the position on the screen that you want to see

        //getting data from arraylist
        Courses currentCourses = courses.get(position);

        //setting data
        holder.textViewTitle.setText(currentCourses.getTitle());
        holder.textViewDescription.setText(currentCourses.getStartDate() );
        holder.textViewPriority.setText(String.valueOf(currentCourses.getTermsID()));
    }

    @Override
    public int getItemCount() {

        // disply how mnay items you want to see
        return courses.size();
    }

    // you manually create this to
    public void setCourse(List<Courses> course)
    {
        // gets the terms into recycler view  of notifying system/DB that a change happened
        this.courses = course;
        notifyDataSetChanged();
    }

    public Courses getCoursesAt (int position)   {
        return courses.get(position);

    }

    class CourseHolder extends RecyclerView.ViewHolder{

        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewPriority;


        public CourseHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
            textViewPriority = itemView.findViewById(R.id.text_view_priority);


            //calling on click listener
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                //getting item position to see item clicked
                public void onClick(View v) {
                    int position = getAdapterPosition();
                  if(listener != null && position != RecyclerView.NO_POSITION) {
                      listener.onItemClick(courses.get(position));
                      final Courses current = courses.get(position);

                  }
                }
            });
        }


    }

    private LayoutInflater mInflater;


    private List<Courses> mCourses;
    //once user clicks the item and the click listen will call method on main activity to update
    public interface onItemClickListener {

        void onItemClick(Courses courses);
    }

    public void setonItemClickListener(CourseAdapter2.onItemClickListener listener) {
// catching click and
        this.listener = listener;
    }

    public void setCourses(List<Courses> courses)
    {
        // gets the terms into recycler view  of notifying system/DB that a change happened
        this.courses = courses;
        notifyDataSetChanged();
    }
}
