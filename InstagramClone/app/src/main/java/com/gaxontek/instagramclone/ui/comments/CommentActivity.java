package com.gaxontek.instagramclone.ui.comments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.gaxontek.instagramclone.R;
import com.gaxontek.instagramclone.login.LoginActivity;
import com.gaxontek.instagramclone.model.PostModel;
import com.gaxontek.instagramclone.ui.home.PostViewHolder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class CommentActivity extends AppCompatActivity {

    private final String NEWS_FEED_DATABASE_LOCATION = "posts";
    private final String COMMENTS_DATABASE_LOCATION = "comments";
    RecyclerView mRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private FirebaseRecyclerAdapter adapterComments;

    Button btnAddComment;
    EditText editTextComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        final String postID = getIntent().getStringExtra("Post Id");
        GetPostInfo(postID);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mRecyclerView = findViewById(R.id.rv_main_comment);
        btnAddComment = findViewById(R.id.btnAddComment);
        editTextComment = findViewById(R.id.edit_text_comment);

        //Stories Items
        linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(adapterComments);
        mRecyclerView.setHasFixedSize(true);
        fetchComments(postID);

        btnAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference dataRefFeed =  FirebaseDatabase.getInstance().getReference().child(COMMENTS_DATABASE_LOCATION).child(postID).push();
                dataRefFeed.push();

                String text = editTextComment.getText().toString().trim();

                if(text.isEmpty() || text.length() == 0) {
                    Toast.makeText(CommentActivity.this, "Please Fill in the Fields!", Toast.LENGTH_LONG).show();
                }
                else {
                    ModelComment comment = new ModelComment();
                    comment.id = dataRefFeed.getKey();
                    comment.image = "Pic.jpg";
                    comment.name = "Name";
                    comment.comment = text;
                    comment.commentTime = Calendar.getInstance().getTime().toString();
                    comment.likes = 0;

                    dataRefFeed.setValue(comment);
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        adapterComments.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapterComments.stopListening();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
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

    private void fetchComments(String postID) {
        DatabaseReference dataRefFeed =  FirebaseDatabase.getInstance().getReference().child(COMMENTS_DATABASE_LOCATION).child(postID);
        Query query = dataRefFeed;

        FirebaseRecyclerOptions<ModelComment> options =
                new FirebaseRecyclerOptions.Builder<ModelComment>()
                        .setQuery(query, new SnapshotParser<ModelComment>() {
                            @NonNull
                            @Override
                            public ModelComment parseSnapshot(@NonNull DataSnapshot snapshot) {
                                return new ModelComment(snapshot.child("id").getValue().toString(),
                                        snapshot.child("image").getValue().toString(),
                                        snapshot.child("name").getValue().toString(),
                                        snapshot.child("comment").getValue().toString(),
                                        snapshot.child("commentTime").getValue().toString(),
                                        Integer.parseInt(snapshot.child("likes").getValue().toString()));}
                        })
                        .build();

        adapterComments = new FirebaseRecyclerAdapter<ModelComment, ViewHolderCommentReply>(options) {

            @Override
            public ViewHolderCommentReply onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_comment_reply, parent, false);

                return new ViewHolderCommentReply(view);
            }
            @Override
            protected void onBindViewHolder(@NonNull ViewHolderCommentReply viewHolderComment, int i, @NonNull ModelComment modelComment) {
                viewHolderComment.setTxtCommentReply(modelComment.name + " " + modelComment.comment);

            }
        };
        mRecyclerView.setAdapter(adapterComments);
    }
}



