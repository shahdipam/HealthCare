package com.example.healthcare.ui.profile;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthcare.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    private EditText name, email, height, weight, age, nut;
    private ImageView edit;
    private Button save;
    private TextView bmi, bmistat;
    FirebaseAuth mAuth;
    DatabaseReference ref;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        name = v.findViewById(R.id.name);
        email = v.findViewById(R.id.email);
        height = v.findViewById(R.id.height);
        weight = v.findViewById(R.id.weight);
        age = v.findViewById(R.id.age);
        edit = v.findViewById(R.id.editInfo);
        save = v.findViewById(R.id.save);
        nut = v.findViewById(R.id.nut);
        mAuth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference("users").child(mAuth.getCurrentUser().getUid());

        getData();

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "bruh", Toast.LENGTH_SHORT).show();
                name.setEnabled(true);
                email.setEnabled(true);
                height.setEnabled(true);
                weight.setEnabled(true);
                age.setEnabled(true);
                edit.setClickable(false);
                save.setVisibility(View.VISIBLE);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref.child("email").setValue(email.getText().toString().trim());
                ref.child("height").setValue(height.getText().toString().trim());
                ref.child("weight").setValue(weight.getText().toString().trim());
                ref.child("age").setValue(age.getText().toString().trim());
                name.setEnabled(false);
                email.setEnabled(false);
                height.setEnabled(false);
                weight.setEnabled(false);
                age.setEnabled(false);
                edit.setClickable(true);
                save.setVisibility(View.GONE);
            }
        });
        return v;
    }

    private void getData() {

        ref.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                name.setText(""+dataSnapshot.child("firstname").getValue()+" "+dataSnapshot.child("lastname").getValue());
                email.setText(""+dataSnapshot.child("email").getValue());
                height.setText(""+(Float.parseFloat(String.valueOf(dataSnapshot.child("height").getValue()))*100));
                weight.setText(""+dataSnapshot.child("weight").getValue());
                age.setText(""+dataSnapshot.child("age").getValue());
                nut.setText(""+dataSnapshot.child("Nut_code").getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        // TODO: Use the ViewModel
    }

}
