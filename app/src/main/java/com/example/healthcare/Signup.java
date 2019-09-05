package com.example.healthcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Signup extends AppCompatActivity {

    private TextInputLayout firstname, lastname, email, password;
    private EditText refcode;
    private Button signupBtn;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    private DatabaseReference ref;
    //private DatabaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();

        firstname = findViewById(R.id.text_input_firstname);
        lastname = findViewById(R.id.text_input_lastname);
        email = findViewById(R.id.text_input_email);
        password = findViewById(R.id.text_input_password);
        signupBtn = (Button) findViewById(R.id.signupBtn);
        progressDialog = new ProgressDialog(this);
        refcode = findViewById(R.id.refCode);
        //db = new DatabaseHelper(this);


        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private Boolean validateFname(){
        String fnameinput = firstname.getEditText().getText().toString().trim();
        if(fnameinput.isEmpty()) {
            firstname.setError("Field can't be empty");
            return false;
        }
        else {
            firstname.setError(null);
            return true;
        }
    }

    private Boolean validateLname(){
        String lnameinput = lastname.getEditText().getText().toString().trim();
        if(lnameinput.isEmpty()) {
            lastname.setError("Field can't be empty");
            return false;
        }
        else {
            lastname.setError(null);
            return true;
        }
    }

    private Boolean validateEmail() {
        String emailinput = email.getEditText().getText().toString().trim();

        if (emailinput.isEmpty()){
            email.setError("Field can't be empty");
            return false;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(emailinput).matches()) {
            email.setError("Enter a valid email");
            return false;
        }
        else{
            email.setError(null);
            return true;
        }
    }

    private Boolean validatePassword() {
        String passwordinput = password.getEditText().getText().toString().trim();

        if (passwordinput.isEmpty()) {
            password.setError("Field can't be empty");
            return false;
        }
        if (passwordinput.length() < 8) {
            password.setError("Enter a stronger password");
            return false;
        } else {
            password.setError(null);
            return true;
        }
    }


    public void registerUser() {

        final String mail = email.getEditText().getText().toString().trim();
        final String fname = firstname.getEditText().getText().toString().trim();
        final String lname = lastname.getEditText().getText().toString().trim();
        final String pass = password.getEditText().getText().toString().trim();
        final String nutcode = refcode.getText().toString().trim();

        if (!validateEmail() | !validatePassword() | !validateFname() | !validateLname()) {
            return;
        } else {

            progressDialog.setMessage("Registering User");
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(mail, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {


                    if (task.isSuccessful()) {

                        FirebaseUser currUser = mAuth.getCurrentUser();
                        String userid = currUser.getUid();
                        ref = FirebaseDatabase.getInstance().getReference("users").child(userid);

                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("firstname", fname);
                        hashMap.put("lastname", lname);
                        hashMap.put("email", mail);
                        hashMap.put("Nut_code",nutcode);

                        ref.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    progressDialog.dismiss();
                                    Toast.makeText(Signup.this, "User Registered", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Signup.this, Dashboard.class));
                                    finish();

                                }
                            }
                        });

                    } else
                        Toast.makeText(Signup.this, "Error registering", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });

        }
    }
}
