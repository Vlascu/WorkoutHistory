package com.example.workouthistory;

public class LogsDataModel {

    private String date,workouts;

    public LogsDataModel(String date, String workouts) {
        this.date = date;
        this.workouts = workouts;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWorkouts() {
        return workouts;
    }

    public void setWorkouts(String workouts) {
        this.workouts = workouts;
    }
}
