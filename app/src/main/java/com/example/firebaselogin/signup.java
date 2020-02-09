package com.example.firebaselogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class signup extends AppCompatActivity {
    Button signup,login;
    EditText email,password;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        signup = findViewById(R.id.signup);
        login = findViewById(R.id.login);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        mAuth = FirebaseAuth.getInstance();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getEmail = email.getText().toString();
                String getPassword = password.getText().toString();

                if(getEmail.equals("") || getPassword.equals("")) {
                    Toast.makeText(getApplicationContext(),"Please Enter email and password", Toast.LENGTH_SHORT).show();


                } else {
                    signupMethod(getEmail,getPassword);
                }
            }
        });
    }
    public void signupMethod(String getEmail, String getPassword) {
        mAuth.createUserWithEmailAndPassword(getEmail,getPassword)
               .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                   @Override
                   public void onSuccess(AuthResult authResult) {
                       finish();
                       Toast.makeText(getApplicationContext(),"Sign up Success", Toast.LENGTH_SHORT).show();
                       startActivity(new Intent(getApplicationContext(),homeActivity.class));
                   }
               })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Sign up Failed", Toast.LENGTH_SHORT).show();

                    }
                });
        }

}
