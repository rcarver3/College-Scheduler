package com.example.collegescheduler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListTaskAdapter  extends RecyclerView.Adapter<ListTaskAdapter.MyViewHolder> {
    Context ctx;
    ArrayList<Object> tasks;
    LayoutInflater inflater;


    public ListTaskAdapter(Context ctx, ArrayList<Object> tasks) {
        this.ctx = ctx;
        this.tasks = tasks;
        inflater = LayoutInflater.from(ctx);
    }

    public int getCount() {
        return tasks.size();
    }

    public Object getItem(int position) {
        return null;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvDueDate, tvDueTime, tvName;
        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            tvDueDate = itemView.findViewById(R.id.dueDate);
            tvDueTime = itemView.findViewById(R.id.dueTime);
            tvName = itemView.findViewById(R.id.taskName);

            itemView.setOnClickListener(v -> {
                if (recyclerViewInterface != null) {
                    int pos = getAdapterPosition();

                    if (pos != RecyclerView.NO_POSITION) {
                        recyclerViewInterface.onItemClick(pos);
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (recyclerViewInterface != null) {
                        int pos = getAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION) {
                            recyclerViewInterface.onItemLongClick("ASSIGNMENT", pos);
                        }
                    }
                    return true;
                }
            });
        }
    }

    @NonNull
    @Override
   public ListTaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return ;
    }


    @Override
    public void onBindViewHolder(@NonNull ListTaskAdapter.MyViewHolder holder, int position) {
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return 0;
    }


}
