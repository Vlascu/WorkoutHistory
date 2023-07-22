package com.example.workouthistory.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.workouthistory.R;
import com.example.workouthistory.db.DataBaseHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LogDialog extends AppCompatDialogFragment {

    Context context;
    EditText insertedData;
    String username, final_log = "", sets_log = "", exercise_name;

    private LogDialogListener dialogListener;

    public interface LogDialogListener {
        void onDialogDismissed();
    }

    public LogDialog(Context context, String username, String exercise_name) {
        this.context = context;
        this.username = username;
        this.exercise_name = exercise_name;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.log_dialog, null);

        insertedData = view.findViewById(R.id.insert_log);

        builder.setView(view).setTitle("Insert log").setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DataBaseHelper db = new DataBaseHelper(getContext());
                        final_log += "|";
                        exercise_name = modifyString(exercise_name);
                        String log_data = db.getDataFromColumn(username, exercise_name);
                        log_data += final_log;
                        if (db.addLog(username, exercise_name, log_data))
                            Toast.makeText(context, "Logs added successfully!", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(context, "Logs adding failed!", Toast.LENGTH_LONG).show();
                        final_log = "";
                        sets_log = "";

                        if (dialogListener != null)
                            dialogListener.onDialogDismissed();
                    }
                });
        final AlertDialog dialog = builder.create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button addButton = view.findViewById(R.id.addButton);
                addButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (final_log.equals("")) {
                            Date currentDate = new Date();
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                            final_log= dateFormat.format(currentDate);
                            final_log+="|";

                            insertSet();
                        } else {
                            insertSet();
                        }
                        Toast.makeText(context, "Set added successfully!"
                                , Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        return dialog;
    }

    public void insertSet() {
        sets_log += insertedData.getText().toString() + ",";
        final_log += sets_log;
        sets_log="";
        insertedData.setText("");
    }

    public String modifyString(String input) {
        String lowercaseString = input.toLowerCase();
        return lowercaseString.replaceAll(" ", "_");
    }

    public void setLogDialogListener(LogDialogListener listener) {
        this.dialogListener = listener;
    }
}
