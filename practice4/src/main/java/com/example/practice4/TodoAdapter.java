package com.example.practice4;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {
    public final static String LOG_TAG = "TodoAdapter";
    private List<Task> tasks;

    public TodoAdapter(List<Task> tasks) {
        this.tasks = tasks;
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new TodoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        holder.textView.setText(tasks.get(position).title);
        holder.checkBox.setChecked(tasks.get(position).isChecked);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean c = holder.checkBox.isChecked();
                holder.checkBox.setChecked(!c);
                Log.d(LOG_TAG, "Action: Status of task \"" + holder.textView.getText() + "\" is changed");
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Log.d(LOG_TAG, "Action: Task \"" + holder.textView.getText() + "\" is removed");
                int position = holder.getAdapterPosition();
                tasks.remove(position);
                notifyItemRemoved(position);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size() ;
    }

    public static class TodoViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        CheckBox checkBox;
        public TodoViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            checkBox = itemView.findViewById(R.id.checkBox);
        }
    }
}
