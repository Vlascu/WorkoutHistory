package com.example.workouthistory.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.workouthistory.R;
import com.example.workouthistory.db.DataBaseHelper;
import com.example.workouthistory.db.entity.User;
import com.example.workouthistory.dialogs.ConfirmationDialog;
import com.example.workouthistory.dialogs.EditDialog;

public class ProfileInfo extends AppCompatActivity implements EditDialog.EditDialogListener {

    TextView usernameText, ageText, bodyweightText, heightText;

    Button editButton, bmiButton;

    DataBaseHelper db;

    User myUser;

    String username= "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_info);

        Toolbar toolbar = findViewById(R.id.normal_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        usernameText = findViewById(R.id.profile_info_username);
        ageText = findViewById(R.id.profile_info_age);
        bodyweightText = findViewById(R.id.profile_info_weight);
        heightText = findViewById(R.id.profile_info_height);
        editButton = findViewById(R.id.profile_info_edit);
        bmiButton = findViewById(R.id.profile_info_bmi);

        getUsername();
        db = new DataBaseHelper(this);
        myUser = db.getUser(username);

        usernameText.setText(getString(R.string.username) + username);
        ageText.setText(getString(R.string.age) + String.valueOf(myUser.getAge()));
        bodyweightText.setText(getString(R.string.bodyweight) + String.valueOf(myUser.getBody_weight()));
        heightText.setText(getString(R.string.height) + String.valueOf(myUser.getHeight()));

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEditDialog();
            }
        });

        bmiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBmiDialog(username, myUser.getBody_weight(), myUser.getHeight());
            }
        });
    }

    private void openBmiDialog(String username, float body_weight, float height) {
        ConfirmationDialog confirmationDialog = new ConfirmationDialog(2,username,body_weight,height);
        confirmationDialog.show(getSupportFragmentManager(),"BMI dialog");
    }

    private void getUsername() {
        SharedPreferences sharedPreferences = getSharedPreferences(Login.SHARED_PREF,MODE_PRIVATE);
        username=sharedPreferences.getString(Login.USERNAME,"");
    }

    private void openEditDialog() {
        EditDialog editDialog = new EditDialog(this, myUser.getId());
        editDialog.setEditDialogListener(this);
        editDialog.show(getSupportFragmentManager(), "Edit Dialog");
    }
    public void refreshUserInfo() {

        getUsername();

        myUser = db.getUser(username);

        usernameText.setText("");
        ageText.setText("");
        bodyweightText.setText("");
        heightText.setText("");

        usernameText.setText(getString(R.string.username) + username);
        ageText.setText(getString(R.string.age) + String.valueOf(myUser.getAge()));
        bodyweightText.setText(getString(R.string.bodyweight) + String.valueOf(myUser.getBody_weight()));
        heightText.setText(getString(R.string.height) + String.valueOf(myUser.getHeight()));
    }
    @Override
    public void onDialogDismissed() {
        refreshUserInfo();
    }

}