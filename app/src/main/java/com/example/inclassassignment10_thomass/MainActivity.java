


package com.example.inclassassignment10_thomass;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.example.inclassassignment10_thomass.Keys.EMAIL;


public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListner;


    private EditText emailText, passwordText;
    private TextView previousEmail;
    private Button signInbtn;
    private String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailText = (EditText) findViewById(R.id.email_text);
        passwordText = (EditText) findViewById(R.id.password_text);
        previousEmail = (TextView) findViewById(R.id.previous_email);
        previousEmail();


        mAuth = FirebaseAuth.getInstance();
        mAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user == null) {

                }
            }
        };
    }


    public void previousEmail() {
        Intent intent = getIntent();
        String str = intent.getStringExtra(EMAIL);
        previousEmail.setText(str);
    }

    @Override
    public void onStart() {
        super.onStart();
        Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show();
        // Check if user is signed in (non-null) and update UI accordingly.
        //mAuth.addAuthStateListener(mAuthListner);
        //updateUI(currentUser); //FIREBASE code that doesn't work
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "See you later", Toast.LENGTH_SHORT).show();
    }

    public void SignButton(View view) {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);

    }


    public void LoginIntoFireBase(String email, String password) {

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user.isEmailVerified()) {
                                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            }

                        } else { //False

                            Toast.makeText(MainActivity.this, "not valid.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        if (!task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Please signup",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    public void LoginButton(View view) {
        LoginIntoFireBase(emailText.getText().toString(), passwordText.getText().toString()); //parameter being called in string
    }

        /*email = emailText.getText().toString(); // converts to String vale for the mAuth
        password = passwordText.getText().toString();


        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) { //abstract boolean
                    Toast.makeText(MainActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                    FirebaseUser user = mAuth.getCurrentUser();
                    // updateUI(user);
                } else {
                    Toast.makeText(MainActivity.this, task.getResult().getUser().getEmail() + "signup complete", Toast.LENGTH_SHORT).show();
                    // updateUI(null);
                    finish();
                }
            }
        });*/

    public void LogOut(View view) {
        moveTaskToBack(true); //stackoverflow
        finish();
        Toast.makeText(this, "Bye", Toast.LENGTH_SHORT).show();
        mAuth.signOut(); //developer site
        //Intent intent = new Intent(MainActivity.this, Ma.class);
        //startActivity(intent);

    }
}


