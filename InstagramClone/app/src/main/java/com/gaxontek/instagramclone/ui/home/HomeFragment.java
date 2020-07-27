package com.gaxontek.instagramclone.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.gaxontek.instagramclone.R;
import com.gaxontek.instagramclone.model.PostModel;
import com.gaxontek.instagramclone.model.StoryCircleModel;
import com.gaxontek.instagramclone.ui.comments.CommentActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerViewFeed, recyclerViewStories;
    private LinearLayoutManager linearLayoutManagerFeed, linearLayoutManagerStories;
    private FirebaseRecyclerAdapter adapterFeed, adapterStories;

    private boolean isDoubleClicked;

    private final String STORIES_DATABASE_LOCATION = "stories";
    private final String NEWS_FEED_DATABASE_LOCATION = "posts";

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerViewStories = view.findViewById(R.id.recycler_view_stories);
        recyclerViewFeed = view.findViewById(R.id.recycler_view_feed);

        //Stories Items
        linearLayoutManagerStories = new LinearLayoutManager(getActivity(), linearLayoutManagerStories.HORIZONTAL, false);
        recyclerViewStories.setLayoutManager(linearLayoutManagerStories);
        recyclerViewStories.setAdapter(adapterStories);
        recyclerViewStories.setHasFixedSize(true);
        fetchStories();

        //News Feed Items
        linearLayoutManagerFeed = new LinearLayoutManager(getActivity());
        recyclerViewFeed.setLayoutManager(linearLayoutManagerFeed);
        recyclerViewFeed.setAdapter(adapterFeed);
        recyclerViewFeed.setHasFixedSize(true);
        fetchNewsFeed();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapterFeed.startListening();
        adapterStories.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapterFeed.stopListening();
        adapterStories.stopListening();
    }

    //--------------------------------------------------------------------------------------------//
    //Fetch the data to populate the stories                                                      //
    //--------------------------------------------------------------------------------------------//
    private void fetchStories() {
        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child(STORIES_DATABASE_LOCATION);

        FirebaseRecyclerOptions<StoryCircleModel> options =
                new FirebaseRecyclerOptions.Builder<StoryCircleModel>()
                        .setQuery(query, new SnapshotParser<StoryCircleModel>() {
                            @NonNull
                            @Override
                            public StoryCircleModel parseSnapshot(@NonNull DataSnapshot snapshot) {
                                return new StoryCircleModel(snapshot.child("id").getValue().toString(),
                                        snapshot.child("image").getValue().toString(),
                                        snapshot.child("name").getValue().toString());
                            }
                        })
                        .build();

        adapterStories = new FirebaseRecyclerAdapter<StoryCircleModel, StoryCircleViewHolder>(options) {

            @NonNull
            @Override
            public StoryCircleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_user_stories, parent, false);

                return new StoryCircleViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull StoryCircleViewHolder holder, final int position, @NonNull StoryCircleModel model) {
                holder.setTxtname(model.getmName());
                //holder.setTxtDesc(model.getmDesc());

                holder.root.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getActivity(), String.valueOf(position), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };
        recyclerViewStories.setAdapter(adapterStories);
    }

    //--------------------------------------------------------------------------------------------//
    //Fetch the data to populate the newsfeed                                                     //
    //--------------------------------------------------------------------------------------------//
    private void fetchNewsFeed() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(NEWS_FEED_DATABASE_LOCATION);
        Query query = databaseReference;

        FirebaseRecyclerOptions<PostModel> options =
                new FirebaseRecyclerOptions.Builder<PostModel>()
                        .setQuery(query, new SnapshotParser<PostModel>() {
                            @NonNull
                            @Override
                            public PostModel parseSnapshot(@NonNull DataSnapshot snapshot) {
                                return new PostModel(snapshot.child("id").getValue().toString(),
                                        snapshot.child("postUsername").getValue().toString(),
                                        snapshot.child("postUserImage").getValue().toString(),
                                        snapshot.child("postImage").getValue().toString(),
                                        Integer.parseInt(snapshot.child("postLikes").getValue().toString()),
                                        (ArrayList<String>) snapshot.child("userLiked").getValue(),
                                        snapshot.child("postDescription").getValue().toString(),
                                        snapshot.child("postTime").getValue().toString());
                            }
                        })
                        .build();

        adapterFeed = new FirebaseRecyclerAdapter<PostModel, PostViewHolder>(options) {
            @Override
            public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_news_feed, parent, false);

                return new PostViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(PostViewHolder holder, final int position, final PostModel model) {
                holder.setTxtName(model.getPostUsername());
                holder.setLikeNumber(model.getPostLikes());
                holder.setTxtPostDescription(model.getPostDescription());

                //Heart Button Event
                holder.btnHeart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LikedPost(getRef(position).getKey());
                        //Toast.makeText(getActivity(), String.valueOf(position), Toast.LENGTH_SHORT).show();
                    }
                });

                holder.btnComment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Comment(getRef(position).getKey());
                    }
                });

                isDoubleClicked = false;
                final Handler handler = new Handler();
                final Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        //Actions when Single Clicked
                        isDoubleClicked = false;
                    }
                };

                //If the image is double clicked event
                holder.root.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Double click event for liking picture
                        if (isDoubleClicked) {
                            //Actions when double Clicked
                            LikedPost(getRef(position).getKey());
                            isDoubleClicked = false;
                            //remove callbacks for Handlers
                            handler.removeCallbacks(r);
                        } else {
                            isDoubleClicked = true;
                            handler.postDelayed(r, 500);
                        }
                    }
                });
            }
        };
        recyclerViewFeed.setAdapter(adapterFeed);
    }

    //--------------------------------------------------------------------------------------------//
    //Method Calls                                                                                //
    //--------------------------------------------------------------------------------------------//
    public void LikedPost(final String postID) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(NEWS_FEED_DATABASE_LOCATION).child(postID);

        databaseReference.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                PostModel post = mutableData.getValue(PostModel.class);
                if (post == null) {
                    return Transaction.success(mutableData);
                }

                if (post.userLiked.contains(getUid())) {
                    // Unlike the post and remove self from list
                    post.postLikes = post.postLikes - 1;
                    post.userLiked.remove(getUid());
                } else {
                    // Like the post and add self to likes list
                    post.postLikes = post.postLikes + 1;
                    post.userLiked.add(getUid());
                }

                // Set value and report transaction success
                mutableData.setValue(post);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean committed,
                                   DataSnapshot currentData) {
                // Transaction completed
            }
        });
    }

    public void stuff(final String postID) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(NEWS_FEED_DATABASE_LOCATION);
        Query query = FirebaseDatabase.getInstance().getReference().child(NEWS_FEED_DATABASE_LOCATION);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (final DataSnapshot cameraSnapshot : dataSnapshot.getChildren()) {

                    final DatabaseReference favRef = cameraSnapshot.getRef().child("postLikes");

                    favRef.runTransaction(new Transaction.Handler() {
                        @Override
                        public Transaction.Result doTransaction(MutableData mutableData) {
                            Integer currentValue = mutableData.getValue(Integer.class);

                            if (Objects.equals(cameraSnapshot.getKey(), postID)) {
                                if (currentValue == null) {
                                    mutableData.setValue(1);
                                } else {
                                    mutableData.setValue(currentValue + 1);
                                }
                                return Transaction.success(mutableData);
                            }
                            return Transaction.abort();
                        }

                        @Override
                        public void onComplete(DatabaseError databaseError, boolean b,
                                               DataSnapshot dataSnapshot) {
                            // Transaction completed
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });
    }

    public void Comment(final String postID) {
        Intent intent = new Intent(this.getContext(), CommentActivity.class);
        intent.putExtra("Post Id", postID);
        startActivity(intent);
    }

    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }
}