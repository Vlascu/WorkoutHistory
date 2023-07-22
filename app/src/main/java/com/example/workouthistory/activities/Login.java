package com.example.workouthistory.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.workouthistory.R;
import com.example.workouthistory.db.DataBaseHelper;

public class Login extends AppCompatActivity {

    Button go_to_register, login;
    EditText usernameText;

    String username = "";

    public static final String SHARED_PREF = "sharedPrefs";
    public static final String USERNAME = "username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = findViewById(R.id.login_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        go_to_register = findViewById(R.id.login_register_button);
        login = findViewById(R.id.login_button);
        usernameText = findViewById(R.id.login_username);
        DataBaseHelper db = new DataBaseHelper(this);

        go_to_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = usernameText.getText().toString();
                if (username.equals(""))
                    Toast.makeText(Login.this, "Please enter a username!", Toast.LENGTH_LONG).show();
                else {
                    if (!db.checkUsername(username))
                        Toast.makeText(Login.this, "User doesn't exist, go to Register window", Toast.LENGTH_SHORT).show();
                    else {
                        Toast.makeText(Login.this, "Login successfully!", Toast.LENGTH_SHORT).show();
                        saveUsername(username);
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });
    }

    private void saveUsername(String username) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USERNAME, username);
        editor.apply();
    }
}