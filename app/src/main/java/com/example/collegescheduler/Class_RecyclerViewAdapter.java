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
    ArrayList<ClassModel> classModels;

    public Class_RecyclerViewAdapter(Context context, ArrayList<ClassModel> classModels) {
        this.context = context;
        this.classModels = classModels;
    }

    @NonNull
    @Override
    public Class_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view  = inflater.inflate(R.layout.recycler_view_row, parent, false);

        return new Class_RecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Class_RecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.tvName.setText(classModels.get(position).getClassName());
        holder.tvDay.setText(classModels.get(position).getClassDay());
        holder.tvTime.setText(classModels.get(position).getClassTime());
        holder.imageView.setImageResource(classModels.get(position).getImage());

    }

    @Override
    public int getItemCount() {
        return classModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView tvName, tvDay, tvTime;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.icon);
            tvName = itemView.findViewById(R.id.class_name);
            tvDay = itemView.findViewById(R.id.class_day);
            tvTime = itemView.findViewById(R.id.class_time);
        }
    }
}