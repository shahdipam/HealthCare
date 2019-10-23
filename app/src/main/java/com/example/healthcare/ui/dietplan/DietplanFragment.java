package com.example.healthcare.ui.dietplan;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.healthcare.R;
import com.example.healthcare.classes.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DietplanFragment extends Fragment {


    private EditText age, height, weight;
    float ht, wt, bmi;
    int AGE;
    TextView bodymassindex;
    Button save;
    DatabaseReference ref;
    FirebaseAuth mAuth;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_dietplan, container, false);

        age = root.findViewById(R.id.age);
        weight = root.findViewById(R.id.weight);
        height = root.findViewById(R.id.height);
        save = root.findViewById(R.id.save);
        bodymassindex = root.findViewById(R.id.bmi);
        mAuth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference("users").child(mAuth.getCurrentUser().getUid());


        return root;
    }



}