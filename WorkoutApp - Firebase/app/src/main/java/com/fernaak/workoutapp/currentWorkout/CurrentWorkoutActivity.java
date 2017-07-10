package com.fernaak.workoutapp.currentWorkout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.fernaak.workoutapp.R;
import com.fernaak.workoutapp.workoutBuilder.WorkoutBuilderViewHolder;
import com.fernaak.workoutapp.workoutBuilder.WorkoutExerciseObject;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CurrentWorkoutActivity  extends AppCompatActivity {

    private TextView exerciseName;
    private ListView setsListView, cardListView;
    private List<String> exercisesList, setList;
    public static final String ANONYMOUS = "anonymous";
    private SetListAdapter mAdapter;

    private String mUsername, mWorkoutName, mSets;

    // Firebase instance variables
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mExercisesDatabaseReference, mWorkoutsDatabaseReference;
    private FirebaseRecyclerAdapter<WorkoutExerciseObject, WorkoutBuilderViewHolder> mRecyclerViewAdapter;
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.current_workout_main);

        Intent intent = getIntent();
        mWorkoutName = (String) intent.getSerializableExtra("Workout Name");

        setupFirebase();
        initializeScreen();
        firebaseExerciseName();

    }

    private void setupFirebase(){
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();

        //If you don't know the users name
        if (mUsername == null)
            getCurrentUser();
        mWorkoutsDatabaseReference = mFirebaseDatabase.getReference().child("workouts");
    }
    public void initializeScreen() {
        exerciseName = (TextView) findViewById(R.id.tv_exercise_name);
        setsListView = (ListView) findViewById(R.id.lv_sets);

        // Initialize ListView and its adapter
        final List<SetObject> setObject = new ArrayList<>();
        mAdapter = new SetListAdapter(this, R.layout.current_workout_main, setObject);
        setsListView.setAdapter(mAdapter);
    }
    private void getCurrentUser(){
        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        if (user != null)
            mUsername = user.getDisplayName();
        else
            mUsername = ANONYMOUS;
    }
    private void firebaseExerciseName(){
        mWorkoutsDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                exercisesList = new ArrayList<String>();

                for (DataSnapshot exerciseSnapshot: dataSnapshot.getChildren()) {
                    String workout = exerciseSnapshot.child("workoutName").getValue(String.class);
                    String exercise = exerciseSnapshot.child("exerciseName").getValue(String.class);
                    String sets = exerciseSnapshot.child("sets").getValue(String.class);
                    if (workout.equals(mWorkoutName)) {
                        exercisesList.add(exercise);
                        mSets = sets;
                    }
                }

                exerciseName.setText(exercisesList.get(0));
                setList = new ArrayList<String>();
                for(int index = 1; index < Integer.parseInt(mSets); index++){
                    SetObject set = new SetObject(Integer.toString(index));
                    mAdapter.add(set);
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
    }
}