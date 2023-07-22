package com.example.workouthistory.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.workouthistory.CardViewModel;
import com.example.workouthistory.R;
import com.example.workouthistory.adapter.CustomAdapter;
import com.example.workouthistory.dialogs.ConfirmationDialog;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    
    ArrayList<CardViewModel> musclesList;
    RecyclerView recyclerView;

    TextView greeting;

    CustomAdapter adapter;

    String username = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar main_toolbar = findViewById(R.id.include_toolbar);
        setSupportActionBar(main_toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        getUsername();
        greeting = findViewById(R.id.main_toolbar_hello_name);

        greeting.setText("Hello " + username);

        musclesList = new ArrayList<>();
        recyclerView = findViewById(R.id.main_recycler_view);
        adapter = new CustomAdapter(this, musclesList);
        muscleGroupesAdding(recyclerView, musclesList, adapter);
    }

    private void getUsername() {
        SharedPreferences sharedPreferences = getSharedPreferences(Login.SHARED_PREF,MODE_PRIVATE);
        username=sharedPreferences.getString(Login.USERNAME,"");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.main_menu_info_id) {
            Intent intent = new Intent(this, ProfileInfo.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.main_menu_delete_id) {
            openConfirmationDialog(0);
        } else if (item.getItemId() == R.id.main_menu_logout_id) {
            openConfirmationDialog(1);
        }
        return super.onOptionsItemSelected(item);
    }

    private void openConfirmationDialog(int option) {
        ConfirmationDialog confirmationDialog = new ConfirmationDialog(username, option);
        confirmationDialog.show(getSupportFragmentManager(), "Confirmation dialog");
    }

    public void muscleGroupesAdding(RecyclerView recyclerView, ArrayList<CardViewModel> musclesList, CustomAdapter adapter) {

        musclesList.add(new CardViewModel("Chest", R.drawable.chest));
        musclesList.add(new CardViewModel("Back", R.drawable.back));
        musclesList.add(new CardViewModel("Legs", R.drawable.legs));
        musclesList.add(new CardViewModel("Shoulders", R.drawable.shoulders));
        musclesList.add(new CardViewModel("Biceps", R.drawable.biceps));
        musclesList.add(new CardViewModel("Triceps", R.drawable.triceps));

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

}