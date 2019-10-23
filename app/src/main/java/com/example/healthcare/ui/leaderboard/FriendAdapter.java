package com.example.healthcare.ui.leaderboard;

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

public class FriendAdapter extends ArrayAdapter<User> {

    private List<User> friendList = new ArrayList<>();

    static class FriendViewHolder{
        TextView name, todaySteps;
    }

    public FriendAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    @Override
    public void add(@Nullable User object) {
        friendList.add(object);
        super.add(object);
    }

    @Override
    public int getCount() {
        return this.friendList.size();
    }

    @Nullable
    @Override
    public User getItem(int position) {
        return this.friendList.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        FriendViewHolder viewHolder;
        if (row==null){
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.friend_item, parent, false);

            viewHolder = new FriendViewHolder();
            viewHolder.name = row.findViewById(R.id.name);
            viewHolder.todaySteps = row.findViewById(R.id.todaysSteps);
            row.setTag(viewHolder);
        } else {
            viewHolder = (FriendViewHolder) row.getTag();
        }
            User user = getItem(position);

            viewHolder.name.setText(user.getName());
            viewHolder.todaySteps.setText(String.valueOf(user.getTodays_steps()));


        return row;
    }
}
