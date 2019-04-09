package com.example.inclassassignment10_thomass;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FireBaseActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextView displayText;

    private FirebaseDatabase database = FirebaseDatabase.getInstance(); //linked to the Firebase
    private DatabaseReference holidayRef = database.getReference("Trip One"); //Location - cloud
    private DatabaseReference anotherHolidayRef = database.getReference("Other Trips"); // This is the location
    private ArrayList<Holiday> people = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fire_base);
        mAuth = FirebaseAuth.getInstance();

        displayText = (TextView) findViewById(R.id.display_text); //Shows constructor

        holidayRef.addValueEventListener(new ValueEventListener() { //if values change they can be pulled
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Holiday h = dataSnapshot.getValue(Holiday.class); //object - where we are looking - library - getvalue
                // displayText.setText(h.toString()); //object into string

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(FireBaseActivity.this, "Error loading Firebase", Toast.LENGTH_SHORT).show();
            }

        });
    }

    public void setTrip(View view) { //push this Onclick
        holidayRef.setValue(new Holiday("NewfoundLand", 15000, false, true));//object from Trip constructor
        Toast.makeText(this, "Added to FireBase", Toast.LENGTH_SHORT).show();
    }

    public void addTrip(View view) { //pushes onClick
        anotherHolidayRef.push().setValue(new Holiday("Faroe Islands", 20000, false, true)); //push always starts a new one
        Toast.makeText(this, "Added another trip", Toast.LENGTH_SHORT).show();
    }
}
