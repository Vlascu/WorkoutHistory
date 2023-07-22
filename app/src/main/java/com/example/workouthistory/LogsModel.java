package com.example.workouthistory;

import android.widget.LinearLayout;

public class LogsModel {

    private String date_string;
    private LinearLayout linearLayout;


    public LogsModel(String date_string, LinearLayout linearLayout) {
        this.date_string = date_string;
        this.linearLayout = linearLayout;
    }

    public String getDate_string() {
        return date_string;
    }

    public void setDate_string(String date_string) {
        this.date_string = date_string;
    }

    public LinearLayout getLinearLayout() {
        return linearLayout;
    }

    public void setLinearLayout(LinearLayout linearLayout) {
        this.linearLayout = linearLayout;
    }
}
