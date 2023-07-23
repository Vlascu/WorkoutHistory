package com.example.workouthistory.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.example.workouthistory.R;
import com.example.workouthistory.db.DataBaseHelper;

public class Register extends AppCompatActivity {

    Button go_to_login, register;
    EditText usernameText, bodyweightText, heightText, ageText;

    String username;
    float bodyweight=0,height=0;

    public static final String SHARED_PREF = "sharedPrefs";
    public static final String USERNAME = "username";

    int age=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar toolbar = findViewById(R.id.register_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        go_to_login = findViewById(R.id.register_login_button);
        register = findViewById(R.id.register_button);

        usernameText=findViewById(R.id.register_username);
        bodyweightText=findViewById(R.id.register_bodyweight);
        heightText= findViewById(R.id.register_heigth);
        ageText= findViewById(R.id.register_age);

        DataBaseHelper db = new DataBaseHelper(this);


        go_to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this,Login.class);
                startActivity(intent);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username=usernameText.getText().toString();
                if(bodyweightText.getText().toString().equals(""))
                    bodyweight=0;
                else
                    bodyweight=Float.parseFloat(bodyweightText.getText().toString());

                if(heightText.getText().toString().equals(""))
                    height=0;
                else
                    height=Float.parseFloat(heightText.getText().toString());

                if(ageText.getText().toString().equals(""))
                    age=0;
                else
                    age= Integer.parseInt(ageText.getText().toString());

                if(username.equals("") || bodyweight==0 || height==0 || age==0)
                {
                    Toast.makeText(Register.this, "Please fill all the information", Toast.LENGTH_LONG).show();
                }
                else
                {
                    long id=db.insertUser(username,bodyweight,height,age);
                    if(id!=-1)
                    {
                        Toast.makeText(Register.this, "Registered successfully!", Toast.LENGTH_LONG).show();
                        saveUsername(username);
                        Intent intent = new Intent(Register.this,MainActivity.class);
                        startActivity(intent);
                    }
                    else
                        Toast.makeText(Register.this, "Username taken, insert another username!", Toast.LENGTH_LONG).show();
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