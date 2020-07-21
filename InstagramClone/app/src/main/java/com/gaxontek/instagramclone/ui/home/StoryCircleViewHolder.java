package com.gaxontek.instagramclone.ui.home;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gaxontek.instagramclone.R;

public class StoryCircleViewHolder extends RecyclerView.ViewHolder {

    public LinearLayout root;
    public TextView txtname;

    public StoryCircleViewHolder(@NonNull View itemView) {
        super(itemView);
        root = itemView.findViewById(R.id.layout_story_root);
        txtname = itemView.findViewById(R.id.story_username);
    }

    public void setTxtname(String string) {
        txtname.setText(string);
    }
}
