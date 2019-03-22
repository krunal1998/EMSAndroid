package com.example.ems.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ems.R;
import com.example.ems.models.GeneratedReview;

import java.util.Date;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
    Context context;
    private List<GeneratedReview> generatedReviewList;


    public void add(int position, GeneratedReview item) {
        generatedReviewList.add(position, item);
        notifyItemInserted(position);
    }
    public void remove(GeneratedReview item) {
        int position = generatedReviewList.indexOf(item);
        generatedReviewList.remove(position);
        notifyItemRemoved(position);
    }
    public ReviewAdapter(Context context1,List<GeneratedReview> plist) {
        generatedReviewList =plist;
        context=context1;
    }
    @Override
    public ReviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.durationText.setText(generatedReviewList.get(position).StartDate.substring(0,10) + " to "+ generatedReviewList.get(position).EndDate.toString());
        holder.ratingText.setText(generatedReviewList.get(position).AverageRating + "/5");
    }
    @Override
    public int getItemCount() {
        return generatedReviewList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView durationText;
        public TextView ratingText;


        public ViewHolder(View v) {
            super(v);
            durationText =  v.findViewById(R.id.duration);
            ratingText = v.findViewById(R.id.main_rating);


        }
    }



}
