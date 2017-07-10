package com.fernaak.workoutapp.workoutBuilder;

public class WorkoutExerciseObject {

    private String exerciseName;
    private String workoutName;
    private String userName;
    private String area;
    private String sets;
    private String reps;

    public WorkoutExerciseObject() {}

    public WorkoutExerciseObject(String exerciseName, String workoutName, String userName, String area, String sets, String reps) {
        this.exerciseName = exerciseName;
        this.workoutName = workoutName;
        this.userName = userName;
        this.area = area;
        this.sets = sets;
        this.reps = reps;

    }

    public String getExerciseName() { return exerciseName; }

    public String getWorkoutName() { return workoutName; }

    public String getUserName() { return userName; }

    public String getArea(){ return area; }

    public String getSets(){ return sets; }

    public String getReps(){ return reps; }


}
