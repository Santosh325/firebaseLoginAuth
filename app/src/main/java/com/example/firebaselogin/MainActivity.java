package com.example.firebaselogin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
public class MainActivity extends AppCompatActivity {
    Button mlogin;
    TextView msignUp;
    EditText email,password;
    TextView forgotPassword;
    private CallbackManager mCallbackManager;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private LoginButton mLoginButton;
    private static final String TAG = "FacebookAuthentication";
    private AccessTokenTracker mAccessTokenTracker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplication());
        setContentView(R.layout.activity_main);
        mlogin = findViewById(R.id.login);
        msignUp = findViewById(R.id.signup);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        forgotPassword = findViewById(R.id.forgotPassword);
        mLoginButton = findViewById(R.id.login_button);
        mAuth = FirebaseAuth.getInstance();
        mCallbackManager = CallbackManager.Factory.create();
        mLoginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                Toast.makeText(getApplicationContext(),"Login success",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),homeActivity.class));
              
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(),"Login cancelled",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        mlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 String getEmail = email.getText().toString();
                 String getPassword = password.getText().toString();
                if(getEmail.equals("") || getPassword.equals("")) {
                    Toast.makeText(getApplicationContext(),"Please Enter email and password",Toast.LENGTH_SHORT).show();

                } else {
                    signinMethod(getEmail,getPassword);

                }

            }
        });
        msignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),signup.class));
            }
        });
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().sendPasswordResetEmail(email.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Check your email.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });



    }

    @Override
   protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void signinMethod(String getEmail, String getPassword) {
        mAuth.signInWithEmailAndPassword(getEmail,getPassword)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        finish();
                        Toast.makeText(getApplicationContext(),"Login success",Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Please check password",Toast.LENGTH_SHORT).show();

                    }
                });

    }

    @Override
    protected void onStart() {
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if(firebaseUser != null) {
            finish();
            startActivity(new Intent(getApplicationContext(),homeActivity.class));
        }
        super.onStart();
    }
}
