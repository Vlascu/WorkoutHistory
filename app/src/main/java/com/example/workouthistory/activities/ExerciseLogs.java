package com.example.workouthistory.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.workouthistory.LogsDataModel;
import com.example.workouthistory.R;
import com.example.workouthistory.adapter.CustomAdapter;
import com.example.workouthistory.adapter.LogsAdapter;
import com.example.workouthistory.db.DataBaseHelper;
import com.example.workouthistory.dialogs.LogDialog;

import java.util.ArrayList;
import java.util.Arrays;

public class ExerciseLogs extends AppCompatActivity implements LogDialog.LogDialogListener {

    TextView exerciseTextName;
    ImageView exercisePicture;
    LogsAdapter adapter;
    RecyclerView recyclerView;
    Button add_log;

    String exercise_name, username = "", complete_log = "";

    ArrayList<LogsDataModel> logs = new ArrayList<>();

    int exercise_picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_logs);
        Toolbar toolbar = findViewById(R.id.normal_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        add_log = findViewById(R.id.logs_add);
        recyclerView = findViewById(R.id.exercise_logs_recycler_view);
        exerciseTextName = findViewById(R.id.logs_exercise_name);
        exercisePicture = findViewById(R.id.logs_exercise_picture);
        setExerciseNameAndPicture(exerciseTextName, exercisePicture);

        getUsername();
        updateRecyclerView();

        add_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLogDialog(username, exercise_name);
            }
        });
    }

    private void updateRecyclerView() {
        exercise_name = modifyString(exercise_name);
        getLogsFromDb(username, exercise_name);
        makeLogs(complete_log);
        FragmentManager fragmentManager = getSupportFragmentManager();
        adapter = new LogsAdapter(this, logs, fragmentManager, username, exercise_name);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void makeLogs(String complete_log) {
        logs.clear();
        if(complete_log!=null)
        {
            ArrayList<String> separatedLogs = new ArrayList<>(Arrays.asList(complete_log.split("\\|")));
            for (int index = 0; index < separatedLogs.size() - 1; index += 2) {
                String workout_logs = separatedLogs.get(index + 1).replace(",","\n");
                logs.add(new LogsDataModel(separatedLogs.get(index), workout_logs.
                        substring(0,workout_logs.length()-1)));
            }
        }

    }

    private void getLogsFromDb(String username, String exercise_name) {
        DataBaseHelper db = new DataBaseHelper(this);
        complete_log = db.getDataFromColumn(username, exercise_name);
    }

    public String modifyString(String input) {
        String lowercaseString = input.toLowerCase();
        return lowercaseString.replaceAll(" ", "_");
    }

    private void openLogDialog(String username, String exercise_name) {
        LogDialog logDialog = new LogDialog(this, username, exercise_name);
        logDialog.setLogDialogListener(this);
        logDialog.show(getSupportFragmentManager(), "Add logs");
    }

    private void getUsername() {
        SharedPreferences sharedPreferences = getSharedPreferences(Login.SHARED_PREF, MODE_PRIVATE);
        username = sharedPreferences.getString(Login.USERNAME, "");
    }

    private void setExerciseNameAndPicture(TextView exerciseTextName, ImageView exercisePicture) {

        getInformation();
        exerciseTextName.setText(exercise_name);
        exercisePicture.setImageResource(exercise_picture);

    }

    private void getInformation() {
        SharedPreferences sharedPreferences = getSharedPreferences(CustomAdapter.SHARED_PREF, MODE_PRIVATE);
        exercise_name = sharedPreferences.getString(CustomAdapter.EXERCISE_NAME, "");
        exercise_picture = sharedPreferences.getInt(CustomAdapter.EXERCISE_PICTURE, 0);
    }

    @Override
    public void onDialogDismissed() {
        updateRecyclerView();
    }
}