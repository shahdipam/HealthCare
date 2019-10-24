package com.example.healthcare.ui.leaderboard;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.healthcare.R;
import com.example.healthcare.classes.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LeaderboardFragment extends Fragment {

    FloatingActionButton addFriend;
    EditText search;
    ListView friendList;
    private FriendAdapter adapter;
    FirebaseAuth mAuth;
    static int no_of_steps;
    List<User> friends;
    List<String> friendIds = new ArrayList<>();
    int flag = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_leaderboard, container, false);

        addFriend = v.findViewById(R.id.addFriend);
        search = v.findViewById(R.id.search);
        friendList = v.findViewById(R.id.friendList);
        adapter = new FriendAdapter(getActivity().getApplicationContext(), R.layout.friend_item);
        friendList.setAdapter(adapter);
        mAuth = FirebaseAuth.getInstance();

        addFriend();

        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                final EditText input = new EditText(getActivity());
                input.setLayoutParams(lp);

                builder.setView(input);
                builder.setTitle("Add Friend");

                builder.setIcon(R.drawable.ic_person_black_24dp);
                builder.setCancelable(false);

                builder.setPositiveButton("ADD",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                final String username = input.getText().toString();
                                Toast.makeText(getContext(), username, Toast.LENGTH_SHORT).show();
                                final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
                                ref.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        for (DataSnapshot ds: dataSnapshot.getChildren()){
                                            if (username.equals(ds.child("firstname").getValue(String.class))){
                                                flag = 0;
                                                ref.child(mAuth.getCurrentUser().getUid()).child("friendList").child(ds.getKey()).setValue(ds.getKey());
                                                addFriend();
                                                break;
                                            }
                                            else flag = 1;
                                        }

                                        if (flag==1){
                                            Toast.makeText(getContext(), "Username does not exist", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });


                            }
                        });

                builder.setNegativeButton("CANCEL",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                builder.show();
            }
        });


        friends = new ArrayList<>();



        Collections.sort(friends, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return o2.getTodays_steps() - o1.getTodays_steps();
            }
        });

        for (int i=0;i<friends.size();i++)
        adapter.add(friends.get(i));

        adapter.notifyDataSetChanged();



        return v;
    }

    public void addFriend(){
        adapter.clear();

        DatabaseReference getfriend = FirebaseDatabase.getInstance().getReference("users").child(mAuth.getCurrentUser().getUid());

        getfriend.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.child("friendList").getChildren()){
//                friendIds.add();
//                Toast.makeText(getContext(), String.valueOf(ds.getValue(String.class)), Toast.LENGTH_SHORT).show();


                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users").child(String.valueOf(ds.getValue(String.class)));
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            User a = dataSnapshot.getValue(User.class);
                            double step = 1000 + Math.random()*5000;
                            int istep = (int) step;
                            User u1 = new User(dataSnapshot.child("firstname").getValue(String.class), istep);

                            adapter.add(u1);
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }

}