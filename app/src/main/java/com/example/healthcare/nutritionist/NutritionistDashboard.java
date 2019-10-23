package com.example.healthcare.nutritionist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.healthcare.R;
import com.example.healthcare.classes.Admin;
import com.example.healthcare.classes.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NutritionistDashboard extends AppCompatActivity {

    private ListView listView;
    private FloatingActionButton addDietPlan;
    private ClientAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutritionist_dashboard);
        Admin admin = (Admin) getIntent().getSerializableExtra("admin");
        Toast.makeText(this, "Welcome "+ admin.getName(), Toast.LENGTH_SHORT).show();
        getSupportActionBar().setTitle("Your Clients");

        addDietPlan = findViewById(R.id.addDietPlan);
        listView = findViewById(R.id.clientList);
        adapter = new ClientAdapter(getApplicationContext(), R.layout.client_item);
        listView.setAdapter(adapter);


        adapter.add(new User("Mr A", 54, 25));
        adapter.add(new User("Mr B", 54, 25));
        adapter.add(new User("Mr C", 34, 25));
        adapter.add(new User("Mr D", 24, 25));
        adapter.add(new User("Mr E", 14, 25));
        adapter.add(new User("Mr F", 4, 25));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User selected = adapter.getItem(position);
                startActivity(new Intent(NutritionistDashboard.this, Client_Detailed.class));
                Toast.makeText(NutritionistDashboard.this, selected.getName(), Toast.LENGTH_SHORT).show();
            }
        });


        addDietPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NutritionistDashboard.this, NewDietPlan.class));
            }
        });
    }
}
