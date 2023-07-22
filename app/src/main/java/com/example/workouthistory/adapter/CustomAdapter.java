package com.example.workouthistory.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workouthistory.CardViewModel;
import com.example.workouthistory.activities.ExerciseLogs;
import com.example.workouthistory.activities.Exercises;
import com.example.workouthistory.activities.MainActivity;
import com.example.workouthistory.R;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    public static final String EXTRA_MUSCLE = "com.example.workouthistory.EXTRA_MUSCLE";
    public static final String EXTRA_MUSCLE_PICTURE = "com.example.workouthistory.EXTRA_MUSCLE_PICTURE";

    public static final String SHARED_PREF = "sharedPrefs";
    public static final String MUSCLE_NAME = "muscle_name";
    public static final String EXERCISE_NAME = "exercise_name";
    public static final String MUSCLE_PICTURE = "muscle_picture";
    public static final String EXERCISE_PICTURE = "exercise_picture";
    private Context context;

    private ArrayList<CardViewModel> muscleGroups;

    public CustomAdapter(Context context, ArrayList<CardViewModel> muscleGroups) {
        this.context = context;
        this.muscleGroups = muscleGroups;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.main_card_image);
            textView = itemView.findViewById(R.id.main_card_text);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.muscle_groupe_cards, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CardViewModel model = muscleGroups.get(position);
        holder.textView.setText(model.getMuscleName());
        holder.imageView.setImageResource(model.getImage());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (context instanceof MainActivity) {

                    Intent intent = new Intent(context, Exercises.class);
                    String muscle_name = holder.textView.getText().toString();
                    int muscle_picture = model.getImage();
                    saveInformation(0, muscle_name, muscle_picture);
                    context.startActivity(intent);

                } else if (context instanceof Exercises) {

                    Intent intent = new Intent(context, ExerciseLogs.class);
                    String exercise_name = holder.textView.getText().toString();
                    int exercise_picture = model.getImage();
                    saveInformation(1, exercise_name, exercise_picture);
                    context.startActivity(intent);

                }

            }
        });

    }

    private void saveInformation(int option, String name, int picture) {
        if (option == 0) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF, context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(MUSCLE_NAME, name);
            editor.putInt(MUSCLE_PICTURE, picture);
            editor.apply();
        } else if (option == 1) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF, context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(EXERCISE_NAME, name);
            editor.putInt(EXERCISE_PICTURE, picture);
            editor.apply();
        }
    }

    @Override
    public int getItemCount() {
        return muscleGroups.size();
    }

}
