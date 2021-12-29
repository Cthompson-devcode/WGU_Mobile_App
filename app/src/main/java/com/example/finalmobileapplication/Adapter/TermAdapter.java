package com.example.finalmobileapplication.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalmobileapplication.Entities.Terms;
import com.example.finalmobileapplication.R;

import java.util.ArrayList;
import java.util.List;

// this is the adapater that helps with the term item screen and data

public class TermAdapter extends RecyclerView.Adapter <TermAdapter.TermHolder> {
    private List<Terms> terms = new ArrayList<>();
    private onItemClickListener listener;

    @NonNull
    @Override
    public TermHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // this pulls the screen you want to see
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.terms_item, parent, false);


        return new TermHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TermHolder holder, int position) {


        // populates the data from table into the position on the screen that you want to see

        //getting data from arraylist
        Terms currentTerms = terms.get(position);

        //setting data
        holder.textViewTitle.setText(currentTerms.getTermName());
        holder.textViewDescription.setText(currentTerms.getStartDate() );
        holder.textViewPriority.setText(String.valueOf(currentTerms.getTermsID()));

    }

    @Override
    public int getItemCount() {

        // disply how mnay items you want to see
        return terms.size();
    }

    // you manually create this to
    public void setTerms(List<Terms> terms)
    {
        // gets the terms into recycler view  of notifying system/DB that a change happened
        this.terms = terms;
        notifyDataSetChanged();
    }

    public Terms getTermAt (int position)   {
        return terms.get(position);

    }

    class TermHolder extends RecyclerView.ViewHolder{

        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewPriority;


        public TermHolder(@NonNull View itemView) {
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
                    if(listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(terms.get(position));
                    }

                }
            });
        }


    }

    //once user clicks the item and the click listen will call method on main activity to update
public interface onItemClickListener {

        void onItemClick(Terms terms);
}

public void setonItemClickListener(onItemClickListener listener) {
// catching click and
        this.listener = listener;
    }
}
