package com.fernaak.workoutapp.exerciseItems;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.fernaak.workoutapp.R;

public class ExercisePage extends AppCompatActivity {

    private ExerciseObject mObject;
    private ImageView mExerciseImage;
    private TextView mExerciseBodyArea;
    private TextView mExerciseDescription;
    private VideoView mVideo;
    private CollapsingToolbarLayout collapsingToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_item);

        // Grabbing Resources From Layout
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar);
        mExerciseImage = (ImageView) findViewById(R.id.exercise_image);
        mExerciseBodyArea = (TextView) findViewById(R.id.text_view_body_areas);
        mExerciseDescription = (TextView) findViewById(R.id.text_view_description);
        mVideo = (VideoView) findViewById(R.id.video_view);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        // Set up the toolbar and display buttons
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        mObject = (ExerciseObject) intent.getSerializableExtra("Exercise Object");
        getSupportActionBar().setTitle(mObject.getName());
        mExerciseDescription.setText(mObject.getDescription());
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
