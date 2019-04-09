


package com.example.inclassassignment10_thomass;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.icu.text.IDNA;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.example.inclassassignment10_thomass.Keys.EMAIL;


public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListner;
    CallbackManager cbm; //manages the callbacks into the FacebookSd - linked to onActivity

    private LoginButton loginButton;

    private EditText emailText, passwordText;
    private TextView previousEmail;
    private Button signInbtn;
    private String email, password;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        cbm.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        printKeyHash(); //retrieved tag for facebook

        emailText = (EditText) findViewById(R.id.email_text);
        passwordText = (EditText) findViewById(R.id.password_text);
        passwordText.setTransformationMethod(new PasswordTransformationMethod()); //Slack this hides password

        //previousEmail = (TextView) findViewById(R.id.previous_email);
        previousEmail(); //a method


        mAuth = FirebaseAuth.getInstance();
        cbm = CallbackManager.Factory.create(); //facebook
        loginButton = findViewById(R.id.login_facebook); // facebook
        loginButton.setReadPermissions("email"); //facebook must be email or it won't work
        loginButton.setOnClickListener(new View.OnClickListener() { //facebook
            @Override
            public void onClick(View view) {
                signInFB();
            }

            private void signInFB() {
                loginButton.registerCallback(cbm, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                    }

                    @Override
                    public void onError(FacebookException error) {

                    }
                });
            }

            private void handleFacebookAccessToken(AccessToken accessToken) { //method utsed for facebook
                AuthCredential creds = FacebookAuthProvider.getCredential(accessToken.getToken());
                mAuth.signInWithCredential(creds).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Doesn't work", Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        String fb = authResult.getUser().getEmail();
                        Toast.makeText(MainActivity.this, "Connected", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, FireBaseActivity.class);
                        startActivity(intent);

                    }
                });
            }
        });

/*
        mAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user == null) {

                }
            }
        };
    }*/
    }

    private void printKeyHash() { //utilized for getting the facebook ID and secret code
        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.example.inclassassignment10_thomass", PackageManager.GET_SIGNATURES);
            for (Signature sign : info.signatures) {
                MessageDigest messageDigest = MessageDigest.getInstance("SHA");
                messageDigest.update(sign.toByteArray());

            }
        } catch (PackageManager.NameNotFoundException e) {
            // e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            //   e.printStackTrace();
        }
    }


    public void previousEmail() { //method
        Intent intent = getIntent();
        String str = intent.getStringExtra(EMAIL);
        //previousEmail.setText(str);
    }

    @Override
    public void onStart() {
        super.onStart();
        Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show(); //starting toast
        // Check if user is signed in (non-null) and update UI accordingly.
        //mAuth.addAuthStateListener(mAuthListner);
        //updateUI(currentUser); //FIREBASE code that doesn't work
    }

    @Override
    public void onDestroy() { //toast when destroyed
        super.onDestroy();
        Toast.makeText(this, "See you later", Toast.LENGTH_SHORT).show();
    }

    public void SignButton(View view) { //onclick method
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);

    }


    public void LoginIntoFireBase(String email, String password) { // parameter arguements and conditional for email

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        FirebaseUser user = mAuth.getCurrentUser();

                        if (task.isSuccessful()) {
                            Intent intent = new Intent(MainActivity.this, FireBaseActivity.class);
                            startActivity(intent);

                            //FirebaseUser user = mAuth.getCurrentUser();
                            //if (user.isEmailVerified()) {
                            Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();


                            // } else { //False

                            //  Toast.makeText(MainActivity.this, "not valid.",
                            //  Toast.LENGTH_SHORT).show();
                        } else {// (!task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Please signup",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    public void LoginButton(View view) { //onClick method with paramters
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


