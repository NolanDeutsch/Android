package com.gaxontek.instagramclone.ui.comments;

import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gaxontek.instagramclone.R;

public class ViewHolderCommentReply extends RecyclerView.ViewHolder{

    public LinearLayout root;
    public TextView txtCommentReply;
    public TextView txtCommentReplyTime;
    public TextView txtLikeNumber;
    public ImageButton btnHeart;

    public ViewHolderCommentReply(@NonNull View itemView) {
        super(itemView);

        root = itemView.findViewById(R.id.layout_comment_reply_root);
        txtCommentReply = itemView.findViewById(R.id.tv_comment_reply);
        txtCommentReplyTime = itemView.findViewById(R.id.tv_comment_time);
        txtLikeNumber = itemView.findViewById(R.id.tv_comment_likes);
        btnHeart = itemView.findViewById(R.id.btn_heart);
    }

    public void setTxtCommentReply(String string) {
        txtCommentReply.setText(string);
    }
    public void setTxtCommentReplyTime(String string) { txtCommentReplyTime.setText(string);}
    public void setLikeNumber(String num) { txtLikeNumber.setText(num); }
}
