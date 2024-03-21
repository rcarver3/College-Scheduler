package com.example.collegescheduler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListTaskAdapter  extends BaseAdapter {
    Context ctx;
    ArrayList<Object> tasks;
    LayoutInflater inflater;


    public ListTaskAdapter(Context ctx, ArrayList<Object> tasks) {
        this.ctx = ctx;
        this.tasks = tasks;
        inflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return tasks.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.recycler_view_column, null);
        View txtView = convertView.findViewById(R.id.examsRecyclerView);
        return convertView;
    }
}
