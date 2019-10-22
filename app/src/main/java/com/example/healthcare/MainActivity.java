  package com.example.healthcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthcare.nutritionist.NutritionistLogin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

  public class MainActivity extends AppCompatActivity {

      private TextInputLayout email, password;
      private TextView redirectToSignup, nutritionist;
      private Button loginBtn;
      private FirebaseAuth mAuth;

      private ProgressDialog progressDialog;

      @Override
      protected void onStart() {

          super.onStart();
          mAuth = FirebaseAuth.getInstance();

          if (mAuth.getCurrentUser() != null) {
              startActivity(new Intent(MainActivity.this, Dashboard.class));
              finish();
          }
      }

      @Override
      protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_main);
          FirebaseApp.initializeApp(this);

          mAuth = FirebaseAuth.getInstance();

          email = findViewById(R.id.text_input_email);
          password = findViewById(R.id.text_input_password);
          loginBtn = (Button) findViewById(R.id.loginBtn);
          redirectToSignup = findViewById(R.id.redirect);
          nutritionist = findViewById(R.id.redirectToNutritionist);

          progressDialog = new ProgressDialog(this);

          loginBtn.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  userLogin();
              }
          });
          redirectToSignup.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  startActivity(new Intent(MainActivity.this, Signup.class));

              }
          });

          nutritionist.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  startActivity(new Intent(MainActivity.this, NutritionistLogin.class));
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
          if (passwordinput.length() < 8) {
              password.setError("Incorrect password");
              return false;
          }
          else {
              password.setError(null);
              return true;
          }
      }

      public void userLogin() {
          String mail = email.getEditText().getText().toString();
          String pass = password.getEditText().getText().toString();

          if (!validateEmail() | !validatePassword()) {
              return;
          } else {

              progressDialog.setMessage("Logging in");
              progressDialog.show();

              mAuth.signInWithEmailAndPassword(mail, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                  @Override
                  public void onComplete(@NonNull Task<AuthResult> task) {
                      progressDialog.dismiss();

                      if (task.isSuccessful()) {
                          Toast.makeText(MainActivity.this, "Logged in", Toast.LENGTH_SHORT).show();
                          Intent intent = new Intent(MainActivity.this, Dashboard.class);
                          startActivity(intent);

                      } else
                          Toast.makeText(MainActivity.this, "Incorrect email or password", Toast.LENGTH_SHORT).show();


                  }
              });
          }

      }
}
