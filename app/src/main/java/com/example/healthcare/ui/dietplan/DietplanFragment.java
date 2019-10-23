package com.example.healthcare.ui.dietplan;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ageEmpty() & heightEmpty() & weightEmpty()){
                    Toast.makeText(getActivity(), "entered", Toast.LENGTH_SHORT).show();
                    ht = Float.parseFloat(height.getText().toString());
                    wt = Float.parseFloat(weight.getText().toString());
                    AGE = Integer.parseInt(age.getText().toString());
                    ht = ht/100;

                    bmi = wt/(ht*ht);

                    if (bmi<18.5)
                        bodymassindex.setText(""+bmi+": Underweight");
                    if (bmi>18.5 & bmi< 24.9)
                        bodymassindex.setText(""+bmi+": Normal");
                    if (bmi>25 & bmi<29.9)
                        bodymassindex.setText(""+bmi+": Overweight");
                    if (bmi>30)
                        bodymassindex.setText(""+bmi+": Obese");

                    height.setEnabled(false);
                    weight.setEnabled(false);
                    age.setEnabled(false);

                    ref.child("height").setValue(ht);
                    ref.child("weight").setValue(wt);
                    ref.child("age").setValue(AGE);
                    ref.child("bmi").setValue(bmi);
                }

            }
        });

        return root;
    }

    private boolean weightEmpty() {
        if (weight.getText().toString().trim().isEmpty()){
            weight.setError("Empty");
            return false;
        }
        else
            return true;
    }

    private boolean heightEmpty() {
        if (height.getText().toString().trim().isEmpty()){
            height.setError("Empty");
            return false;
        }
        else
            return true;
    }

    private boolean ageEmpty() {
        if (age.getText().toString().trim().isEmpty()){
            age.setError("Empty");
            return false;
        }
        else
            return true;
    }
}