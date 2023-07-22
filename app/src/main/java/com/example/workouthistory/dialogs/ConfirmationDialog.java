package com.example.workouthistory.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.workouthistory.activities.Login;
import com.example.workouthistory.activities.MainActivity;
import com.example.workouthistory.activities.Register;
import com.example.workouthistory.adapter.LogsAdapter;
import com.example.workouthistory.db.DataBaseHelper;

public class ConfirmationDialog extends AppCompatDialogFragment {

    private String username;
    private int option, position;

    private float weight;

    private LogsAdapter currentAdapter;

    private float height;


    public ConfirmationDialog(String username, int option) {
        this.username = username;
        this.option = option;
    }

    public ConfirmationDialog(int position, LogsAdapter currentAdapter, int option) {
        this.position = position;
        this.currentAdapter = currentAdapter;
        this.option=option;
    }

    public ConfirmationDialog(int option, String username1, float weight, float height)
    {
        this.username = username1;
        this.option=option;
        this.weight=weight;
        this.height=height;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if (option == 0) {
            builder.setTitle("Confirmation")
                    .setMessage("Are you sure you want to delete this account?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MainActivity mainActivity = new MainActivity();
                            DataBaseHelper db = new DataBaseHelper(getContext());
                            db.deleteUser(username);
                            Intent intent = new Intent(getContext(), Register.class);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
        } else if (option == 1) {
            builder.setTitle("Confirmation")
                    .setMessage("Are you sure you want to log out?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getContext(), Login.class);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
        }
        else if (option==2)
        {
            float bmi= (float) (weight/Math.pow((height/100),2));
            String category = "";

            if(bmi<18.5)
                category = "UNDERWEIGHT";
            else if(bmi>=18.5 && bmi <25)
                category = "NORMAL";
            else if(bmi>=25 && bmi <30)
                category = "OVERWEIGHT";
            else if(bmi>=30 && bmi<35)
                category = "OBESE";
            else if(bmi>=35)
                category = "EXTREMELY OBESE";

            String formatedBMI = String.format("%.1f",bmi);
            builder.setTitle("BMI")
                    .setMessage("You BMI is "+ formatedBMI + "\nYou are " + category)
                    .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

        }
        else if(option==3)
        {
            builder.setTitle("Confirmation").setMessage("Are you sure you want to delete this log?")
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            currentAdapter.deleteItem(position);
                            Toast.makeText(getContext(), "Log deleted successfully", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        return builder.create();
    }
}
