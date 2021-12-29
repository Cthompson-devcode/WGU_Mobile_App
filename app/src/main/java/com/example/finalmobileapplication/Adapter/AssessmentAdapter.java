package com.example.finalmobileapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalmobileapplication.Entities.Assessments;
import com.example.finalmobileapplication.Entities.Courses;
import com.example.finalmobileapplication.R;

import java.util.ArrayList;
import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter <AssessmentAdapter.AssessmentHolder> {
    private List<Assessments> assessments = new ArrayList<>();
    private AssessmentAdapter.onItemClickListener listener;
    private Context mcontext;

    public AssessmentAdapter() {
    }


    @NonNull
    @Override
    public AssessmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // this pulls the screen you want to see
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.terms_item, parent, false);


        return new AssessmentHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentHolder holder, int position) {


        // populates the data from table into the position on the screen that you want to see

        //getting data from arraylist
        Assessments currentassessments = assessments.get(position);

        //setting data
        holder.textViewTitle.setText(currentassessments.getAssessmentName());
        holder.textViewDescription.setText(currentassessments.getDueDate());
        holder.textViewPriority.setText(String.valueOf(currentassessments.getCoursesID()));


    }

    @Override
    public int getItemCount() {

        // disply how mnay items you want to see
        return assessments.size();
    }

    // you manually create this to
    public void setAssessment(List<Assessments> assessment) {
        // gets the terms into recycler view  of notifying system/DB that a change happened
        this.assessments = assessment;
        notifyDataSetChanged();
    }

    public Assessments getAssessmentsAt(int position) {
        return assessments.get(position);

    }


    class AssessmentHolder extends RecyclerView.ViewHolder {

        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewPriority;


        public AssessmentHolder(@NonNull View itemView) {
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
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(assessments.get(position));
                        final Assessments current = assessments.get(position);
                    }


                }
            });
        }


    }

    private LayoutInflater mInflater;


    private List<Assessments> mAssessments;

    //once user clicks the item and the click listen will call method on main activity to update
    public interface onItemClickListener {

        void onItemClick(Assessments assessments);
    }

    public void setonItemClickListener(AssessmentAdapter.onItemClickListener listener) {
// catching click and
        this.listener = listener;
    }

    public void setAssessments(List<Assessments> assessments) {
        // gets the terms into recycler view  of notifying system/DB that a change happened
        this.assessments = assessments;
        notifyDataSetChanged();
    }
}