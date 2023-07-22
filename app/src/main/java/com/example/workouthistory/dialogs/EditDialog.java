package com.example.workouthistory.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.workouthistory.R;
import com.example.workouthistory.db.DataBaseHelper;
import com.example.workouthistory.db.entity.User;

public class EditDialog extends AppCompatDialogFragment {

    EditText usernameText, bodyWeightText, heightText, ageText;
    Context context;

    int id;

    public static final String SHARED_PREF = "sharedPrefs";
    public static final String USERNAME = "username";

    private EditDialogListener dialogListener;

    public interface EditDialogListener {
        void onDialogDismissed();
    }

    public EditDialog(Context context, int id) {
        this.context = context;
        this.id = id;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.edit_dialog, null);

        builder.setView(view).setTitle("Edit Information").setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (usernameText.getText().toString().equals("") || bodyWeightText.getText().toString().equals("") ||
                        heightText.getText().toString().equals("") || ageText.getText().toString().equals("")) {
                    Toast.makeText(context, "Please fill all the information", Toast.LENGTH_LONG).show();
                } else {
                    DataBaseHelper db = new DataBaseHelper(getContext());
                    db.updateUser(new User(usernameText.getText().toString(), Integer.parseInt(ageText.getText().toString()),
                            id, Float.parseFloat(bodyWeightText.getText().toString()), Float.parseFloat(heightText.getText().toString())));
                    saveUsername(usernameText.getText().toString());
                    if (dialogListener != null)
                        dialogListener.onDialogDismissed();
                }
            }
        });
        usernameText = view.findViewById(R.id.edit_username);
        bodyWeightText = view.findViewById(R.id.edit_bodyweight);
        heightText = view.findViewById(R.id.edit_height);
        ageText = view.findViewById(R.id.edit_age);


        return builder.create();
    }

    private void saveUsername(String username) {
        Context context = getContext();
        if (context != null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(USERNAME, username);
            editor.apply();
        }
    }

    public void setEditDialogListener(EditDialogListener listener) {
        this.dialogListener = listener;
    }
}
