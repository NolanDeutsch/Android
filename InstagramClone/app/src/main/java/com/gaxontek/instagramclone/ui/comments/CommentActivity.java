package com.gaxontek.instagramclone.ui.comments;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.gaxontek.instagramclone.R;
import com.gaxontek.instagramclone.model.PostModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

public class CommentActivity extends AppCompatActivity {

    private final String NEWS_FEED_DATABASE_LOCATION = "posts";

    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        String postID = getIntent().getStringExtra("Post Id");

        GetPostInfo(postID);

    }

    public void GetPostInfo(final String postID) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(NEWS_FEED_DATABASE_LOCATION).child(postID);
        //Query query = FirebaseDatabase.getInstance().getReference().child(NEWS_FEED_DATABASE_LOCATION).child(postID);

        ValueEventListener postListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                PostModel post = dataSnapshot.getValue(PostModel.class);

                TextView desc = findViewById(R.id.tvPostDescription);
                desc.setText(post.getPostDescription());
            }

            // Get Post object and use the values to update the UI
        }
        @Override
        public void onCancelled(DatabaseError databaseError) {
            // Getting Post failed, log a message
            }
        };
        databaseReference.addValueEventListener(postListener);
    }
}



