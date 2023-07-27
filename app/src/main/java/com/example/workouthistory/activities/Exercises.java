package com.example.workouthistory.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.workouthistory.CardViewModel;
import com.example.workouthistory.R;
import com.example.workouthistory.adapter.CustomAdapter;

import java.util.ArrayList;

public class Exercises extends AppCompatActivity {

    ArrayList<CardViewModel> chestExercises, backExercises, shoulderExercises, bicepsExercises, tricepsExercises,
            absExercises, legExercises;
    RecyclerView recyclerView;
    CustomAdapter adapter;
    TextView muscleTextName;
    ImageView musclePicture;

    String muscle_name;
    int muscle_picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);

        Toolbar toolbar = findViewById(R.id.exercises_normal_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        muscleTextName = findViewById(R.id.exercises_muscle_name);
        musclePicture = findViewById(R.id.exercises_muscle_picture);
        recyclerView = findViewById(R.id.exercises_recycler_view);

        exercisesListsInitialization();
        setAdapterBasedOnMuscle(muscleTextName, musclePicture);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    private void exercisesListsInitialization() {
        chestExercises = new ArrayList<>();
        chestExercises.add(new CardViewModel("BB chest press", R.drawable.bb_chest_press));
        chestExercises.add(new CardViewModel("BB incline chest press", R.drawable.bb_incline_chest_press));
        chestExercises.add(new CardViewModel("DB chest press", R.drawable.db_chest_press));
        chestExercises.add(new CardViewModel("DB incline chest press", R.drawable.db_incline_chest_press));
        chestExercises.add(new CardViewModel("Machine chest press", R.drawable.machine_chest_press));
        chestExercises.add(new CardViewModel("Cable crossover", R.drawable.cable_crossover));

        backExercises = new ArrayList<>();
        backExercises.add(new CardViewModel("BB row", R.drawable.bb_row));
        backExercises.add(new CardViewModel("Close grip pulldown", R.drawable.close_grip_pulldown));
        backExercises.add(new CardViewModel("DB bent over row", R.drawable.db_bent_over_row));
        backExercises.add(new CardViewModel("Seated cable row", R.drawable.seated_cable_row));
        backExercises.add(new CardViewModel("Straight arm pulldown", R.drawable.straight_arm_pulldown));
        backExercises.add(new CardViewModel("T bar row", R.drawable.t_bar_row));

        shoulderExercises = new ArrayList<>();
        shoulderExercises.add(new CardViewModel("Cable lateral raise", R.drawable.cable_lateral_raise));
        shoulderExercises.add(new CardViewModel("DB military press", R.drawable.db_military_press));
        shoulderExercises.add(new CardViewModel("SM military press", R.drawable.sm_military_press));
        shoulderExercises.add(new CardViewModel("Y cable crossovers", R.drawable.y_cable_crossovers));
        shoulderExercises.add(new CardViewModel("DB lateral raises", R.drawable.db_lateral_raises));

        bicepsExercises = new ArrayList<>();
        bicepsExercises.add(new CardViewModel("Cable hammer curls", R.drawable.cable_hammer_curls));
        bicepsExercises.add(new CardViewModel("DB curls", R.drawable.db_curls));
        bicepsExercises.add(new CardViewModel("EZ curl", R.drawable.ez_curl));
        bicepsExercises.add(new CardViewModel("DB hammer curls", R.drawable.db_hammer_curls));
        bicepsExercises.add(new CardViewModel("incline DB curls", R.drawable.incline_db_curls));

        tricepsExercises = new ArrayList<>();
        tricepsExercises.add(new CardViewModel("Close grip press", R.drawable.close_grip_press));
        tricepsExercises.add(new CardViewModel("Parallel bars dip", R.drawable.parallel_bars_dip));
        tricepsExercises.add(new CardViewModel("Rope pushdown", R.drawable.rope_pushdown));
        tricepsExercises.add(new CardViewModel("Skullcrushers", R.drawable.skullcrushers));

        absExercises = new ArrayList<>();
        absExercises.add(new CardViewModel("Crunches", R.drawable.crunches));

        legExercises = new ArrayList<>();
        legExercises.add(new CardViewModel("BB Squat", R.drawable.bb_squat));

    }

    private void setAdapterBasedOnMuscle(TextView muscleTextName, ImageView musclePicture) {

        getInformation();
        muscleTextName.setText(muscle_name);
        musclePicture.setImageResource(muscle_picture);
        if (muscle_name.equals("Chest")) {
            adapter = new CustomAdapter(this, chestExercises);
        } else if (muscle_name.equals("Back")) {
            adapter = new CustomAdapter(this, backExercises);

        } else if (muscle_name.equals("Legs")) {
            adapter = new CustomAdapter(this, legExercises);

        } else if (muscle_name.equals("Shoulders")) {
            adapter = new CustomAdapter(this, shoulderExercises);

        } else if (muscle_name.equals("Biceps")) {
            adapter = new CustomAdapter(this, bicepsExercises);

        } else if (muscle_name.equals("Triceps")) {
            adapter = new CustomAdapter(this, tricepsExercises);

        }
    }

    private void getInformation() {
        SharedPreferences sharedPreferences = getSharedPreferences(CustomAdapter.SHARED_PREF, MODE_PRIVATE);
        muscle_name = sharedPreferences.getString(CustomAdapter.MUSCLE_NAME, "");
        muscle_picture = sharedPreferences.getInt(CustomAdapter.MUSCLE_PICTURE, 0);
    }
}