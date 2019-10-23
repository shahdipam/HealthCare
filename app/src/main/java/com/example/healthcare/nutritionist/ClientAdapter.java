package com.example.healthcare.nutritionist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.healthcare.R;
import com.example.healthcare.classes.User;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ClientAdapter extends ArrayAdapter<User> {

    private List<User> clients = new ArrayList<User>();

    static class ClientViewHolder {
        TextView name, age, bmi, dietplan;
    }

    public ClientAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    @Override
    public void add(@Nullable User object) {
        clients.add(object);
        super.add(object);
    }

    @Override
    public int getCount() {
        return this.clients.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        ClientViewHolder viewHolder;

        if (row == null){
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.client_item, parent, false);
            viewHolder = new ClientViewHolder();
            viewHolder.name = row.findViewById(R.id.name);
            viewHolder.age = row.findViewById(R.id.age);
            viewHolder.bmi = row.findViewById(R.id.bmi);
            viewHolder.dietplan = row.findViewById(R.id.plantype);
            row.setTag(viewHolder);
        }
        else {
            viewHolder = (ClientViewHolder) row.getTag();
        }

        User user = getItem(position);
        viewHolder.name.setText(user.getName());
        viewHolder.bmi.setText(String.valueOf(user.getBMI()));
        viewHolder.age.setText(String.valueOf(user.getAge()));
        viewHolder.dietplan.setText("default");

        return row;
    }

}
