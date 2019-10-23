package com.example.healthcare;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.healthcare.classes.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class Dashboard extends AppCompatActivity {


    DatabaseReference ref;
    FirebaseAuth mAuth;
    Button button;
    private EditText age, height, weight;
    float ht, wt, bmi;
    int AGE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_leaderboard, R.id.navigation_dietplan, R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        mAuth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference("users").child(mAuth.getCurrentUser().getUid());


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("height")){
                }
                else {
                    openDialog(getApplicationContext());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void openDialog(Context context){
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dietplan_dialogbox);
        dialog.setTitle("Enter your details");
        dialog.show();
        button = (Button)dialog.findViewById(R.id.save);
        age = dialog.findViewById(R.id.age);
        weight = dialog.findViewById(R.id.weight);
        height = dialog.findViewById(R.id.height);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ageEmpty() & heightEmpty() & weightEmpty()){
                    Toast.makeText(getApplicationContext(), "entered", Toast.LENGTH_SHORT).show();
                    ht = Float.parseFloat(height.getText().toString());
                    wt = Float.parseFloat(weight.getText().toString());
                    AGE = Integer.parseInt(age.getText().toString());
                    ht = ht/100;

                    User user = new User();
                    user.setAge(AGE);
                    user.setHeight(ht);
                    user.setWeight(wt);
                    bmi = user.cal_BMI();


                    ref.child("height").setValue(ht);
                    ref.child("weight").setValue(wt);
                    ref.child("age").setValue(AGE);
                    ref.child("bmi").setValue(bmi);


                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Dashboard.this, MainActivity.class));
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
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
