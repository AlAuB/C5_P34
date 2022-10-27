package com.example.w7_q34;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    Context context;
    Activity activity;
    ArrayList<String> todo, dateTime, id;

    public CustomAdapter(Activity activity, Context context, ArrayList<String> id, ArrayList<String> todo, ArrayList<String> dateTime) {
        this.activity = activity;
        this.id = id;
        this.context = context;
        this.todo = todo;
        this.dateTime = dateTime;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //Get current time
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat
                = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date();
        String now = simpleDateFormat.format(date);
        //Compare date and time
        String thing = todo.get(position);
        String time = dateTime.get(position);
        holder.todo.setText(thing);
        holder.dateTime.setText(time);
        if (time.compareTo(now) < 0) {
            holder.todo.setTextColor(Color.parseColor("#FF0000"));
            holder.dateTime.setTextColor(Color.parseColor("#FF0000"));
        } else {
            holder.todo.setTextColor(Color.parseColor("#7F7F7F"));
            holder.dateTime.setTextColor(Color.parseColor("#7F7F7F"));
        }
        holder.linearLayout.setOnClickListener(view -> {
            Intent intent = new Intent(context, DeleteActivity.class);
            intent.putExtra("id", id.get(position));
            intent.putExtra("todo", todo.get(position));
            activity.startActivityForResult(intent, 1);
        });
    }

    @Override
    public int getItemCount() {
        return todo.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView todo, dateTime;
        LinearLayout linearLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            todo = itemView.findViewById(R.id.TODO);
            dateTime = itemView.findViewById(R.id.dateTime);
            linearLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
