package com.fernaak.workoutapp.workoutBuilder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fernaak.workoutapp.R;

public class WorkoutBuilderViewHolder extends RecyclerView.ViewHolder {
    private ImageView exerciseImageView;
    private TextView nameTextView;
    private TextView setsTextView;
    private TextView repsTextView;
    public View itemView;

    public WorkoutBuilderViewHolder(View itemView) {
        super(itemView);
        exerciseImageView = (ImageView) itemView.findViewById(R.id.exercise_image);
        nameTextView = (TextView) itemView.findViewById(R.id.tv_exercise_name);
        setsTextView = (TextView) itemView.findViewById(R.id.tv_sets);
        repsTextView = (TextView) itemView.findViewById(R.id.tv_reps);
        this.itemView = itemView;
    }
/**
    public void setImage(String image) {
        exerciseImageView.setImageDrawable(Drawable.createFromPath("drawable://" + R.drawable.list_view_image_a));
    }
 */
    public void setName(String name) {
        nameTextView.setText(name);
    }
    public void setSets(String sets) {
        setsTextView.setText(sets);
    }
    public void setReps(String reps) {
        repsTextView.setText(reps);
    }

}