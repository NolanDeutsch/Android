package com.fernaak.workoutapp.currentWorkout;


import android.widget.EditText;

public class SetObject {

    private String setNumber;
    private EditText reps;
    private EditText weight;

    public SetObject(){}

    public SetObject(String setNumber){
        this.setNumber = setNumber;
    }
    public String getSetNumber(){ return setNumber; }
    public void setText(String setNumber){this.setNumber = setNumber;}
}
