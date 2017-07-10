package com.fernaak.workoutapp.exerciseItems;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.fernaak.workoutapp.R;
import com.fernaak.workoutapp.workoutBuilder.WorkoutBuilderActivity;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ExerciseActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ImageView mExerciseImage;
    private RecyclerView mRecyclerView;
    private GridLayoutManager mLayoutManager;
    private String mUsername;

    public static final String ANONYMOUS = "anonymous";
    public static final int RC_SIGN_IN = 1;

    // Firebase instance variables
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private FirebaseRecyclerAdapter<ExerciseObject, ExerciseViewHolder> mRecyclerViewAdapter;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercises_main);

        setupFirebase();
        initializeScreen();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExerciseActivity.this, WorkoutBuilderActivity.class);
                startActivity(intent);
            }
        });
        // Set up the toolbar and display buttons
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    //User is signed in
                    onSignedInInitialize(user.getDisplayName());
                } else {
                    // User is signed out
                    onSignedOutCleanup();
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)
                                    .setProviders(
                                            AuthUI.EMAIL_PROVIDER,
                                            AuthUI.GOOGLE_PROVIDER)
                                    .build(),
                            RC_SIGN_IN);
                }
            }
        };
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out_menu:
                AuthUI.getInstance().signOut(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN){  }
        else if (resultCode == RESULT_CANCELED){
            finish();
        }
    }
    @Override
    protected void onPause(){
        super.onPause();
        if (mAuthStateListener != null)
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
    }
    @Override
    protected void onResume(){
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
    }
    public void initializeScreen() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mExerciseImage = (ImageView) findViewById(R.id.exercise_image);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mExerciseImage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.gym_pic));
    }
    private void setupFirebase(){
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();

        mDatabaseReference = mFirebaseDatabase.getReference().child("exercises");
    }
    private void attachRecyclerViewAdapter(){
        mRecyclerViewAdapter = new FirebaseRecyclerAdapter<ExerciseObject, ExerciseViewHolder>
                (ExerciseObject.class, R.layout.exercise_cards, ExerciseViewHolder.class, mDatabaseReference) {
            @Override
            protected void populateViewHolder(ExerciseViewHolder viewHolder, final ExerciseObject model, int position) {
                viewHolder.setName(model.getName());
                viewHolder.setDescription(model.getDescription());

                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), ExercisePage.class);
                        intent.putExtra("Exercise Object", model);
                        v.getContext().startActivity(intent);
                    }
                });
            }
        };
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
    }
    private void onSignedOutCleanup() {
        mUsername = ANONYMOUS;
        //detachDatabaseReadListener();
    }
    private void onSignedInInitialize(String username){
        mUsername = username;
        attachRecyclerViewAdapter();
    }
}