package com.fernaak.workoutapp.exerciseItems;

import java.io.Serializable;

public class ExerciseObject implements Serializable{

    private String description;
    private String name;
    private String area;
    private String mPhotoUrl;

    public ExerciseObject() {}

    public ExerciseObject(String name, String description, String area) {
        this.name = name;
        this.description = description;
        this.area = area;
    }

    public String getDescription() { return description; }

    public String getName() { return name; }

    public String getArea(){ return area; }

    public String getPhotoUrl() {
        return mPhotoUrl;
    }

}