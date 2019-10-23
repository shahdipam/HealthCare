package com.example.healthcare.nutritionist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.healthcare.R;
import com.example.healthcare.classes.Admin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class NutritionistLogin extends AppCompatActivity {

        private TextInputLayout email, password;
        private Button loginBtn;
        private FirebaseAuth mAuth;
        private DatabaseReference ref;
        private ProgressDialog progressDialog;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_nutritionist_login);
            FirebaseApp.initializeApp(this);

            mAuth = FirebaseAuth.getInstance();

            email = findViewById(R.id.text_input_email);
            password = findViewById(R.id.text_input_password);
            loginBtn = findViewById(R.id.loginBtn);

            progressDialog = new ProgressDialog(this);

            loginBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    userLogin();
                }
            });

        }

        private Boolean validateEmail() {
            String emailinput = email.getEditText().getText().toString().trim();

            if (emailinput.isEmpty()) {
                email.setError("Field can't be empty");
                return false;
            }
            else if (!Patterns.EMAIL_ADDRESS.matcher(emailinput).matches()) {
                email.setError("Enter a valid email");
                return false;
            }else
                email.setError(null);
            return true;
        }

        private Boolean validatePassword() {
            String passwordinput = password.getEditText().getText().toString().trim();

            if (passwordinput.isEmpty()) {
                password.setError("Field can't be empty");
                return false;
            }
            else {
                password.setError(null);
                return true;
            }
        }

        public void userLogin() {
            final String mail = email.getEditText().getText().toString();
            final String pass = password.getEditText().getText().toString();


            if (!validateEmail() | !validatePassword()) {
                return;
            } else {
                ref = FirebaseDatabase.getInstance().getReference("admin");

                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds: dataSnapshot.getChildren()){
                            Admin admin = ds.getValue(Admin.class);

                            if (admin.getEmail().equals(mail) && admin.getPassword().equals(pass)){
                                Toast.makeText(NutritionistLogin.this, "correct", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(NutritionistLogin.this, NutritionistDashboard.class);
                                intent.putExtra("admin", admin);
                                startActivity(intent);
                            }
                            else
                                Toast.makeText(NutritionistLogin.this, "incorrect", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }

        }
    }

