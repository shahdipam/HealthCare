package com.example.healthcare.ui.dietplan;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.healthcare.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DietPlanAdapter extends ArrayAdapter<String> {

    Activity context;
    String[] time;
    String[] food;

    public DietPlanAdapter(@NonNull Activity context, String[] time, String[] food) {
        super(context, R.layout.dietplan_list_item);

        this.context = context;
        this.time = time;
        this.food = food;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater= context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.dietplan_list_item, null,true);

        TextView time = (TextView)rowView.findViewById(R.id.time);
        TextView food = (TextView)rowView.findViewById(R.id.desc);

        time.setText(this.time[position]);
        food.setText(this.food[position]);

        return rowView;

    }
}
