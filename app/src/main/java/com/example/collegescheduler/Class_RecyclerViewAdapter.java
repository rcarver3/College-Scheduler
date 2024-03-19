package com.example.collegescheduler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Class_RecyclerViewAdapter extends RecyclerView.Adapter<Class_RecyclerViewAdapter.MyViewHolder> {
    Context context;
    public ArrayList<ClassModel> classModels = MainActivity.classModels;

    private final RecyclerViewInterface recyclerViewInterface;

    public Class_RecyclerViewAdapter(Context context, ArrayList<ClassModel> classModels,
                                     RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.classModels = classModels;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public Class_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view  = inflater.inflate(R.layout.recycler_view_row, parent, false);

        return new Class_RecyclerViewAdapter.MyViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull Class_RecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.tvName.setText(classModels.get(position).getClassName());
        holder.tvDay.setText(classModels.get(position).getClassDay());
        holder.tvTime.setText(classModels.get(position).getClassTime());
        holder.imageView.setImageResource(R.drawable.side_nav_bar);
        int i = classModels.get(position).getAssignments().size();
        holder.assignDue.setText(Integer.toString(i));

    }

    @Override
    public int getItemCount() {
        return classModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView tvName, tvDay, tvTime, assignDue;
        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            imageView = itemView.findViewById(R.id.icon);
            tvName = itemView.findViewById(R.id.class_name);
            tvDay = itemView.findViewById(R.id.class_day);
            tvTime = itemView.findViewById(R.id.class_time);
            assignDue = itemView.findViewById(R.id.assignments_due);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recyclerViewInterface != null) {
                        int pos = getAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION) {
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (recyclerViewInterface != null) {
                        int pos = getAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION) {
                            recyclerViewInterface.onItemLongClick("CLASS", pos);
                        }
                    }
                    return true;
                }
            });
        }
    }
}
