package com.example.loginregisterfirebase1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.Iterator;

public class Stats extends AppCompatActivity {
    DatabaseReference dbref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://loginregister-b7bea-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        dbref.orderByChild("liftStatus").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild("liftStatus"))
                {
                    long totalTrips = snapshot.child("liftStatus").getChildrenCount();
                    TextView totalFloors = findViewById(R.id.totalFloors);
                    totalFloors.setText(totalTrips+"");
                    Iterable<DataSnapshot> timewisefloors = snapshot.child("liftStatus").getChildren();
                    Iterator<DataSnapshot> itr = timewisefloors.iterator();
                    TextView recentFloor = findViewById(R.id.recentFloor);
                    while(itr.hasNext())
                        recentFloor.setText(itr.next().getValue().toString().substring(7,8));
                }
                else
                    Toast.makeText(Stats.this, "No Lift Data", Toast.LENGTH_SHORT).show();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}