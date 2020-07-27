package com.gaxontek.instagramclone.ui.comments;

import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gaxontek.instagramclone.R;

public class ViewHolderPostDescription extends RecyclerView.ViewHolder{

    public LinearLayout root;
    public TextView txtPostDescription;
    public TextView txtPostTime;

    public ViewHolderPostDescription(@NonNull View itemView) {
        super(itemView);

        root = itemView.findViewById(R.id.layout_post_description_root);
        txtPostDescription = itemView.findViewById(R.id.tvPostDescription);
        txtPostTime = itemView.findViewById(R.id.tv_comment_time);

    }

    public void setTxtPostDescription(String string) {
        txtPostDescription.setText(string);
    }
    public void setTxtPostTime(String string) { txtPostTime.setText(string);}
}
