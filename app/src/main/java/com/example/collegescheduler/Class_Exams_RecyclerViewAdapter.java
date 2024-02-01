package com.example.collegescheduler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Class_Exams_RecyclerViewAdapter extends RecyclerView.Adapter<Class_Exams_RecyclerViewAdapter.MyViewHolder> {
    Context context;
    ArrayList<ClassModel> classModels;

    private final RecyclerViewInterface recyclerViewInterface;

    public Class_Exams_RecyclerViewAdapter(Context context, ArrayList<ClassModel> classModels,
                                           RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.classModels = classModels;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public Class_Exams_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view  = inflater.inflate(R.layout.recycler_view_row_exams, parent, false);

        return new Class_Exams_RecyclerViewAdapter.MyViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull Class_Exams_RecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.tvName.setText(classModels.get(position).getClassName());
        holder.tvDay.setText(classModels.get(position).getClassDay());
    }

    @Override
    public int getItemCount() {
        return classModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvDay;
        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            tvName = itemView.findViewById(R.id.class_name);
            tvDay = itemView.findViewById(R.id.class_day);

            itemView.setOnClickListener(v -> {
                if (recyclerViewInterface != null) {
                    int pos = getAdapterPosition();

                    if (pos != RecyclerView.NO_POSITION) {
                        recyclerViewInterface.onItemClick(pos);
                    }
                }
            });
        }
    }
}
