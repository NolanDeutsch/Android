package com.fernaak.workoutapp.workoutBuilder;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.fernaak.workoutapp.R;
import com.fernaak.workoutapp.currentWorkout.CurrentWorkoutActivity;
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

public class WorkoutBuilderActivity extends AppCompatActivity{

    private CollapsingToolbarLayout mCollapsingToolbar;
    private Toolbar mToolbar;
    private ImageView mExerciseImage;
    private Spinner repsSpinner, setsSpinner, bodyareaSpinner, exerciseSpinner;
    private Button addExerciseButton, startWorkoutButton;
    private List<String> exercisesList, workoutNameList;
    private LinearLayout exerciseSpinnerLayout, setsRepsSpinnerLayout, addExerciseButtonLayout;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;

    //Exercise values
    private String mUsername, mWorkoutName, mExerciseName, mExerciseArea, mExerciseSets, mExerciseReps;

    public static final String ANONYMOUS = "anonymous";
    public static final int RC_SIGN_IN = 1;
    private boolean setsSelected, repsSelected;

    //Firebase instance variables
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mExercisesDatabaseReference, mWorkoutsDatabaseReference;
    private FirebaseRecyclerAdapter<WorkoutExerciseObject, WorkoutBuilderViewHolder> mRecyclerViewAdapter;
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workout_builder_main);

        mWorkoutName = "Workout 1";

        setupFirebase();
        getCurrentUser();
        //getWorkoutName();
        initializeScreen();
        attachRecyclerViewAdapter();
        populateSpinners();
        eventListeners();

    }
    //-------------------------------------------------------------//
    //Overrides
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

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    //-------------------------------------------------------------//

    private void setupFirebase(){
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();

        //Reference to get and set exercises under exercises table name
        mExercisesDatabaseReference = mFirebaseDatabase.getReference().child("exercises");
        mWorkoutsDatabaseReference = mFirebaseDatabase.getReference().child("workouts");
    }
    public void initializeScreen() {
        //Toolbars
        mCollapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        //Main image
        mExerciseImage = (ImageView) findViewById(R.id.exercise_image);
        mExerciseImage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.gym_pic));
        //Layouts
        exerciseSpinnerLayout = (LinearLayout) findViewById(R.id.exercise_spinner_layout);
        setsRepsSpinnerLayout = (LinearLayout) findViewById(R.id.sets_reps_spinner_layout);
        addExerciseButtonLayout = (LinearLayout) findViewById(R.id.button_add_layout);
        exerciseSpinnerLayout.setVisibility(LinearLayout.GONE);
        setsRepsSpinnerLayout.setVisibility(LinearLayout.GONE);
        addExerciseButtonLayout.setVisibility(LinearLayout.GONE);
        //Spinners for different attributes
        repsSpinner = (Spinner) findViewById(R.id.reps_spinner);
        setsSpinner = (Spinner) findViewById(R.id.sets_spinner);
        bodyareaSpinner = (Spinner) findViewById(R.id.body_area_spinner);
        exerciseSpinner = (Spinner) findViewById(R.id.exercise_spinner);
        //Button to add to database
        addExerciseButton = (Button) findViewById(R.id.add_to_workout_button);
        startWorkoutButton = (Button) findViewById(R.id.start_workout_button);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Set up the toolbar and display buttons
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mCollapsingToolbar.setTitle(mWorkoutName);
    }
    public void populateSpinners(){
        ArrayAdapter<CharSequence> repsAdapter = ArrayAdapter.createFromResource(this, R.array.array_rep_options, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> setsAdapter = ArrayAdapter.createFromResource(this, R.array.array_set_options, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> bodyAreaAdapter = ArrayAdapter.createFromResource(this, R.array.array_body_area_options, android.R.layout.simple_spinner_item);

        repsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        setsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bodyAreaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        bodyareaSpinner.setAdapter(bodyAreaAdapter);
        setsSpinner.setAdapter(setsAdapter);
        repsSpinner.setAdapter(repsAdapter);
    }
    private void setsRepsCheck(){
        if (setsSelected && repsSelected){
            addExerciseButtonLayout.setVisibility(LinearLayout.VISIBLE);
        }
    }
    private void attachRecyclerViewAdapter(){
        mRecyclerViewAdapter = new FirebaseRecyclerAdapter<WorkoutExerciseObject, WorkoutBuilderViewHolder>
                (WorkoutExerciseObject.class, R.layout.list_item, WorkoutBuilderViewHolder.class, mWorkoutsDatabaseReference) {
            @Override
            protected void populateViewHolder(WorkoutBuilderViewHolder viewHolder, final WorkoutExerciseObject model, int position) {
                viewHolder.setName(model.getExerciseName());
                viewHolder.setSets(model.getSets());
                viewHolder.setReps(model.getReps());
                //viewHolder.setImage(model.getArea());


                /**
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), ExercisePage.class);
                        intent.putExtra("Exercise Object", model);
                        v.getContext().startActivity(intent);
                    }
                });
                 */
                //TODO: Launch the appropriate exercise page for the item clicked
            }
        };
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
    }
    private void getCurrentUser(){
        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        if (user != null)
            mUsername = user.getDisplayName();
        else
            mUsername = ANONYMOUS;
    }
    private void getWorkoutName(){
        mWorkoutsDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                workoutNameList = new ArrayList<String>();

                for (DataSnapshot workoutsSnapshot: dataSnapshot.getChildren()) {
                    String workoutName = workoutsSnapshot.child("workoutName").getValue(String.class);
                    workoutNameList.add(workoutName);
                }
                if(workoutNameList != null){
                    String lastItem = workoutNameList.get(workoutNameList.size() - 1);
                    String lastChar = lastItem.substring(lastItem.length() - 1);
                    int num = Integer.parseInt(lastChar);

                    mWorkoutName = "Workout " + (num + 1);
                    mCollapsingToolbar.setTitle(mWorkoutName);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void eventListeners(){
        //Spinner Listeners
        bodyareaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    exerciseSpinnerLayout.setVisibility(LinearLayout.VISIBLE);
                    mExerciseArea = bodyareaSpinner.getSelectedItem().toString().toLowerCase();
                    firebaseSpinner();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        exerciseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setsRepsSpinnerLayout.setVisibility(LinearLayout.VISIBLE);
                mExerciseName = exerciseSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        setsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    setsSelected = true;
                    mExerciseSets = setsSpinner.getSelectedItem().toString();
                } else {
                    setsSelected = false;
                }
                setsRepsCheck();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        repsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    repsSelected = true;
                    mExerciseReps = repsSpinner.getSelectedItem().toString();
                } else {
                    repsSelected = false;
                }
                setsRepsCheck();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(getApplicationContext(), "This will launch a change name dialog", Toast.LENGTH_LONG);
                toast.show();
                //TODO: Create a change workout name dialog
            }
        });
        addExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WorkoutExerciseObject workoutExerciseObject = new WorkoutExerciseObject(mExerciseName, mWorkoutName, mUsername, mExerciseArea, mExerciseSets, mExerciseReps);
                mWorkoutsDatabaseReference.push().setValue(workoutExerciseObject);
            }
        });
        startWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CurrentWorkoutActivity.class);
                intent.putExtra("Workout Name", mWorkoutName);
                v.getContext().startActivity(intent);
            }
        });
    }
    private void firebaseSpinner(){
        mExercisesDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                exercisesList = new ArrayList<String>();

                for (DataSnapshot exerciseSnapshot: dataSnapshot.getChildren()){
                    String area = exerciseSnapshot.child("area").getValue(String.class);
                    String exercise = exerciseSnapshot.child("name").getValue(String.class);
                    if (area.equals(mExerciseArea))
                        exercisesList.add(exercise);
                }
                ArrayAdapter<String> exerciseAdapter = new ArrayAdapter<>(WorkoutBuilderActivity.this, android.R.layout.simple_spinner_item, exercisesList);

                exerciseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                exerciseSpinner.setAdapter(exerciseAdapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
    }
}