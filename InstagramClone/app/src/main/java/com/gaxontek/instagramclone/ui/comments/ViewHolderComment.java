package com.gaxontek.instagramclone.ui.comments;

import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gaxontek.instagramclone.R;

public class ViewHolderComment extends RecyclerView.ViewHolder{

    public LinearLayout root;
    public TextView txtComment;
    public TextView txtCommentTime;
    public TextView txtLikeNumber;
    public ImageButton btnHeart;

    public ViewHolderComment(@NonNull View itemView) {
        super(itemView);

        root = itemView.findViewById(R.id.layout_comment_root);
        txtComment = itemView.findViewById(R.id.tvComment);
        txtCommentTime = itemView.findViewById(R.id.tv_comment_time);
        txtLikeNumber = itemView.findViewById(R.id.tv_comment_likes);
        btnHeart = itemView.findViewById(R.id.btn_heart);
    }

    public void setTxtComment(String string) { txtComment.setText(string); }
    public void setTxtCommentTime(String string) { txtCommentTime.setText(string); }
    public void setLikeNumber(String num) { txtLikeNumber.setText(num); }
}
