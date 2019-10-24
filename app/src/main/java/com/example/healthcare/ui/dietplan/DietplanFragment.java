package com.example.healthcare.ui.dietplan;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthcare.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class DietplanFragment extends Fragment {


    private TextView age, height, weight;
    float ht, wt, bmi;
    int AGE;
    TextView bodymassindex;

    DatabaseReference ref;
    FirebaseAuth mAuth;

    String[] time = {"08:00", "13:00", "16:00", "20:00"};
    String[] food = {"breakfast", "lunch", "snacks", "dinner"};
    ListView listView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_dietplan, container, false);
        age = root.findViewById(R.id.age);
        weight = root.findViewById(R.id.weight);
        height = root.findViewById(R.id.height);
        bodymassindex = root.findViewById(R.id.bmi);
        mAuth = FirebaseAuth.getInstance();

        ref = FirebaseDatabase.getInstance().getReference("users").child(mAuth.getCurrentUser().getUid());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                height.setText(dataSnapshot.child("height").getValue(String.class));
                weight.setText(dataSnapshot.child("weight").getValue(String.class));
                age.setText(dataSnapshot.child("age").getValue(String.class));
                String bmi = dataSnapshot.child("bmi").getValue(Double.class).toString();
                bodymassindex.setText(bmi.substring(0,5));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DietPlanAdapter adapter = new DietPlanAdapter(getContext(), time, food);

        listView = (ListView)root.findViewById(R.id.list);
        listView.setAdapter(adapter);

        return root;
    }



}

