package com.example.workouthistory.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workouthistory.dialogs.ConfirmationDialog;
import com.example.workouthistory.LogsDataModel;
import com.example.workouthistory.R;
import com.example.workouthistory.db.DataBaseHelper;

import java.util.ArrayList;

public class LogsAdapter extends RecyclerView.Adapter<LogsAdapter.MyViewHolder> {

    private Context context;

    private ArrayList<LogsDataModel> logs;

    FragmentManager fragmentManager;

    String username, exercise_name;

    public LogsAdapter(Context context, ArrayList<LogsDataModel> logs, FragmentManager fragmentManager, String username, String muscle_name) {
        this.context = context;
        this.logs = logs;
        this.fragmentManager=fragmentManager;
        this.username=username;
        this.exercise_name = muscle_name;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView dateText, logsText;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            dateText = itemView.findViewById(R.id.date_logs);
            logsText = itemView.findViewById(R.id.workout_logs);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.logs_card_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final LogsDataModel model = logs.get(position);
        holder.dateText.setText(model.getDate());
        holder.logsText.setText(model.getWorkouts());

        LogsAdapter currentAdapter = this;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION)
                    openDialog(currentAdapter,adapterPosition);
            }
        });
    }

    private void openDialog(LogsAdapter currentAdapter, int position) {
        ConfirmationDialog confirmationDialog = new ConfirmationDialog(position,currentAdapter,3);
        confirmationDialog.show(fragmentManager,"Delete confirmation");
    }

    @Override
    public int getItemCount() {
        if(logs !=null)
            return logs.size();
        return -1;
    }

    public void deleteItem(int position) {
        if (position >= 0 && position < logs.size()) {
            logs.remove(position);
            notifyItemRemoved(position);
            addModifiedStringToDB(logs);
        }
    }

    private void addModifiedStringToDB(ArrayList<LogsDataModel> logs) {
        String completedString = reconstructString(logs);

        DataBaseHelper db = new DataBaseHelper(context);
        db.addLog(username,exercise_name, completedString);
    }

    private String reconstructString(ArrayList<LogsDataModel> logs) {
        ArrayList<String> notCompleteString = new ArrayList<>();
        for (LogsDataModel model : logs)
        {
            notCompleteString.add(model.getDate()+"|");
            notCompleteString.add(model.getWorkouts().replace("\n", ",")+","+"|");
        }
        StringBuilder completeStringBuilder= new StringBuilder();
        for (String substring : notCompleteString)
        {
            completeStringBuilder.append(substring);
        }
       return completeStringBuilder.toString();
    }

}
