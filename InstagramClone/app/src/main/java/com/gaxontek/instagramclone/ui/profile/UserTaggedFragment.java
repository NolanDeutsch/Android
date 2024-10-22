package com.gaxontek.instagramclone.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.gaxontek.instagramclone.R;
import com.gaxontek.instagramclone.model.PostModel;
import com.gaxontek.instagramclone.ui.home.PostViewHolder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class UserTaggedFragment extends Fragment {
    private final String NEWS_FEED_DATABASE_LOCATION = "posts";

    private RecyclerView recyclerViewUserTaggedPosts;
    private GridLayoutManager gridLayoutUserTaggedPosts;
    private FirebaseRecyclerAdapter adapterUserTaggedPosts;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_tagged, container, false);

        recyclerViewUserTaggedPosts = view.findViewById(R.id.recycler_user_tagged_posts);

        //User Post Items
        gridLayoutUserTaggedPosts = new GridLayoutManager(getActivity(), 3);
        recyclerViewUserTaggedPosts.setLayoutManager(gridLayoutUserTaggedPosts);
        recyclerViewUserTaggedPosts.setAdapter(adapterUserTaggedPosts);
        recyclerViewUserTaggedPosts.setHasFixedSize(true);
        fetchPosts();

        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        adapterUserTaggedPosts.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapterUserTaggedPosts.stopListening();
    }

    private void fetchPosts() {
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

        adapterUserTaggedPosts = new FirebaseRecyclerAdapter<PostModel, PostViewHolder>(options) {
            @Override
            public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_user_posts, parent, false);

                return new PostViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(PostViewHolder holder, final int position, final PostModel model) {

            }
        };
        recyclerViewUserTaggedPosts.setAdapter(adapterUserTaggedPosts);
    }
}