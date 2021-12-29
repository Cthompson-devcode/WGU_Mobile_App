package com.example.finalmobileapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalmobileapplication.Entities.Courses;
import com.example.finalmobileapplication.Entities.Notes;
import com.example.finalmobileapplication.R;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter <NotesAdapter.NotesHolder>  {

    private List<Notes> notes = new ArrayList<>();
    private NotesAdapter.onItemClickListener listener;
    private Context mcontext;


    @NonNull
    @Override
    public NotesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // this pulls the screen you want to see
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.terms_item, parent, false);


        return new NotesHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesHolder holder, int position) {


        // populates the data from table into the position on the screen that you want to see

        //getting data from arraylist
        Notes currentNotes = notes.get(position);

        //setting data
        holder.textViewTitle.setText(currentNotes.getTitle());
        holder.textViewDescription.setText(currentNotes.getNoteInfo() );
        holder.textViewPriority.setText(String.valueOf(currentNotes.getCoursesID()));


    }

    @Override
    public int getItemCount() {

        // disply how mnay items you want to see
        return notes.size();
    }

    // you manually create this to
    public void setNotes(List<Notes> notes)
    {
        // gets the terms into recycler view  of notifying system/DB that a change happened
        this.notes = notes;
        notifyDataSetChanged();
    }

    public Notes getNotesAt (int position)   {
        return notes.get(position);

    }





    class NotesHolder extends RecyclerView.ViewHolder{

        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewPriority;


        public NotesHolder(@NonNull View itemView) {
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
                        listener.onItemClick(notes.get(position));
                        final Notes current = notes.get(position);
                    }

                }
            });
        }


    }

    private LayoutInflater mInflater;


    private List<Notes> mNotes;
    //once user clicks the item and the click listen will call method on main activity to update
    public interface onItemClickListener {

        void onItemClick(Notes notes);
    }

    public void setonItemClickListener(NotesAdapter.onItemClickListener listener) {
// catching click and
        this.listener = listener;
    }

    public void setNote(List<Notes> notes)
    {
        // gets the terms into recycler view  of notifying system/DB that a change happened
        this.notes = notes;
        notifyDataSetChanged();
    }
}
